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
///   the metamodels are listed ([ReuseIndex]);
/// - [#VISIBILITY] — `package` makes the generated metamodels package-private,
///   so that they are no public API of the module that generates them: only
///   the code of their package reaches them. The default is `public`, the
///   metamodels of a library of them, which other code names;
/// - [#MIRROR] — `false` writes every metamodel into the base package itself,
///   `<base>.T_`, instead of the mirror package of its type,
///   `<base>.p.q.T_`. A module whose own code is to use package-private
///   metamodels says so: a package-private class is of one package alone.
///   The default is `true`.
///
/// @param basePackage the base of the mirror packages, if given
/// @param strict whether a member skipped in a type `@Facts` asks for is an error
/// @param index whether the metamodels are listed for the processors of other modules
/// @param visibility whether the generated metamodels are `public` or package-private
/// @param mirror whether the package of a type is mirrored under the base
record Options(Optional<String> basePackage, boolean strict, Index index, Visibility visibility, boolean mirror) {

    /// Whether the metamodels are listed in the index resources other modules find them by.
    enum Index {
        /// Every metamodel is listed.
        PUBLISHED,
        /// No metamodel is listed: they are for this module alone.
        UNPUBLISHED
    }

    /// Who may name a generated metamodel class.
    enum Visibility {
        /// Every metamodel is `public`, as a metamodel another module names is.
        PUBLIC,
        /// A metamodel is package-private: only the code of its package reaches it.
        PACKAGE
    }

    /// The option naming the base of the mirror packages.
    static final String PACKAGE = "javafile.facts.package";

    /// The option turning the members skipped in the types `@Facts` asks for into errors.
    static final String STRICT = "javafile.facts.strict";

    /// The option that turns the index of the metamodels off.
    static final String INDEX = "javafile.facts.index";

    /// The option that makes the generated metamodels package-private.
    static final String VISIBILITY = "javafile.facts.visibility";

    /// The option that turns the mirror packages off.
    static final String MIRROR = "javafile.facts.mirror";

    /// Reads the options javac passes to the processor.
    ///
    /// @param options the processor options
    /// @return the options, or what is wrong with them
    static Parsed parse(Map<String, String> options) {
        Optional<String> basePackage = Optional.ofNullable(options.get(PACKAGE));
        String strictValue = options.getOrDefault(STRICT, "false");
        String indexValue = options.getOrDefault(INDEX, "true");
        String visibilityValue = options.getOrDefault(VISIBILITY, "public");
        String mirrorValue = options.getOrDefault(MIRROR, "true");
        List<String> errors = Stream.of(
                        basePackage
                                .filter(name -> !SourceVersion.isName(name))
                                .map(name -> "-A" + PACKAGE + "=" + name + " is not a package name"),
                        Optional.of(strictValue)
                                .filter(value -> !value.equals("true") && !value.equals("false"))
                                .map(value -> "-A" + STRICT + "=" + value + " is neither true nor false"),
                        Optional.of(indexValue)
                                .filter(value -> !value.equals("true") && !value.equals("false"))
                                .map(value -> "-A" + INDEX + "=" + value + " is neither true nor false"),
                        Optional.of(visibilityValue)
                                .filter(value -> !value.equals("public") && !value.equals("package"))
                                .map(value -> "-A" + VISIBILITY + "=" + value + " is neither public nor package"),
                        Optional.of(mirrorValue)
                                .filter(value -> !value.equals("true") && !value.equals("false"))
                                .map(value -> "-A" + MIRROR + "=" + value + " is neither true nor false"))
                .flatMap(Optional::stream)
                .toList();
        return errors.isEmpty()
                ? new Parsed.Valid(new Options(
                        basePackage,
                        strictValue.equals("true"),
                        indexValue.equals("true") ? Index.PUBLISHED : Index.UNPUBLISHED,
                        visibilityValue.equals("package") ? Visibility.PACKAGE : Visibility.PUBLIC,
                        mirrorValue.equals("true")))
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
