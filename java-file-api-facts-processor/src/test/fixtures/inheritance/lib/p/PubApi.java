package p;

public interface PubApi {
    String pub();

    default String beyond() {
        return "beyond";
    }
}
