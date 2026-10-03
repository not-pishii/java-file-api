package p;

public interface Sub2 extends Sub { default String twice(String s) { return apply(apply(s)); } }
