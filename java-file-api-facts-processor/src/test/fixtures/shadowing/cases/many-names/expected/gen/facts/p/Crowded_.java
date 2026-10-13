package gen.facts.p;

import gen.facts.p.Crowded_.Canonical;
import gen.facts.p.Crowded_.Data;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Complete;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.DeclaredKind.OpenClass;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.ShapeOrigin.Metamodel;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.meta.GeneratedMetamodel;
import me.supcheg.javafile.facts.meta.GeneratedMetamodelPart;
import org.jspecify.annotations.NullMarked;
import p.Crowded;

/// The full metamodel of [Crowded], which `@Facts` asks for: a fact of every `public` member the type declares.
///
/// A member [Crowded] inherits has its fact in the metamodel of the supertype that declares it: [gen.facts.java.lang.Object_].
///
/// A generated class extends or implements [Crowded] by the proof its metamodel starts, with the stages of `Crowded_Heritage`.
@Generated("me.supcheg.javafile.facts.processor.FactsProcessor")
@GeneratedMetamodel(of = Crowded.class, fingerprint = "e93ad2c4c934e6f3bae664287d8b784da5195999eb23437b8e066e752c707c13", complete = true, format = 14)
@SuppressWarnings({
    "deprecation",
    "removal"
})
@NullMarked
public final class Crowded_ {
    /// The shape of [Crowded] as plain data: initializing it touches no other metamodel.
    @GeneratedMetamodelPart
    public static final class Data {
        /// What [Crowded] was when this metamodel was generated. Its tokens are made from it.
        public static final TypeShape<OpenClass> SHAPE = UnsafeFacts.shape(new Metamodel(ClassDesc.of("gen.facts.p.Crowded_"), "e93ad2c4c934e6f3bae664287d8b784da5195999eb23437b8e066e752c707c13", () -> Canonical.TEXT), DeclaredKind.OPEN_CLASS, ClassDesc.of("p.Crowded"), List.of(), List.of(ClassDesc.of("java.lang.Object")), List.of(), Supertypes.NONE, new MethodTableTemplate(Set.of(), Set.of(Signature.of("clone"), Signature.of("equals", Param.fixed(ClassDesc.of("java.lang.Object"))), Signature.of("finalize"), Signature.of("getClass"), Signature.of("hashCode"), Signature.of("notify"), Signature.of("notifyAll"), Signature.of("toString"), Signature.of("wait"), Signature.of("wait", Param.fixed(ConstantDescs.CD_long)), Signature.of("wait", Param.fixed(ConstantDescs.CD_long), Param.fixed(ConstantDescs.CD_int))), Set.of(), Set.of(Signature.of("Crowded"))), false, new Crowded_Heritage());

        private Data() {
        }
    }

