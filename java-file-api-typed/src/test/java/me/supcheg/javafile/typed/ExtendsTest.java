package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Mid;
import me.supcheg.javafile.typed.fixtures.Shape;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.Thread_;
import me.supcheg.javafile.typed.testfacts.java.lang.Throwable_;
import me.supcheg.javafile.typed.testfacts.java.util.AbstractCollection_;
import me.supcheg.javafile.typed.testfacts.java.util.AbstractList_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Shape_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.AbstractList;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// A class that extends a class (§8e): the proof it extends by, the
/// constructors that begin with `super(...)`, the witness of the superclass,
/// `super.m()`, what is `protected`, and the class that is its own
/// exception. The classes that are accepted are compiled with `-Xlint:all`
/// and run; what is rejected is rejected where it is said, and javac is asked
/// about the Java the declaration would have been.
class ExtendsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Nested
    class Accepted {

        @Test
        void aClassExtendsAnAbstractGenericClassAndImplementsAnInterfaceOfItself() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var list = new AbstractList_<>(String_.TOKEN);
                    var coll = new AbstractCollection_<>(String_.TOKEN);
                    var cmp = new Comparable_<>(h.self());
                    var get = h.overriding(list.get_int);
                    var size = h.overriding(coll.size);
                    var compareTo = h.overriding(cmp.compareTo_T);

                    var sh = h.extends_(list.extend(h.self()).get_int(get).size(size));
                    Upcast<Self, Comparable<Self>> asComparable =
                            sh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
                    var cb = sh.body(ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
                    Upcast<Self, AbstractList<String>> asList = cb.asSuper();

                    cb.define(get.method(), (b, self, i) -> b.return_(literal("item")));
                    cb.define(size.method(), (b, self) -> b.return_(literal(2)));
                    cb.define(
                            compareTo.method(),
                            (b, self, other) ->
                                    b.return_(subInt(call(self, size.method()), call(other, size.method()))));
                    // what the class inherits is called through the witness, and as super.m()
                    cb.override(
                            coll.toString,
                            (b, self) -> b.return_(concat(literal("names"), cb.superCall(self, coll.toString))));
                    cb.method(
                            "empty",
                            PrimitiveToken.BOOLEAN,
                            (b, self) -> b.return_(call(asList.apply(self), coll.isEmpty)));
                    cb.method(
                            "joined",
                            String_.TOKEN,
                            (b, self) -> b.forEach(String_.TOKEN, asList.apply(self), (body, item, loop) -> {})
                                    .return_(call(asComparable.apply(self), Object_.toString)));
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends AbstractList<String> implements Comparable<Probe> {")
                    .contains("public Probe() {\n        super();\n    }")
                    .contains("@Override\n    public String get(int v0) {")
                    .contains("@Override\n    public int size() {")
                    .contains("return \"names\" + super.toString();")
                    .contains("return this.isEmpty();")
                    .contains("for (String v0 : (AbstractList<String>) this) {");
            Object probe = compiled.generated().getConstructor().newInstance();
            assertThat(probe).isInstanceOf(AbstractList.class).isInstanceOf(Comparable.class);
            assertThat(List.copyOf((List<?>) probe)).isEqualTo(List.of("item", "item"));
            assertThat(probe).hasToString("names[item, item]");
            assertThat(compiled.invoke("empty")).isEqualTo(false);
            assertThat(compiled.invoke("joined")).isEqualTo("names[item, item]");
        }

        @Test
        void aClassThatExtendsAnExceptionIsThrownCaughtAndDeclaredByItsWitness() throws Throwable {
            CompiledClasses compiled = CompiledClasses.ofSerializable(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Exception_.extend(h.self()));
                    var cb = sh.body(
                            ctor -> ctor.super_(Exception_.new_String, literal("failed"), (b, self) -> b.end()));
                    Upcast<Self, Exception> asException = cb.asSuper();
                    var ofMessage = cb.constructor(
                            String_.TOKEN,
                            (ctor, message) -> ctor.super_(Exception_.new_String, message, (b, self) -> b.end()));

                    // throws Probe: the clause, the throw and the catch are of the class itself
                    var raise = cb.throwing(asException, cb.self())
                            .staticMethod(
                                    "raise",
                                    String_.TOKEN,
                                    String_.TOKEN,
                                    (b, message) -> b.throw_(asException, new_(ofMessage, message)));
                    cb.staticMethod(
                            "caught",
                            String_.TOKEN,
                            b -> b.tryTerminated(
                                    handlers -> handlers.catch_(
                                            asException,
                                            cb.self(),
                                            (c, e) -> c.return_(call(asException.apply(e), Throwable_.getMessage))),
                                    t -> t.return_(staticCall(raise, literal("mine")))));
                    // seen as an Exception the thrown value is still the class to javac, and to the checks
                    cb.throwing(asException, cb.self())
                            .voidMethod("rethrow", (b, self) -> b.throw_(asException.apply(self)));
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends Exception {")
                    .contains("super(\"failed\");")
                    .contains("public static String raise(String v0) throws Probe {")
                    .contains("catch (Probe v0) {")
                    .contains("public void rethrow() throws Probe {\n        throw this;");
            assertThat(compiled.invoke("caught")).isEqualTo("mine");
            assertThatThrownBy(() -> compiled.invoke("rethrow"))
                    .isInstanceOf(Exception.class)
                    .hasMessage("failed")
                    .isInstanceOf(compiled.generated());
        }

        @Test
        void aMethodOfAnInterfaceIsHadFromTheSuperclassByInheritance() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Thread_.extend(h.self()));
                    Upcast<Self, Runnable> asRunnable =
                            sh.implements_(Runnable_.implement(h.self()).run(sh.inherited(Thread_.run)));
                    var cb = sh.body(ctor -> ctor.super_(Thread_.new_, (b, self) -> b.end()));
                    cb.voidMethod(
                            "ranAsRunnable",
                            (b, self) -> b.exec(voidCall(asRunnable.apply(self), Runnable_.run))
                                    .end());
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends Thread implements Runnable {")
                    .doesNotContain("public void run()");
            Object probe = compiled.generated().getConstructor().newInstance();
            assertThat(probe).isInstanceOf(Thread.class).isInstanceOf(Runnable.class);
            compiled.invoke("ranAsRunnable");
        }

        @Test
        void aProtectedHookIsOverriddenCalledAsSuperAndOnTheClassItself() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Base_.extend(h.self()));
                    var cb = sh.body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    cb.constructor(
                            String_.TOKEN,
                            (ctor, name) ->
                                    ctor.super_(Base_.super_String, concat(name, literal("!")), (b, self) -> b.end()));
                    Upcast<Self, Base> asBase = cb.asSuper();
                    var hook = cb.protected_(Base_.hook_String);
                    var hits = cb.protected_(Base_.hits);
                    var motto = cb.protected_(Base_.motto);

                    var own = cb.override(
                            hook, (b, self, s) -> b.return_(concat(cb.superCall(self, hook, s), literal("+"))));
                    // this.hook(...) by the fact of the superclass, on the class itself
                    cb.method(
                            "viaBase",
                            String_.TOKEN,
                            (b, self) -> b.return_(call(asBase.apply(self), hook, literal("x"))));
                    cb.method("viaOwn", String_.TOKEN, (b, self) -> b.return_(call(self, own, literal("y"))));
                    cb.method(
                            "counted",
                            PrimitiveToken.INT,
                            (b, self) -> b.exec(assignField(
                                            asBase.apply(self),
                                            hits,
                                            addInt(field(asBase.apply(self), hits), literal(5))))
                                    .return_(field(asBase.apply(self), hits)));
                    cb.method("saying", String_.TOKEN, (b, self) -> b.return_(staticCall(motto)));
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends Base {")
                    .contains("public Probe(String v0) {\n        super(v0 + \"!\");\n    }")
                    .contains("@Override\n    public String hook(String v0) {\n        return super.hook(v0) + \"+\";")
                    .contains("return this.hook(\"x\");")
                    .contains("super.hits = super.hits + 5;")
                    .contains("return Base.motto();");
            assertThat(compiled.invoke("viaBase")).isEqualTo("base:x+");
            assertThat(compiled.invoke("viaOwn")).isEqualTo("base:y+");
            assertThat(compiled.invoke("counted")).isEqualTo(5);
            assertThat(compiled.invoke("saying")).isEqualTo("motto");
            Object named = compiled.generated().getConstructor(String.class).newInstance("n");
            assertThat(((Base) named).greet()).isEqualTo("base:n!+");
        }

        @Test
        void anAbstractClassIsExtendedByItsProtectedAbstractMethodAndAConstructorThatThrows() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var area = h.overriding(Shape_.area);
                    var describe = h.overriding(h.protected_(Shape_.describe_String));
                    var sh = h.extends_(Shape_.extend(h.self()).area(area).describe_String(describe));
                    var cb = sh.throwing(IOException_.TOKEN)
                            .body(
                                    String_.TOKEN,
                                    (ctor, label) -> ctor.super_(Shape_.super_String, label, (b, self) -> b.end()));
                    cb.define(area.method(), (b, self) -> b.return_(literal(4)));
                    cb.define(describe.method(), (b, self, prefix) -> b.return_(concat(prefix, literal("="))));
                    var check = cb.protected_(Shape_.check);
                    cb.throwing(IOException_.TOKEN)
                            .voidMethod(
                                    "checked",
                                    (b, self) ->
                                            b.exec(cb.superCall(self, check)).end());
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends Shape {")
                    .contains("public Probe(String v0) throws IOException {\n        super(v0);\n    }")
                    .contains("@Override\n    public String describe(String v0) {")
                    .contains("super.check();");
            Object probe = compiled.generated().getConstructor(String.class).newInstance("sq");
            assertThat(((Shape) probe).render()).isEqualTo("sq=4");
            assertThatThrownBy(() ->
                            compiled.generated().getConstructor(String.class).newInstance(""))
                    .hasCauseInstanceOf(IOException.class);
        }

        @Test
        void aClassThatExtendsObjectOverridesCloneAndCallsTheOneOfObject() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().body();
                    var clone = cb.protected_(Object_.clone);
                    cb.throwing(me.supcheg.javafile.typed.testfacts.java.lang.CloneNotSupportedException_.TOKEN)
                            .override(clone, (b, self) -> b.return_(cb.superCall(self, clone)));
                }
            }));

            assertThat(compiled.source())
                    .contains("@Override\n    public Object clone() throws CloneNotSupportedException {\n"
                            + "        return super.clone();");
            assertThatThrownBy(() -> compiled.invoke("clone")).isInstanceOf(CloneNotSupportedException.class);
        }

        @Test
        void aSubclassOfASubclassReachesTheMembersOfEveryClassAboveIt() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Mid_.extend(h.self()));
                    Upcast<Self, Runnable> asRunnable =
                            sh.implements_(Runnable_.implement(h.self()).run(sh.inherited(Mid_.run)));
                    var cb = sh.body(ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                    Upcast<Self, Mid> asMid = cb.asSuper();
                    var hook = cb.protected_(Mid_.hook_String);
                    cb.method(
                            "hooked",
                            String_.TOKEN,
                            (b, self) -> b.return_(cb.superCall(self, hook, call(asMid.apply(self), Base_.greet))));
                    cb.method(
                            "ran",
                            PrimitiveToken.INT,
                            (b, self) -> b.exec(voidCall(asRunnable.apply(self), Runnable_.run))
                                    .return_(field(asMid.apply(self), Mid_.runs)));
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends Mid implements Runnable {")
                    .contains("return super.hook(this.greet());");
            assertThat(compiled.invoke("hooked")).isEqualTo("mid:mid:base");
            assertThat(compiled.invoke("ran")).isEqualTo(1);
        }
    }

    @Nested
    class TheHeader {

        @Test
        void aClassExtendsAClassAndImplementsAnInterface() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var run = h.overriding(Runnable_.run);
                            h.extends_(Runnable_.implement(h.self()).run(run));
                        }
                    }))
                    .withMessageContaining("cannot extend java.lang.Runnable: it is an interface");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extendsObject().implements_(Base_.extend(h.self()));
                        }
                    }))
                    .withMessageContaining("it is a class, which a class extends");
        }

        @Test
        void aClassHasOneSuperclassAndItsMembersFollowAConstructor() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extends_(Base_.extend(h.self()));
                            h.extends_(Mid_.extend(h.self()));
                        }
                    }))
                    .withMessageContaining("has its superclass already");
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extends_(Base_.extend(h.self()));
                        }
                    }))
                    .withMessageContaining("its header never came to the members");
        }

        @Test
        void theSuperclassOfTheTokenIsKnownOnceTheHeaderNamesIt() {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    assertThatIllegalStateException()
                            .isThrownBy(() -> h.self().superclasses())
                            .withMessageContaining("is known only once its header names it");
                    var sh = h.extends_(Exception_.extend(h.self()));
                    assertThat(h.self().superclasses())
                            .containsExactly(
                                    ClassDesc.of("java.lang.Exception"),
                                    ClassDesc.of("java.lang.Throwable"),
                                    ClassDesc.of("java.lang.Object"));
                    assertThat(h.self().isCheckedException()).isTrue();
                    sh.body(ctor -> ctor.super_(Exception_.new_, (b, self) -> b.end()));
                }
            });
        }

        @Test
        void anAbstractMethodOfTheSuperclassIsOverriddenNotInherited() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var describe = h.overriding(h.protected_(Shape_.describe_String));
                            h.extends_(Shape_.extend(h.self())
                                    .area(UnsafeFacts.inherited0(
                                            new me.supcheg.javafile.facts.Implemented.ByInheritance(Shape_.area)))
                                    .describe_String(describe));
                        }
                    }))
                    .withMessageContaining("a step takes what inherited(fact) of the header of the class gave");
        }

        @Test
        void aProtectedFactTakenOutInTheHeaderIsOfASuperclass() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.protected_(Shape_.describe_String);
                            h.extends_(Base_.extend(h.self()));
                        }
                    }))
                    .withMessageContaining("is no subclass of me.supcheg.javafile.typed.fixtures.Shape");
        }
    }

    @Nested
    class Inheriting {

        @Test
        void aFinalMethodOfTheSuperclassIsNotOverridden() {
            classAsJavac(
                    IllegalArgumentException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            cb.override(Base_.sealed, (b, self) -> b.return_(literal("x")));
                        }
                    }),
                    "the method is final (JLS 8.4.3.3)",
                    "extends Base",
                    "@Override public String sealed() { return \"x\"; }",
                    "overridden method is final");
        }

        @Test
        void aMethodDeclaredByTheNameOfOneOfTheSuperclassIsRejected() {
            classUnlikeJavac(
                    IllegalArgumentException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            cb.method("greet", String_.TOKEN, (b, self) -> b.return_(literal("x")));
                        }
                    }),
                    "a method declared by its name overrides nothing",
                    "extends Base",
                    "public String greet() { return \"x\"; }");
            classAsJavac(
                    IllegalArgumentException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            cb.method("util", String_.TOKEN, (b, self) -> b.return_(literal("x")));
                        }
                    }),
                    "has the signature of the static method",
                    "extends Base",
                    "public String util() { return \"x\"; }",
                    "overridden method is static");
            // javac lets a class of another package declare a method by the name of one of package access
            classUnlikeJavac(
                    IllegalArgumentException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            cb.method("local", String_.TOKEN, (b, self) -> b.return_(literal("x")));
                        }
                    }),
                    "a method declared by its name overrides nothing",
                    "extends Base",
                    "public String local() { return \"x\"; }");
        }

        @Test
        void aMethodTheSuperclassAndAnInterfaceGiveABodyEachIsOverridden() throws Throwable {
            // javac takes the method of the class; the typed layer has the class say which it is
            classUnlikeJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Mid_.extend(h.self()));
                            sh.implements_(Named_.implement(h.self()));
                            sh.body(ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                        }
                    }),
                    "unrelated methods with a body each, and overrides neither",
                    "extends Mid implements Named",
                    "");
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Mid_.extend(h.self()));
                    sh.implements_(Named_.implement(h.self()));
                    var cb = sh.body(ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                    cb.override(
                            Mid_.label, (b, self) -> b.return_(concat(cb.superCall(self, Mid_.label), literal("!"))));
                }
            }));

            assertThat(compiled.invoke("label")).isEqualTo("mid!");
        }

        @Test
        void aProtectedMethodOfTheSuperclassDoesNotStandForTheMethodOfAnInterface() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Mid_.extend(h.self()));
                            sh.inherited(UnsafeFacts.open(Mid_.quiet));
                        }
                    }))
                    .withMessageContaining("it is not public there");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Base_.extend(h.self()));
                            sh.inherited(UnsafeFacts.voidMethod(Base_.TOKEN, "absent", MemberTraits.FINAL));
                        }
                    }))
                    .withMessageContaining("on the target classpath the class has no method absent()");
        }
    }

    @Nested
    class Constructors {

        @Test
        void aConstructorDeclaresWhatTheConstructorOfTheSuperclassThrows() {
            classAsJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var area = h.overriding(Shape_.area);
                            var describe = h.overriding(h.protected_(Shape_.describe_String));
                            h.extends_(Shape_.extend(h.self()).area(area).describe_String(describe))
                                    .body(ctor -> ctor.super_(Shape_.super_String, literal("s"), (b, self) -> b.end()));
                        }
                    }),
                    "can throw the checked exception java.io.IOException",
                    "extends Shape",
                    "public Probe() { super(\"s\"); }\n"
                            + "@Override public int area() { return 0; }\n"
                            + "@Override protected String describe(String p) { return p; }",
                    "unreported exception");
        }

        @Test
        void superIsCalledOnceAndOfTheSuperclass() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extends_(Base_.extend(h.self())).body(ctor -> {
                                ctor.super_(Base_.new_, (b, self) -> b.end());
                                return ctor.super_(Base_.new_, (b, self) -> b.end());
                            });
                        }
                    }))
                    .withMessageContaining("has called super(...) already");
            classAsJavac(
                    IllegalArgumentException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            cb.constructor(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        }
                    }),
                    "clashes with the constructor",
                    "extends Base",
                    "public Probe() { super(); }\npublic Probe() { super(); }",
                    "is already defined");
        }
    }

    @Nested
    class SuperAndProtected {

        @Test
        void superDoesNotCallAnAbstractMethod() {
            classAsJavac(
                    IllegalArgumentException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var area = h.overriding(Shape_.area);
                            var describe = h.overriding(h.protected_(Shape_.describe_String));
                            var cb = h.extends_(
                                            Shape_.extend(h.self()).area(area).describe_String(describe))
                                    .throwing(IOException_.TOKEN)
                                    .body(ctor -> ctor.super_(Shape_.super_String, literal("s"), (b, self) -> b.end()));
                            cb.define(area.method(), (b, self) -> b.return_(cb.superCall(self, Shape_.area)));
                        }
                    }),
                    "is abstract in me.supcheg.javafile.typed.fixtures.Shape (JLS 15.12.3)",
                    "extends Shape",
                    "public Probe() throws java.io.IOException { super(\"s\"); }\n"
                            + "@Override public int area() { return super.area(); }\n"
                            + "@Override protected String describe(String p) { return p; }",
                    "cannot be accessed directly");
        }

        @Test
        void superCallsTheMethodAsTheSuperclassHasIt() {
            // Mid overrides risky() without its throws clause: super.risky() is that method, and the
            // fact of Base tells another, which a catch of its exception would not compile against
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Mid_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                            var risky = cb.protected_(Base_.risky);
                            cb.throwing(IOException_.TOKEN)
                                    .voidMethod(
                                            "risk",
                                            (b, self) -> b.exec(cb.superCall(self, risky))
                                                    .end());
                        }
                    }))
                    .withMessageContaining("its throws clause is not that of the fact")
                    .withMessageContaining("name the method by the fact of that class");
        }

        @Test
        void aProtectedMethodIsCalledOnTheClassItself() {
            classAsJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            var hook = cb.protected_(Base_.hook_String);
                            cb.method(
                                    "other",
                                    String_.TOKEN,
                                    Base_.TOKEN,
                                    (b, self, base) -> b.return_(call(base, hook, literal("x"))));
                        }
                    }),
                    "is protected, and is called on an expression of me.supcheg.javafile.typed.fixtures.Base",
                    "extends Base",
                    "public String other(Base base) { return base.hook(\"x\"); }",
                    "has protected access in");
        }

        @Test
        void aProtectedCallWhoseTypeHasToBeExactIsBoundToAVariableFirst() throws Throwable {
            // among the overloads of pick the argument is of exactly its type, which a call on the
            // class itself is not known to be, and the receiver cannot be cast to Base
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            Upcast<Self, Base> asBase = cb.asSuper();
                            var hook = cb.protected_(Base_.hook_String);
                            cb.method(
                                    "picked",
                                    String_.TOKEN,
                                    (b, self) -> b.return_(call(
                                            asBase.apply(self),
                                            Base_.pick_String,
                                            call(asBase.apply(self), hook, literal("x")))));
                        }
                    }))
                    .withMessageContaining("bind the result to a variable with let first");
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extends_(Base_.extend(h.self()))
                            .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    Upcast<Self, Base> asBase = cb.asSuper();
                    var hook = cb.protected_(Base_.hook_String);
                    cb.method(
                            "picked",
                            String_.TOKEN,
                            (b, self) -> b.let(
                                    String_.TOKEN,
                                    call(asBase.apply(self), hook, literal("x")),
                                    hooked -> b.return_(call(asBase.apply(self), Base_.pick_String, hooked))));
                }
            }));

            assertThat(compiled.invoke("picked")).isEqualTo("string");
        }

        @Test
        void aProtectedFieldIsReachedOnThisAlone() {
            // stricter than javac, which lets a class reach the field on any value of the class
            classUnlikeJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Base_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            var hits = cb.protected_(Base_.hits);
                            cb.method(
                                    "peek",
                                    PrimitiveToken.INT,
                                    cb.self(),
                                    (b, self, other) ->
                                            b.return_(field(cb.asSuper().apply(other), hits)));
                        }
                    }),
                    "is reached on an expression that is not this",
                    "extends Base",
                    "public int peek(Probe other) { return other.hits; }");
        }

        @Test
        void aClassThatIsACheckedExceptionIsDeclaredWhereItIsThrown() {
            classAsJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extends_(Exception_.extend(h.self()))
                                    .body(ctor -> ctor.super_(Exception_.new_, (b, self) -> b.end()));
                            cb.voidMethod(
                                    "raise", (b, self) -> b.throw_(cb.asSuper().apply(self)));
                        }
                    }),
                    "can throw the checked exception me.supcheg.example.Probe",
                    "extends Exception",
                    "public void raise() { throw this; }",
                    "unreported exception");
        }

        @Test
        void aProtectedFactIsOfNoUseToAClassThatIsNoSubclass() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cb = h.extendsObject().body();
                            cb.method(
                                    "motto",
                                    String_.TOKEN,
                                    (b, self) -> b.return_(staticCall(UnsafeFacts.open(Base_.motto))));
                        }
                    }))
                    .withMessageContaining("is protected: it is reached in the body of a subclass of")
                    .withMessageContaining("the fact was taken out for another class");
        }

        @Test
        void aProtectedMethodThatThrowsIsCalledAsSuper() {
            // this.check() would be the method the class has, which may declare fewer exceptions
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var area = h.overriding(Shape_.area);
                            var describe = h.overriding(h.protected_(Shape_.describe_String));
                            var cb = h.extends_(
                                            Shape_.extend(h.self()).area(area).describe_String(describe))
                                    .throwing(IOException_.TOKEN)
                                    .body(ctor -> ctor.super_(Shape_.super_String, literal("s"), (b, self) -> b.end()));
                            cb.define(area.method(), (b, self) -> b.return_(literal(0)));
                            cb.define(describe.method(), (b, self, p) -> b.return_(p));
                            var check = cb.protected_(Shape_.check);
                            cb.throwing(IOException_.TOKEN)
                                    .voidMethod(
                                            "checked",
                                            (b, self) -> b.exec(voidCall(
                                                            cb.asSuper().apply(self), check))
                                                    .end());
                        }
                    }))
                    .withMessageContaining("it declares a checked exception")
                    .withMessageContaining("call it as super.check(...) by superCall");
        }
    }
}
