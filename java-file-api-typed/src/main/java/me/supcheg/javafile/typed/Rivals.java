package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.TypeVarToken;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

/// Whether javac could apply another method of the name of a call (JLS 15.12.2) to arguments of the types the
/// call is rendered with: a method that cannot is no rival of the one the call names, whatever its arity.
///
/// The answer is conservative: `false` only where the facts disprove it. Subtyping implies the subtyping of the
/// erasures, a type argument a method infers is within the bounds of the erasure of its type parameter, and a
/// type that records no interfaces may be a subtype of any interface, so a method javac can apply is one
/// [#mayApply] says it may.
final class Rivals {
    private Rivals() {}

    private static final Set<ClassDesc> BOXES = Set.of(
            ConstantDescs.CD_Boolean,
            ConstantDescs.CD_Byte,
            ConstantDescs.CD_Character,
            ConstantDescs.CD_Short,
            ConstantDescs.CD_Integer,
            ConstantDescs.CD_Long,
            ConstantDescs.CD_Float,
            ConstantDescs.CD_Double);

    /// The primitive types each one is converted to by identity or by a widening primitive conversion
    /// (JLS 5.1.2), by descriptor.
    private static final Map<String, Set<String>> WIDENED = Map.of(
            "Z", Set.of("Z"),
            "B", Set.of("B", "S", "I", "J", "F", "D"),
            "S", Set.of("S", "I", "J", "F", "D"),
            "C", Set.of("C", "I", "J", "F", "D"),
            "I", Set.of("I", "J", "F", "D"),
            "J", Set.of("J", "F", "D"),
            "F", Set.of("F", "D"),
            "D", Set.of("D"));

    private static final Set<ClassDesc> ARRAY_SUPERTYPES =
            Set.of(ConstantDescs.CD_Object, ClassDesc.of("java.lang.Cloneable"), ClassDesc.of("java.io.Serializable"));

    /// Whether javac could apply a method of the parameters `rival` to arguments of exactly `types`: in the
    /// phases of fixed arity, or as of variable arity.
    ///
    /// @param rival the erasures of the parameters of the method, under the type arguments of the type searched
    /// @param types the types of the arguments
    /// @return `false` if the facts prove the method cannot be applied
    static boolean mayApply(List<ClassDesc> rival, List<TypeToken<?>> types) {
        return mayApplyFixed(rival, types) || mayApplyVariable(rival, types);
    }

    private static boolean mayApplyFixed(List<ClassDesc> rival, List<TypeToken<?>> types) {
        return rival.size() == types.size()
                && IntStream.range(0, types.size()).allMatch(i -> mayConvert(types.get(i), rival.get(i)));
    }

    private static boolean mayApplyVariable(List<ClassDesc> rival, List<TypeToken<?>> types) {
        if (rival.isEmpty() || !rival.getLast().isArray() || rival.size() - 1 > types.size()) {
            return false;
        }
        int leading = rival.size() - 1;
        // the last rival is an array, and only an array has a component type
        ClassDesc element = Objects.requireNonNull(rival.getLast().componentType());
        return IntStream.range(0, types.size())
                .allMatch(i -> mayConvert(types.get(i), i < leading ? rival.get(i) : element));
    }

    /// Whether a value of the type `arg` may be converted to the parameter type `param` by method invocation
    /// conversion (JLS 5.3): identity, widening, boxing, unboxing.
    ///
    /// @param arg the type of the argument
    /// @param param the erasure of the parameter
    /// @return `false` if the facts prove it cannot
    static boolean mayConvert(TypeToken<?> arg, ClassDesc param) {
        return switch (arg) {
            case me.supcheg.javafile.facts.AnnotatedRefToken<?> annotated -> mayConvert(annotated.token(), param);
            // boxing, then widening of the reference: any reference but an array
            case PrimitiveToken<?, ?, ?> primitive ->
                param.isPrimitive()
                        ? WIDENED.getOrDefault(primitive.erasure().descriptorString(), Set.of())
                                .contains(param.descriptorString())
                        : !param.isArray();
            case ArrayToken<?, ?> array ->
                param.isArray()
                        ? (array.component() instanceof PrimitiveToken<?, ?, ?>
                                ? array.component().erasure().equals(Objects.requireNonNull(param.componentType()))
                                : mayConvert(array.component(), Objects.requireNonNull(param.componentType())))
                        : ARRAY_SUPERTYPES.contains(param);
            case TypeVarToken<?> _ -> !param.isArray();
            case DeclaredToken<?> declared ->
                !param.isArray()
                        && (param.isPrimitive()
                                ? BOXES.contains(declared.erasure())
                                : param.equals(ConstantDescs.CD_Object)
                                        || param.equals(declared.erasure())
                                        || declared.shape().superclasses().contains(param)
                                        || declared.shape().interfaces().contains(param)
                                        // none recorded: the list proves a subtype and never that there is none
                                        || declared.shape().interfaces().isEmpty());
        };
    }
}
