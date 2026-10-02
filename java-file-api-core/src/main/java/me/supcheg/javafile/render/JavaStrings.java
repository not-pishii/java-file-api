package me.supcheg.javafile.render;

final class JavaStrings {

    private JavaStrings() {}

    static String escape(String raw) {
        StringBuilder sb = new StringBuilder(raw.length());
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append("\\\"");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    // a surrogate without its pair is no character: no encoding writes it
                    boolean paired = Character.isHighSurrogate(c)
                            ? i + 1 < raw.length() && Character.isLowSurrogate(raw.charAt(i + 1))
                            : i > 0 && Character.isHighSurrogate(raw.charAt(i - 1));
                    if (c < 0x20 || Character.isSurrogate(c) && !paired) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }
}
