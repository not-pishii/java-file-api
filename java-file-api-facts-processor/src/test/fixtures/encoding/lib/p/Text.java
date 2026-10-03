package p;

// the source of the metamodel is ASCII: the constants are written as the escapes they are, in this file too
public class Text {
    public static final String LONE_HIGH = "a\uD800b";
    public static final String LONE_LOW = "\uDC00";
    public static final String PAIR = "😀";
    public static final String CYRILLIC = "привет é\u007f";
    public static final String ESCAPES = "\\u0041 \\é \"é\"";
    public static final char CHAR = 'я';
    public static final char LONE_CHAR = '\uD800';
    public static final char DELETE = '\u007f';
    public int число;
}
