package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.Map;

/// Facts of `java.util.ArrayList<E>`, instantiated with the token of `E`.
///
/// @param <E> the element type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class ArrayList_<E> {
    /// `ArrayList<E>`.
    public final OpenClassToken<ArrayList<E>> token;

    /// `new ArrayList<E>()`.
    public final CtorRef0<ArrayList<E>> new_;

    /// Instantiates the facts for an element type.
    ///
    /// @param element the element type
    public ArrayList_(RefToken<E> element) {
        this.token = Jdk.openClass(ArrayList.class, Map.of("E", element), Jdk.arg(element));
        this.new_ = UnsafeFacts.ctor(token, Jdk.FINAL);
    }
}
