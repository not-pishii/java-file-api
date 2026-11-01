/// The JDK types the typed layer itself needs, as metamodels the `@Facts` processor generates (Q12): there is no
/// library of them, every module that wants a JDK type asks for it under its own JDK.
///
/// The metamodels are package-private, in the package of the typed layer itself (`-Ajavafile.facts.package` and
/// `-Ajavafile.facts.mirror=false`, `-Ajavafile.facts.visibility=package`), so that no user of the library names
/// them. They are not listed in the index of reusable metamodels (`-Ajavafile.facts.index=false`): another module's
/// processor generates its own. Ask here for the type a construct of the typed layer needs, no more.
@Facts({String.class, Math.class})
package me.supcheg.javafile.typed.jdk;

import me.supcheg.javafile.facts.meta.Facts;
