import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.PBox_;
import gen.facts.p.Poly_;
import gen.facts.p.Witness_;

/// The witnesses are under the bounds the generic method declares: a token of a type out of them does not compile.
class AWitnessOutOfBoundsDoesNotCompile {
    // T extends Comparable<? super T>, and Object is not
    Object inferred = Poly_.max_Collection(Object_.TOKEN); // error: has incompatible bounds
    Object explicit = Poly_.<Object>max_Collection(Object_.TOKEN); // error: explicit type argument java.lang.Object does not conform to declared bound(s)
    // T extends Number & Comparable<T>: Comparable<String>, but not a Number
    Object notANumber = Poly_.clamp_T(String_.TOKEN); // error: has incompatible bounds
    // B extends A
    Object notASubtype = Poly_.widen_B(String_.TOKEN, Witness_.TOKEN); // error: has incompatible bounds
    // U extends T, the type argument of the instance
    Object notOfTheTypeArgument = new PBox_<>(String_.TOKEN).put_U(Witness_.TOKEN); // error: has incompatible bounds
    // a witness is a token: a value of the type is none
    Object notAToken = Poly_.id_T("text"); // error: java.lang.String cannot be converted to me.supcheg.javafile.facts.RefToken<T>
}
