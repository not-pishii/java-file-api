# Проектный анализ новой библиотеки генерации исходного Java-кода (для проекта type-safe-messages)

## TL;DR
- Ручная конкатенация строк в type-safe-messages работоспособна, но хрупка; JavaParser переносить туда не стоит (он оптимизирован под парсинг и модификацию существующего кода, а не под генерацию с нуля, тяжёл по зависимостям и не даёт type-safety). JavaPoet (оригинал заархивирован 10 октября 2024, форк Palantir жив под координатой `com.palantir.javapoet`) остаётся практичным эволюционным выбором.
- Все существующие библиотеки (JavaPoet, JavaParser, Roaster, CodeModel, JDT) разделяют один корневой изъян: они позволяют собрать синтаксически невалидный AST, а тело метода моделируют строками (stringly-typed), поэтому валидность проверяется только javac позже.
- Стратегически перспективна новая библиотека, переносящая принципы `java.lang.classfile` (JEP 484, финализирован в JDK 24): immutable sealed-модель, builder-как-Consumer, transforms, символические дескрипторы вместо строк, плюс type-state builders, делающие невалидный Java невыразимым. Baseline Java 25.

## Key Findings
1. **Как эмитится код в annotation processors.** Стандартный путь: `processingEnv.getFiler().createSourceFile(name, originatingElements...)`, затем запись через `Writer`/`PrintWriter`. Три техники наполнения: (а) сырые строки (как сейчас в type-safe-messages), (б) JavaPoet (собственная древовидная модель `TypeSpec`/`MethodSpec`), (в) JavaParser (полноценный AST). Filer сам управляет раундами обработки и инкрементальностью через originating elements.
2. **JavaParser не создан для генерации.** Его модель мутабельна, stringly-typed по именам/типам, не гарантирует синтаксическую валидность, а `LexicalPreservingPrinter` имеет много багов форматирования. Он тянет тяжёлый транзитивный граф (JavaCC-парсер, symbol-solver), что в annotation-processor-режиме требует шейдинга и вредит скорости раундов javac.
3. **JavaPoet: статус и боли.** Оригинал `com.squareup:javapoet` заархивирован 10 октября 2024 (страница репозитория: «This repository was archived by the owner on Oct 10, 2024. It is now read-only», последний тег javapoet-1.13.0, коммит от 18 июня 2020). Форк `com.palantir.javapoet` активно развивается (records через `recordBuilder`, sealed через `addPermittedSubclass`). Ключевая боль: формат-строки `$T/$N/$L/$S`, тела методов не моделируются (это просто строки, «не обязательно валидный Java-код, javac проверит потом»).
4. **Общий изъян библиотек: невалидные состояния выразимы.** Можно собрать абстрактный метод с телом, интерфейс с непубличным полем, дублирующиеся модификаторы. Валидация откладывается до компиляции сгенерированного кода.
5. **Class-File API как образец дизайна.** Три абстракции: element (immutable), builder (Consumer соответствующего элемента), transform. Sealed-иерархии + pattern matching вместо visitor'ов. Символические дескрипторы `ClassDesc`/`MethodTypeDesc` (`java.lang.constant`) вместо строк.

## Details

### Область 1. Кодогенерация в annotation processors и применимость JavaParser

**Механика Filer.** Процессор наследует `AbstractProcessor`, объявляет поддерживаемые аннотации и версию (`@SupportedAnnotationTypes`, `@SupportedSourceVersion`). В методе `process(...)` для каждого элемента вызывается `getFiler().createSourceFile(qualifiedName, originatingElement)`, после чего в открытый `Writer` пишется текст класса. Важная деталь для инкрементальности: передача originating elements (типов, вызвавших генерацию) позволяет Gradle/javac определять, какие сгенерированные файлы устарели (Gradle различает isolating- и aggregating-процессоры и требует регистрации в `META-INF/gradle/incremental.annotation.processors`). Файл с данным именем можно создать лишь один раз за проход, повторная попытка бросает `FilerException`.

