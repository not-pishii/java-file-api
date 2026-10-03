import gen.facts.p.Greeter_;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim;
import p.Greeter;

/// The fact of a `final` field is not of a mutable family: the typed
/// layer has nothing to assign to it with.
class AFinalFieldIsNotMutable {
    MutableFieldRef<Greeter, Prim.Int> count = Greeter_.count; // error: incompatible types
    MutableStaticFieldRef<Prim.Int> constant = Greeter_.INT; // error: incompatible types
}
