package me.supcheg.javafile.langmodel.mirror;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import java.util.ArrayList;
import java.util.List;

/// The type variables a type mirror may mention, for
/// [MirrorTranslator#typeRef]: those of a type, or of a method or
/// constructor and the type that declares it. A type variable out of scope —
/// of an enclosing type of an inner class, say — is unrepresentable.
public final class VarScope {

    /// No type variables.
    public static final VarScope EMPTY = new VarScope(List.of());

    private final List<TypeParameterElement> variables;

    private VarScope(List<TypeParameterElement> variables) {
        this.variables = List.copyOf(variables);
    }

    /// The type parameters of a class or interface.
    ///
    /// @param type the class or interface
    /// @return the scope
    public static VarScope of(TypeElement type) {
        return new VarScope(List.copyOf(type.getTypeParameters()));
    }

    /// The type parameters of a method or constructor and of the class or
    /// interface that declares it.
    ///
    /// @param executable the method or constructor
    /// @return the scope
    public static VarScope of(ExecutableElement executable) {
        List<TypeParameterElement> variables = new ArrayList<>(executable.getTypeParameters());
        variables.addAll(((TypeElement) executable.getEnclosingElement()).getTypeParameters());
        return new VarScope(variables);
    }

    /// Whether a type variable is in scope.
    ///
    /// @param variable the element of the type variable
    /// @return `true` if it is declared by an element of this scope
    boolean declares(TypeParameterElement variable) {
        return variables.contains(variable);
    }
}
