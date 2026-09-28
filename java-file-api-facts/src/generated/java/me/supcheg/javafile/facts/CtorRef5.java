package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class CtorRef5<O, A1, A2, A3, A4, A5> implements Invocable {
    private final ClassToken<O> owner;

    private final TypeToken<A1> param1;

    private final TypeToken<A2> param2;

    private final TypeToken<A3> param3;

    private final TypeToken<A4> param4;

    private final TypeToken<A5> param5;

    private final MemberTraits traits;

    private CtorRef5(ClassToken<O> owner, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, MemberTraits traits) {
        this.owner = owner;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.traits = traits;
    }

    public static <O, A1, A2, A3, A4, A5> CtorRef5<O, A1, A2, A3, A4, A5> introduce(ClassToken<O> owner, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, MemberTraits traits) {
        return new CtorRef5<>(owner, param1, param2, param3, param4, param5, traits);
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

    public TypeToken<A2> param2() {
        return this.param2;
    }

    public TypeToken<A3> param3() {
        return this.param3;
    }

    public TypeToken<A4> param4() {
        return this.param4;
    }

    public TypeToken<A5> param5() {
        return this.param5;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.empty();
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1, this.param2, this.param3, this.param4, this.param5);
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
