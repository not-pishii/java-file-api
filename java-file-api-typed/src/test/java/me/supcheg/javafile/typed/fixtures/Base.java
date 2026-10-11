package me.supcheg.javafile.typed.fixtures;

/// An open class a generated class extends: overloads a witness has to tell apart, a field and an enum
/// read through a witness, and what only a subclass reaches — a `protected` constructor, method, field
/// and static method. Beside them what no generated class overrides or declares over: a `final`
/// method, a `static` one, one of package access.
public class Base {
    public final String name;
    public final Op op = Op.SUB;
    protected int hits;

    public Base() {
        this.name = "base";
    }

    protected Base(String name) {
        this.name = name;
    }

    public String pick(Object value) {
        return "object";
    }

    public String pick(String value) {
        return "string";
    }

    public String greet() {
        return hook(name);
    }

    protected String hook(String value) {
        hits++;
        return "base:" + value;
    }

    protected void risky() throws java.io.IOException {}

    protected static String motto() {
        return "motto";
    }

    public final String sealed() {
        return name;
    }

    public static String util() {
        return "util";
    }

    String local() {
        return "local";
    }
}
