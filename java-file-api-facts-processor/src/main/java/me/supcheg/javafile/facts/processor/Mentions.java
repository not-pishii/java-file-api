package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.FieldModel;
import me.supcheg.javafile.langmodel.mirror.MemberModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/// The classes and interfaces that types, members and type parameters
/// mention: the class of a type and of each type argument, wildcard bound
/// and array component, but no type variables or primitives.
///
/// The mentions come in the order they are written in, with repetitions.
final class Mentions {
    private Mentions() {}

    /// What a type mentions in the bounds of its type parameters and in its members.
    ///
    /// @param model the type
    /// @return the mentions
    static Stream<ClassDesc> of(TypeModel model) {
        return Stream.concat(ofBounds(model.typeParams()), ofMembers(model.members()));
    }

    /// What the signatures of members mention.
    ///
    /// @param members the members
    /// @return the mentions
    static Stream<ClassDesc> ofMembers(Collection<? extends MemberModel> members) {
        return members.stream().flatMap(Mentions::of);
    }

    /// What the signature of a member mentions, the bounds of its own type
    /// parameters among it.
    ///
    /// @param member the member
    /// @return the mentions
    static Stream<ClassDesc> of(MemberModel member) {
        return switch (member) {
            case MethodModel method ->
                Stream.of(
                                ofBounds(method.typeParams()),
                                method.result().stream().flatMap(Mentions::of),
                                method.params().stream().flatMap(Mentions::of),
                                method.throwsTypes().stream().flatMap(Mentions::of))
                        .flatMap(Function.identity());
            case CtorModel ctor ->
                Stream.of(
                                ofBounds(ctor.typeParams()),
                                ctor.params().stream().flatMap(Mentions::of),
                                ctor.throwsTypes().stream().flatMap(Mentions::of))
                        .flatMap(Function.identity());
            case FieldModel field -> of(field.type());
        };
    }

    /// What a type mentions.
    ///
    /// @param type the type
    /// @return the mentions
    static Stream<ClassDesc> of(TypeRef type) {
        return switch (type) {
            case ClassTypeRef(var desc, var _) -> Stream.of(desc);
            case ParameterizedTypeRef(var raw, var args, var _) ->
                Stream.concat(Stream.of(raw), args.stream().flatMap(Mentions::of));
            case ArrayTypeRef(var component, var _) -> of(component);
            case TypeVarRef _, PrimitiveTypeRef _ -> Stream.empty();
        };
    }

    /// What the bounds of type parameters mention.
    ///
    /// @param typeParams the type parameters
    /// @return the mentions
    static Stream<ClassDesc> ofBounds(List<TypeParam> typeParams) {
        return typeParams.stream()
                .map(TypeParam::bounds)
                .flatMap(Collection::stream)
                .flatMap(Mentions::of);
    }

    private static Stream<ClassDesc> of(TypeArg arg) {
        return switch (arg) {
            case ExactTypeArg(var type) -> of(type);
            case ExtendsTypeArg(var bound) -> of(bound);
            case SuperTypeArg(var bound) -> of(bound);
            case UnboundedTypeArg _ -> Stream.empty();
        };
    }
}
