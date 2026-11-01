package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.literal;

/// The orders in which the slot of an initializer block is found beside the other declared members, in a class
/// and in an enum: a block defined before the fields, the methods and the other block are defined, and fields
/// initialized while a block still waits for its body.
class InitializerBlockCoverageTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    @Test
    void aClassFindsItsBlocksAmongEveryKindOfSlot() {
        TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                var s = cb.declareStaticField("S", PrimitiveToken.INT);
                var f = cb.declareMutableField("f", PrimitiveToken.INT);
                var ctor = cb.declareConstructor();
                var staticBlock = cb.declareStaticInitializerBlock();
                var instanceBlock = cb.declareInitializerBlock();
                var s2 = cb.declareStaticField("S2", PrimitiveToken.INT);
                var f2 = cb.declareMutableField("f2", PrimitiveToken.INT);
                var method = cb.declareVoidMethod("m");
                var bodies = cb.bodies();
                bodies.initialize(s, literal(1));
                bodies.initialize(f, literal(2));
                bodies.initialize(s2, literal(3));
                bodies.initialize(f2, literal(4));
                bodies.define(method, (b, _) -> b.end());
                bodies.define(instanceBlock, (b, _) -> b.end());
                bodies.define(staticBlock, b -> b.end());
                bodies.define(ctor, (b, _) -> b.end());
                return bodies;
            }
        });
    }

    @Test
    void anEnumFindsItsBlocksAmongEveryKindOfSlot() {
        TypedJavaFile.enum_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedEnumSpec() {
            @Override
            public <Self> TypedEnumBodies<Self> build(TypedEnumBuilder<Self> eb) {
                eb.constant("A");
                var s = eb.declareStaticField("S", PrimitiveToken.INT);
                var f = eb.declareField(
                        "f", PrimitiveToken.INT, java.util.Set.of(me.supcheg.javafile.model.Modifier.PUBLIC));
                var staticBlock = eb.declareStaticInitializerBlock();
                var instanceBlock = eb.declareInitializerBlock();
                var s2 = eb.declareStaticField("S2", PrimitiveToken.INT);
                var f2 = eb.declareField(
                        "f2", PrimitiveToken.INT, java.util.Set.of(me.supcheg.javafile.model.Modifier.PUBLIC));
                var method = eb.declareVoidMethod("m");
                var bodies = eb.bodies();
                bodies.initialize(s, literal(1));
                bodies.initialize(f, literal(2));
                bodies.initialize(s2, literal(3));
                bodies.initialize(f2, literal(4));
                bodies.define(method, (b, _) -> b.end());
                bodies.define(instanceBlock, (b, _) -> b.end());
                bodies.define(staticBlock, b -> b.end());
                return bodies;
            }
        });
    }
}
