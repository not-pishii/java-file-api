package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.code.Expr;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.code.StaticMethodCallExpr;
import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.FieldModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.type.UnboundedTypeArg;

import java.lang.constant.ClassDesc;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The facts of the members of a type in its full metamodel (mini-spec §2.3,
/// §2.4, §2.7), each made by `UnsafeFacts`:
///
/// - a `public static final` field with an initializer ([Spec.Constant]) for
///   a member of a type that is not generic, and for a `static` member of a
///   generic one;
/// - a `public final` field the constructor of the metamodel assigns
///   ([Spec.Assigned]) for an instance member of a generic type, in terms of
///   its type parameters and of the tokens the constructor takes for them;
/// - a method ([Spec.Factory]) for a generic method: it takes a token per
///   type parameter of the method — a witness, never inferred — under the
///   bounds the method declares, and returns the fact with the witnesses as
///   its explicit type arguments. It is an instance method for an instance
///   method of a generic type, which needs the tokens of the type, and
///   `static` otherwise.
///
/// Every token of another type is made on the spot from the shape in that
/// type's metamodel — `UnsafeFacts.<List<E>>interfaceToken(List_.Data.SHAPE,
/// TokenArg.exact(e))` — never from `Y_.TOKEN` or the constructor of `Y_`,
/// so the classes of metamodels initialize without a cycle (§2.6) and making
/// a metamodel makes no other. The token of the type itself is `TOKEN`, or
/// `token` for a generic type applied to its own type parameters; a `static`
/// member of a generic type is owned by `ANY`. A generic type mentioned
/// without type arguments is raw, and its token is the shape applied to
/// nothing.
final class MemberFacts {
    private static final String FACTS = "me.supcheg.javafile.facts";

    private static final ClassDesc CD_UNSAFE_FACTS = ClassDesc.of(FACTS, "UnsafeFacts");
    private static final ClassDesc CD_MEMBER_TRAITS = ClassDesc.of(FACTS, "MemberTraits");
    private static final ClassDesc CD_PRIMITIVE_TOKEN = ClassDesc.of(FACTS, "PrimitiveToken");
    private static final ClassDesc CD_ARRAY_TOKEN = ClassDesc.of(FACTS, "ArrayToken");
    private static final ClassDesc CD_TOKEN_ARG = ClassDesc.of(FACTS, "TokenArg");
    private static final ClassDesc CD_REF_TOKEN = ClassDesc.of(FACTS, "RefToken");
    private static final ClassDesc CD_FLOAT = ClassDesc.of("java.lang.Float");
    private static final ClassDesc CD_DOUBLE = ClassDesc.of("java.lang.Double");

    /// The greatest number of parameters of a member with a fact.
    private static final int MAX_ARITY = 12;

    /// A fact as a member of the metamodel.
    sealed interface Spec {

        /// The name of the fact.
        ///
        /// @return the name
        String name();

        /// A `public static final` field.
        ///
        /// @param name the name of the fact
        /// @param type the type of the field
        /// @param init the initializer
        record Constant(String name, TypeRef type, Expr init) implements Spec {}

        /// A `public final` field of a generic metamodel, assigned by its
        /// constructor.
        ///
        /// @param name the name of the fact
        /// @param type the type of the field
        /// @param init what the constructor assigns, in terms of its parameters
        record Assigned(String name, TypeRef type, Expr init) implements Spec {}

        /// A method that makes the fact of a generic method from a token per
        /// type parameter.
        ///
        /// @param name the name of the fact
        /// @param isStatic whether the method needs no instance of the metamodel
        /// @param typeParams the type parameters of the generic method, with its bounds
        /// @param witnesses the parameters: a token per type parameter
        /// @param type the type of the fact
        /// @param result the fact, in terms of the parameters
        record Factory(
                String name,
                boolean isStatic,
                List<TypeParam> typeParams,
                List<Witness> witnesses,
                TypeRef type,
                Expr result)
                implements Spec {}
    }

    /// A parameter of a [Spec.Factory]: the token of a type argument.
    ///
    /// @param name the name of the parameter
    /// @param type its type, `RefToken<T>`
    record Witness(String name, TypeRef type) {}

    /// The type the facts are of, as its metamodel names it.
    ///
    /// @param desc the type
    /// @param kind the kind of the type
    /// @param typeVars the type parameters of the type, in order; empty unless generic
    record Self(ClassDesc desc, DeclaredKind kind, List<TypeVar> typeVars) {

        /// The type, its type parameters given by the lists that name them.
        ///
        /// @param desc the type
        /// @param kind the kind of the type
        /// @param declared the type parameters as the type declares them, in order
        /// @param typeParams the type parameters of the metamodel, in order: those of `declared`, some renamed
        /// @param witnesses the parameters of the constructor of the metamodel and its fields of the same
        ///     names: a token per type parameter
        /// @return the type
        static Self of(
                ClassDesc desc,
                DeclaredKind kind,
                List<String> declared,
                List<String> typeParams,
                List<String> witnesses) {
            return new Self(
                    desc,
                    kind,
                    IntStream.range(0, declared.size())
                            .mapToObj(i -> new TypeVar(declared.get(i), typeParams.get(i), witnesses.get(i)))
                            .toList());
        }

        boolean generic() {
            return !typeVars.isEmpty();
        }
    }

    /// A type parameter of the type the facts are of.
    ///
    /// @param declared its name as the type declares it
    /// @param name its name in the metamodel: `declared`, or renamed
    /// @param witness the parameter of the constructor of the metamodel that takes its token, and the
    ///     field of the same name
    record TypeVar(String declared, String name, String witness) {}

    /// How the type parameters and parameters of a [Spec.Factory] are named.
    sealed interface Locals {

        /// Under names no member has, starting with [MemberPlan#PROBE]: for
        /// the source the names the metamodel uses itself are read off, which
        /// what is local to a method is not among.
        record Probe() implements Locals {}

        /// As the generic methods name their type parameters, and after them.
        ///
        /// @param typeNames the simple names of the classes the metamodel refers to, which a type
        ///     parameter of the same name would hide
        /// @param taken the names the body of the metamodel starts a name with, which a parameter of
        ///     the same name would hide
        record Named(Set<String> typeNames, Set<String> taken) implements Locals {}
    }

    private final Self self;
    private final Targets targets;
    private final Locals locals;
    /// The classes the facts refer to by simple name apart from the class of the metamodel, as
    /// [Facts#uses] tells; each [#specs] adds to it.
    private final Set<ClassDesc> uses = new LinkedHashSet<>();

    private MemberFacts(Self self, Targets targets, Locals locals) {
        this.self = self;
        this.targets = targets;
        this.locals = locals;
    }

    /// The facts of a plan.
    ///
    /// @param self the type of the metamodel
    /// @param targets the metamodels of the types the signatures mention
    /// @param locals how what is local to a factory is named
    /// @param plan the plan
    /// @return the facts
    static Facts of(Self self, Targets targets, Locals locals, MemberPlan plan) {
        MemberFacts facts = new MemberFacts(self, targets, locals);
        List<Spec> specs = facts.specs(plan);
        return new Facts(specs, facts.uses);
    }

    /// The facts of a plan, and what they refer to.
    ///
    /// @param specs the facts: enum constants in declaration order, then fields, constructors, methods
    ///     — each sorted by name — and `sam`
    /// @param uses the classes the facts refer to by simple name apart from the class of the
    ///     metamodel: the fact classes, `MemberTraits`, the nested `Data` classes of other metamodels
    ///     and the markers of primitives
    record Facts(List<Spec> specs, Set<ClassDesc> uses) {

        /// Whether a factory is an instance method, which reads the tokens of
        /// the type parameters off the fields of the metamodel.
        ///
        /// @return `true` if the metamodel is to keep the tokens its constructor takes
        boolean instanceFactories() {
            return specs.stream().anyMatch(spec -> spec instanceof Spec.Factory factory && !factory.isStatic());
        }
    }

    private List<Spec> specs(MemberPlan plan) {
        return Stream.of(
                        plan.enumConstants().stream()
                                .<Spec>map(constant -> new Spec.Constant(
                                        constant.name(),
                                        parameterized(family("EnumConstant"), Types.of(self.desc())),
                                        Exprs.field(MetamodelNames.TOKEN)
                                                .call("constant", Exprs.literal(constant.constant())))),
                        plan.members().stream()
                                .sorted(Comparator.comparingInt(MemberFacts::group)
                                        .thenComparing(MemberPlan.Fact::name))
                                .map(fact -> switch (fact.model()) {
                                    case FieldModel field -> field(fact.name(), field);
                                    case CtorModel ctor -> ctor(fact.name(), ctor);
                                    case MethodModel method -> method(fact.name(), method);
                                }),
                        plan.sam().stream().map(this::sam))
                .flatMap(Function.identity())
                .toList();
    }

    private static int group(MemberPlan.Fact fact) {
        return switch (fact.model()) {
            case FieldModel _ -> 0;
            case CtorModel _ -> 1;
            case MethodModel _ -> 2;
        };
    }

    // ---- scopes

    /// A type variable in scope.
    ///
    /// @param name its name in the metamodel
    /// @param witness the token of the type it stands for
    /// @param position its position among the type parameters of the type, or -1 for one of a method
    private record Var(String name, Expr witness, int position) {}

    /// Where a fact is made.
    ///
    /// @param vars the type variables in scope, by declared name; those of a method hide those of the type
    /// @param instance whether the fact is made for an instance of a generic metamodel, where `token`
    ///     is the type applied to its own type parameters
    private record Scope(Map<String, Var> vars, boolean instance) {

        Var var(String declared) {
            Var found = vars.get(declared);
            if (found == null) {
                throw new IllegalStateException("type variable " + declared + " is not in scope");
            }
            return found;
        }
    }

    /// The scope of a member without type parameters of its own.
    private Scope scope(boolean isStatic) {
        boolean instance = self.generic() && !isStatic;
        Map<String, Var> vars = instance
                ? IntStream.range(0, self.typeVars().size())
                        .boxed()
                        .collect(Collectors.toMap(
                                i -> self.typeVars().get(i).declared(),
                                i -> new Var(
                                        self.typeVars().get(i).name(),
                                        Exprs.field(self.typeVars().get(i).witness()),
                                        i)))
                : Map.of();
        return new Scope(vars, instance);
    }

    /// The token that owns a member: `TOKEN`, or, of a generic type, `token`
    /// for an instance member and `ANY` for a `static` one.
    private Expr owner(boolean isStatic) {
        if (!self.generic()) {
            return Exprs.field(MetamodelNames.TOKEN);
        }
        return Exprs.field(isStatic ? MetamodelNames.ANY : MetamodelNames.INSTANCE_TOKEN);
    }

    /// The type itself as the facts of its instance members are typed by it:
    /// applied to its own type parameters.
    private TypeRef ownerType() {
        if (!self.generic()) {
            return Types.of(self.desc());
        }
        return new ParameterizedTypeRef(
                self.desc(),
                self.typeVars().stream()
                        .<TypeArg>map(typeVar -> Types.exact(Types.typeVar(typeVar.name())))
                        .toList());
    }

    /// A field for a member without type parameters of its own: assigned by
    /// the constructor for an instance member of a generic type.
    private Spec value(String name, TypeRef type, Expr init, Scope scope) {
        return scope.instance() ? new Spec.Assigned(name, type, init) : new Spec.Constant(name, type, init);
    }

    // ---- fields

    private Spec field(String name, FieldModel field) {
        Scope scope = scope(field.isStatic());
        TypeRef type = phantom(field.type(), scope);
        TypeRef owner = ownerType();
        Expr token = token(field.type(), scope);
        Expr nameLiteral = Exprs.literal(field.name());
        Expr owned = owner(field.isStatic());
        if (field.isStatic()) {
            return switch (field.mutability()) {
                case FieldModel.Mutability.Constant constant ->
                    value(
                            name,
                            parameterized(family("StaticFieldRef"), type),
                            unsafe("constantField", owned, nameLiteral, token, constantValue(constant.value())),
                            scope);
                case FieldModel.Mutability.Final _ ->
                    value(
                            name,
                            parameterized(family("StaticFieldRef"), type),
                            unsafe("staticField", owned, nameLiteral, token),
                            scope);
                case FieldModel.Mutability.Mutable _ ->
                    value(
                            name,
                            parameterized(family("MutableStaticFieldRef"), type),
                            unsafe("mutableStaticField", owned, nameLiteral, token),
                            scope);
            };
        }
        return switch (field.mutability()) {
            case FieldModel.Mutability.Mutable _ ->
                value(
                        name,
                        parameterized(family("MutableFieldRef"), owner, type),
                        unsafe("mutableField", owned, nameLiteral, token),
                        scope);
            case FieldModel.Mutability.Final _ ->
                value(
                        name,
                        parameterized(family("FieldRef"), owner, type),
                        unsafe("field", owned, nameLiteral, token),
                        scope);
            case FieldModel.Mutability.Constant _ ->
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
        if (!ctor.typeParams().isEmpty()) {
            throw new IllegalStateException("a generic constructor has no fact: " + name);
        }
        Scope scope = scope(false);
        boolean isAbstract = self.kind() instanceof DeclaredKind.AbstractClass;
        int arity = ctor.params().size();
        List<TypeRef> typeArgs = Stream.concat(
                        Stream.of(ownerType()), ctor.params().stream().map(param -> phantom(param, scope)))
                .toList();
        List<Expr> args = Stream.of(
                        Stream.of(owner(false)),
                        params(ctor.params(), ctor.declared(), scope),
                        Stream.of(traits(Overridability.FINAL, ctor.throwsTypes(), List.of(), scope)))
                .flatMap(Function.identity())
                .toList();
        String family = (isAbstract ? "AbstractCtorRef" : "CtorRef") + requireArity(arity);
        return value(
                name,
                parameterized(family(family), typeArgs),
                unsafe(isAbstract ? "abstractCtor" : "ctor", args),
                scope);
    }

    private Spec method(String name, MethodModel method) {
        if (method.typeParams().isEmpty()) {
            Scope scope = scope(method.isStatic());
            return value(name, methodType(method, scope), methodFact(method, List.of(), scope), scope);
        }
        Scope outer = scope(method.isStatic());
        List<String> declared =
                method.typeParams().stream().map(TypeParam::name).toList();
        LocalNames local =
                switch (locals) {
                    case Locals.Probe _ ->
                        new LocalNames(numbered("T", declared.size()), numbered("w", declared.size()));
                    case Locals.Named(Set<String> typeNames, Set<String> taken) -> {
                        Set<String> hidden = outer.instance()
                                ? union(typeNames, self.typeVars().stream().map(TypeVar::name))
                                : typeNames;
                        Set<String> avoided = outer.instance()
                                ? union(taken, self.typeVars().stream().map(TypeVar::witness))
                                : taken;
                        List<String> names = MetamodelNames.typeParameters(declared, hidden);
                        yield new LocalNames(names, MetamodelNames.witnesses(names, avoided));
                    }
                };
        List<String> names = local.typeParams();
        List<Expr> witnessTokens =
                local.witnesses().stream().<Expr>map(Exprs::field).toList();
        List<Witness> witnesses = IntStream.range(0, declared.size())
                .mapToObj(i -> new Witness(
                        local.witnesses().get(i), parameterized(use(CD_REF_TOKEN), Types.typeVar(names.get(i)))))
                .toList();
        Map<String, Var> vars = Stream.concat(
                        outer.vars().entrySet().stream(),
                        IntStream.range(0, declared.size())
                                .mapToObj(i ->
                                        Map.entry(declared.get(i), new Var(names.get(i), witnessTokens.get(i), -1))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (_, inner) -> inner));
        Scope scope = new Scope(vars, outer.instance());
        List<TypeParam> typeParams = IntStream.range(0, declared.size())
                .mapToObj(i -> new TypeParam(
                        names.get(i),
                        method.typeParams().get(i).bounds().stream()
                                .map(bound -> (ClassOrInterfaceTypeRef) javaType(bound, scope))
                                .toList()))
                .toList();
        return new Spec.Factory(
                name,
                !outer.instance(),
                typeParams,
                witnesses,
                methodType(method, scope),
                methodFact(method, witnessTokens, scope));
    }

    /// The names of the type parameters of a generic method and of its parameters.
    ///
    /// @param typeParams the type parameters, in order
    /// @param witnesses the parameters, a token per type parameter
    private record LocalNames(List<String> typeParams, List<String> witnesses) {}

    private static Set<String> union(Set<String> names, Stream<String> more) {
        return Stream.concat(names.stream(), more).collect(Collectors.toSet());
    }

    private static List<String> numbered(String kind, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> MemberPlan.PROBE + kind + i)
                .toList();
    }

    /// The phantom types of the result and the parameters of a method.
    private Stream<TypeRef> phantoms(MethodModel method, Scope scope) {
        return Stream.concat(
                method.result().stream().map(result -> phantom(result, scope)),
                method.params().stream().map(param -> phantom(param, scope)));
    }

    private TypeRef methodType(MethodModel method, Scope scope) {
        List<TypeRef> typeArgs = Stream.concat(
                        Stream.of(ownerType()).filter(_ -> !method.isStatic()), phantoms(method, scope))
                .toList();
        String family = (method.result().isEmpty() ? "Void" : "")
                + (method.isStatic() ? "Static" : "")
                + "MethodRef"
                + requireArity(method.params().size());
        return parameterized(family(family), typeArgs);
    }

    /// The `UnsafeFacts.method(owner, "name", result, params…, traits)` of a
    /// method, or the factory of its kind.
    private Expr methodFact(MethodModel method, List<Expr> witnesses, Scope scope) {
        List<Expr> args = Stream.of(
                        Stream.of(owner(method.isStatic()), Exprs.literal(method.name())),
                        method.result().stream().map(result -> token(result, scope)),
                        params(method.params(), method.declared(), scope),
                        Stream.of(traits(method.overridability(), method.throwsTypes(), witnesses, scope)))
                .flatMap(Function.identity())
                .toList();
        String factory = method.isStatic() ? "staticMethod" : "method";
        return unsafe(method.result().isEmpty() ? "void" + capitalized(factory) : factory, args);
    }

    /// The parameters of a method or constructor as its fact takes them: the
    /// token of each, and how the member declares it where that is not how
    /// the token erases — a type variable, or an array of one, which erases
    /// with what it stands for. The declaration tells the fact from another
    /// overload that takes the same tokens.
    private Stream<Expr> params(List<TypeRef> params, List<MethodTableTemplate.Param> declared, Scope scope) {
        return IntStream.range(0, params.size()).mapToObj(i -> {
            Expr token = token(params.get(i), scope);
            return erasesWithItsVariable(params.get(i))
                    ? unsafe("param", token, MetamodelEmitter.param(declared.get(i)))
                    : token;
        });
    }

    private static boolean erasesWithItsVariable(TypeRef type) {
        return switch (type) {
            case TypeVarRef _ -> true;
            case ArrayTypeRef array -> erasesWithItsVariable(array.component());
            case PrimitiveTypeRef _, ClassTypeRef _, ParameterizedTypeRef _ -> false;
        };
    }

    private Spec sam(MemberPlan.SamFact sam) {
        MethodModel method = sam.method();
        Scope scope = scope(false);
        List<TypeRef> typeArgs =
                Stream.concat(Stream.of(ownerType()), phantoms(method, scope)).toList();
        boolean isVoid = method.result().isEmpty();
        String family =
                (isVoid ? "VoidSam" : "Sam") + requireArity(method.params().size());
        Expr fact = sam.reuse().<Expr>map(Exprs::field).orElseGet(() -> methodFact(method, List.of(), scope));
        return value("sam", parameterized(family(family), typeArgs), unsafe(isVoid ? "voidSam" : "sam", fact), scope);
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

    /// The traits of a method or constructor: what it throws — a type
    /// variable by the token given for it — and, for a generic method, the
    /// tokens of its type arguments.
    private Expr traits(
            Overridability overridability,
            List<ClassOrInterfaceTypeRef> throwsTypes,
            List<Expr> witnesses,
            Scope scope) {
        Expr traits = Exprs.staticField(use(CD_MEMBER_TRAITS), overridability.name());
        if (!throwsTypes.isEmpty()) {
            traits = traits.call(
                    "throwing", throwsTypes.stream().map(t -> token(t, scope)).toList());
        }
        return witnesses.isEmpty() ? traits : traits.call("withTypeArgs", witnesses);
    }

    // ---- tokens and their phantom types

    /// The token of a type: a primitive's constant, the array token of an
    /// array, the token given for a type variable, and for a class or
    /// interface the token of the type itself or one made from the shape of
    /// its metamodel.
    private Expr token(TypeRef type, Scope scope) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> Exprs.staticField(use(CD_PRIMITIVE_TOKEN), primitive.name());
            case ArrayTypeRef array ->
                array.component() instanceof PrimitiveTypeRef
                        ? token(array.component(), scope).call("array")
                        : Exprs.staticCall(use(CD_ARRAY_TOKEN), "of", token(array.component(), scope));
            case ClassTypeRef cls -> declaredToken(cls, cls.desc(), List.of(), scope);
            case ParameterizedTypeRef parameterized ->
                declaredToken(parameterized, parameterized.raw(), parameterized.args(), scope);
            case TypeVarRef variable -> scope.var(variable.name()).witness();
        };
    }

    private Expr declaredToken(ClassOrInterfaceTypeRef type, ClassDesc desc, List<TypeArg> args, Scope scope) {
        if (desc.equals(self.desc())) {
            if (!self.generic()) {
                return Exprs.field(MetamodelNames.TOKEN);
            }
            if (scope.instance() && ownTypeParameters(args, scope)) {
                return Exprs.field(MetamodelNames.INSTANCE_TOKEN);
            }
        }
        Targets.Target target =
                targets.of(desc).orElseThrow(() -> new IllegalStateException("no metamodel of " + desc.displayName()));
        ClassDesc data = use(target.metamodel().nested(MetamodelNames.DATA));
        List<Expr> tokenArgs = Stream.concat(
                        Stream.of(Exprs.staticField(data, MetamodelNames.SHAPE)),
                        args.stream().map(arg -> tokenArg(arg, scope)))
                .toList();
        return new StaticMethodCallExpr(
                Types.of(use(CD_UNSAFE_FACTS)), factory(target.kind()), tokenArgs, List.of(javaType(type, scope)));
    }

    /// Whether type arguments are the type parameters of the type itself, in order.
    private static boolean ownTypeParameters(List<TypeArg> args, Scope scope) {
        return !args.isEmpty()
                && IntStream.range(0, args.size())
                        .allMatch(i -> args.get(i) instanceof ExactTypeArg(TypeRef exact)
                                && exact instanceof TypeVarRef variable
                                && scope.var(variable.name()).position() == i);
    }

    private Expr tokenArg(TypeArg arg, Scope scope) {
        ClassDesc tokenArg = use(CD_TOKEN_ARG);
        return switch (arg) {
            case ExactTypeArg exact -> Exprs.staticCall(tokenArg, "exact", token(exact.type(), scope));
            case ExtendsTypeArg bound -> Exprs.staticCall(tokenArg, "extendsBound", token(bound.bound(), scope));
            case SuperTypeArg bound -> Exprs.staticCall(tokenArg, "superBound", token(bound.bound(), scope));
            case UnboundedTypeArg _ -> Exprs.staticCall(tokenArg, "unbounded");
        };
    }

    private static String factory(DeclaredKind kind) {
        return switch (kind) {
            case DeclaredKind.FinalClass _ -> "finalClassToken";
            case DeclaredKind.OpenClass _ -> "openClassToken";
            case DeclaredKind.AbstractClass _ -> "abstractClassToken";
            case DeclaredKind.Interface _ -> "interfaceToken";
            case DeclaredKind.EnumClass _ -> "enumToken";
        };
    }

    /// The type a token is typed by: the marker of a primitive, the type
    /// itself otherwise.
    private TypeRef phantom(TypeRef type, Scope scope) {
        if (type instanceof PrimitiveTypeRef primitive) {
            return Types.of(use(ClassDesc.of(FACTS, "Prim$" + marker(primitive))));
        }
        return javaType(type, scope);
    }

    /// A type as the metamodel writes it: its type variables under their
    /// names in the metamodel.
    private TypeRef javaType(TypeRef type, Scope scope) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> primitive;
            case ArrayTypeRef array -> Types.array(javaType(array.component(), scope));
            case ClassTypeRef cls -> cls;
            case ParameterizedTypeRef parameterized ->
                new ParameterizedTypeRef(
                        parameterized.raw(),
                        parameterized.args().stream()
                                .map(arg -> javaTypeArg(arg, scope))
                                .toList());
            case TypeVarRef variable -> Types.typeVar(scope.var(variable.name()).name());
        };
    }

    private TypeArg javaTypeArg(TypeArg arg, Scope scope) {
        return switch (arg) {
            case ExactTypeArg exact -> Types.exact(javaType(exact.type(), scope));
            case ExtendsTypeArg bound -> Types.extendsBound(javaType(bound.bound(), scope));
            case SuperTypeArg bound -> Types.superBound(javaType(bound.bound(), scope));
            case UnboundedTypeArg unbounded -> unbounded;
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
