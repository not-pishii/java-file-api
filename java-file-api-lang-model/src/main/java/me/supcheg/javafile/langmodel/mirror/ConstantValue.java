package me.supcheg.javafile.langmodel.mirror;

/// The value of a constant variable (JLS 4.12.4): of a primitive type or a
/// `String`, and of nothing else.
public sealed interface ConstantValue {

    /// The value of a constant as `javax.lang.model` gives it
    /// (`VariableElement.getConstantValue()`): a `String` or the box of a
    /// primitive.
    ///
    /// @param value the value
    /// @return the value, by its type
    /// @throws IllegalArgumentException if `value` is neither a `String` nor the box of a primitive
    static ConstantValue of(Object value) {
        return switch (value) {
            case String string -> new OfString(string);
            case Boolean bool -> new OfBoolean(bool);
            case Byte number -> new OfByte(number);
            case Short number -> new OfShort(number);
            case Character character -> new OfChar(character);
            case Integer number -> new OfInt(number);
            case Long number -> new OfLong(number);
            case Float number -> new OfFloat(number);
            case Double number -> new OfDouble(number);
            default ->
                throw new IllegalArgumentException("a constant is a String or a boxed primitive, got "
                        + value.getClass().getName());
        };
    }

    /// A `String`.
    ///
    /// @param value the value
    record OfString(String value) implements ConstantValue {}

    /// A `boolean`.
    ///
    /// @param value the value
    record OfBoolean(boolean value) implements ConstantValue {}

    /// A `byte`.
    ///
    /// @param value the value
    record OfByte(byte value) implements ConstantValue {}

    /// A `short`.
    ///
    /// @param value the value
    record OfShort(short value) implements ConstantValue {}

    /// A `char`.
    ///
    /// @param value the value
    record OfChar(char value) implements ConstantValue {}

    /// An `int`.
    ///
    /// @param value the value
    record OfInt(int value) implements ConstantValue {}

    /// A `long`.
    ///
    /// @param value the value
    record OfLong(long value) implements ConstantValue {}

    /// A `float`.
    ///
    /// @param value the value, which may be no number or an infinity
    record OfFloat(float value) implements ConstantValue {}

    /// A `double`.
    ///
    /// @param value the value, which may be no number or an infinity
    record OfDouble(double value) implements ConstantValue {}
}
