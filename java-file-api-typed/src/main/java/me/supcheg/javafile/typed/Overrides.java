package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;

import java.util.List;

/// The facts of the methods a class declares to override methods of its
/// supertypes: each of the name, the parameters and the result of the
/// method it overrides, as that one is a member of the supertype, `public`
/// and — the class being final — never overridden itself.
final class Overrides {
    private Overrides() {}

    private static MemberTraits traits(List<ClassToken<? extends Throwable>> thrown) {
        return new MemberTraits(
                List.copyOf(thrown),
                MemberTraits.FINAL.overridability(),
                MemberTraits.FINAL.typeArgs(),
                MemberTraits.FINAL.access());
    }

    /// The method of `self` that overrides `method`, declaring `thrown`.
    static <Self, R> MethodRef0<Self, R> own(
            FinalClassToken<Self> self, MethodRef0<?, R> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeFacts.method(self, method.name(), method.result(), traits(thrown));
    }

    /// The method of `self` that overrides `method`, declaring `thrown`.
    static <Self, R, A1> MethodRef1<Self, R, A1> own(
            FinalClassToken<Self> self, MethodRef1<?, R, A1> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeFacts.method(self, method.name(), method.result(), method.param1(), traits(thrown));
    }

    /// The method of `self` that overrides `method`, declaring `thrown`.
    static <Self, R, A1, A2> MethodRef2<Self, R, A1, A2> own(
            FinalClassToken<Self> self, MethodRef2<?, R, A1, A2> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeFacts.method(
                self, method.name(), method.result(), method.param1(), method.param2(), traits(thrown));
    }

    /// The method of `self` that overrides `method`, declaring `thrown`.
    static <Self> VoidMethodRef0<Self> own(
            FinalClassToken<Self> self, VoidMethodRef0<?> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeFacts.voidMethod(self, method.name(), traits(thrown));
    }

    /// The method of `self` that overrides `method`, declaring `thrown`.
    static <Self, A1> VoidMethodRef1<Self, A1> own(
            FinalClassToken<Self> self, VoidMethodRef1<?, A1> method, List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeFacts.voidMethod(self, method.name(), method.param1(), traits(thrown));
    }

    /// The method of `self` that overrides `method`, declaring `thrown`.
    static <Self, A1, A2> VoidMethodRef2<Self, A1, A2> own(
            FinalClassToken<Self> self,
            VoidMethodRef2<?, A1, A2> method,
            List<ClassToken<? extends Throwable>> thrown) {
        return UnsafeFacts.voidMethod(self, method.name(), method.param1(), method.param2(), traits(thrown));
    }
}
