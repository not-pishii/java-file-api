package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.assignStaticField;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// `synchronized` (6.3): the statement renders exactly, compiles under `-Xlint:all`, and its monitor
/// makes two threads increment one counter without a lost update.
class SynchronizedCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Locked");

    @Test
    void twoThreadsIncrementAGuardedCounter() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var count = cb.declareMutableStaticField("count", PrimitiveToken.INT);
                        var race = cb.declareVoidStaticMethod("race", Object_.TOKEN, PrimitiveToken.INT);
                        var inTry = cb.declareVoidStaticMethod("inTry", Object_.TOKEN);
                        var read = cb.declareStaticMethod("read", PrimitiveToken.INT);
                        var bodies = cb.bodies();
                        bodies.initialize(count, literal(0));
                        bodies.define(
                                inTry,
                                (b, lock) -> b.try_(
                                                h -> h.finally_(
                                                        f -> f.exec(assignStaticField(count, staticField(count)))),
                                                t -> t.synchronized_(
                                                                lock,
                                                                s -> s.exec(assignStaticField(
                                                                        count, addInt(staticField(count), literal(0)))))
                                                        .end())
                                        .end());
                        bodies.define(
                                race,
                                (b, lock, times) -> b.for_(
                                                PrimitiveToken.INT,
                                                literal(0),
                                                i -> ltInt(i, times),
                                                i -> assign(i, addInt(i, literal(1))),
                                                (loop, _, _) -> loop.synchronized_(
                                                        lock,
                                                        s -> s.exec(assignStaticField(
                                                                count, addInt(staticField(count), literal(1))))))
                                        .end());
                        bodies.define(read, b -> b.return_(staticField(count)));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("synchronized (v0) {").contains("Locked.count = Locked.count + 1;");

        Object lock = new Object();
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            List<Future<?>> running = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                running.add(pool.submit(() -> {
                    try {
                        compiled.invoke("race", lock, 1000);
                    } catch (Throwable e) {
                        throw new AssertionError(e);
                    }
                }));
            }
            for (Future<?> task : running) {
                task.get();
            }
        } finally {
            pool.shutdownNow();
        }

        assertThat(compiled.invoke("read")).isEqualTo(8000);
        assertThatCode(() -> compiled.invoke("inTry", lock)).doesNotThrowAnyException();
    }

    @Test
    void aSynchronizedBodyThatCannotCompleteNormallyEndsTheBlock() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var stop = cb.declareStaticMethod("stop", PrimitiveToken.INT, Object_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(stop, (b, lock) -> b.synchronizedTerminated(lock, s -> s.return_(literal(1))));
                        return bodies;
                    }
                }));

        assertThat(compiled.source()).contains("synchronized (v0) {").contains("return 1;");
        assertThat(compiled.invoke("stop", new Object())).isEqualTo(1);
    }

    @Test
    void aPrimitiveMonitorIsRefused() {
        assertThatThrownBy(() -> file(b -> b.synchronizedTerminated(literal(1), s -> s.return_(literal(1)))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("primitive type");
    }

    @Test
    void aSynchronizedBodyThatEndsWithoutATerminatedFormIsRefused() {
        assertThatThrownBy(() -> file(b -> b.synchronized_(literal("lock"), s -> s.return_(literal(1)))
                        .return_(literal(1))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot complete normally");
    }

    private static JavaFile file(Consumer<? super Body<Prim.Int>> spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var method = cb.declareStaticMethod("method", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(method, b -> {
                    spec.accept(b);
                    return b.return_(literal(0));
                });
                return bodies;
            }
        });
    }
}
