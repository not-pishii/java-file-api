package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.typed.Inheritance.Supertype;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.InterruptedException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// What a method of a class cannot override ([SignatureRegistry#requireOverrides]) and what a method of
/// its superclass cannot stand for ([SignatureRegistry#requireInherited]), on heritages made by hand:
/// the shapes no fixture of the tests has.
class OverrideRefusalsTest {
    private static final String OWNER = "class x.Self";
    private static final ClassDesc SELF = ClassDesc.of("x", "Self");
    private static final ClassDesc I = ClassDesc.of("x", "I");
    private static final ClassDesc K = ClassDesc.of("x", "K");
    private static final ClassDesc CD_LIST = ClassDesc.of("java.util.List");
    private static final ClassDesc CD_STRINGS = ConstantDescs.CD_String.arrayType();
    private static final OpenClassToken<Object> SELF_TOKEN =
            UnsafeFacts.openClassToken(new ClassTypeRef(SELF), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY);
    private static final Subtyping.Declaring DECLARING = new Subtyping.Declaring(SELF, List.of());

    private static final Heritage.Declarer ABSTRACT_OF_I =
            new Heritage.Declarer.OfInterface(I, Heritage.InterfaceMethod.ABSTRACT);

    private static Heritage.Declarer ofK(Heritage.ClassMethod how) {
        return new Heritage.Declarer.OfClass(K, Heritage.Visibility.PUBLIC, how);
    }

    /// `int m(params)`, as a supertype has it.
    private static Heritage.Method<Heritage.Inherited> m(
            Heritage.Declarer by,
            Arity arity,
            List<TypeRef> params,
            List<ClassDesc> erasure,
            ClassOrInterfaceTypeRef... thrown) {
        return new Heritage.Method<>(
                Signature.of("m", erasure.stream().map(Param::fixed).toArray(Param[]::new)),
                List.of(new Heritage.Plain(
                        by, arity, params, new Heritage.Result.Of<>(PrimitiveTypeRef.INT), List.of(thrown))),
                Set.of(erasure),
                List.of());
    }

    private static Supertype iface(Heritage.Method<Heritage.Inherited> method) {
        return new Supertype(
                UnsafeFacts.interfaceToken(new ClassTypeRef(I), MethodTable.EMPTY),
                List.of(),
                new Heritage.Applied(List.of(method), List.of()));
    }

    private static Supertype klass(Heritage.Method<Heritage.Inherited> method) {
        return new Supertype(
                UnsafeFacts.openClassToken(new ClassTypeRef(K), List.of(ConstantDescs.CD_Object), MethodTable.EMPTY),
                List.of(),
                new Heritage.Applied(List.of(method), List.of()));
    }

    /// The methods `m` of the erased parameters `erasure` a class that extends `superclass` and
    /// implements `interfaces` inherits.
    private static Inheritance.Group group(List<ClassDesc> erasure, Supertype superclass, Supertype... interfaces) {
        Inheritance inheritance = Inheritance.of(new Inheritance.Heir.OfClass(OWNER, "x"), superclass);
        for (Supertype anInterface : interfaces) {
            inheritance = inheritance.with(anInterface);
        }
        return inheritance.group(new MethodSignature("m", erasure)).orElseThrow();
    }

    private static Supertype object() {
        return Inheritance.supertype(OWNER, UnsafeFacts.unverifiedClasspath(), Object_.TOKEN);
    }

    private static void overrides(Invocable own, Inheritance.Group group) {
        SignatureRegistry.requireOverrides(OWNER, own, group, DECLARING);
    }

    /// The source of the type `name` of the package `x`, the types of the Java a test stands for.
    private static JavaFileObject java(String name, String declaration) {
        return JavaFileObjects.forSourceString("x." + name, "package x;\n" + declaration + "\n");
    }

    /// What javac says of the types, under every lint.
    private static Compilation javac(JavaFileObject... types) {
        return Compiler.javac().withOptions("-Xlint:all").compile(types);
    }

    /// javac rejects the Java a refusal stands for with `error`.
    private static void javacRejects(String error, JavaFileObject... types) {
        Compilation compilation = javac(types);
        CompilationSubject.assertThat(compilation).failed();
        CompilationSubject.assertThat(compilation).hadErrorContaining(error);
    }

    /// javac accepts the Java of an accepted neighbour of a refusal, without a warning.
    private static void javacAccepts(JavaFileObject... types) {
        CompilationSubject.assertThat(javac(types)).succeededWithoutWarnings();
    }

