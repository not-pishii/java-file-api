package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.PrimitiveTypeRef;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

/// A token of a primitive type. The eight tokens are the constants of this
/// class.
///
/// The phantom `P` is the primitive's marker from [Prim] — [#INT] is a
/// `TypeToken<Prim.Int>` — so a primitive and its box are different types to
/// javac and never convert into each other implicitly (§6.1). The phantom `B`
/// is the box, reachable only through [#boxed()]; the phantom `A` is the
/// primitive array type, so [#array()] of [#INT] is an
/// `ArrayToken<int[], Prim.Int>`, never confused with `Integer[]`.
///
/// @param <P> the marker of the primitive type
/// @param <B> the box of the primitive type
/// @param <A> the array type of the primitive type
public final class PrimitiveToken<P, B, A> implements TypeToken<P> {
    private static final List<ClassDesc> NUMBER = List.of(ClassDesc.of("java.lang.Number"), ConstantDescs.CD_Object);
    private static final List<ClassDesc> OBJECT = List.of(ConstantDescs.CD_Object);

    /// The `int` type.
    public static final PrimitiveToken<Prim.Int, Integer, int[]> INT =
            new PrimitiveToken<>(PrimitiveTypeRef.INT, ConstantDescs.CD_int, Integer.class, NUMBER);

    /// The `long` type.
    public static final PrimitiveToken<Prim.Long, Long, long[]> LONG =
            new PrimitiveToken<>(PrimitiveTypeRef.LONG, ConstantDescs.CD_long, Long.class, NUMBER);

    /// The `double` type.
    public static final PrimitiveToken<Prim.Double, Double, double[]> DOUBLE =
            new PrimitiveToken<>(PrimitiveTypeRef.DOUBLE, ConstantDescs.CD_double, Double.class, NUMBER);

    /// The `float` type.
    public static final PrimitiveToken<Prim.Float, Float, float[]> FLOAT =
            new PrimitiveToken<>(PrimitiveTypeRef.FLOAT, ConstantDescs.CD_float, Float.class, NUMBER);

    /// The `boolean` type.
    public static final PrimitiveToken<Prim.Bool, Boolean, boolean[]> BOOLEAN =
            new PrimitiveToken<>(PrimitiveTypeRef.BOOLEAN, ConstantDescs.CD_boolean, Boolean.class, OBJECT);

    /// The `char` type.
    public static final PrimitiveToken<Prim.Char, Character, char[]> CHAR =
            new PrimitiveToken<>(PrimitiveTypeRef.CHAR, ConstantDescs.CD_char, Character.class, OBJECT);

    /// The `byte` type.
    public static final PrimitiveToken<Prim.Byte, Byte, byte[]> BYTE =
            new PrimitiveToken<>(PrimitiveTypeRef.BYTE, ConstantDescs.CD_byte, Byte.class, NUMBER);

    /// The `short` type.
    public static final PrimitiveToken<Prim.Short, Short, short[]> SHORT =
            new PrimitiveToken<>(PrimitiveTypeRef.SHORT, ConstantDescs.CD_short, Short.class, NUMBER);

    private final PrimitiveTypeRef typeRef;
    private final ClassDesc erasure;
    private final Class<B> boxClass;
    private final FinalClassToken<B> boxed;
    private final ArrayToken<A, P> array;

    private PrimitiveToken(
            PrimitiveTypeRef typeRef, ClassDesc erasure, Class<B> boxClass, List<ClassDesc> superclasses) {
        this.typeRef = typeRef;
        this.erasure = erasure;
        this.boxClass = boxClass;
        this.boxed = new FinalClassToken<>(
                TypeShape.builtin(ClassDesc.ofDescriptor(boxClass.descriptorString()), superclasses), List.of());
        this.array = new ArrayToken<>(this);
    }

    /// The token of the box type, e.g. `Integer` for `int`. Its shape is
    /// [ShapeOrigin#BUILTIN] and records no methods and no supertypes.
    ///
    /// @return the box token
    public FinalClassToken<B> boxed() {
        return boxed;
    }

    /// The token of the array type, e.g. `int[]` for `int`.
    ///
    /// @return the array token
    public ArrayToken<A, P> array() {
        return array;
    }

    /// The name of the unboxing method of the box, e.g. `intValue`.
    ///
    /// @return the method name
    public String unboxMethodName() {
        return typeRef.sourceName() + "Value";
    }

    Class<B> boxClass() {
        return boxClass;
    }

    @Override
    public PrimitiveTypeRef typeRef() {
        return typeRef;
    }

    @Override
    public ClassDesc erasure() {
        return erasure;
    }

    @Override
    public String toString() {
        return typeRef.sourceName();
    }
}
