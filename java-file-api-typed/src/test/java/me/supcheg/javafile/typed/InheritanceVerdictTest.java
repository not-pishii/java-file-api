package me.supcheg.javafile.typed;

import com.google.testing.compile.CompilationSubject;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Counted;
import me.supcheg.javafile.typed.fixtures.Joiner;
import me.supcheg.javafile.typed.fixtures.Local;
import me.supcheg.javafile.typed.fixtures.Sealing;
import me.supcheg.javafile.typed.fixtures.Sourced;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Counted_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Joiner_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Local_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Measured_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbers_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Renamed_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Sealing_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Sourced_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.TakesNumbers_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.TakesTexts_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Texts_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Unnamed_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Useful_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static me.supcheg.javafile.typed.JavacVerdict.compiledClass;
import static me.supcheg.javafile.typed.Subclasses.began;
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
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
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
    /// ([DeclarationChecksTest]).
    ///
    /// Of a class with a superclass, each held against javac where it is told:
    ///
    /// - a method the superclass and an interface give a body each is overridden, where javac
    ///   takes that of the class — `ExtendsTest.Inheriting`
    ///   (`aMethodTheSuperclassAndAnInterfaceGiveABodyEachIsOverridden`); so a `final` method of
    ///   the superclass beside a default one leaves no class to declare
    ///   ([OfASuperclass#aFinalMethodOfTheSuperclassDoesNotStandForADefaultOne]);
    /// - a method of package access of the superclass is neither overridden nor declared over,
    ///   whatever the package of the class ([OfASuperclass#aMethodWithPackageAccessIsNotOverridden],
    ///   `ExtendsTest.Inheriting.aMethodDeclaredByTheNameOfOneOfTheSuperclassIsRejected`), nor
    ///   called as `super.m()`; a `static` one is not called through `super`
    ///   ([ExtendsFormsTest#superCallsAMethodTheClassReachesAndAnInstanceOne]);
    /// - a method of the superclass stands for that of an interface with its very result, not a
    ///   narrower one ([ExtendsFormsTest#aMethodOfTheSuperclassStandsForThatOfAnInterfaceOnlyAsAnOverrideWould]);
    /// - a `protected` field is reached on `this` alone and of the direct superclass alone
    ///   (`ExtendsTest.SuperAndProtected.aProtectedFieldIsReachedOnThisAlone`); a `protected`
    ///   method that declares a checked exception, or whose result has to be of exactly its
    ///   type, is not called on the class itself (`aProtectedMethodThatThrowsIsCalledAsSuper`,
    ///   `aProtectedCallWhoseTypeHasToBeExactIsBoundToAVariableFirst`); access from the same
    ///   package is not offered;
    /// - `super.m()` by the fact of a class above the one that narrowed the `throws` clause of
    ///   the method (`ExtendsTest.SuperAndProtected.superCallsTheMethodAsTheSuperclassHasIt`).
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

    /// The rules a class meets only with a superclass that is not `Object`: what [Base] has beside
    /// what an interface has.
    @Nested
    class OfASuperclass {

        private static <Self> SubclassHeader<Self, Base> extendsBase(ClassHeader<Self> h) {
            return h.extends_(Base_.extend(h.self()));
        }

        @Test
        void aStaticMethodAndAnInstanceOneOfOneSignatureAreNotInheritedTogether() {
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            extendsBase(h).implements_(Useful_.implement(h.self()));
                        }
                    }),
                    "one is static and the other an instance method of the same signature",
                    "extends Base implements Useful",
                    "",
                    "overriding method is static");
        }

        @Test
        void aMethodWithPackageAccessIsNotOverridden() {
            // stricter than javac, which goes by the package of the class: to a class of another
            // package the method of Base is no member, and the one of the interface is overridden
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = extendsBase(h);
                            Upcast<Self, Local> asLocal = sh.implements_(Local_.implement(h.self()));
                            began(sh, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()))
                                    .override(asLocal, Local_.local, (b, _) -> b.return_(literal("")));
                        }
                    }),
                    "the method has package access",
                    "extends Base implements Local",
                    "@Override public String local() { return \"\"; }");
        }

        @Test
        void aFinalMethodOfTheSuperclassDoesNotStandForADefaultOne() {
            // javac takes the method of the class for that of the interface; the typed layer has a
            // class that gets a body from each say which it is, and a final method cannot be said
            classUnlikeJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = extendsBase(h);
                            sh.implements_(Sealing_.implement(h.self()));
                            began(sh, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        }
                    }),
                    "unrelated methods with a body each, and overrides neither",
                    "extends Base implements Sealing",
                    "");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = extendsBase(h);
                            Upcast<Self, Sealing> asSealing = sh.implements_(Sealing_.implement(h.self()));
                            began(sh, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()))
                                    .override(asSealing, Sealing_.sealed, (b, _) -> b.return_(literal("")));
                        }
                    }))
                    .withMessageContaining("the method is final (JLS 8.4.3.3)");
        }
    }
}
