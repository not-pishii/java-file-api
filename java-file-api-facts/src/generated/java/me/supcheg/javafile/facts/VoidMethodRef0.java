package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class VoidMethodRef0<O> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final MemberTraits traits;

    private VoidMethodRef0(DeclaredToken<O> owner, String name, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.traits = traits;
    }

    public static <O> VoidMethodRef0<O> introduce(DeclaredToken<O> owner, String name, MemberTraits traits) {
        return new VoidMethodRef0<>(owner, name, traits);
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

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.empty();
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
