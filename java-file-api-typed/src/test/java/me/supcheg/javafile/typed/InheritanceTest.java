package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.Inheritance.Supertype;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.Comparator_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The merge of the heritages of the supertypes of a class ([Inheritance]),
/// on the heritages of generated metamodels and on ones made by hand for the
/// shapes the JDK has none of at hand: the diamonds.
class InheritanceTest {
    private static final String OWNER = "class x.Self";
    private static final TargetClasspath TARGET = UnsafeFacts.unverifiedClasspath();

    private static final ClassDesc I = ClassDesc.of("x", "I");
    private static final ClassDesc J = ClassDesc.of("x", "J");
    private static final ClassDesc K = ClassDesc.of("x", "K");
    private static final ClassDesc CD_LIST = ClassDesc.of("java.util.List");

    private static Inheritance ofObject() {
        return Inheritance.of(OWNER, Inheritance.supertype(OWNER, TARGET, Object_.TOKEN));
    }

    private static Supertype real(DeclaredToken<?> token) {
        return Inheritance.supertype(OWNER, TARGET, token);
    }

    /// An interface made by hand, with the methods it has as a member.
    private static Supertype iface(ClassDesc desc, Heritage.Method... methods) {
        return new Supertype(
                UnsafeFacts.interfaceToken(new ClassTypeRef(desc), MethodTable.EMPTY),
                new Heritage.Told(List.of(methods), List.of()));
    }

    private static Heritage.Method m(Dispatch dispatch, ClassDesc declaredBy, ClassDesc... overrides) {
        return method(dispatch, declaredBy, "m", List.of(), List.of(), overrides);
    }

    private static Heritage.Method method(
            Dispatch dispatch,
            ClassDesc declaredBy,
            String name,
            List<TypeRef> params,
            List<ClassDesc> erasure,
            ClassDesc... overrides) {
        return new Heritage.Method(
                Heritage.Visibility.PUBLIC,
                dispatch,
                declaredBy,
                Signature.of(name, erasure.stream().map(Param::fixed).toArray(Param[]::new)),
                List.of(),
                params,
                Heritage.Arity.FIXED,
                new Heritage.Result.Of(PrimitiveTypeRef.INT),
                List.of(),
                Set.of(erasure),
                List.of(overrides));
    }

    private static MethodSignature sig(String name, ClassDesc... params) {
        return new MethodSignature(name, List.of(params));
    }

    @Test
    void aClassInheritsTheMethodsOfObjectAndOfAnInterfaceAppliedToItsArguments() {
        Inheritance inheritance = ofObject().with(real(new Comparable_<>(String_.TOKEN).token));

        assertThat(inheritance.group(sig("toString"))).isPresent();
        assertThat(inheritance.group(sig("compareTo", ConstantDescs.CD_String))).hasValueSatisfying(group -> {
            assertThat(group.entries()).singleElement().matches(Inheritance.Entry::isAbstract);
            assertThat(group.entries().getFirst().method().params()).containsExactly(Types.of(String.class));
            assertThat(group.bodies()).isEmpty();
        });
        assertThat(inheritance.group(sig("compareTo", ConstantDescs.CD_Object))).isEmpty();
        // the bridge javac makes of compareTo(String) is compareTo(Object)
        assertThat(inheritance.bridged(sig("compareTo", ConstantDescs.CD_Object)))
                .isPresent();
        assertThat(inheritance.bridged(sig("compareTo", ConstantDescs.CD_String)))
                .isEmpty();
    }

    @Test
    void aMethodOfAnInterfaceThatObjectHasPublicIsNoneToInherit() {
        // Comparator declares `boolean equals(Object)` anew, abstract
        Inheritance inheritance = ofObject().with(real(new Comparator_<>(String_.TOKEN).token));

        assertThat(inheritance.group(sig("equals", ConstantDescs.CD_Object)))
                .hasValueSatisfying(group -> assertThat(group.entries())
                        .singleElement()
                        .matches(entry -> entry.method().declaredBy().equals(ConstantDescs.CD_Object)));
    }

