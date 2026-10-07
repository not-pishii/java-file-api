import gen.facts.java.lang.String_;
import gen.facts.p.ProtAbs_;
import gen.facts.p.ProtFinal_;
import gen.facts.p.ProtG_;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import p.ProtAbs;
import p.ProtG;

/// A fact held back is not the fact it holds, and gives it to no code outside of `facts`; the `protected`
/// members of a class that cannot be extended have no facts at all.
class NothingButASubclassOpensAProtectedFact {
    MethodRef1<ProtAbs, String, String> hook = ProtAbs_.hook_String; // error: incompatible types
    VoidStaticMethodRef0 reset = ProtG_.reset; // error: incompatible types
    CtorRef1<ProtG<String>, String> constructor = new ProtG_<>(String_.TOKEN).super_T; // error: incompatible types
    Object taken = ProtAbs_.hook_String.fact(); // error: fact() is not public in me.supcheg.javafile.facts.Protected
    Object ofAFinalClass = ProtFinal_.field; // error: cannot find symbol
    Object methodOfAFinalClass = ProtFinal_.method; // error: cannot find symbol
    Object constructorOfAFinalClass = ProtFinal_.new_int; // error: cannot find symbol
    Object noSuperOfAFinalClass = ProtFinal_.super_int; // error: cannot find symbol
}
