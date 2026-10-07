import gen.facts.java.lang.String_;
import gen.facts.p.ProtAbs_;
import gen.facts.p.ProtAbs_Heritage;
import gen.facts.p.ProtFinal_Heritage; // error: cannot find symbol
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.Implementation1;
import me.supcheg.javafile.facts.Steps;
import p.ProtAbs;

/// The proof that a class extends a type is given by the last step of its entry and by nothing else: a step
/// left out, a proof or an implementation made by hand, a stage entered in the middle do not compile, and a
/// type that cannot be extended has no entry at all.
class TheProofIsMadeByItsStepsAlone {
    Complete<String, ProtAbs> withoutItsStep = ProtAbs_.extend(String_.TOKEN); // error: incompatible types
    Complete<String, ProtAbs> byHand = new Complete<>(ProtAbs_.TOKEN, String_.TOKEN, java.util.List.of()); // error: is not public in me.supcheg.javafile.facts.Complete
    Implementation1<String, String, String> implementation = new Implementation1<>(null); // error: is not public in me.supcheg.javafile.facts.Implementation1
    Object stage = new ProtAbs_Heritage.Extend<String>(null); // error: has private access
    Steps<String, ProtAbs> steps = new Steps<>(ProtAbs_.TOKEN, String_.TOKEN, java.util.List.of()); // error: is not public in me.supcheg.javafile.facts.Steps
}
