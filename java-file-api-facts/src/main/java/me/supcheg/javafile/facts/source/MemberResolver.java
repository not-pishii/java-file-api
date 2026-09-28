package me.supcheg.javafile.facts.source;

import me.supcheg.javafile.facts.FactLookupException;

/// The checking half of a runtime fact source (§3.8): proves that a type has
/// a member, by full signature, or fails fast.
///
/// A resolver alone introduces nothing. It becomes a
/// [me.supcheg.javafile.facts.FactSource] — the typed lookups that turn a
/// proven member into a fact — only through
/// [me.supcheg.javafile.facts.UnsafeFacts#factSource(me.supcheg.javafile.facts.DeclaredToken, MemberResolver)],
/// so a resolver that proves everything is a fact introduced by hand and
/// falls under the audit of the guarantee (§1).
@FunctionalInterface
public interface MemberResolver {

    /// Proves that the type has the member `query` asks for.
    ///
    /// @param query the member, by kind, name, and full signature
    /// @return what the resolver learned about the member
    /// @throws FactLookupException saying what was looked for, what similar
    ///     members exist, and where the type came from, if there is no such
    ///     member
    Resolution resolve(MemberQuery query);
}
