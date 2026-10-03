package p;

interface HLabel {
    String NAME = "name";

    default String label() {
        return "label";
    }
}
