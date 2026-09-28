package me.supcheg.javafile.facts;

public final class Sam2<F, R, A1, A2> {
    private final InterfaceToken<F> owner;

    private final MethodRef2<F, R, A1, A2> method;

    private Sam2(MethodRef2<F, R, A1, A2> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, R, A1, A2> Sam2<F, R, A1, A2> introduce(MethodRef2<F, R, A1, A2> method) {
        return new Sam2<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef2<F, R, A1, A2> method() {
        return this.method;
    }

    public TypeToken<R> result() {
        return this.method.result();
    }

    public TypeToken<A1> param1() {
        return this.method.param1();
    }

    public TypeToken<A2> param2() {
        return this.method.param2();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
