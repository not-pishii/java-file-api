package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec1;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec2;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec3;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericClassSpec9;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec1;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec2;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec3;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericInterfaceSpec9;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec1;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec10;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec11;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec12;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec2;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec3;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec4;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec5;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec6;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec7;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec8;
import me.supcheg.javafile.typed.GenericDeclarationArities.TypedGenericRecordSpec9;
import me.supcheg.javafile.typed.GenericDeclarations.ClassBodies;
import me.supcheg.javafile.typed.GenericDeclarations.InterfaceBodies;
import me.supcheg.javafile.typed.GenericDeclarations.RecordBodies;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class GenericDeclarationArities permits TypedJavaFile {
    /**
     * The specification of a generic class with one type parameter, whose leftmost bound is `B1`.
     *
     * @param <B1> the leftmost bound of the type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec1<B1> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the token of its type parameter.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1);
    }

    /**
     * The specification of a generic interface with one type parameter, whose leftmost bound is `B1`.
     *
     * @param <B1> the leftmost bound of the type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec1<B1> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the token of its type parameter.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1);
    }

    /**
     * The specification of a generic record with one type parameter, whose leftmost bound is `B1`.
     *
     * @param <B1> the leftmost bound of the type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec1<B1> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the token of its type parameter.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1);
    }

    /**
     * The specification of a generic class with two type parameters, whose leftmost bounds are `B1` and `B2`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec2<B1, B2> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2);
    }

    /**
     * The specification of a generic interface with two type parameters, whose leftmost bounds are `B1` and `B2`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec2<B1, B2> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2);
    }

    /**
     * The specification of a generic record with two type parameters, whose leftmost bounds are `B1` and `B2`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec2<B1, B2> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2);
    }

    /**
     * The specification of a generic class with three type parameters, whose leftmost bounds are `B1`, `B2` and `B3`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec3<B1, B2, B3> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3);
    }

    /**
     * The specification of a generic interface with three type parameters, whose leftmost bounds are `B1`, `B2` and `B3`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec3<B1, B2, B3> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3);
    }

    /**
     * The specification of a generic record with three type parameters, whose leftmost bounds are `B1`, `B2` and `B3`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec3<B1, B2, B3> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3);
    }

    /**
     * The specification of a generic class with four type parameters, whose leftmost bounds are `B1`, `B2`, `B3` and `B4`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec4<B1, B2, B3, B4> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4);
    }

    /**
     * The specification of a generic interface with four type parameters, whose leftmost bounds are `B1`, `B2`, `B3` and `B4`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec4<B1, B2, B3, B4> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4);
    }

    /**
     * The specification of a generic record with four type parameters, whose leftmost bounds are `B1`, `B2`, `B3` and `B4`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec4<B1, B2, B3, B4> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4);
    }

    /**
     * The specification of a generic class with five type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4` and `B5`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec5<B1, B2, B3, B4, B5> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5);
    }

    /**
     * The specification of a generic interface with five type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4` and `B5`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec5<B1, B2, B3, B4, B5> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5);
    }

    /**
     * The specification of a generic record with five type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4` and `B5`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec5<B1, B2, B3, B4, B5> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5);
    }

    /**
     * The specification of a generic class with six type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5` and `B6`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec6<B1, B2, B3, B4, B5, B6> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6);
    }

    /**
     * The specification of a generic interface with six type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5` and `B6`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec6<B1, B2, B3, B4, B5, B6> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6);
    }

    /**
     * The specification of a generic record with six type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5` and `B6`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec6<B1, B2, B3, B4, B5, B6> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6);
    }

    /**
     * The specification of a generic class with seven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6` and `B7`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec7<B1, B2, B3, B4, B5, B6, B7> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7);
    }

    /**
     * The specification of a generic interface with seven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6` and `B7`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec7<B1, B2, B3, B4, B5, B6, B7> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7);
    }

    /**
     * The specification of a generic record with seven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6` and `B7`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec7<B1, B2, B3, B4, B5, B6, B7> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7);
    }

    /**
     * The specification of a generic class with eight type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7` and `B8`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec8<B1, B2, B3, B4, B5, B6, B7, B8> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8);
    }

    /**
     * The specification of a generic interface with eight type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7` and `B8`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec8<B1, B2, B3, B4, B5, B6, B7, B8> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8);
    }

    /**
     * The specification of a generic record with eight type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7` and `B8`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec8<B1, B2, B3, B4, B5, B6, B7, B8> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8);
    }

    /**
     * The specification of a generic class with nine type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8` and `B9`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9);
    }

    /**
     * The specification of a generic interface with nine type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8` and `B9`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9);
    }

    /**
     * The specification of a generic record with nine type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8` and `B9`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9);
    }

    /**
     * The specification of a generic class with ten type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9` and `B10`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10);
    }

    /**
     * The specification of a generic interface with ten type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9` and `B10`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10);
    }

    /**
     * The specification of a generic record with ten type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9` and `B10`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10);
    }

    /**
     * The specification of a generic class with eleven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10` and `B11`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param t11 the token of the eleventh type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @param <T11> the brand of the eleventh type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10, T11 extends B11> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11);
    }

    /**
     * The specification of a generic interface with eleven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10` and `B11`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param t11 the token of the eleventh type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @param <T11> the brand of the eleventh type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10, T11 extends B11> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11);
    }

    /**
     * The specification of a generic record with eleven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10` and `B11`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param t11 the token of the eleventh type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @param <T11> the brand of the eleventh type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10, T11 extends B11> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11);
    }

    /**
     * The specification of a generic class with twelve type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11` and `B12`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericClassSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> {
        /**
         * Declares the class through `h`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param h the self-branded first stage of the header of the class
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param t11 the token of the eleventh type parameter
         * @param t12 the token of the twelfth type parameter
         * @param <Self> the brand of the class being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @param <T11> the brand of the eleventh type parameter
         * @param <T12> the brand of the twelfth type parameter
         * @return the bodies of the members: the stage the declaration of the class ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10, T11 extends B11, T12 extends B12> MemberBodies<Self, ?> build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12);
    }

    /**
     * The specification of a generic interface with twelve type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11` and `B12`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericInterfaceSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> {
        /**
         * Declares the interface through `ib`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param ib the self-branded builder of the interface
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param t11 the token of the eleventh type parameter
         * @param t12 the token of the twelfth type parameter
         * @param <Self> the brand of the interface being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @param <T11> the brand of the eleventh type parameter
         * @param <T12> the brand of the twelfth type parameter
         * @return the bodies of the members: the stage the declaration of the interface ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10, T11 extends B11, T12 extends B12> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> ib, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12);
    }

    /**
     * The specification of a generic record with twelve type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11` and `B12`.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <B9> the leftmost bound of the ninth type parameter, `Object` for none
     * @param <B10> the leftmost bound of the tenth type parameter, `Object` for none
     * @param <B11> the leftmost bound of the eleventh type parameter, `Object` for none
     * @param <B12> the leftmost bound of the twelfth type parameter, `Object` for none
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypedGenericRecordSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> {
        /**
         * Declares the record through `rb`, branded with a fresh `Self`, with the tokens of its type parameters.
         *
         * @param rb the self-branded builder of the record
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @param t10 the token of the tenth type parameter
         * @param t11 the token of the eleventh type parameter
         * @param t12 the token of the twelfth type parameter
         * @param <Self> the brand of the record being declared
         * @param <T1> the brand of the first type parameter
         * @param <T2> the brand of the second type parameter
         * @param <T3> the brand of the third type parameter
         * @param <T4> the brand of the fourth type parameter
         * @param <T5> the brand of the fifth type parameter
         * @param <T6> the brand of the sixth type parameter
         * @param <T7> the brand of the seventh type parameter
         * @param <T8> the brand of the eighth type parameter
         * @param <T9> the brand of the ninth type parameter
         * @param <T10> the brand of the tenth type parameter
         * @param <T11> the brand of the eleventh type parameter
         * @param <T12> the brand of the twelfth type parameter
         * @return the bodies of the members: the stage the declaration of the record ends in
         */
        <Self, T1 extends B1, T2 extends B2, T3 extends B3, T4 extends B4, T5 extends B5, T6 extends B6, T7 extends B7, T8 extends B8, T9 extends B9, T10 extends B10, T11 extends B11, T12 extends B12> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> rb, TypeVarToken<T1> t1, TypeVarToken<T2> t2, TypeVarToken<T3> t3, TypeVarToken<T4> t4, TypeVarToken<T5> t5, TypeVarToken<T6> t6, TypeVarToken<T7> t7, TypeVarToken<T8> t8, TypeVarToken<T9> t9, TypeVarToken<T10> t10, TypeVarToken<T11> t11, TypeVarToken<T12> t12);
    }

    /**
     * Declares a generic `public final class` with one type parameter: the token is in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the type parameter
     */
    public static <B1> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypedGenericClassSpec1<B1> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0));
            }
        });
    }

    /**
     * Declares a generic `public interface` with one type parameter: the token is in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the type parameter
     */
    public static <B1> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypedGenericInterfaceSpec1<B1> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0));
            }
        });
    }

    /**
     * Declares a generic `public record` with one type parameter: the token is in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the type parameter
     */
    public static <B1> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypedGenericRecordSpec1<B1> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0));
            }
        });
    }

    /**
     * Declares a generic `public final class` with two type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     */
    public static <B1, B2> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypedGenericClassSpec2<B1, B2> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1));
            }
        });
    }

    /**
     * Declares a generic `public interface` with two type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     */
    public static <B1, B2> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypedGenericInterfaceSpec2<B1, B2> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1));
            }
        });
    }

    /**
     * Declares a generic `public record` with two type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     */
    public static <B1, B2> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypedGenericRecordSpec2<B1, B2> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1));
            }
        });
    }

    /**
     * Declares a generic `public final class` with three type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     */
    public static <B1, B2, B3> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypedGenericClassSpec3<B1, B2, B3> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2));
            }
        });
    }

    /**
     * Declares a generic `public interface` with three type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     */
    public static <B1, B2, B3> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypedGenericInterfaceSpec3<B1, B2, B3> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2));
            }
        });
    }

    /**
     * Declares a generic `public record` with three type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     */
    public static <B1, B2, B3> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypedGenericRecordSpec3<B1, B2, B3> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2));
            }
        });
    }

    /**
     * Declares a generic `public final class` with four type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     */
    public static <B1, B2, B3, B4> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypedGenericClassSpec4<B1, B2, B3, B4> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3));
            }
        });
    }

    /**
     * Declares a generic `public interface` with four type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     */
    public static <B1, B2, B3, B4> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypedGenericInterfaceSpec4<B1, B2, B3, B4> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3));
            }
        });
    }

    /**
     * Declares a generic `public record` with four type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     */
    public static <B1, B2, B3, B4> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypedGenericRecordSpec4<B1, B2, B3, B4> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3));
            }
        });
    }

    /**
     * Declares a generic `public final class` with five type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     */
    public static <B1, B2, B3, B4, B5> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypedGenericClassSpec5<B1, B2, B3, B4, B5> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4));
            }
        });
    }

    /**
     * Declares a generic `public interface` with five type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     */
    public static <B1, B2, B3, B4, B5> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypedGenericInterfaceSpec5<B1, B2, B3, B4, B5> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4));
            }
        });
    }

    /**
     * Declares a generic `public record` with five type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     */
    public static <B1, B2, B3, B4, B5> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypedGenericRecordSpec5<B1, B2, B3, B4, B5> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4));
            }
        });
    }

    /**
     * Declares a generic `public final class` with six type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     */
    public static <B1, B2, B3, B4, B5, B6> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypedGenericClassSpec6<B1, B2, B3, B4, B5, B6> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5));
            }
        });
    }

    /**
     * Declares a generic `public interface` with six type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     */
    public static <B1, B2, B3, B4, B5, B6> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypedGenericInterfaceSpec6<B1, B2, B3, B4, B5, B6> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5));
            }
        });
    }

    /**
     * Declares a generic `public record` with six type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     */
    public static <B1, B2, B3, B4, B5, B6> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypedGenericRecordSpec6<B1, B2, B3, B4, B5, B6> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5));
            }
        });
    }

    /**
     * Declares a generic `public final class` with seven type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     */
    public static <B1, B2, B3, B4, B5, B6, B7> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypedGenericClassSpec7<B1, B2, B3, B4, B5, B6, B7> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5), GenericDeclarations.variable(declaration, 6));
            }
        });
    }

    /**
     * Declares a generic `public interface` with seven type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     */
    public static <B1, B2, B3, B4, B5, B6, B7> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypedGenericInterfaceSpec7<B1, B2, B3, B4, B5, B6, B7> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6));
            }
        });
    }

    /**
     * Declares a generic `public record` with seven type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     */
    public static <B1, B2, B3, B4, B5, B6, B7> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypedGenericRecordSpec7<B1, B2, B3, B4, B5, B6, B7> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6));
            }
        });
    }

    /**
     * Declares a generic `public final class` with eight type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     */
    public static <B1, B2, B3, B4, B5, B6, B7, B8> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypedGenericClassSpec8<B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5), GenericDeclarations.variable(declaration, 6), GenericDeclarations.variable(declaration, 7));
            }
        });
    }

    /**
     * Declares a generic `public interface` with eight type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     */
    public static <B1, B2, B3, B4, B5, B6, B7, B8> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypedGenericInterfaceSpec8<B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7));
            }
        });
    }

    /**
     * Declares a generic `public record` with eight type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
     * @param <B1> the leftmost bound of the first type parameter
     * @param <B2> the leftmost bound of the second type parameter
     * @param <B3> the leftmost bound of the third type parameter
     * @param <B4> the leftmost bound of the fourth type parameter
     * @param <B5> the leftmost bound of the fifth type parameter
     * @param <B6> the leftmost bound of the sixth type parameter
     * @param <B7> the leftmost bound of the seventh type parameter
     * @param <B8> the leftmost bound of the eighth type parameter
     */
    public static <B1, B2, B3, B4, B5, B6, B7, B8> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypedGenericRecordSpec8<B1, B2, B3, B4, B5, B6, B7, B8> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7));
            }
        });
    }

    /**
     * Declares a generic `public final class` with nine type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypedGenericClassSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5), GenericDeclarations.variable(declaration, 6), GenericDeclarations.variable(declaration, 7), GenericDeclarations.variable(declaration, 8));
            }
        });
    }

    /**
     * Declares a generic `public interface` with nine type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypedGenericInterfaceSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8));
            }
        });
    }

    /**
     * Declares a generic `public record` with nine type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
     * @param p1 the first type parameter
     * @param p2 the second type parameter
     * @param p3 the third type parameter
     * @param p4 the fourth type parameter
     * @param p5 the fifth type parameter
     * @param p6 the sixth type parameter
     * @param p7 the seventh type parameter
     * @param p8 the eighth type parameter
     * @param p9 the ninth type parameter
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypedGenericRecordSpec9<B1, B2, B3, B4, B5, B6, B7, B8, B9> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8));
            }
        });
    }

    /**
     * Declares a generic `public final class` with ten type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
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
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypedGenericClassSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5), GenericDeclarations.variable(declaration, 6), GenericDeclarations.variable(declaration, 7), GenericDeclarations.variable(declaration, 8), GenericDeclarations.variable(declaration, 9));
            }
        });
    }

    /**
     * Declares a generic `public interface` with ten type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
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
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypedGenericInterfaceSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8), GenericDeclarations.variable(builder, 9));
            }
        });
    }

    /**
     * Declares a generic `public record` with ten type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
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
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypedGenericRecordSpec10<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8), GenericDeclarations.variable(builder, 9));
            }
        });
    }

    /**
     * Declares a generic `public final class` with eleven type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
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
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypedGenericClassSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5), GenericDeclarations.variable(declaration, 6), GenericDeclarations.variable(declaration, 7), GenericDeclarations.variable(declaration, 8), GenericDeclarations.variable(declaration, 9), GenericDeclarations.variable(declaration, 10));
            }
        });
    }

    /**
     * Declares a generic `public interface` with eleven type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
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
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypedGenericInterfaceSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8), GenericDeclarations.variable(builder, 9), GenericDeclarations.variable(builder, 10));
            }
        });
    }

    /**
     * Declares a generic `public record` with eleven type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
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
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypedGenericRecordSpec11<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8), GenericDeclarations.variable(builder, 9), GenericDeclarations.variable(builder, 10));
            }
        });
    }

    /**
     * Declares a generic `public final class` with twelve type parameters: the tokens are in scope in the header, the members and the member types of the class.
     *
     * @param target the classpath the class is compiled against
     * @param desc the class's binary name
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
     * @return the rendered class
     * @throws IllegalArgumentException if a type parameter has the name of another or of the class, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the class, given its header and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> JavaFile declareClass(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypedGenericClassSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        return GenericDeclarations.declareClass(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), new ClassBodies() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassDeclaration<Self> declaration) {
                return spec.build(new ClassHeader<>(declaration), GenericDeclarations.variable(declaration, 0), GenericDeclarations.variable(declaration, 1), GenericDeclarations.variable(declaration, 2), GenericDeclarations.variable(declaration, 3), GenericDeclarations.variable(declaration, 4), GenericDeclarations.variable(declaration, 5), GenericDeclarations.variable(declaration, 6), GenericDeclarations.variable(declaration, 7), GenericDeclarations.variable(declaration, 8), GenericDeclarations.variable(declaration, 9), GenericDeclarations.variable(declaration, 10), GenericDeclarations.variable(declaration, 11));
            }
        });
    }

    /**
     * Declares a generic `public interface` with twelve type parameters: the tokens are in scope in the header, the members and the member types of the interface.
     *
     * @param target the classpath the interface is compiled against
     * @param desc the interface's binary name
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
     * @return the rendered interface
     * @throws IllegalArgumentException if a type parameter has the name of another or of the interface, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the interface, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> JavaFile interface_(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypedGenericInterfaceSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        return GenericDeclarations.interface_(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), new InterfaceBodies() {
            @Override
            public <Self> TypedInterfaceBodies<Self> build(TypedInterfaceBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8), GenericDeclarations.variable(builder, 9), GenericDeclarations.variable(builder, 10), GenericDeclarations.variable(builder, 11));
            }
        });
    }

    /**
     * Declares a generic `public record` with twelve type parameters: the tokens are in scope in the components, its header, its members and its member types.
     *
     * @param target the classpath the record is compiled against
     * @param desc the record's binary name
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
     * @return the rendered record
     * @throws IllegalArgumentException if a type parameter has the name of another or of the record, or its bound is a raw type; or if `desc` describes a nested type, or a name of it cannot be written
     * @throws IllegalStateException if a member `spec` declared is not defined
     * @param spec declares the record, given its builder and the tokens of its type parameters
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
    public static <B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> JavaFile record(TargetClasspath target, ClassDesc desc, TypeParameter<B1> p1, TypeParameter<B2> p2, TypeParameter<B3> p3, TypeParameter<B4> p4, TypeParameter<B5> p5, TypeParameter<B6> p6, TypeParameter<B7> p7, TypeParameter<B8> p8, TypeParameter<B9> p9, TypeParameter<B10> p10, TypeParameter<B11> p11, TypeParameter<B12> p12, TypedGenericRecordSpec12<B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> spec) {
        return GenericDeclarations.record(target, desc, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), new RecordBodies() {
            @Override
            public <Self> TypedRecordBodies<Self> build(TypedRecordBuilder<Self> builder) {
                return spec.build(builder, GenericDeclarations.variable(builder, 0), GenericDeclarations.variable(builder, 1), GenericDeclarations.variable(builder, 2), GenericDeclarations.variable(builder, 3), GenericDeclarations.variable(builder, 4), GenericDeclarations.variable(builder, 5), GenericDeclarations.variable(builder, 6), GenericDeclarations.variable(builder, 7), GenericDeclarations.variable(builder, 8), GenericDeclarations.variable(builder, 9), GenericDeclarations.variable(builder, 10), GenericDeclarations.variable(builder, 11));
            }
        });
    }
}
