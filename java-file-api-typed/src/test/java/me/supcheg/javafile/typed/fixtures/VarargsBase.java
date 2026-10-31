package me.supcheg.javafile.typed.fixtures;

/// A class whose method is of variable arity, `T...`, and its subclass [VarargsArray], which declares the
/// same method as the array `T[]`: the two erase alike and are one method, and javac reads the arguments of
/// a call to them differently.
@SuppressWarnings("unchecked")
public class VarargsBase<T> {
    public String join(T... parts) {
        return "base:" + parts.length;
    }
}
