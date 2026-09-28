package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class CtorRef0<O> implements Invocable {
    private final ClassToken<O> owner;

    private final MemberTraits traits;

    private CtorRef0(ClassToken<O> owner, MemberTraits traits) {
        this.owner = owner;
        this.traits = traits;
    }

    public static <O> CtorRef0<O> introduce(ClassToken<O> owner, MemberTraits traits) {
        return new CtorRef0<>(owner, traits);
    }

    @Override
    public InvocableKind kind() {
        return InvocableKind.CONSTRUCTOR;
    }

    @Override
    public ClassToken<O> owner() {
        return this.owner;
    }

    @Override
    public String name() {
        return this.owner.erasure().displayName();
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
