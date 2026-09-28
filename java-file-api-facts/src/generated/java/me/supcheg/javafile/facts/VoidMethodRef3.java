package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;

public final class VoidMethodRef3<O, A1, A2, A3> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final TypeToken<A1> param1;

    private final TypeToken<A2> param2;

    private final TypeToken<A3> param3;

    private final MemberTraits traits;

    private VoidMethodRef3(DeclaredToken<O> owner, String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, MemberTraits traits) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.traits = traits;
    }

    public static <O, A1, A2, A3> VoidMethodRef3<O, A1, A2, A3> introduce(DeclaredToken<O> owner, String name, TypeToken<A1> param1, TypeToken<A2> param2, TypeToken<A3> param3, MemberTraits traits) {
        return new VoidMethodRef3<>(owner, name, param1, param2, param3, traits);
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

    public TypeToken<A1> param1() {
        return this.param1;
    }

    public TypeToken<A2> param2() {
        return this.param2;
    }

    public TypeToken<A3> param3() {
        return this.param3;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.empty();
    }

    @Override
    public List<TypeToken<?>> params() {
        return List.of(this.param1, this.param2, this.param3);
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
