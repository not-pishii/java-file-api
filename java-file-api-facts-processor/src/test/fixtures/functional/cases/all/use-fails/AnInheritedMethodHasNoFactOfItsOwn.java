import gen.facts.p.Sub2_;
import gen.facts.p.Sub_;

/// Only the declared members of a type are facts (Q6(b)): `apply` is reached through `sam`.
class AnInheritedMethodHasNoFactOfItsOwn {
    Object apply = Sub_.apply_String; // error: cannot find symbol
    Object applyOfTheLonger = Sub2_.apply_String; // error: cannot find symbol
}
