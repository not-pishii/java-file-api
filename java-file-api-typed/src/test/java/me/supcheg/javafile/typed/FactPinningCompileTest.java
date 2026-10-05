package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.io.PrintStream_;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.java.util.stream.Stream_;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.box;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static me.supcheg.javafile.typed.Expressions.unbox;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.Fixtures.BASE;
import static me.supcheg.javafile.typed.Fixtures.DUAL;
import static me.supcheg.javafile.typed.Fixtures.LABEL;
import static me.supcheg.javafile.typed.Fixtures.NAME_OF_BASE;
import static me.supcheg.javafile.typed.Fixtures.NAME_OF_SUB;
import static me.supcheg.javafile.typed.Fixtures.NEW_SUB;
import static me.supcheg.javafile.typed.Fixtures.NONE;
import static me.supcheg.javafile.typed.Fixtures.ONLY;
import static me.supcheg.javafile.typed.Fixtures.PICK_OBJECT;
import static me.supcheg.javafile.typed.Fixtures.PICK_STRING;
import static me.supcheg.javafile.typed.Fixtures.SELF;
import static me.supcheg.javafile.typed.Fixtures.SUB;
import static me.supcheg.javafile.typed.Fixtures.WHICH2_BASE;
import static me.supcheg.javafile.typed.Fixtures.WHICH_OBJECT;
import static me.supcheg.javafile.typed.Fixtures.WHICH_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// End-to-end tests of "lowering pins the chosen fact" (§6.1, §10): the
/// generated code is compiled with `-Xlint:all` — no error, no warning, so
/// no redundant cast either — and run, and each test observes which member
/// the running code reached. Without the pinning, javac would pick another
/// overload, another field, or infer other type arguments.
class FactPinningCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Pinned");

    private static CompiledClasses compile(TypedJavaFile.TypedClassSpec spec) {
        JavaFile file = TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, spec);
        return CompiledClasses.of(file, Fixtures.SOURCES);
    }

    // ------------------------------------------------------------------
    // M1: overloads
    // ------------------------------------------------------------------

    @Test
    void anArgumentIsCastToTheParameterOfTheChosenOverload() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod(
                        "pickObject", String_.TOKEN, SUB, (b, s) -> b.return_(call(s, PICK_OBJECT, literal("x"))));
                cb.staticMethod(
                        "pickString", String_.TOKEN, SUB, (b, s) -> b.return_(call(s, PICK_STRING, literal("x"))));
                // the method table of Sub proves `only` has no other overload: no cast
                cb.staticMethod("only", String_.TOKEN, SUB, (b, s) -> b.return_(call(s, ONLY, literal("x"))));
                // a static method has no table to prove it by
                cb.staticMethod("whichObject", String_.TOKEN, b -> b.return_(staticCall(WHICH_OBJECT, literal("x"))));
                cb.staticMethod("whichString", String_.TOKEN, b -> b.return_(staticCall(WHICH_STRING, literal("x"))));
            }
        });
        Object sub = compiled.instantiate(Fixtures.SUB_NAME);

        assertThat(compiled.source())
                .contains(".pick((Object) \"x\")")
                .contains(".pick(\"x\")")
                .contains(".only(\"x\")")
                .contains("Base.which((Object) \"x\")")
                .contains("Base.which(\"x\")");
        assertThat(compiled.invoke("pickObject", sub)).isEqualTo("Object");
        assertThat(compiled.invoke("pickString", sub)).isEqualTo("String");
        assertThat(compiled.invoke("only", sub)).isEqualTo("only");
        assertThat(compiled.invoke("whichObject")).isEqualTo("Object");
        assertThat(compiled.invoke("whichString")).isEqualTo("String");
    }

    @Test
    void anInstanceCallKeepsItsCastWhenAStaticOverloadOfTheSameArityExists() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                // Base declares the instance dual(Object) and the static dual(String): javac
                // considers both for `sub.dual("x")` and picks the static one, unless cast
                cb.staticMethod("dual", String_.TOKEN, SUB, (b, s) -> b.return_(call(s, DUAL, literal("x"))));
            }
        });
        Object sub = compiled.instantiate(Fixtures.SUB_NAME);

        assertThat(compiled.source()).contains(".dual((Object) \"x\")");
        assertThat(compiled.invoke("dual", sub)).isEqualTo("instance");
    }

    @Test
    void listRemoveOfAnObjectIsNotRemoveAtAnIndex() throws Throwable {
        List_<Integer> integers = new List_<>(Integer_.TOKEN);
        MethodRef1<List<Integer>, Prim.Bool, Object> removeObject = UnsafeFacts.method(
                integers.token, "remove", PrimitiveToken.BOOLEAN, Object_.TOKEN, MemberTraits.DEFAULT);
        MethodRef1<List<Integer>, Integer, Prim.Int> removeAt =
                UnsafeFacts.method(integers.token, "remove", Integer_.TOKEN, PrimitiveToken.INT, MemberTraits.DEFAULT);

        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod(
                        "removeValue",
                        PrimitiveToken.INT,
                        integers.token,
                        (b, l) -> b.exec(call(l, removeObject, literal(1)))
                                .return_(unbox(PrimitiveToken.INT, call(l, integers.get_int, literal(0)))));
                cb.staticMethod(
                        "removeBoxed",
                        PrimitiveToken.INT,
                        integers.token,
                        (b, l) -> b.exec(call(l, removeObject, box(PrimitiveToken.INT, literal(1))))
                                .return_(unbox(PrimitiveToken.INT, call(l, integers.get_int, literal(0)))));
                cb.staticMethod(
                        "removeIndex",
                        PrimitiveToken.INT,
                        integers.token,
                        (b, l) -> b.exec(call(l, removeAt, literal(1)))
                                .return_(unbox(PrimitiveToken.INT, call(l, integers.get_int, literal(0)))));
            }
        });

        assertThat(compiled.source())
                .contains(".remove((Object) 1)")
                .contains(".remove((Object) Integer.valueOf(1))")
                .contains(".remove(1)");
        assertThat(compiled.invoke("removeValue", new ArrayList<>(List.of(1, 7))))
                .isEqualTo(7);
        assertThat(compiled.invoke("removeBoxed", new ArrayList<>(List.of(1, 7))))
                .isEqualTo(7);
        assertThat(compiled.invoke("removeIndex", new ArrayList<>(List.of(1, 7))))
                .isEqualTo(1);
    }

    @Test
    void printlnOfAnObjectIsNotPrintlnOfAString() throws Throwable {
        VoidMethodRef1<PrintStream, Object> printlnObject =
                UnsafeFacts.voidMethod(PrintStream_.TOKEN, "println", Object_.TOKEN, MemberTraits.DEFAULT);

        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.voidStaticMethod(
                        "printObject",
                        PrintStream_.TOKEN,
                        (b, out) -> b.exec(voidCall(out, printlnObject, literal("x")))
                                .end());
                cb.voidStaticMethod(
                        "printString",
                        PrintStream_.TOKEN,
                        (b, out) -> b.exec(voidCall(out, PrintStream_.println_String, literal("x")))
                                .end());
            }
        });
        Sink sink = new Sink();
        compiled.invoke("printObject", sink);
        compiled.invoke("printString", sink);

        assertThat(compiled.source()).contains(".println((Object) \"x\")").contains(".println(\"x\")");
        assertThat(sink.calls).containsExactly("println(Object)", "println(String)");
    }

    @Test
    void overloadsOfTheClassBeingDeclaredArePinnedOnceItsTableIsComplete() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                var mObject = cb.declareMethod("m", String_.TOKEN, Object_.TOKEN);
                var single = cb.declareMethod("single", String_.TOKEN, Object_.TOKEN);
                cb.method("callObject", String_.TOKEN, (b, self) -> b.return_(call(self, mObject, literal("x"))));
                cb.method("callSingle", String_.TOKEN, (b, self) -> b.return_(call(self, single, literal("x"))));
                // declared after the body calling m(Object) was built
                var mString = cb.declareMethod("m", String_.TOKEN, String_.TOKEN);
                cb.define(mObject, (b, _, _) -> b.return_(literal("m(Object)")));
                cb.define(mString, (b, _, _) -> b.return_(literal("m(String)")));
                cb.define(single, (b, _, _) -> b.return_(literal("single(Object)")));
            }
        });

        assertThat(compiled.source()).contains("this.m((Object) \"x\")").contains("this.single(\"x\")");
        assertThat(compiled.invoke("callObject")).isEqualTo("m(Object)");
        assertThat(compiled.invoke("callSingle")).isEqualTo("single(Object)");
    }

    // ------------------------------------------------------------------
    // M1: fields and inexact types
    // ------------------------------------------------------------------

    @Test
    void aFieldIsReachedThroughItsOwnerNotAFieldHidingIt() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod("readBase", String_.TOKEN, SUB, (b, s) -> b.return_(field(s, NAME_OF_BASE)));
                cb.staticMethod("readSub", String_.TOKEN, SUB, (b, s) -> b.return_(field(s, NAME_OF_SUB)));
                cb.staticMethod(
                        "writeBase",
                        String_.TOKEN,
                        SUB,
                        (b, s) -> b.exec(assignField(s, NAME_OF_BASE, literal("written")))
                                .return_(field(s, NAME_OF_BASE)));
                cb.staticMethod("readNew", String_.TOKEN, b -> b.return_(field(new_(NEW_SUB), NAME_OF_BASE)));
            }
        });
        Object sub = compiled.instantiate(Fixtures.SUB_NAME);

        assertThat(compiled.source())
                .contains("((Base) v0).name")
                .contains("((Base) v0).name = \"written\"")
                .contains("((Base) new Sub()).name");
        assertThat(compiled.invoke("readBase", sub)).isEqualTo("base");
        assertThat(compiled.invoke("readSub", sub)).isEqualTo("sub");
        assertThat(compiled.invoke("writeBase", sub)).isEqualTo("written");
        assertThat(compiled.invoke("readSub", sub)).isEqualTo("sub");
        assertThat(compiled.invoke("readNew")).isEqualTo("base");
    }

    @Test
    void aCovariantResultIsPinnedWhereItsTypeDecidesTheOverload() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                // Sub overrides `Base self()` with `Sub self()`: unpinned, javac would pick which2(Sub)
                cb.staticMethod(
                        "which2", String_.TOKEN, SUB, (b, s) -> b.return_(staticCall(WHICH2_BASE, call(s, SELF))));
                // where the type does not matter, nothing is cast
                cb.staticMethod("self", BASE, SUB, (b, s) -> b.return_(call(s, SELF)));
            }
        });
        Object sub = compiled.instantiate(Fixtures.SUB_NAME);

        assertThat(compiled.source())
                .contains("Base.which2(((Base) v0).self())")
                .contains("return v0.self();");
        assertThat(compiled.invoke("which2", sub)).isEqualTo("Base");
        assertThat(compiled.invoke("self", sub)).isSameAs(sub);
    }

    // ------------------------------------------------------------------
    // B5: null
    // ------------------------------------------------------------------

    @Test
    void nullIsAlwaysTyped() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod(
                        "nullLength",
                        PrimitiveToken.INT,
                        b -> b.return_(call(literalNull(String_.TOKEN), String_.length)));
                cb.staticMethod(
                        "nulls",
                        String_.TOKEN,
                        b -> b.return_(concat(literalNull(String_.TOKEN), literalNull(String_.TOKEN))));
                cb.staticMethod("nullField", String_.TOKEN, b -> b.return_(field(literalNull(BASE), NAME_OF_BASE)));
                cb.staticMethod(
                        "whichNull",
                        String_.TOKEN,
                        b -> b.return_(staticCall(WHICH_STRING, literalNull(String_.TOKEN))));
                cb.staticMethod(
                        "whichNullObject",
                        String_.TOKEN,
                        b -> b.return_(staticCall(WHICH_OBJECT, literalNull(Object_.TOKEN))));
                cb.staticMethod(
                        "pickNull",
                        String_.TOKEN,
                        SUB,
                        (b, s) -> b.return_(call(s, PICK_OBJECT, literalNull(String_.TOKEN))));
                cb.staticMethod("returnNull", String_.TOKEN, b -> b.return_(literalNull(String_.TOKEN)));
            }
        });
        Object sub = compiled.instantiate(Fixtures.SUB_NAME);

        assertThat(compiled.source())
                .contains("((String) null).length()")
                .contains("(String) null + (String) null")
                .contains("((Base) null).name")
                .contains("Base.which((String) null)")
                .contains("Base.which((Object) null)")
                .contains(".pick((Object) (String) null)")
                .contains("return (String) null;");
        assertThatThrownBy(() -> compiled.invoke("nullLength")).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> compiled.invoke("nullField")).isInstanceOf(NullPointerException.class);
        assertThat(compiled.invoke("nulls")).isEqualTo("nullnull");
        assertThat(compiled.invoke("whichNull")).isEqualTo("String");
        assertThat(compiled.invoke("whichNullObject")).isEqualTo("Object");
        assertThat(compiled.invoke("pickNull", sub)).isEqualTo("Object");
        assertThat(compiled.invoke("returnNull")).isNull();
    }

    // ------------------------------------------------------------------
    // B6: statics of a generic type, explicit witnesses
    // ------------------------------------------------------------------

    @Test
    void staticsOfAGenericTypeAreQualifiedByTheRawTypeWithExplicitWitnesses() throws Throwable {
        List_<String> strings = new List_<>(String_.TOKEN);
        var map = new Stream_<>(String_.TOKEN).map_Function(Integer_.TOKEN);
        Stream_<Integer> integers = new Stream_<>(Integer_.TOKEN);
        List_<Integer> integerList = new List_<>(Integer_.TOKEN);

        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod("empty", strings.token, b -> b.return_(staticCall(List_.of(String_.TOKEN))));
                // the review's probe E: rendered `List<String>.of().size()`
                cb.staticMethod(
                        "size",
                        PrimitiveToken.INT,
                        b -> b.return_(call(staticCall(List_.of(String_.TOKEN)), strings.size)));
                cb.staticMethod(
                        "one", strings.token, b -> b.return_(staticCall(List_.of_E(String_.TOKEN), literal("a"))));
                cb.staticMethod("none", strings.token, b -> b.return_(staticCall(Fixtures.NONE)));
                cb.staticMethod("label", String_.TOKEN, b -> b.return_(staticField(LABEL)));
                cb.voidStaticMethod(
                        "relabel",
                        b -> b.exec(assignStaticField(LABEL, literal("relabeled")))
                                .end());
                cb.method(
                        "lengths",
                        integerList.token,
                        new Stream_<>(String_.TOKEN).token,
                        map.param1(),
                        (b, _, s, f) -> b.return_(call(call(s, map, f), integers.toList)));
            }
        });

        assertThat(compiled.source())
                .contains("List.<String>of()")
                .contains("List.<String>of(\"a\")")
                .contains("List.<String>of().size()")
                .contains("Box.<String>none()")
                .contains("return Box.label;")
                .contains("Box.label = \"relabeled\";")
                .contains(".<Integer>map(v1).toList()")
                .doesNotContain("List<String>.")
                .doesNotContain("Box<String>.");
        assertThat(compiled.invoke("empty")).isEqualTo(List.of());
        assertThat(compiled.invoke("one")).isEqualTo(List.of("a"));
        assertThat(compiled.invoke("none")).isEqualTo(List.of());
        assertThat(compiled.invoke("label")).isEqualTo("box");
        compiled.invoke("relabel");
        assertThat(compiled.invoke("label")).isEqualTo("relabeled");
        Function<String, Integer> length = String::length;
        assertThat(compiled.invoke("lengths", Stream.of("a", "bcd"), length)).isEqualTo(List.of(1, 3));
        assertThat(NONE.traits().typeArgs()).containsExactly(String_.TOKEN);
    }

    // ------------------------------------------------------------------
    // B12: the diamond
    // ------------------------------------------------------------------

    @Test
    void theDiamondStandsOnlyWhereTheTargetTypeIsTheConstructedType() throws Throwable {
        ArrayList_<String> arrayList = new ArrayList_<>(String_.TOKEN);
        List_<String> strings = new List_<>(String_.TOKEN);

        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                var items = cb.field("items", arrayList.token, new_(arrayList.new_));
                cb.staticMethod(
                        "local",
                        PrimitiveToken.INT,
                        b -> b.let(arrayList.token, new_(arrayList.new_), v -> b.return_(call(v, strings.size))));
                cb.staticMethod("returned", arrayList.token, b -> b.return_(new_(arrayList.new_)));
                cb.staticMethod("upcast", strings.token, b -> b.return_(new_(arrayList.new_)));
                cb.staticMethod(
                        "receiver", PrimitiveToken.INT, b -> b.return_(call(new_(arrayList.new_), strings.size)));
                cb.staticMethod(
                        "wider",
                        PrimitiveToken.INT,
                        b -> b.let(strings.token, new_(arrayList.new_), v -> b.return_(call(v, strings.size))));
                cb.staticMethod("object", Object_.TOKEN, b -> b.return_(new_(Object_.new_)));
                cb.method("items", arrayList.token, (b, self) -> b.return_(field(self, items)));
            }
        });

        assertThat(compiled.source())
                .contains("ArrayList<String> items = new ArrayList<>();")
                .contains("ArrayList<String> v0 = new ArrayList<>();")
                .contains("return new ArrayList<>();")
                .contains("return new ArrayList<String>();")
                .contains("new ArrayList<String>().size()")
                .contains("List<String> v0 = new ArrayList<String>();")
                .contains("return new Object();")
                .doesNotContain("Object<>");
        assertThat(compiled.invoke("local")).isEqualTo(0);
        assertThat(compiled.invoke("returned")).isEqualTo(List.of());
        assertThat(compiled.invoke("items")).isEqualTo(List.of());
    }

    // ------------------------------------------------------------------
    // B13: reifiable types
    // ------------------------------------------------------------------

    @Test
    void castsInstanceofAndArraysOfReifiableTypesCompileWithoutUncheckedWarnings() throws Throwable {
        InterfaceToken<List<?>> anyList = UnsafeFacts.interfaceToken(
                Types.parameterized(ConstantDescs.CD_List, Types.unbounded()), MethodTable.EMPTY);
        ArrayToken<List<?>[], List<?>> anyLists = ArrayToken.of(anyList);

        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod(
                        "cast",
                        anyList,
                        Object_.TOKEN,
                        (b, o) ->
                                b.ifInstanceOfElse(o, anyList, Body::return_, e -> e.return_(castChecked(anyList, o))));
                cb.staticMethod(
                        "lists", PrimitiveToken.INT, b -> b.return_(length(anyLists, newArray(anyLists, literal(2)))));
                // castChecked to the operand's own type casts nothing
                cb.staticMethod(
                        "same", String_.TOKEN, String_.TOKEN, (b, s) -> b.return_(castChecked(String_.TOKEN, s)));
            }
        });

        assertThat(compiled.source())
                .contains("instanceof List<?> v1")
                .contains("(List<?>) v0")
                .contains("new List<?>[2]")
                .contains("return v0;");
        assertThat(compiled.invoke("cast", List.of(1))).isEqualTo(List.of(1));
        assertThatThrownBy(() -> compiled.invoke("cast", "no list")).isInstanceOf(ClassCastException.class);
        assertThat(compiled.invoke("lists")).isEqualTo(2);
    }

    @Test
    void castsAndInstanceofToParameterizedSubtypesDeterminedByTheOperandCompileWithoutUncheckedWarnings()
            throws Throwable {
        List_<String> strings = new List_<>(String_.TOKEN);
        ArrayList_<String> arrayOfStrings = new ArrayList_<>(String_.TOKEN);
        InterfaceToken<java.util.Collection<String>> collectionOfStrings = UnsafeFacts.interfaceToken(
                Types.parameterized(ConstantDescs.CD_Collection, Types.STRING), MethodTable.EMPTY);

        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                cb.staticMethod(
                        "narrow",
                        arrayOfStrings.token,
                        strings.token,
                        (b, l) -> b.return_(castChecked(arrayOfStrings.token, l)));
                cb.staticMethod(
                        "size",
                        PrimitiveToken.INT,
                        strings.token,
                        (b, l) -> b.ifInstanceOfElse(
                                l,
                                arrayOfStrings.token,
                                (t, a) -> t.return_(call(a, strings.size)),
                                e -> e.return_(literal(-1))));
                cb.staticMethod(
                        "list", strings.token, collectionOfStrings, (b, c) -> b.return_(castChecked(strings.token, c)));
            }
        });

        assertThat(compiled.source())
                .contains("(ArrayList<String>) v0")
                .contains("v0 instanceof ArrayList<String> v1")
                .contains("(List<String>) v0");
        ArrayList<String> items = new ArrayList<>(List.of("a", "b"));
        assertThat(compiled.invoke("narrow", items)).isSameAs(items);
        assertThatThrownBy(() -> compiled.invoke("narrow", List.of("a"))).isInstanceOf(ClassCastException.class);
        assertThat(compiled.invoke("size", items)).isEqualTo(2);
        assertThat(compiled.invoke("size", List.of("a"))).isEqualTo(-1);
        assertThat(compiled.invoke("list", items)).isSameAs(items);
        assertThatThrownBy(() -> compiled.invoke("list", java.util.Set.of("a"))).isInstanceOf(ClassCastException.class);
    }

    // ------------------------------------------------------------------
    // M2: cond
    // ------------------------------------------------------------------

    @Test
    void condIsOfExactlyItsTypeWithoutBoxingOrPromotion() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                // Integer on both sides: no unboxing of the null branch
                cb.staticMethod(
                        "maybe",
                        Integer_.TOKEN,
                        PrimitiveToken.BOOLEAN,
                        (b, flag) -> b.return_(cond(
                                flag,
                                box(PrimitiveToken.INT, literal(1)),
                                literalNull(Integer_.TOKEN),
                                Integer_.TOKEN)));
                // different reference types: each cast to the type of the conditional
                cb.staticMethod(
                        "either",
                        Object_.TOKEN,
                        PrimitiveToken.BOOLEAN,
                        (b, flag) -> b.return_(
                                cond(flag, literal("a"), box(PrimitiveToken.INT, literal(1)), Object_.TOKEN)));
                // a conditional argument selects the overload of its type
                cb.staticMethod(
                        "which",
                        String_.TOKEN,
                        PrimitiveToken.BOOLEAN,
                        (b, flag) -> b.return_(
                                staticCall(WHICH_STRING, cond(flag, literal("a"), literal("b"), String_.TOKEN))));
            }
        });

        assertThat(compiled.source())
                .contains("v0 ? Integer.valueOf(1) : (Integer) null")
                .contains("v0 ? (Object) \"a\" : (Object) Integer.valueOf(1)")
                .contains("Base.which(v0 ? \"a\" : \"b\")");
        assertThat(compiled.invoke("maybe", false)).isNull();
        assertThat(compiled.invoke("maybe", true)).isEqualTo(1);
        assertThat(compiled.invoke("either", true)).isEqualTo("a");
        assertThat(compiled.invoke("either", false)).isEqualTo(1);
        assertThat(compiled.invoke("which", true)).isEqualTo("String");
    }

    // ------------------------------------------------------------------
    // Constant folding still matches javac
    // ------------------------------------------------------------------

    @Test
    void castsOfPinningLeaveConstantFoldingAsJavacHasIt() throws Throwable {
        CompiledClasses compiled = compile(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                // "a".equals((Object) true): the cast lowering adds is in a call, never a constant
                cb.staticMethod(
                        "call",
                        PrimitiveToken.INT,
                        b -> b.while_(call(literal("a"), Object_.equals_Object, literal(true)), (_, _) -> {})
                                .return_(literal(1)));
                // a conditional cast to CharSequence is not a constant expression (JLS 15.29)
                cb.staticMethod(
                        "charSequence",
                        PrimitiveToken.INT,
                        b -> b.while_(
                                        eqRef(
                                                cond(literal(true), literal("a"), literal("b"), CharSequence_.TOKEN),
                                                literal("a")),
                                        Body::break_)
                                .return_(literal(2)));
                // (String) null is not a constant either
                cb.staticMethod(
                        "nulls",
                        PrimitiveToken.INT,
                        b -> b.while_(eqRef(literalNull(String_.TOKEN), literalNull(String_.TOKEN)), Body::break_)
                                .return_(literal(3)));
            }
        });

        assertThat(compiled.source())
                .contains("\"a\".equals((Object) true)")
                .contains("(true ? (CharSequence) \"a\" : (CharSequence) \"b\") == \"a\"");
        assertThat(compiled.invoke("charSequence")).isEqualTo(2);
        assertThat(compiled.invoke("nulls")).isEqualTo(3);
    }

    /// Records which `println` overload was called.
    private static final class Sink extends PrintStream {
        final List<String> calls = new ArrayList<>();

        Sink() {
            super(OutputStream.nullOutputStream());
        }

        @Override
        public void println(Object x) {
            calls.add("println(Object)");
        }

        @Override
        public void println(String x) {
            calls.add("println(String)");
        }
    }
}
