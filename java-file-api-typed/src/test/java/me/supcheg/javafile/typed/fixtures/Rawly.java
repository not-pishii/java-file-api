package me.supcheg.javafile.typed.fixtures;

import java.util.ArrayList;
import java.util.List;

/// A class whose members have raw types in their signatures, which code that writes them is warned of.
@SuppressWarnings("rawtypes")
public class Rawly {
    /// Makes one.
    public Rawly() {}

    /// A raw list.
    ///
    /// @return the list
    public static List raw() {
        return new ArrayList();
    }

    /// Takes a raw list.
    ///
    /// @param list the list
    /// @return its size
    public int take(List list) {
        return list.size();
    }

    /// A list of raw lists.
    ///
    /// @return the list
    public static List<? extends List> nested() {
        return new ArrayList<List>();
    }
}
