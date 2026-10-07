package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class SuperCtorRef1<O, A1> implements Invocable {
    private final ExtendableClassToken<O> owner;

    private final TypeToken<A1> param1;

    private final MemberTraits traits;

    private final List<Param> declaredParams;

    SuperCtorRef1(ExtendableClassToken<O> owner, TypeToken<A1> param1, MemberTraits traits, List<Param> declaredParams) {
        this.owner = owner;
        this.param1 = param1;
        this.traits = traits;
        this.declaredParams = declaredParams;
    }

    @Override
    public InvocableKind kind() {
        return InvocableKind.CONSTRUCTOR;
    }

    @Override
    public ExtendableClassToken<O> owner() {
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
    public List<Param> declaredParams() {
        return this.declaredParams;
    }

    @Override
    public String toString() {
        return Invocables.describeSuper(this);
    }
}
