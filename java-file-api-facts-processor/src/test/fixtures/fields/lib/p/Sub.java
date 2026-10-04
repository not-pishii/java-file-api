package p;

/// `sub.f` and `Sub.g` are the fields of `Sub`, which hide those of `Super`.
public class Sub extends Super {
    public String f = "Sub.f";
    public static String g = "Sub.g";
}
