package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/// The proof that a type is a subtype of another (JLS 4.10), on the data of
/// the facts: what a class being declared is held to where javac would hold
/// the rendered class to it and the types of the generator cannot — the
/// result of a method against the result of every method it overrides (JLS
/// 8.4.5, 8.4.8.3).
///
/// The subtype is a token, which tells what its type is a subtype of; the
/// supertype is a type as it is written. The proof is **conservative**:
/// [Verdict.Proven] is said only of what is a subtype by the rules below, and
/// everything else is [Verdict.Unproven] — whether it is no subtype or one
/// the data does not tell of. A type javac would accept may be refused; one
/// javac would refuse is never proven.
///
/// **Proven:**
///
/// - a type is a subtype of itself: the two are written the same, a primitive
///   type and one with wildcards among them;
/// - a reference type is a subtype of `Object`;
/// - a class or an interface is a subtype of the classes of its superclass
///   chain ([me.supcheg.javafile.facts.TypeShape#superclasses()]) and of the
///   interfaces its shape records
///   ([me.supcheg.javafile.facts.TypeShape#interfaces()]), each as a type
///   without type arguments — a raw one, if it is generic: in what a
///   heritage or a fact writes, the class type of a generic type with no
///   arguments is the raw type, never the type with its own parameters;
/// - a class or an interface is a subtype of the parameterization of a
///   generic supertype that [DeclaredToken#supertypes()] gives for its type
///   arguments, `ArrayList<String>` of `Collection<String>`, `String` of
///   `Comparable<String>`, and of one that contains it (JLS 4.5.1): a type
///   argument that is a type is the same type, `?` contains any, `? extends
///   Object` any, `? extends B` the type `B` and `? extends B`, `? super B`
///   the type `B` and `? super B`;
/// - an array of references is a subtype of an array of a supertype of its
///   component, and any array of `Cloneable` and `java.io.Serializable`;
/// - the class being declared is a subtype of what a supertype it has named
///   so far is a subtype of ([Declaring]).
///
/// **Refused on purpose**, though javac may accept:
///
/// - a supertype the shape does not record: an empty list of interfaces and
///   [Supertypes#NONE] say "not recorded", never "none", so nothing is proven
///   by them and nothing disproven;
/// - a wildcard type argument whose bound is another type than the type
///   argument, `List<String>` of `List<? extends CharSequence>`: the bound is
///   a written type, which tells no supertypes;
/// - a type with a wildcard type argument seen as a parameterization of
///   another generic type, `ArrayList<? extends T>` of `List<? extends T>`
///   (capture conversion), and a raw type as a parameterized one (unchecked
///   conversion, JLS 5.1.9);
/// - a type variable as anything but itself, `Object` and what a bound of it
///   is a subtype of — where it is a type parameter of the class being
///   declared ([Declaring]): the token of another does not tell its bounds;
/// - a primitive type as any other type, and an array of primitives as an
///   array of anything else: the result of a method that overrides a
///   primitive or a `void` one is the same (JLS 8.4.5);
/// - the class being declared as a supertype it names later in its header and
///   did not say it is about to name ([Overriding#becoming]).
final class Subtyping {
    private static final ClassDesc CLONEABLE = ClassDesc.of("java.lang", "Cloneable");
    private static final ClassDesc SERIALIZABLE = ClassDesc.of("java.io", "Serializable");
    private static final Verdict PROVEN = new Verdict.Proven();

    private Subtyping() {}

    /// Whether the type of `sub` is a subtype of `sup`.
    ///
    /// @param declaring the class being declared, and what it is a subtype of so far
    /// @param sub the token of the subtype
    /// @param sup the supertype, as it is written
    /// @return that it is, or why it is not known to be
    static Verdict prove(Declaring declaring, TypeToken<?> sub, TypeRef sup) {
        if (sub.typeRef().equals(sup)) {
            return PROVEN;
        }
        return switch (sub) {
            case PrimitiveToken<?, ?, ?> _ ->
                unproven("a primitive type is a subtype of no other type: the result of a method that overrides"
                        + " one of a primitive result is the same type (JLS 8.4.5)");
            case RefToken<?> ref ->
                switch (sup) {
                    case PrimitiveTypeRef _ -> unproven("a reference type is no subtype of a primitive one");
                    case TypeVarRef variable ->
                        unproven("the type variable " + variable.name() + " has no subtype known but itself");
                    case ClassTypeRef cls when cls.desc().equals(ConstantDescs.CD_Object) -> PROVEN;
                    case ClassTypeRef cls -> ofClass(declaring, ref, cls);
                    case ParameterizedTypeRef parameterized -> ofParameterized(declaring, ref, parameterized);
                    case ArrayTypeRef array -> ofArray(declaring, ref, array);
                };
        };
    }

