package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `enums` (mini-spec §9.2): constants, `values` and `valueOf`, an
/// enum with constant bodies, an enum with an interface; inherited members
/// are reached through `Enum` (Q6(b)), but the method table has them.
class EnumsFixtureTest extends FixtureSupport {
    private static final String DAY = """
            package p;
            public enum Day {
                MON, TUE, WED;
                public static final int COUNT = 3;
                public Day next() { return null; }
                public static Day of(int i) { return null; }
            }
            """;
    private static final String OP = """
            package p;
            public enum Op {
                ADD { public int apply(int a, int b) { return a + b; } },
                SUB { public int apply(int a, int b) { return a - b; } };
                public abstract int apply(int a, int b);
                public int twice(int a) { return 0; }
            }
            """;
    private static final String TOK = """
            package p;
            public enum Tok implements Runnable {
                TOKEN, sam, token;
                public void run() {}
            }
            """;

    @Test
    void anEnumHasAFactPerConstantAndPerDeclaredMember() throws Exception {
        Compilation compilation = generate("p.Day.class", DAY);
        ClassLoader loader = load(compilation);
        String day = "gen.facts.p.Day_";

        EnumToken<?> token = (EnumToken<?>) token(loader, day);
        assertThat(token.constants()).containsExactly("MON", "TUE", "WED");
        assertThat(shape(loader, day).enumConstants()).containsExactly("MON", "TUE", "WED");
        assertThat(factNames(loader, day))
                .containsExactly("COUNT", "MON", "TUE", "WED", "next", "of_int", "valueOf_String", "values");
        for (String constant : List.of("MON", "TUE", "WED")) {
            EnumConstant<?> fact = (EnumConstant<?>) fact(loader, day, constant);
            assertThat(fact.name()).isEqualTo(constant);
            assertThat(fact.owner()).isSameAs(token);
        }
        assertThat(((StaticFieldRef<?>) fact(loader, day, "COUNT")).constantValue())
                .contains(3);
        assertThat(((Invocable) fact(loader, day, "values"))
                        .resultType()
                        .orElseThrow()
                        .typeRef())
                .isEqualTo(Types.array(Types.of(ClassDesc.of("p.Day"))));
        assertThat(((Invocable) fact(loader, day, "valueOf_String")).traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) fact(loader, day, "of_int")).params().get(0)).isSameAs(PrimitiveToken.INT);
        assertThat(((Invocable) fact(loader, day, "of_int")).resultType().orElseThrow())
                .isEqualTo(token);
        // the enum has no constant bodies: it is final, and so are its methods
        assertThat(((Invocable) fact(loader, day, "next")).traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(sources(compilation).get(day))
                .containsSubsequence("EnumConstant<Day> MON", "EnumConstant<Day> TUE", "EnumConstant<Day> WED");
        assertThat(sources(compilation).get(day)).contains("TOKEN.constant(\"MON\")");
    }

    @Test
    void theMethodTableOfAnEnumHasEveryMethodItInherits() throws Exception {
        ClassLoader loader = load(generate("p.Day.class", DAY));
        var methods = token(loader, "gen.facts.p.Day_").methods();

        // ordinal() is not a fact of Day_, but the overload decision needs it
        assertThat(methods.concreteMethods())
                .contains(
                        new MethodSignature("ordinal", List.of()),
                        new MethodSignature("name", List.of()),
                        new MethodSignature("next", List.of()),
                        new MethodSignature("compareTo", List.of(ClassDesc.of("p.Day"))));
        assertThat(methods.staticMethods())
                .contains(
                        new MethodSignature("values", List.of()),
                        new MethodSignature("valueOf", List.of(ConstantDescs.CD_String)),
                        new MethodSignature("of", List.of(ConstantDescs.CD_int)),
                        new MethodSignature("valueOf", List.of(ConstantDescs.CD_Class, ConstantDescs.CD_String)));
    }

    @Test
    void anEnumWithConstantBodiesIsNotFinal() throws Exception {
        ClassLoader loader = load(generate("p.Op.class", OP));
        String op = "gen.facts.p.Op_";

        assertThat(token(loader, op)).isInstanceOf(EnumToken.class);
        assertThat(shape(loader, op).enumConstants()).containsExactly("ADD", "SUB");
        assertThat(factNames(loader, op))
                .containsExactly("ADD", "SUB", "apply_int_int", "twice_int", "valueOf_String", "values");
        assertThat(((Invocable) fact(loader, op, "apply_int_int")).traits()).isEqualTo(MemberTraits.ABSTRACT);
        assertThat(((Invocable) fact(loader, op, "twice_int")).traits()).isEqualTo(MemberTraits.OVERRIDABLE);
        assertThat(((Invocable) fact(loader, op, "apply_int_int")).resultType().orElseThrow())
                .isSameAs(PrimitiveToken.INT);
        assertThat(((Invocable) fact(loader, op, "apply_int_int"))
                        .params()
                        .get(1)
                        .typeRef())
                .isEqualTo(PrimitiveTypeRef.INT);
    }

    @Test
    void aConstantNamedLikeAReservedNameIsEscaped() throws Exception {
        ClassLoader loader = load(generate("p.Tok.class", TOK));
        String tok = "gen.facts.p.Tok_";

        assertThat(factNames(loader, tok))
                .containsExactly("TOKEN_", "run", "sam_", "token_", "valueOf_String", "values");
        assertThat(((EnumConstant<?>) fact(loader, tok, "TOKEN_")).name()).isEqualTo("TOKEN");
        assertThat(((EnumConstant<?>) fact(loader, tok, "sam_")).name()).isEqualTo("sam");
        assertThat(((EnumConstant<?>) fact(loader, tok, "token_")).name()).isEqualTo("token");
        assertThat(token(loader, tok)).isInstanceOf(EnumToken.class);
    }
}
