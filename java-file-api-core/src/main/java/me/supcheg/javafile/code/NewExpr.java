package me.supcheg.javafile.code;

import me.supcheg.javafile.model.EnumConstantMember;

import java.util.List;
import java.util.Optional;

/// An object creation expression, `new type(args)` or `new type<>(args)`,
/// optionally with an anonymous class body, `new type(args) { ... }`.
///
/// @param target the instantiated target
/// @param args the constructor arguments, in order; copied defensively
/// @param anonymousBody the anonymous subclass's body members, or empty for a plain object creation;
///                       uses [EnumConstantMember]'s narrower member set, since an anonymous class
///                       cannot declare a constructor or an abstract method
public record NewExpr(NewTarget target, List<Expr> args, Optional<List<EnumConstantMember>> anonymousBody)
        implements Expr, StatementExpr {
    public NewExpr {
        args = List.copyOf(args);
        anonymousBody = anonymousBody.map(List::copyOf);
    }

    public NewExpr(NewTarget target, List<Expr> args) {
        this(target, args, Optional.empty());
    }
}
