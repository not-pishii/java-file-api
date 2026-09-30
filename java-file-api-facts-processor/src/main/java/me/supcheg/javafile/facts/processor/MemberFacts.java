package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.StaticMethodCallExpr;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.FieldModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/// The fields of a full metamodel that hold the facts of the members of its
/// type (mini-spec §2.3, §2.7): a type and an initializer each, made by
/// `UnsafeFacts`.
///
/// Every token of another type is made on the spot from the shape in that
/// type's metamodel — `UnsafeFacts.<Y>openClassToken(Y_.Data.SHAPE)` —
/// never from `Y_.TOKEN`, so the classes of metamodels initialize without a
/// cycle (§2.6); only the token of the type itself is `TOKEN`.
///
/// The types of a signature are those of [MemberPlan]: classes and
/// interfaces that are not generic, primitives and arrays of them.
final class MemberFacts {
    private static final String FACTS = "me.supcheg.javafile.facts";

    private static final ClassDesc CD_UNSAFE_FACTS = ClassDesc.of(FACTS, "UnsafeFacts");
    private static final ClassDesc CD_MEMBER_TRAITS = ClassDesc.of(FACTS, "MemberTraits");
    private static final ClassDesc CD_PRIMITIVE_TOKEN = ClassDesc.of(FACTS, "PrimitiveToken");
    private static final ClassDesc CD_ARRAY_TOKEN = ClassDesc.of(FACTS, "ArrayToken");
    private static final ClassDesc CD_FLOAT = ClassDesc.of("java.lang.Float");
    private static final ClassDesc CD_DOUBLE = ClassDesc.of("java.lang.Double");

    /// The greatest number of parameters of a member with a fact.
    private static final int MAX_ARITY = 12;

    /// The simple names of the classes the fact fields refer to at the start
    /// of an expression: a fact named so would hide the class.
    static final Set<String> QUALIFIERS =
            Set.of("UnsafeFacts", "MemberTraits", "PrimitiveToken", "ArrayToken", "Float", "Double");

    /// A field of the metamodel.
    ///
    /// @param name the name of the fact
    /// @param type the type of the field
    /// @param init the initializer
    record Spec(String name, TypeRef type, Expr init) {}

    private final ClassDesc self;
    private final DeclaredKind selfKind;
    private final Targets targets;
    private final Set<ClassDesc> uses = new LinkedHashSet<>();
    private final Set<ClassDesc> signatureTypes = new LinkedHashSet<>();

    /// @param self the type of the metamodel
    /// @param selfKind the kind of the type
    /// @param targets the metamodels of the types the signatures mention
    MemberFacts(ClassDesc self, DeclaredKind selfKind, Targets targets) {
        this.self = self;
        this.selfKind = selfKind;
        this.targets = targets;
    }

    /// The classes the fields refer to by simple name apart from the class
    /// of the metamodel: the fact classes, `MemberTraits`, the nested `Data`
    /// classes of other metamodels and the types of the signatures.
    ///
    /// @return the classes, after [#specs]
    Set<ClassDesc> uses() {
        return uses;
    }

    /// The classes and interfaces the signatures mention.
    ///
    /// @return the types, after [#specs]
    Set<ClassDesc> signatureTypes() {
        return signatureTypes;
    }

    /// The fields of a plan: enum constants in declaration order, then
    /// fields, constructors, methods — each sorted by name — and `sam`.
    ///
    /// @param plan the plan
    /// @return the fields
    List<Spec> specs(MemberPlan plan) {
        List<Spec> specs = new ArrayList<>();
        for (MemberPlan.EnumFact constant : plan.enumConstants()) {
            specs.add(new Spec(
                    constant.name(),
                    parameterized(family("EnumConstant"), Types.of(self)),
                    Exprs.field(MetamodelNames.TOKEN).call("constant", Exprs.literal(constant.constant()))));
        }
        List<MemberPlan.Fact> sorted = plan.members().stream()
                .sorted(Comparator.<MemberPlan.Fact>comparingInt(f -> group(f)).thenComparing(MemberPlan.Fact::name))
                .toList();
        for (MemberPlan.Fact fact : sorted) {
            specs.add(
                    switch (fact.model()) {
                        case FieldModel field -> field(fact.name(), field);
                        case CtorModel ctor -> ctor(fact.name(), ctor);
                        case MethodModel method -> new Spec(fact.name(), methodType(method), methodFact(method));
                    });
        }
        plan.sam().ifPresent(sam -> specs.add(sam(sam)));
        return specs;
    }

    private static int group(MemberPlan.Fact fact) {
        return switch (fact.model()) {
            case FieldModel ignored -> 0;
            case CtorModel ignored -> 1;
            case MethodModel ignored -> 2;
        };
    }

    // ---- fields

