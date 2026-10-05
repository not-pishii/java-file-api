package gen.facts.p;

import gen.facts.p.SqlB_.Canonical;
import gen.facts.p.SqlB_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.Interface;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.SqlB;

/// The full metamodel of [SqlB]: a fact of every `public` member the type declares.
///
/// `@Facts` does not ask for [SqlB]: it is here as a supertype of [p.Disjoint], whose inherited members are called through this metamodel.
///
/// A member [SqlB] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = SqlB.class, fingerprint = "52c856a57d74ed922b7b2dd19688cc72d2b8d9ee07605280719ce9b4a359dc94", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class SqlB_ {
    /// The shape of [SqlB] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [SqlB] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<Interface> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.SqlB_"), "52c856a57d74ed922b7b2dd19688cc72d2b8d9ee07605280719ce9b4a359dc94", () -> Canonical.TEXT), DeclaredKind.INTERFACE, ClassDesc.of("p.SqlB"), List.of(), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(Signature.of("m")), Set.of(Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of()), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [SqlB], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [SqlB].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.SqlB interface sealed=no
        tparams -
        superclasses -
        interfaces -
        supertypes -
        enum -
        members declared-public
        member method abstract m() -> void throws java.sql.SQLException
        sam m() -> void throws java.sql.SQLException
        table abstract m()
        table concrete equals(java.lang.Object); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor -
        """;

        private Canonical() {
        }
    }

    /// The token of [SqlB].
    public static final InterfaceToken<SqlB> TOKEN = UnsafeFacts.interfaceToken(Data.SHAPE);

    /// The fact of [SqlB#m()].
    public static final VoidMethodRef0<SqlB> m = UnsafeFacts.voidMethod(TOKEN, "m", MemberTraits.ABSTRACT.throwing(UnsafeFacts.<SQLException>openClassToken(gen.facts.java.sql.SQLException_.Data.SHAPE)));

    /// The fact of the single abstract method [SqlB#m()], which a lambda implements.
    public static final VoidSam0<SqlB> sam = UnsafeFacts.voidSam(m);

    private SqlB_() {
    }
}
