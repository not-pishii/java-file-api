import gen.facts.java.lang.Object_;
import gen.facts.p.ByHidden_;

/// `ByHidden_` cannot write the bound `T extends Hidden`: it declares no
/// type parameter and no constructor to take a token with.
class AMetamodelWithoutTypeParametersTakesNoToken {
    Object any = new ByHidden_<>(Object_.TOKEN); // error: cannot infer type arguments for gen.facts.p.ByHidden_
}
