package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Shape_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.concurrent.atomic.AtomicReference;

import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// The stages of a declaration out of their turn (§8d, §8e): a stage that is
/// kept and used after the declaration went on is rejected in whatever stage
/// the declaration is by then — while its members are declared, while they
/// are defined, and once it is complete —, and what belongs to another
/// declaration is none of this one. What the Java types of the stages cannot
/// tell is said through an unchecked cast, as a generator that went around
/// them would.
class DeclarationStagesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final String PROBE = "class me.supcheg.example.Probe";

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return file(DESC, spec);
    }

    private static JavaFile file(ClassDesc desc, TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), desc, spec);
    }

    /// What a header that is kept is asked once the header has ended.
    private static void assertEnded(ClassHeader<?> h) {
        assertThatIllegalStateException()
                .isThrownBy(() -> h.overriding(Object_.toString))
                .withMessageContaining("cannot say that " + PROBE + " overrides")
                .withMessageContaining("the header of the class has ended");
        assertThatIllegalStateException()
                .isThrownBy(() -> h.becoming(Runnable_.TOKEN))
                .withMessageContaining(PROBE + " cannot say it is about to be java.lang.Runnable")
                .withMessageContaining("the header of the class has ended");
        assertThatIllegalStateException()
                .isThrownBy(h::extendsObject)
                .withMessageContaining(PROBE + " has its superclass already");
    }

    @Test
    void aHeaderThatIsKeptIsEndedInEveryStageAfterIt() {
        AtomicReference<ClassHeader<?>> kept = new AtomicReference<>();
        file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                assertEnded(h);
                var bodies = cb.bodies();
                assertEnded(h);
                kept.set(h);
                return bodies;
            }
        });

        assertEnded(kept.get());
    }

    @Test
    void theMethodsOfTheClassAreUnknownWhileItsHeaderIsSaid() {
        file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                assertThatIllegalStateException()
                        .isThrownBy(() -> h.self().methods())
                        .withMessageContaining(
                                "the methods of " + PROBE + " are known only once its members are" + " declared");
                var oh = h.extendsObject();
                assertThatIllegalStateException()
                        .isThrownBy(() -> h.self().methods())
                        .withMessageContaining(
                                "the methods of " + PROBE + " are known only once its members are" + " declared");
                var cb = oh.members();
                var m = cb.declareStaticMethod("m", String_.TOKEN);
                var bodies = cb.bodies();
                bodies.define(m, b -> b.return_(literal("m")));
                return bodies;
            }
        });
    }

    @Test
    void theMembersAreDeclaredUntilTheirBodiesBeginAndNotOnceTheClassIsComplete() {
        AtomicReference<TypedClassBuilder<?>> kept = new AtomicReference<>();
        file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var bodies = cb.bodies();
                assertThatIllegalStateException()
                        .isThrownBy(cb::bodies)
                        .withMessageContaining("cannot declare the bodies of " + PROBE + ": the declarations of "
                                + PROBE + " have ended");
                assertThatIllegalStateException()
                        .isThrownBy(() -> cb.declareVoidMethod("late"))
                        .withMessageContaining("the declarations of " + PROBE + " have ended");
                kept.set(cb);
                return bodies;
            }
        });

        assertThatIllegalStateException()
                .isThrownBy(() -> kept.get().declareVoidMethod("late"))
                .withMessageContaining("the declaration of " + PROBE + " is already complete");
        assertThatIllegalStateException()
                .isThrownBy(() -> kept.get().bodies())
                .withMessageContaining("the declaration of " + PROBE + " is already complete");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aSpecificationReturnsTheBodiesOfItsOwnMembers() {
        AtomicReference<MemberBodies<?, ?>> other = new AtomicReference<>();
        file(ClassDesc.of("me.supcheg.example", "Other"), new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var bodies = h.extendsObject().members().bodies();
                other.set(bodies);
                return bodies;
            }
        });

        // before its own members began
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        return (MemberBodies<Self, ?>) other.get();
                    }
                }))
                .withMessageContaining(
                        "the specification of " + PROBE + " returned bodies that are not those of its" + " members");
        // and beside its own
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.extendsObject().members().bodies();
                        return (MemberBodies<Self, ?>) other.get();
                    }
                }))
                .withMessageContaining(
                        "the specification of " + PROBE + " returned bodies that are not those of its" + " members");
    }

    @Test
    @SuppressWarnings("unchecked")
    void aProofIsAboutTheClassThatNamesTheSupertype() {
        AtomicReference<Complete<?, Runnable>> other = new AtomicReference<>();
        file(ClassDesc.of("me.supcheg.example", "Other"), new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var run = h.overriding(Runnable_.run);
                var proof = Runnable_.implement(h.self()).run(run);
                other.set(proof);
                var oh = h.extendsObject();
                oh.implements_(proof);
                var bodies = oh.members().bodies();
                bodies.define(run.method(), (b, self) -> b.end());
                return bodies;
            }
        });

        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.extendsObject().implements_((Complete<Self, Runnable>) other.get());
                        return Unreached.here();
                    }
                }))
                .withMessageContaining("is not a proof about " + PROBE);
    }

    @Test
    void whatAClassHasOfItsSuperclassIsSaidBeforeItsMembers() {
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var sh = h.extends_(Mid_.extend(h.self()));
                        sh.members();
                        sh.inherited(Mid_.label);
                        return Unreached.here();
                    }
                }))
                .withMessageContaining(PROBE + " cannot be said to inherit")
                .withMessageContaining("after the superclass is named and before the members");
    }

    @Test
    void anAbstractMethodOfTheSuperclassIsNoneTheClassHasWithABody() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var eh = h.extending(Shape_.TOKEN);
                        var area = eh.overriding(Shape_.area);
                        var describe = eh.overriding(eh.protected_(Shape_.describe_String));
                        var sh = eh.extends_(Shape_.extend(h.self()).area(area).describe_String(describe));
                        sh.inherited(Shape_.area);
                        return Unreached.here();
                    }
                }))
                .withMessageContaining(PROBE + " does not have area() of")
                .withMessageContaining("the method is abstract there");
    }

    @Test
    @SuppressWarnings("unchecked")
    void superIsCalledOnTheThisOfTheClass() {
        AtomicReference<This<?>> other = new AtomicReference<>();
        file(ClassDesc.of("me.supcheg.example", "Other"), new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var cb = h.extendsObject().members();
                var m = cb.declareVoidMethod("m");
                var bodies = cb.bodies();
                bodies.define(m, (b, self) -> {
                    other.set(self);
                    return b.end();
                });
                return bodies;
            }
        });

        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Mid_.extend(h.self())).members();
                        var constructor = cb.declareConstructor();
                        var bodies = cb.bodies(constructor, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                        bodies.superCall((This<Self>) other.get(), Mid_.label);
                        return Unreached.here();
                    }
                }))
                .withMessageContaining(
                        "super.label(...) is called on this of " + PROBE + ", got this of me.supcheg.example.Other");
    }

    @Test
    void aMethodWithPackageAccessOfTheSuperclassIsNeitherCalledAsSuperNorOverriddenInItsPackage() {
        ClassDesc near = ClassDesc.of("me.supcheg.javafile.typed.fixtures", "Probe");
        // the processor makes no fact of a method that is not accessible: it is vouched for by hand
        var local = UnsafeInvocables.method(Base_.TOKEN, "local", String_.TOKEN, MemberTraits.DEFAULT);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(near, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Base_.extend(h.self())).members();
                        var constructor = cb.declareConstructor();
                        var m = cb.declareMethod("m", String_.TOKEN);
                        var bodies = cb.bodies(constructor, ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        bodies.define(m, (b, self) -> b.return_(bodies.superCall(self, local)));
                        return bodies;
                    }
                }))
                .withMessageContaining("super.local(...) of class me.supcheg.javafile.typed.fixtures.Probe cannot call")
                .withMessageContaining("the method has package access and the class is in its package");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(near, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extends_(Base_.extend(h.self())).members();
                        cb.declareOverride(local);
                        return Unreached.here();
                    }
                }))
                .withMessageContaining("the method has package access and the class is in its package: whether the"
                        + " class inherits it, and so overrides it, depends on the packages of the classes between");
    }

    @Test
    void aClassOfTheUnnamedPackageIsDeclared() {
        ClassDesc unnamed = ClassDesc.of("Probe");

        assertThat(file(unnamed, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                var cb = h.extendsObject().members();
                                var m = cb.declareStaticMethod("m", String_.TOKEN);
                                var bodies = cb.bodies();
                                bodies.define(m, b -> b.return_(literal("unnamed")));
                                return bodies;
                            }
                        })
                        .render())
                .startsWith("public final class Probe {")
                .contains("return \"unnamed\";");
        assertThatIllegalStateException()
                .isThrownBy(() -> file(unnamed, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.extendsObject();
                        h.extendsObject();
                        return Unreached.here();
                    }
                }))
                .withMessageContaining("class Probe has its superclass already");
    }
}
