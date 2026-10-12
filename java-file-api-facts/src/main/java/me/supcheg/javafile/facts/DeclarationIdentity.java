package me.supcheg.javafile.facts;

/// What one declaration of the typed layer is known by: a class being
/// declared makes one ([UnsafeFacts#declarationIdentity(String)]), and the
/// tokens of its type parameters carry it ([TypeVarToken.Origin.Declared]),
/// so that a type parameter is told from one of another declaration,
/// whatever the two are called. Two identities are equal only if they are
/// the same object.
public final class DeclarationIdentity {
    private final String described;

    DeclarationIdentity(String described) {
        this.described = described;
    }

    @Override
    public String toString() {
        return described;
    }
}
