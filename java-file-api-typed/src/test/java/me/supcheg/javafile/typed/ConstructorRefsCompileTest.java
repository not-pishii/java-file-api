package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.fixtures.Wide;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.function.BiFunction_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static me.supcheg.javafile.typed.Expressions.constructorRef;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static org.assertj.core.api.Assertions.assertThat;

/// Constructor references of the generated code (6.4): `Type::new` of no parameters and of two, each of the
/// functional interface its method implements, rendered exactly, compiled under `-Xlint:all`, and run so
/// that what it constructs is observed.
// the casts check the compile-time refusals, and are unchecked on purpose
@SuppressWarnings({"rawtypes", "unchecked"})
class ConstructorRefsCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Makers");

    private static final Supplier_<Wide> SUPPLIER = new Supplier_<>(Wide_.TOKEN);
    private static final BiFunction_<String, String, Wide> BI_FUNCTION =
            new BiFunction_<>(String_.TOKEN, String_.TOKEN, Wide_.TOKEN);
    private static final ArrayList_<String> STRING_LIST = new ArrayList_<>(String_.TOKEN);
    private static final Supplier_<ArrayList<String>> LIST_SUPPLIER = new Supplier_<>(STRING_LIST.token);

    @Test
    void aConstructorReferenceOfNoParametersAndOfTwoCompilesAndConstructsTheClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var made = cb.declareStaticMethod("made", SUPPLIER.token);
                        var joined = cb.declareStaticMethod("joined", BI_FUNCTION.token);
                        var viaInvocable = cb.declareStaticMethod("viaInvocable", SUPPLIER.token);
                        var same = cb.declareStaticMethod("same", PrimitiveToken.BOOLEAN);
                        var lists = cb.declareStaticMethod("lists", LIST_SUPPLIER.token);
                        var bodies = cb.bodies();
                        bodies.define(
                                made,
                                b -> b.return_(constructorRef(Wide_.new_, SUPPLIER.token, SUPPLIER.sam.method())));
                        bodies.define(
                                joined,
                                b -> b.return_(constructorRef(
                                        Wide_.new_String_String, BI_FUNCTION.token, BI_FUNCTION.sam.method())));
                        bodies.define(
                                viaInvocable,
                                b -> b.return_(constructorRef(
                                        Wide_.TOKEN, Wide_.new_, SUPPLIER.token, SUPPLIER.sam.method())));
                        bodies.define(
                                same,
                                b -> b.return_(eqRef(
                                        constructorRef(Wide_.new_, SUPPLIER.token, SUPPLIER.sam.method()),
                                        constructorRef(Wide_.new_, SUPPLIER.token, SUPPLIER.sam.method()))));
                        bodies.define(
                                lists,
                                b -> b.return_(constructorRef(
                                        STRING_LIST.new_, LIST_SUPPLIER.token, LIST_SUPPLIER.sam.method())));
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("return (Supplier<Wide>) Wide::new;")
                .contains("return (BiFunction<String, String, Wide>) Wide::new;")
                .contains("(Supplier<Wide>) Wide::new == (Supplier<Wide>) Wide::new")
                .contains("return (Supplier<ArrayList<String>>) ArrayList<String>::new;");

        Supplier<Wide> made = (Supplier<Wide>) compiled.invoke("made");
        assertThat(made.get().told).isEmpty();
        BiFunction<String, String, Wide> joined = (BiFunction<String, String, Wide>) compiled.invoke("joined");
        assertThat(joined.apply("a", "b").told).isEqualTo("new:ab");
        Supplier<Wide> viaInvocable = (Supplier<Wide>) compiled.invoke("viaInvocable");
        assertThat(viaInvocable.get().told).isEmpty();
        assertThat(compiled.invoke("same")).isEqualTo(false);
        Supplier<ArrayList<String>> lists = (Supplier<ArrayList<String>>) compiled.invoke("lists");
        assertThat(lists.get()).isEmpty();
    }
}
