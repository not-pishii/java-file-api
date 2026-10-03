package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.source.MemberQuery;
import me.supcheg.javafile.facts.source.Resolution;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
abstract class InvocableLookup<O> {
    public abstract DeclaredToken<O> token();

    abstract Resolution resolve(MemberQuery query);

    abstract ConcreteClassToken<O> concreteClassToken();

    abstract AbstractClassToken<O> abstractClassToken();

    public <R> MethodRef0<O, R> method(String name, TypeToken<R> result) {
        return new MethodRef0<>(token(), name, result, resolve(MemberQuery.method(name, result)).traits(), Invocables.declared(token()));
    }

    public VoidMethodRef0<O> voidMethod(String name) {
        return new VoidMethodRef0<>(token(), name, resolve(MemberQuery.voidMethod(name)).traits(), Invocables.declared(token()));
    }

    public <R> StaticMethodRef0<R> staticMethod(String name, TypeToken<R> result) {
        return new StaticMethodRef0<>(token(), name, result, resolve(MemberQuery.staticMethod(name, result)).traits(), Invocables.declared(token()));
    }

    public VoidStaticMethodRef0 voidStaticMethod(String name) {
        return new VoidStaticMethodRef0(token(), name, resolve(MemberQuery.voidStaticMethod(name)).traits(), Invocables.declared(token()));
    }

    public CtorRef0<O> ctor() {
        return new CtorRef0<>(concreteClassToken(), resolve(MemberQuery.constructor()).traits(), Invocables.declared(concreteClassToken()));
    }

    public AbstractCtorRef0<O> abstractCtor() {
        return new AbstractCtorRef0<>(abstractClassToken(), resolve(MemberQuery.constructor()).traits(), Invocables.declared(abstractClassToken()));
    }

    public <R, A1> MethodRef1<O, R, A1> method(String name, TypeToken<R> result, TypeToken<A1> param1) {
        return new MethodRef1<>(token(), name, result, param1, resolve(MemberQuery.method(name, result, param1)).traits(), Invocables.declared(token(), param1));
    }

    public <A1> VoidMethodRef1<O, A1> voidMethod(String name, TypeToken<A1> param1) {
        return new VoidMethodRef1<>(token(), name, param1, resolve(MemberQuery.voidMethod(name, param1)).traits(), Invocables.declared(token(), param1));
    }

