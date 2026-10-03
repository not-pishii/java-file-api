package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidMethodRef0<O> implements Invocable {
    private final DeclaredToken<O> owner;

    private final String name;

    private final MemberTraits traits;

    private final List<Param> declaredParams;

    VoidMethodRef0(DeclaredToken<O> owner, String name, MemberTraits traits, List<Param> declaredParams) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
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
    public List<Param> declaredParams() {
        return this.declaredParams;
    }

    @Override
    public String toString() {
        return Invocables.describe(this);
    }
}
