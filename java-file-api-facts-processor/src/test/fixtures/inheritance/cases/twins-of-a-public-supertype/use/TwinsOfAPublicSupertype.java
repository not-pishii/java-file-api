import gen.facts.java.lang.String_;
import gen.facts.p.PObj_;
import gen.facts.p.PStr_;
import gen.facts.p.PubAbs_;
import gen.facts.p.PubFn2_;
import gen.facts.p.PubFn3_;
import gen.facts.p.Sink_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.type.Types;
import p.PObj;
import p.PStr;
import p.PubFn2;
import p.PubFn3;
import p.PubImpl;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;

/// A hidden supertype and a `public` one each declare an abstract `get()`:
/// on the subtype javac sees the one of the more specific result, and so
/// does the fact — of the subtype if the hidden supertype declares that
/// one, of the `public` supertype if it does.
public final class TwinsOfAPublicSupertype {
    private TwinsOfAPublicSupertype() {}

    /// `PubFn2 extends HObj, PStr`: `Object get()` of the hidden `HObj`, `String get()` of `PStr`.
    public static void theSamIsTheMethodOfThePublicSupertype() {
        Sam0<PubFn2, String> sam = PubFn2_.sam;
        MethodRef0<PStr, String> told = PStr_.get;

        assertThat(sam.method().result().typeRef()).isEqualTo(Types.STRING);
        assertThat(told.result().typeRef()).isEqualTo(Types.STRING);
    }

    /// `PubFn3 extends HStr, PObj`: `String get()` of the hidden `HStr`, `Object get()` of `PObj`.
    public static void theMoreSpecificMethodOfTheHiddenSupertypeIsAdopted() {
        MethodRef0<PubFn3, String> get = PubFn3_.get;
        Sam0<PubFn3, String> sam = PubFn3_.sam;
        MethodRef0<PObj, Object> wider = PObj_.get;

        assertThat(sam.method()).isSameAs(get);
        assertThat(wider.result().typeRef()).isEqualTo(Types.OBJECT);
    }

    /// `PubAbs extends HAbs implements PStr`: `get()` on a `PubAbs` is a `String` to javac, so the
    /// call it compiles is `take(String)` — the overload the fact names.
    public static void theOverloadJavacCallsIsTheOneTheFactNames(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN,
                        PubAbs_.TOKEN,
                        abs -> staticCall(Sink_.take_String, call(abs, PStr_.get)),
                        new PubImpl()))
                .isEqualTo("string");
    }

    public static void theWiderOverloadIsCalledWithACast(Typed typed) {
        assertThat(typed.apply(
                        String_.TOKEN,
                        PubAbs_.TOKEN,
                        abs -> staticCall(Sink_.take_Object, call(abs, PStr_.get)),
                        new PubImpl()))
                .isEqualTo("object");
    }
}
