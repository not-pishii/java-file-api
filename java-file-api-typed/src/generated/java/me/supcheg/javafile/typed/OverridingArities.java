package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Implemented.ByOverride;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef10;
import me.supcheg.javafile.facts.MethodRef11;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef13;
import me.supcheg.javafile.facts.MethodRef14;
import me.supcheg.javafile.facts.MethodRef15;
import me.supcheg.javafile.facts.MethodRef16;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodRef5;
import me.supcheg.javafile.facts.MethodRef6;
import me.supcheg.javafile.facts.MethodRef7;
import me.supcheg.javafile.facts.MethodRef8;
import me.supcheg.javafile.facts.MethodRef9;
import me.supcheg.javafile.facts.Overriding0;
import me.supcheg.javafile.facts.Overriding1;
import me.supcheg.javafile.facts.Overriding10;
import me.supcheg.javafile.facts.Overriding11;
import me.supcheg.javafile.facts.Overriding12;
import me.supcheg.javafile.facts.Overriding13;
import me.supcheg.javafile.facts.Overriding14;
import me.supcheg.javafile.facts.Overriding15;
import me.supcheg.javafile.facts.Overriding16;
import me.supcheg.javafile.facts.Overriding2;
import me.supcheg.javafile.facts.Overriding3;
import me.supcheg.javafile.facts.Overriding4;
import me.supcheg.javafile.facts.Overriding5;
import me.supcheg.javafile.facts.Overriding6;
import me.supcheg.javafile.facts.Overriding7;
import me.supcheg.javafile.facts.Overriding8;
import me.supcheg.javafile.facts.Overriding9;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.SelfOverriding0;
import me.supcheg.javafile.facts.SelfOverriding1;
import me.supcheg.javafile.facts.SelfOverriding10;
import me.supcheg.javafile.facts.SelfOverriding11;
import me.supcheg.javafile.facts.SelfOverriding12;
import me.supcheg.javafile.facts.SelfOverriding13;
import me.supcheg.javafile.facts.SelfOverriding14;
import me.supcheg.javafile.facts.SelfOverriding15;
import me.supcheg.javafile.facts.SelfOverriding16;
import me.supcheg.javafile.facts.SelfOverriding2;
import me.supcheg.javafile.facts.SelfOverriding3;
import me.supcheg.javafile.facts.SelfOverriding4;
import me.supcheg.javafile.facts.SelfOverriding5;
import me.supcheg.javafile.facts.SelfOverriding6;
import me.supcheg.javafile.facts.SelfOverriding7;
import me.supcheg.javafile.facts.SelfOverriding8;
import me.supcheg.javafile.facts.SelfOverriding9;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef10;
import me.supcheg.javafile.facts.VoidMethodRef11;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.VoidMethodRef13;
import me.supcheg.javafile.facts.VoidMethodRef14;
import me.supcheg.javafile.facts.VoidMethodRef15;
import me.supcheg.javafile.facts.VoidMethodRef16;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidMethodRef5;
import me.supcheg.javafile.facts.VoidMethodRef6;
import me.supcheg.javafile.facts.VoidMethodRef7;
import me.supcheg.javafile.facts.VoidMethodRef8;
import me.supcheg.javafile.facts.VoidMethodRef9;
import me.supcheg.javafile.facts.VoidOverriding0;
import me.supcheg.javafile.facts.VoidOverriding1;
import me.supcheg.javafile.facts.VoidOverriding10;
import me.supcheg.javafile.facts.VoidOverriding11;
import me.supcheg.javafile.facts.VoidOverriding12;
import me.supcheg.javafile.facts.VoidOverriding13;
import me.supcheg.javafile.facts.VoidOverriding14;
import me.supcheg.javafile.facts.VoidOverriding15;
import me.supcheg.javafile.facts.VoidOverriding16;
import me.supcheg.javafile.facts.VoidOverriding2;
import me.supcheg.javafile.facts.VoidOverriding3;
import me.supcheg.javafile.facts.VoidOverriding4;
import me.supcheg.javafile.facts.VoidOverriding5;
import me.supcheg.javafile.facts.VoidOverriding6;
import me.supcheg.javafile.facts.VoidOverriding7;
import me.supcheg.javafile.facts.VoidOverriding8;
import me.supcheg.javafile.facts.VoidOverriding9;

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

    /**
     * Says the class overrides a method of 3 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3> Overriding3<Self, R, A1, A2, A3> overriding(MethodRef3<O, R, A1, A2, A3> method) {
        return UnsafeInvocables.overriding3(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 3 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3> Overriding3<Self, R2, A1, A2, A3> overriding(MethodRef3<O, R, A1, A2, A3> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding3(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 3 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3> SelfOverriding3<Self, X, A1, A2, A3> overriding(MethodRef3<O, R, A1, A2, A3> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding3(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 3 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3> VoidOverriding3<Self, A1, A2, A3> overriding(VoidMethodRef3<O, A1, A2, A3> method) {
        return UnsafeInvocables.voidOverriding3(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 4 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4> Overriding4<Self, R, A1, A2, A3, A4> overriding(MethodRef4<O, R, A1, A2, A3, A4> method) {
        return UnsafeInvocables.overriding4(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 4 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4> Overriding4<Self, R2, A1, A2, A3, A4> overriding(MethodRef4<O, R, A1, A2, A3, A4> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding4(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 4 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4> SelfOverriding4<Self, X, A1, A2, A3, A4> overriding(MethodRef4<O, R, A1, A2, A3, A4> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding4(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 4 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4> VoidOverriding4<Self, A1, A2, A3, A4> overriding(VoidMethodRef4<O, A1, A2, A3, A4> method) {
        return UnsafeInvocables.voidOverriding4(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 5 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5> Overriding5<Self, R, A1, A2, A3, A4, A5> overriding(MethodRef5<O, R, A1, A2, A3, A4, A5> method) {
        return UnsafeInvocables.overriding5(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 5 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5> Overriding5<Self, R2, A1, A2, A3, A4, A5> overriding(MethodRef5<O, R, A1, A2, A3, A4, A5> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding5(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 5 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5> SelfOverriding5<Self, X, A1, A2, A3, A4, A5> overriding(MethodRef5<O, R, A1, A2, A3, A4, A5> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding5(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 5 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5> VoidOverriding5<Self, A1, A2, A3, A4, A5> overriding(VoidMethodRef5<O, A1, A2, A3, A4, A5> method) {
        return UnsafeInvocables.voidOverriding5(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 6 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6> Overriding6<Self, R, A1, A2, A3, A4, A5, A6> overriding(MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method) {
        return UnsafeInvocables.overriding6(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 6 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6> Overriding6<Self, R2, A1, A2, A3, A4, A5, A6> overriding(MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding6(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 6 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6> SelfOverriding6<Self, X, A1, A2, A3, A4, A5, A6> overriding(MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding6(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 6 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6> VoidOverriding6<Self, A1, A2, A3, A4, A5, A6> overriding(VoidMethodRef6<O, A1, A2, A3, A4, A5, A6> method) {
        return UnsafeInvocables.voidOverriding6(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 7 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7> Overriding7<Self, R, A1, A2, A3, A4, A5, A6, A7> overriding(MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method) {
        return UnsafeInvocables.overriding7(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 7 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7> Overriding7<Self, R2, A1, A2, A3, A4, A5, A6, A7> overriding(MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding7(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 7 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7> SelfOverriding7<Self, X, A1, A2, A3, A4, A5, A6, A7> overriding(MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding7(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 7 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7> VoidOverriding7<Self, A1, A2, A3, A4, A5, A6, A7> overriding(VoidMethodRef7<O, A1, A2, A3, A4, A5, A6, A7> method) {
        return UnsafeInvocables.voidOverriding7(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 8 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8> Overriding8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> overriding(MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return UnsafeInvocables.overriding8(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 8 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8> Overriding8<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8> overriding(MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding8(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 8 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8> SelfOverriding8<Self, X, A1, A2, A3, A4, A5, A6, A7, A8> overriding(MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding8(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 8 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8> VoidOverriding8<Self, A1, A2, A3, A4, A5, A6, A7, A8> overriding(VoidMethodRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return UnsafeInvocables.voidOverriding8(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 9 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Overriding9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> overriding(MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return UnsafeInvocables.overriding9(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 9 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Overriding9<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9> overriding(MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding9(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 9 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9> SelfOverriding9<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9> overriding(MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding9(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 9 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidOverriding9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> overriding(VoidMethodRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return UnsafeInvocables.voidOverriding9(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 10 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Overriding10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> overriding(MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return UnsafeInvocables.overriding10(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 10 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Overriding10<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> overriding(MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding10(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 10 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> SelfOverriding10<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> overriding(MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding10(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 10 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidOverriding10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> overriding(VoidMethodRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return UnsafeInvocables.voidOverriding10(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 11 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Overriding11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> overriding(MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return UnsafeInvocables.overriding11(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 11 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Overriding11<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> overriding(MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding11(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 11 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> SelfOverriding11<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> overriding(MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding11(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 11 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidOverriding11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> overriding(VoidMethodRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return UnsafeInvocables.voidOverriding11(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 12 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Overriding12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> overriding(MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return UnsafeInvocables.overriding12(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 12 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Overriding12<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> overriding(MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding12(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 12 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> SelfOverriding12<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> overriding(MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding12(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 12 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidOverriding12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> overriding(VoidMethodRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return UnsafeInvocables.voidOverriding12(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 13 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Overriding13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> overriding(MethodRef13<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method) {
        return UnsafeInvocables.overriding13(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 13 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Overriding13<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> overriding(MethodRef13<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding13(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 13 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> SelfOverriding13<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> overriding(MethodRef13<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding13(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 13 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidOverriding13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> overriding(VoidMethodRef13<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method) {
        return UnsafeInvocables.voidOverriding13(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 14 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Overriding14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> overriding(MethodRef14<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method) {
        return UnsafeInvocables.overriding14(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 14 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Overriding14<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> overriding(MethodRef14<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding14(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 14 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> SelfOverriding14<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> overriding(MethodRef14<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding14(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 14 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidOverriding14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> overriding(VoidMethodRef14<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method) {
        return UnsafeInvocables.voidOverriding14(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 15 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Overriding15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> overriding(MethodRef15<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method) {
        return UnsafeInvocables.overriding15(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 15 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Overriding15<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> overriding(MethodRef15<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding15(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 15 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> SelfOverriding15<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> overriding(MethodRef15<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding15(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 15 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidOverriding15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> overriding(VoidMethodRef15<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method) {
        return UnsafeInvocables.voidOverriding15(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method of 16 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Overriding16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> overriding(MethodRef16<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method) {
        return UnsafeInvocables.overriding16(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method of a narrower result (JLS 8.4.5) of 16 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Overriding16<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> overriding(MethodRef16<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, RefToken<R2> result) {
        return UnsafeInvocables.overriding16(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /**
     * Says the class overrides a method with a method whose result is the class itself (JLS 8.4.5) of 16 parameters.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <X> the supertype of the class the witness is of
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, R, X extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> SelfOverriding16<Self, X, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> overriding(MethodRef16<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, Upcast<Self, X> result) {
        return UnsafeInvocables.selfOverriding16(new ByOverride(method, declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /**
     * Says the class overrides a `void` method of 16 parameters.
     *
     * @param method the fact of the overridden method
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return how the class has the method: by one of its own, `method()` of the result
     * @throws IllegalArgumentException if the method is `final` or generic, the class overrides a method of that signature already, or cannot override what it inherits under it
     * @throws IllegalStateException if the header of the class has ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidOverriding16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> overriding(VoidMethodRef16<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method) {
        return UnsafeInvocables.voidOverriding16(new ByOverride(method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }
}
