package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class StaticMethodRef4<R, A1, A2, A3, A4> implements Invocable {
    private final DeclaredToken<?> owner;

    private final String name;

    private final TypeToken<R> result;

    private final TypeToken<A1> param1;

    private final TypeToken<A2> param2;

    private final TypeToken<A3> param3;

    private final TypeToken<A4> param4;

    private final MemberTraits traits;

    private StaticMethodRef4(DeclaredToken<?> owner, String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.result = result;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.traits = traits;
    }

    public static <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> introduce(DeclaredToken<?> owner, String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, MemberTraits traits) {
        return new StaticMethodRef4<>(owner, name, result, param1, param2, param3, param4, traits);
    }

    @Override
    public InvocableKind kind() {
        return InvocableKind.STATIC_METHOD;
    }

    @Override
    public DeclaredToken<?> owner() {
        return this.owner;
    }

    @Override
    public String name() {
        return this.name;
    }

    public TypeToken<R> result() {
        return this.result;
    }

    public TypeToken<A1> param1() {
        return this.param1;
    }

    public TypeToken<A2> param2() {
        return this.param2;
    }

    public TypeToken<A3> param3() {
        return this.param3;
    }

    public TypeToken<A4> param4() {
        return this.param4;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.of(this.result);
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1, this.param2, this.param3, this.param4);
    }

    @Override
    public MemberTraits traits() {
        return this.traits;
    }

    @Override
    public String toString() {
        return Invocables.describe(this);
    }
}
