package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class StaticMethodRef0<R> implements Invocable {
    private final DeclaredToken<?> owner;

    private final String name;

    private final TypeToken<R> result;

    private final MemberTraits traits;

    private StaticMethodRef0(DeclaredToken<?> owner, String name, TypeToken<R> result, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.result = result;
        this.traits = traits;
    }

    public static <R> StaticMethodRef0<R> introduce(DeclaredToken<?> owner, String name, TypeToken<R> result, MemberTraits traits) {
        return new StaticMethodRef0<>(owner, name, result, traits);
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
