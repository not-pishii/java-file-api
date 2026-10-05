package com.acme.gen.facts.p;

import com.acme.gen.facts.p.Svc_.Canonical;
import com.acme.gen.facts.p.Svc_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Dep;
import p.Svc;

/// The full metamodel of [Svc], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Svc] inherits has its fact in the metamodel of the supertype that declares it: [com.acme.gen.facts.java.lang.Object_].
///
/// These members have no fact:
///
/// - `method hidden()`, which mentions types that are not public: p.Hidden
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Svc.class, fingerprint = "8cbc49e7d3b61ab6cbf920932c0d0ef9de92286d1b3216ae4a61daf20e43d586", complete = true, format = 7)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Svc_ {
    /// The shape of [Svc] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Svc] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("com.acme.gen.facts.p.Svc_"), "8cbc49e7d3b61ab6cbf920932c0d0ef9de92286d1b3216ae4a61daf20e43d586", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Svc"), List.of(), List.of(ClassDesc.of("java.lang.Object")), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("dep"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("hidden"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Svc"))), List.of(), false);

        private Data() {
        }
    }

    /// The canonical form of [Svc], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Svc].
        static final String TEXT = """
        javafile-facts-canonical 5
        type p.Svc open-class sealed=no
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-public
        member ctor() throws -
        member method overridable dep() -> p.Dep throws -
        table abstract -
        table concrete clone(); dep(); equals(java.lang.Object); finalize(); getClass(); hashCode(); hidden(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Svc()
        """;

        private Canonical() {
        }
    }

    /// The token of [Svc].
    public static final OpenClassToken<Svc> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Svc#Svc()].
    public static final CtorRef0<Svc> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// The fact of [Svc#dep()].
    public static final MethodRef0<Svc, Dep> dep = UnsafeFacts.method(TOKEN, "dep", UnsafeFacts.<Dep>openClassToken(Dep_.Data.SHAPE), MemberTraits.OVERRIDABLE);

    private Svc_() {
    }
}