В type-safe-messages это ровно то, что делают `CompileTimeBundleWriter`, `RuntimeBundleWriter`, `ContractWriter`: собирают строку и пишут через Filer, а `JavaStrings` экранирует строковые литералы. Плюсы такого подхода: нулевые зависимости у процессора (важно, так как процессор попадает на `annotationProcessor`-classpath), полный контроль над форматированием, тривиальная инкрементальность. Минусы: экранирование, импорты, генерики и валидность полностью на плечах автора; любая опечатка проявится только при компиляции сгенерированного кода; поддержка новых синтаксисов (records, sealed, text blocks) требует ручного кодирования.

**Что решает и чего не решает JavaParser.** JavaParser даёт полноценный AST: `CompilationUnit`, `ClassOrInterfaceDeclaration`, `MethodDeclaration`, `BlockStmt`, выражения (`AssignExpr`, `FieldAccessExpr`, `NameExpr`) и т. д. Он умеет и билдить дерево программно (`cu.addClass(...).addField(...).addMethod(...)`), и печатать его (`PrettyPrinter`), и сохранять исходное форматирование при модификации (`LexicalPreservingPrinter`). Поддерживает синтаксис вплоть до современных версий Java (ветка поддерживает Java 1-25). Для чего он действительно хорош: анализ, рефакторинг, трансформация существующего кода.

