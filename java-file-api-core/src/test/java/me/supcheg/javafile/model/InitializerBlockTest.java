package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InitializerBlockTest {

    @Test
    void staticFlagDistinguishesInstanceFromStaticBlock() {
        assertThat(new InitializerBlock(true, CodeBody.EMPTY).isStatic()).isTrue();
        assertThat(new InitializerBlock(false, CodeBody.EMPTY).isStatic()).isFalse();
    }
}
