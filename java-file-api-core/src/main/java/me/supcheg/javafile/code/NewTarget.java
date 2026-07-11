package me.supcheg.javafile.code;

/// The instantiated target of a [NewExpr]: an explicit type
/// ([TypedNewTarget]) or a raw generic class with the diamond operator
/// ([DiamondNewTarget]).
public sealed interface NewTarget permits TypedNewTarget, DiamondNewTarget {}
