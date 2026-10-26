package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Implemented.ByOverride;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Overriding0;
import me.supcheg.javafile.facts.Overriding1;
import me.supcheg.javafile.facts.Overriding2;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.SelfOverriding0;
import me.supcheg.javafile.facts.SelfOverriding1;
import me.supcheg.javafile.facts.SelfOverriding2;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidOverriding0;
import me.supcheg.javafile.facts.VoidOverriding1;
import me.supcheg.javafile.facts.VoidOverriding2;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class OverridingArities<Self> permits Overriding {
    abstract ClassDeclaration<Self> declaration();

    abstract List<ClassToken<? extends Throwable>> thrown();

    /**
     * Says the class overrides a method of 0 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R> Overriding0<Self, R> overriding(MethodRef0<O, R> method) {
        return UnsafeInvocables.overriding0(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 0 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R> Overriding0<Self, R2> overriding(MethodRef0<O, R> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding0(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 0 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R> SelfOverriding0<Self, X> overriding(MethodRef0<O, R> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding0(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 0 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O> VoidOverriding0<Self> overriding(VoidMethodRef0<O> method) {
        return UnsafeInvocables.voidOverriding0(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 1 parameter.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1> Overriding1<Self, R, A1> overriding(MethodRef1<O, R, A1> method) {
        return UnsafeInvocables.overriding1(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 1 parameter.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1> Overriding1<Self, R2, A1> overriding(MethodRef1<O, R, A1> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding1(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 1 parameter.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1> SelfOverriding1<Self, X, A1> overriding(MethodRef1<O, R, A1> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding1(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 1 parameter.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1> VoidOverriding1<Self, A1> overriding(VoidMethodRef1<O, A1> method) {
        return UnsafeInvocables.voidOverriding1(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 2 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2> Overriding2<Self, R, A1, A2> overriding(MethodRef2<O, R, A1, A2> method) {
        return UnsafeInvocables.overriding2(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 2 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2> Overriding2<Self, R2, A1, A2> overriding(MethodRef2<O, R, A1, A2> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding2(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 2 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2> SelfOverriding2<Self, X, A1, A2> overriding(MethodRef2<O, R, A1, A2> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding2(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 2 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2> VoidOverriding2<Self, A1, A2> overriding(VoidMethodRef2<O, A1, A2> method) {
        return UnsafeInvocables.voidOverriding2(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }
}
