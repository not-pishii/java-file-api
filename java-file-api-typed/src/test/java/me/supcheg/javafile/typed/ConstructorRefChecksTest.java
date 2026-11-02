package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.typed.fixtures.Wide;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.AbstractList_;
import me.supcheg.javafile.typed.testfacts.java.util.function.BiFunction_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Function_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide_;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;

import static me.supcheg.javafile.typed.Expressions.constructorRef;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// The refusals of constructor references (6.4): the typed layer, not javac, rejects a `Type::new` whose
/// class, constructor, parameters or result the method of the functional interface does not take.
// the checks are tested with deliberately raw values
@SuppressWarnings({"rawtypes", "unchecked"})
class ConstructorRefChecksTest {

    private static final Supplier_<String> SUPPLIER = new Supplier_<>(String_.TOKEN);
    private static final Function_<String, String> FUNCTION = new Function_<>(String_.TOKEN, String_.TOKEN);

    @Test
    void aConstructorOfAnotherClassAnInterfaceOrAnAbstractClassIsRefused() {
        AbstractList_<String> abstractList = new AbstractList_<>(String_.TOKEN);
        Supplier_<AbstractList<String>> supplier = new Supplier_<>(abstractList.token);
        refused(
                () -> constructorRef(String_.TOKEN, Wide_.new_, SUPPLIER.token, SUPPLIER.sam.method()),
                "pass the class the constructor belongs to");
        refused(
                () -> constructorRef(Runnable_.TOKEN, Wide_.new_, SUPPLIER.token, SUPPLIER.sam.method()),
                "an interface is no class to construct");
        refused(
                () -> constructorRef(
                        abstractList.token,
                        UnsafeInvocables.superCtor(abstractList.token, MemberTraits.DEFAULT),
                        supplier.token,
                        supplier.sam.method()),
                "is abstract");
    }

    @Test
    void aFactThatIsNoConstructorIsRefused() {
        refused(
                () -> constructorRef(Wide_.TOKEN, String_.trim, SUPPLIER.token, SUPPLIER.sam.method()),
                "is of the kind");
    }

    @Test
    void aConstructorWhoseParametersAreNotTheSamsIsRefused() {
        refused(
                () -> constructorRef(Wide_.TOKEN, Wide_.new_String_String, SUPPLIER.token, SUPPLIER.sam.method()),
                "passes");
        BiFunction_<String, Integer, Wide> wrong = new BiFunction_(String_.TOKEN, Integer_.TOKEN, Wide_.TOKEN);
        refused(() -> constructorRef(Wide_.TOKEN, Wide_.new_String_String, wrong.token, wrong.sam.method()), "passes");
    }

    @Test
    void aConstructedClassThatIsNotTheSamsResultIsRefused() {
        BiFunction_<String, String, String> strings = new BiFunction_(String_.TOKEN, String_.TOKEN, String_.TOKEN);
        refused(
                () -> constructorRef(Wide_.TOKEN, Wide_.new_String_String, strings.token, strings.sam.method()),
                "has the result");
        refused(
                () -> constructorRef(Wide_.TOKEN, Wide_.new_, Runnable_.TOKEN, Runnable_.sam.method()),
                "has the result");
    }

    @Test
    void theInterfaceOfTheReferenceMustBeThatOfItsFact() {
        refused(
                () -> constructorRef(Wide_.TOKEN, Wide_.new_, FUNCTION.token, SUPPLIER.sam.method()),
                "the method of the functional interface is");
    }

    private static void refused(Runnable call, String message) {
        assertThatThrownBy(call::run)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message);
    }
}
