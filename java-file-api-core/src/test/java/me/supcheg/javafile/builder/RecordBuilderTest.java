package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RecordBuilderTest {

    @Test
    void buildsARecordWithComponentsAndAllMemberKinds() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));

        builder.withComponent("x", PrimitiveTypeRef.INT)
                .withComponent("y", PrimitiveTypeRef.INT)
                .withCompactConstructor(b -> b.exprStatement(b.call("requireValid")))
                .withMethod("magnitude", PrimitiveTypeRef.INT, mb -> mb.withBody(b -> b.return_(b.literal(0))))
                .withStaticField("ORIGIN", PrimitiveTypeRef.INT, new IntLiteral(0));

        RecordDecl decl = builder.build();

        assertThat(decl.components()).hasSize(2);
        assertThat(decl.members()).hasSize(3);
        assertThat(decl.members().get(0)).isInstanceOf(CompactConstructorDecl.class);
        assertThat(decl.members().get(1)).isInstanceOf(MethodDecl.class);
        assertThat(decl.members().get(2)).isInstanceOf(StaticFieldDecl.class);
    }

    @Test
    void typeParamIsCarriedOver() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Box"));
        me.supcheg.javafile.type.ClassOrInterfaceTypeRef bound = Types.of(ClassDesc.of("java.lang", "Comparable"));

        builder.withTypeParam("T", bound);

        RecordDecl decl = builder.build();
        assertThat(decl.typeParams()).hasSize(1);
        assertThat(decl.typeParams().get(0).name()).isEqualTo("T");
        assertThat(decl.typeParams().get(0).bounds()).containsExactly(bound);
    }

    @Test
    void interfaceIsAddedViaClassDescOrTypeRef() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));
        ClassDesc iface = ClassDesc.of("geom", "Shape");

        builder.withInterface(iface);

        assertThat(builder.build().interfaces()).containsExactly(Types.of(iface));
    }

    @Test
    void compactConstructorWithExplicitModifiersAndThrowsClause() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");

        builder.withCompactConstructor(
                Set.of(Modifier.PUBLIC), List.of(ioException), b -> b.exprStatement(b.call("requireValid")));

        RecordDecl decl = builder.build();
        CompactConstructorDecl ctor = (CompactConstructorDecl) decl.members().get(0);
        assertThat(ctor.modifiers()).containsExactly(Modifier.PUBLIC);
        assertThat(ctor.throwsTypes()).containsExactly(Types.of(ioException));
    }

    @Test
    void recordCanDeclareAVoidMethod() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));

        builder.withVoidMethod("reset", mb -> mb.withBody(b -> b.return_()));

        RecordDecl decl = builder.build();
        MethodDecl method = (MethodDecl) decl.members().get(0);
        assertThat(method.name()).isEqualTo("reset");
        assertThat(method.returnType()).isEmpty();
    }

    @Test
    void acceptAppendsAPreBuiltMember() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));
        StaticFieldDecl member = new StaticFieldDecl("ORIGIN", PrimitiveTypeRef.INT, new IntLiteral(0));

        builder.accept(member);

        assertThat(builder.build().members()).containsExactly(member);
    }
}
