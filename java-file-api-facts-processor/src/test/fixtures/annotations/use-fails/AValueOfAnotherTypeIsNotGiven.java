import gen.facts.p.Author_;
import me.supcheg.javafile.facts.AnnotationValues;

/// An element takes a value of its type: the builder of `name` is no builder
/// of an `int`.
class AValueOfAnotherTypeIsNotGiven {
    Object wrong = Author_.annotation().name(AnnotationValues.int_(1)); // error: incompatible types
}
