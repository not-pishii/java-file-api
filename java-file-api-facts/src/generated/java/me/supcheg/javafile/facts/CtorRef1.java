package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class CtorRef1<O, A1> implements Invocable {
    private final ClassToken<O> owner;

    private final TypeToken<A1> param1;

    private final MemberTraits traits;

    private CtorRef1(ClassToken<O> owner, TypeToken<A1> param1, MemberTraits traits) {
        this.owner = owner;
        this.param1 = param1;
        this.traits = traits;
    }

    public static <O, A1> CtorRef1<O, A1> introduce(ClassToken<O> owner, TypeToken<A1> param1, MemberTraits traits) {
        return new CtorRef1<>(owner, param1, traits);
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

    public TypeToken<A1> param1() {
        return this.param1;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.empty();
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
