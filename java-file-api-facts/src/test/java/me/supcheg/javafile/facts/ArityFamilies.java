package me.supcheg.javafile.facts;

import me.supcheg.javafile.facts.source.MemberQuery;
import me.supcheg.javafile.facts.source.Resolution;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// What the generated tests of the arity families (`ArityNFamiliesTest`, written by `FactsCodegen` beside the
/// families themselves) are made of: a class `arity.Subject`, the result `arity.Result` of its methods and their
/// parameters `arity.A1`..`arity.A12`, each a token of a type of its own, so that a fact which gave one parameter
/// for another would not compile.
final class ArityFamilies {
    private ArityFamilies() {}

    /// Phantom of `arity.Subject`, the owner of the members.
    interface Subject {}

    /// Phantom of `arity.Functional`, a functional interface.
    interface Functional {}

    /// Phantom of `arity.Result`, the result of the methods.
    interface Result {}

    interface A1 {}

    interface A2 {}

    interface A3 {}

    interface A4 {}

    interface A5 {}

    interface A6 {}

    interface A7 {}

    interface A8 {}

    interface A9 {}

    interface A10 {}

    interface A11 {}

    interface A12 {}

    /// A class that is neither abstract nor final: `new` makes it and a subclass extends it.
    static final OpenClassToken<Subject> OWNER = UnsafeFacts.openClassToken(
            Types.of(ClassDesc.of("arity", "Subject")), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);

    static final FinalClassToken<Result> RESULT = type("Result");
    static final FinalClassToken<A1> PARAM1 = type("A1");
    static final FinalClassToken<A2> PARAM2 = type("A2");
    static final FinalClassToken<A3> PARAM3 = type("A3");
    static final FinalClassToken<A4> PARAM4 = type("A4");
    static final FinalClassToken<A5> PARAM5 = type("A5");
    static final FinalClassToken<A6> PARAM6 = type("A6");
    static final FinalClassToken<A7> PARAM7 = type("A7");
    static final FinalClassToken<A8> PARAM8 = type("A8");
    static final FinalClassToken<A9> PARAM9 = type("A9");
    static final FinalClassToken<A10> PARAM10 = type("A10");
    static final FinalClassToken<A11> PARAM11 = type("A11");
    static final FinalClassToken<A12> PARAM12 = type("A12");

    /// The traits of every member: not the default ones, so that a fact which lost its traits would show.
    static final MemberTraits TRAITS = MemberTraits.FINAL;

    private static <T> FinalClassToken<T> type(String name) {
        return UnsafeFacts.finalClassToken(
                Types.of(ClassDesc.of("arity", name)), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
    }

    /// The interface `arity.Functional` whose single abstract method is `run` of the parameters `params`.
    static InterfaceToken<Functional> functional(TypeToken<?>... params) {
        MethodSignature run = new MethodSignature(
                "run", Stream.of(params).map(TypeToken::erasure).toList());
        return UnsafeFacts.interfaceToken(
                Types.of(ClassDesc.of("arity", "Functional")), new MethodTable(Set.of(run), Set.of(), Set.of()));
    }

    /// A source of the facts of [#OWNER] that proves every member it is asked for, with [#TRAITS], and keeps what
    /// it was asked.
    static final class Asked {
        private final List<MemberQuery> queries = new ArrayList<>();
        private final FactSource<Subject> source = UnsafeFacts.factSource(OWNER, query -> {
            queries.add(query);
            return Resolution.of(TRAITS);
        });

        FactSource<Subject> source() {
            return source;
        }

        List<MemberQuery> queries() {
            return queries;
        }
    }

    /// A fact is of the member it was made of: what an [Invocable] tells of it, whatever its family and arity.
    static void assertFact(
            Invocable fact,
            InvocableKind kind,
            DeclaredToken<?> owner,
            String name,
            Optional<TypeToken<?>> result,
            List<TypeToken<?>> params,
            String described) {
        assertThat(fact.kind()).isEqualTo(kind);
        assertThat(fact.owner()).isSameAs(owner);
        assertThat(fact.name()).isEqualTo(name);
        assertThat(fact.resultType()).isEqualTo(result);
        assertThat(fact.params()).isEqualTo(params);
        assertThat(fact.traits()).isSameAs(TRAITS);
        assertThat(fact.declaredParams())
                .isEqualTo(params.stream()
                        .map(param -> new MethodTableTemplate.Fixed(param.erasure()))
                        .toList());
        assertThat(fact).hasToString(described);
    }
}
