package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.FinalClassToken;
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
import me.supcheg.javafile.facts.RefToken;
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

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class OverrideArities permits Overrides {
    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R> MethodRef0<Self, R> own(FinalClassToken<Self> self, MethodRef0<?, R> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R> MethodRef0<Self, R> own(FinalClassToken<Self> self, MethodRef0<?, ? super R> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self> MethodRef0<Self, Self> ownOfSelf(FinalClassToken<Self> self, MethodRef0<?, ?> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self> VoidMethodRef0<Self> own(FinalClassToken<Self> self, VoidMethodRef0<?> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1> MethodRef1<Self, R, A1> own(FinalClassToken<Self> self, MethodRef1<?, R, A1> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1> MethodRef1<Self, R, A1> own(FinalClassToken<Self> self, MethodRef1<?, ? super R, A1> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1> MethodRef1<Self, Self, A1> ownOfSelf(FinalClassToken<Self> self, MethodRef1<?, ?, A1> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1> VoidMethodRef1<Self, A1> own(FinalClassToken<Self> self, VoidMethodRef1<?, A1> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2> MethodRef2<Self, R, A1, A2> own(FinalClassToken<Self> self, MethodRef2<?, R, A1, A2> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2> MethodRef2<Self, R, A1, A2> own(FinalClassToken<Self> self, MethodRef2<?, ? super R, A1, A2> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2> MethodRef2<Self, Self, A1, A2> ownOfSelf(FinalClassToken<Self> self, MethodRef2<?, ?, A1, A2> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2> VoidMethodRef2<Self, A1, A2> own(FinalClassToken<Self> self, VoidMethodRef2<?, A1, A2> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> own(FinalClassToken<Self> self, MethodRef3<?, R, A1, A2, A3> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> own(FinalClassToken<Self> self, MethodRef3<?, ? super R, A1, A2, A3> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3> MethodRef3<Self, Self, A1, A2, A3> ownOfSelf(FinalClassToken<Self> self, MethodRef3<?, ?, A1, A2, A3> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> own(FinalClassToken<Self> self, VoidMethodRef3<?, A1, A2, A3> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> own(FinalClassToken<Self> self, MethodRef4<?, R, A1, A2, A3, A4> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> own(FinalClassToken<Self> self, MethodRef4<?, ? super R, A1, A2, A3, A4> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4> MethodRef4<Self, Self, A1, A2, A3, A4> ownOfSelf(FinalClassToken<Self> self, MethodRef4<?, ?, A1, A2, A3, A4> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> own(FinalClassToken<Self> self, VoidMethodRef4<?, A1, A2, A3, A4> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> own(FinalClassToken<Self> self, MethodRef5<?, R, A1, A2, A3, A4, A5> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> own(FinalClassToken<Self> self, MethodRef5<?, ? super R, A1, A2, A3, A4, A5> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5> MethodRef5<Self, Self, A1, A2, A3, A4, A5> ownOfSelf(FinalClassToken<Self> self, MethodRef5<?, ?, A1, A2, A3, A4, A5> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> own(FinalClassToken<Self> self, VoidMethodRef5<?, A1, A2, A3, A4, A5> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> own(FinalClassToken<Self> self, MethodRef6<?, R, A1, A2, A3, A4, A5, A6> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> own(FinalClassToken<Self> self, MethodRef6<?, ? super R, A1, A2, A3, A4, A5, A6> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6> MethodRef6<Self, Self, A1, A2, A3, A4, A5, A6> ownOfSelf(FinalClassToken<Self> self, MethodRef6<?, ?, A1, A2, A3, A4, A5, A6> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> own(FinalClassToken<Self> self, VoidMethodRef6<?, A1, A2, A3, A4, A5, A6> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> own(FinalClassToken<Self> self, MethodRef7<?, R, A1, A2, A3, A4, A5, A6, A7> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> own(FinalClassToken<Self> self, MethodRef7<?, ? super R, A1, A2, A3, A4, A5, A6, A7> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, Self, A1, A2, A3, A4, A5, A6, A7> ownOfSelf(FinalClassToken<Self> self, MethodRef7<?, ?, A1, A2, A3, A4, A5, A6, A7> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> own(FinalClassToken<Self> self, VoidMethodRef7<?, A1, A2, A3, A4, A5, A6, A7> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> own(FinalClassToken<Self> self, MethodRef8<?, R, A1, A2, A3, A4, A5, A6, A7, A8> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> own(FinalClassToken<Self> self, MethodRef8<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8> ownOfSelf(FinalClassToken<Self> self, MethodRef8<?, ?, A1, A2, A3, A4, A5, A6, A7, A8> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> own(FinalClassToken<Self> self, VoidMethodRef8<?, A1, A2, A3, A4, A5, A6, A7, A8> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> own(FinalClassToken<Self> self, MethodRef9<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> own(FinalClassToken<Self> self, MethodRef9<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> ownOfSelf(FinalClassToken<Self> self, MethodRef9<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> own(FinalClassToken<Self> self, VoidMethodRef9<?, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> own(FinalClassToken<Self> self, MethodRef10<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> own(FinalClassToken<Self> self, MethodRef10<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ownOfSelf(FinalClassToken<Self> self, MethodRef10<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> own(FinalClassToken<Self> self, VoidMethodRef10<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> own(FinalClassToken<Self> self, MethodRef11<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> own(FinalClassToken<Self> self, MethodRef11<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ownOfSelf(FinalClassToken<Self> self, MethodRef11<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> own(FinalClassToken<Self> self, VoidMethodRef11<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> own(FinalClassToken<Self> self, MethodRef12<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> own(FinalClassToken<Self> self, MethodRef12<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ownOfSelf(FinalClassToken<Self> self, MethodRef12<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> own(FinalClassToken<Self> self, VoidMethodRef12<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> own(FinalClassToken<Self> self, MethodRef13<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> own(FinalClassToken<Self> self, MethodRef13<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> ownOfSelf(FinalClassToken<Self> self, MethodRef13<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> own(FinalClassToken<Self> self, VoidMethodRef13<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> own(FinalClassToken<Self> self, MethodRef14<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> own(FinalClassToken<Self> self, MethodRef14<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> ownOfSelf(FinalClassToken<Self> self, MethodRef14<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> own(FinalClassToken<Self> self, VoidMethodRef14<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> own(FinalClassToken<Self> self, MethodRef15<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> own(FinalClassToken<Self> self, MethodRef15<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ownOfSelf(FinalClassToken<Self> self, MethodRef15<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> own(FinalClassToken<Self> self, VoidMethodRef15<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> own(FinalClassToken<Self> self, MethodRef16<?, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), method.result(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), method.param16(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the result `result`, declaring `thrown`.
     */
    public static <Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> own(FinalClassToken<Self> self, MethodRef16<?, ? super R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, RefToken<R> result, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), result, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), method.param16(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method` with the class itself for its result, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> ownOfSelf(FinalClassToken<Self> self, MethodRef16<?, ?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.method(self, method.name(), self, method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), method.param16(), Overrides.traits(method, thrown));
    }

    /**
     * The method of `self` that overrides `method`, declaring `thrown`.
     */
    public static <Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> own(FinalClassToken<Self> self, VoidMethodRef16<?, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeInvocables.voidMethod(self, method.name(), method.param1(), method.param2(), method.param3(), method.param4(), method.param5(), method.param6(), method.param7(), method.param8(), method.param9(), method.param10(), method.param11(), method.param12(), method.param13(), method.param14(), method.param15(), method.param16(), Overrides.traits(method, thrown));
    }
}
