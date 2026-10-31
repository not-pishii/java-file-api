package me.supcheg.javafile.typed.fixtures;

/// Declares the variable-arity method of [VarargsBase] as the array, `String[]`, which javac warns of
/// (`[overrides]`) and accepts: the fact of the supertype is of variable arity, and a call of it on a value
/// of this class must write the array of the arguments.
public class VarargsArray extends VarargsBase<String> {
    @Override
    @SuppressWarnings("overrides")
    public String join(String[] parts) {
        return "array:" + String.join("+", parts);
    }
}
