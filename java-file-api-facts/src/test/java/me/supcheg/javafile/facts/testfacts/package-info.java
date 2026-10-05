/// The JDK types the tests of `facts` need, as metamodels the `@Facts` processor generates: the metamodels are in
/// `me.supcheg.javafile.facts.testfacts` (`-Ajavafile.facts.package`) and are not listed in the index
/// (`-Ajavafile.facts.index=false`).
@Facts({ArrayList.class, Function.class, Integer.class, List.class, String.class})
package me.supcheg.javafile.facts.testfacts;

import me.supcheg.javafile.facts.meta.Facts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
