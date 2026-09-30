package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The processor on types of the JDK, whose members are many and odd: the
/// generated metamodels compile under every lint and their facts describe the
/// members.
class JdkTypesTest extends FixtureSupport {
    private static final String TYPES = "java.lang.String.class, java.lang.StringBuilder.class,"
            + " java.lang.Object.class, java.lang.Math.class, java.lang.Integer.class, java.lang.Double.class,"
            + " java.lang.System.class, java.time.DayOfWeek.class, java.lang.Throwable.class,"
            + " java.lang.Runnable.class, java.lang.CharSequence.class, java.io.IOException.class,"
            + " java.lang.Character.class, java.util.concurrent.TimeUnit.class, java.lang.Thread.class,"
            + " java.nio.file.Files.class, java.nio.charset.StandardCharsets.class";

    private Compilation compile() {
        return ProcessorHarness.succeeded(ProcessorHarness.process(List.of(), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({%s})
                class G {}
                """.formatted(TYPES)));
    }

    @Test
    void theFactsOfJdkTypesCompileAndDescribeTheirMembers() throws Exception {
        Compilation compilation = compile();
        ClassLoader loader = ProcessorHarness.compileAndLoad(compilation, out, List.of());
        String string = "gen.facts.java.lang.String_";

        assertThat(token(loader, string)).isInstanceOf(FinalClassToken.class);
        Invocable length = (Invocable) fact(loader, string, "length");
        assertThat(length.resultType().orElseThrow()).isSameAs(PrimitiveToken.INT);
        assertThat(length.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) fact(loader, string, "charAt_int")).resultType().orElseThrow())
                .isSameAs(PrimitiveToken.CHAR);
        assertThat(((Invocable) fact(loader, string, "substring_int_int")).params())
                .hasSize(2);
        assertThat(((Invocable) fact(loader, string, "valueOf_int")).traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(((Invocable) fact(loader, string, "getBytes_String"))
                        .traits()
                        .throwsTypes())
                .extracting(t -> t.typeRef())
                .containsExactly(Types.of(ClassDesc.of("java.io.UnsupportedEncodingException")));

        String integer = "gen.facts.java.lang.Integer_";
        assertThat(((StaticFieldRef<?>) fact(loader, integer, "MAX_VALUE")).constantValue())
                .contains(Integer.MAX_VALUE);
        String dbl = "gen.facts.java.lang.Double_";
        assertThat(((StaticFieldRef<?>) fact(loader, dbl, "NaN")).constantValue())
                .contains(Double.NaN);
        assertThat(((StaticFieldRef<?>) fact(loader, dbl, "POSITIVE_INFINITY")).constantValue())
                .contains(Double.POSITIVE_INFINITY);
        assertThat(((StaticFieldRef<?>) fact(loader, dbl, "MIN_VALUE")).constantValue())
                .contains(Double.MIN_VALUE);

        assertThat(((EnumToken<?>) token(loader, "gen.facts.java.time.DayOfWeek_")).constants())
                .containsExactly("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        assertThat(((EnumConstant<?>) fact(loader, "gen.facts.java.time.DayOfWeek_", "MONDAY")).name())
                .isEqualTo("MONDAY");
        assertThat(fact(loader, "gen.facts.java.lang.Runnable_", "sam")).isInstanceOf(VoidSam0.class);
    }

    @Test
    void anObjectMetamodelHasOnlyTheMethodsObjectDeclares() throws Exception {
        ClassLoader loader = ProcessorHarness.compileAndLoad(compile(), out, List.of());

        assertThat(factNames(loader, "gen.facts.java.lang.Object_"))
                .contains("equals_Object", "hashCode", "toString", "new_")
                .doesNotContain("length");
    }
}