    private Spec field(String name, FieldModel field) {
        TypeRef type = phantom(field.type());
        TypeRef owner = Types.of(self);
        Expr token = token(field.type());
        Expr nameLiteral = Exprs.literal(field.name());
        Expr owned = Exprs.field(MetamodelNames.TOKEN);
        if (field.isStatic()) {
            return switch (field.mutability()) {
                case FieldModel.Mutability.Constant constant ->
                    new Spec(
                            name,
                            parameterized(family("StaticFieldRef"), type),
                            unsafe("constantField", owned, nameLiteral, token, constantValue(constant.value())));
                case FieldModel.Mutability.Final ignored ->
                    new Spec(
                            name,
                            parameterized(family("StaticFieldRef"), type),
                            unsafe("staticField", owned, nameLiteral, token));
                case FieldModel.Mutability.Mutable ignored ->
                    new Spec(
                            name,
                            parameterized(family("MutableStaticFieldRef"), type),
                            unsafe("mutableStaticField", owned, nameLiteral, token));
            };
        }
        return switch (field.mutability()) {
            case FieldModel.Mutability.Mutable ignored ->
                new Spec(
                        name,
                        parameterized(family("MutableFieldRef"), owner, type),
                        unsafe("mutableField", owned, nameLiteral, token));
            case FieldModel.Mutability.Final ignored ->
                new Spec(
                        name,
                        parameterized(family("FieldRef"), owner, type),
                        unsafe("field", owned, nameLiteral, token));
            case FieldModel.Mutability.Constant ignored ->
                throw new IllegalStateException("an instance field is not a constant: " + field.name());
        };
    }

    /// The value of a constant as an expression a `constantField` takes: the
    /// box of the field's type. `byte`, `short`, `char` and `float` are cast,
    /// since a literal is an `int` or a `double`; a value no literal
    /// writes — `NaN` and the infinities — is made from its bits.
    private Expr constantValue(Object value) {
        return switch (value) {
            case String string -> Exprs.literal(string);
            case Boolean bool -> Exprs.literal(bool);
            case Integer integer -> Exprs.literal(integer);
            case Long number -> Exprs.literal(number);
            case Byte number -> Exprs.cast(PrimitiveTypeRef.BYTE, Exprs.literal((int) number));
            case Short number -> Exprs.cast(PrimitiveTypeRef.SHORT, Exprs.literal((int) number));
            case Character character -> Exprs.cast(PrimitiveTypeRef.CHAR, Exprs.literal((int) character));
            case Float number
            when Float.isFinite(number) -> Exprs.cast(PrimitiveTypeRef.FLOAT, Exprs.literal((double) number));
            case Float number ->
                Exprs.staticCall(use(CD_FLOAT), "intBitsToFloat", Exprs.literal(Float.floatToRawIntBits(number)));
            case Double number when Double.isFinite(number) -> Exprs.literal(number.doubleValue());
            case Double number ->
                Exprs.staticCall(use(CD_DOUBLE), "longBitsToDouble", Exprs.literal(Double.doubleToRawLongBits(number)));
            default -> throw new IllegalStateException("not a constant: " + value);
        };
    }

    // ---- constructors and methods

    private Spec ctor(String name, CtorModel ctor) {
        boolean isAbstract = selfKind instanceof DeclaredKind.AbstractClass;
        int arity = ctor.params().size();
        List<TypeRef> typeArgs = new ArrayList<>(List.of(Types.of(self)));
        List<Expr> args = new ArrayList<>(List.of(Exprs.field(MetamodelNames.TOKEN)));
        for (TypeRef param : ctor.params()) {
            typeArgs.add(phantom(param));
            args.add(token(param));
        }
        args.add(traits(Overridability.FINAL, ctor.throwsTypes()));
        String family = (isAbstract ? "AbstractCtorRef" : "CtorRef") + requireArity(arity);
        return new Spec(
                name, parameterized(family(family), typeArgs), unsafe(isAbstract ? "abstractCtor" : "ctor", args));
    }

    private TypeRef methodType(MethodModel method) {
        List<TypeRef> typeArgs = new ArrayList<>();
        if (!method.isStatic()) {
            typeArgs.add(Types.of(self));
        }
        method.result().ifPresent(result -> typeArgs.add(phantom(result)));
        method.params().forEach(param -> typeArgs.add(phantom(param)));
        String family = (method.result().isEmpty() ? "Void" : "")
                + (method.isStatic() ? "Static" : "")
                + "MethodRef"
                + requireArity(method.params().size());
        return parameterized(family(family), typeArgs);
    }

    /// The `UnsafeFacts.method(TOKEN, "name", result, params…, traits)` of a
    /// method, or the factory of its kind.
    private Expr methodFact(MethodModel method) {
        List<Expr> args = new ArrayList<>(List.of(Exprs.field(MetamodelNames.TOKEN), Exprs.literal(method.name())));
        method.result().ifPresent(result -> args.add(token(result)));
        method.params().forEach(param -> args.add(token(param)));
        args.add(traits(method.overridability(), method.throwsTypes()));
        String factory = method.isStatic() ? "staticMethod" : "method";
        return unsafe(method.result().isEmpty() ? "void" + capitalized(factory) : factory, args);
    }

