import gen.facts.p.Abs_;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.Prim;
import p.Abs;

/// `Abs(long)` is `protected`: its fact is one a subclass constructor takes, as that of every constructor of an
/// abstract class, and none `new` does.
class AProtectedConstructorIsForASubclass {
    CtorRef1<Abs, Prim.Long> none = Abs_.super_long; // error: incompatible types
}
