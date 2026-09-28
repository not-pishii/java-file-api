package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class VoidStaticMethodRef0 implements Invocable {
    private final DeclaredToken<?> owner;

    private final String name;

    private final MemberTraits traits;

    private VoidStaticMethodRef0(DeclaredToken<?> owner, String name, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.traits = traits;
    }

    public static VoidStaticMethodRef0 introduce(DeclaredToken<?> owner, String name, MemberTraits traits) {
        return new VoidStaticMethodRef0(owner, name, traits);
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
