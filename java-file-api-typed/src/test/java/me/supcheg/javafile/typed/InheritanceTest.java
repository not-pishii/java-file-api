package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
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
import me.supcheg.javafile.typed.testfacts.java.util.List_;
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
    private static final Inheritance.Heir HEIR = new Inheritance.Heir.OfClass(OWNER, "x");
    private static final TargetClasspath TARGET = UnsafeFacts.unverifiedClasspath();

    private static final ClassDesc I = ClassDesc.of("x", "I");
    private static final ClassDesc J = ClassDesc.of("x", "J");
    private static final ClassDesc K = ClassDesc.of("x", "K");
    private static final ClassDesc CD_LIST = ClassDesc.of("java.util.List");

    private static Inheritance ofObject() {
        return Inheritance.of(HEIR, Inheritance.supertype(OWNER, TARGET, Object_.TOKEN));
    }

    private static Supertype real(DeclaredToken<?> token) {
        return Inheritance.supertype(OWNER, TARGET, token);
    }

    /// An interface made by hand, with the methods it has as a member.
    @SafeVarargs
    private static Supertype iface(ClassDesc desc, Heritage.Method<Heritage.Inherited>... methods) {
        return new Supertype(
                UnsafeFacts.interfaceToken(new ClassTypeRef(desc), MethodTable.EMPTY),
                List.of(),
                new Heritage.Applied(List.of(methods), List.of()));
    }

    /// A class made by hand, with the methods it has as a member.
    @SafeVarargs
    private static Supertype klass(ClassDesc desc, Heritage.Method<Heritage.Inherited>... methods) {
        return new Supertype(
                UnsafeFacts.openClassToken(new ClassTypeRef(desc), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY),
                List.of(),
                new Heritage.Applied(List.of(methods), List.of()));
    }

    /// The methods of a group that come with a body.
    private static List<Inheritance.Entry> bodies(Inheritance.Group group) {
        return group.entries().stream().filter(Inheritance.Entry::hasBody).toList();
    }

    private static Heritage.Method<Heritage.Inherited> m(
            Dispatch dispatch, ClassDesc declaredBy, ClassDesc... overrides) {
        return method(dispatch, declaredBy, "m", List.of(), List.of(), overrides);
    }

    private static Heritage.Method<Heritage.Inherited> method(
            Dispatch dispatch,
            ClassDesc declaredBy,
            String name,
            List<TypeRef> params,
            List<ClassDesc> erasure,
            ClassDesc... overrides) {
        Heritage.Declarer by =
                switch (dispatch) {
                    case ABSTRACT -> new Heritage.Declarer.OfInterface(declaredBy, Heritage.InterfaceMethod.ABSTRACT);
                    case DEFAULT -> new Heritage.Declarer.OfInterface(declaredBy, Heritage.InterfaceMethod.DEFAULT);
                    case CONCRETE ->
                        new Heritage.Declarer.OfClass(
                                declaredBy, Heritage.Visibility.PUBLIC, Heritage.ClassMethod.CONCRETE);
                    case FINAL ->
                        new Heritage.Declarer.OfClass(
                                declaredBy, Heritage.Visibility.PUBLIC, Heritage.ClassMethod.FINAL);
                    case STATIC ->
                        new Heritage.Declarer.OfClass(
                                declaredBy, Heritage.Visibility.PUBLIC, Heritage.ClassMethod.STATIC);
                };
        return new Heritage.Method<>(
                Signature.of(name, erasure.stream().map(Param::fixed).toArray(Param[]::new)),
                List.of(new Heritage.Plain(
                        by, Heritage.Arity.FIXED, params, new Heritage.Result.Of<>(PrimitiveTypeRef.INT), List.of())),
                Set.of(erasure),
                List.of(overrides));
    }

    /// A generic method `m`, of `variables` type parameters without bounds, as a supertype has it.
    private static Heritage.Method<Heritage.Inherited> generic(
            Heritage.Declarer by,
            int variables,
            List<Heritage.Type> params,
            List<ClassDesc> erasure,
            Heritage.Result<Heritage.Type> result,
            Heritage.Type... thrown) {
        return new Heritage.Method<>(
                Signature.of("m", erasure.stream().map(Param::fixed).toArray(Param[]::new)),
                List.of(new Heritage.Generic(new Heritage.Declaration(
                        by,
                        Heritage.Arity.FIXED,
                        Stream.generate(() -> new Heritage.Variable(List.of()))
                                .limit(variables)
                                .toList(),
                        params,
                        result,
                        List.of(thrown)))),
                Set.of(erasure),
                List.of());
    }

    private static Heritage.Declarer abstractOf(ClassDesc declarer) {
        return new Heritage.Declarer.OfInterface(declarer, Heritage.InterfaceMethod.ABSTRACT);
    }

    private static Heritage.Declarer concreteOf(ClassDesc declarer) {
        return new Heritage.Declarer.OfClass(declarer, Heritage.Visibility.PUBLIC, Heritage.ClassMethod.CONCRETE);
    }

    private static MethodSignature sig(String name, ClassDesc... params) {
        return new MethodSignature(name, List.of(params));
    }

    @Test
    void aClassInheritsTheMethodsOfObjectAndOfAnInterfaceAppliedToItsArguments() {
        Inheritance inheritance = ofObject().with(real(new Comparable_<>(String_.TOKEN).token));

        assertThat(inheritance.group(sig("toString"))).isPresent();
        assertThat(inheritance.group(sig("compareTo", ConstantDescs.CD_String))).hasValueSatisfying(group -> {
            assertThat(group.entries()).singleElement().matches(Inheritance.Entry::leftAbstract);
            assertThat(group.standing()).isEqualTo(Dispatch.ABSTRACT);
            assertThat(group.entries().getFirst().method().declared())
                    .isInstanceOfSatisfying(
                            Heritage.Plain.class,
                            plain -> assertThat(plain.params()).containsExactly(Types.of(String.class)));
            assertThat(bodies(group)).isEmpty();
            assertThat(group.conflict()).isEmpty();
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

        assertThat(bodies(inheritance.group(sig("m")).orElseThrow()))
                .singleElement()
                .matches(entry -> entry.method().declaredBy().equals(J));
        assertThat(inheritance.group(sig("m")).orElseThrow().conflict()).isEmpty();
    }

    @Test
    void aMethodReabstractedBySubinterfaceLeavesNoBody() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, I))).with(iface(J, m(Dispatch.ABSTRACT, J, I)));

        assertThat(inheritance.group(sig("m")).orElseThrow())
                .matches(group -> bodies(group).isEmpty())
                .matches(group -> group.standing() == Dispatch.ABSTRACT)
                .matches(group -> group.entries().size() == 1);
    }

    @Test
    void theDefaultMethodsOfUnrelatedInterfacesAreTwoBodies() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, I))).with(iface(J, m(Dispatch.DEFAULT, J)));

        Inheritance.Group group = inheritance.group(sig("m")).orElseThrow();
        assertThat(bodies(group)).hasSize(2);
        assertThat(group.conflict())
                .hasValueSatisfying(why ->
                        assertThat(why).contains("m() of x.I and m() of x.J are unrelated methods with a body each"));
        assertThat(group.unsettled()).isEqualTo(group.conflict());
    }

    @Test
    void aDefaultMethodBesideAnAbstractOneOfAnUnrelatedInterfaceIsForAMethodOfTheClassToSettle() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, I))).with(iface(J, m(Dispatch.ABSTRACT, J)));

        Inheritance.Group group = inheritance.group(sig("m")).orElseThrow();
        assertThat(group.standing()).isEqualTo(Dispatch.ABSTRACT);
        assertThat(group.conflict())
                .hasValueSatisfying(why -> assertThat(why).contains("one gives a body and another leaves abstract"));
        // the step of the proof for J settles the abstract one: by a method of the class
        assertThat(group.unsettled()).isEmpty();
    }

    @Test
    void theMethodOfAClassStandsWhateverAnInterfaceDeclares() {
        Inheritance inheritance = Inheritance.of(HEIR, klass(K, m(Dispatch.CONCRETE, K)))
                .with(iface(I, m(Dispatch.DEFAULT, I)))
                .with(iface(J, m(Dispatch.ABSTRACT, J)));

        Inheritance.Group group = inheritance.group(sig("m")).orElseThrow();
        assertThat(group.standing()).isEqualTo(Dispatch.CONCRETE);
        assertThat(group.stands().method().declaredBy()).isEqualTo(K);
        assertThat(group.conflict()).isEmpty();
    }

    @Test
    void aMethodOfAClassThatIsNotPublicDoesNotStandForOneOfAnInterface() {
        Heritage.Method<Heritage.Inherited> hidden = new Heritage.Method<>(
                Signature.of("m"),
                List.of(new Heritage.Plain(
                        new Heritage.Declarer.OfClass(K, Heritage.Visibility.PROTECTED, Heritage.ClassMethod.CONCRETE),
                        Heritage.Arity.FIXED,
                        List.of(),
                        new Heritage.Result.Of<>(PrimitiveTypeRef.INT),
                        List.of())),
                Set.of(List.of()),
                List.of());
        Inheritance inheritance = Inheritance.of(HEIR, klass(K, hidden)).with(iface(I, m(Dispatch.DEFAULT, I)));

        assertThat(inheritance.group(sig("m")).orElseThrow().conflict())
                .hasValueSatisfying(why -> assertThat(why).contains("it is not public"));
    }

    @Test
    void aMethodWithPackageAccessOfAnotherPackageIsNoMemberOfAClassAndIsOneOfATypeVariable() {
        ClassDesc far = ClassDesc.of("y", "Far");
        Heritage.Method<Heritage.Inherited> hidden = new Heritage.Method<>(
                Signature.of("m"),
                List.of(new Heritage.Plain(
                        new Heritage.Declarer.OfClass(far, Heritage.Visibility.PACKAGE, Heritage.ClassMethod.CONCRETE),
                        Heritage.Arity.FIXED,
                        List.of(),
                        new Heritage.Result.Of<>(PrimitiveTypeRef.INT),
                        List.of())),
                Set.of(List.of()),
                List.of());

        Inheritance ofClass = Inheritance.of(HEIR, klass(far, hidden)).with(iface(I, m(Dispatch.DEFAULT, I)));
        assertThat(ofClass.group(sig("m")).orElseThrow().entries())
                .singleElement()
                .matches(entry -> entry.method().declaredBy().equals(I));
        // and yet the method the virtual machine selects: for a method of the class to settle
        assertThat(ofClass.group(sig("m")).orElseThrow().unsettled())
                .hasValueSatisfying(why -> assertThat(why).contains("IllegalAccessError"));
        // in the package of the method the class may inherit it, and a type variable is held to
        Inheritance near = Inheritance.of(new Inheritance.Heir.OfClass("class y.Self", "y"), klass(far, hidden));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> near.with(iface(I, m(Dispatch.DEFAULT, I))))
                .withMessageContaining("the first has package access");
        Inheritance ofVariable = Inheritance.of(new Inheritance.Heir.OfVariable("T"), klass(far, hidden));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ofVariable.with(iface(I, m(Dispatch.DEFAULT, I))))
                .withMessageContaining("the first has package access");
    }

    @Test
    void aMethodTwoSupertypesHaveFromOneIsInheritedOnce() {
        Inheritance inheritance =
                ofObject().with(iface(I, m(Dispatch.DEFAULT, K))).with(iface(J, m(Dispatch.DEFAULT, K)));

        assertThat(bodies(inheritance.group(sig("m")).orElseThrow())).hasSize(1);
    }

    @Test
    void aStaticMethodOfTheSuperclassAndAnInstanceOneOfOneSignatureAreRejected() {
        // a static method is one of a class alone: those of an interface are not inherited (JLS 8.4.8)
        Inheritance inheritance = Inheritance.of(HEIR, klass(I, m(Dispatch.STATIC, I)));
        assertThat(inheritance.group(sig("m")).orElseThrow().standing()).isEqualTo(Dispatch.STATIC);

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
    void genericMethodsAreOfTheSameParametersWhenTheirTypeParametersAndParametersAreTheSame() {
        Heritage.Result<Heritage.Type> ofVariable = new Heritage.Result.Of<>(new Heritage.Type.OfMember(0));
        Heritage.Type variable = new Heritage.Type.OfMember(0);
        Heritage.Type object = new Heritage.Type.Fixed(Types.OBJECT);
        List<ClassDesc> erased = List.of(ConstantDescs.CD_Object);
        Inheritance inheritance =
                ofObject().with(iface(I, generic(abstractOf(I), 1, List.of(variable), erased, ofVariable)));

        // <T> T m(T) of two interfaces is one method to implement
        assertThat(inheritance
                        .with(iface(J, generic(abstractOf(J), 1, List.of(variable), erased, ofVariable)))
                        .group(sig("m", ConstantDescs.CD_Object))
                        .orElseThrow()
                        .entries())
                .hasSize(2);
        // <T> T m(Object), <T, U> T m(T) and a method that is not generic erase as it does, and are others
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        inheritance.with(iface(J, generic(abstractOf(J), 1, List.of(object), erased, ofVariable))))
                .withMessageContaining("name clash, both erase to m(java.lang.Object)");
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        inheritance.with(iface(J, generic(abstractOf(J), 2, List.of(variable), erased, ofVariable))))
                .withMessageContaining("name clash, both erase to m(java.lang.Object)");
        Supertype plain = iface(J, method(Dispatch.ABSTRACT, J, "m", List.of(Types.OBJECT), erased));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> inheritance.with(plain))
                .withMessageContaining("name clash, both erase to m(java.lang.Object)");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ofObject()
                        .with(plain)
                        .with(iface(I, generic(abstractOf(I), 1, List.of(variable), erased, ofVariable))))
                .withMessageContaining("name clash, both erase to m(java.lang.Object)");
    }

    @Test
    void aGenericMethodOfAClassStandsForOneOfAnInterfaceOfItsResultAndItsExceptions() {
        Heritage.Result<Heritage.Type> ofVariable = new Heritage.Result.Of<>(new Heritage.Type.OfMember(0));
        Heritage.Type failure = new Heritage.Type.Fixed(Types.of(java.io.IOException.class));
        Supertype declaring = iface(I, generic(abstractOf(I), 1, List.of(), List.of(), ofVariable, failure));

        assertThat(Inheritance.of(HEIR, klass(K, generic(concreteOf(K), 1, List.of(), List.of(), ofVariable, failure)))
                        .with(declaring)
                        .group(sig("m"))
                        .orElseThrow()
                        .conflict())
                .isEmpty();
        assertThat(Inheritance.of(
                                HEIR,
                                klass(
                                        K,
                                        generic(
                                                concreteOf(K),
                                                1,
                                                List.of(),
                                                List.of(),
                                                new Heritage.Result.Of<>(new Heritage.Type.Fixed(Types.OBJECT)))))
                        .with(declaring)
                        .group(sig("m"))
                        .orElseThrow()
                        .conflict())
                .hasValueSatisfying(why -> assertThat(why).contains("its result is not the one the other has"));
        assertThat(Inheritance.of(HEIR, klass(K, generic(concreteOf(K), 1, List.of(), List.of(), ofVariable, failure)))
                        .with(iface(I, generic(abstractOf(I), 1, List.of(), List.of(), ofVariable)))
                        .group(sig("m"))
                        .orElseThrow()
                        .conflict())
                .hasValueSatisfying(why -> assertThat(why).contains("it declares the exception"));
    }

    @Test
    void aMemberOfTheSuperclassIsTheMethodAFactTellsWhenItIsWrittenAsTheFact() {
        DeclaredToken<Object> owner =
                UnsafeFacts.openClassToken(new ClassTypeRef(K), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
        var strings = new List_<>(String_.TOKEN).token;
        Inheritance.Entry member = Inheritance.of(
                        HEIR, klass(K, method(Dispatch.CONCRETE, K, "m", List.of(strings.typeRef()), List.of(CD_LIST))))
                .group(sig("m", CD_LIST))
                .orElseThrow()
                .stands();

        assertThat(member.differsFrom(
                        UnsafeInvocables.method(owner, "m", PrimitiveToken.INT, strings, MemberTraits.DEFAULT)))
                .isEmpty();
        assertThat(member.differsFrom(UnsafeInvocables.method(
                        owner, "m", PrimitiveToken.INT, new List_<>(Integer_.TOKEN).token, MemberTraits.DEFAULT)))
                .hasValue("its parameters are not those of the fact");
        assertThat(member.differsFrom(
                        UnsafeInvocables.method(owner, "m", String_.TOKEN, strings, MemberTraits.DEFAULT)))
                .hasValue("its result is not that of the fact");
        assertThat(Inheritance.of(
                                HEIR,
                                klass(
                                        K,
                                        generic(
                                                concreteOf(K),
                                                1,
                                                List.of(),
                                                List.of(),
                                                new Heritage.Result.Of<>(new Heritage.Type.OfMember(0)))))
                        .group(sig("m"))
                        .orElseThrow()
                        .stands()
                        .differsFrom(UnsafeInvocables.method(owner, "m", Object_.TOKEN, MemberTraits.DEFAULT)))
                .hasValue("it is generic, and the fact tells it with type arguments");
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
                .isThrownBy(() ->
                        real(UnsafeFacts.interfaceToken(Comparable_.Data.SHAPE, TokenArg.extendsBound(String_.TOKEN))))
                .withMessageContaining("a supertype has no wildcard type argument");
        assertThatIllegalArgumentException()
                .isThrownBy(() ->
                        real(UnsafeFacts.interfaceToken(Comparable_.Data.SHAPE, TokenArg.superBound(String_.TOKEN))))
                .withMessageContaining("a supertype has no wildcard type argument");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> real(UnsafeFacts.interfaceToken(Comparable_.Data.SHAPE)))
                .withMessageContaining("raw supertype");
    }

    @Test
    void anAbstractMethodWithPackageAccessIsNoneAClassOfAnotherPackageImplements() {
        ClassDesc far = ClassDesc.of("y", "Far");
        Heritage.Method<Heritage.Inherited> hidden = new Heritage.Method<>(
                Signature.of("m"),
                List.of(new Heritage.Plain(
                        new Heritage.Declarer.OfClass(far, Heritage.Visibility.PACKAGE, Heritage.ClassMethod.ABSTRACT),
                        Heritage.Arity.FIXED,
                        List.of(),
                        new Heritage.Result.Of<>(PrimitiveTypeRef.INT),
                        List.of())),
                Set.of(List.of()),
                List.of());

        assertThatIllegalArgumentException()
                .isThrownBy(() -> Inheritance.of(HEIR, klass(far, hidden)))
                .withMessageContaining(OWNER + " cannot have the supertype")
                .withMessageContaining(
                        "is abstract and has package access, so a class of another package cannot" + " implement it");
        // a type variable is of the class its bounds make, and implements nothing
        assertThat(Inheritance.of(new Inheritance.Heir.OfVariable("T"), klass(far, hidden))
                        .group(sig("m")))
                .isPresent();
    }

    @Test
    void aShapeThatTellsNoHeritageIsNoSupertype() {
        // a class that is being declared tells none: it is final
        assertThatIllegalArgumentException()
                .isThrownBy(() -> real(UnsafeFacts.declaredClassToken(
                        new ClassTypeRef(K), () -> List.of(ConstantDescs.CD_Object), () -> MethodTable.EMPTY)))
                .withMessageContaining("tells no heritage");
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
