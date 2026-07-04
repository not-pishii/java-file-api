package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

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
}
