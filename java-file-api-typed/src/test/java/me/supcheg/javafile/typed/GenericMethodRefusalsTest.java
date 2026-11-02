package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Generic_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;

import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// What a generic method of a class refuses (§8g): a type parameter named twice or as one of the class, a
/// bound that names a type parameter of the class, a type parameter of a method out of the member it is
/// declared in — the signature of another method, a field, a member type —, a call with the wrong type
/// arguments, and an override of a generic method by a fact that names no type variable or other type
/// parameters.
class GenericMethodRefusalsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    /// What a test declares of the members of the class, given their builder.
    private interface Members {
        <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h);
    }

    /// The class `Probe`, extending `Object`, whose members `members` declares.
    private static JavaFile probe(Members members) {
        return TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        return members.build(h);
                    }
                });
    }

    /// A place for a test to keep the token of a type parameter the specification is given.
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static TypeVarToken<Object>[] captured() {
        // the token of a type parameter of `Object`'s bound, which every test of a refusal names
        return (TypeVarToken<Object>[]) new TypeVarToken[1];
    }

    /// The specification of `<T> T id(T)`, whose token `captured` takes.
    private static <Self>
            GenericMethodArities.MethodSpec1<Self, Object, MethodRef1<Self, Object, Object>, Object> identity(
                    TypeVarToken<Object>[] captured) {
        return new GenericMethodArities.MethodSpec1<>() {
            @Override
            public MethodRef1<Self, Object, Object> build(
                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> t) {
                captured[0] = t;
                return m.declareMethod("id", t, t);
            }
        };
    }

    @Test
    void aTypeParameterIsNamedOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(
                                typeParam("T"),
                                typeParam("T"),
                                new GenericMethodArities.MethodSpec2<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t,
                                            TypeVarToken<Object> u) {
                                        return m.declareMethod("id", t, t);
                                    }
                                });
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("declares the type parameter T twice");
    }

    @Test
    void aTypeParameterIsNotNamedAsOneOfTheClass() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new GenericDeclarationArities.TypedGenericClassSpec1<Object>() {
                            @Override
                            public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                                var cb = h.extendsObject().members();
                                cb.declareGenericMethod(
                                        typeParam("T"),
                                        new GenericMethodArities.MethodSpec1<
                                                Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                            @Override
                                            public MethodRef1<Self, Object, Object> build(
                                                    GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> u) {
                                                return m.declareMethod("id", u, u);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("the class declares a type parameter of that name");
    }

    @Test
    void aTypeParameterOfAMethodIsNotInScopeInAnotherMethod() {
        TypeVarToken<Object>[] captured = captured();
        assertThatIllegalStateException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(typeParam("T"), identity(captured));
                        cb.declareMethod("other", captured[0], captured[0]);
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("is not in scope in the signature of");
    }

    @Test
    void aTypeParameterOfAMethodIsNotInScopeInAField() {
        TypeVarToken<Object>[] captured = captured();
        assertThatIllegalStateException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(typeParam("T"), identity(captured));
                        cb.declareField("held", captured[0]);
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("is not in scope in the type of field held");
    }

    @Test
    void aTypeParameterOfAMethodIsNotInScopeInAMemberType() {
        TypeVarToken<Object>[] captured = captured();
        assertThatIllegalStateException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(typeParam("T"), identity(captured));
                        cb.declareNestedClass("Inner", new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Inner> MemberBodies<Inner, ?> build(ClassHeader<Inner> inner) {
                                var members = inner.extendsObject().members();
                                members.declareField("held", captured[0]);
                                return members.bodies();
                            }
                        });
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("is not in scope in");
    }

    @Test
    void aBoundNamesNoTypeParameterOfTheClass() {
        assertThatIllegalStateException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(),
                        DESC,
                        typeParam("T"),
                        new GenericDeclarationArities.TypedGenericClassSpec1<Object>() {
                            @Override
                            public <Self, T> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T> t) {
                                var cb = h.extendsObject().members();
                                cb.declareGenericMethod(
                                        typeParam("U", new List_<>(t).token),
                                        new GenericMethodArities.MethodSpec1<
                                                Self,
                                                Object,
                                                MethodRef1<Self, java.util.List<T>, java.util.List<T>>,
                                                java.util.List<T>>() {
                                            @Override
                                            public MethodRef1<Self, java.util.List<T>, java.util.List<T>> build(
                                                    GenericMethodDeclaration<Self, Object> m,
                                                    TypeVarToken<java.util.List<T>> u) {
                                                return m.declareMethod("id", u, u);
                                            }
                                        });
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("which is no type parameter of the member");
    }

    @Test
    void aCallTakesOneTypeArgumentPerTypeParameter() {
        TypeVarToken<Object>[] captured = captured();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        MethodRef1<Self, Object, Object> id =
                                cb.declareGenericMethod(typeParam("T"), identity(captured));
                        id.withTypeArgs();
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("and a call of it takes one type argument per one of them");
    }

    @Test
    void aMethodThatIsNotGenericTakesNoTypeArgument() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        var plain = cb.declareMethod("plain", String_.TOKEN, String_.TOKEN);
                        plain.withTypeArgs(String_.TOKEN);
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("the method has no type parameter of its own");
    }

    @Test
    void aCallTakesOneTypeArgumentPerEachOfTwoTypeParameters() {
        TypeVarToken<Object>[] captured = captured();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        MethodRef1<Self, Object, Object> pair = cb.declareGenericMethod(
                                typeParam("T"),
                                typeParam("U"),
                                new GenericMethodArities.MethodSpec2<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m,
                                            TypeVarToken<Object> t,
                                            TypeVarToken<Object> u) {
                                        captured[0] = u;
                                        return m.declareMethod("pair", t, t);
                                    }
                                });
                        pair.withTypeArgs(String_.TOKEN);
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("the method declares 2 type parameters");
    }

    @Test
    void anOverrideOfAGenericMethodNamesATypeVariable() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.declareClass(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                            @Override
                            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                                var sh = h.extends_(Generic_.extend(h.self()));
                                var cb = sh.members();
                                // a class `CharSequence` where the method has the type variable `K`
                                cb.declareOverride(Generic_.length_K(CharSequence_.TOKEN));
                                var ctor = cb.declareConstructor();
                                return cb.bodies(ctor, c -> c.super_(Generic_.new_, (b, self) -> b.end()));
                            }
                        }))
                .withMessageContaining("which is no type variable");
    }

    @Test
    void aBoundIsATypeParameterOfTheMember() {
        var other = UnsafeFacts.typeVarToken(Types.typeVar("T"), ConstantDescs.CD_Object);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(
                                typeParam("C", CharSequence_.TOKEN),
                                new GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, CharSequence, CharSequence>, CharSequence>() {
                                    @Override
                                    public MethodRef1<Self, CharSequence, CharSequence> build(
                                            GenericMethodDeclaration<Self, Object> m, TypeVarToken<CharSequence> c) {
                                        m.bound(other, new Comparable_<>(c).token);
                                        return m.declareMethod("pick", c, c);
                                    }
                                });
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("is no type parameter of the member being declared");
    }

    @Test
    void aBoundIsSaidOnce() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(
                                typeParam("C", CharSequence_.TOKEN),
                                new GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, CharSequence, CharSequence>, CharSequence>() {
                                    @Override
                                    public MethodRef1<Self, CharSequence, CharSequence> build(
                                            GenericMethodDeclaration<Self, Object> m, TypeVarToken<CharSequence> c) {
                                        m.bound(c, new Comparable_<>(c).token);
                                        m.bound(c, new Comparable_<>(c).token);
                                        return m.declareMethod("pick", c, c);
                                    }
                                });
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("already: a bound is said once");
    }

    @Test
    void aBoundIsNoRawType() {
        assertThatIllegalStateException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(
                                typeParam("C", CharSequence_.TOKEN),
                                new GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, CharSequence, CharSequence>, CharSequence>() {
                                    @Override
                                    public MethodRef1<Self, CharSequence, CharSequence> build(
                                            GenericMethodDeclaration<Self, Object> m, TypeVarToken<CharSequence> c) {
                                        // the raw `List` of a bound, which javac warns of
                                        m.bound(c, UnsafeFacts.interfaceToken(List_.Data.SHAPE));
                                        return m.declareMethod("pick", c, c);
                                    }
                                });
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("the raw type");
    }

    @Test
    void aTypeParameterHasNoRawBound() {
        assertThatIllegalStateException()
                .isThrownBy(() -> probe(new Members() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var cb = h.extendsObject().members();
                        cb.declareGenericMethod(
                                typeParam("U", UnsafeFacts.interfaceToken(List_.Data.SHAPE)),
                                new GenericMethodArities.MethodSpec1<
                                        Self, Object, MethodRef1<Self, Object, Object>, Object>() {
                                    @Override
                                    public MethodRef1<Self, Object, Object> build(
                                            GenericMethodDeclaration<Self, Object> m, TypeVarToken<Object> u) {
                                        return m.declareMethod("id", u, u);
                                    }
                                });
                        return cb.bodies();
                    }
                }))
                .withMessageContaining("the raw type");
    }
}
