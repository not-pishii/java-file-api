package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TryStmtTest {

    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");
    private static final ClassDesc SQL_EXCEPTION = ClassDesc.of("java.sql", "SQLException");

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
    void withFinallyDefensivelyCopiesResourcesAndCatches() {
        var mutableResources = new java.util.ArrayList<Resource>();
        mutableResources.add(new Resource.Existing("r"));
        var mutableCatches = new java.util.ArrayList<CatchClause>();
        mutableCatches.add(new CatchClause(
                NonEmptyList.copyOf(List.<ClassOrInterfaceTypeRef>of(Types.of(IO_EXCEPTION))), "e", CodeBody.EMPTY));

        TryStmt.WithFinally stmt =
                new TryStmt.WithFinally(mutableResources, CodeBody.EMPTY, mutableCatches, CodeBody.EMPTY);
        mutableResources.clear();
        mutableCatches.clear();

        assertThat(stmt.resources()).hasSize(1);
        assertThat(stmt.catches()).hasSize(1);
    }

    @Test
    void catchOnlyHoldsAtLeastOneCatchClauseByConstruction() {
        NonEmptyList<CatchClause> catches = NonEmptyList.copyOf(List.of(new CatchClause(
                NonEmptyList.copyOf(List.<ClassOrInterfaceTypeRef>of(Types.of(IO_EXCEPTION), Types.of(SQL_EXCEPTION))),
                "e",
                CodeBody.EMPTY)));

        TryStmt.CatchOnly stmt = new TryStmt.CatchOnly(List.of(), CodeBody.EMPTY, catches);

        assertThat(stmt.catches().toList()).hasSize(1);
        assertThat(stmt.catches().head().exceptionTypes().toList()).hasSize(2);
    }
}
