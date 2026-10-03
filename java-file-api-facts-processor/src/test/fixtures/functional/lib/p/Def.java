package p;

public interface Def extends Fn { default String apply(String s) { return s; } }
