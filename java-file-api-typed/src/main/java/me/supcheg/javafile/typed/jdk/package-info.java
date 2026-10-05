/// The JDK types the typed layer itself needs, as metamodels the `@Facts` processor generates (Q12): there is no
/// library of them, every module that wants a JDK type asks for it under its own JDK.
///
/// The metamodels are in `me.supcheg.javafile.typed.jdk.facts` (`-Ajavafile.facts.package`) and are not listed in
/// the index of reusable metamodels (`-Ajavafile.facts.index=false`): another module's processor generates its own.
/// Ask here for the type a construct of the typed layer needs, no more.
@Facts({String.class, Math.class})
package me.supcheg.javafile.typed.jdk;

import me.supcheg.javafile.facts.meta.Facts;
