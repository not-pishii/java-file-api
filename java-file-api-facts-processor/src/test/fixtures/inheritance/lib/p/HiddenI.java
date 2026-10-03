package p;

interface HiddenI {
    String K = "k";

    default String run() {
        return "run";
    }

    default String more() {
        return "more";
    }
}
