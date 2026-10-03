import gen.facts.p.Pub_;

/// `Far<T> self()` mentions `Far`, which is not `public`: no fact, and a
/// warning — see `expected/diagnostics.txt`.
class AMemberThatMentionsAHiddenTypeHasNoFact {
    Object self = Pub_.self; // error: cannot find symbol
}
