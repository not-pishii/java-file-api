package me.supcheg.javafile.code;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LocalVarDeclStmtTest {

    @Test
    void typedLocalMayOmitInitializer() {
        LocalVarDeclStmt.Typed stmt = new LocalVarDeclStmt.Typed(PrimitiveTypeRef.INT, "x", Optional.empty());
        assertThat(stmt.initializer()).isEmpty();
    }

    @Test
    void inferredLocalAlwaysHasAnInitializer() {
        LocalVarDeclStmt.Inferred stmt = new LocalVarDeclStmt.Inferred("x", new IntLiteral(1));
        assertThat(stmt.initializer()).isEqualTo(new IntLiteral(1));
    }
}
