package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.CD_String;

/// Hand-written classes the generated code of the fact-pinning tests is
/// compiled with, and their facts: overloads, a hidden field, a covariant
/// override, and statics of a generic class — each a place where javac
/// picks another member than the fact unless lowering pins it.
final class Fixtures {
    private Fixtures() {}

    /// Phantom of `fixtures.Base`.
    interface BaseP {}

    /// Phantom of `fixtures.Sub`, a subclass of `Base`.
    interface SubP extends BaseP {}

    /// Phantom of `fixtures.Box<T>`.
    interface BoxP<T> {}

    static final String BASE_NAME = "fixtures.Base";
    static final String SUB_NAME = "fixtures.Sub";

    /// Binary names and sources, as [CompiledClasses#of] takes them.
    static final String[] SOURCES = {BASE_NAME, """
        package fixtures;

        public class Base {
            public String name = "base";

            public String pick(Object o) {
                return "Object";
            }

            public String pick(String s) {
                return "String";
            }

            public String only(Object o) {
                return "only";
            }

            public Base self() {
                return this;
            }

            public String dual(Object o) {
                return "instance";
            }

            public static String dual(String s) {
                return "static";
            }

            public static String which(Object o) {
                return "Object";
            }

            public static String which(String s) {
                return "String";
            }

            public static String which2(Base b) {
                return "Base";
            }

            public static String which2(Sub s) {
                return "Sub";
            }
        }
        """, SUB_NAME, """
        package fixtures;

        public class Sub extends Base {
            public String name = "sub";

            @Override
            public Sub self() {
                return this;
            }
        }
        """, "fixtures.Box", """
        package fixtures;

        import java.util.List;

        public final class Box<T> {
            public static String label = "box";

            public static <E> List<E> none() {
                return List.of();
            }
        }
        """};

    private static final ClassDesc BASE_DESC = ClassDesc.of("fixtures", "Base");
    private static final ClassDesc SUB_DESC = ClassDesc.of("fixtures", "Sub");
    private static final ClassDesc BOX_DESC = ClassDesc.of("fixtures", "Box");

    /// The methods of `Base`, and so of `Sub`, which overrides `self` only.
    /// `dual` is an instance and a `static` overload of the same arity.
    private static final MethodTable BASE_METHODS = new MethodTable(
            Set.of(),
            Set.of(
                    new MethodSignature("pick", List.of(CD_Object)),
                    new MethodSignature("pick", List.of(CD_String)),
                    new MethodSignature("only", List.of(CD_Object)),
                    new MethodSignature("dual", List.of(CD_Object)),
                    new MethodSignature("self", List.of()),
                    new MethodSignature("equals", List.of(CD_Object)),
                    new MethodSignature("hashCode", List.of()),
                    new MethodSignature("toString", List.of())),
            Set.of(
                    new MethodSignature("dual", List.of(CD_String)),
                    new MethodSignature("which", List.of(CD_Object)),
                    new MethodSignature("which", List.of(CD_String)),
                    new MethodSignature("which2", List.of(BASE_DESC)),
                    new MethodSignature("which2", List.of(SUB_DESC))));

    static final OpenClassToken<BaseP> BASE =
            UnsafeFacts.openClassToken(Types.of(BASE_DESC), List.of(CD_Object), BASE_METHODS);
    static final OpenClassToken<SubP> SUB =
            UnsafeFacts.openClassToken(Types.of(SUB_DESC), List.of(BASE_DESC, CD_Object), BASE_METHODS);

    static final CtorRef0<SubP> NEW_SUB = UnsafeFacts.ctor(SUB, MemberTraits.DEFAULT);

    static final MethodRef1<BaseP, String, Object> PICK_OBJECT =
            UnsafeFacts.method(BASE, "pick", String_.TOKEN, Object_.TOKEN, MemberTraits.DEFAULT);
    static final MethodRef1<BaseP, String, String> PICK_STRING =
            UnsafeFacts.method(BASE, "pick", String_.TOKEN, String_.TOKEN, MemberTraits.DEFAULT);
    static final MethodRef1<BaseP, String, Object> ONLY =
            UnsafeFacts.method(BASE, "only", String_.TOKEN, Object_.TOKEN, MemberTraits.DEFAULT);
    static final MethodRef1<BaseP, String, Object> DUAL =
            UnsafeFacts.method(BASE, "dual", String_.TOKEN, Object_.TOKEN, MemberTraits.DEFAULT);
    static final MethodRef0<BaseP, BaseP> SELF = UnsafeFacts.method(BASE, "self", BASE, MemberTraits.DEFAULT);

    static final MutableFieldRef<BaseP, String> NAME_OF_BASE = UnsafeFacts.mutableField(BASE, "name", String_.TOKEN);
    static final MutableFieldRef<SubP, String> NAME_OF_SUB = UnsafeFacts.mutableField(SUB, "name", String_.TOKEN);

    static final StaticMethodRef1<String, Object> WHICH_OBJECT =
            UnsafeFacts.staticMethod(BASE, "which", String_.TOKEN, Object_.TOKEN, MemberTraits.DEFAULT);
    static final StaticMethodRef1<String, String> WHICH_STRING =
            UnsafeFacts.staticMethod(BASE, "which", String_.TOKEN, String_.TOKEN, MemberTraits.DEFAULT);
    static final StaticMethodRef1<String, BaseP> WHICH2_BASE =
            UnsafeFacts.staticMethod(BASE, "which2", String_.TOKEN, BASE, MemberTraits.DEFAULT);

    /// `Box<String>`: its statics are rendered through the raw `Box`.
    static final FinalClassToken<BoxP<String>> BOX_OF_STRING = UnsafeFacts.finalClassToken(
            Types.parameterized(BOX_DESC, Types.STRING), List.of(CD_Object), MethodTable.EMPTY);

    static final MutableStaticFieldRef<String> LABEL =
            UnsafeFacts.mutableStaticField(BOX_OF_STRING, "label", String_.TOKEN);

    /// `static <E> List<E> none()`, with `String` for `E`.
    static final StaticMethodRef0<List<String>> NONE = UnsafeFacts.staticMethod(
            BOX_OF_STRING, "none", new List_<>(String_.TOKEN).token, MemberTraits.DEFAULT.withTypeArgs(String_.TOKEN));
}
