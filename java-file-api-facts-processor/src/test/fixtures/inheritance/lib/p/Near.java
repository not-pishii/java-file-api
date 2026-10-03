package p;

abstract class Near<T> extends Far<T> implements HiddenApi {
    public static final String HID = "near";

    public String overridden() {
        return "near";
    }

    public String near() {
        return "near";
    }

    public static String snear() {
        return "snear";
    }
}
