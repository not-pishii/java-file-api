package me.supcheg.javafile.langmodel.mirror;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.type.TypeParam;

import java.lang.constant.ClassDesc;
import java.util.List;

/// A declared type as [MirrorTranslator] reads it: what a `TypeShape`
/// holds, plus the members a metamodel makes facts of. Plain data: it makes
/// no facts, and [Canonical#of(TypeModel)] prints it for the fingerprint.
///
/// Type variables are by name, as declared: `#0`, `^0` and so on appear only
/// in the canonical form. Within a member, a type variable of the member
/// shadows one of the type with the same name, as in Java.
///
/// @param desc the class or interface, by binary name (`java.util.Map$Entry`)
/// @param kind the kind of the type
/// @param typeParams the type parameters, with their bounds; a lone `Object` bound is left out
/// @param nonPublicBoundTypes the types that are not `public` mentioned by the bounds of
///                            `typeParams`, by binary name, sorted: a metamodel cannot
///                            declare such a bound, so the type is usable only raw
/// @param superclasses the erased superclass chain, the direct superclass first and `Object`
///                     last; empty for `Object` and an interface
/// @param supertypes every parameterized supertype, transitively, sorted by binary name
/// @param methods every non-`private` method a call on the type may resolve to: declared and
///                inherited instance methods, and the `static` methods of the type and its
///                superclasses, but not those of its superinterfaces; a public method of
///                `Object` that an interface redeclares abstract is not abstract
/// @param enumConstants the enum constants, in declaration order; empty unless an enum
/// @param sealed whether the type is `sealed`
/// @param filter which members are in `members`
/// @param members the members `filter` selects that have facts, in declaration order
/// @param skipped the members `filter` selects that have no facts, in declaration order
public record TypeModel(
        ClassDesc desc,
        DeclaredKind kind,
        List<TypeParam> typeParams,
        List<String> nonPublicBoundTypes,
        List<ClassDesc> superclasses,
        Supertypes supertypes,
        MethodTableTemplate methods,
        List<String> enumConstants,
        boolean sealed,
        MemberFilter filter,
        List<MemberModel> members,
        List<SkippedMember> skipped) {

    /// @throws IllegalArgumentException if `desc` is not a class or interface, or `filter` is
    ///                                  [MemberFilter#NONE] but there are members
    public TypeModel {
        if (!desc.isClassOrInterface()) {
            throw new IllegalArgumentException("a declared type is a class or interface, got " + desc.displayName());
        }
        typeParams = List.copyOf(typeParams);
        nonPublicBoundTypes = List.copyOf(nonPublicBoundTypes);
        superclasses = List.copyOf(superclasses);
        enumConstants = List.copyOf(enumConstants);
        members = List.copyOf(members);
        skipped = List.copyOf(skipped);
        if (filter == MemberFilter.NONE && !(members.isEmpty() && skipped.isEmpty())) {
            throw new IllegalArgumentException("a model without members has members " + members + " " + skipped);
        }
    }
}
