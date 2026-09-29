package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodTableTemplate;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.TypeShape;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;

import javax.annotation.processing.Generated;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/// Shorthands of the hand-written metamodel: the [TypeShape] of a JDK type,
/// read by reflection once per type, and its tokens.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
final class Jdk {
    /// The public, non-final methods of `Object` every class inherits.
    private static final Set<Signature> OBJECT_METHODS = Set.of(
            Signature.of("equals", Param.fixed(ConstantDescs.CD_Object)),
            Signature.of("hashCode"),
            Signature.of("toString"));

    private static final ClassValue<TypeShape<DeclaredKind.FinalClass>> FINAL_CLASSES =
            shapes(DeclaredKind.FINAL_CLASS);
    private static final ClassValue<TypeShape<DeclaredKind.OpenClass>> OPEN_CLASSES = shapes(DeclaredKind.OPEN_CLASS);
    private static final ClassValue<TypeShape<DeclaredKind.Interface>> INTERFACES = shapes(DeclaredKind.INTERFACE);

    private Jdk() {}

    static <T> FinalClassToken<T> finalClass(Class<T> type) {
        return UnsafeFacts.finalClassToken(FINAL_CLASSES.get(type));
    }

    static <T> OpenClassToken<T> openClass(Class<T> type) {
        return UnsafeFacts.openClassToken(OPEN_CLASSES.get(type));
    }

    static <T> InterfaceToken<T> iface(Class<T> type) {
        return UnsafeFacts.interfaceToken(INTERFACES.get(type));
    }

    /// An interface token of a parameterized type, e.g. `List<E>`; the
    /// caller's signature vouches for the phantom.
    static <T> InterfaceToken<T> iface(Class<?> raw, TokenArg... args) {
        return UnsafeFacts.interfaceToken(INTERFACES.get(raw), args);
    }

    /// A class token of a parameterized type, e.g. `ArrayList<E>`; the
    /// caller's signature vouches for the phantom.
    static <T> OpenClassToken<T> openClass(Class<?> raw, TokenArg... args) {
        return UnsafeFacts.openClassToken(OPEN_CLASSES.get(raw), args);
    }

    private static <K extends DeclaredKind> ClassValue<TypeShape<K>> shapes(K kind) {
        return new ClassValue<>() {
            @Override
            protected TypeShape<K> computeValue(Class<?> type) {
                return shape(kind, type);
            }
        };
    }

    /// Reads the shape of a JDK type by reflection, vouched for by hand.
    ///
    /// A stand-in for what the `@Facts` processor computes from
    /// `javax.lang.model`. A type that is not generic records no supertypes.
    private static <K extends DeclaredKind> TypeShape<K> shape(K kind, Class<?> type) {
        Supertypes supertypes = type.getTypeParameters().length > 0 ? supertypes(type) : Supertypes.NONE;
        return UnsafeFacts.shape(
                kind,
                desc(type),
                typeParameters(type),
                chain(type),
                supertypes,
                methods(type, supertypes),
                List.of(),
                type.isSealed());
    }

    private static List<ClassDesc> chain(Class<?> type) {
        List<ClassDesc> chain = new ArrayList<>();
        for (Class<?> c = type.getSuperclass(); c != null; c = c.getSuperclass()) {
            chain.add(desc(c));
        }
        return chain;
    }

    private static ClassDesc desc(Class<?> type) {
        return ClassDesc.ofDescriptor(type.descriptorString());
    }

    private static Map<String, TypeRef> ownVariables(Class<?> type) {
        Map<String, TypeRef> env = new HashMap<>();
        for (TypeVariable<?> var : type.getTypeParameters()) {
            env.put(var.getName(), Types.typeVar(var.getName()));
        }
        return env;
    }

    private static List<TypeParam> typeParameters(Class<?> type) {
        Map<String, TypeRef> env = ownVariables(type);
        return Arrays.stream(type.getTypeParameters())
                .map(var -> new TypeParam(
                        var.getName(),
                        Arrays.stream(var.getBounds())
                                .filter(bound -> bound != Object.class)
                                .map(bound -> (ClassOrInterfaceTypeRef) typeRef(bound, env))
                                .toList()))
                .toList();
    }

    /// Reads the method table template of a JDK type by reflection, statics
    /// included: a
    /// parameter typed by a type variable that stands for a type parameter
    /// of `type` refers to it, every other parameter is erased.
    private static MethodTableTemplate methods(Class<?> type, Supertypes supertypes) {
        Set<Signature> abstracts = new HashSet<>();
        Set<Signature> concretes = new HashSet<>(OBJECT_METHODS);
        Set<Signature> statics = new HashSet<>();
        for (Method method : type.getMethods()) {
            List<Param> params = new ArrayList<>();
            for (int i = 0; i < method.getParameterCount(); i++) {
                params.add(param(type, supertypes, method, i));
            }
            Signature signature = new Signature(method.getName(), params);
            if (Modifier.isStatic(method.getModifiers())) {
                statics.add(signature);
            } else if (Modifier.isAbstract(method.getModifiers())) {
                if (!OBJECT_METHODS.contains(signature)) {
                    abstracts.add(signature);
                }
            } else {
                concretes.add(signature);
            }
        }
        concretes.removeAll(abstracts);
        return new MethodTableTemplate(abstracts, concretes, statics);
    }

