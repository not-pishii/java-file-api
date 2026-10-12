package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Mid;
import me.supcheg.javafile.typed.fixtures.Named;
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
                    var ctor1 = sh.declareConstructor();
                    var cb = sh.members();
                    Upcast<Self, AbstractList<String>> asList = cb.asSuper();
                    // what the class inherits is called through the witness, and as super.m()
                    var own2 = cb.declareOverride(coll.toString);
                    var member1 = cb.declareMethod("empty", PrimitiveToken.BOOLEAN);
                    var member2 = cb.declareMethod("joined", String_.TOKEN);
                    var bodies = cb.bodies(ctor1, ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
                    bodies.define(get.method(), (b, self, i) -> b.return_(literal("item")));
                    bodies.define(size.method(), (b, self) -> b.return_(literal(2)));
                    bodies.define(
                            compareTo.method(),
                            (b, self, other) ->
                                    b.return_(subInt(call(self, size.method()), call(other, size.method()))));
                    bodies.define(
                            own2,
                            (b, self) -> b.return_(concat(literal("names"), bodies.superCall(self, coll.toString))));
                    bodies.define(member1, (b, self) -> b.return_(call(asList.apply(self), coll.isEmpty)));
                    bodies.define(
                            member2,
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
                    var ctor3 = sh.declareConstructor();
                    var cb = sh.members();
                    Upcast<Self, Exception> asException = cb.asSuper();
                    var ofMessage = cb.declareConstructor(String_.TOKEN);

                    // throws Probe: the clause, the throw and the catch are of the class itself
                    var raise = cb.throwing(asException, cb.self())
                            .declareStaticMethod("raise", String_.TOKEN, String_.TOKEN);
                    var member3 = cb.declareStaticMethod("caught", String_.TOKEN);
                    // seen as an Exception the thrown value is still the class to javac, and to the checks
                    var member4 = cb.throwing(asException, cb.self()).declareVoidMethod("rethrow");
                    var bodies = cb.bodies(
                            ctor3, ctor -> ctor.super_(Exception_.new_String, literal("failed"), (b, self) -> b.end()));
                    bodies.define(
                            ofMessage,
                            (ctor, message) -> ctor.super_(Exception_.new_String, message, (b, self) -> b.end()));
                    bodies.define(raise, (b, message) -> b.throw_(asException, new_(ofMessage, message)));
                    bodies.define(
                            member3,
                            b -> b.tryTerminated(
                                    handlers -> handlers.catch_(
                                            asException,
                                            cb.self(),
                                            (c, e) -> c.return_(call(asException.apply(e), Throwable_.getMessage))),
                                    t -> t.return_(staticCall(raise, literal("mine")))));
                    bodies.define(member4, (b, self) -> b.throw_(asException.apply(self)));
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
                    var ctor4 = sh.declareConstructor();
                    var cb = sh.members();
                    var member5 = cb.declareVoidMethod("ranAsRunnable");
                    var bodies = cb.bodies(ctor4, ctor -> ctor.super_(Thread_.new_, (b, self) -> b.end()));
                    bodies.define(
                            member5,
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
                    var ctor5 = sh.declareConstructor();
                    var cb = sh.members();
                    var member6 = cb.declareConstructor(String_.TOKEN);
                    Upcast<Self, Base> asBase = cb.asSuper();
                    var hook = cb.protected_(Base_.hook_String);
                    var hits = cb.protected_(Base_.hits);
                    var motto = cb.protected_(Base_.motto);

                    var own = cb.declareOverride(hook);
                    // this.hook(...) by the fact of the superclass, on the class itself
                    var member7 = cb.declareMethod("viaBase", String_.TOKEN);
                    var member8 = cb.declareMethod("viaOwn", String_.TOKEN);
                    var member9 = cb.declareMethod("counted", PrimitiveToken.INT);
                    var member10 = cb.declareMethod("saying", String_.TOKEN);
                    var bodies = cb.bodies(ctor5, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    bodies.define(
                            own, (b, self, s) -> b.return_(concat(bodies.superCall(self, hook, s), literal("+"))));
                    bodies.define(
                            member6,
                            (ctor, name) ->
                                    ctor.super_(Base_.super_String, concat(name, literal("!")), (b, self) -> b.end()));
                    bodies.define(member7, (b, self) -> b.return_(call(asBase.apply(self), hook, literal("x"))));
                    bodies.define(member8, (b, self) -> b.return_(call(self, own, literal("y"))));
                    bodies.define(
                            member9,
                            (b, self) -> b.exec(assignField(
                                            asBase.apply(self),
                                            hits,
                                            addInt(field(asBase.apply(self), hits), literal(5))))
                                    .return_(field(asBase.apply(self), hits)));
                    bodies.define(member10, (b, self) -> b.return_(staticCall(motto)));
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
                    var eh = h.extending(Shape_.TOKEN);
                    var describe = eh.overriding(eh.protected_(Shape_.describe_String));
                    var sh = eh.extends_(Shape_.extend(h.self()).area(area).describe_String(describe));
                    var ctor6 = sh.throwing(IOException_.TOKEN).declareConstructor(String_.TOKEN);
                    var cb = sh.throwing(IOException_.TOKEN).members();
                    var check = cb.protected_(Shape_.check);
                    var member11 = cb.throwing(IOException_.TOKEN).declareVoidMethod("checked");
                    var bodies = cb.bodies(
                            ctor6, (ctor, label) -> ctor.super_(Shape_.super_String, label, (b, self) -> b.end()));
                    bodies.define(area.method(), (b, self) -> b.return_(literal(4)));
                    bodies.define(describe.method(), (b, self, prefix) -> b.return_(concat(prefix, literal("="))));
                    bodies.define(
                            member11,
                            (b, self) -> b.exec(bodies.superCall(self, check)).end());
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
                    var cb = h.extendsObject().members();
                    var clone = cb.protected_(Object_.clone);
                    var own7 = cb.throwing(
                                    me.supcheg.javafile.typed.testfacts.java.lang.CloneNotSupportedException_.TOKEN)
                            .declareOverride(clone);
                    var bodies = cb.bodies();
                    bodies.define(own7, (b, self) -> b.return_(bodies.superCall(self, clone)));
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
                    var ctor8 = sh.declareConstructor();
                    var cb = sh.members();
                    Upcast<Self, Mid> asMid = cb.asSuper();
                    var hook = cb.protected_(Mid_.hook_String);
                    var member12 = cb.declareMethod("hooked", String_.TOKEN);
                    var member13 = cb.declareMethod("ran", PrimitiveToken.INT);
                    var bodies = cb.bodies(ctor8, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                    bodies.define(
                            member12,
                            (b, self) -> b.return_(bodies.superCall(self, hook, call(asMid.apply(self), Base_.greet))));
                    bodies.define(
                            member13,
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
                    .withMessageContaining("its header, which named the superclass at")
                    .withMessageContaining("never came to the members");
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
                    var ctor9 = sh.declareConstructor();
                    sh.members().bodies(ctor9, ctor -> ctor.super_(Exception_.new_, (b, self) -> b.end()));
                }
            });
        }

        @Test
        void anAbstractMethodOfTheSuperclassIsOverriddenNotInherited() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var eh = h.extending(Shape_.TOKEN);
                            var describe = eh.overriding(eh.protected_(Shape_.describe_String));
                            eh.extends_(Shape_.extend(h.self())
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
                            h.extending(Shape_.TOKEN).protected_(Shape_.describe_String);
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
                            var sh10 = h.extends_(Base_.extend(h.self()));
                            var ctor10 = sh10.declareConstructor();
                            var cb = sh10.members();
                            var own11 = cb.declareOverride(Base_.sealed);
                            var bodies = cb.bodies(ctor10, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(own11, (b, self) -> b.return_(literal("x")));
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
                            var sh12 = h.extends_(Base_.extend(h.self()));
                            var ctor12 = sh12.declareConstructor();
                            var cb = sh12.members();
                            var member14 = cb.declareMethod("greet", String_.TOKEN);
                            var bodies = cb.bodies(ctor12, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(member14, (b, self) -> b.return_(literal("x")));
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
                            var sh13 = h.extends_(Base_.extend(h.self()));
                            var ctor13 = sh13.declareConstructor();
                            var cb = sh13.members();
                            var member15 = cb.declareMethod("util", String_.TOKEN);
                            var bodies = cb.bodies(ctor13, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(member15, (b, self) -> b.return_(literal("x")));
                        }
                    }),
                    "has the signature of the static method",
                    "extends Base",
                    "public String util() { return \"x\"; }",
                    "overridden method is static");
        }

        @Test
        void theMethodOfTheSuperclassStandsForTheDefaultOneOfAnInterface() throws Throwable {
            // String label() of Mid, default String label() of Named: the method of the class stands
            CompiledClasses inherited = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Mid_.extend(h.self()));
                    Upcast<Self, Named> asNamed = sh.implements_(Named_.implement(h.self()));
                    var ctor14 = sh.declareConstructor();
                    var cb = sh.members();
                    var member16 = cb.declareMethod("go", String_.TOKEN);
                    var bodies = cb.bodies(ctor14, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                    bodies.define(member16, (b, self) -> b.return_(call(asNamed.apply(self), Named_.label)));
                }
            }));
            assertThat(inherited.invoke("go")).isEqualTo("mid");

            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Mid_.extend(h.self()));
                    sh.implements_(Named_.implement(h.self()));
                    var ctor15 = sh.declareConstructor();
                    var cb = sh.members();
                    var own16 = cb.declareOverride(Mid_.label);
                    var bodies = cb.bodies(ctor15, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                    bodies.define(
                            own16, (b, self) -> b.return_(concat(bodies.superCall(self, Mid_.label), literal("!"))));
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
                            var eh = h.extending(Shape_.TOKEN);
                            var describe = eh.overriding(eh.protected_(Shape_.describe_String));
                            var sh = eh.extends_(
                                    Shape_.extend(h.self()).area(area).describe_String(describe));
                            var ctor17 = sh.declareConstructor();
                            sh.members()
                                    .bodies(
                                            ctor17,
                                            ctor -> ctor.super_(
                                                    Shape_.super_String, literal("s"), (b, self) -> b.end()));
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
                            var sh = h.extends_(Base_.extend(h.self()));
                            var ctor18 = sh.declareConstructor();
                            sh.members().bodies(ctor18, ctor -> {
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
                            var sh19 = h.extends_(Base_.extend(h.self()));
                            var ctor19 = sh19.declareConstructor();
                            var cb = sh19.members();
                            var member17 = cb.declareConstructor();
                            var bodies = cb.bodies(ctor19, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(member17, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
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
                            var eh = h.extending(Shape_.TOKEN);
                            var describe = eh.overriding(eh.protected_(Shape_.describe_String));
                            var sh = eh.extends_(
                                    Shape_.extend(h.self()).area(area).describe_String(describe));
                            var ctor20 = sh.throwing(IOException_.TOKEN).declareConstructor();
                            var cb = sh.members();
                            var bodies = cb.bodies(
                                    ctor20,
                                    ctor -> ctor.super_(Shape_.super_String, literal("s"), (b, self) -> b.end()));
                            bodies.define(area.method(), (b, self) -> b.return_(bodies.superCall(self, Shape_.area)));
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
                            var sh21 = h.extends_(Mid_.extend(h.self()));
                            var ctor21 = sh21.declareConstructor();
                            var cb = sh21.members();
                            var risky = cb.protected_(Base_.risky);
                            var member18 = cb.throwing(IOException_.TOKEN).declareVoidMethod("venture");
                            var bodies = cb.bodies(ctor21, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                            bodies.define(
                                    member18,
                                    (b, self) -> b.exec(bodies.superCall(self, risky))
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
                            var sh22 = h.extends_(Base_.extend(h.self()));
                            var ctor22 = sh22.declareConstructor();
                            var cb = sh22.members();
                            var hook = cb.protected_(Base_.hook_String);
                            var member19 = cb.declareMethod("other", String_.TOKEN, Base_.TOKEN);
                            var bodies = cb.bodies(ctor22, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(member19, (b, self, base) -> b.return_(call(base, hook, literal("x"))));
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
                            var sh23 = h.extends_(Base_.extend(h.self()));
                            var ctor23 = sh23.declareConstructor();
                            var cb = sh23.members();
                            Upcast<Self, Base> asBase = cb.asSuper();
                            var hook = cb.protected_(Base_.hook_String);
                            var member20 = cb.declareMethod("picked", String_.TOKEN);
                            var bodies = cb.bodies(ctor23, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(
                                    member20,
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
                    var sh24 = h.extends_(Base_.extend(h.self()));
                    var ctor24 = sh24.declareConstructor();
                    var cb = sh24.members();
                    Upcast<Self, Base> asBase = cb.asSuper();
                    var hook = cb.protected_(Base_.hook_String);
                    var member21 = cb.declareMethod("picked", String_.TOKEN);
                    var bodies = cb.bodies(ctor24, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    bodies.define(
                            member21,
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
                            var sh25 = h.extends_(Base_.extend(h.self()));
                            var ctor25 = sh25.declareConstructor();
                            var cb = sh25.members();
                            var hits = cb.protected_(Base_.hits);
                            var member22 = cb.declareMethod("peek", PrimitiveToken.INT, cb.self());
                            var bodies = cb.bodies(ctor25, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                            bodies.define(
                                    member22,
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
                            var sh26 = h.extends_(Exception_.extend(h.self()));
                            var ctor26 = sh26.declareConstructor();
                            var cb = sh26.members();
                            var member23 = cb.declareVoidMethod("raise");
                            var bodies = cb.bodies(ctor26, ctor -> ctor.super_(Exception_.new_, (b, self) -> b.end()));
                            bodies.define(
                                    member23, (b, self) -> b.throw_(cb.asSuper().apply(self)));
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
                            var cb = h.extendsObject().members();
                            var member24 = cb.declareMethod("motto", String_.TOKEN);
                            var bodies = cb.bodies();
                            bodies.define(member24, (b, self) -> b.return_(staticCall(UnsafeFacts.open(Base_.motto))));
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
                            var eh = h.extending(Shape_.TOKEN);
                            var describe = eh.overriding(eh.protected_(Shape_.describe_String));
                            var sh = eh.extends_(
                                    Shape_.extend(h.self()).area(area).describe_String(describe));
                            var ctor27 = sh.throwing(IOException_.TOKEN).declareConstructor();
                            var cb = sh.members();
                            var check = cb.protected_(Shape_.check);
                            var member25 = cb.throwing(IOException_.TOKEN).declareVoidMethod("checked");
                            var bodies = cb.bodies(
                                    ctor27,
                                    ctor -> ctor.super_(Shape_.super_String, literal("s"), (b, self) -> b.end()));
                            bodies.define(area.method(), (b, self) -> b.return_(literal(0)));
                            bodies.define(describe.method(), (b, self, p) -> b.return_(p));
                            bodies.define(
                                    member25,
                                    (b, self) -> b.exec(voidCall(cb.asSuper().apply(self), check))
                                            .end());
                        }
                    }))
                    .withMessageContaining("it declares a checked exception")
                    .withMessageContaining("call it as super.check(...) by superCall");
        }
    }
}
