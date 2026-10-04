package me.supcheg.javafile.render;

import java.lang.constant.ClassDesc;

interface TypeContext {
    // The name of a type in code. It may claim the simple name for the type, which is then imported.
    String reference(ClassDesc desc);

    // The name of a type in a documentation comment: the simple name if that means the type in
    // the file, the qualified name otherwise. It claims nothing, so a comment adds no import and
    // changes no name in the code.
    String mention(ClassDesc desc);
}
