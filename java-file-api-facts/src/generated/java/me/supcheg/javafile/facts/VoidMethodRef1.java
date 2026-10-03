package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidMethodRef1<O, A1> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final TypeToken<A1> param1;

    private final MemberTraits traits;

    private final List<Param> declaredParams;

    VoidMethodRef1(DeclaredToken<O> owner, String name, TypeToken<A1> param1, MemberTraits traits, List<Param> declaredParams) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.param1 = param1;
        this.traits = traits;
        this.declaredParams = declaredParams;
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
    public List<Param> declaredParams() {
        return this.declaredParams;
    }

    @Override
    public String toString() {
        return Invocables.describe(this);
    }
}
