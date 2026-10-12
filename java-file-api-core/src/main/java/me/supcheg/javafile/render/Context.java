package me.supcheg.javafile.render;

import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.model.TypeDecl;
import me.supcheg.javafile.render.SourceRenderer.Format;

import java.lang.constant.ClassDesc;

interface Context extends Format, TypeContext {
    @Override
    Context withIncreasedPad();

    @Override
    Context withoutPad();

    static Context of(Format format, TypeContext type) {
        record Impl(Format format, TypeContext type) implements Context {
            @Override
            public String pad() {
                return format.pad();
            }

            @Override
            public String newline() {
                return format.newline();
            }

            @Override
            public Context withIncreasedPad() {
                return new Impl(format.withIncreasedPad(), type);
            }

            @Override
            public Context withoutPad() {
                return new Impl(format.withoutPad(), type);
            }

            @Override
            public DocStyle docStyle() {
                return format.docStyle();
            }

            @Override
            public String reference(ClassDesc desc) {
                return type.reference(desc);
            }

            @Override
            public String qualifier(ClassDesc desc) {
                return type.qualifier(desc);
            }

            @Override
            public String mention(ClassDesc desc) {
                return type.mention(desc);
            }

            @Override
            public void variable(String name) {
                type.variable(name);
            }

            @Override
            public void localType(TypeDecl decl) {
                type.localType(decl);
            }
        }

        return new Impl(format, type);
    }
}
