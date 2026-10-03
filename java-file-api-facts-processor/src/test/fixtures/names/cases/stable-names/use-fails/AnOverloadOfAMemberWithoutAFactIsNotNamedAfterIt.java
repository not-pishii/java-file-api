import gen.facts.p.Ov_;

/// `m(java.util.List<Hidden>)` has no fact, and the fact of the other `m` does not take its short name `m_List`.
class AnOverloadOfAMemberWithoutAFactIsNotNamedAfterIt {
    Object shortName = Ov_.m_List; // error: cannot find symbol
}