    @Test
    void aMethodOverriddenBySubinterfaceIsNotInherited() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, I))).with(iface(J, m(Dispatch.DEFAULT, J, I)));

        assertThat(inheritance.group(sig("m")).orElseThrow().bodies())
                .singleElement()
                .matches(entry -> entry.method().declaredBy().equals(J));
    }

    @Test
    void aMethodReabstractedBySubinterfaceLeavesNoBody() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, I))).with(iface(J, m(Dispatch.ABSTRACT, J, I)));

        assertThat(inheritance.group(sig("m")).orElseThrow())
                .matches(group -> group.bodies().isEmpty())
                .matches(group -> group.entries().size() == 1);
    }

    @Test
    void theDefaultMethodsOfUnrelatedInterfacesAreTwoBodies() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, I))).with(iface(J, m(Dispatch.DEFAULT, J)));

        assertThat(inheritance.group(sig("m")).orElseThrow().bodies()).hasSize(2);
    }

    @Test
    void aMethodTwoSupertypesHaveFromOneIsInheritedOnce() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, K))).with(iface(J, m(Dispatch.DEFAULT, K)));

        assertThat(inheritance.group(sig("m")).orElseThrow().bodies()).hasSize(1);
    }

    @Test
    void aStaticAndAnInstanceMethodOfOneSignatureAreRejected() {
        Inheritance inheritance = ofObject().with(iface(I, m(Dispatch.STATIC, I)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> inheritance.with(iface(J, m(Dispatch.ABSTRACT, J))))
                .withMessageContaining("one is static and the other an instance method")
                .withMessageContaining("m() of x.I")
                .withMessageContaining("m() of x.J");
    }

    @Test
    void methodsOfOneErasureAndOtherParametersAreRejected() {
        TypeRef strings = Types.parameterized(CD_LIST, Types.of(String.class));
        TypeRef integers = Types.parameterized(CD_LIST, Types.of(Integer.class));
        Inheritance inheritance =
                ofObject().with(iface(I, method(Dispatch.ABSTRACT, I, "m", List.of(strings), List.of(CD_LIST))));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> inheritance.with(
                        iface(J, method(Dispatch.ABSTRACT, J, "m", List.of(integers), List.of(CD_LIST)))))
                .withMessageContaining("name clash, both erase to m(java.util.List)");
    }

    @Test
    void aMethodThatWouldBeTheBridgeOfAnotherIsRejected() {
        // Comparable<String>: compareTo(String), bridged as compareTo(Object)
        Inheritance inheritance = ofObject().with(real(new Comparable_<>(String_.TOKEN).token));
        Supertype clashing = iface(
                J,
                method(
                        Dispatch.ABSTRACT,
                        J,
                        "compareTo",
                        List.of(Types.of(Object.class)),
                        List.of(ConstantDescs.CD_Object)));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> inheritance.with(clashing))
                .withMessageContaining("name clash")
                .withMessageContaining("the bridge of one would be the other");
    }

    @Test
    void aSupertypeIsNamedOnce() {
        Inheritance inheritance = ofObject().with(real(Runnable_.TOKEN));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> inheritance.with(real(Runnable_.TOKEN)))
                .withMessageContaining("has the supertype java.lang.Runnable already");
        // another parameterization of the same interface is the same interface named twice
        Inheritance comparable = ofObject().with(real(new Comparable_<>(String_.TOKEN).token));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> comparable.with(real(new Comparable_<>(Integer_.TOKEN).token)))
                .withMessageContaining("already");
    }

    @Test
    void aSupertypeHasExactTypeArguments() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> real(Comparable_.ANY))
                .withMessageContaining("a supertype has no wildcard type argument");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> real(UnsafeFacts.interfaceToken(Comparable_.Data.SHAPE)))
                .withMessageContaining("raw supertype");
    }

    @Test
    void aShapeThatTellsNoHeritageIsNoSupertype() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> real(String_.TOKEN))
                .withMessageContaining("tells no heritage");
    }

    @Test
    void theGroupsAreOneMethodEach() {
        Inheritance inheritance = ofObject().with(real(Runnable_.TOKEN));

        assertThat(inheritance.groups().map(Inheritance.Group::signature))
                .doesNotHaveDuplicates()
                .contains(sig("run"), sig("hashCode"), sig("wait", ConstantDescs.CD_long));
        assertThat(inheritance.supertypes().stream()
                        .map(supertype -> supertype.token().shape().desc()))
                .containsExactly(ConstantDescs.CD_Object, ClassDesc.of("java.lang.Runnable"));
        assertThat(Stream.of("clone", "finalize").map(name -> inheritance.group(sig(name))))
                .allMatch(java.util.Optional::isPresent);
    }
}
