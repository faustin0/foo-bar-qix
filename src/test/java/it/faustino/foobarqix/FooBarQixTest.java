package it.faustino.foobarqix;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FooBarQixTest {

    FooBarQix sut;

    @BeforeEach
    void setUp() {
        sut = new FooBarQix();
    }

    @Test
    void shouldCreateFooBarQix() {
        assertThat(sut).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9, 12})
    void shouldWriteFoo(int param) {
        String result = sut.emit(param);
        assertThat(result).isEqualTo("Foo");

    }
}
