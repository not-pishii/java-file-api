package me.supcheg.javafile.facts.processor;

import javax.lang.model.SourceVersion;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/// The options of the processor, `-A<name>=<value>` on the javac command
/// line (mini-spec §2.1, §2.3):
///
/// - [#PACKAGE] — the base of the mirror packages, `<base>.p.q.T_` for
///   `p.q.T`; without it, see [BasePackage];
/// - [#STRICT] — `true` makes a member skipped for want of a fact an error
///   instead of a warning (Q10), in the types `@Facts` asks for: in a
///   supertype of one, which gets its metamodel without being asked for, it
///   stays a warning (Q13).
///
/// @param basePackage the base of the mirror packages, if given
/// @param strict whether a member skipped in a type `@Facts` asks for is an error
record Options(Optional<String> basePackage, boolean strict) {

    /// The option naming the base of the mirror packages.
    static final String PACKAGE = "javafile.facts.package";

    /// The option turning the members skipped in the types `@Facts` asks for into errors.
    static final String STRICT = "javafile.facts.strict";

    /// Reads the options javac passes to the processor.
    ///
    /// @param options the processor options
    /// @return the options, or what is wrong with them
    static Parsed parse(Map<String, String> options) {
        Optional<String> basePackage = Optional.ofNullable(options.get(PACKAGE));
        String strictValue = options.getOrDefault(STRICT, "false");
        List<String> errors = Stream.of(
                        basePackage
                                .filter(name -> !SourceVersion.isName(name))
                                .map(name -> "-A" + PACKAGE + "=" + name + " is not a package name"),
                        Optional.of(strictValue)
                                .filter(value -> !value.equals("true") && !value.equals("false"))
                                .map(value -> "-A" + STRICT + "=" + value + " is neither true nor false"))
                .flatMap(Optional::stream)
                .toList();
        return errors.isEmpty()
                ? new Parsed.Valid(new Options(basePackage, strictValue.equals("true")))
                : new Parsed.Invalid(errors);
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
