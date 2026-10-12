package me.supcheg.javafile.typed;

import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
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
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Defaulted_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Joiner_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Local_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Measured_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
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

import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.typed.Expressions.call;
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
                    "are unrelated methods with a body each",
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
    /// - the method of the superclass stands for the default one of an interface, as to javac
    ///   (`ExtendsTest.Inheriting.theMethodOfTheSuperclassStandsForTheDefaultOneOfAnInterface`,
    ///   [OfASuperclass#aFinalMethodOfTheSuperclassStandsForADefaultOne]) — where it is `public`,
    ///   of the very result and of no exception the other does not write: a narrower result or an
    ///   unchecked exception javac would let stand is for an override to settle
    ///   ([OfASuperclass#aMethodOfTheSuperclassOfANarrowerResultIsOverriddenToStandForADefaultOne]);
    /// - a method of package access of a superclass is none to a class of another package, as to
    ///   javac ([OfASuperclass#aMethodWithPackageAccessIsNoMemberOfAClassOfAnotherPackage]); in
    ///   its package it is neither overridden nor declared over nor had beside one of an
    ///   interface, whatever the packages of the classes between
    ///   ([OfASuperclass#aMethodWithPackageAccessIsNotOverriddenInItsPackage]); a `static` one
    ///   is not called through `super`
    ///   ([ExtendsFormsTest#superCallsAMethodTheClassReachesAndAnInstanceOne]);
    /// - the result of an override, and of a method of the superclass that stands for one of an
    ///   interface, is a subtype of the result of every method of the signature only where the
    ///   facts prove it one ([Subtyping], [SubtypingTest]): not by a wildcard whose bound is
    ///   another type than the type argument
    ///   (`CovariantReturnTest.StricterThanJavac.aWildcardBoundIsMetByTheVeryTypeAlone`), by the
    ///   bound of a type variable, by capture or unchecked conversion, by a supertype a metamodel
    ///   does not record, nor — of the class itself — by a supertype the header names later; and
    ///   a result that is the class itself is said of a method whose result is `Object` alone,
    ///   the brand of the class being no subtype to the compiler of the generator;
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
        void aMethodWithPackageAccessIsNoMemberOfAClassOfAnotherPackage() throws Throwable {
            // String local() of Base has package access: to a class of another package it is no member,
            // and the default one of Local is overridden, or a method is declared by its name
            // — it has to be: javac compiles the class that has neither, and where it runs a call of
            // local() is an IllegalAccessError, the virtual machine selecting the method of Base
            classUnlikeJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = extendsBase(h);
                            sh.implements_(Local_.implement(h.self()));
                            began(sh, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        }
                    }),
                    "IllegalAccessError",
                    "extends Base implements Local",
                    "");
            CompiledClasses overridden = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = extendsBase(h);
                    Upcast<Self, Local> asLocal = sh.implements_(Local_.implement(h.self()));
                    began(sh, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()))
                            .override(asLocal, Local_.local, (b, _) -> b.return_(literal("mine")));
                }
            }));
            assertThat(overridden.invoke("local")).isEqualTo("mine");

            CompiledClasses declared = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    began(extendsBase(h), ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()))
                            .method("local", String_.TOKEN, (b, self) -> b.return_(literal("named")));
                }
            }));
            assertThat(declared.invoke("local")).isEqualTo("named");
        }

        @Test
        void aMethodWithPackageAccessIsNotOverriddenInItsPackage() {
            ClassDesc near = ClassDesc.of("me.supcheg.javafile.typed.fixtures", "Probe");
            // as javac: there the class inherits the method of Base, which is not public as that of Local is
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(), near, new TypedJavaFile.TypedClassHeaderSpec() {
                                @Override
                                public <Self> void build(ClassHeader<Self> h) {
                                    extendsBase(h).implements_(Local_.implement(h.self()));
                                }
                            }))
                    .withMessageContaining("the first has package access");
            CompilationSubject.assertThat(javac().withOptions("-Xlint:all")
                            .compile(JavaFileObjects.forSourceString(
                                    "me.supcheg.javafile.typed.fixtures.Probe",
                                    "package me.supcheg.javafile.typed.fixtures;\n"
                                            + "public final class Probe extends Base implements Local {}\n")))
                    .hadErrorContaining("attempting to assign weaker access privileges; was public");
            // stricter than javac, which lets a class of the package override the method
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> TypedJavaFile.declareClass(
                            UnsafeFacts.unverifiedClasspath(), near, new TypedJavaFile.TypedClassHeaderSpec() {
                                @Override
                                public <Self> void build(ClassHeader<Self> h) {
                                    began(extendsBase(h), ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()))
                                            .method("local", String_.TOKEN, (b, self) -> b.return_(literal("")));
                                }
                            }))
                    .withMessageContaining("a method declared by its name overrides nothing");
            CompilationSubject.assertThat(javac().withOptions("-Xlint:all")
                            .compile(JavaFileObjects.forSourceString(
                                    "me.supcheg.javafile.typed.fixtures.Probe",
                                    "package me.supcheg.javafile.typed.fixtures;\n"
                                            + "public final class Probe extends Base {"
                                            + " @Override public String local() { return \"\"; } }\n")))
                    .succeeded();
        }

        @Test
        void aMethodOfTheSuperclassOfANarrowerResultIsOverriddenToStandForADefaultOne() {
            // stricter than javac, which takes String label() of Mid for CharSequence label(): the typed
            // layer proves a subtype by the fact of a method, and no method of the class is there
            classUnlikeJavac(
                    IllegalStateException.class,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Mid_.extend(h.self()));
                            sh.implements_(Defaulted_.implement(h.self()));
                            began(sh, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                        }
                    }),
                    "cannot stand for label() of me.supcheg.javafile.typed.fixtures.Defaulted: its result is not"
                            + " the one the other has",
                    "extends Mid implements Defaulted",
                    "");
        }

        @Test
        void aFinalMethodOfTheSuperclassStandsForADefaultOne() throws Throwable {
            // final String sealed() of Base, default String sealed() of Sealing: the class wins, as to javac
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = extendsBase(h);
                    Upcast<Self, Sealing> asSealing = sh.implements_(Sealing_.implement(h.self()));
                    var cb = began(sh, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                    cb.method(
                            "go", String_.TOKEN, (b, self) -> b.return_(call(asSealing.apply(self), Sealing_.sealed)));
                }
            }));
            assertThat(compiled.invoke("go")).isEqualTo("base");
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
