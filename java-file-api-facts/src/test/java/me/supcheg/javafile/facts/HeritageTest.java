package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.Heritage.Argument;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.ClassMethod;
import me.supcheg.javafile.facts.Heritage.Declaration;
import me.supcheg.javafile.facts.Heritage.Declarer;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Generic;
import me.supcheg.javafile.facts.Heritage.Inherited;
import me.supcheg.javafile.facts.Heritage.InterfaceMethod;
import me.supcheg.javafile.facts.Heritage.Named;
import me.supcheg.javafile.facts.Heritage.Plain;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Type;
import me.supcheg.javafile.facts.Heritage.Variable;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The heritage of a type: what it holds, which shapes tell one, and which
/// one a target classpath gives.
class HeritageTest {
    private static final String FINGERPRINT = "0123456789abcdef".repeat(4);
    private static final ClassDesc BASE = ClassDesc.of("p", "Base");
    private static final ClassDesc API = ClassDesc.of("p", "Api");

    private static Heritage.Method<Declaration> method(String name, ClassMethod what) {
        return new Heritage.Method<>(
                Signature.of(name),
                List.of(declaration(new Declarer.OfClass(BASE, Visibility.PUBLIC, what))),
                Set.of(List.of()),
                List.of());
    }

    /// A method without parameters, result or exceptions.
    private static Declaration declaration(Declarer by) {
        return new Declaration(by, Arity.FIXED, List.of(), List.of(), Result.nothing(), List.of());
    }

    private static Heritage.Method<Declaration> group(Declarer... declarers) {
        return new Heritage.Method<>(
                Signature.of("m"),
                Stream.of(declarers).map(HeritageTest::declaration).toList(),
                Set.of(List.of()),
                List.of());
    }

    private static final Heritage.Told GENERATED = new Heritage.Told(
            List.of(method("run", ClassMethod.ABSTRACT)),
            List.of(new Heritage.Constructor(
                    Visibility.PROTECTED, Signature.of("Base"), Arity.FIXED, List.of(), List.of(), List.of())),
            List.of());
    private static final Heritage.Told OF_TARGET = new Heritage.Told(
            List.of(method("run", ClassMethod.ABSTRACT), method("stop", ClassMethod.ABSTRACT)), List.of(), List.of());