    private static Verdict ofClass(Declaring declaring, RefToken<?> sub, ClassTypeRef sup) {
        return switch (sub) {
            case DeclaredToken<?> declared when declaring.is(declared) -> bySupertype(declaring, sup);
            case DeclaredToken<?> declared ->
                declared.shape().desc().equals(sup.desc())
                                || declared.shape().superclasses().contains(sup.desc())
                                || declared.shape().interfaces().contains(sup.desc())
                        ? PROVEN
                        : unproven("the metamodel of " + declared + " records no supertype "
                                + TypeNames.describe(sup.desc()));
            case ArrayToken<?, ?> _ ->
                sup.desc().equals(CLONEABLE) || sup.desc().equals(SERIALIZABLE)
                        ? PROVEN
                        : unproven("an array is of Object, Cloneable and java.io.Serializable alone (JLS 4.10.3)");
            case TypeVarToken<?> variable -> ofVariable(declaring, variable, sup);
        };
    }

    private static Verdict ofParameterized(Declaring declaring, RefToken<?> sub, ParameterizedTypeRef sup) {
        return switch (sub) {
            case DeclaredToken<?> declared when declaring.is(declared) -> bySupertype(declaring, sup);
            case DeclaredToken<?> declared ->
                parameterization(declared, sup.raw())
                        .map(mine -> contained(mine, sup)
                                ? PROVEN
                                : unproven(declared + " is " + TypeNames.describe(mine)
                                        + ", whose type arguments are not known to be contained by those of "
                                        + TypeNames.describe(sup) + " (JLS 4.5.1)"))
                        .orElseGet(() -> unproven("the metamodel of " + declared + " tells no parameterization of "
                                + TypeNames.describe(sup.raw()) + " it is of — the supertype is not recorded, or"
                                + " the type is raw or has a wildcard type argument"));
            case ArrayToken<?, ?> _ -> unproven("an array is of no parameterized type");
            case TypeVarToken<?> variable -> ofVariable(declaring, variable, sup);
        };
    }

    private static Verdict ofArray(Declaring declaring, RefToken<?> sub, ArrayTypeRef sup) {
        return switch (sub) {
            case ArrayToken<?, ?> array ->
                switch (array.component()) {
                    case RefToken<?> component ->
                        switch (prove(declaring, component, sup.component())) {
                            case Verdict.Proven proven -> proven;
                            case Verdict.Unproven(var why) -> unproven("of the components, " + why);
                        };
                    case PrimitiveToken<?, ?, ?> _ ->
                        unproven("an array of a primitive type is a subtype of no other array type (JLS 4.10.3)");
                };
            case DeclaredToken<?> _ -> unproven("a class or an interface is no array");
            case TypeVarToken<?> variable -> ofVariable(declaring, variable, sup);
        };
    }

    /// A type parameter of the class being declared is of what a bound of it is of.
    private static Verdict ofVariable(Declaring declaring, TypeVarToken<?> variable, TypeRef sup) {
        return declaring.variables().stream()
                        .filter(bounded -> bounded.variable() == variable)
                        .flatMap(bounded -> bounded.bounds().stream())
                        .anyMatch(bound -> prove(declaring, bound, sup) instanceof Verdict.Proven)
                ? PROVEN
                : unproven("the type variable " + variable.typeRef().name() + " is known to be of itself, of"
                        + " Object and of what its bounds are of — where it is a type parameter of the class"
                        + " being declared —, and no bound of it is known to be one");
    }

    /// The class being declared is of what a supertype it has named is of.
    private static Verdict bySupertype(Declaring declaring, TypeRef sup) {
        return declaring.supertypes().stream()
                        .anyMatch(supertype -> prove(declaring, supertype, sup) instanceof Verdict.Proven)
                ? PROVEN
                : unproven("no supertype the class has named so far is known to be one — a supertype that makes"
                        + " the class of it is named before the one that asks for it, or said first by"
                        + " becoming(token)");
    }

