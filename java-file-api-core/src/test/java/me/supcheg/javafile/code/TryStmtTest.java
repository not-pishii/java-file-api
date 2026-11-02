package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TryStmtTest {

    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");

    @Test
    void withFinallyAcceptsEmptyCatchesForPlainTryFinally() {
        CodeBody block = CodeBody.EMPTY;
        CodeBody finallyBody = CodeBody.EMPTY;

        TryStmt.WithFinally stmt = new TryStmt.WithFinally(List.of(), block, List.of(), finallyBody);

        assertThat(stmt.catches()).isEmpty();
        assertThat(stmt.finallyBody()).isEqualTo(finallyBody);
        assertThat((Stmt) stmt).isInstanceOf(TryStmt.class);
    }

    @Test
    void resourcesOnlyHasAtLeastOneResource() {
        assertThatThrownBy(() -> new TryStmt.ResourcesOnly(List.of(), CodeBody.EMPTY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least one resource");
    }

    @Test
    void withFinallyDefensivelyCopiesResourcesAndCatches() {
        var mutableResources = new java.util.ArrayList<Resource>();
        mutableResources.add(new Resource.Existing("r"));
        var mutableCatches = new java.util.ArrayList<CatchClause>();
        CatchClause caught = new CatchClause(
                NonEmptyList.copyOf(List.<ClassOrInterfaceTypeRef>of(Types.of(IO_EXCEPTION))), "e", CodeBody.EMPTY);
        mutableCatches.add(caught);

        TryStmt.WithFinally stmt =
                new TryStmt.WithFinally(mutableResources, CodeBody.EMPTY, mutableCatches, CodeBody.EMPTY);
        mutableResources.clear();
        mutableCatches.clear();

        assertThat(stmt.resources()).containsExactly(new Resource.Existing("r"));
        assertThat(stmt.catches()).containsExactly(caught);
    }
}
