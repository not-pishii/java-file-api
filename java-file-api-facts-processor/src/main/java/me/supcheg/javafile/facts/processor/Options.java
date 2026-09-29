package me.supcheg.javafile.facts.processor;

import javax.lang.model.SourceVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/// The options of the processor, `-A<name>=<value>` on the javac command
/// line (mini-spec §2.1, §2.3):
///
/// - [#PACKAGE] — the base of the mirror packages, `<base>.p.q.T_` for
///   `p.q.T`; without it, see [BasePackage];
/// - [#STRICT] — `true` makes a member skipped for want of a fact an error
///   instead of a warning (Q10).
///
/// @param basePackage the base of the mirror packages, if given
/// @param strict whether a skipped member is an error
record Options(Optional<String> basePackage, boolean strict) {

    /// The option naming the base of the mirror packages.
    static final String PACKAGE = "javafile.facts.package";

    /// The option turning skipped members into errors.
    static final String STRICT = "javafile.facts.strict";

    /// Reads the options javac passes to the processor.
    ///
    /// @param options the processor options
    /// @return the options, or what is wrong with them
    static Parsed parse(Map<String, String> options) {
        List<String> errors = new ArrayList<>();
        Optional<String> basePackage = Optional.ofNullable(options.get(PACKAGE));
        basePackage
                .filter(name -> !SourceVersion.isName(name))
                .ifPresent(name -> errors.add("-A" + PACKAGE + "=" + name + " is not a package name"));
        String strictValue = options.getOrDefault(STRICT, "false");
        boolean strict = strictValue.equals("true");
        if (!strict && !strictValue.equals("false")) {
            errors.add("-A" + STRICT + "=" + strictValue + " is neither true nor false");
        }
        return errors.isEmpty() ? new Parsed.Valid(new Options(basePackage, strict)) : new Parsed.Invalid(errors);
    }

    /// The options, or why there are none.
    sealed interface Parsed {

        /// The options are valid.
        ///
        /// @param options the options
        record Valid(Options options) implements Parsed {}

        /// The options are not valid.
        ///
        /// @param errors what is wrong, one message per option
        record Invalid(List<String> errors) implements Parsed {}
    }
}
