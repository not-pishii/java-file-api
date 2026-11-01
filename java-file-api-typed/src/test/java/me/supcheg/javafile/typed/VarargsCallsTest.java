package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.fixtures.Gathered;
import me.supcheg.javafile.typed.fixtures.Origin;
import me.supcheg.javafile.typed.fixtures.Spreads;
import me.supcheg.javafile.typed.testfacts.java.io.StringReader_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.Objects_;
import me.supcheg.javafile.typed.testfacts.java.util.stream.IntStream_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Gathered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Lined_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbering_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Origin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Packed_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Pairing_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Scattering_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Spreads_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tripling_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.varargs;
import static me.supcheg.javafile.typed.Expressions.voidStaticCall;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// The arguments of a parameter of variable arity given one by one, `m(a, e1, e2)`: written so where javac
/// resolves the call to the method by them, and as the array of the arguments where it may take another method.
class VarargsCallsTest {
    private static final ArrayToken<String[], String> STRINGS = ArrayToken.of(String_.TOKEN);
    private static final ArrayToken<Object[], Object> OBJECTS = ArrayToken.of(Object_.TOKEN);
    private static final ArrayToken<String[][], String[]> STRINGS_2 = ArrayToken.of(STRINGS);
    private static final ArrayToken<int[], me.supcheg.javafile.facts.Prim.Int> INTS = PrimitiveToken.INT.array();
    private static final String FIXTURES = "me.supcheg.javafile.typed.fixtures.";

