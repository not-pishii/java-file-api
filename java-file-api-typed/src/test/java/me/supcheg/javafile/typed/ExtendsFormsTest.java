package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Joining;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.Thread_;
import me.supcheg.javafile.typed.testfacts.java.lang.Throwable_;
import me.supcheg.javafile.typed.testfacts.java.util.AbstractCollection_;
import me.supcheg.javafile.typed.testfacts.java.util.AbstractList_;
import me.supcheg.javafile.typed.testfacts.java.util.concurrent.Callable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Joining_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Risking_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.concurrent.Callable;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static me.supcheg.javafile.typed.Subclasses.began;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The forms of a class with a superclass that [ExtendsTest] leaves out (§8e): a constructor
/// declared in the header, whose fact is there before any member; the members of two
/// parameters; what a superclass gives a class by inheritance for the methods of an interface;
/// and the facts of a superclass held against its heritage.
class ExtendsFormsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void aConstructorDeclaredInTheHeaderIsThereForEveryBody() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(Base_.extend(h.self()));
                var make = sh.declareConstructor(String_.TOKEN);
                var cb = sh.body(make, (ctor, name) -> ctor.super_(Base_.super_String, name, (b, self) -> b.end()));
                cb.staticMethod("of", cb.self(), String_.TOKEN, (b, s) -> b.return_(new_(make, s)));
                // among the members a constructor is declared and defined as that of any class is
                var plain = cb.declareConstructor();
                cb.define(plain, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                var limit = cb.protected_(Base_.limit);
                cb.staticMethod(
                        "raised",
                        PrimitiveToken.INT,
                        b -> b.exec(assignStaticField(limit, addInt(staticField(limit), literal(1))))
                                .return_(staticField(limit)));
            }
        }));

        assertThat(compiled.source())
                .contains("public Probe(String v0) {\n        super(v0);\n    }")
                .contains("return new Probe(v0);")
                .contains("Base.limit = Base.limit + 1;");
        assertThat(((Base) compiled.invoke("of", "made")).name).isEqualTo("made");
        assertThat(compiled.invoke("raised")).isEqualTo(4);
    }

    @Test
    void aConstructorTheHeaderDeclaresIsDefined() {
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var sh = h.extends_(Base_.extend(h.self()));
                        sh.declareConstructor(String_.TOKEN);
                        sh.body(sh.declareConstructor(), ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    }
                }))
                .withMessageContaining("these declared members are never defined")
                .withMessageContaining("Probe(java.lang.String)");
        // one of another class is not one the header declared
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var sh = h.extends_(Base_.extend(h.self()));
                        sh.body(
                                UnsafeFacts.ctor(h.self(), MemberTraits.FINAL),
                                ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    }
                }))
                .withMessageContaining("it was not declared by the builder of");
    }

    @Test
    void anExceptionOfTwoParametersImplementsAnInterfaceThatThrowsIt() throws Throwable {
        Callable_<String> callable = new Callable_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.ofSerializable(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(Exception_.extend(h.self()));
                Upcast<Self, Exception> asException = sh.asSuper();
                // call() throws Probe: the clause of the override is of the class itself
                var call = sh.throwing(asException, h.self()).overriding(callable.call);
                sh.implements_(callable.implement(h.self()).call(call));
                var make = sh.declareConstructor(String_.TOKEN, Throwable_.TOKEN);
                var cb = sh.body(
                        make,
                        (ctor, message, cause) ->
                                ctor.super_(Exception_.new_String_Throwable, message, cause, (b, self) -> b.end()));
                cb.constructor(ctor -> ctor.super_(Exception_.new_String, literal("plain"), (b, self) -> b.end()));
                cb.define(call.method(), (b, self) -> b.throw_(asException, self));
                cb.method(
                        "handled",
                        String_.TOKEN,
                        (b, self) -> b.let(
                                String_.TOKEN,
                                literal("none"),
                                ignored -> b.try_(
                                                handlers -> handlers.catch_(asException, cb.self(), (c, e) -> {}),
                                                t -> t.exec(call(self, call.method())))
                                        .return_(call(asException.apply(self), Throwable_.getMessage))));
            }
        }));

        assertThat(compiled.source())
                .contains("public Probe(String v0, Throwable v1) {\n        super(v0, v1);\n    }")
                .contains("public String call() throws Probe {\n        throw this;")
                .contains("} catch (Probe v");
        assertThat(compiled.invoke("handled")).isEqualTo("plain");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThatThrownBy(() -> ((Callable<?>) probe).call()).isSameAs(probe);
    }

    @Test
    void aThreadRunsTheMethodTheClassOverrides() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(Thread_.extend(h.self()));
                var cb = began(sh, ctor -> ctor.super_(Thread_.new_, (b, self) -> b.end()));
                var ran = cb.mutableField("ran", String_.TOKEN, literal("no"));
                cb.override(
                        Thread_.run,
                        (b, self) -> b.exec(
                                        assignField(self, ran, call(cb.asSuper().apply(self), Thread_.getName)))
                                .end());
            }
        }));

        Thread thread = (Thread) compiled.generated().getConstructor().newInstance();
        thread.setName("probe");
        thread.start();
        thread.join();
        assertThat(compiled.generated().getField("ran").get(thread)).isEqualTo("probe");
    }

    @Test
    void methodsWithParametersAreHadFromTheSuperclassAndCalledAsSuper() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var sh = h.extends_(Mid_.extend(h.self()));
                Upcast<Self, Joining> asJoining = sh.implements_(Joining_.implement(h.self())
                        .join_String_String(sh.inherited(Mid_.join_String_String))
                        .put_String(sh.inherited(Mid_.put_String))
                        .put_String_String(sh.inherited(Mid_.put_String_String)));
                var cb = began(sh, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                cb.method(
                        "joined",
                        String_.TOKEN,
                        (b, self) -> b.exec(cb.superCall(self, Mid_.put_String, literal("k")))
                                .exec(cb.superCall(self, Mid_.put_String_String, literal("k"), literal("v")))
                                .exec(voidCall(asJoining.apply(self), Joining_.put_String, literal("k")))
                                .return_(concat(
                                        cb.superCall(self, Mid_.join_String_String, literal("a"), literal("b")),
                                        call(
                                                asJoining.apply(self),
                                                Joining_.join_String_String,
                                                literal("c"),
                                                literal("d")))));
                cb.method(
                        "runs",
                        PrimitiveToken.INT,
                        (b, self) -> b.exec(cb.superCall(self, Mid_.put_String_String, literal("k"), literal("v")))
                                .return_(field(cb.asSuper().apply(self), Mid_.runs)));
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe extends Mid implements Joining {")
                .contains("super.put(\"k\", \"v\");")
                .contains("super.join(\"a\", \"b\")");
        assertThat(compiled.invoke("joined")).isEqualTo("abcd");
        assertThat(compiled.invoke("runs")).isEqualTo(100);
    }

    @Test
    void aMethodOfTheSuperclassStandsForThatOfAnInterfaceOnlyAsAnOverrideWould() {
        // label() of Mid, a String, stands for the CharSequence of Labelled as an override of that
        // result would (CovariantReturnTest); risk() of Mid declares what that of Risking does not
        classAsJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var sh = h.extends_(Mid_.extend(h.self()));
                        sh.implements_(Risking_.implement(h.self()).risk(sh.inherited(Mid_.risk)));
                    }
                }),
                "an override cannot declare the checked exception java.io.IOException",
                "extends Mid implements Risking",
                "",
                "overridden method does not throw java.io.IOException");
    }

    @Test
    void twoConstructorsOfOneSignatureDeclaredInTheHeaderAreRejectedWhereTheMembersBegin() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var sh = h.extends_(Base_.extend(h.self()));
                        var first = sh.declareConstructor(String_.TOKEN);
                        sh.declareConstructor(String_.TOKEN);
                        sh.body(first, (ctor, name) -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    }
                }),
                "clashes with the constructor",
                "extends Base",
                "public Probe(String a) { super(); } public Probe(String b) { super(); }",
                "constructor Probe(java.lang.String) is already defined");
    }

    @Test
    void superCallsAMethodTheClassReachesAndAnInstanceOne() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = began(
                                h.extends_(Base_.extend(h.self())),
                                ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        cb.method(
                                "m",
                                String_.TOKEN,
                                (b, self) -> b.return_(cb.superCall(
                                        self,
                                        UnsafeFacts.method(
                                                Base_.TOKEN, "local", String_.TOKEN, MemberTraits.OVERRIDABLE))));
                    }
                }),
                "the method has package access",
                "extends Base",
                "public String m() { return super.local(); }",
                "cannot be accessed from outside package");
        // javac calls a static method through super, and warns
        classUnlikeJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = began(
                                h.extends_(Base_.extend(h.self())),
                                ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        cb.method(
                                "m",
                                String_.TOKEN,
                                (b, self) -> b.return_(cb.superCall(
                                        self,
                                        UnsafeFacts.method(Base_.TOKEN, "util", String_.TOKEN, MemberTraits.FINAL))));
                    }
                }),
                "calls an instance method, and util() of me.supcheg.javafile.typed.fixtures.Base is static",
                "extends Base",
                "public String m() { return super.util(); }");
    }

    @Test
    void aSupertypeHasNoWildcardTypeArgument() {
        classAsJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        h.extends_(
                                UnsafeFacts.steps(AbstractList_.ANY, h.self()).complete());
                    }
                }),
                "a supertype has no wildcard type argument (JLS 8.1.4, 8.1.5)",
                "extends AbstractList<?>",
                "public Object get(int i) { return null; } public int size() { return 0; }",
                "unexpected type");
    }

    @Test
    void theFactsOfASuperclassAreWrittenAsItsHeritageIs() throws Throwable {
        // super.m() holds the fact against the member of the superclass by its types as they are
        // written: a primitive, an array, a type argument of the class, a wildcard of it
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var list = new AbstractList_<>(String_.TOKEN);
                var coll = new AbstractCollection_<>(String_.TOKEN);
                var eh = h.extending(list.token);
                var get = eh.overriding(list.get_int);
                var size = eh.overriding(coll.size);
                var sh = eh.extends_(list.extend(h.self()).get_int(get).size(size));
                var cb = began(sh, ctor -> ctor.super_(list.super_, (b, self) -> b.end()));
                cb.define(get.method(), (b, self, i) -> b.return_(literal("item")));
                cb.define(size.method(), (b, self) -> b.return_(literal(2)));
                cb.method(
                        "index",
                        PrimitiveToken.INT,
                        (b, self) -> b.return_(cb.superCall(self, list.indexOf_Object, literal("item"))));
                cb.method(
                        "part",
                        list.subList_int_int.result(),
                        (b, self) -> b.return_(cb.superCall(self, list.subList_int_int, literal(0), literal(1))));
                cb.method("array", coll.toArray.result(), (b, self) -> b.return_(cb.superCall(self, coll.toArray)));
                cb.method(
                        "contained",
                        PrimitiveToken.BOOLEAN,
                        (b, self) -> b.return_(cb.superCall(
                                self,
                                coll.containsAll_Collection,
                                cb.superCall(self, list.subList_int_int, literal(0), literal(1)))));
            }
        }));

        assertThat(compiled.invoke("index")).isEqualTo(0);
        assertThat(compiled.invoke("part")).isEqualTo(List.of("item"));
        assertThat((Object[]) compiled.invoke("array")).containsExactly("item", "item");
        assertThat(compiled.invoke("contained")).isEqualTo(true);
    }
}
