package p;

public enum Op {
    ADD {
        public int apply(int a, int b) {
            return a + b;
        }
    },
    SUB {
        public int apply(int a, int b) {
            return a - b;
        }
    };

    public abstract int apply(int a, int b);

    public int twice(int a) {
        return 0;
    }
}
