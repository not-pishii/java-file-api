package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> implements Invocable {
    private final ConcreteClassToken<O> owner;

    private final TypeToken<A1> param1;

    private final TypeToken<A2> param2;

    private final TypeToken<A3> param3;

    private final TypeToken<A4> param4;

    private final TypeToken<A5> param5;

    private final TypeToken<A6> param6;

    private final TypeToken<A7> param7;

    private final TypeToken<A8> param8;

    private final TypeToken<A9> param9;

    private final TypeToken<A10> param10;

    private final MemberTraits traits;

    CtorRef10(ConcreteClassToken<O> owner, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, TypeToken<A9> param9, TypeToken<A10> param10, MemberTraits traits) {
        this.owner = owner;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.param6 = param6;
        this.param7 = param7;
        this.param8 = param8;
        this.param9 = param9;
        this.param10 = param10;
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

    public TypeToken<A3> param3() {
        return this.param3;
    }

    public TypeToken<A4> param4() {
        return this.param4;
    }

    public TypeToken<A5> param5() {
        return this.param5;
    }

    public TypeToken<A6> param6() {
        return this.param6;
    }

    public TypeToken<A7> param7() {
        return this.param7;
    }

    public TypeToken<A8> param8() {
        return this.param8;
    }

    public TypeToken<A9> param9() {
        return this.param9;
    }

    public TypeToken<A10> param10() {
        return this.param10;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.empty();
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1, this.param2, this.param3, this.param4, this.param5, this.param6, this.param7, this.param8, this.param9, this.param10);
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
