package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.Supertypes;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeRef;
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
import java.util.Set;

/// Shorthands of the hand-written metamodel.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
final class Jdk {
    static final MemberTraits OVERRIDABLE = MemberTraits.DEFAULT;
    static final MemberTraits FINAL = MemberTraits.DEFAULT.with(Overridability.FINAL);
    static final MemberTraits ABSTRACT = MemberTraits.DEFAULT.with(Overridability.ABSTRACT);

    /// The public, non-final methods of `Object` every class inherits.
    static final Set<MethodSignature> OBJECT_METHODS = Set.of(
            new MethodSignature("equals", List.of(ConstantDescs.CD_Object)),
            new MethodSignature("hashCode", List.of()),
            new MethodSignature("toString", List.of()));

    private Jdk() {}

    static List<ClassDesc> chain(Class<?> type) {
        List<ClassDesc> chain = new ArrayList<>();
        for (Class<?> c = type.getSuperclass(); c != null; c = c.getSuperclass()) {
            chain.add(desc(c));
        }
        return chain;
    }

    static ClassDesc desc(Class<?> type) {
        return ClassDesc.ofDescriptor(type.descriptorString());
    }

    /// Reads the method table of a JDK type by reflection, substituting the
    /// erasures of the given type-variable tokens by variable name.
    ///
    /// A stand-in for what metagen computes from `Signature` attributes.
    static MethodTable methods(Class<?> type, Map<String, TypeToken<?>> vars) {
        Set<MethodSignature> abstracts = new HashSet<>();
        Set<MethodSignature> concretes = new HashSet<>(OBJECT_METHODS);
        for (Method method : type.getMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            List<ClassDesc> params = new ArrayList<>();
            Type[] generic = method.getGenericParameterTypes();
            for (int i = 0; i < generic.length; i++) {
                params.add(
                        generic[i] instanceof TypeVariable<?> var && vars.containsKey(var.getName())
                                ? vars.get(var.getName()).erasure()
                                : desc(method.getParameterTypes()[i]));
            }
            MethodSignature signature = new MethodSignature(method.getName(), params);
            if (Modifier.isAbstract(method.getModifiers())) {
                if (!OBJECT_METHODS.contains(signature)) {
                    abstracts.add(signature);
                }
            } else {
                concretes.add(signature);
            }
        }
        concretes.removeAll(abstracts);
        return new MethodTable(abstracts, concretes);
    }

    /// Reads the parameterized supertypes of a generic JDK type by
    /// reflection, in terms of its type parameters.
    ///
    /// A stand-in for what metagen computes from `Signature` attributes.
    static Supertypes supertypes(Class<?> type) {
        Map<String, TypeRef> env = new HashMap<>();
        for (TypeVariable<?> var : type.getTypeParameters()) {
            env.put(var.getName(), Types.typeVar(var.getName()));
        }
        Map<ClassDesc, ParameterizedTypeRef> found = new LinkedHashMap<>();
        collectSupertypes(type, env, found);
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

    static <T> FinalClassToken<T> finalClass(Class<T> type) {
        return UnsafeFacts.finalClassToken(Types.of(type), chain(type), methods(type, Map.of()));
    }

    static <T> OpenClassToken<T> openClass(Class<T> type) {
        return UnsafeFacts.openClassToken(Types.of(type), chain(type), methods(type, Map.of()));
    }

    static <T> InterfaceToken<T> iface(Class<T> type) {
        return UnsafeFacts.interfaceToken(Types.of(type), methods(type, Map.of()));
    }

    /// An interface token of a parameterized type, e.g. `List<E>`; the
    /// caller's signature vouches for the phantom.
    static <T> InterfaceToken<T> iface(Class<?> raw, Map<String, TypeToken<?>> vars, TypeArg... args) {
        return UnsafeFacts.interfaceToken(
                Types.parameterized(desc(raw), List.of(args)), supertypes(raw), methods(raw, vars));
    }

    /// A class token of a parameterized type, e.g. `ArrayList<E>`; the
    /// caller's signature vouches for the phantom.
    static <T> OpenClassToken<T> openClass(Class<?> raw, Map<String, TypeToken<?>> vars, TypeArg... args) {
        return UnsafeFacts.openClassToken(
                Types.parameterized(desc(raw), List.of(args)), chain(raw), supertypes(raw), methods(raw, vars));
    }

    /// An exact type argument.
    static TypeArg arg(TypeToken<?> token) {
        return Types.exact(token.typeRef());
    }
}
