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
///   stays a warning (Q13);
/// - [#INDEX] — `false` keeps the metamodels to the module that generates them
///   (Q12): they are generated as usual, but none is listed in the resources
///   `META-INF/javafile/metamodel/**`, so the processor of no other module
///   reuses them. For a module that needs metamodels of its own and ships
///   no library of them, `java-file-api-typed` for one. The default is `true`:
///   the metamodels are listed ([ReuseIndex]).
///
/// @param basePackage the base of the mirror packages, if given
/// @param strict whether a member skipped in a type `@Facts` asks for is an error
/// @param index whether the metamodels are listed for the processors of other modules
record Options(Optional<String> basePackage, boolean strict, Index index) {

    /// Whether the metamodels are listed in the index resources other modules find them by.
    enum Index {
        /// Every metamodel is listed.
        PUBLISHED,
        /// No metamodel is listed: they are for this module alone.
        UNPUBLISHED
    }

    /// The option naming the base of the mirror packages.
    static final String PACKAGE = "javafile.facts.package";

    /// The option turning the members skipped in the types `@Facts` asks for into errors.
    static final String STRICT = "javafile.facts.strict";

    /// The option that turns the index of the metamodels off.
    static final String INDEX = "javafile.facts.index";

    /// Reads the options javac passes to the processor.
    ///
    /// @param options the processor options
    /// @return the options, or what is wrong with them
    static Parsed parse(Map<String, String> options) {
        Optional<String> basePackage = Optional.ofNullable(options.get(PACKAGE));
        String strictValue = options.getOrDefault(STRICT, "false");
        String indexValue = options.getOrDefault(INDEX, "true");
        List<String> errors = Stream.of(
                        basePackage
                                .filter(name -> !SourceVersion.isName(name))
                                .map(name -> "-A" + PACKAGE + "=" + name + " is not a package name"),
                        Optional.of(strictValue)
                                .filter(value -> !value.equals("true") && !value.equals("false"))
                                .map(value -> "-A" + STRICT + "=" + value + " is neither true nor false"),
                        Optional.of(indexValue)
                                .filter(value -> !value.equals("true") && !value.equals("false"))
                                .map(value -> "-A" + INDEX + "=" + value + " is neither true nor false"))
                .flatMap(Optional::stream)
                .toList();
        return errors.isEmpty()
                ? new Parsed.Valid(new Options(
                        basePackage,
                        strictValue.equals("true"),
                        indexValue.equals("true") ? Index.PUBLISHED : Index.UNPUBLISHED))
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
