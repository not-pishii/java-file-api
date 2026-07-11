package me.supcheg.javafile.render;

import me.supcheg.javafile.render.SourceRenderer.Format;

import java.lang.constant.ClassDesc;

/// The full render-time context used by the renderer internals: a caller-supplied
/// [Format] plus the [TypeContext] the renderer itself owns for resolving
/// [ClassDesc]s. Only constructible with both parts present, so there is no
/// state in which `reference` is callable without a real type resolver behind it.
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
            public String reference(ClassDesc desc) {
                return type.reference(desc);
            }
        }

        return new Impl(format, type);
    }
}
