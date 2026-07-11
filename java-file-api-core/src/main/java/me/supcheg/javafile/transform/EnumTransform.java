package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.model.EnumMember;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface EnumTransform extends BiConsumer<EnumBuilder, EnumMember> {
    default EnumTransform andThen(EnumTransform next) {
        return (builder, member) -> {
            accept(builder, member);
            next.accept(builder, member);
        };
    }
}
