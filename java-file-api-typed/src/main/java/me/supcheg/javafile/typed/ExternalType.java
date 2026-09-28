package me.supcheg.javafile.typed;

import org.jspecify.annotations.Nullable;

import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassModel;
import java.lang.classfile.FieldModel;
import java.lang.classfile.MethodModel;
import java.lang.constant.ClassDesc;
import java.lang.reflect.AccessFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

final class ExternalType extends TypeSym {
    private final Env env;
    private final ClassModel model;
    private final List<Decl> declared;
    private @Nullable List<TypeSym> supertypes;

    ExternalType(Env env, ClassDesc desc, ClassModel model) {
        super(desc);
        this.env = env;
        this.model = model;
        this.declared = declared(model);
    }

    @Override
    public boolean isInterface() {
        return model.flags().has(AccessFlag.INTERFACE);
    }

    @Override
    List<TypeSym> supertypes() {
        if (supertypes == null) {
            supertypes = Stream.concat(model.superclass().stream(), model.interfaces().stream())
                    .map(entry -> env.type(entry.asSymbol()))
                    .toList();
        }
        return supertypes;
    }

    @Override
    List<Decl> declared() {
        return declared;
    }

    private static List<Decl> declared(ClassModel model) {
        List<Decl> declared = new ArrayList<>();
        for (MethodModel method : model.methods()) {
            String name = method.methodName().stringValue();
            AccessFlags flags = method.flags();
            if (hidden(flags) || flags.has(AccessFlag.BRIDGE) || name.equals("<clinit>")) {
                continue;
            }
            Decl.Kind kind = name.equals("<init>") ? Decl.Kind.CTOR : Decl.Kind.METHOD;
            declared.add(new Decl(kind, name, method.methodType().stringValue(), flags.has(AccessFlag.STATIC)));
        }
        for (FieldModel field : model.fields()) {
            if (!hidden(field.flags())) {
                declared.add(new Decl(
                        Decl.Kind.FIELD,
                        field.fieldName().stringValue(),
                        field.fieldType().stringValue(),
                        field.flags().has(AccessFlag.STATIC)));
            }
        }
        return List.copyOf(declared);
    }

    private static boolean hidden(AccessFlags flags) {
        return flags.has(AccessFlag.PRIVATE) || flags.has(AccessFlag.SYNTHETIC);
    }
}