    private Spec sam(MemberPlan.SamFact sam) {
        MethodModel method = sam.method();
        List<TypeRef> typeArgs = new ArrayList<>(List.of(Types.of(self)));
        method.result().ifPresent(result -> typeArgs.add(phantom(result)));
        method.params().forEach(param -> typeArgs.add(phantom(param)));
        boolean isVoid = method.result().isEmpty();
        String family =
                (isVoid ? "VoidSam" : "Sam") + requireArity(method.params().size());
        Expr fact = sam.reuse().<Expr>map(Exprs::field).orElseGet(() -> methodFact(method));
        return new Spec("sam", parameterized(family(family), typeArgs), unsafe(isVoid ? "voidSam" : "sam", fact));
    }

    private static int requireArity(int arity) {
        if (arity > MAX_ARITY) {
            throw new IllegalStateException(arity + " parameters, more than " + MAX_ARITY);
        }
        return arity;
    }

    private static String capitalized(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private Expr traits(Overridability overridability, List<ClassOrInterfaceTypeRef> throwsTypes) {
        Expr base = Exprs.staticField(use(CD_MEMBER_TRAITS), overridability.name());
        return throwsTypes.isEmpty()
                ? base
                : base.call("throwing", throwsTypes.stream().map(this::token).toList());
    }

    // ---- tokens and their phantom types

    /// The token of a type: a primitive's constant, the array token of an
    /// array, `TOKEN` for the type itself and the token made from the shape
    /// of its metamodel for any other type.
    private Expr token(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> Exprs.staticField(use(CD_PRIMITIVE_TOKEN), primitive.name());
            case ArrayTypeRef array ->
                array.component() instanceof PrimitiveTypeRef
                        ? token(array.component()).call("array")
                        : Exprs.staticCall(use(CD_ARRAY_TOKEN), "of", token(array.component()));
            case ClassTypeRef cls -> declaredToken(cls);
            case ParameterizedTypeRef ignored -> throw new IllegalStateException("not supported yet: " + type);
            case TypeVarRef ignored -> throw new IllegalStateException("not supported yet: " + type);
        };
    }

    private Expr declaredToken(ClassTypeRef cls) {
        if (cls.desc().equals(self)) {
            return Exprs.field(MetamodelNames.TOKEN);
        }
        Targets.Target target = targets.of(cls.desc())
                .orElseThrow(() -> new IllegalStateException(
                        "no metamodel of " + cls.desc().displayName()));
        signatureTypes.add(cls.desc());
        ClassDesc data = use(target.metamodel().nested(MetamodelNames.DATA));
        return new StaticMethodCallExpr(
                Types.of(use(CD_UNSAFE_FACTS)),
                factory(target.kind()),
                List.of(Exprs.staticField(data, MetamodelNames.SHAPE)),
                List.of(Types.of(cls.desc())));
    }

    private static String factory(DeclaredKind kind) {
        return switch (kind) {
            case DeclaredKind.FinalClass ignored -> "finalClassToken";
            case DeclaredKind.OpenClass ignored -> "openClassToken";
            case DeclaredKind.AbstractClass ignored -> "abstractClassToken";
            case DeclaredKind.Interface ignored -> "interfaceToken";
            case DeclaredKind.EnumClass ignored -> "enumToken";
        };
    }

    /// The type a token is typed by: the marker of a primitive, the type
    /// itself otherwise.
    private TypeRef phantom(TypeRef type) {
        if (type instanceof PrimitiveTypeRef primitive) {
            return Types.of(use(ClassDesc.of(FACTS, "Prim$" + marker(primitive))));
        }
        return javaType(type);
    }

    private TypeRef javaType(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> primitive;
            case ArrayTypeRef array -> Types.array(javaType(array.component()));
            case ClassTypeRef cls -> {
                signatureTypes.add(cls.desc());
                yield cls;
            }
            case ParameterizedTypeRef ignored -> throw new IllegalStateException("not supported yet: " + type);
            case TypeVarRef ignored -> throw new IllegalStateException("not supported yet: " + type);
        };
    }

    private static String marker(PrimitiveTypeRef primitive) {
        return switch (primitive) {
            case INT -> "Int";
            case LONG -> "Long";
            case DOUBLE -> "Double";
            case FLOAT -> "Float";
            case BOOLEAN -> "Bool";
            case CHAR -> "Char";
            case BYTE -> "Byte";
            case SHORT -> "Short";
        };
    }

    // ---- helpers

    private Expr unsafe(String factory, Expr... args) {
        return unsafe(factory, List.of(args));
    }

    private Expr unsafe(String factory, List<Expr> args) {
        return Exprs.staticCall(use(CD_UNSAFE_FACTS), factory, args);
    }

    private ClassDesc family(String name) {
        return use(ClassDesc.of(FACTS, name));
    }

    private ClassDesc use(ClassDesc desc) {
        uses.add(desc);
        return desc;
    }

    private static TypeRef parameterized(ClassDesc raw, TypeRef... args) {
        return parameterized(raw, List.of(args));
    }

    private static TypeRef parameterized(ClassDesc raw, List<TypeRef> args) {
        if (args.isEmpty()) {
            return Types.of(raw);
        }
        List<TypeArg> exact = args.stream().map(Types::exact).toList();
        return new ParameterizedTypeRef(raw, exact);
    }
}
