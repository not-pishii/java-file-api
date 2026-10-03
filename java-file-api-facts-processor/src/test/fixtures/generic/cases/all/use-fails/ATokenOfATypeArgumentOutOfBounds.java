import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.Both_;
import gen.facts.p.Box_;
import gen.facts.p.Self_;
import gen.facts.p.Sorted_;

/// The metamodel of a type with bounded type parameters takes no token of
/// a type out of the bounds. The same code with type arguments within
/// them is `Bounds.aTokenOfATypeArgumentWithinBoundsIsTaken` of `use/`.
class ATokenOfATypeArgumentOutOfBounds {
    // Object is no Comparable<Object>
    Object explicit = new Sorted_<Object>(Object_.TOKEN); // error: type argument java.lang.Object is not within bounds of type-variable T
    Object inferred = new Sorted_<>(Object_.TOKEN); // error: has incompatible bounds
    // String is a Comparable<String>, but no Number
    Object both = new Both_<>(String_.TOKEN); // error: has incompatible bounds
    // Box<String> is no Self<Box<String>>
    Object self = new Self_<>(new Box_<>(String_.TOKEN).token); // error: has incompatible bounds
}
