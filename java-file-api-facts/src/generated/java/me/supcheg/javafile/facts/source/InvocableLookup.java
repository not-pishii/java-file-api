package me.supcheg.javafile.facts.source;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef10;
import me.supcheg.javafile.facts.CtorRef11;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.CtorRef5;
import me.supcheg.javafile.facts.CtorRef6;
import me.supcheg.javafile.facts.CtorRef7;
import me.supcheg.javafile.facts.CtorRef8;
import me.supcheg.javafile.facts.CtorRef9;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef10;
import me.supcheg.javafile.facts.MethodRef11;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodRef5;
import me.supcheg.javafile.facts.MethodRef6;
import me.supcheg.javafile.facts.MethodRef7;
import me.supcheg.javafile.facts.MethodRef8;
import me.supcheg.javafile.facts.MethodRef9;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef10;
import me.supcheg.javafile.facts.StaticMethodRef11;
import me.supcheg.javafile.facts.StaticMethodRef12;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.StaticMethodRef5;
import me.supcheg.javafile.facts.StaticMethodRef6;
import me.supcheg.javafile.facts.StaticMethodRef7;
import me.supcheg.javafile.facts.StaticMethodRef8;
import me.supcheg.javafile.facts.StaticMethodRef9;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef10;
import me.supcheg.javafile.facts.VoidMethodRef11;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidMethodRef5;
import me.supcheg.javafile.facts.VoidMethodRef6;
import me.supcheg.javafile.facts.VoidMethodRef7;
import me.supcheg.javafile.facts.VoidMethodRef8;
import me.supcheg.javafile.facts.VoidMethodRef9;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef10;
import me.supcheg.javafile.facts.VoidStaticMethodRef11;
import me.supcheg.javafile.facts.VoidStaticMethodRef12;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef5;
import me.supcheg.javafile.facts.VoidStaticMethodRef6;
import me.supcheg.javafile.facts.VoidStaticMethodRef7;
import me.supcheg.javafile.facts.VoidStaticMethodRef8;
import me.supcheg.javafile.facts.VoidStaticMethodRef9;

public interface InvocableLookup<O> {
    DeclaredToken<O> token();

    ClassToken<O> classToken();

    Resolution resolve(MemberQuery query);

    default <R> MethodRef0<O, R> method(String name, TypeToken<R> result) {
        return MethodRef0.introduce(token(), name, result, resolve(MemberQuery.method(name, result)).traits());
    }

    default VoidMethodRef0<O> voidMethod(String name) {
        return VoidMethodRef0.introduce(token(), name, resolve(MemberQuery.voidMethod(name)).traits());
    }

    default <R> StaticMethodRef0<R> staticMethod(String name, TypeToken<R> result) {
        return StaticMethodRef0.introduce(token(), name, result, resolve(MemberQuery.staticMethod(name, result)).traits());
    }

    default VoidStaticMethodRef0 voidStaticMethod(String name) {
        return VoidStaticMethodRef0.introduce(token(), name, resolve(MemberQuery.voidStaticMethod(name)).traits());
    }

    default CtorRef0<O> ctor() {
        return CtorRef0.introduce(classToken(), resolve(MemberQuery.constructor()).traits());
    }

    default <R, A1> MethodRef1<O, R, A1> method(String name, TypeToken<R> result, TypeToken<A1> param1) {
        return MethodRef1.introduce(token(), name, result, param1, resolve(MemberQuery.method(name, result, param1)).traits());
    }

    default <A1> VoidMethodRef1<O, A1> voidMethod(String name, TypeToken<A1> param1) {
        return VoidMethodRef1.introduce(token(), name, param1, resolve(MemberQuery.voidMethod(name, param1)).traits());
    }

