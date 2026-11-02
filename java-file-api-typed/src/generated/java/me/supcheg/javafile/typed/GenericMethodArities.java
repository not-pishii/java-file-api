package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.TypeVarToken;

/**
 * The specification of a generic method or constructor of a class, for one to sixteen type parameters: the stage [GenericMethodDeclaration] the type parameters of the member are in scope in, and the tokens of those type parameters.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public final class GenericMethodArities {
    private GenericMethodArities() {
    }

    /**
     * The specification of a generic member of one type parameter, whose leftmost bound is `B1`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec1<Self, S, F extends Invocable, B1> {
        /**
         * Declares the member through `declaration`, with the token of its type parameter in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1);
    }

    /**
     * The specification of a generic member of two type parameters, whose leftmost bounds are `B1` and `B2`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec2<Self, S, F extends Invocable, B1, B2> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2);
    }

    /**
     * The specification of a generic member of three type parameters, whose leftmost bounds are `B1`, `B2` and `B3`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec3<Self, S, F extends Invocable, B1, B2, B3> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3);
    }

    /**
     * The specification of a generic member of four type parameters, whose leftmost bounds are `B1`, `B2`, `B3` and `B4`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec4<Self, S, F extends Invocable, B1, B2, B3, B4> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4);
    }

    /**
     * The specification of a generic member of five type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4` and `B5`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec5<Self, S, F extends Invocable, B1, B2, B3, B4, B5> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5);
    }

    /**
     * The specification of a generic member of six type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5` and `B6`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec6<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6);
    }

    /**
     * The specification of a generic member of seven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6` and `B7`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec7<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7);
    }

    /**
     * The specification of a generic member of eight type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7` and `B8`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec8<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8);
    }

    /**
     * The specification of a generic member of nine type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8` and `B9`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec9<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9);
    }

    /**
     * The specification of a generic member of ten type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9` and `B10`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec10<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10);
    }

    /**
     * The specification of a generic member of eleven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10` and `B11`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec11<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11);
    }

    /**
     * The specification of a generic member of twelve type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11` and `B12`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec12<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12);
    }

    /**
     * The specification of a generic member of thirteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12` and `B13`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec13<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13);
    }

    /**
     * The specification of a generic member of fourteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13` and `B14`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec14<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @param t14 the token of the fourteenth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13, TypeVarToken<B14> t14);
    }

    /**
     * The specification of a generic member of fifteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14` and `B15`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param <B15> the leftmost bound of the fifteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec15<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @param t14 the token of the fourteenth type parameter
         * @param t15 the token of the fifteenth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13, TypeVarToken<B14> t14, TypeVarToken<B15> t15);
    }

    /**
     * The specification of a generic member of sixteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15` and `B16`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param <B15> the leftmost bound of the fifteenth type parameter, `Object` for none
     * @param <B16> the leftmost bound of the sixteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface MethodSpec16<Self, S, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @param t14 the token of the fourteenth type parameter
         * @param t15 the token of the fifteenth type parameter
         * @param t16 the token of the sixteenth type parameter
         * @return the fact of the member declared
         */
        F build(GenericMethodDeclaration<Self, S> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13, TypeVarToken<B14> t14, TypeVarToken<B15> t15, TypeVarToken<B16> t16);
    }

    /**
     * The specification of a generic method of a type that is not a class, of one type parameter, whose leftmost bound is `B1`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec1<Self, F extends Invocable, B1> {
        /**
         * Declares the member through `declaration`, with the token of its type parameter in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1);
    }

    /**
     * The specification of a generic method of a type that is not a class, of two type parameters, whose leftmost bounds are `B1` and `B2`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec2<Self, F extends Invocable, B1, B2> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2);
    }

    /**
     * The specification of a generic method of a type that is not a class, of three type parameters, whose leftmost bounds are `B1`, `B2` and `B3`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec3<Self, F extends Invocable, B1, B2, B3> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3);
    }

    /**
     * The specification of a generic method of a type that is not a class, of four type parameters, whose leftmost bounds are `B1`, `B2`, `B3` and `B4`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec4<Self, F extends Invocable, B1, B2, B3, B4> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4);
    }

    /**
     * The specification of a generic method of a type that is not a class, of five type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4` and `B5`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec5<Self, F extends Invocable, B1, B2, B3, B4, B5> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5);
    }

    /**
     * The specification of a generic method of a type that is not a class, of six type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5` and `B6`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec6<Self, F extends Invocable, B1, B2, B3, B4, B5, B6> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6);
    }

    /**
     * The specification of a generic method of a type that is not a class, of seven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6` and `B7`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec7<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7);
    }

    /**
     * The specification of a generic method of a type that is not a class, of eight type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7` and `B8`: the fact of the member is `F`, which the declaration of the specification says.
     *
     * @param <B1> the leftmost bound of the first type parameter, `Object` for none
     * @param <B2> the leftmost bound of the second type parameter, `Object` for none
     * @param <B3> the leftmost bound of the third type parameter, `Object` for none
     * @param <B4> the leftmost bound of the fourth type parameter, `Object` for none
     * @param <B5> the leftmost bound of the fifth type parameter, `Object` for none
     * @param <B6> the leftmost bound of the sixth type parameter, `Object` for none
     * @param <B7> the leftmost bound of the seventh type parameter, `Object` for none
     * @param <B8> the leftmost bound of the eighth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec8<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8);
    }

    /**
     * The specification of a generic method of a type that is not a class, of nine type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8` and `B9`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec9<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
         * @param t1 the token of the first type parameter
         * @param t2 the token of the second type parameter
         * @param t3 the token of the third type parameter
         * @param t4 the token of the fourth type parameter
         * @param t5 the token of the fifth type parameter
         * @param t6 the token of the sixth type parameter
         * @param t7 the token of the seventh type parameter
         * @param t8 the token of the eighth type parameter
         * @param t9 the token of the ninth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9);
    }

    /**
     * The specification of a generic method of a type that is not a class, of ten type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9` and `B10`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec10<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10);
    }

    /**
     * The specification of a generic method of a type that is not a class, of eleven type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10` and `B11`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec11<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11);
    }

    /**
     * The specification of a generic method of a type that is not a class, of twelve type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11` and `B12`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec12<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12);
    }

    /**
     * The specification of a generic method of a type that is not a class, of thirteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12` and `B13`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec13<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13);
    }

    /**
     * The specification of a generic method of a type that is not a class, of fourteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13` and `B14`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec14<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @param t14 the token of the fourteenth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13, TypeVarToken<B14> t14);
    }

    /**
     * The specification of a generic method of a type that is not a class, of fifteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14` and `B15`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param <B15> the leftmost bound of the fifteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec15<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @param t14 the token of the fourteenth type parameter
         * @param t15 the token of the fifteenth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13, TypeVarToken<B14> t14, TypeVarToken<B15> t15);
    }

    /**
     * The specification of a generic method of a type that is not a class, of sixteen type parameters, whose leftmost bounds are `B1`, `B2`, `B3`, `B4`, `B5`, `B6`, `B7`, `B8`, `B9`, `B10`, `B11`, `B12`, `B13`, `B14`, `B15` and `B16`: the fact of the member is `F`, which the declaration of the specification says.
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
     * @param <B13> the leftmost bound of the thirteenth type parameter, `Object` for none
     * @param <B14> the leftmost bound of the fourteenth type parameter, `Object` for none
     * @param <B15> the leftmost bound of the fifteenth type parameter, `Object` for none
     * @param <B16> the leftmost bound of the sixteenth type parameter, `Object` for none
     * @param <F> the fact of the member declared, e.g. `MethodRef1&lt;Self, B1, B1&gt;`
     */
    @Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
    public interface TypeMethodSpec16<Self, F extends Invocable, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16> {
        /**
         * Declares the member through `declaration`, with the tokens of its type parameters in scope in its signature and its body.
         *
         * @param declaration the declarations of the members, the type parameters of this one in scope
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
         * @param t13 the token of the thirteenth type parameter
         * @param t14 the token of the fourteenth type parameter
         * @param t15 the token of the fifteenth type parameter
         * @param t16 the token of the sixteenth type parameter
         * @return the fact of the member declared
         */
        F build(TypeGenericMethodDeclaration<Self> declaration, TypeVarToken<B1> t1, TypeVarToken<B2> t2, TypeVarToken<B3> t3, TypeVarToken<B4> t4, TypeVarToken<B5> t5, TypeVarToken<B6> t6, TypeVarToken<B7> t7, TypeVarToken<B8> t8, TypeVarToken<B9> t9, TypeVarToken<B10> t10, TypeVarToken<B11> t11, TypeVarToken<B12> t12, TypeVarToken<B13> t13, TypeVarToken<B14> t14, TypeVarToken<B15> t15, TypeVarToken<B16> t16);
    }
}
