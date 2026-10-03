package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
abstract class InvocableFactories {
    public static <O, R> MethodRef0<O, R> method(DeclaredToken<O> owner, String name, TypeToken<R> result, MemberTraits traits) {
        return new MethodRef0<>(owner, name, result, traits, Invocables.declared(owner));
    }

    public static <O> VoidMethodRef0<O> voidMethod(DeclaredToken<O> owner, String name, MemberTraits traits) {
        return new VoidMethodRef0<>(owner, name, traits, Invocables.declared(owner));
    }

    public static <R> StaticMethodRef0<R> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, MemberTraits traits) {
        return new StaticMethodRef0<>(owner, name, result, traits, Invocables.declared(owner));
    }

    public static VoidStaticMethodRef0 voidStaticMethod(DeclaredToken<?> owner, String name, MemberTraits traits) {
        return new VoidStaticMethodRef0(owner, name, traits, Invocables.declared(owner));
    }

    public static <O> CtorRef0<O> ctor(ConcreteClassToken<O> owner, MemberTraits traits) {
        return new CtorRef0<>(owner, traits, Invocables.declared(owner));
    }

    public static <O> AbstractCtorRef0<O> abstractCtor(AbstractClassToken<O> owner, MemberTraits traits) {
        return new AbstractCtorRef0<>(owner, traits, Invocables.declared(owner));
    }

    public static <F, R> Sam0<F, R> sam(MethodRef0<F, R> method) {
        return new Sam0<>(method);
    }

    public static <F> VoidSam0<F> voidSam(VoidMethodRef0<F> method) {
        return new VoidSam0<>(method);
    }

    public static <O, R, A1> MethodRef1<O, R, A1> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, MemberTraits traits) {
        return new MethodRef1<>(owner, name, result, Invocables.token(param1), traits, Invocables.declared(owner, param1));
    }

    public static <O, A1> VoidMethodRef1<O, A1> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, MemberTraits traits) {
        return new VoidMethodRef1<>(owner, name, Invocables.token(param1), traits, Invocables.declared(owner, param1));
    }

    public static <R, A1> StaticMethodRef1<R, A1> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, MemberTraits traits) {
        return new StaticMethodRef1<>(owner, name, result, Invocables.token(param1), traits, Invocables.declared(owner, param1));
    }

    public static <A1> VoidStaticMethodRef1<A1> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, MemberTraits traits) {
        return new VoidStaticMethodRef1<>(owner, name, Invocables.token(param1), traits, Invocables.declared(owner, param1));
    }

    public static <O, A1> CtorRef1<O, A1> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, MemberTraits traits) {
        return new CtorRef1<>(owner, Invocables.token(param1), traits, Invocables.declared(owner, param1));
    }

    public static <O, A1> AbstractCtorRef1<O, A1> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, MemberTraits traits) {
        return new AbstractCtorRef1<>(owner, Invocables.token(param1), traits, Invocables.declared(owner, param1));
    }

    public static <F, R, A1> Sam1<F, R, A1> sam(MethodRef1<F, R, A1> method) {
        return new Sam1<>(method);
    }

    public static <F, A1> VoidSam1<F, A1> voidSam(VoidMethodRef1<F, A1> method) {
        return new VoidSam1<>(method);
    }

    public static <O, R, A1, A2> MethodRef2<O, R, A1, A2> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, MemberTraits traits) {
        return new MethodRef2<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), traits, Invocables.declared(owner, param1, param2));
    }

    public static <O, A1, A2> VoidMethodRef2<O, A1, A2> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, MemberTraits traits) {
        return new VoidMethodRef2<>(owner, name, Invocables.token(param1), Invocables.token(param2), traits, Invocables.declared(owner, param1, param2));
    }

    public static <R, A1, A2> StaticMethodRef2<R, A1, A2> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, MemberTraits traits) {
        return new StaticMethodRef2<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), traits, Invocables.declared(owner, param1, param2));
    }

    public static <A1, A2> VoidStaticMethodRef2<A1, A2> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, MemberTraits traits) {
        return new VoidStaticMethodRef2<>(owner, name, Invocables.token(param1), Invocables.token(param2), traits, Invocables.declared(owner, param1, param2));
    }

    public static <O, A1, A2> CtorRef2<O, A1, A2> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, MemberTraits traits) {
        return new CtorRef2<>(owner, Invocables.token(param1), Invocables.token(param2), traits, Invocables.declared(owner, param1, param2));
    }

    public static <O, A1, A2> AbstractCtorRef2<O, A1, A2> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, MemberTraits traits) {
        return new AbstractCtorRef2<>(owner, Invocables.token(param1), Invocables.token(param2), traits, Invocables.declared(owner, param1, param2));
    }

    public static <F, R, A1, A2> Sam2<F, R, A1, A2> sam(MethodRef2<F, R, A1, A2> method) {
        return new Sam2<>(method);
    }

    public static <F, A1, A2> VoidSam2<F, A1, A2> voidSam(VoidMethodRef2<F, A1, A2> method) {
        return new VoidSam2<>(method);
    }

    public static <O, R, A1, A2, A3> MethodRef3<O, R, A1, A2, A3> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, MemberTraits traits) {
        return new MethodRef3<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), traits, Invocables.declared(owner, param1, param2, param3));
    }

    public static <O, A1, A2, A3> VoidMethodRef3<O, A1, A2, A3> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, MemberTraits traits) {
        return new VoidMethodRef3<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), traits, Invocables.declared(owner, param1, param2, param3));
    }

    public static <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, MemberTraits traits) {
        return new StaticMethodRef3<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), traits, Invocables.declared(owner, param1, param2, param3));
    }

    public static <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, MemberTraits traits) {
        return new VoidStaticMethodRef3<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), traits, Invocables.declared(owner, param1, param2, param3));
    }

    public static <O, A1, A2, A3> CtorRef3<O, A1, A2, A3> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, MemberTraits traits) {
        return new CtorRef3<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), traits, Invocables.declared(owner, param1, param2, param3));
    }

    public static <O, A1, A2, A3> AbstractCtorRef3<O, A1, A2, A3> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, MemberTraits traits) {
        return new AbstractCtorRef3<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), traits, Invocables.declared(owner, param1, param2, param3));
    }

    public static <F, R, A1, A2, A3> Sam3<F, R, A1, A2, A3> sam(MethodRef3<F, R, A1, A2, A3> method) {
        return new Sam3<>(method);
    }

    public static <F, A1, A2, A3> VoidSam3<F, A1, A2, A3> voidSam(VoidMethodRef3<F, A1, A2, A3> method) {
        return new VoidSam3<>(method);
    }

    public static <O, R, A1, A2, A3, A4> MethodRef4<O, R, A1, A2, A3, A4> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, MemberTraits traits) {
        return new MethodRef4<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), traits, Invocables.declared(owner, param1, param2, param3, param4));
    }

    public static <O, A1, A2, A3, A4> VoidMethodRef4<O, A1, A2, A3, A4> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, MemberTraits traits) {
        return new VoidMethodRef4<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), traits, Invocables.declared(owner, param1, param2, param3, param4));
    }

    public static <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, MemberTraits traits) {
        return new StaticMethodRef4<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), traits, Invocables.declared(owner, param1, param2, param3, param4));
    }

    public static <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, MemberTraits traits) {
        return new VoidStaticMethodRef4<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), traits, Invocables.declared(owner, param1, param2, param3, param4));
    }

    public static <O, A1, A2, A3, A4> CtorRef4<O, A1, A2, A3, A4> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, MemberTraits traits) {
        return new CtorRef4<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), traits, Invocables.declared(owner, param1, param2, param3, param4));
    }

    public static <O, A1, A2, A3, A4> AbstractCtorRef4<O, A1, A2, A3, A4> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, MemberTraits traits) {
        return new AbstractCtorRef4<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), traits, Invocables.declared(owner, param1, param2, param3, param4));
    }

    public static <F, R, A1, A2, A3, A4> Sam4<F, R, A1, A2, A3, A4> sam(MethodRef4<F, R, A1, A2, A3, A4> method) {
        return new Sam4<>(method);
    }

    public static <F, A1, A2, A3, A4> VoidSam4<F, A1, A2, A3, A4> voidSam(VoidMethodRef4<F, A1, A2, A3, A4> method) {
        return new VoidSam4<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5> MethodRef5<O, R, A1, A2, A3, A4, A5> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, MemberTraits traits) {
        return new MethodRef5<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), traits, Invocables.declared(owner, param1, param2, param3, param4, param5));
    }

    public static <O, A1, A2, A3, A4, A5> VoidMethodRef5<O, A1, A2, A3, A4, A5> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, MemberTraits traits) {
        return new VoidMethodRef5<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), traits, Invocables.declared(owner, param1, param2, param3, param4, param5));
    }

    public static <R, A1, A2, A3, A4, A5> StaticMethodRef5<R, A1, A2, A3, A4, A5> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, MemberTraits traits) {
        return new StaticMethodRef5<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), traits, Invocables.declared(owner, param1, param2, param3, param4, param5));
    }

    public static <A1, A2, A3, A4, A5> VoidStaticMethodRef5<A1, A2, A3, A4, A5> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, MemberTraits traits) {
        return new VoidStaticMethodRef5<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), traits, Invocables.declared(owner, param1, param2, param3, param4, param5));
    }

    public static <O, A1, A2, A3, A4, A5> CtorRef5<O, A1, A2, A3, A4, A5> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, MemberTraits traits) {
        return new CtorRef5<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), traits, Invocables.declared(owner, param1, param2, param3, param4, param5));
    }

    public static <O, A1, A2, A3, A4, A5> AbstractCtorRef5<O, A1, A2, A3, A4, A5> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, MemberTraits traits) {
        return new AbstractCtorRef5<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), traits, Invocables.declared(owner, param1, param2, param3, param4, param5));
    }

    public static <F, R, A1, A2, A3, A4, A5> Sam5<F, R, A1, A2, A3, A4, A5> sam(MethodRef5<F, R, A1, A2, A3, A4, A5> method) {
        return new Sam5<>(method);
    }

    public static <F, A1, A2, A3, A4, A5> VoidSam5<F, A1, A2, A3, A4, A5> voidSam(VoidMethodRef5<F, A1, A2, A3, A4, A5> method) {
        return new VoidSam5<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6> MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, MemberTraits traits) {
        return new MethodRef6<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6));
    }

    public static <O, A1, A2, A3, A4, A5, A6> VoidMethodRef6<O, A1, A2, A3, A4, A5, A6> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, MemberTraits traits) {
        return new VoidMethodRef6<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6));
    }

    public static <R, A1, A2, A3, A4, A5, A6> StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, MemberTraits traits) {
        return new StaticMethodRef6<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6));
    }

    public static <A1, A2, A3, A4, A5, A6> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, MemberTraits traits) {
        return new VoidStaticMethodRef6<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6));
    }

    public static <O, A1, A2, A3, A4, A5, A6> CtorRef6<O, A1, A2, A3, A4, A5, A6> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, MemberTraits traits) {
        return new CtorRef6<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6));
    }

    public static <O, A1, A2, A3, A4, A5, A6> AbstractCtorRef6<O, A1, A2, A3, A4, A5, A6> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, MemberTraits traits) {
        return new AbstractCtorRef6<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6> Sam6<F, R, A1, A2, A3, A4, A5, A6> sam(MethodRef6<F, R, A1, A2, A3, A4, A5, A6> method) {
        return new Sam6<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6> VoidSam6<F, A1, A2, A3, A4, A5, A6> voidSam(VoidMethodRef6<F, A1, A2, A3, A4, A5, A6> method) {
        return new VoidSam6<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, MemberTraits traits) {
        return new MethodRef7<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<O, A1, A2, A3, A4, A5, A6, A7> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, MemberTraits traits) {
        return new VoidMethodRef7<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7));
    }

    public static <R, A1, A2, A3, A4, A5, A6, A7> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, MemberTraits traits) {
        return new StaticMethodRef7<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7));
    }

    public static <A1, A2, A3, A4, A5, A6, A7> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, MemberTraits traits) {
        return new VoidStaticMethodRef7<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7> CtorRef7<O, A1, A2, A3, A4, A5, A6, A7> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, MemberTraits traits) {
        return new CtorRef7<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7> AbstractCtorRef7<O, A1, A2, A3, A4, A5, A6, A7> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, MemberTraits traits) {
        return new AbstractCtorRef7<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7> Sam7<F, R, A1, A2, A3, A4, A5, A6, A7> sam(MethodRef7<F, R, A1, A2, A3, A4, A5, A6, A7> method) {
        return new Sam7<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7> VoidSam7<F, A1, A2, A3, A4, A5, A6, A7> voidSam(VoidMethodRef7<F, A1, A2, A3, A4, A5, A6, A7> method) {
        return new VoidSam7<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, MemberTraits traits) {
        return new MethodRef8<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, MemberTraits traits) {
        return new VoidMethodRef8<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public static <R, A1, A2, A3, A4, A5, A6, A7, A8> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, MemberTraits traits) {
        return new StaticMethodRef8<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public static <A1, A2, A3, A4, A5, A6, A7, A8> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, MemberTraits traits) {
        return new VoidStaticMethodRef8<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, MemberTraits traits) {
        return new CtorRef8<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8> AbstractCtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, MemberTraits traits) {
        return new AbstractCtorRef8<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8> Sam8<F, R, A1, A2, A3, A4, A5, A6, A7, A8> sam(MethodRef8<F, R, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return new Sam8<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7, A8> VoidSam8<F, A1, A2, A3, A4, A5, A6, A7, A8> voidSam(VoidMethodRef8<F, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return new VoidSam8<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, MemberTraits traits) {
        return new MethodRef9<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, MemberTraits traits) {
        return new VoidMethodRef9<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, MemberTraits traits) {
        return new StaticMethodRef9<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, MemberTraits traits) {
        return new VoidStaticMethodRef9<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, MemberTraits traits) {
        return new CtorRef9<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> AbstractCtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, MemberTraits traits) {
        return new AbstractCtorRef9<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Sam9<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> sam(MethodRef9<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return new Sam9<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidSam9<F, A1, A2, A3, A4, A5, A6, A7, A8, A9> voidSam(VoidMethodRef9<F, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return new VoidSam9<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, MemberTraits traits) {
        return new MethodRef10<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, MemberTraits traits) {
        return new VoidMethodRef10<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, MemberTraits traits) {
        return new StaticMethodRef10<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, MemberTraits traits) {
        return new VoidStaticMethodRef10<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, MemberTraits traits) {
        return new CtorRef10<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> AbstractCtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, MemberTraits traits) {
        return new AbstractCtorRef10<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Sam10<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam(MethodRef10<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return new Sam10<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidSam10<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidSam(VoidMethodRef10<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return new VoidSam10<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, MemberTraits traits) {
        return new MethodRef11<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, MemberTraits traits) {
        return new VoidMethodRef11<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, MemberTraits traits) {
        return new StaticMethodRef11<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, MemberTraits traits) {
        return new VoidStaticMethodRef11<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, MemberTraits traits) {
        return new CtorRef11<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> AbstractCtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, MemberTraits traits) {
        return new AbstractCtorRef11<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Sam11<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> sam(MethodRef11<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return new Sam11<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidSam11<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidSam(VoidMethodRef11<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return new VoidSam11<>(method);
    }

    public static <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method(DeclaredToken<O> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, FactParam<A12> param12, MemberTraits traits) {
        return new MethodRef12<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), Invocables.token(param12), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidMethod(DeclaredToken<O> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, FactParam<A12> param12, MemberTraits traits) {
        return new VoidMethodRef12<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), Invocables.token(param12), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> staticMethod(DeclaredToken<?> owner, String name, TypeToken<R> result, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, FactParam<A12> param12, MemberTraits traits) {
        return new StaticMethodRef12<>(owner, name, result, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), Invocables.token(param12), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidStaticMethod(DeclaredToken<?> owner, String name, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, FactParam<A12> param12, MemberTraits traits) {
        return new VoidStaticMethodRef12<>(owner, name, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), Invocables.token(param12), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor(ConcreteClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, FactParam<A12> param12, MemberTraits traits) {
        return new CtorRef12<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), Invocables.token(param12), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> AbstractCtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> abstractCtor(AbstractClassToken<O> owner, FactParam<A1> param1, FactParam<A2> param2, FactParam<A3> param3, FactParam<A4> param4, FactParam<A5> param5, FactParam<A6> param6, FactParam<A7> param7, FactParam<A8> param8, FactParam<A9> param9, FactParam<A10> param10, FactParam<A11> param11, FactParam<A12> param12, MemberTraits traits) {
        return new AbstractCtorRef12<>(owner, Invocables.token(param1), Invocables.token(param2), Invocables.token(param3), Invocables.token(param4), Invocables.token(param5), Invocables.token(param6), Invocables.token(param7), Invocables.token(param8), Invocables.token(param9), Invocables.token(param10), Invocables.token(param11), Invocables.token(param12), traits, Invocables.declared(owner, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Sam12<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam(MethodRef12<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return new Sam12<>(method);
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidSam12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidSam(VoidMethodRef12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return new VoidSam12<>(method);
    }
}
