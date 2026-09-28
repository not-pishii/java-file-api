package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class MethodRef0<O, R> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final TypeToken<R> result;

    private final MemberTraits traits;

    private MethodRef0(DeclaredToken<O> owner, String name, TypeToken<R> result, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.result = result;
        this.traits = traits;
    }

    public static <O, R> MethodRef0<O, R> introduce(DeclaredToken<O> owner, String name, TypeToken<R> result, MemberTraits traits) {
        return new MethodRef0<>(owner, name, result, traits);
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

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.of(this.result);
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of();
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
