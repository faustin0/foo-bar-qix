package it.faustino.foobarqix;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FooBarQixTest {

    FooBarQix sut;

    @BeforeEach
    void setUp() {
        Predicate<Integer> isDivisibleByThree = n -> n % 3 == 0;
        Function<String, String> writeFoo = s -> "Foo";

        Predicate<Integer> isDivisibleByFive = n -> n % 5 == 0;
        Function<String, String> addBar = s -> s + "Bar";

        Predicate<Integer> isDivisibleBySeven = n -> n % 7 == 0;
        Function<String, String> addQuix = s -> s + "Qix";

        sut = FooBarQix
                .create()
                .when(isDivisibleByThree, writeFoo)
                .when(isDivisibleByFive, addBar)
                .when(isDivisibleBySeven, addQuix);
    }

    @Test
    void shouldCreateFooBarQix() {
        assertThat(sut).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9, 12})
    void shouldWriteFoo(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("Foo");

    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 20})
    void shouldWriteBar(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("Bar");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    void shouldWriteTheNumber(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo(String.valueOf(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 30})
    void shouldWriteFooBar(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("FooBar");
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 14, 28})
    void shouldWriteQix(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("Qix");
    }

    @ParameterizedTest
    @ValueSource(ints = {21})
    void shouldAddQix(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("FooQix");
    }

   /* @ParameterizedTest
    @ValueSource(ints = {3, 13, 23, 33})
    void shouldAddFooForEachThree(int number) {
        var result = sut.emit(number);
        String.valueOf(number).subSequence(0,1);
        assertThat(result).contains("Foo");
    }*/

    @Test
    void shouldExecuteRegisteredDecorations() {
        Predicate<Integer> mockPredicate = Mockito.mock(Predicate.class);
        Function mockFun = Mockito.mock(Function.class);

        Mockito.doReturn(true).
                when(mockPredicate)
                .test(Mockito.any());

        sut.when(mockPredicate, mockFun)
                .emit(5);

        Mockito.verify(mockPredicate).test(Mockito.any());
        Mockito.verify(mockFun).apply(Mockito.any());
    }
}
