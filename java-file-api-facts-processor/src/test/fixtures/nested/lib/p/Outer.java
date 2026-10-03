package p;

public class Outer {
    public static class Inner {
        public Inner() {}

        public String s() {
            return null;
        }
    }

    public class NonStatic {
        public NonStatic() {}

        public NonStatic(int x) {}

        public int x() {
            return 0;
        }
    }

    public interface Api {
        void run();
    }

    public enum E {
        A,
        B
    }

    public record R(int x) {}

    public Inner inner() {
        return null;
    }

    public NonStatic nonStatic() {
        return null;
    }

    public E e() {
        return null;
    }

    public static class Deep {
        public static class Deeper {
            public Deeper() {}
        }
    }
}