Но для генерации с нуля в аннотационном процессоре он проблематичен:
- **Мутабельность и отсутствие гарантий валидности.** Узлы AST мутабельны, а система типов не мешает собрать бессмысленное дерево (метод без тела с телом, поле без инициализатора там, где он обязателен). Валидность — не свойство типа.
- **Stringly-typed имена и типы.** Типы задаются строками (`book.addField("String", "title")`), импорты и разрешение имён (`JavaSymbolSolver`) требуют ручной настройки `CombinedTypeSolver`.
- **Проблемы печати/форматирования.** У `LexicalPreservingPrinter` известная история багов: неверные отступы при добавлении аннотаций (issue #1461) и комментариев (issue #3387), потеря или смещение line-комментариев при удалении узлов (issue #3441), расхождения между `toString()` и `LexicalPreservingPrinter.print()` (issue #2199). Для генерации это означает нестабильный, недетерминированный вывод.
- **Вес и шейдинг.** Артефакт `javaparser-symbol-solver-core` тянет парсер (JavaCC) и solver. В процессоре это лишний вес на apt-classpath и потенциальные конфликты версий, требующие релокации (shade — стандартная рекомендация для процессоров, репакующих свои зависимости). В раундах javac парсинг ради генерации — лишняя работа.
- **Вердикт по проекту.** Для type-safe-messages переход на JavaParser не оправдан: проект генерирует новый код из `.properties`, а не трансформирует существующий Java. JavaParser стал бы шагом в сторону (парсер там, где нужен эмиттер). Если нужен эволюционный шаг от строк, разумнее JavaPoet (Palantir-форк); стратегический шаг — новая библиотека (Область 3).

### Область 2. Каталог болей существующих библиотек

**JavaPoet (square/javapoet и форк palantir/javapoet).**
- *Статус.* Репозиторий Square заархивирован 10 октября 2024, стал read-only; последний релиз оригинала — javapoet-1.13.0 (коммит от 18 июня 2020). В дискуссии «Future of JavaPoet» (#866) мейнтейнер Jesse Wilson (@swankjesse) написал дословно: «I'm interested in handing this project off to a new steward». Там же Palantir подтвердил форк: «we use Javapoet heavily and have been maintaining a fork here: https://github.com/palantir/javapoet with classes using a new package name (not a drop-in replacement)». Форк меняет координату на `com.palantir.javapoet:javapoet` (пример миграции в README заменяет `com.squareup:javapoet:1.13.0` на `com.palantir.javapoet:javapoet:0.5.0`), добавлены records (`TypeSpec#recordBuilder`) и permitted-подклассы sealed-типов (`TypeSpec.Builder#addPermittedSubclass`).
- *Stringly-typed формат-строки.* Тело методов не моделируется структурно: используется собственный синтаксис `$L` (literal без экранирования), `$S` (строка с кавычками и экранированием), `$T` (тип с авто-импортом), `$N` (имя). Ошибка в плейсхолдере или числе аргументов — рантайм-ошибка генератора либо невалидный вывод. `CodeBlock` в javadoc прямо описан как «фрагмент, не обязательно корректный Java-код, класс полагается, что javac проверит корректность позже».
- *Невалидный код представим.* Модификаторы задаются набором `javax.lang.model.element.Modifier`; можно указать несочетаемые комбинации. Интерфейсные методы «должны быть PUBLIC ABSTRACT», а поля «PUBLIC STATIC FINAL» — это документная договорённость, а не гарантия типа.
- *Отставание в фичах в оригинале.* Records (issue #829) и sealed (issue #823) годами висели открытыми в Square-репозитории и приехали только в форк Palantir. Регрессии экранирования тоже случались (issue #446: двойные кавычки перестали экранироваться в 1.6.0).
- *Разрешение типов и коллизии FQN.* JavaPoet автоматически управляет импортами и это его сильная сторона, но `TypeName.get(TypeMirror)` может бросить исключение на незавершённых типах (`CompletionFailure`, class file not found), и приходится вручную откатываться к `ClassName.bestGuess(...)`. Type-use аннотации на `TypeMirror` не подхватываются `TypeName.get` (issue #685).
- *Интеграция с javax.lang.model.* Это плюс: `TypeName.get(TypeMirror)`, `ClassName.get(TypeElement)`, модификаторы из `javax.lang.model`. Оговорка: класс `Modifier` недоступен на Android (касается только кода-генерирующего-код).

**JavaParser.** Мутабельный AST, stringly-typed типы, отсутствие гарантий валидности, тяжёлые зависимости, нестабильное форматирование при lexical preservation (перечислено в Области 1). Как генератор проигрывает специализированным эмиттерам, поскольку это в первую очередь парсер.

**Roaster (JBoss Forge, org.jboss.forge.roaster).** Fluent-API поверх Eclipse JDT (`Roaster.create(JavaClassSource.class).setName(...).addMethod()...`). Тела методов задаются строками (`setBody("return null;")`), то есть та же stringly-typed проблема. Зависит от `roaster-jdt` (рантайм). Ориентирован скорее на round-trip (парсинг + модификация + форматирование), чем на строгую генерацию.

**CodeModel (com.sun.codemodel, изначально GlassFish/JAXB).** Классический «code DOM»: `JCodeModel` → `JDefinedClass` → `JMethod`/`JFieldVar`/`JBlock`, работа сверху вниз, узлы владеются моделью. Достоинство: выражения и операторы моделируются объектами (`JExpr._this().ref(...)`, `body._return(...)`), а не только строками, есть проверки (нельзя создать класс с именем из `java.lang`, проверка имён пакетов). Проблема: оригинал фактически заброшен (мигрирован в JAXB RI, «legacy viewing only»); живут форки — `com.helger:jcodemodel` (добавлены records, `TYPE_USE`-аннотации, try-with-resources) и `com.unquietcode.tools.jcodemodel`. API многословный, дублирует модификаторы через битовые маски `JMod`, `JClassAlreadyExistsException` — checked-исключение на ровном месте.

**Eclipse JDT AST.** Мощный, промышленный (на нём же стоит Roaster), поддерживает актуальные уровни языка (`AST.JLS21` и новее), но API громоздкий (фабрики `AST`, `ASTRewrite`, `ASTParser`), рассчитан на IDE-сценарии и тяжёл как зависимость вне Eclipse.

**KotlinPoet (как эталон дизайна, square/kotlinpoet).** Живой преемник идей JavaPoet для `.kt`: `FileSpec`/`TypeSpec`/`FunSpec`/`PropertySpec`, авто-импорты через `%T`, форматтеры `%P`/`%S`. Показателен честный вывод его документации: KotlinPoet защищает от части невалидных комбинаций (например, в исходнике `FunSpec` есть runtime-проверка `require(code.isEmpty() || !builder.modifiers.contains(KModifier.ABSTRACT)) { "abstract function ... cannot have code" }` — нельзя добавить тело абстрактной функции), но всё ещё «will let us generate code that we shouldn't — for example, we can specify multiple access modifiers simultaneously, or we can specify both abstract and final simultaneously». То есть даже лучший в классе builder-эмиттер не достигает полной type-safety, и часть инвариантов держится на рантайм-проверках, а не на типах.

**Javassist / ByteBuddy (только для контраста).** Работают с байткодом, а не с исходником; ByteBuddy — про рантайм-генерацию классов и агенты. Не конкуренты source-генератору, но полезны как напоминание, что байткод-мир уже получил свой современный immutable-API — Class-File API.

**Сводка рецидивирующих болей (каталог):**
- (а) *Невалидный код представим.* Абстрактный метод с телом, интерфейсное поле без инициализатора, дублирующиеся/несочетаемые модификаторы (JavaPoet, JavaParser, JDT, Roaster). KotlinPoet ловит часть рантайм-проверками, но не всё.
- (б) *Тела методов stringly-typed.* JavaPoet (`$L/$S/$T/$N`), Roaster (`setBody(String)`), JavaParser (частично) не дают компиляторной проверки тела на этапе сборки дерева.
- (в) *Разрешение типов/импортов и коллизии FQN.* Авто-импорт есть у JavaPoet/KotlinPoet/CodeModel, но `TypeName.get(TypeMirror)` ломается на незавершённых типах, type-use аннотации теряются.
- (г) *Генерики и annotated type-use.* Поддержаны неполно (issue #685 у JavaPoet, `TYPE_USE` добавляли форком в jcodemodel).
- (д) *Text blocks и синтаксис Java 21-25.* В оригинальном JavaPoet отсутствовали records/sealed; text blocks/pattern-matching-switch в моделях не представлены (эмитируются как строки).
- (е) *Форматирование/идемпотентность.* `LexicalPreservingPrinter` даёт нестабильный вывод; JavaPoet фиксирован на 2 пробела по умолчанию.
- (ж) *Статус сопровождения.* Square JavaPoet заархивирован; CodeModel — legacy; живы форки (Palantir, helger) и KotlinPoet.

### Область 3. Эскиз новой библиотеки: принципы Class-File API, перенесённые на генерацию исходников

**Принципы-источники из java.lang.classfile (JEP 484).** Class-File API построен на трёх абстракциях: *element* (immutable-описание части класс-файла), *builder* (специфичные методы + он же `Consumer` нужного типа элемента), *transform* (функция element+builder). Хронология: превью JEP 457 (JDK 22) и JEP 466 (JDK 23), финализация — JEP 484 в JDK 24 («We here propose to finalize the API in JDK 24 with minor changes»). Ключевые свойства: immutable-объекты для надёжного шаринга при трансформациях; древовидное представление; ленивость и user-driven навигация; единый словарь для streaming- и materialized-видов; трансформация как «эмерджентное» свойство (flatMap по элементам); pattern matching в switch вместо visitor'ов (JEP 484 дословно: «Visitors are bulky and inflexible; the visitor pattern is often characterized as a library workaround for the lack of pattern matching in a language. Now that the Java language has pattern matching we can express things more directly and concisely»); символические дескрипторы `ClassDesc`/`MethodTypeDesc` из `java.lang.constant` вместо строк.

Точная форма иерархии (её и надо копировать для исходников):
- Корневой маркер `ClassFileElement`; над ним `CompoundElement<E extends ClassFileElement> extends ClassFileElement, Iterable<E>` (модель, которая одновременно и элемент, и итерируема по своим под-элементам).
- Sealed-словари: `ClassElement`, `MethodElement`, `FieldElement`, `CodeElement`. Например, прямые под-интерфейсы `ClassElement` — `AccessFlags`, `ClassFileVersion`, `FieldModel`, `Interfaces`, `MethodModel`, `Superclass` (плюс атрибуты и `CustomAttribute`); то есть `MethodModel`/`FieldModel` сами являются `ClassElement`, что и даёт двойственность «модель есть элемент». (Oracle помечает эти `permits`-списки как «(not exhaustive)», так как они охватывают под-пакеты `attribute` и `instruction`.)
- Единый корень билдеров: `sealed interface ClassFileBuilder<E extends ClassFileElement, B extends ClassFileBuilder<E,B>> extends Consumer<E> permits ClassBuilder, FieldBuilder, MethodBuilder, CodeBuilder`. Каждый конкретный билдер параметризован своим элементом: `ClassBuilder extends ClassFileBuilder<ClassElement, ClassBuilder>`. JEP 484 дословно: «Each kind of compound element has a corresponding builder which has specific building methods (e.g., ClassBuilder::withMethod) and is also a Consumer of the appropriate element type».
- Методы-фабрики принимают Consumer-лямбды: `ClassBuilder withMethod(String name, MethodTypeDesc desc, int flags, Consumer<? super MethodBuilder> handler)`, `withMethodBody(..., Consumer<? super CodeBuilder>)`, `withField(..., Consumer<? super FieldBuilder>)`, `MethodBuilder.withCode(Consumer<? super CodeBuilder>)`. Точка входа: `ClassFile.of().build(ClassDesc, Consumer<ClassBuilder>)`.
- Символические типы: `ClassDesc.of("java.lang.String")`, `ClassDesc.ofDescriptor("Ljava/lang/String;")`, `ClassDesc.ofInternalName("java/lang/String")`, `MethodTypeDesc.of(returnDesc, paramDescs...)` / `MethodTypeDesc.ofDescriptor("(ZI)V")`; `ConstantDesc` — sealed, `permits ClassDesc, MethodHandleDesc, MethodTypeDesc, Double, DynamicConstantDesc, Float, Integer, Long, String`. Готовые константы (`CD_String`, `CD_int`, `CD_void`) — в `ConstantDescs`. `ClassModel implements CompoundElement<ClassElement>, AttributedElement` (то есть `Iterable<ClassElement>`), что и позволяет обход `for (ClassElement ce : classModel)`.

**Перенос на генерацию исходного кода.** Идея: sealed-AST, где система типов делает синтаксически невалидный Java невыразимым, тела структурны (не строки), модель immutable с with-ерами, а трансформации — first-class.

1. **Sealed-иерархия узлов.**
```java
public sealed interface JavaFileElement permits TypeDecl, PackageDecl, ImportDecl {}

public sealed interface TypeDecl extends JavaFileElement
        permits ClassDecl, InterfaceDecl, RecordDecl, EnumDecl, AnnotationDecl {}

// то, что может стоять в теле разных контейнеров, различается на уровне типов:
public sealed interface ClassMember permits FieldDecl, MethodDecl, ConstructorDecl, InitBlock, TypeDecl {}
public sealed interface InterfaceMember permits AbstractMethodDecl, DefaultMethodDecl, StaticMethodDecl, ConstantDecl, TypeDecl {}
public sealed interface RecordMember permits CompactConstructorDecl, MethodDecl, StaticFieldDecl, TypeDecl {}
```
Здесь и заключена главная идея «make illegal states unrepresentable» (формулировка Yaron Minsky, Jane Street, цикл «Effective ML»): интерфейс не принимает `FieldDecl` с приватным нестатическим полем, потому что его контекст (`InterfaceMember`) вообще не имеет такого варианта; record-билдер принимает компоненты, а не изменяемые поля; абстрактный метод и метод-с-телом — разные типы (`AbstractMethodDecl` не имеет метода `body(...)`). В Java роль ADT исполняют sealed interfaces + records + pattern matching.

2. **Builder-как-Consumer + Consumer-лямбды (по образцу Class-File API).**
```java
JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Messages"), cb -> cb
    .withModifiers(PUBLIC, FINAL)
    .withField("bundle", ClassDesc.of("java.util.ResourceBundle"), fb -> fb
        .withModifiers(PRIVATE, FINAL))
    .withMethod("greeting", MethodTypeDesc.of(CD_String, CD_String), PUBLIC, mb -> mb
        .withBody(body -> body
            .return_(body.call(body.field("bundle"), "getString", body.literal("greeting"))))));
```
`ClassBuilder` реализует `Consumer<ClassElement>`, поэтому готовые элементы можно «пропускать» через `with(element)`, а специфичные `withField/withMethod` дают удобные типизированные входы. Тело метода строится структурно (`return_`, `call`, `field`, `literal`), а не форматными строками, поэтому невозможен невалидный `$L`-плейсхолдер.

3. **Type-state builders (фантомные типы) для обязательного порядка.** Там, где Java требует последовательности (например, у поля-константы интерфейса инициализатор обязателен), кодируем состояние в параметре типа:
```java
// FieldBuilder<InitRequired> -> FieldBuilder<Ready>; build() доступен только у Ready
ConstantDecl c = ConstantField.named("MAX")     // FieldBuilder<InitRequired>
    .ofType(CD_int)
    .initializedWith(expr.literal(10))            // -> FieldBuilder<Ready>
    .build();                                     // компилируется только теперь
```
Это прямой перенос идеи «Builder как конечный автомат»: невалидные переходы (например, `build()` без инициализатора) не выражаются в системе типов. Ограничение Java: без extension-функций комбинаторная сложность состояний растёт, поэтому type-state применяем точечно (обязательные поля record/enum-константы/throws), а не ко всему API.

4. **Immutable-модель с with-ерами и трансформации.** Каждый узел — record (immutable), правки создают новый узел, разделяя неизменённое поддерево (аналог persistent-структур). Трансформации — first-class, как `ClassTransform`/`MethodTransform` в Class-File API:
```java
JavaFile withGeneratedAnnotation = file.transform((builder, element) -> {
    switch (element) {
        case MethodDecl m when m.isPublic() ->
            builder.with(m.withAnnotation(GENERATED));
        default -> builder.with(element);
    }
});
```
Трансформация как flatMap по элементам + pattern matching в switch (Java 25) устраняет visitor-boilerplate.

5. **Символические ссылки на типы вместо строк.** Переиспользуем `java.lang.constant.ClassDesc`/`MethodTypeDesc` как готовый, стандартный, immutable словарь имён типов (это устраняет коллизии FQN и делает авто-импорт детерминированным: библиотека сама решает, что импортировать, а что писать полным именем). Для generics вводим собственный `TypeArg`/`ParameterizedDesc` поверх `ClassDesc` (в `java.lang.constant` генериков нет, они стёрты в дескрипторах).

**Двойной режим работы.**
- *Внутри annotation processor.* Мост к `javax.lang.model`: `Types.toClassDesc(TypeMirror)`, `Types.toParameterized(DeclaredType)`, `Elements.toTypeDecl(TypeElement)` (конверсия в дескрипторы библиотеки), запись через `JavaFile.writeTo(Filer, originatingElements...)` (обёртка над `createSourceFile`, автоматически прокидывающая originating elements для инкрементальности Gradle). Библиотека декларирует себя isolating-процессору-совместимой (originating elements обязательны).
- *Standalone.* `JavaFile.writeTo(Path outputDir)` для Gradle-задач, генерирующих исходники из схем: это ровно кейс type-safe-messages (генерация из `messages_*.properties`). Здесь дескрипторы задаются напрямую (`ClassDesc.of(...)`), без `javax.lang.model`.

**Примеры «как это выглядит»: JavaPoet/JavaParser vs новый API.**

*Record. JavaPoet (Palantir):*
```java
TypeSpec point = TypeSpec.recordBuilder("Point")
    .addRecordComponent(int.class, "x")
    .addRecordComponent(int.class, "y")
    .build();
// компонент передаётся как ParameterSpec/строка типа; sealed/permits — отдельными вызовами
```
*Новый API:*
```java
JavaFile.record(ClassDesc.of("geom", "Point"), rb -> rb
    .withComponent("x", CD_int)
    .withComponent("y", CD_int));
// RecordBuilder не имеет withField(...): поля record недоступны как варианты типа
```

*Sealed interface. JavaParser* потребовал бы вручную выставлять модификатор и `permits` строками без проверки, что перечисленные типы существуют. *Новый API:*
```java
JavaFile.sealedInterface(ClassDesc.of("ast", "Node"), ib -> ib
    .permits(ClassDesc.of("ast", "Leaf"), ClassDesc.of("ast", "Branch"))
    .withAbstractMethod("kind", MethodTypeDesc.of(CD_String)));
// InterfaceBuilder принимает только abstract/default/static-формы методов
```

*Класс с методом. JavaParser* (мутабельно, stringly-typed):
```java
CompilationUnit cu = new CompilationUnit();
ClassOrInterfaceDeclaration c = cu.addClass("Greeter");
c.addMethod("greet", Modifier.Keyword.PUBLIC)
 .setBody(new BlockStmt().addStatement("return \"hi\";")); // тело — строка
```
*Новый API* (immutable, структурное тело) — см. пример из пункта 2 выше.

**Прочая арт-прайор для дизайна.**
- *KotlinPoet* — доказательство, что builder-эмиттер удобен, но сам по себе не даёт полной type-safety (ловит часть невалидных комбинаций рантайм-проверками, но не все). Наш ответ: sealed-контексты + type-state там, где KotlinPoet полагается на рантайм-проверки.
- *Roslyn SyntaxFactory (.NET)* — immutable red-green trees: «green» дерево immutable, без родительских ссылок, строится снизу вверх; при правке пересобирается лишь затронутая часть (Eric Lippert: «typically about O(log n) of the total parse nodes»), а официальные roslyn/docs указывают, что новое green-дерево «reuses roughly 99% of the green nodes» (для типичных правок переиспользование приближается к 99,99%); «red» — фасад сверху, вычисляющий позиции/родителей лениво. Для генерации (в отличие от IDE-редактирования) нам не нужен полный full-fidelity red-green, но принцип структурного шаринга immutable-узлов при with-ерах мы берём.
- *scala.meta* и общий тезис «make illegal states unrepresentable» (Yaron Minsky): алгебраические типы данных / sealed-иерархии + immutability — основной инструмент; в Java их роль исполняют sealed interfaces + records + pattern matching (Java 25 baseline).

## Recommendations

**Что должно войти в MVP (этап 1).**
1. Sealed-модель верхнего уровня: `JavaFile`, `ClassDecl`, `InterfaceDecl`, `RecordDecl`, `EnumDecl`; члены через sealed-контексты (`ClassMember`/`InterfaceMember`/`RecordMember`), различающие abstract/default/static-методы типами.
2. Builder-как-Consumer с Consumer-лямбдами (`withMethod`, `withField`, `withBody`) по образцу `ClassBuilder`/`MethodBuilder`/`CodeBuilder`.
3. Символические типы: обёртка над `ClassDesc`/`MethodTypeDesc` + собственный слой генериков (`ParameterizedDesc`, `TypeArg`, wildcards). Детерминированный менеджер импортов и разрешение коллизий FQN.
4. Структурные тела для наиболее частых конструкций type-safe-messages: `return`, вызов метода, доступ к полю, строковый литерал (с корректным экранированием, забрав роль `JavaStrings`), text block. Для редких конструкций — экранированный «escape hatch» `rawStatement(String)` (осознанный компромисс, помеченный как непроверяемый).
5. Два выхода: `writeTo(Filer, Element...)` и `writeTo(Path)`; идемпотентный, детерминированный вывод (стабильный порядок, фиксированное форматирование).
6. Нулевые тяжёлые зависимости у ядра (только JDK); ничего, что потребовало бы шейдинга на apt-classpath.

**Этап 2 (после стабилизации MVP).** Трансформации (`ClassTransform`/`MethodTransform`) и pattern-matching-обход; type-state builders для обязательных инвариантов (инициализатор константы, компактный конструктор record, throws); мост `javax.lang.model` (`TypeMirror → ClassDesc`); генерация sealed-иерархий и enum с телами.

**Этап 3.** Полное покрытие тел (control flow как структурные узлы, а не строки), pattern matching в switch как узлы, поддержка новейшего синтаксиса по мере выхода JDK.

**Пороговые критерии, меняющие план.**
- Если объём кейсов type-safe-messages мал и стабилен: возможно, MVP избыточен, и правильнее остаться на строках + `JavaStrings`, вынеся лишь маленький типизированный слой для сигнатур.
- Если появляется потребность трансформировать существующий код (а не только генерировать): тогда и только тогда стоит смотреть на JavaParser/JDT, а не на новую библиотеку.
- Если критична скорость раундов javac и размер apt-classpath: это аргумент против JavaParser и за лёгкое JDK-only ядро.

**Практический промежуточный шаг для самого проекта.** До готовности новой библиотеки заменить ручную конкатенацию в `*Writer`-классах на Palantir JavaPoet (`com.palantir.javapoet`) ради авто-импортов и records/sealed, оставив `JavaStrings` только там, где JavaPoet недостаточен. Это снизит хрупкость без стратегической ставки.

## Caveats
- **Полная type-safety vs эргономика — фундаментальный компромисс.** Чем строже sealed-контексты и type-state, тем больше типов и тем многословнее API; в Java (без extension-функций) комбинаторный рост состояний реален. Практичное решение: строгие инварианты только для частых/опасных случаев, `rawStatement`-escape-hatch для остального.
- **Class-File API — про байткод, не про исходники.** Мы заимствуем принципы (immutable elements, builder-Consumer, transforms, символические дескрипторы), но не сам API; `java.lang.constant` не знает генериков (они стёрты), поэтому слой типов придётся достраивать.
- **Риск «ещё одной заброшенной библиотеки».** История JavaPoet (архив), CodeModel (legacy) показывает, что генераторы кода склонны к забросу. Митигирующие факторы: минимальные зависимости, узкий MVP, ориентация на стандартные JDK-абстракции.
- **Baseline Java 25** отсекает пользователей на старых JDK; для библиотеки-эмиттера это приемлемо (сгенерированный код может целиться и в более старые версии, ограничение касается только среды генерации), но должно быть заявлено явно.
- **Датировка/версии.** Class-File API финализирован в JDK 24 (JEP 484); превью были JEP 457 (JDK 22) и JEP 466 (JDK 23). Часть сигнатур в источниках приведена для JDK 22/23 (эпоха preview), но структурно (sealed/permits/extends Consumer) они совпадают с финальной версией. Оговорка Oracle: списки `permits` в JavaDoc помечены «(not exhaustive)».
- **Форматирование как отдельная задача.** Детерминированный вывод не равен «красивому» выводу; при необходимости стиля стоит опираться на внешний форматтер (например, google-java-format или palantir-java-format), а не встраивать полноценный pretty-printer в ядро.