    private static Param param(Class<?> type, Supertypes supertypes, Method method, int i) {
        return method.getGenericParameterTypes()[i] instanceof TypeVariable<?> var
                        && var.getGenericDeclaration() instanceof Class<?> declaring
                ? typeParameterIndex(type, supertypes, declaring, var)
                        .<Param>map(Param::var)
                        .orElseGet(() -> Param.fixed(desc(method.getParameterTypes()[i])))
                : Param.fixed(desc(method.getParameterTypes()[i]));
    }

    /// The position of the type parameter of `type` that the type variable
    /// `var` of its supertype `declaring` stands for, if one does.
    private static Optional<Integer> typeParameterIndex(
            Class<?> type, Supertypes supertypes, Class<?> declaring, TypeVariable<?> var) {
        List<String> own = Arrays.stream(type.getTypeParameters())
                .map(TypeVariable::getName)
                .toList();
        if (declaring == type) {
            return Optional.of(own.indexOf(var.getName()));
        }
        if (own.isEmpty()) {
            return Optional.empty();
        }
        int position = Arrays.asList(declaring.getTypeParameters()).indexOf(var);
        return supertypes
                .supertype(desc(declaring))
                .map(supertype -> supertype.args().get(position))
                .flatMap(arg -> arg instanceof ExactTypeArg(TypeVarRef named)
                        ? Optional.of(own.indexOf(named.name()))
                        : Optional.empty());
    }

    /// Reads the parameterized supertypes of a generic JDK type by
    /// reflection, in terms of its type parameters.
    private static Supertypes supertypes(Class<?> type) {
        Map<ClassDesc, ParameterizedTypeRef> found = new LinkedHashMap<>();
        collectSupertypes(type, ownVariables(type), found);
        return new Supertypes(
                Arrays.stream(type.getTypeParameters())
                        .map(v -> Types.typeVar(v.getName()))
                        .toList(),
                List.copyOf(found.values()));
    }

    private static void collectSupertypes(
            Class<?> type, Map<String, TypeRef> env, Map<ClassDesc, ParameterizedTypeRef> found) {
        List<Type> direct = new ArrayList<>(Arrays.asList(type.getGenericInterfaces()));
        if (type.getGenericSuperclass() != null) {
            direct.addFirst(type.getGenericSuperclass());
        }
        for (Type supertype : direct) {
            if (supertype instanceof ParameterizedType parameterized) {
                Class<?> raw = (Class<?>) parameterized.getRawType();
                ParameterizedTypeRef ref = (ParameterizedTypeRef) typeRef(parameterized, env);
                found.putIfAbsent(desc(raw), ref);
                Map<String, TypeRef> inner = new HashMap<>();
                TypeVariable<?>[] vars = raw.getTypeParameters();
                for (int i = 0; i < vars.length; i++) {
                    inner.put(vars[i].getName(), typeRef(parameterized.getActualTypeArguments()[i], env));
                }
                collectSupertypes(raw, inner, found);
            } else if (supertype instanceof Class<?> cls && cls.getTypeParameters().length == 0) {
                // a raw use of a generic type has only erased supertypes
                collectSupertypes(cls, Map.of(), found);
            }
        }
    }

    private static TypeRef typeRef(Type type, Map<String, TypeRef> env) {
        return switch (type) {
            case Class<?> cls when cls.isArray() -> Types.array(typeRef(cls.getComponentType(), env));
            case Class<?> cls -> Types.of(cls);
            case TypeVariable<?> var -> {
                TypeRef bound = env.get(var.getName());
                if (bound == null) {
                    throw new IllegalArgumentException("unbound type variable " + var.getName());
                }
                yield bound;
            }
            case ParameterizedType parameterized ->
                Types.parameterized(
                        desc((Class<?>) parameterized.getRawType()),
                        Arrays.stream(parameterized.getActualTypeArguments())
                                .map(a -> typeArg(a, env))
                                .toList());
            case GenericArrayType array -> Types.array(typeRef(array.getGenericComponentType(), env));
            default -> throw new IllegalArgumentException("unsupported type " + type);
        };
    }

    private static TypeArg typeArg(Type type, Map<String, TypeRef> env) {
        if (!(type instanceof WildcardType wildcard)) {
            return Types.exact(typeRef(type, env));
        }
        if (wildcard.getLowerBounds().length > 0) {
            return Types.superBound(typeRef(wildcard.getLowerBounds()[0], env));
        }
        Type upper = wildcard.getUpperBounds()[0];
        return upper == Object.class ? Types.unbounded() : Types.extendsBound(typeRef(upper, env));
    }
}