    @Test
    void anInstanceMethodDoesNotOverrideAStaticOne() {
        Inheritance.Group group =
                group(List.of(), klass(m(ofK(Heritage.ClassMethod.STATIC), Arity.FIXED, List.of(), List.of())));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> overrides(
                        UnsafeInvocables.method(SELF_TOKEN, "m", PrimitiveToken.INT, MemberTraits.DEFAULT), group))
                .withMessageContaining("an instance method cannot override a static one (JLS 8.4.8.1)");
        javacRejects(
                "overridden method is static",
                java("K", "public class K { public static int m() { return 0; } }"),
                java("Self", "public class Self extends K { public int m() { return 0; } }"));
    }

    @Test
    void aMethodOfVariableArityOfATypeVariableIsNotOverridden() {
        // int m(T... all): javac warns of heap pollution where an override is declared
        Inheritance.Group group = group(
                List.of(ConstantDescs.CD_Object.arrayType()),
                object(),
                iface(m(
                        ABSTRACT_OF_I,
                        Arity.VARIABLE,
                        List.of(Types.array(Types.typeVar("T"))),
                        List.of(ConstantDescs.CD_Object.arrayType()))));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN,
                                "m",
                                PrimitiveToken.INT,
                                ArrayToken.of(Object_.TOKEN),
                                MemberTraits.DEFAULT),
                        group))
                .withMessageContaining(
                        "the method is of variable arity and its last parameter is of the type T[], which is not reifiable");
        JavaFileObject self =
                java("Self", "public abstract class Self<T> implements I<T> { public int m(T... all) { return 0; } }");
        Compilation compilation = javac(java("I", "public interface I<T> { int m(T... all); }"), self);
        CompilationSubject.assertThat(compilation).succeeded();
        CompilationSubject.assertThat(compilation)
                .hadWarningContaining("Possible heap pollution from parameterized vararg type T")
                .inFile(self);
    }

    @Test
    void aMethodOfVariableArityOfArraysIsOverridden() {
        // int m(String[]... all): an array of a reifiable type is reifiable
        ClassDesc arrays = CD_STRINGS.arrayType();
        Inheritance.Group group = group(
                List.of(arrays),
                object(),
                iface(m(
                        ABSTRACT_OF_I,
                        Arity.VARIABLE,
                        List.of(Types.array(Types.array(Types.STRING))),
                        List.of(arrays))));

        assertThatCode(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN,
                                "m",
                                PrimitiveToken.INT,
                                ArrayToken.of(ArrayToken.of(String_.TOKEN)),
                                MemberTraits.DEFAULT),
                        group))
                .doesNotThrowAnyException();
        javacAccepts(
                java("I", "public interface I { int m(String[]... all); }"),
                java(
                        "Self",
                        "public abstract class Self implements I { public int m(String[]... all) { return 0; } }"));
    }

    @Test
    void aMethodOfVariableArityOfPrimitivesIsOverridden() {
        // int m(int... all)
        ClassDesc ints = ConstantDescs.CD_int.arrayType();
        Inheritance.Group group = group(
                List.of(ints),
                object(),
                iface(m(ABSTRACT_OF_I, Arity.VARIABLE, List.of(Types.array(PrimitiveTypeRef.INT)), List.of(ints))));

        assertThatCode(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN, "m", PrimitiveToken.INT, PrimitiveToken.INT.array(), MemberTraits.DEFAULT),
                        group))
                .doesNotThrowAnyException();
        javacAccepts(
                java("I", "public interface I { int m(int... all); }"),
                java("Self", "public abstract class Self implements I { public int m(int... all) { return 0; } }"));
    }

    @Test
    void aMethodOfOtherParametersOfTheSameErasureIsNoOverride() {
        // int m(List<Integer>) and int m(List<String>): a name clash to javac
        Inheritance.Group group = group(
                List.of(CD_LIST),
                object(),
                iface(m(
                        ABSTRACT_OF_I,
                        Arity.FIXED,
                        List.of(new List_<>(Integer_.TOKEN).token.typeRef()),
                        List.of(CD_LIST))));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN,
                                "m",
                                PrimitiveToken.INT,
                                new List_<>(String_.TOKEN).token,
                                MemberTraits.DEFAULT),
                        group))
                .withMessageContaining("its parameters are (java.util.List<java.lang.Integer>), which erase as those"
                        + " of the method do and are not the same: name clash (JLS 8.4.8.3)");
        javacRejects(
                "name clash: m(java.util.List<java.lang.String>) in x.Self and m(java.util.List<java.lang.Integer>) in x.I have the same erasure",
                java("I", "import java.util.List; public interface I { int m(List<Integer> list); }"),
                java(
                        "Self",
                        "import java.util.List; public abstract class Self implements I {"
                                + " public int m(List<String> list) { return 0; } }"));
    }

    @Test
    void anOverrideDeclaresNoCheckedExceptionTheMethodDoesNot() {
        // int m() throws IOException, K<String>, X: of the classes of the clause InterruptedException is under none
        Inheritance.Group group = group(
                List.of(),
                object(),
                iface(m(
                        ABSTRACT_OF_I,
                        Arity.FIXED,
                        List.of(),
                        List.of(),
                        Types.of(IOException.class),
                        Types.parameterized(K, Types.STRING),
                        Types.typeVar("X"))));

        assertThatCode(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN, "m", PrimitiveToken.INT, MemberTraits.DEFAULT.throwing(IOException_.TOKEN)),
                        group))
                .doesNotThrowAnyException();
        javacAccepts(
                java(
                        "I",
                        "import java.io.*; public interface I<X extends Exception> { int m() throws IOException, X; }"),
                java(
                        "Self",
                        "import java.io.*; public abstract class Self implements I<FileNotFoundException> {"
                                + " public int m() throws IOException { return 0; } }"));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN,
                                "m",
                                PrimitiveToken.INT,
                                MemberTraits.DEFAULT.throwing(InterruptedException_.TOKEN)),
                        group))
                .withMessageContaining("the method declares java.io.IOException, ")
                .withMessageContaining(
                        ": an override cannot declare the checked exception java.lang.InterruptedException"
                                + " (JLS 8.4.8.3)");
        javacRejects(
                "overridden method does not throw java.lang.InterruptedException",
                java(
                        "I",
                        "import java.io.*; public interface I<X extends Exception> { int m() throws IOException, X; }"),
                java(
                        "Self",
                        "import java.io.*; public abstract class Self implements I<FileNotFoundException> {"
                                + " public int m() throws InterruptedException { return 0; } }"));
    }

    @Test
    void anOverrideDeclaresNoTypeVariableForAnException() {
        // a type variable is known to be under no class of the clause
        TypeVarToken<Exception> thrown =
                UnsafeFacts.typeVarToken(Types.typeVar("X"), ClassDesc.of("java.lang.Exception"));
        Inheritance.Group group = group(
                List.of(),
                object(),
                iface(m(ABSTRACT_OF_I, Arity.FIXED, List.of(), List.of(), Types.of(IOException.class))));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> overrides(
                        UnsafeInvocables.method(
                                SELF_TOKEN, "m", PrimitiveToken.INT, MemberTraits.DEFAULT.throwing(thrown)),
                        group))
                .withMessageContaining("an override cannot declare the checked exception X (JLS 8.4.8.3)");
        javacRejects(
                "overridden method does not throw X",
                java("I", "import java.io.*; public interface I { int m() throws IOException; }"),
                java(
                        "Self",
                        "public abstract class Self<X extends Exception> implements I {"
                                + " public int m() throws X { return 0; } }"));
    }

    @Test
    void aMethodOfTheSuperclassThatTakesTheArrayIsNoImplementationOfOneOfVariableArity() {
        // int m(String[]) of the superclass for the abstract int m(String...) of the interface: javac warns
        Supertype spreading =
                iface(m(ABSTRACT_OF_I, Arity.VARIABLE, List.of(Types.array(Types.STRING)), List.of(CD_STRINGS)));
        DeclaredToken<?> through = spreading.token();
        Inheritance.Group group = group(
                List.of(CD_STRINGS),
                klass(m(
                        ofK(Heritage.ClassMethod.CONCRETE),
                        Arity.FIXED,
                        List.of(Types.array(Types.STRING)),
                        List.of(CD_STRINGS))),
                spreading);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> SignatureRegistry.requireInherited(
                        OWNER,
                        UnsafeInvocables.method(
                                SELF_TOKEN,
                                "m",
                                PrimitiveToken.INT,
                                ArrayToken.of(String_.TOKEN),
                                MemberTraits.DEFAULT),
                        through,
                        group,
                        DECLARING))
                .withMessageContaining("from its superclass: the last parameter of one is of variable arity and"
                        + " that of the other an array");
        Compilation compilation = javac(
                java("I", "public interface I { int m(String... all); }"),
                java("K", "public class K { public int m(String[] all) { return 0; } }"),
                java("Self", "public abstract class Self extends K implements I { }"));
        CompilationSubject.assertThat(compilation).succeeded();
        CompilationSubject.assertThat(compilation)
                .hadWarningContaining("m(java.lang.String[]) in x.K implements m(java.lang.String...) in x.I;"
                        + " overriding method is missing '...'");
    }
}
