package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/// The member signatures of a class being declared (§6.5, §9.7): rejects,
/// when the member is declared, what javac would reject in the rendered
/// class —
///
/// - two methods with the same erased signature (JLS 8.4.2), whatever their
///   return types and whether they are `static`;
/// - two constructors with the same erased parameter types (JLS 8.8.2);
/// - two fields with the same name (JLS 8.3);
/// - an instance method that overrides a `final` method of `Object`
///   (`getClass`, `notify`, `notifyAll`, `wait`; JLS 8.4.3.3);
/// - an instance method that overrides a method of `Object` with an
///   incompatible return type, e.g. `int toString()` (JLS 8.4.8.3);
/// - an instance method that overrides a method of `Object` and declares a
///   checked exception the overridden method does not, e.g. `String
///   toString() throws IOException` (JLS 8.4.8.3);
/// - a `static` method with the signature of an instance method of `Object`,
///   which it cannot hide (JLS 8.4.8.2).
///
/// The only supertype of a class declared today is `Object`; the methods of
/// other supertypes join the same check once `extends`/`implements` exist.
final class SignatureRegistry {
    private static final List<Inherited> OBJECT_METHODS = List.of(
            Inherited.final_("getClass"),
            Inherited.final_("notify"),
            Inherited.final_("notifyAll"),
            Inherited.final_("wait"),
            Inherited.final_("wait", ConstantDescs.CD_long),
            Inherited.final_("wait", ConstantDescs.CD_long, ConstantDescs.CD_int),
            Inherited.overridable("boolean", is(PrimitiveToken.BOOLEAN), "equals", ConstantDescs.CD_Object),
            Inherited.overridable("int", is(PrimitiveToken.INT), "hashCode"),
            Inherited.overridable(
                    "String",
                    r -> r.map(t -> t.erasure().equals(ConstantDescs.CD_String)).orElse(false),
                    "toString"),
            Inherited.overridable(
                            "a reference type",
                            r -> r.map(t -> !(t instanceof PrimitiveToken<?, ?, ?>))
                                    .orElse(false),
                            "clone")
                    .throwing(ClassDesc.of("java.lang.CloneNotSupportedException")),
            Inherited.overridable("void", Optional::isEmpty, "finalize").throwing(ConstantDescs.CD_Throwable));

    private final String owner;
    private final Map<MethodSignature, Invocable> methods = new LinkedHashMap<>();
    private final Map<List<ClassDesc>, Invocable> constructors = new HashMap<>();
    private final Set<String> fields = new HashSet<>();

    /// @param owner the class being declared, for messages
    SignatureRegistry(String owner) {
        this.owner = owner;
    }

    /// Registers a field.
    ///
    /// @param name the field name
    /// @throws IllegalArgumentException if a field of that name is declared already
    void field(String name) {
        if (!fields.add(name)) {
            throw new IllegalArgumentException("field " + name + " of " + owner
                    + " is already declared: field names are unique in a class" + " (JLS 8.3)");
        }
    }

