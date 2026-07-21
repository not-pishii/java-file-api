package me.supcheg.javafile.code;

import me.supcheg.javafile.model.ClassDecl;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LocalTypeDeclStmtTest {

    @Test
    void wrapsATypeDeclAsAStatement() {
        ClassDecl decl = new ClassDecl(
                ClassDesc.of("Counter"),
                List.of(),
                Set.of(me.supcheg.javafile.model.Modifier.FINAL),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());
        LocalTypeDeclStmt stmt = new LocalTypeDeclStmt(decl);
        assertThat(stmt.decl()).isEqualTo(decl);
    }
}