    default <R, A1> StaticMethodRef1<R, A1> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1) {
        return StaticMethodRef1.introduce(token(), name, result, param1, resolve(MemberQuery.staticMethod(name, result, param1)).traits());
    }

    default <A1> VoidStaticMethodRef1<A1> voidStaticMethod(String name, TypeToken<A1> param1) {
        return VoidStaticMethodRef1.introduce(token(), name, param1, resolve(MemberQuery.voidStaticMethod(name, param1)).traits());
    }

    default <A1> CtorRef1<O, A1> ctor(TypeToken<A1> param1) {
        return CtorRef1.introduce(classToken(), param1, resolve(MemberQuery.constructor(param1)).traits());
    }

    default <R, A1, A2> MethodRef2<O, R, A1, A2> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2) {
        return MethodRef2.introduce(token(), name, result, param1, param2, resolve(MemberQuery.method(name, result, param1, param2)).traits());
    }

    default <A1, A2> VoidMethodRef2<O, A1, A2> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2) {
        return VoidMethodRef2.introduce(token(), name, param1, param2, resolve(MemberQuery.voidMethod(name, param1, param2)).traits());
    }

    default <R, A1, A2> StaticMethodRef2<R, A1, A2> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2) {
        return StaticMethodRef2.introduce(token(), name, result, param1, param2, resolve(MemberQuery.staticMethod(name, result, param1, param2)).traits());
    }

    default <A1, A2> VoidStaticMethodRef2<A1, A2> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2) {
        return VoidStaticMethodRef2.introduce(token(), name, param1, param2, resolve(MemberQuery.voidStaticMethod(name, param1, param2)).traits());
    }

    default <A1, A2> CtorRef2<O, A1, A2> ctor(TypeToken<A1> param1, TypeToken<A2> param2) {
        return CtorRef2.introduce(classToken(), param1, param2, resolve(MemberQuery.constructor(param1, param2)).traits());
    }

    default <R, A1, A2, A3> MethodRef3<O, R, A1, A2, A3> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return MethodRef3.introduce(token(), name, result, param1, param2, param3, resolve(MemberQuery.method(name, result, param1, param2, param3)).traits());
    }

    default <A1, A2, A3> VoidMethodRef3<O, A1, A2, A3> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return VoidMethodRef3.introduce(token(), name, param1, param2, param3, resolve(MemberQuery.voidMethod(name, param1, param2, param3)).traits());
    }

    default <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return StaticMethodRef3.introduce(token(), name, result, param1, param2, param3, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3)).traits());
    }

    default <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return VoidStaticMethodRef3.introduce(token(), name, param1, param2, param3, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3)).traits());
    }

    default <A1, A2, A3> CtorRef3<O, A1, A2, A3> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3) {
        return CtorRef3.introduce(classToken(), param1, param2, param3, resolve(MemberQuery.constructor(param1, param2, param3)).traits());
    }

    default <R, A1, A2, A3, A4> MethodRef4<O, R, A1, A2, A3, A4> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return MethodRef4.introduce(token(), name, result, param1, param2, param3, param4, resolve(MemberQuery.method(name, result, param1, param2, param3, param4)).traits());
    }

    default <A1, A2, A3, A4> VoidMethodRef4<O, A1, A2, A3, A4> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return VoidMethodRef4.introduce(token(), name, param1, param2, param3, param4, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4)).traits());
    }

    default <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return StaticMethodRef4.introduce(token(), name, result, param1, param2, param3, param4, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4)).traits());
    }

    default <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return VoidStaticMethodRef4.introduce(token(), name, param1, param2, param3, param4, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4)).traits());
    }

    default <A1, A2, A3, A4> CtorRef4<O, A1, A2, A3, A4> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4) {
        return CtorRef4.introduce(classToken(), param1, param2, param3, param4, resolve(MemberQuery.constructor(param1, param2, param3, param4)).traits());
    }

    default <R, A1, A2, A3, A4, A5> MethodRef5<O, R, A1, A2, A3, A4, A5> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return MethodRef5.introduce(token(), name, result, param1, param2, param3, param4, param5, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5)).traits());
    }

    default <A1, A2, A3, A4, A5> VoidMethodRef5<O, A1, A2, A3, A4, A5> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return VoidMethodRef5.introduce(token(), name, param1, param2, param3, param4, param5, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5)).traits());
    }

    default <R, A1, A2, A3, A4, A5> StaticMethodRef5<R, A1, A2, A3, A4, A5> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return StaticMethodRef5.introduce(token(), name, result, param1, param2, param3, param4, param5, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5)).traits());
    }

    default <A1, A2, A3, A4, A5> VoidStaticMethodRef5<A1, A2, A3, A4, A5> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return VoidStaticMethodRef5.introduce(token(), name, param1, param2, param3, param4, param5, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5)).traits());
    }

    default <A1, A2, A3, A4, A5> CtorRef5<O, A1, A2, A3, A4, A5> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5) {
        return CtorRef5.introduce(classToken(), param1, param2, param3, param4, param5, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6> MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return MethodRef6.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6)).traits());
    }

    default <A1, A2, A3, A4, A5, A6> VoidMethodRef6<O, A1, A2, A3, A4, A5, A6> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return VoidMethodRef6.introduce(token(), name, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6> StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return StaticMethodRef6.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6)).traits());
    }

    default <A1, A2, A3, A4, A5, A6> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return VoidStaticMethodRef6.introduce(token(), name, param1, param2, param3, param4, param5, param6, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6)).traits());
    }

    default <A1, A2, A3, A4, A5, A6> CtorRef6<O, A1, A2, A3, A4, A5, A6> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6) {
        return CtorRef6.introduce(classToken(), param1, param2, param3, param4, param5, param6, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return MethodRef7.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<O, A1, A2, A3, A4, A5, A6, A7> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return VoidMethodRef7.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return StaticMethodRef7.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return VoidStaticMethodRef7.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7> CtorRef7<O, A1, A2, A3, A4, A5, A6, A7> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7) {
        return CtorRef7.introduce(classToken(), param1, param2, param3, param4, param5, param6, param7, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return MethodRef8.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return VoidMethodRef8.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return StaticMethodRef8.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return VoidStaticMethodRef8.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8) {
        return CtorRef8.introduce(classToken(), param1, param2, param3, param4, param5, param6, param7, param8, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return MethodRef9.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return VoidMethodRef9.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return StaticMethodRef9.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return VoidStaticMethodRef9.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9) {
        return CtorRef9.introduce(classToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return MethodRef10.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return VoidMethodRef10.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return StaticMethodRef10.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return VoidStaticMethodRef10.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10) {
        return CtorRef10.introduce(classToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return MethodRef11.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return VoidMethodRef11.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return StaticMethodRef11.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return VoidStaticMethodRef11.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11) {
        return CtorRef11.introduce(classToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return MethodRef12.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.method(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return VoidMethodRef12.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.voidMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits());
    }

    default <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> staticMethod(String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return StaticMethodRef12.introduce(token(), name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.staticMethod(name, result, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> voidStaticMethod(String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return VoidStaticMethodRef12.introduce(token(), name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.voidStaticMethod(name, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits());
    }

    default <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor(TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, TypeToken<A11> param11, TypeToken<A12> param12) {
        return CtorRef12.introduce(classToken(), param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12, resolve(MemberQuery.constructor(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, param12)).traits());
    }
}