    /// The parameterization of the generic type `raw` that the type of `declared` is, or is a subtype of.
    private static Optional<ParameterizedTypeRef> parameterization(DeclaredToken<?> declared, ClassDesc raw) {
        Supertypes supertypes = declared.supertypes();
        return switch (declared.typeRef()) {
            case ParameterizedTypeRef mine when mine.raw().equals(raw) -> Optional.of(mine);
            case ParameterizedTypeRef mine -> {
                List<TypeRef> arguments = mine.args().stream()
                        .flatMap(argument -> argument instanceof ExactTypeArg(TypeRef type)
                                ? java.util.stream.Stream.of(type)
                                : java.util.stream.Stream.<TypeRef>empty())
                        .toList();
                yield arguments.size() == mine.args().size()
                                && arguments.size()
                                        == supertypes.typeParameters().size()
                        ? supertypes.supertype(raw, arguments)
                        : Optional.empty();
            }
            // Not generic: its supertypes are in terms of no type parameter. A raw type has erased ones.
            case ClassTypeRef _ ->
                declared.shape().typeParameters().isEmpty() ? supertypes.supertype(raw) : Optional.empty();
        };
    }

    /// Whether every type argument of `sup` contains that of `sub`, a parameterization of the same type.
    private static boolean contained(ParameterizedTypeRef sub, ParameterizedTypeRef sup) {
        return sub.args().size() == sup.args().size()
                && IntStream.range(0, sup.args().size())
                        .allMatch(i -> contains(sup.args().get(i), sub.args().get(i)));
    }

    private static boolean contains(TypeArg sup, TypeArg sub) {
        return switch (sup) {
            case UnboundedTypeArg _ -> true;
            case ExactTypeArg(TypeRef type) -> sub instanceof ExactTypeArg(TypeRef mine) && mine.equals(type);
            case ExtendsTypeArg(TypeRef bound) ->
                isObject(bound)
                        || switch (sub) {
                            case ExactTypeArg(TypeRef mine) -> mine.equals(bound);
                            case ExtendsTypeArg(TypeRef mine) -> mine.equals(bound);
                            case SuperTypeArg _, UnboundedTypeArg _ -> false;
                        };
            case SuperTypeArg(TypeRef bound) ->
                switch (sub) {
                    case ExactTypeArg(TypeRef mine) -> mine.equals(bound);
                    case SuperTypeArg(TypeRef mine) -> mine.equals(bound);
                    case ExtendsTypeArg _, UnboundedTypeArg _ -> false;
                };
        };
    }

    private static boolean isObject(TypeRef type) {
        return type instanceof ClassTypeRef cls && cls.desc().equals(ConstantDescs.CD_Object);
    }

    private static Verdict unproven(String why) {
        return new Verdict.Unproven(why);
    }

    /// The class being declared, as the proof knows it: its token tells neither its interfaces
    /// nor — before its superclass is named — its superclass chain, so what it is a subtype of
    /// is told here.
    ///
    /// @param self the class being declared
    /// @param supertypes the supertypes it has named so far, the superclass first
    /// @param variables its type parameters, each with its bounds
    record Declaring(ClassDesc self, List<DeclaredToken<?>> supertypes, List<Bounded> variables) {
        Declaring {
            supertypes = List.copyOf(supertypes);
            variables = List.copyOf(variables);
        }

        /// A class without type parameters.
        Declaring(ClassDesc self, List<DeclaredToken<?>> supertypes) {
            this(self, supertypes, List.of());
        }

        /// The class with its type parameters.
        Declaring knowing(List<Bounded> variables) {
            return new Declaring(self, supertypes, variables);
        }

        boolean is(DeclaredToken<?> token) {
            return token.shape().desc().equals(self);
        }
    }

    /// A type parameter of the class being declared, known by the identity of its token.
    ///
    /// @param variable the token of the type parameter
    /// @param bounds its bounds: the leftmost, a type of the generator, then those said by a witness
    record Bounded(TypeVarToken<?> variable, List<DeclaredToken<?>> bounds) {
        Bounded {
            bounds = List.copyOf(bounds);
        }
    }

    /// What the proof says.
    sealed interface Verdict {
        /// The type is a subtype.
        record Proven() implements Verdict {}

        /// The type is not known to be a subtype.
        ///
        /// @param why what the proof lacks, for the message of the refusal
        record Unproven(String why) implements Verdict {}
    }
}
