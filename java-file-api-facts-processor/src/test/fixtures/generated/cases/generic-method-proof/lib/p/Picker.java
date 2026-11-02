package p;

public interface Picker {
    <T extends CharSequence> T pick(T value);
}
