import gen.facts.p.Seq_;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.ShapeOrigin;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Seq;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// A generic interface has its shape, `ANY` and a constructor per parameterization; the shape says where it comes
/// from, and its fingerprint is the one of its canonical form.
public final class Generic {
    private Generic() {}

    private static final ClassDesc CD_SEQ = ClassDesc.of("p.Seq");
    private static final ClassDesc CD_COLL = ClassDesc.of("p.Coll");

    public static void theShapeDescribesTheInterface() {
        TypeShape<DeclaredKind.Interface> shape = Seq_.Data.SHAPE;

        assertThat(shape.kind()).isEqualTo(DeclaredKind.INTERFACE);
        assertThat(shape.desc()).isEqualTo(CD_SEQ);
        assertThat(shape.typeParameters()).containsExactly(new TypeParam("E", List.of()));
        assertThat(shape.superclasses()).isEmpty();
        assertThat(shape.supertypes().supertype(CD_COLL)).contains(Types.parameterized(CD_COLL, Types.typeVar("E")));
    }

    public static void theMethodTableIsOfTheTypeArguments() {
        TypeShape<DeclaredKind.Interface> shape = Seq_.Data.SHAPE;

        assertThat(shape.methods().instantiate(List.of(ConstantDescs.CD_String)).abstractMethods())
                .contains(new MethodSignature("add", List.of(ConstantDescs.CD_String)));
        assertThat(shape.methods().instantiate(List.of(ConstantDescs.CD_String)).staticMethods())
                .contains(new MethodSignature("of", List.of()));
    }

    /// The origin is the metamodel, and its fingerprint is the SHA-256 of its canonical form (which the
    /// annotation `@GeneratedMetamodel` in the source in `expected/` has too).
    public static void theOriginIsTheMetamodelAndTheFingerprintIsOfItsCanonicalForm() {
        assertThat(Seq_.Data.SHAPE.origin()).isInstanceOfSatisfying(ShapeOrigin.Metamodel.class, origin -> {
            assertThat(origin.metamodel()).isEqualTo(ClassDesc.of("gen.facts.p.Seq_"));
            assertThat(sha256(origin.canonical().get())).isEqualTo(origin.fingerprint());
        });
    }

    public static void anyIsTheTokenOfTheInterfaceOfAnyArgument() {
        InterfaceToken<Seq<?>> any = Seq_.ANY;

        assertThat(any.typeRef()).isEqualTo(Types.parameterized(CD_SEQ, Types.unbounded()));
        assertThat(any.shape()).isSameAs(Seq_.Data.SHAPE);
    }

    public static void aConstructorMakesTheTokenOfTheParameterization() {
        Seq_<Integer> ofIntegers = new Seq_<>(PrimitiveToken.INT.boxed());
        InterfaceToken<Seq<Integer>> token = ofIntegers.token;

        assertThat(token.typeRef())
                .isEqualTo(new ParameterizedTypeRef(CD_SEQ, List.of(Types.exact(Types.of(ConstantDescs.CD_Integer)))));
        assertThat(token.methods().abstractMethods())
                .contains(new MethodSignature("add", List.of(ConstantDescs.CD_Integer)));
    }

    private static String sha256(String text) {
        try {
            return HexFormat.of()
                    .formatHex(MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
