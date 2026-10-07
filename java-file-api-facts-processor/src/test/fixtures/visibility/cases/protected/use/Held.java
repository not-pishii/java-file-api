import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.String_;
import gen.facts.p.ProtAbs_;
import gen.facts.p.ProtFinal_;
import gen.facts.p.ProtG_;
import me.supcheg.javafile.facts.Access;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.Protected;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import p.ProtAbs;
import p.ProtFinal;
import p.ProtG;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/// The `protected` members of a class that can be extended have facts held back for a subclass: instance and
/// `static` ones, of a generic type and of a generic method, declared and adopted from a supertype that is not
/// `public`; a `protected` constructor is a fact for a subclass constructor.
public final class Held {
    private Held() {}

    public static void theProtectedMembersOfAGenericClassAreHeldBackInItsTerms() {
        ProtG_<String> strings = new ProtG_<>(String_.TOKEN);

        Protected<ProtG<String>, MutableFieldRef<ProtG<String>, String>> item = strings.item;
        Protected<ProtG<String>, FieldRef<ProtG<String>, Prim.Int>> fin = strings.fin;
        Protected<ProtG<String>, MethodRef0<ProtG<String>, String>> get = strings.get;
        Protected<ProtG<String>, VoidMethodRef1<ProtG<String>, String>> set = strings.set_T;
        Protected<ProtG<String>, VoidMethodRef0<ProtG<String>>> fixed = strings.fixed;
        Protected<ProtG<String>, MethodRef2<ProtG<String>, Integer, Integer, String>> pick =
                strings.pick_X_T(Integer_.TOKEN);

        assertThat(item.owner()).isSameAs(strings.token);
        assertThat(item).hasToString("protected java.lang.String p.ProtG<java.lang.String>.item");
        assertThat(fin).hasToString("protected int p.ProtG<java.lang.String>.fin");
        assertThat(get).hasToString("protected java.lang.String p.ProtG<java.lang.String>.get()");
        assertThat(set).hasToString("protected void p.ProtG<java.lang.String>.set(java.lang.String)");
        assertThat(fixed).hasToString("protected void p.ProtG<java.lang.String>.fixed()");
        assertThat(pick.owner()).isSameAs(strings.token);
    }

    public static void theProtectedStaticMembersAreHeldBackByTheTypeOfAnyArguments() {
        Protected<ProtG<?>, MutableStaticFieldRef<Prim.Int>> count = ProtG_.count;
        Protected<ProtG<?>, StaticFieldRef<Object>> lock = ProtG_.LOCK;
        Protected<ProtG<?>, StaticFieldRef<Prim.Int>> limit = ProtG_.LIMIT;
        Protected<ProtG<?>, StaticFieldRef<String>> name = ProtG_.NAME;
        Protected<ProtG<?>, VoidStaticMethodRef0> reset = ProtG_.reset;
        Protected<ProtG<?>, StaticMethodRef1<Integer, Integer>> spick = ProtG_.spick_X(Integer_.TOKEN);

        assertThat(count.owner()).isSameAs(ProtG_.ANY);
        assertThat(count).hasToString("protected static int p.ProtG<?>.count");
        assertThat(lock).hasToString("protected static java.lang.Object p.ProtG<?>.LOCK");
        assertThat(limit).hasToString("protected static int p.ProtG<?>.LIMIT");
        assertThat(name).hasToString("protected static java.lang.String p.ProtG<?>.NAME");
        assertThat(reset).hasToString("protected static void p.ProtG<?>.reset()");
        assertThat(spick.owner()).isSameAs(ProtG_.ANY);
    }

    public static void theProtectedMembersOfASupertypeThatIsNotPublicAreAdopted() {
        ProtG_<String> strings = new ProtG_<>(String_.TOKEN);

        Protected<ProtG<String>, MutableFieldRef<ProtG<String>, Prim.Int>> field = strings.adoptedField;
        Protected<ProtG<String>, VoidMethodRef0<ProtG<String>>> method = strings.adopted;
        Protected<ProtG<?>, VoidStaticMethodRef0> staticMethod = ProtG_.adoptedStatic;

        assertThat(field).hasToString("protected int p.ProtG<java.lang.String>.adoptedField");
        assertThat(method).hasToString("protected void p.ProtG<java.lang.String>.adopted()");
        assertThat(staticMethod).hasToString("protected static void p.ProtG<?>.adoptedStatic()");
    }

    public static void aProtectedConstructorIsForASubclassAndAPublicOneOfAnOpenClassForNewToo() {
        ProtG_<String> strings = new ProtG_<>(String_.TOKEN);

        CtorRef0<ProtG<String>> publicOne = strings.new_;
        SuperCtorRef1<ProtG<String>, String> protectedOne = strings.super_T;
        SuperCtorRef0<ProtAbs> ofAnAbstractClass = ProtAbs_.super_;

        assertThat(publicOne.access()).isEqualTo(Access.PUBLIC);
        assertThat(protectedOne.access()).isEqualTo(Access.PROTECTED);
        assertThat(protectedOne).hasToString("super p.ProtG<java.lang.String>(java.lang.String)");
        assertThat(protectedOne.traits().throwsTypes()).hasSize(1);
        assertThat(ofAnAbstractClass.access()).isEqualTo(Access.PROTECTED);
    }

    public static void aProtectedAbstractMethodIsHeldBackAsAnyOther() {
        Protected<ProtAbs, MethodRef1<ProtAbs, String, String>> hook = ProtAbs_.hook_String;

        assertThat(hook.owner()).isSameAs(ProtAbs_.TOKEN);
        assertThat(hook).hasToString("protected java.lang.String p.ProtAbs.hook(java.lang.String)");
    }

    public static void aClassThatCannotBeExtendedHasNoFactsOfItsProtectedMembers() {
        CtorRef0<ProtFinal> constructor = ProtFinal_.new_;

        assertThat(constructor.owner()).isSameAs(ProtFinal_.TOKEN);
        assertThat(ProtFinal_.class.getFields()).extracting(Field::getName).containsExactlyInAnyOrder("TOKEN", "new_");
    }
}
