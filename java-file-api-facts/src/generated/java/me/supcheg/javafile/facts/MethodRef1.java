package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class MethodRef1<O, R, A1> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final TypeToken<R> result;

    private final TypeToken<A1> param1;

    private final MemberTraits traits;

    private MethodRef1(DeclaredToken<O> owner, String name, TypeToken<R> result, TypeToken<A1> param1, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.result = result;
        this.param1 = param1;
        this.traits = traits;
    }

    public static <O, R, A1> MethodRef1<O, R, A1> introduce(DeclaredToken<O> owner, String name, TypeToken<R> result, TypeToken<A1> param1, MemberTraits traits) {
        return new MethodRef1<>(owner, name, result, param1, traits);
    }

    @Override
    public InvocableKind kind() {
        return InvocableKind.INSTANCE_METHOD;
    }

    @Override
    public DeclaredToken<O> owner() {
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

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.of(this.result);
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1);
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
