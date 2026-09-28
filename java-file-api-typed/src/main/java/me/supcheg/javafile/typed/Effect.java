package me.supcheg.javafile.typed;

/// An expression that may stand as a statement: a method call, an instance
/// creation, or an assignment (JLS 14.8). Only effects can be passed to
/// [Block#exec(Effect)], so `1 + 2;` is not representable.
public sealed interface Effect permits Invocation, VoidInvocation, Assignment {}