    private static TypeShape<DeclaredKind.AbstractClass> withHeritage(String name) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", name + "_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.ABSTRACT_CLASS,
                ClassDesc.of("p", name),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                false,
                () -> GENERATED);
    }

    private static TypeShape<DeclaredKind.AbstractClass> withoutHeritage(String name) {
        return UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", name + "_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.ABSTRACT_CLASS,
                ClassDesc.of("p", name),
                List.of(),
                List.of(ConstantDescs.CD_Object),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false);
    }

    @Test
    void aMethodOfAHeritageHoldsEveryDeclarationOfItsSignature() {
        Heritage.Type strings = new Type.Fixed(Types.array(Types.STRING));
        Declaration ofClass = new Declaration(
                new Declarer.OfClass(BASE, Visibility.PROTECTED, ClassMethod.ABSTRACT),
                Arity.VARIABLE,
                List.of(),
                List.of(strings),
                new Result.Of<>(new Type.Fixed(Types.STRING)),
                List.of());
        Declaration ofInterface = new Declaration(
                new Declarer.OfInterface(API, InterfaceMethod.DEFAULT),
                Arity.FIXED,
                List.of(),
                List.of(strings),
                new Result.Of<>(new Type.Fixed(Types.OBJECT)),
                List.of(new Type.Fixed(Types.of(Exception.class))));
        Heritage.Method<Declaration> format = new Heritage.Method<>(
                Signature.of("format", Param.fixed(ConstantDescs.CD_String.arrayType())),
                List.of(ofClass, ofInterface),
                Set.of(List.of(ConstantDescs.CD_String.arrayType()), List.of(ConstantDescs.CD_Object.arrayType())),
                List.of(ClassDesc.of("p", "Root")));

        assertThat(format.name()).isEqualTo("format");
        assertThat(format.declarations()).containsExactly(ofClass, ofInterface);
        assertThat(format.erasures()).hasSize(2);
        assertThat(ofClass.by().visibility()).isEqualTo(Visibility.PROTECTED);
        assertThat(ofInterface.by().visibility()).isEqualTo(Visibility.PUBLIC);
        assertThat(ofInterface.by().dispatch()).isEqualTo(Dispatch.DEFAULT);
        // the abstract method of the class stands, whatever the interface gives (JLS 8.4.8.4)
        assertThat(format.standing()).isEqualTo(Dispatch.ABSTRACT);
    }

    @Test
    void theMethodOfAClassStandsWhateverAnInterfaceDeclares() {
        Declarer anAbstract = new Declarer.OfInterface(API, InterfaceMethod.ABSTRACT);
        Declarer aDefault = new Declarer.OfInterface(ClassDesc.of("p", "Other"), InterfaceMethod.DEFAULT);

        assertThat(group(new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.ABSTRACT), aDefault)
                        .standing())
                .isEqualTo(Dispatch.ABSTRACT);
        assertThat(group(aDefault, new Declarer.OfClass(BASE, Visibility.PROTECTED, ClassMethod.ABSTRACT))
                        .standing())
                .isEqualTo(Dispatch.ABSTRACT);
        assertThat(group(new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.FINAL))
                        .standing())
                .isEqualTo(Dispatch.FINAL);
        assertThat(group(new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.STATIC))
                        .standing())
                .isEqualTo(Dispatch.STATIC);
        assertThat(group(new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.CONCRETE))
                        .standing())
                .isEqualTo(Dispatch.CONCRETE);
        assertThat(group(aDefault).standing()).isEqualTo(Dispatch.DEFAULT);
        assertThat(group(anAbstract, aDefault).standing()).isEqualTo(Dispatch.ABSTRACT);
        assertThat(group(anAbstract).standing()).isEqualTo(Dispatch.ABSTRACT);
    }

    @Test
    void aMethodWithPackageAccessStandsOnlyWhereNothingElseDoes() {
        Declarer hiddenAbstract = new Declarer.OfClass(BASE, Visibility.PACKAGE, ClassMethod.ABSTRACT);
        Declarer hiddenBody = new Declarer.OfClass(BASE, Visibility.PACKAGE, ClassMethod.CONCRETE);
        Declarer nearer = new Declarer.OfClass(ClassDesc.of("q", "Sub"), Visibility.PUBLIC, ClassMethod.CONCRETE);

        // javac asks a class of any package for an abstract method, though one of another package cannot give it
        assertThat(group(hiddenAbstract).standing()).isEqualTo(Dispatch.ABSTRACT);
        assertThat(group(nearer, hiddenAbstract).standing()).isEqualTo(Dispatch.ABSTRACT);
        // a class of another package does not inherit the body: the method of the interface is left abstract
        assertThat(group(new Declarer.OfInterface(API, InterfaceMethod.ABSTRACT), hiddenBody)
                        .standing())
                .isEqualTo(Dispatch.ABSTRACT);
        assertThat(group(nearer, new Declarer.OfClass(BASE, Visibility.PACKAGE, ClassMethod.FINAL))
                        .standing())
                .isEqualTo(Dispatch.CONCRETE);
        assertThat(group(hiddenBody).standing()).isEqualTo(Dispatch.CONCRETE);
    }

    @Test
    void aHeritageAppliedToTypeArgumentsIsInTheirTerms() {
        ClassDesc list = ClassDesc.of("java.util.List");
        Heritage.Type t = new Type.OfType(0);
        Heritage.Method<Declaration> put = new Heritage.Method<>(
                Signature.of("put", Param.var(0, 1), Param.fixed(list)),
                List.of(new Declaration(
                        new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.ABSTRACT),
                        Arity.FIXED,
                        List.of(),
                        List.of(Type.array(t), Type.parameterized(list, List.of(new Argument.Extends(t)))),
                        new Result.Of<>(t),
                        List.of(t))),
                Set.of(List.of(ConstantDescs.CD_Object.arrayType(), list)),
                List.of());
        Heritage.Told generic = new Heritage.Told(
                List.of(put), List.of(), List.of(new Named.Field("count", BASE, Visibility.PROTECTED)));

        Heritage.Applied ofStrings = generic.applied(List.of(Types.STRING));

        Heritage.Method<Inherited> applied = ofStrings.methods().getFirst();
        assertThat(applied.declarations())
                .containsExactly(new Plain(
                        new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.ABSTRACT),
                        Arity.FIXED,
                        List.of(Types.array(Types.STRING), Types.parameterized(list, Types.extendsBound(Types.STRING))),
                        new Result.Of<>(Types.STRING),
                        List.of(Types.STRING)));
        assertThat(applied.signature()).isEqualTo(put.signature());
        assertThat(applied.erasures()).isEqualTo(put.erasures());
        assertThat(ofStrings.named()).isEqualTo(generic.named());
        assertThatIllegalArgumentException()
                .isThrownBy(() -> generic.applied(List.of()))
                .withMessage("a member mentions the type parameter #0 of the type, got 0 type arguments");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> generic.applied(List.of(Types.INT)))
                .withMessage("an exception type is no primitive, got int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> generic.applied(List.of(Types.array(Types.STRING))))
                .withMessage("an exception type is no array, got java.lang.String[]");
    }

    @Test
    void aTypeParameterOfAMethodIsNeverTheOneOfTheTypeWhateverTheyAreCalled() {
        // class GBase<E> { <T extends E> void g(E e, T t) }, inherited by a class Box<T> extends GBase<T>
        Heritage.Method<Declaration> g = new Heritage.Method<>(
                Signature.of("g", Param.var(0), Param.fixed(ConstantDescs.CD_Object)),
                List.of(new Declaration(
                        new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.CONCRETE),
                        Arity.FIXED,
                        List.of(new Variable(List.of(new Type.OfType(0)))),
                        List.of(new Type.OfType(0), new Type.OfMember(0)),
                        Result.nothing(),
                        List.of())),
                Set.of(List.of(ConstantDescs.CD_Object, ConstantDescs.CD_Object)),
                List.of());
        TypeRef tOfTheClass = Types.typeVar("T");

        Inherited applied = new Heritage.Told(List.of(g), List.of(), List.of())
                .applied(List.of(tOfTheClass))
                .methods()
                .getFirst()
                .declarations()
                .getFirst();

        // the bound is applied too; the T of the class and the type parameter of the method stay apart
        assertThat(applied)
                .isEqualTo(new Generic(new Declaration(
                        new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.CONCRETE),
                        Arity.FIXED,
                        List.of(new Variable(List.of(new Type.Fixed(tOfTheClass)))),
                        List.of(new Type.Fixed(tOfTheClass), new Type.OfMember(0)),
                        Result.nothing(),
                        List.of())));
    }

    @Test
    void aTypeOfAHeritageIsInNormalForm() {
        ClassDesc map = ClassDesc.of("java.util.Map");
        assertThat(Type.array(new Type.Fixed(Types.INT))).isEqualTo(new Type.Fixed(Types.array(Types.INT)));
        assertThat(Type.array(new Type.OfType(1))).isEqualTo(new Type.Array(new Type.OfType(1)));
        assertThat(Type.parameterized(
                        map, List.of(new Argument.Exact(new Type.Fixed(Types.STRING)), new Argument.Unbounded())))
                .isEqualTo(new Type.Fixed(Types.parameterized(map, Types.exact(Types.STRING), Types.unbounded())));
        assertThat(Type.parameterized(map, List.of(new Argument.Super(new Type.OfMember(0)), new Argument.Unbounded()))
                        .applied(List.of()))
                .isEqualTo(new Type.Parameterized(
                        map, List.of(new Argument.Super(new Type.OfMember(0)), new Argument.Unbounded())));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Type.Array(new Type.Fixed(Types.INT)))
                .withMessageStartingWith("an array of a fixed type is fixed");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Type.Parameterized(map, List.of(new Argument.Unbounded())))
                .withMessageStartingWith("a parameterized type of fixed arguments is fixed");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Type.OfType(-1))
                .withMessage("a type parameter is at a position from 0, got -1");
    }

    @Test
    void aHeritageTellsAMethodOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Told(
                        List.of(method("run", ClassMethod.ABSTRACT), method("run", ClassMethod.FINAL)),
                        List.of(),
                        List.of()))
                .withMessage("a heritage tells a method once, got [run(), run()]");
    }

    @Test
    void aMemberOfAHeritageIsConsistent() {
        Signature unary = Signature.of("m", Param.fixed(ConstantDescs.CD_int));
        Declarer base = new Declarer.OfClass(BASE, Visibility.PUBLIC, ClassMethod.FINAL);
        Declaration ofInt = new Declaration(
                base, Arity.FIXED, List.of(), List.of(new Type.Fixed(Types.INT)), Result.nothing(), List.of());
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Declarer.OfClass(ConstantDescs.CD_int, Visibility.PUBLIC, ClassMethod.FINAL))
                .withMessage("a member is declared by a class or interface, got int");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Method<>(unary, List.<Declaration>of(), Set.of(List.of()), List.of()))
                .withMessage("method m(int) is declared by a type");
        assertThatIllegalArgumentException()
                .isThrownBy(
                        () -> new Heritage.Method<>(unary, List.of(declaration(base)), Set.of(List.of()), List.of()))
                .withMessage("method m(int) of p.Base has 0 parameters");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Method<>(unary, List.of(ofInt), Set.of(), List.of()))
                .withMessage("method m(int) erases to its 1 parameters, got []");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Declaration(
                        base,
                        Arity.VARIABLE,
                        List.of(),
                        List.of(new Type.Fixed(Types.INT)),
                        Result.nothing(),
                        List.of()))
                .withMessage("a method of p.Base is of variable arity, so its last parameter is an array");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Declaration(
                        base, Arity.FIXED, List.of(), List.of(new Type.OfMember(0)), Result.nothing(), List.of()))
                .withMessage("a method of p.Base mentions its type parameter ^0, and has 0");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Constructor(
                        Visibility.PUBLIC, Signature.of("Base"), Arity.VARIABLE, List.of(), List.of(), List.of()))
                .withMessage("constructor Base() is of variable arity, so its last parameter is an array");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Heritage.Constructor(
                        Visibility.PUBLIC,
                        Signature.of("Base"),
                        Arity.FIXED,
                        List.of(),
                        List.of(new Type.Fixed(Types.INT)),
                        List.of()))
                .withMessage("constructor Base() has 1 parameters");
    }

    @Test
    void onlyAShapeMadeWithAHeritageTellsOne() {
        assertThat(withHeritage("Told").heritage()).isSameAs(GENERATED);
        assertThat(withoutHeritage("Untold").heritage()).isSameAs(Heritage.UNTOLD);
        assertThat(PrimitiveToken.INT.boxed().shape().heritage()).isSameAs(Heritage.UNTOLD);
    }

    @Test
    void theHeritageOfAShapeIsLoadedWhenItIsAskedFor() {
        java.util.concurrent.atomic.AtomicInteger loads = new java.util.concurrent.atomic.AtomicInteger();
        TypeShape<DeclaredKind.Interface> shape = UnsafeFacts.shape(
                new ShapeOrigin.Metamodel(ClassDesc.of("gen.facts.p", "Api_"), FINGERPRINT, () -> "canonical"),
                DeclaredKind.INTERFACE,
                ClassDesc.of("p", "Api"),
                List.of(),
                List.of(),
                List.of(),
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                false,
                () -> {
                    loads.incrementAndGet();
                    return GENERATED;
                });

        assertThat(loads).hasValue(0);
        assertThat(shape.heritage()).isSameAs(GENERATED);
        assertThat(loads).hasValue(1);
    }

    @Test
    void aTargetClasspathGivesTheHeritageOfTheTypeItHas() {
        TypeShape<DeclaredKind.AbstractClass> same = withHeritage("Same");
        TypeShape<DeclaredKind.AbstractClass> changed = withHeritage("Changed");
        TypeShape<DeclaredKind.AbstractClass> untold = withoutHeritage("Untold");
        Map<TypeShape<?>, TargetType> answers = Map.of(
                same, TargetType.UNCHANGED,
                changed, new TargetType.Changed(MethodTableTemplate.EMPTY, OF_TARGET),
                untold, new TargetType.Changed(MethodTableTemplate.EMPTY, OF_TARGET));
        TargetClasspath target = UnsafeFacts.targetClasspath((shape, _) -> answers.get(shape));

        assertThat(target.heritage(same)).isSameAs(GENERATED);
        // another version of the type, which every fact still holds of: what a subclass inherits is of that one
        assertThat(target.heritage(changed)).isSameAs(OF_TARGET);
        // a shape that tells none has none on the target either
        assertThat(target.heritage(untold)).isSameAs(Heritage.UNTOLD);
    }

    @Test
    void aClasspathThatChecksNothingGivesTheHeritageOfTheMetamodel() {
        TargetClasspath unverified = UnsafeFacts.unverifiedClasspath();

        assertThat(unverified.heritage(withHeritage("Told"))).isSameAs(GENERATED);
        assertThat(unverified.heritage(withoutHeritage("Untold"))).isSameAs(Heritage.UNTOLD);
    }
}
