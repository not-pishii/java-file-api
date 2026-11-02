package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class StaticMethodRef1<R, A1> implements InvocableFamily {
    private final DeclaredToken<?> owner;

    private final String name;

    private final TypeToken<R> result;

    private final TypeToken<A1> param1;

    private final MemberTraits traits;

    private final List<Param> declaredParams;

    StaticMethodRef1(DeclaredToken<?> owner, String name, TypeToken<R> result, TypeToken<A1> param1, MemberTraits traits, List<Param> declaredParams) {
        this.owner = owner;
        this.name = Invocables.requireMethodName(name);
        this.result = result;
        this.param1 = param1;
        this.traits = traits;
        this.declaredParams = declaredParams;
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

    public TypeToken<R> result() {
        return this.result;
    }

    public TypeToken<A1> param1() {
        return this.param1;
    }

    @Override
    public Optional<TypeToken<?>> resultType() {
        return Optional.of(this.result);
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

    /**
     * The fact of a call of this method with the explicit type arguments `typeArgs`, which javac takes for the type parameters of the declaration instead of inferring them from the arguments.
     *
     * @param typeArgs the type arguments of the call, one per type parameter of the method
     * @return the fact of the call
     * @throws IllegalArgumentException if the method is not generic, or has another number of type parameters
     */
    public StaticMethodRef1<R, A1> withTypeArgs(RefToken<?>... typeArgs) {
        return new StaticMethodRef1<>(this.owner, this.name, this.result, this.param1, Invocables.withTypeArgs(this, this.traits, typeArgs), this.declaredParams);
    }
}
