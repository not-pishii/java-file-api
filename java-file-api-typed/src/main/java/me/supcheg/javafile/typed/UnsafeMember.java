package me.supcheg.javafile.typed;

import me.supcheg.javafile.model.ClassMember;

/// A single untyped class member, produced by [Unsafe#member(ClassMember)]
/// and accepted by [TypedClassBuilder#add(UnsafeMember)]. There is no public
/// constructor: the only way to obtain one is through `Unsafe`.
public final class UnsafeMember {
    private final ClassMember member;

    UnsafeMember(ClassMember member) {
        this.member = member;
    }

    ClassMember member() {
        return member;
    }
}
