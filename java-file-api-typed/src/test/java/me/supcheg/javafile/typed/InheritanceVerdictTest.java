package me.supcheg.javafile.typed;

import com.google.testing.compile.CompilationSubject;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.typed.fixtures.Counted;
import me.supcheg.javafile.typed.fixtures.Joiner;
import me.supcheg.javafile.typed.fixtures.Sourced;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Counted_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Joiner_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Measured_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbers_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Renamed_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Sourced_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.TakesNumbers_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.TakesTexts_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Texts_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Unnamed_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static me.supcheg.javafile.typed.JavacVerdict.compiledClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// What a class inherits from several supertypes, held against javac: the
/// typed layer is no weaker — whatever javac rejects of the supertypes of a
/// class and of its overrides is rejected where it is said — and where it is
/// stricter, [StricterThanJavac] lists it.
///
/// Two rules have no class to show them by yet: a class that extends
/// `Object` and implements interfaces inherits no `static` method and none
/// with package access. They are checked on the data ([OnData]) until a
/// class extends another.
class InheritanceVerdictTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Class<IllegalArgumentException> AT_THE_PLACE = IllegalArgumentException.class;

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Nested
    class AsJavac {

        @Test
        void aGenericInterfaceReachedWithOtherArgumentsThroughTwoInterfacesIsRejected() {
            // Texts extends Sourced<String>, Numbers extends Sourced<Integer>
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Texts_.implement(h.self()));
                            oh.implements_(Numbers_.implement(h.self()));
                        }
                    }),
                    "cannot inherit me.supcheg.javafile.typed.fixtures.Sourced<java.lang.String> and"
                            + " me.supcheg.javafile.typed.fixtures.Sourced<java.lang.Integer>: a class has one"
                            + " parameterization of a generic type among its supertypes (JLS 8.1.5)",
                    "implements Texts, Numbers",
                    "",
                    "Sourced cannot be inherited with different arguments");
        }

        @Test
        void aGenericInterfaceNamedWithOtherArgumentsThanASuperinterfaceGivesItIsRejected() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(new Sourced_<>(Integer_.TOKEN).implement(h.self()));
                            oh.implements_(Texts_.implement(h.self()));
                        }
                    }),
                    "a class has one parameterization of a generic type among its supertypes",
                    "implements Sourced<Integer>, Texts",
                    "",
                    "Sourced cannot be inherited with different arguments");
            // and the other way round: the superinterface first
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Texts_.implement(h.self()));
                            oh.implements_(new Sourced_<>(Integer_.TOKEN).implement(h.self()));
                        }
                    }))
                    .withMessageContaining("a class has one parameterization of a generic type");
        }

        @Test
        void theSameArgumentsThroughTwoInterfacesAreOneSupertype() {
            String source = file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            Upcast<Self, Sourced<String>> asSourced =
                                    oh.implements_(new Sourced_<>(String_.TOKEN).implement(h.self()));
                            oh.implements_(Texts_.implement(h.self()));
                            oh.body();
                            assertThat(asSourced.supertype()).isNotNull();
                        }
                    })
                    .render();

            assertThat(source).contains("implements Sourced<String>, Texts");
            CompilationSubject.assertThat(compiledClass("implements Sourced<String>, Texts", ""))
                    .succeededWithoutWarnings();
        }

        @Test
        void methodsOfOneErasureAndOtherParametersAreRejected() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(TakesTexts_.implement(h.self()));
                            oh.implements_(TakesNumbers_.implement(h.self()));
                        }
                    }),
                    "name clash, both erase to take(java.util.List) and neither overrides the other (JLS 8.4.8.3)",
                    "implements TakesTexts, TakesNumbers",
                    "",
                    "name clash");
        }

        @Test
        void methodsOfOneSignatureAndUnrelatedResultsAreRejected() {
            // int size() of Counted and long size() of Measured: no method of the class is both
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            Upcast<Self, Counted> asCounted = oh.implements_(Counted_.implement(h.self()));
                            oh.implements_(Measured_.implement(h.self()));
                            oh.body().override(asCounted, Counted_.size, (b, _) -> b.return_(literal(1)));
                        }
                    }),
                    "cannot override size() of me.supcheg.javafile.typed.fixtures.Measured: its result is long, and"
                            + " that of the method int: the return types are incompatible (JLS 8.4.8.3)",
                    "implements Counted, Measured",
                    "public int size() { return 1; }",
                    "return type int is not compatible with long");
            // and without a method of the class the two bodies are left: rejected when the class is complete
            classAsJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Counted_.implement(h.self()));
                            oh.implements_(Measured_.implement(h.self()));
                            oh.body();
                        }
                    }),
                    "unrelated methods with a body each, and overrides neither",
                    "implements Counted, Measured",
                    "",
                    "both define size(), but with unrelated return types");
        }

        @Test
        void aMethodASubinterfaceOverridesIsInheritedFromIt() {
            // Renamed overrides label() of Named: one body, no override wanted — to javac neither
            String source = file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.implements_(Renamed_.implement(h.self()));
                            oh.body();
                        }
                    })
                    .render();

            assertThat(source).contains("implements Named, Renamed");
            CompilationSubject.assertThat(compiledClass("implements Named, Renamed", ""))
                    .succeededWithoutWarnings();
        }

        @Test
        void aMethodASubinterfaceMakesAbstractAgainIsImplemented() {
            // the proof of Unnamed has the step: without it there is no proof, as there is no class to javac
            String source = file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var label = h.overriding(Unnamed_.label);
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.implements_(Unnamed_.implement(h.self()).label(label));
                            oh.body().define(label.method(), (b, _) -> b.return_(literal("mine")));
                        }
                    })
                    .render();

            assertThat(source).contains("implements Named, Unnamed").contains("@Override\n    public String label() {");
            CompilationSubject.assertThat(compiledClass("implements Named, Unnamed", ""))
                    .hadErrorContaining("Probe is not abstract and does not override abstract method label()");
        }
    }

    /// Where the typed layer rejects what javac accepts. Beside these: a method declared by its
    /// name with the signature of an inherited one, and `equals` overridden without `hashCode`
    /// ([DeclarationChecksTest]); `clone` and `finalize` of `Object`, which have no fact a class
    /// that extends `Object` can name. A method a class and an interface would both give a body,
    /// which javac inherits from the class, has no class to show it by before one extends another.
    @Nested
    class StricterThanJavac {

        @Test
        void aMethodOfVariableArityIsNotOverridden() {
            // javac takes String[] for String... and warns
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            Upcast<Self, Joiner> asJoiner = oh.implements_(Joiner_.implement(h.self()));
                            oh.body().override(asJoiner, Joiner_.join_StringArray, (b, _, _) -> b.return_(literal("")));
                        }
                    }),
                    "the method is of variable arity, and the typed layer declares none",
                    "implements Joiner",
                    "@Override public String join(String[] parts) { return \"\"; }");
        }

        @Test
        void aGenericMethodIsNotOverridden() {
            // javac takes the erasure of <T> T pick(T) for an override
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            Upcast<Self, Joiner> asJoiner = oh.implements_(Joiner_.implement(h.self()));
                            oh.body()
                                    .override(
                                            asJoiner,
                                            Joiner_.pick_T(Object_.TOKEN),
                                            (b, _, picked) -> b.return_(picked));
                        }
                    }),
                    "the method is generic, and the typed layer declares no generic method",
                    "implements Joiner",
                    "@Override public Object pick(Object picked) { return picked; }");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.overriding(Joiner_.pick_T(Object_.TOKEN));
                        }
                    }))
                    .withMessageContaining("the method is generic");
        }
    }

    /// The rules no class that extends `Object` meets.
    @Nested
    class OnData {
        private static final ClassDesc BASE = ClassDesc.of("x", "Base");
        private static final ClassToken<Object> OWNER = UnsafeFacts.<Object>openClassToken(
                new ClassTypeRef(ClassDesc.of("x", "Self")), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
        private static final MethodRef0<Object, me.supcheg.javafile.facts.Prim.Int> OWN =
                UnsafeFacts.method((OpenClassToken<Object>) OWNER, "m", PrimitiveToken.INT, MemberTraits.FINAL);

        private static Inheritance.Group group(Visibility visibility, Dispatch dispatch) {
            Heritage.Method method = new Heritage.Method(
                    visibility,
                    dispatch,
                    BASE,
                    Signature.of("m"),
                    List.of(),
                    List.of(),
                    Heritage.Arity.FIXED,
                    new Heritage.Result.Of(PrimitiveTypeRef.INT),
                    List.of(),
                    Set.of(List.of()),
                    List.of());
            return new Inheritance.Group(
                    OWN.signature(), List.of(new Inheritance.Entry(Object_.TOKEN, method, OWN.signature())));
        }

        @Test
        void anInstanceMethodDoesNotOverrideAStaticOne() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> SignatureRegistry.requireOverrides(
                            "class x.Self", OWN, group(Visibility.PUBLIC, Dispatch.STATIC)))
                    .withMessageContaining("an instance method cannot override a static one (JLS 8.4.8.1)");
            CompilationSubject.assertThat(compiledClass(
                            "",
                            "static class B { public static int m() { return 0; } }"
                                    + " static class S extends B { public int m() { return 1; } }"))
                    .hadErrorContaining("overridden method is static");
        }

        @Test
        void aMethodWithPackageAccessIsNotOverridden() {
            // stricter than javac, which goes by the package of the class
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> SignatureRegistry.requireOverrides(
                            "class x.Self", OWN, group(Visibility.PACKAGE, Dispatch.CONCRETE)))
                    .withMessageContaining("the method has package access");
            SignatureRegistry.requireOverrides("class x.Self", OWN, group(Visibility.PROTECTED, Dispatch.CONCRETE));
        }
    }
}
