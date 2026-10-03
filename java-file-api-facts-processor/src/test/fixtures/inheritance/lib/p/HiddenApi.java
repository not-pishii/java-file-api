package p;

interface HiddenApi extends PubApi {
    String CONST = "const";

    String api();

    default String dflt() {
        return "dflt";
    }

    static void istatic() {}
}