    /// The canonical form of [Crowded], loaded only to compare the type with the one on the target classpath.
    @GeneratedMetamodelPart
    static final class Canonical {
        /// The canonical form of [Crowded].
        static final String TEXT = """
        javafile-facts-canonical 8
        type p.Crowded open-class sealed=no nesting=top
        tparams -
        superclasses java.lang.Object
        interfaces -
        supertypes -
        enum -
        members declared-accessible
        member ctor public () throws -
        inherit ctor Crowded() public () throws -
        inherit field package p.Crowded f0
        inherit field package p.Crowded f1
        inherit field package p.Crowded f10
        inherit field package p.Crowded f100
        inherit field package p.Crowded f101
        inherit field package p.Crowded f102
        inherit field package p.Crowded f103
        inherit field package p.Crowded f104
        inherit field package p.Crowded f105
        inherit field package p.Crowded f106
        inherit field package p.Crowded f107
        inherit field package p.Crowded f108
        inherit field package p.Crowded f109
        inherit field package p.Crowded f11
        inherit field package p.Crowded f110
        inherit field package p.Crowded f111
        inherit field package p.Crowded f112
        inherit field package p.Crowded f113
        inherit field package p.Crowded f114
        inherit field package p.Crowded f115
        inherit field package p.Crowded f116
        inherit field package p.Crowded f117
        inherit field package p.Crowded f118
        inherit field package p.Crowded f119
        inherit field package p.Crowded f12
        inherit field package p.Crowded f120
        inherit field package p.Crowded f121
        inherit field package p.Crowded f122
        inherit field package p.Crowded f123
        inherit field package p.Crowded f124
        inherit field package p.Crowded f125
        inherit field package p.Crowded f126
        inherit field package p.Crowded f127
        inherit field package p.Crowded f128
        inherit field package p.Crowded f129
        inherit field package p.Crowded f13
        inherit field package p.Crowded f130
        inherit field package p.Crowded f131
        inherit field package p.Crowded f132
        inherit field package p.Crowded f133
        inherit field package p.Crowded f134
        inherit field package p.Crowded f135
        inherit field package p.Crowded f136
        inherit field package p.Crowded f137
        inherit field package p.Crowded f138
        inherit field package p.Crowded f139
        inherit field package p.Crowded f14
        inherit field package p.Crowded f140
        inherit field package p.Crowded f141
        inherit field package p.Crowded f142
        inherit field package p.Crowded f143
        inherit field package p.Crowded f144
        inherit field package p.Crowded f145
        inherit field package p.Crowded f146
        inherit field package p.Crowded f147
        inherit field package p.Crowded f148
        inherit field package p.Crowded f149
        inherit field package p.Crowded f15
        inherit field package p.Crowded f150
        inherit field package p.Crowded f151
        inherit field package p.Crowded f152
        inherit field package p.Crowded f153
        inherit field package p.Crowded f154
        inherit field package p.Crowded f155
        inherit field package p.Crowded f156
        inherit field package p.Crowded f157
        inherit field package p.Crowded f158
        inherit field package p.Crowded f159
        inherit field package p.Crowded f16
        inherit field package p.Crowded f160
        inherit field package p.Crowded f161
        inherit field package p.Crowded f162
        inherit field package p.Crowded f163
        inherit field package p.Crowded f164
        inherit field package p.Crowded f165
        inherit field package p.Crowded f166
        inherit field package p.Crowded f167
        inherit field package p.Crowded f168
        inherit field package p.Crowded f169
        inherit field package p.Crowded f17
        inherit field package p.Crowded f170
        inherit field package p.Crowded f171
        inherit field package p.Crowded f172
        inherit field package p.Crowded f173
        inherit field package p.Crowded f174
        inherit field package p.Crowded f175
        inherit field package p.Crowded f176
        inherit field package p.Crowded f177
        inherit field package p.Crowded f178
        inherit field package p.Crowded f179
        inherit field package p.Crowded f18
        inherit field package p.Crowded f180
        inherit field package p.Crowded f181
        inherit field package p.Crowded f182
        inherit field package p.Crowded f183
        inherit field package p.Crowded f184
        inherit field package p.Crowded f185
        inherit field package p.Crowded f186
        inherit field package p.Crowded f187
        inherit field package p.Crowded f188
        inherit field package p.Crowded f189
        inherit field package p.Crowded f19
        inherit field package p.Crowded f190
        inherit field package p.Crowded f191
        inherit field package p.Crowded f192
        inherit field package p.Crowded f193
        inherit field package p.Crowded f194
        inherit field package p.Crowded f195
        inherit field package p.Crowded f196
        inherit field package p.Crowded f197
        inherit field package p.Crowded f198
        inherit field package p.Crowded f199
        inherit field package p.Crowded f2
        inherit field package p.Crowded f20
        inherit field package p.Crowded f200
        inherit field package p.Crowded f21
        inherit field package p.Crowded f22
        inherit field package p.Crowded f23
        inherit field package p.Crowded f24
        inherit field package p.Crowded f25
        inherit field package p.Crowded f26
        inherit field package p.Crowded f27
        inherit field package p.Crowded f28
        inherit field package p.Crowded f29
        inherit field package p.Crowded f3
        inherit field package p.Crowded f30
        inherit field package p.Crowded f31
        inherit field package p.Crowded f32
        inherit field package p.Crowded f33
        inherit field package p.Crowded f34
        inherit field package p.Crowded f35
        inherit field package p.Crowded f36
        inherit field package p.Crowded f37
        inherit field package p.Crowded f38
        inherit field package p.Crowded f39
        inherit field package p.Crowded f4
        inherit field package p.Crowded f40
        inherit field package p.Crowded f41
        inherit field package p.Crowded f42
        inherit field package p.Crowded f43
        inherit field package p.Crowded f44
        inherit field package p.Crowded f45
        inherit field package p.Crowded f46
        inherit field package p.Crowded f47
        inherit field package p.Crowded f48
        inherit field package p.Crowded f49
        inherit field package p.Crowded f5
        inherit field package p.Crowded f50
        inherit field package p.Crowded f51
        inherit field package p.Crowded f52
        inherit field package p.Crowded f53
        inherit field package p.Crowded f54
        inherit field package p.Crowded f55
        inherit field package p.Crowded f56
        inherit field package p.Crowded f57
        inherit field package p.Crowded f58
        inherit field package p.Crowded f59
        inherit field package p.Crowded f6
        inherit field package p.Crowded f60
        inherit field package p.Crowded f61
        inherit field package p.Crowded f62
        inherit field package p.Crowded f63
        inherit field package p.Crowded f64
        inherit field package p.Crowded f65
        inherit field package p.Crowded f66
        inherit field package p.Crowded f67
        inherit field package p.Crowded f68
        inherit field package p.Crowded f69
        inherit field package p.Crowded f7
        inherit field package p.Crowded f70
        inherit field package p.Crowded f71
        inherit field package p.Crowded f72
        inherit field package p.Crowded f73
        inherit field package p.Crowded f74
        inherit field package p.Crowded f75
        inherit field package p.Crowded f76
        inherit field package p.Crowded f77
        inherit field package p.Crowded f78
        inherit field package p.Crowded f79
        inherit field package p.Crowded f8
        inherit field package p.Crowded f80
        inherit field package p.Crowded f81
        inherit field package p.Crowded f82
        inherit field package p.Crowded f83
        inherit field package p.Crowded f84
        inherit field package p.Crowded f85
        inherit field package p.Crowded f86
        inherit field package p.Crowded f87
        inherit field package p.Crowded f88
        inherit field package p.Crowded f89
        inherit field package p.Crowded f9
        inherit field package p.Crowded f90
        inherit field package p.Crowded f91
        inherit field package p.Crowded f92
        inherit field package p.Crowded f93
        inherit field package p.Crowded f94
        inherit field package p.Crowded f95
        inherit field package p.Crowded f96
        inherit field package p.Crowded f97
        inherit field package p.Crowded f98
        inherit field package p.Crowded f99
        inherit method clone() erased () overrides - by class protected concrete java.lang.Object () -> java.lang.Object throws java.lang.CloneNotSupportedException
        inherit method equals(java.lang.Object) erased (java.lang.Object) overrides - by class public concrete java.lang.Object (java.lang.Object) -> boolean throws -
        inherit method finalize() erased () overrides - by class protected concrete java.lang.Object () -> void throws java.lang.Throwable
        inherit method getClass() erased () overrides - by class public final java.lang.Object () -> java.lang.Class<?> throws -
        inherit method hashCode() erased () overrides - by class public concrete java.lang.Object () -> int throws -
        inherit method notify() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method notifyAll() erased () overrides - by class public final java.lang.Object () -> void throws -
        inherit method toString() erased () overrides - by class public concrete java.lang.Object () -> java.lang.String throws -
        inherit method wait() erased () overrides - by class public final java.lang.Object () -> void throws java.lang.InterruptedException
        inherit method wait(long) erased (long) overrides - by class public final java.lang.Object (long) -> void throws java.lang.InterruptedException
        inherit method wait(long, int) erased (long, int) overrides - by class public final java.lang.Object (long, int) -> void throws java.lang.InterruptedException
        table abstract -
        table concrete clone(); equals(java.lang.Object); finalize(); getClass(); hashCode(); notify(); notifyAll(); toString(); wait(); wait(long); wait(long, int)
        table static -
        table ctor Crowded()
        """;

        private Canonical() {
        }
    }

    /// The token of [Crowded].
    public static final OpenClassToken<Crowded> TOKEN = UnsafeFacts.openClassToken(Data.SHAPE);

    /// The fact of [Crowded#Crowded()].
    public static final CtorRef0<Crowded> new_ = UnsafeInvocables.ctor(TOKEN, MemberTraits.FINAL);

    private Crowded_() {
    }

    /// Starts the proof that a class being declared extends [Crowded]: a step per abstract method of the type follows, and the declaration of the class takes the proof after the last.
    ///
    /// @param self the token of the class being declared
    /// @param <Self> the class being declared
    /// @return the proof, or the stage of its first step
    public static <Self> Complete<Self, Crowded> extend(RefToken<Self> self) {
        return new Crowded_Heritage().extend(TOKEN, self);
    }
}
