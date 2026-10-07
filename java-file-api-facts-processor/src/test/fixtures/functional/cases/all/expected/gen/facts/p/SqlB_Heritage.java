package gen.facts.p;

import gen.facts.p.SqlB_Heritage.Held;
import gen.facts.p.SqlB_Heritage.Implement;
import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage.Arity;
import me.supcheg.javafile.facts.Heritage.Dispatch;
import me.supcheg.javafile.facts.Heritage.Method;
import me.supcheg.javafile.facts.Heritage.Result;
import me.supcheg.javafile.facts.Heritage.Told;
import me.supcheg.javafile.facts.Heritage.Visibility;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Steps;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidImplementation0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import me.supcheg.javafile.type.Types;
import org.jspecify.annotations.NullMarked;
import p.SqlB;

/// What a class that extends or implements [SqlB] inherits: the heritage the shape of its metamodel tells, loaded only when one is declared.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodelPart
@SuppressWarnings({
    "rawtypes",
    "deprecation",
    "removal"
})
@NullMarked
public final class SqlB_Heritage implements Supplier<Told> {
    /// For the shape of the metamodel.
    public SqlB_Heritage() {
    }

    /// The heritage.
    ///
    /// @return the heritage, made when it is first asked for
    @Override
    public Told get() {
        return Held.HERITAGE;
    }

    static final class Held {
        /// Every constructor and method of [SqlB] that is not private, as a member of it, whoever declares it.
        static final Told HERITAGE = new Told(List.of(m0()), List.of());

        private Held() {
        }

        private static Method m0() {
            return new Method(Visibility.PUBLIC, Dispatch.ABSTRACT, ClassDesc.of("p.SqlB"), Signature.of("m"), List.of(), List.of(), Arity.FIXED, Result.NOTHING, List.of(Types.of(ClassDesc.of("java.sql.SQLException"))), Set.of(List.of()), List.of());
        }
    }

    /// Starts the proof that a class being declared implements [SqlB]: a step per abstract method follows, in the order of their names.
    ///
    /// @param type the token of the type
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the stage of the first step
    public static <Self> Implement<Self> implement(DeclaredToken<SqlB> type, RefToken<Self> self) {
        return new Implement<>(UnsafeFacts.steps(type, self));
    }

    /// The proof that a class implements [SqlB] after 0 of its 1 steps.
    ///
    /// @param <Self> the class being declared
    public static final class Implement<Self> {
        private final Steps<Self, SqlB> steps;

        private Implement(Steps<Self, SqlB> steps) {
            this.steps = steps;
        }

        /// Takes how the class has the abstract method `m()`, declared in `SqlB`.
        ///
        /// @param how the implementation of the method
        /// @return the proof, complete
        public Complete<Self, SqlB> m(VoidImplementation0<Self> how) {
            return this.steps.with(Signature.of("m"), how).complete();
        }
    }
}
