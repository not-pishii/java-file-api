package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

/// A token of a primitive type. The eight tokens are the constants of this
/// class.
///
/// The phantom `T` is the box — [#INT] is a `TypeToken<Integer>` — and the
/// phantom `A` is the primitive array type, so [#array()] of [#INT] is an
/// `ArrayToken<int[]>`, never confused with `Integer[]`.
///
/// @param <T> the box of the primitive type
/// @param <A> the array type of the primitive type
public final class PrimitiveToken<T, A> implements TypeToken<T> {
    private static final List<ClassDesc> NUMBER = List.of(ClassDesc.of("java.lang.Number"), ConstantDescs.CD_Object);
    private static final List<ClassDesc> OBJECT = List.of(ConstantDescs.CD_Object);

    /// The `int` type.
    public static final PrimitiveToken<Integer, int[]> INT =
            new PrimitiveToken<>(PrimitiveTypeRef.INT, ConstantDescs.CD_int, Integer.class, NUMBER);

    /// The `long` type.
    public static final PrimitiveToken<Long, long[]> LONG =
            new PrimitiveToken<>(PrimitiveTypeRef.LONG, ConstantDescs.CD_long, Long.class, NUMBER);

    /// The `double` type.
    public static final PrimitiveToken<Double, double[]> DOUBLE =
            new PrimitiveToken<>(PrimitiveTypeRef.DOUBLE, ConstantDescs.CD_double, Double.class, NUMBER);

    /// The `float` type.
    public static final PrimitiveToken<Float, float[]> FLOAT =
            new PrimitiveToken<>(PrimitiveTypeRef.FLOAT, ConstantDescs.CD_float, Float.class, NUMBER);

    /// The `boolean` type.
    public static final PrimitiveToken<Boolean, boolean[]> BOOLEAN =
            new PrimitiveToken<>(PrimitiveTypeRef.BOOLEAN, ConstantDescs.CD_boolean, Boolean.class, OBJECT);

    /// The `char` type.
    public static final PrimitiveToken<Character, char[]> CHAR =
            new PrimitiveToken<>(PrimitiveTypeRef.CHAR, ConstantDescs.CD_char, Character.class, OBJECT);

    /// The `byte` type.
    public static final PrimitiveToken<Byte, byte[]> BYTE =
            new PrimitiveToken<>(PrimitiveTypeRef.BYTE, ConstantDescs.CD_byte, Byte.class, NUMBER);

    /// The `short` type.
    public static final PrimitiveToken<Short, short[]> SHORT =
            new PrimitiveToken<>(PrimitiveTypeRef.SHORT, ConstantDescs.CD_short, Short.class, NUMBER);

    private final PrimitiveTypeRef typeRef;
    private final ClassDesc erasure;
    private final FinalClassToken<T> boxed;
    private final ArrayToken<A> array;

    private PrimitiveToken(PrimitiveTypeRef typeRef, ClassDesc erasure, Class<T> box, List<ClassDesc> superclasses) {
        this.typeRef = typeRef;
        this.erasure = erasure;
        this.boxed = FinalClassToken.introduce(Types.of(box), superclasses, MethodTable.EMPTY);
        this.array = new ArrayToken<>(this);
    }

    /// The token of the box type, e.g. `Integer` for `int`.
    ///
    /// @return the box token
    public FinalClassToken<T> boxed() {
        return boxed;
    }

    /// The token of the array type, e.g. `int[]` for `int`.
    ///
    /// @return the array token
    public ArrayToken<A> array() {
        return array;
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
