/// The SPI of runtime fact sources (§3.8): a
/// [me.supcheg.javafile.facts.source.MemberResolver] proves members by full
/// signature, described by a [me.supcheg.javafile.facts.source.MemberQuery],
/// or fails fast with a [me.supcheg.javafile.facts.FactLookupException].
/// [me.supcheg.javafile.facts.UnsafeFacts#factSource(me.supcheg.javafile.facts.DeclaredToken, MemberResolver)]
/// turns a resolver into a [me.supcheg.javafile.facts.FactSource].
@NullMarked
package me.supcheg.javafile.facts.source;

import org.jspecify.annotations.NullMarked;