    private static JavaFile file(String name, TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    private static JavaFile declared(String name, TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example", name), spec);
    }

    @Test
    void aMethodNothingElseOfItsNameCompetesWithIsCalledWithItsArgumentsOneByOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file("Calls", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var j1 = cb.declareStaticMethod("j1", String_.TOKEN);
                var j2 = cb.declareStaticMethod("j2", String_.TOKEN);
                var j3 = cb.declareStaticMethod("j3", String_.TOKEN);
                var p1 = cb.declareStaticMethod("p1", String_.TOKEN);
                var p2 = cb.declareStaticMethod("p2", String_.TOKEN);
                var p3 = cb.declareStaticMethod("p3", String_.TOKEN);
                var s1 = cb.declareStaticMethod("s1", String_.TOKEN);
                var s2 = cb.declareStaticMethod("s2", String_.TOKEN);
                var s3 = cb.declareStaticMethod("s3", String_.TOKEN);
                var c1 = cb.declareStaticMethod("c1", PrimitiveToken.INT);
                var c2 = cb.declareStaticMethod("c2", PrimitiveToken.INT);
                var c3 = cb.declareStaticMethod("c3", PrimitiveToken.INT);
                var bodies = cb.bodies();
                var a = literal("a");
                var b2 = literal("b");
                bodies.define(
                        j1,
                        b -> b.return_(new_(Spreads_.new_).call(Spreads_.join1_StringArray, varargs(STRINGS, a, b2))));
                bodies.define(
                        j2,
                        b -> b.return_(new_(Spreads_.new_)
                                .call(Spreads_.join2_int_StringArray, literal(1), varargs(STRINGS, a))));
                bodies.define(
                        j3,
                        b -> b.return_(new_(Spreads_.new_)
                                .call(Spreads_.join3_int_int_StringArray, literal(1), literal(2), varargs(STRINGS))));
                bodies.define(
                        p1,
                        b -> b.let(
                                Spreads_.TOKEN,
                                new_(Spreads_.new_),
                                s -> b.exec(s.voidCall(Spreads_.put1_StringArray, varargs(STRINGS, a, b2)))
                                        .return_(s.field(Spreads_.told))));
                bodies.define(
                        p2,
                        b -> b.let(
                                Spreads_.TOKEN,
                                new_(Spreads_.new_),
                                s -> b.exec(s.voidCall(Spreads_.put2_int_StringArray, literal(1), varargs(STRINGS, a)))
                                        .return_(s.field(Spreads_.told))));
                bodies.define(
                        p3,
                        b -> b.let(
                                Spreads_.TOKEN,
                                new_(Spreads_.new_),
                                s -> b.exec(s.voidCall(
                                                Spreads_.put3_int_int_StringArray,
                                                literal(1),
                                                literal(2),
                                                varargs(STRINGS, a)))
                                        .return_(s.field(Spreads_.told))));
                bodies.define(s1, b -> b.return_(staticCall(Spreads_.sjoin1_StringArray, varargs(STRINGS, a, b2))));
                bodies.define(
                        s2,
                        b -> b.return_(staticCall(Spreads_.sjoin2_int_StringArray, literal(1), varargs(STRINGS, a))));
                bodies.define(
                        s3,
                        b -> b.return_(staticCall(
                                Spreads_.sjoin3_int_int_StringArray, literal(1), literal(2), varargs(STRINGS, a))));
                bodies.define(
                        c1,
                        b -> b.exec(voidStaticCall(Spreads_.count1_StringArray, varargs(STRINGS, a, b2)))
                                .return_(literal(0)));
                bodies.define(
                        c2,
                        b -> b.exec(voidStaticCall(Spreads_.count2_int_StringArray, literal(1), varargs(STRINGS, a)))
                                .return_(literal(0)));
                bodies.define(
                        c3,
                        b -> b.exec(voidStaticCall(
                                        Spreads_.count3_int_int_StringArray,
                                        literal(1),
                                        literal(2),
                                        varargs(STRINGS, a)))
                                .return_(literal(0)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("new Spreads().join1(\"a\", \"b\")")
                .contains("new Spreads().join2(1, \"a\")")
                .contains("new Spreads().join3(1, 2)")
                .contains("v0.put1(\"a\", \"b\");")
                .contains("Spreads.sjoin3(1, 2, \"a\")")
                .contains("Spreads.count2(1, \"a\");");
        assertThat(compiled.invoke("j1")).isEqualTo("a+b");
        assertThat(compiled.invoke("j2")).isEqualTo("1:a");
        assertThat(compiled.invoke("j3")).isEqualTo("1,2:");
        assertThat(compiled.invoke("p1")).isEqualTo("a+b");
        assertThat(compiled.invoke("p2")).isEqualTo("1:a");
        assertThat(compiled.invoke("p3")).isEqualTo("1,2:a");
        assertThat(compiled.invoke("s1")).isEqualTo("a+b");
        assertThat(compiled.invoke("s2")).isEqualTo("1:a");
        assertThat(compiled.invoke("s3")).isEqualTo("1,2:a");
        int before = Spreads.counted;
        compiled.invoke("c1");
        compiled.invoke("c2");
        compiled.invoke("c3");
        assertThat(Spreads.counted - before).isEqualTo(2 + 2 + 3);
    }

    @Test
    void aConstructorOfVariableArityIsCalledByNewAndByTheSuperCallOfASubclass() throws Throwable {
        CompiledClasses created = CompiledClasses.of(file("Created", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var one = cb.declareStaticMethod("one", String_.TOKEN);
                var two = cb.declareStaticMethod("two", String_.TOKEN);
                var three = cb.declareStaticMethod("three", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(
                        one,
                        b -> b.return_(new_(Lined_.new_StringArray, varargs(STRINGS, literal("a"), literal("b")))
                                .field(Lined_.told)));
                bodies.define(
                        two,
                        b -> b.return_(new_(Pairing_.new_int_StringArray, literal(1), varargs(STRINGS, literal("a")))
                                .field(Pairing_.told)));
                bodies.define(
                        three,
                        b -> b.return_(new_(
                                        Tripling_.new_int_int_StringArray,
                                        literal(1),
                                        literal(2),
                                        varargs(STRINGS, literal("a")))
                                .field(Tripling_.told)));
                return bodies;
            }
        }));
        assertThat(created.source())
                .contains("new Lined(\"a\", \"b\").told")
                .contains("new Pairing(1, \"a\").told")
                .contains("new Tripling(1, 2, \"a\").told");
        assertThat(created.invoke("one")).isEqualTo("a+b");
        assertThat(created.invoke("two")).isEqualTo("1:a");
        assertThat(created.invoke("three")).isEqualTo("1,2:a");

        // super(...) of a protected constructor, and of public ones
        JavaFile packed = declared("Packs", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(Packed_.extend(h.self())).members();
                var ctor = cb.declareConstructor();
                return cb.bodies(
                        ctor,
                        c -> c.super_(
                                Packed_.super_StringArray,
                                varargs(STRINGS, literal("a"), literal("b")),
                                (b, self) -> b.end()));
            }
        });
        JavaFile numbered = declared("Numbers", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(Numbering_.extend(h.self())).members();
                var ctor = cb.declareConstructor();
                return cb.bodies(
                        ctor,
                        c -> c.super_(
                                Numbering_.super_int_StringArray,
                                literal(7),
                                varargs(STRINGS, literal("a")),
                                (b, self) -> b.end()));
            }
        });
        JavaFile lined = declared("Lines", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(Lined_.extend(h.self())).members();
                var ctor = cb.declareConstructor();
                return cb.bodies(
                        ctor,
                        c -> c.super_(Lined_.new_StringArray, varargs(STRINGS, literal("x")), (b, self) -> b.end()));
            }
        });
        JavaFile paired = declared("Pairs", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(Pairing_.extend(h.self())).members();
                var ctor = cb.declareConstructor();
                return cb.bodies(
                        ctor,
                        c -> c.super_(
                                Pairing_.new_int_StringArray,
                                literal(3),
                                varargs(STRINGS, literal("x"), literal("y")),
                                (b, self) -> b.end()));
            }
        });
        Compilation compilation = RenderedJava.compile(List.of(packed, numbered, lined, paired));
        CompilationSubject.assertThat(compilation).succeededWithoutWarnings();
        assertThat(packed.render()).contains("super(\"a\", \"b\");");
        assertThat(numbered.render()).contains("super(7, \"a\");");
        assertThat(lined.render()).contains("super(\"x\");");
        assertThat(paired.render()).contains("super(3, \"x\", \"y\");");
    }

    @Test
    void aMethodOfASuperclassOfVariableArityIsCalledBySuperWithItsArgumentsOneByOne() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(declared("Supers", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extends_(Spreads_.extend(h.self())).members();
                Upcast<Self, Spreads> asSpreads = cb.asSuper();
                var ctor = cb.declareConstructor();
                var j1 = cb.declareMethod("viaSuper1", String_.TOKEN);
                var j2 = cb.declareMethod("viaSuper2", String_.TOKEN);
                var p1 = cb.declareMethod("viaPut1", String_.TOKEN);
                var p2 = cb.declareMethod("viaPut2", String_.TOKEN);
                var own = cb.declareMethod("viaThis", String_.TOKEN);
                var bodies = cb.bodies(ctor, c -> c.super_(Spreads_.new_, (b, self) -> b.end()));
                bodies.define(
                        j1,
                        (b, self) -> b.return_(
                                bodies.superCall(self, Spreads_.join1_StringArray, varargs(STRINGS, literal("a")))));
                bodies.define(
                        j2,
                        (b, self) -> b.return_(bodies.superCall(
                                self, Spreads_.join2_int_StringArray, literal(5), varargs(STRINGS, literal("a")))));
                bodies.define(
                        p1,
                        (b, self) -> b.exec(bodies.superCall(
                                        self, Spreads_.put1_StringArray, varargs(STRINGS, literal("a"), literal("b"))))
                                .return_(asSpreads.apply(self).field(Spreads_.told)));
                bodies.define(
                        p2,
                        (b, self) -> b.exec(bodies.superCall(
                                        self,
                                        Spreads_.put2_int_StringArray,
                                        literal(5),
                                        varargs(STRINGS, literal("a"))))
                                .return_(asSpreads.apply(self).field(Spreads_.told)));
                bodies.define(
                        own,
                        (b, self) -> b.return_(asSpreads
                                .apply(self)
                                .call(Spreads_.join1_StringArray, varargs(STRINGS, literal("q")))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("super.join1(\"a\")")
                .contains("super.join2(5, \"a\")")
                .contains("super.put1(\"a\", \"b\");")
                .contains("this.join1(\"q\")");
        assertThat(compiled.invoke("viaSuper1")).isEqualTo("a");
        assertThat(compiled.invoke("viaSuper2")).isEqualTo("5:a");
        assertThat(compiled.invoke("viaPut1")).isEqualTo("a+b");
        assertThat(compiled.invoke("viaPut2")).isEqualTo("5:a");
        assertThat(compiled.invoke("viaThis")).isEqualTo("q");
    }

    @Test
    void whereJavacMayTakeAnotherMethodTheArrayOfTheArgumentsIsWritten() throws Throwable {
        var strings = new List_<>(String_.TOKEN);
        CompiledClasses compiled = CompiledClasses.of(file("Explicit", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var listed = cb.declareStaticMethod("listed", PrimitiveToken.INT);
                var single = cb.declareStaticMethod("single", PrimitiveToken.INT);
                var triple = cb.declareStaticMethod("triple", PrimitiveToken.INT);
                var none = cb.declareStaticMethod("none", PrimitiveToken.INT);
                var hashed = cb.declareStaticMethod("hashed", PrimitiveToken.INT);
                var counted = cb.declareStaticMethod("counted", PrimitiveToken.INT);
                var lone = cb.declareStaticMethod("lone", PrimitiveToken.INT);
                var bodies = cb.bodies();
                // List.of(E, E) is a choice for javac
                bodies.define(
                        listed,
                        b -> b.return_(
                                staticCall(List_.of_EArray(String_.TOKEN), varargs(STRINGS, literal("a"), literal("b")))
                                        .call(strings.size)));
                // IntStream.of(int) is a choice for one int, and none for three
                bodies.define(
                        single,
                        b -> b.return_(staticCall(IntStream_.of_intArray, varargs(INTS, literal(4)))
                                .call(IntStream_.sum)));
                bodies.define(
                        triple,
                        b -> b.return_(
                                staticCall(IntStream_.of_intArray, varargs(INTS, literal(1), literal(2), literal(3)))
                                        .call(IntStream_.sum)));
                bodies.define(
                        none,
                        b -> b.return_(staticCall(IntStream_.of_intArray, varargs(INTS))
                                .call(IntStream_.sum)));
                bodies.define(
                        hashed,
                        b -> b.return_(
                                staticCall(Objects_.hash_ObjectArray, varargs(OBJECTS, literal("a"), literal("b")))));
                bodies.define(
                        counted,
                        b -> b.return_(new_(Gathered_.new_).call(Gathered_.count_ObjectArray, varargs(OBJECTS))));
                // one element that is an array: the array of the parameter to javac, so it is cast
                bodies.define(
                        lone,
                        b -> b.let(
                                STRINGS,
                                Expressions.newArrayOf(STRINGS, literal("x"), literal("y")),
                                arr -> b.return_(new_(Gathered_.new_)
                                        .call(Gathered_.count_ObjectArray, varargs(OBJECTS, arr)))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("List.<String>of(new String[] {\"a\", \"b\"})")
                .contains("IntStream.of(new int[] {4})")
                .contains("IntStream.of(1, 2, 3)")
                .contains("IntStream.of()")
                .contains("Objects.hash(\"a\", \"b\")")
                .contains("new Gathered().count()")
                .contains("count((Object) v0)");
        assertThat(compiled.invoke("listed")).isEqualTo(2);
        assertThat(compiled.invoke("single")).isEqualTo(4);
        assertThat(compiled.invoke("triple")).isEqualTo(6);
        assertThat(compiled.invoke("none")).isEqualTo(0);
        assertThat(compiled.invoke("lone")).isEqualTo(1);
    }

    @Test
    void anOverloadThatCannotApplyToTheArgumentsIsNoChoiceForJavac() throws Throwable {
        // mix() takes none, and mix(int, int, String[]) more than are given: neither is applicable to ("a"), and
        // rows(String[]...) given one array is the arguments of its parameter, as it is
        CompiledClasses compiled = CompiledClasses.of(file("Mixes", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var mix = cb.declareStaticMethod("mix", String_.TOKEN);
                var rows = cb.declareStaticMethod("rows", PrimitiveToken.INT);
                var pick = cb.declareStaticMethod("pick", String_.TOKEN);
                var none = cb.declareStaticMethod("none", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(
                        mix, b -> b.return_(staticCall(Spreads_.mix_StringArray, varargs(STRINGS, literal("a")))));
                // pick(int, String...) takes the arguments of its arity, and no String for its int
                bodies.define(
                        pick, b -> b.return_(staticCall(Spreads_.pick_StringArray, varargs(STRINGS, literal("a")))));
                // pick(int, String...) takes an int, and takes none
                bodies.define(none, b -> b.return_(staticCall(Spreads_.pick_StringArray, varargs(STRINGS))));
                bodies.define(
                        rows,
                        b -> b.let(
                                STRINGS,
                                Expressions.newArrayOf(STRINGS, literal("x")),
                                row -> b.return_(staticCall(Spreads_.rows_StringArrayArray, varargs(STRINGS_2, row)))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("Spreads.pick(\"a\")")
                .contains("Spreads.pick()")
                .contains("Spreads.mix(\"a\")")
                .contains("Spreads.rows(v0)");
        assertThat(compiled.invoke("mix")).isEqualTo("parts:a");
        assertThat(compiled.invoke("pick")).isEqualTo("parts:a");
        assertThat(compiled.invoke("none")).isEqualTo("parts:");
        assertThat(compiled.invoke("rows")).isEqualTo(1);
    }

    @Test
    void manyElementsOfATypeThatIsNotReifiableAreSpreadWhereNoMethodCompetes() {
        var strings = new List_<>(String_.TOKEN);
        var lists = new List_<>(strings.token);
        ArrayToken<List<String>[], List<String>> listArray = ArrayToken.of(strings.token);
        var a = Expressions.literalNull(strings.token);

        JavacVerdict.asJavacAccepts(
                () -> file("Eleven", new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                m,
                                b -> b.return_(staticCall(
                                                List_.of_EArray(strings.token),
                                                varargs(listArray, a, a, a, a, a, a, a, a, a, a, a))
                                        .call(lists.size)));
                        return bodies;
                    }
                }),
                "static void m(List<String> a) { List<List<String>> l = List.<List<String>>of(a, a, a, a, a, a, a, a, a,"
                        + " a, a); }");
    }

    @Test
    void fewElementsOfATypeThatIsNotReifiableAreRefusedWhereJavacTakesAnotherMethod() {
        var strings = new List_<>(String_.TOKEN);
        ArrayToken<List<String>[], List<String>> listArray = ArrayToken.of(strings.token);
        var a = Expressions.literalNull(strings.token);

        // javac takes of(E, E), whose array is not that of the call
        JavacVerdict.unlikeJavac(
                () -> file("Two", new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                m,
                                b -> b.return_(staticCall(List_.of_EArray(strings.token), varargs(listArray, a, a))
                                        .call(new List_<>(strings.token).size)));
                        return bodies;
                    }
                }),
                "cannot call ",
                "static void m(List<String> a) { List<List<String>> l = List.<List<String>>of(a, a); }");
    }

    @Test
    void aReceiverWhoseMethodsAreNotKnownIsGivenTheArrayOfTheArguments() throws Throwable {
        // a type variable has the methods of its bounds, which the facts of the variable do not list
        CompiledClasses typeVariable = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Bounded"),
                typeParam("T", Gathered_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<Gathered>() {
                    @Override
                    public <Self, T extends Gathered> MemberBodies<Self, ?> build(
                            ClassHeader<Self> h, TypeVarToken<T> t) {
                        var cb = h.extendsObject().members();
                        var constructor = cb.declareConstructor();
                        var go = cb.declareMethod("go", PrimitiveToken.INT, t);
                        var bodies = cb.bodies();
                        bodies.define(constructor, (b, self) -> b.end());
                        bodies.define(
                                go,
                                (b, self, v) -> b.return_(v.call(
                                        Gathered_.count_ObjectArray, varargs(OBJECTS, literal("a"), literal("b")))));
                        return bodies;
                    }
                }));
        assertThat(typeVariable.source()).contains("v0.count(new Object[] {\"a\", \"b\"})");

        // a type whose facts list no method at all: the member is not among them
        var unlisted =
                UnsafeFacts.<Gathered>interfaceToken(Types.of(ClassDesc.of(FIXTURES + "Gathered")), MethodTable.EMPTY);
        CompiledClasses listed = CompiledClasses.of(file("Unlisted", new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                var n = cb.declareStaticMethod("n", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(
                        m,
                        b -> b.return_(new_(Gathered_.new_)
                                .call(Gathered_.count_ObjectArray, varargs(OBJECTS, literal("a")))));
                bodies.define(
                        n,
                        b -> b.return_(literalNull(unlisted)
                                .call(Gathered_.count_ObjectArray, varargs(OBJECTS, literal("a"), literal("b")))));
                return bodies;
            }
        }));
        assertThat(listed.source()).contains("((Gathered) null).count(new Object[] {\"a\", \"b\"})");
    }

    @Test
    void aMemberOfFixedArityTakesNoArgumentsOfAParameterOfVariableArity() {
        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> new_(Gathered_.new_).call(Gathered_.join_StringArray, varargs(STRINGS, literal("a"))),
                "varargs gives the arguments of a parameter of variable arity, and the last parameter of",
                "String m(" + FIXTURES + "Gathered g) { return g.join(\"a\"); }",
                "incompatible types");
    }

    @Test
    void elementsOfATypeThatIsNotReifiableAreRefusedForAMethodThatIsNotSafeVarargs() {
        var strings = new List_<>(String_.TOKEN);
        var scattering = new Scattering_<>(strings.token);
        ArrayToken<List<String>[], List<String>> listArray = ArrayToken.of(strings.token);
        var a = literalNull(strings.token);

        JavacVerdict.asJavacWarns(
                IllegalArgumentException.class,
                () -> literalNull(scattering.token).call(scattering.scatter_TArray, varargs(listArray, a, a)),
                "is of variable arity and not @SafeVarargs, and the arguments of its last parameter are of",
                "int m(" + FIXTURES + "Scattering<List<String>> s, List<String> a) { return s.scatter(a, a); }",
                "unchecked generic array creation");
    }

    @Test
    void aPrimitiveElementOfAReferenceTypeIsRefusedThoughJavacBoxesIt() {
        JavacVerdict.unlikeJavac(
                IllegalArgumentException.class,
                () -> varargs(OBJECTS, literal(1)),
                "varargs takes a java.lang.Object and is given a value of the primitive type int",
                "int m(" + FIXTURES + "Gathered g) { return g.count(1); }");
    }

    @Test
    void aLoneElementJavacWouldTypeAsTheArrayOfTheElementTypeIsPinnedToOneElement() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(declared("Lone", new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                // Origin.get() is instanceof of the class as String[], and the fact of the interface as Object:
                // javac types the call by the narrower result, an array of the element type
                var get = h.overriding(Origin_.get, STRINGS);
                var oh = h.extendsObject();
                Upcast<Self, Origin> asOrigin =
                        oh.implements_(Origin_.implement(h.self()).get(get));
                var cb = oh.members();
                var ctor = cb.declareConstructor();
                var m = cb.declareMethod("m", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(ctor, (b, self) -> b.end());
                bodies.define(
                        get.method(),
                        (b, self) -> b.return_(Expressions.newArrayOf(STRINGS, literal("mine"), literal("yours"))));
                bodies.define(
                        m,
                        (b, self) -> b.return_(new_(Gathered_.new_)
                                .call(
                                        Gathered_.count_ObjectArray,
                                        varargs(OBJECTS, asOrigin.apply(self).call(Origin_.get)))));
                return bodies;
            }
        }));

        assertThat(compiled.source()).isEqualTo("""
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.Gathered;
                        import me.supcheg.javafile.typed.fixtures.Origin;

                        public final class Lone implements Origin {
                            @Override
                            public String[] get() {
                                return new String[] {"mine", "yours"};
                            }

                            public Lone() {
                            }

                            public int m() {
                                return new Gathered().count(((Origin) this).get());
                            }
                        }
                        """);
        assertThat(compiled.invoke("m")).isEqualTo(1);
    }

    @Test
    void anElementThatThrowsACheckedExceptionIsThatOfTheStatement() {
        var read = box(
                PrimitiveToken.INT, new_(StringReader_.new_String, literal("a")).call(StringReader_.read));

        JavacVerdict.asJavac(
                () -> file("Throws", new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var m = cb.declareStaticMethod("m", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.define(
                                m,
                                b -> b.return_(new_(Gathered_.new_)
                                        .call(Gathered_.count_ObjectArray, varargs(OBJECTS, read))));
                        return bodies;
                    }
                }),
                "can throw the checked exception java.io.IOException",
                "static int m() { return new " + FIXTURES + "Gathered().count(Integer.valueOf("
                        + "new StringReader(\"a\").read())); }",
                "unreported exception java.io.IOException");
    }
}
