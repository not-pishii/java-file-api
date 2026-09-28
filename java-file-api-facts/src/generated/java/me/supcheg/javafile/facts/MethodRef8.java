package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final TypeToken<R> result;

    private final TypeToken<A1> param1;

    private final TypeToken<A2> param2;

    private final TypeToken<A3> param3;

    private final TypeToken<A4> param4;

    private final TypeToken<A5> param5;

    private final TypeToken<A6> param6;

    private final TypeToken<A7> param7;

    private final TypeToken<A8> param8;

    private final MemberTraits traits;

    MethodRef8(DeclaredToken<O> owner, String name, TypeToken<R> result, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, TypeToken<A4> param4, TypeToken<A5> param5, TypeToken<A6> param6, TypeToken<A7> param7, TypeToken<A8> param8, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.result = result;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.param6 = param6;
        this.param7 = param7;
        this.param8 = param8;
        this.traits = traits;
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

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.of(this.result);
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1, this.param2, this.param3, this.param4, this.param5, this.param6, this.param7, this.param8);
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