    public <R, A1> StaticMethodRef1<R, A1> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1) {
        return new StaticMethodRef1<>(token(), name, result, param1, resolve(MemberQuery.staticMethod(name, result, param1)).traits(), Invocables.declared(token(), param1));
    }

    public <A1> VoidStaticMethodRef1<A1> voidStaticMethod(String name, TypeToken<A1> param1) {
        return new VoidStaticMethodRef1<>(token(), name, param1, resolve(MemberQuery.voidStaticMethod(name, param1)).traits(), Invocables.declared(token(), param1));
    }

    public <A1> CtorRef1<O, A1> ctor(TypeToken<A1> param1) {
        return new CtorRef1<>(concreteClassToken(), param1, resolve(MemberQuery.constructor(param1)).traits(), Invocables.declared(concreteClassToken(), param1));
    }

    public <A1> AbstractCtorRef1<O, A1> abstractCtor(TypeToken<A1> param1) {
        return new AbstractCtorRef1<>(abstractClassToken(), param1, resolve(MemberQuery.constructor(param1)).traits(), Invocables.declared(abstractClassToken(), param1));
    }

    public <R, A1, A2> MethodRef2<O, R, A1, A2> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2) {
        return new MethodRef2<>(token(), name, result, param1, param2, resolve(MemberQuery.method(name, result, param1, param2)).traits(), Invocables.declared(token(), param1, param2));
    }

    public <A1, A2> VoidMethodRef2<O, A1, A2> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2) {
        return new VoidMethodRef2<>(token(), name, param1, param2, resolve(MemberQuery.voidMethod(name, param1, param2)).traits(), Invocables.declared(token(), param1, param2));
    }

    public <R, A1, A2> StaticMethodRef2<R, A1, A2> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2) {
        return new StaticMethodRef2<>(token(), name, result, param1, param2, resolve(MemberQuery.staticMethod(name, result, param1, param2)).traits(), Invocables.declared(token(), param1, param2));
    }

    public <A1, A2> VoidStaticMethodRef2<A1, A2> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2) {
        return new VoidStaticMethodRef2<>(token(), name, param1, param2, resolve(MemberQuery.voidStaticMethod(name, param1, param2)).traits(), Invocables.declared(token(), param1, param2));
    }

    public <A1, A2> CtorRef2<O, A1, A2> ctor(TypeToken<A1> param1, TypeToken<A2> param2) {
        return new CtorRef2<>(concreteClassToken(), param1, param2, resolve(MemberQuery.constructor(param1, param2)).traits(), Invocables.declared(concreteClassToken(), param1, param2));
    }

    public <A1, A2> AbstractCtorRef2<O, A1, A2> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2) {
        return new AbstractCtorRef2<>(abstractClassToken(), param1, param2, resolve(MemberQuery.constructor(param1, param2)).traits(), Invocables.declared(abstractClassToken(), param1, param2));
    }

    public <R, A1, A2, A3> MethodRef3<O, R, A1, A2, A3> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return new MethodRef3<>(token(), name, result, param1, param2, param3, resolve(MemberQuery.method(name, result, param1, param2, param3)).traits(), Invocables.declared(token(), param1, param2, param3));
    }

    public <A1, A2, A3> VoidMethodRef3<O, A1, A2, A3> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return new VoidMethodRef3<>(token(), name, param1, param2, param3, resolve(MemberQuery.voidMethod(name, param1, param2, param3)).traits(), Invocables.declared(token(), param1, param2, param3));
    }

    public <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return new StaticMethodRef3<>(token(), name, result, param1, param2, param3, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3)).traits(), Invocables.declared(token(), param1, param2, param3));
    }

    public <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return new VoidStaticMethodRef3<>(token(), name, param1, param2, param3, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3)).traits(), Invocables.declared(token(), param1, param2, param3));
    }

    public <A1, A2, A3> CtorRef3<O, A1, A2, A3> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return new CtorRef3<>(concreteClassToken(), param1, param2, param3, resolve(MemberQuery.constructor(param1, param2, param3)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3));
    }

    public <A1, A2, A3> AbstractCtorRef3<O, A1, A2, A3> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return new AbstractCtorRef3<>(abstractClassToken(), param1, param2, param3, resolve(MemberQuery.constructor(param1, param2, param3)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3));
    }

    public <R, A1, A2, A3, A4> MethodRef4<O, R, A1, A2, A3, A4> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return new MethodRef4<>(token(), name, result, param1, param2, param3, param4, resolve(MemberQuery.method(name, result, param1, param2, param3, param4)).traits(), Invocables.declared(token(), param1, param2, param3, param4));
    }

    public <A1, A2, A3, A4> VoidMethodRef4<O, A1, A2, A3, A4> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return new VoidMethodRef4<>(token(), name, param1, param2, param3, param4, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4)).traits(), Invocables.declared(token(), param1, param2, param3, param4));
    }

    public <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return new StaticMethodRef4<>(token(), name, result, param1, param2, param3, param4, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4)).traits(), Invocables.declared(token(), param1, param2, param3, param4));
    }

    public <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return new VoidStaticMethodRef4<>(token(), name, param1, param2, param3, param4, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4)).traits(), Invocables.declared(token(), param1, param2, param3, param4));
    }

    public <A1, A2, A3, A4> CtorRef4<O, A1, A2, A3, A4> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return new CtorRef4<>(concreteClassToken(), param1, param2, param3, param4, resolve(MemberQuery.constructor(param1, param2, param3, param4)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4));
    }

    public <A1, A2, A3, A4> AbstractCtorRef4<O, A1, A2, A3, A4> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return new AbstractCtorRef4<>(abstractClassToken(), param1, param2, param3, param4, resolve(MemberQuery.constructor(param1, param2, param3, param4)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4));
    }

    public <R, A1, A2, A3, A4, A5> MethodRef5<O, R, A1, A2, A3, A4, A5> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return new MethodRef5<>(token(), name, result, param1, param2, param3, param4, param5, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5));
    }

    public <A1, A2, A3, A4, A5> VoidMethodRef5<O, A1, A2, A3, A4, A5> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return new VoidMethodRef5<>(token(), name, param1, param2, param3, param4, param5, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5));
    }

    public <R, A1, A2, A3, A4, A5> StaticMethodRef5<R, A1, A2, A3, A4, A5> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return new StaticMethodRef5<>(token(), name, result, param1, param2, param3, param4, param5, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5));
    }

    public <A1, A2, A3, A4, A5> VoidStaticMethodRef5<A1, A2, A3, A4, A5> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return new VoidStaticMethodRef5<>(token(), name, param1, param2, param3, param4, param5, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5));
    }

    public <A1, A2, A3, A4, A5> CtorRef5<O, A1, A2, A3, A4, A5> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return new CtorRef5<>(concreteClassToken(), param1, param2, param3, param4, param5, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5));
    }

    public <A1, A2, A3, A4, A5> AbstractCtorRef5<O, A1, A2, A3, A4, A5> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return new AbstractCtorRef5<>(abstractClassToken(), param1, param2, param3, param4, param5, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5));
    }

    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return new MethodRef6<>(token(), name, result, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6));
    }

    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<O, A1, A2, A3, A4, A5, A6> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return new VoidMethodRef6<>(token(), name, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6));
    }

    public <R, A1, A2, A3, A4, A5, A6> StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return new StaticMethodRef6<>(token(), name, result, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6));
    }

    public <A1, A2, A3, A4, A5, A6> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return new VoidStaticMethodRef6<>(token(), name, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6));
    }

    public <A1, A2, A3, A4, A5, A6> CtorRef6<O, A1, A2, A3, A4, A5, A6> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return new CtorRef6<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6));
    }

    public <A1, A2, A3, A4, A5, A6> AbstractCtorRef6<O, A1, A2, A3, A4, A5, A6> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return new AbstractCtorRef6<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return new MethodRef7<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7));
    }

    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<O, A1, A2, A3, A4, A5, A6, A7> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return new VoidMethodRef7<>(token(), name, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return new StaticMethodRef7<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7));
    }

    public <A1, A2, A3, A4, A5, A6, A7> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return new VoidStaticMethodRef7<>(token(), name, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7));
    }

    public <A1, A2, A3, A4, A5, A6, A7> CtorRef7<O, A1, A2, A3, A4, A5, A6, A7> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return new CtorRef7<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7));
    }

    public <A1, A2, A3, A4, A5, A6, A7> AbstractCtorRef7<O, A1, A2, A3, A4, A5, A6, A7> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return new AbstractCtorRef7<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return new MethodRef8<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return new VoidMethodRef8<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return new StaticMethodRef8<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return new VoidStaticMethodRef8<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return new CtorRef8<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8> AbstractCtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return new AbstractCtorRef8<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return new MethodRef9<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return new VoidMethodRef9<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return new StaticMethodRef9<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return new VoidStaticMethodRef9<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return new CtorRef9<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> AbstractCtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return new AbstractCtorRef9<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return new MethodRef10<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return new VoidMethodRef10<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return new StaticMethodRef10<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return new VoidStaticMethodRef10<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return new CtorRef10<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> AbstractCtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return new AbstractCtorRef10<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return new MethodRef11<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return new VoidMethodRef11<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return new StaticMethodRef11<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return new VoidStaticMethodRef11<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return new CtorRef11<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> AbstractCtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return new AbstractCtorRef11<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return new MethodRef12<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return new VoidMethodRef12<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return new StaticMethodRef12<>(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return new VoidStaticMethodRef12<>(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits(), Invocables.declared(token(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return new CtorRef12<>(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits(), Invocables.declared(concreteClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }

    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> AbstractCtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> abstractCtor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return new AbstractCtorRef12<>(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits(), Invocables.declared(abstractClassToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12));
    }
}
