import gen.facts.p.Vis_;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.VoidMethodRef0;
import p.Vis;

/// The fact of a `protected` member is no fact of a member anyone uses: it is held back in a `Protected`, which
/// does not give it away, and that of a `protected` constructor is no fact `new` takes.
class AProtectedFactIsHeldBack {
    MutableFieldRef<Vis, Prim.Int> field = Vis_.prot; // error: incompatible types
    VoidMethodRef0<Vis> method = Vis_.protM; // error: incompatible types
    CtorRef1<Vis, Prim.Int> constructor = Vis_.super_int; // error: incompatible types
    Object taken = Vis_.protM.fact(); // error: fact() is not public in me.supcheg.javafile.facts.Protected
}
