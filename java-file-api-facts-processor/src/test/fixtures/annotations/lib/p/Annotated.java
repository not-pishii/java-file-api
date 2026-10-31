package p;

@Author(name = "Ada", year = 1815, tags = @Tag(value = "type", rank = 2, levels = {Level.LOW, Level.HIGH},
        type = String.class, flag = false, big = 2L, ratio = 2.5, letter = 'b', small = 3, medium = 4, decimal = 2.0f))
public class Annotated {
    @Author(name = "field")
    @Tag(value = "field", rank = 3, levels = {Level.HIGH}, type = Integer.class, primitive = int.class,
            array = String[].class, flag = true, big = 3L, ratio = 3.5, letter = 'c', small = 4, medium = 5,
            decimal = 3.0f)
    public String name;

    @Author(name = "constructor")
    public Annotated() {
        this.name = "annotated";
    }

    @Author(name = "method", tags = @Tag("nested"))
    @Tag(value = "method", rank = 4, levels = {Level.LOW}, type = Object.class, primitive = byte.class,
            array = int[].class, flag = false, big = 4L, ratio = 4.5, letter = 'd', small = 6, medium = 7,
            decimal = 4.0f)
    public String greet(@Tag("parameter") String who) {
        return "hello " + who;
    }
}
