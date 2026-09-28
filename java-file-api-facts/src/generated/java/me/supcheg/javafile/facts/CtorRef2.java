package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class CtorRef2<O, A1, A2> implements Invocable {
    private final ConcreteClassToken<O> owner;

    private final TypeToken<A1> param1;

    private final TypeToken<A2> param2;

    private final MemberTraits traits;

    CtorRef2(ConcreteClassToken<O> owner, TypeToken<A1> param1, TypeToken<A2> param2, MemberTraits traits) {
        this.owner = owner;
        this.param1 = param1;
        this.param2 = param2;
        this.traits = traits;
    }

    @Override
    public InvocableKind kind() {
        return InvocableKind.CONSTRUCTOR;
    }

    @Override
    public ConcreteClassToken<O> owner() {
        return this.owner;
    }

    @Override
    public String name() {
        return this.owner.erasure().displayName();
    }

    public TypeToken<A1> param1() {
        return this.param1;
    }

    public TypeToken<A2> param2() {
        return this.param2;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.empty();
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1, this.param2);
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
