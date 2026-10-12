package me.supcheg.javafile.render;

import me.supcheg.javafile.model.TypeDecl;

import java.lang.constant.ClassDesc;

interface TypeContext {
    // The name of a type in code. It may claim the simple name for the type, which is then imported.
    String reference(ClassDesc desc);

    // The name of a type where an expression may stand as well: before `.member` and `::method`. There
    // a simple name means a variable in scope before it means a type, and the first name of a
    // qualified one a variable before a package (JLS 6.4.2, 6.5.2).
    String qualifier(ClassDesc desc);

    // The name of a type in a documentation comment: the simple name if that means the type in
    // the file, the qualified name otherwise. It claims nothing, so a comment adds no import and
    // changes no name in the code.
    String mention(ClassDesc desc);

    // A variable the code declares: a field, a parameter, a local variable, a binding.
    void variable(String name);

    // A class or interface declared in a body: its name and its type parameters are of the file.
    void localType(TypeDecl decl);
}