    /// Registers a method or constructor.
    ///
    /// @param member the member being declared
    /// @return whether it overrides a method of a supertype, so that it is
    ///     rendered with `@Override`
    /// @throws IllegalArgumentException if javac would reject the member
    boolean member(Invocable member) {
        if (member.kind() == InvocableKind.CONSTRUCTOR) {
            List<ClassDesc> params = member.signature().params();
            Invocable clash = constructors.putIfAbsent(params, member);
            if (clash != null) {
                throw new IllegalArgumentException("the constructor " + member + " of " + owner
                        + " clashes with the constructor " + clash + " declared before: both erase to the"
                        + " parameters " + member.signature() + " (JLS 8.8.2)");
            }
            return false;
        }
        MethodSignature signature = member.signature();
        Invocable clash = methods.get(signature);
        if (clash != null) {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " clashes with the method "
                    + clash + " declared before: both erase to " + signature + " (JLS 8.4.2)");
        }
        boolean overrides = overridesObjectMethod(member, signature);
        methods.put(signature, member);
        return overrides;
    }

    private boolean overridesObjectMethod(Invocable member, MethodSignature signature) {
        List<Inherited> overridden = OBJECT_METHODS.stream()
                .filter(inherited -> inherited.signature().equals(signature))
                .toList();
        overridden.forEach(inherited -> requireOverride(member, signature, inherited));
        return !overridden.isEmpty();
    }

    /// Rejects `member` as an override of `inherited`, whose signature it has.
    private void requireOverride(Invocable member, MethodSignature signature, Inherited inherited) {
        if (member.kind() == InvocableKind.STATIC_METHOD) {
            throw new IllegalArgumentException("the static method " + member + " of " + owner
                    + " would hide the instance method " + signature + " of java.lang.Object, which a static"
                    + " method cannot (JLS 8.4.8.2)");
        }
        if (inherited.isFinal()) {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " would override the"
                    + " final method " + signature + " of java.lang.Object (JLS 8.4.3.3)");
        }
        if (!inherited.returns().test(member.resultType())) {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " overrides "
                    + signature + " of java.lang.Object, whose result is " + inherited.describedReturn()
                    + ": the return types are incompatible (JLS 8.4.8.3)");
        }
        List<ExceptionType> undeclared = ExceptionType.ofAll(member.traits().throwsTypes()).stream()
                .filter(ExceptionType::isChecked)
                .filter(thrown -> !inherited.declares(thrown))
                .toList();
        if (!undeclared.isEmpty()) {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " overrides "
                    + signature + " of java.lang.Object, which declares "
                    + (inherited.throwsTypes().isEmpty()
                            ? "no exception"
                            : inherited.throwsTypes().stream()
                                    .map(ClassDesc::displayName)
                                    .collect(Collectors.joining(", ")))
                    + ": an override cannot declare the checked exception "
                    + undeclared.stream().map(Object::toString).collect(Collectors.joining(", "))
                    + " (JLS 8.4.8.3)");
        }
    }

    /// The methods of the class: the instance methods declared and those
    /// inherited from `Object`, and the `static` ones. The class is final, so
    /// no method is abstract.
    MethodTable table() {
        Set<MethodSignature> concrete = new LinkedHashSet<>();
        Set<MethodSignature> statics = new LinkedHashSet<>();
        for (Map.Entry<MethodSignature, Invocable> e : methods.entrySet()) {
            if (e.getValue().kind() == InvocableKind.INSTANCE_METHOD) {
                concrete.add(e.getKey());
            } else {
                statics.add(e.getKey());
            }
        }
        for (Inherited inherited : OBJECT_METHODS) {
            concrete.add(inherited.signature());
        }
        return new MethodTable(Set.of(), concrete, statics);
    }

    private static Predicate<Optional<TypeToken<?>>> is(PrimitiveToken<?, ?, ?> type) {
        return r -> r.map(type::equals).orElse(false);
    }

    /// An instance method of `Object`.
    ///
    /// @param signature its signature
    /// @param isFinal whether it is `final`
    /// @param describedReturn the return type an override needs, for messages
    /// @param returns whether an override's result type is compatible
    /// @param throwsTypes the classes of its `throws` clause: an override declares no checked exception
    ///     that is not one of them or a subclass of one
    private record Inherited(
            MethodSignature signature,
            boolean isFinal,
            String describedReturn,
            Predicate<Optional<TypeToken<?>>> returns,
            List<ClassDesc> throwsTypes) {

        static Inherited final_(String name, ClassDesc... params) {
            return new Inherited(new MethodSignature(name, List.of(params)), true, "", _ -> false, List.of());
        }

        static Inherited overridable(
                String describedReturn, Predicate<Optional<TypeToken<?>>> returns, String name, ClassDesc... params) {
            return new Inherited(
                    new MethodSignature(name, List.of(params)), false, describedReturn, returns, List.of());
        }

        Inherited throwing(ClassDesc... throwsTypes) {
            return new Inherited(signature, isFinal, describedReturn, returns, List.of(throwsTypes));
        }

        /// Whether the `throws` clause covers `thrown`: it is a class of the clause or a subclass of
        /// one. A type variable is known to be neither.
        boolean declares(ExceptionType thrown) {
            return switch (thrown) {
                case ExceptionType.OfClass(var cls) -> throwsTypes.stream().anyMatch(cls::isSubclassOf);
                case ExceptionType.OfVariable _ -> false;
            };
        }
    }
}
