package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec13;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec14;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec15;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec16;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec9;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec13;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec14;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec15;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec16;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec9;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec13;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec14;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec15;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec16;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec9;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec1;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec10;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec11;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec12;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec13;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec14;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec15;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec16;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec2;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec3;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec4;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec5;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec6;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec7;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec8;
import me.supcheg.javafile.typed.GenericMethodArities.TypeMethodSpec9;
import me.supcheg.javafile.typed.NestedDeclarations.ClassBodies;
import me.supcheg.javafile.typed.NestedDeclarations.InterfaceBodies;
import me.supcheg.javafile.typed.NestedDeclarations.NestedType;
import me.supcheg.javafile.typed.NestedDeclarations.RecordBodies;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class TypeMemberDeclarationArities<Self> extends TypeDeclarationSupport<Self> permits TypeMemberDeclarations {
    TypeMemberDeclarationArities(TargetClasspath target, ClassDesc desc, String kind) {
        super(target, desc, kind);
    }

    /**
     * Declares a generic member class with four type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     */
    public <N, B1, B2, B3, B4> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypedGenericClassSpec4<B1, B2, B3, B4> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with four type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     */
    public <I, B1, B2, B3, B4> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypedGenericInterfaceSpec4<B1, B2, B3, B4> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with four type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     */
    public <R, B1, B2, B3, B4> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypedGenericRecordSpec4<B1, B2, B3, B4> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with five type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     */
    public <N, B1, B2, B3, B4, B5> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypedGenericClassSpec5<B1, B2, B3, B4, B5> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with five type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     */
    public <I, B1, B2, B3, B4, B5> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypedGenericInterfaceSpec5<B1, B2, B3, B4, B5> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with five type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     */
    public <R, B1, B2, B3, B4, B5> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypedGenericRecordSpec5<B1, B2, B3, B4, B5> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with six type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypedGenericClassSpec6<B1, B2, B3, B4, B5, B6> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with six type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypedGenericInterfaceSpec6<B1, B2, B3, B4, B5, B6> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with six type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypedGenericRecordSpec6<B1, B2, B3, B4, B5, B6> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with seven type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypedGenericClassSpec7<B1, B2, B3, B4, B5, B6, B7> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with seven type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypedGenericInterfaceSpec7<B1, B2, B3, B4, B5, B6, B7> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with seven type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypedGenericRecordSpec7<B1, B2, B3, B4, B5, B6, B7> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with eight type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypedGenericClassSpec8<B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with eight type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypedGenericInterfaceSpec8<B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with eight type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypedGenericRecordSpec8<B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with nine type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypedGenericClassSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with nine type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypedGenericInterfaceSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with nine type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypedGenericRecordSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with ten type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypedGenericClassSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with ten type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypedGenericInterfaceSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with ten type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypedGenericRecordSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with eleven type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypedGenericClassSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9), NestedDeclarations.variable(declaration, 10));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with eleven type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypedGenericInterfaceSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9), NestedDeclarations.variable(ib, 10));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with eleven type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypedGenericRecordSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9), NestedDeclarations.variable(rb, 10));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with twelve type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypedGenericClassSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9), NestedDeclarations.variable(declaration, 10), NestedDeclarations.variable(declaration, 11));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with twelve type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypedGenericInterfaceSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9), NestedDeclarations.variable(ib, 10), NestedDeclarations.variable(ib, 11));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with twelve type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypedGenericRecordSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9), NestedDeclarations.variable(rb, 10), NestedDeclarations.variable(rb, 11));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with thirteen type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypedGenericClassSpec13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9), NestedDeclarations.variable(declaration, 10), NestedDeclarations.variable(declaration, 11), NestedDeclarations.variable(declaration, 12));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with thirteen type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypedGenericInterfaceSpec13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9), NestedDeclarations.variable(ib, 10), NestedDeclarations.variable(ib, 11), NestedDeclarations.variable(ib, 12));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with thirteen type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypedGenericRecordSpec13<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9), NestedDeclarations.variable(rb, 10), NestedDeclarations.variable(rb, 11), NestedDeclarations.variable(rb, 12));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with fourteen type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypedGenericClassSpec14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9), NestedDeclarations.variable(declaration, 10), NestedDeclarations.variable(declaration, 11), NestedDeclarations.variable(declaration, 12), NestedDeclarations.variable(declaration, 13));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with fourteen type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypedGenericInterfaceSpec14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9), NestedDeclarations.variable(ib, 10), NestedDeclarations.variable(ib, 11), NestedDeclarations.variable(ib, 12), NestedDeclarations.variable(ib, 13));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with fourteen type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypedGenericRecordSpec14<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9), NestedDeclarations.variable(rb, 10), NestedDeclarations.variable(rb, 11), NestedDeclarations.variable(rb, 12), NestedDeclarations.variable(rb, 13));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with fifteen type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param p15 the fifteenth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypedGenericClassSpec15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9), NestedDeclarations.variable(declaration, 10), NestedDeclarations.variable(declaration, 11), NestedDeclarations.variable(declaration, 12), NestedDeclarations.variable(declaration, 13), NestedDeclarations.variable(declaration, 14));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with fifteen type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param p15 the fifteenth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypedGenericInterfaceSpec15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9), NestedDeclarations.variable(ib, 10), NestedDeclarations.variable(ib, 11), NestedDeclarations.variable(ib, 12), NestedDeclarations.variable(ib, 13), NestedDeclarations.variable(ib, 14));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with fifteen type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param p15 the fifteenth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypedGenericRecordSpec15<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9), NestedDeclarations.variable(rb, 10), NestedDeclarations.variable(rb, 11), NestedDeclarations.variable(rb, 12), NestedDeclarations.variable(rb, 13), NestedDeclarations.variable(rb, 14));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member class with sixteen type parameters.
     *
     * @param name the simple name of the member class
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param p15 the fifteenth type parameter
     * @param p16 the sixteenth type parameter
     * @param spec declares the member class, given its header and the tokens of its type parameters
     * @param <N> the brand of the member class
     * @return the token of the member class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member class, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member class is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter
     * @param <B16> the leftmost bound of the sixteenth type parameter
     */
    public <N, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> FinalClassToken<N> declareNestedClass(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypeParameter<B16> p16, TypedGenericClassSpec16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> spec) {
        requireDeclaring("a member class of " + described());
        NestedType<FinalClassToken<N>> nested = NestedDeclarations.class_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), List.of(), new ClassBodies() {
            @Override
            public <N2> MemberBodies<N2, ?> build(ClassHeader<N2> h, ClassDeclaration<N2> declaration) {
                return spec.build(h, NestedDeclarations.variable(declaration, 0), NestedDeclarations.variable(declaration, 1), NestedDeclarations.variable(declaration, 2), NestedDeclarations.variable(declaration, 3), NestedDeclarations.variable(declaration, 4), NestedDeclarations.variable(declaration, 5), NestedDeclarations.variable(declaration, 6), NestedDeclarations.variable(declaration, 7), NestedDeclarations.variable(declaration, 8), NestedDeclarations.variable(declaration, 9), NestedDeclarations.variable(declaration, 10), NestedDeclarations.variable(declaration, 11), NestedDeclarations.variable(declaration, 12), NestedDeclarations.variable(declaration, 13), NestedDeclarations.variable(declaration, 14), NestedDeclarations.variable(declaration, 15));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member interface with sixteen type parameters.
     *
     * @param name the simple name of the member interface
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param p15 the fifteenth type parameter
     * @param p16 the sixteenth type parameter
     * @param spec declares the member interface, given its builder and the tokens of its type parameters
     * @param <I> the brand of the member interface
     * @return the token of the member interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member interface, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member interface is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter
     * @param <B16> the leftmost bound of the sixteenth type parameter
     */
    public <I, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> InterfaceToken<I> declareNestedInterface(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypeParameter<B16> p16, TypedGenericInterfaceSpec16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> spec) {
        requireDeclaring("a member interface of " + described());
        NestedType<InterfaceToken<I>> nested = NestedDeclarations.interface_(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), new InterfaceBodies() {
            @Override
            public <I2> TypedInterfaceBodies<I2> build(TypedInterfaceBuilder<I2> ib) {
                return spec.build(ib, NestedDeclarations.variable(ib, 0), NestedDeclarations.variable(ib, 1), NestedDeclarations.variable(ib, 2), NestedDeclarations.variable(ib, 3), NestedDeclarations.variable(ib, 4), NestedDeclarations.variable(ib, 5), NestedDeclarations.variable(ib, 6), NestedDeclarations.variable(ib, 7), NestedDeclarations.variable(ib, 8), NestedDeclarations.variable(ib, 9), NestedDeclarations.variable(ib, 10), NestedDeclarations.variable(ib, 11), NestedDeclarations.variable(ib, 12), NestedDeclarations.variable(ib, 13), NestedDeclarations.variable(ib, 14), NestedDeclarations.variable(ib, 15));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    /**
     * Declares a generic member record with sixteen type parameters.
     *
     * @param name the simple name of the member record
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @param p10 the tenth type parameter
     * @param p11 the eleventh type parameter
     * @param p12 the twelfth type parameter
     * @param p13 the thirteenth type parameter
     * @param p14 the fourteenth type parameter
     * @param p15 the fifteenth type parameter
     * @param p16 the sixteenth type parameter
     * @param spec declares the member record, given its builder and the tokens of its type parameters
     * @param <R> the brand of the member record
     * @return the token of the member record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the member record, or its bound is a raw type; or if `name` cannot name a type
     * @throws IllegalStateException if the declarations of the type have ended, or a member of the member record is not defined
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter
     * @param <B16> the leftmost bound of the sixteenth type parameter
     */
    public <R, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> FinalClassToken<R> declareNestedRecord(String name, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypeParameter<B16> p16, TypedGenericRecordSpec16<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> spec) {
        requireDeclaring("a member record of " + described());
        NestedType<FinalClassToken<R>> nested = NestedDeclarations.record(target(), desc(), name, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), new RecordBodies() {
            @Override
            public <R2> TypedRecordBodies<R2> build(TypedRecordBuilder<R2> rb) {
                return spec.build(rb, NestedDeclarations.variable(rb, 0), NestedDeclarations.variable(rb, 1), NestedDeclarations.variable(rb, 2), NestedDeclarations.variable(rb, 3), NestedDeclarations.variable(rb, 4), NestedDeclarations.variable(rb, 5), NestedDeclarations.variable(rb, 6), NestedDeclarations.variable(rb, 7), NestedDeclarations.variable(rb, 8), NestedDeclarations.variable(rb, 9), NestedDeclarations.variable(rb, 10), NestedDeclarations.variable(rb, 11), NestedDeclarations.variable(rb, 12), NestedDeclarations.variable(rb, 13), NestedDeclarations.variable(rb, 14), NestedDeclarations.variable(rb, 15));
            }
        });
        declaredNested(nested.type());
        return nested.token();
    }

    abstract <F extends Invocable> F genericMethod(List<TypeParameter<?>> parameters, TypeGenericMethodBodies<Self, F> spec);

    /**
     * Declares a generic method of the type with one type parameter: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the token of its type parameter
     * @param p1 the type parameter
     * @param <B1> the leftmost bound of the type parameter, `Object` for none
     */
    public <F extends Invocable, B1> F declareGenericMethod(TypeParameter<B1> p1, TypeMethodSpec1<Self, F, B1> spec) {
        return genericMethod(List.of(p1), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0));
            }
        });
    }

    /**
     * Declares a generic method of the type with two type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeMethodSpec2<Self, F, B1, B2> spec) {
        return genericMethod(List.of(p1, p2), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1));
            }
        });
    }

    /**
     * Declares a generic method of the type with three type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeMethodSpec3<Self, F, B1, B2, B3> spec) {
        return genericMethod(List.of(p1, p2, p3), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2));
            }
        });
    }

    /**
     * Declares a generic method of the type with four type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeMethodSpec4<Self, F, B1, B2, B3, B4> spec) {
        return genericMethod(List.of(p1, p2, p3, p4), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3));
            }
        });
    }

    /**
     * Declares a generic method of the type with five type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeMethodSpec5<Self, F, B1, B2, B3, B4, B5> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4));
            }
        });
    }

    /**
     * Declares a generic method of the type with six type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeMethodSpec6<Self, F, B1, B2, B3, B4, B5, B6> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5));
            }
        });
    }

    /**
     * Declares a generic method of the type with seven type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeMethodSpec7<Self, F, B1, B2, B3, B4, B5, B6, B7> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6));
            }
        });
    }

    /**
     * Declares a generic method of the type with eight type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeMethodSpec8<Self, F, B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7));
            }
        });
    }

    /**
     * Declares a generic method of the type with nine type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeMethodSpec9<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8));
            }
        });
    }

    /**
     * Declares a generic method of the type with ten type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeMethodSpec10<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9));
            }
        });
    }

    /**
     * Declares a generic method of the type with eleven type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param p11 the eleventh type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeMethodSpec11<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9), declaration.variable(10));
            }
        });
    }

    /**
     * Declares a generic method of the type with twelve type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param p11 the eleventh type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param p12 the twelfth type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeMethodSpec12<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9), declaration.variable(10), declaration.variable(11));
            }
        });
    }

    /**
     * Declares a generic method of the type with thirteen type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param p11 the eleventh type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param p12 the twelfth type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     * @param p13 the thirteenth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeMethodSpec13<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9), declaration.variable(10), declaration.variable(11), declaration.variable(12));
            }
        });
    }

    /**
     * Declares a generic method of the type with fourteen type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param p11 the eleventh type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param p12 the twelfth type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     * @param p13 the thirteenth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param p14 the fourteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeMethodSpec14<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9), declaration.variable(10), declaration.variable(11), declaration.variable(12), declaration.variable(13));
            }
        });
    }

    /**
     * Declares a generic method of the type with fifteen type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param p11 the eleventh type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param p12 the twelfth type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     * @param p13 the thirteenth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param p14 the fourteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param p15 the fifteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypeMethodSpec15<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9), declaration.variable(10), declaration.variable(11), declaration.variable(12), declaration.variable(13), declaration.variable(14));
            }
        });
    }

    /**
     * Declares a generic method of the type with sixteen type parameters: a method, an abstract one, a `default` or `private` one of an interface, or a `static` one, whose type parameters are in scope in the signature and the body of that member alone (JLS 6.3). The fact of the member is the `F` of the specification, which no type of the call below tells: name it, `new TypeMethodSpec1&lt;Self, MethodRef1&lt;Self, B1, B1&gt;, B1&gt;()`, where a diamond would leave it an `Invocable`.
     *
     * @return the fact of the member declared
     * @throws IllegalArgumentException if a type parameter has the name of another or of the type, or its bound names a type parameter out of its scope
     * @throws IllegalStateException if the declarations of the type have ended
     * @param spec declares the member, given the declarations of the members and the tokens of its type parameters
     * @param p1 the first type parameter
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param p2 the second type parameter
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param p3 the third type parameter
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param p4 the fourth type parameter
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param p5 the fifth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param p6 the sixth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param p7 the seventh type parameter
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param p8 the eighth type parameter
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param p9 the ninth type parameter
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param p10 the tenth type parameter
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param p11 the eleventh type parameter
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param p12 the twelfth type parameter
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     * @param p13 the thirteenth type parameter
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param p14 the fourteenth type parameter
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param p15 the fifteenth type parameter
     * @param <B15> the leftmost bound of the fifteenth type parameter, `Object` for none
     * @param p16 the sixteenth type parameter
     * @param <B16> the leftmost bound of the sixteenth type parameter, `Object` for none
     */
    public <F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> F declareGenericMethod(TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypeParameter<B13> p13, TypeParameter<B14> p14, TypeParameter<B15> p15, TypeParameter<B16> p16, TypeMethodSpec16<Self, F, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> spec) {
        return genericMethod(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), new TypeGenericMethodBodies<Self, F>() {
            @Override
            public F build(TypeGenericMethodDeclaration<Self> declaration) {
                return spec.build(declaration, declaration.variable(0), declaration.variable(1), declaration.variable(2), declaration.variable(3), declaration.variable(4), declaration.variable(5), declaration.variable(6), declaration.variable(7), declaration.variable(8), declaration.variable(9), declaration.variable(10), declaration.variable(11), declaration.variable(12), declaration.variable(13), declaration.variable(14), declaration.variable(15));
            }
        });
    }
}
