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

import static it.faustino.foobarqix.Predicates.containsNumber;
import static it.faustino.foobarqix.Predicates.isDivisibleBy;
import static it.faustino.foobarqix.Functions.replace;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FooBarQixTest {

    FooBarQix sut;

    @Mock
    Predicate<Integer> mockIntPredicate;
    @Mock
    Function<Integer, String> mockFun;

    Function<Integer, String> writeFoo = s -> "Foo";

    @BeforeEach
    void setUp() {
        Function<Integer, String> addBar = s -> "Bar";
        Function<Integer, String> addQuix = s -> "Qix";
        sut = FooBarQix
                .create()
                .when(isDivisibleBy(3), writeFoo)
                .when(isDivisibleBy(5), addBar)
                .when(isDivisibleBy(7), addQuix)
                .when(containsNumber(3), replace(3, "Foo"))
                .when(containsNumber(5), replace(5, "Bar"))
                .when(containsNumber(7), replace(7, "Qix"));
    }

    @Test
    void shouldCreateFooBarQix() {
        assertThat(sut).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    void shouldWriteTheNumber(int number) {
        String result = sut.compute(number);
        assertThat(result).isEqualTo(String.valueOf(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9, 12})
    void shouldWrite_Foo_divisibleByThree(int number) {
        sut = FooBarQix
                .create()
                .when(isDivisibleBy(3), writeFoo);
        String result = sut.compute(number);
        assertThat(result).isEqualTo("Foo");
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20})
    void shouldWrite_Bar_divisibleByFive(int number) {
        String result = sut.compute(number);
        assertThat(result).isEqualTo("Bar");
    }

    @ParameterizedTest
    @ValueSource(ints = {14, 28})
    void shouldWrite_Qix(int number) {
        String result = sut.compute(number);
        assertThat(result).isEqualTo("Qix");
    }

    @ParameterizedTest
    @ValueSource(ints = {21})
    void shouldAdd_Qix(int number) {
        String result = sut.compute(number);
        assertThat(result).isEqualTo("FooQix");
    }

    @Test
    void shouldReturn_BarBar() {
        String result = sut.compute(5);
        assertThat(result).isEqualTo("BarBar");
    }

    @Test
    void shouldReturn_QixQix() {
        String result = sut.compute(7);
        assertThat(result).isEqualTo("QixQix");
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 45})
    void shouldReturn_FooBarBar(int number) {
        String result = sut.compute(number);
        assertThat(result).isEqualTo("FooBarBar");
    }

    @Test
    void shouldReturn_FooQixFooBarQix() {
        String result = sut.compute(357);
        assertThat(result).isEqualTo("FooQixFooBarQix");
    }


    @Test
    void shouldReturn_FooFooFoo() {
        String result = sut.compute(33);
        assertThat(result).isEqualTo("FooFooFoo");
    }

    @Test
    void shouldExecuteRegisteredDecorations() {
        Mockito.doReturn(true).
                when(mockIntPredicate)
                .test(Mockito.any());

        Mockito.doReturn("").
                when(mockFun)
                .apply(Mockito.any());

        FooBarQix.create()
                .when(mockIntPredicate, mockFun)
                .compute(5);

        Mockito.verify(mockIntPredicate).test(Mockito.any());
        Mockito.verify(mockFun).apply(Mockito.any());
    }

    @Test
    void shouldBeTrue_IfContainsNumber() {
        int input = 35643;
        int toSearch = 3;
        boolean test = containsNumber(toSearch).test(input);
        assertThat(test).isTrue();
    }

    @Test
    void shouldBeFalse_IfNotContainsNumber() {
        int input = 35643;
        int toSearch = 1;
        boolean test = containsNumber(toSearch).test(input);
        assertThat(test).isFalse();
    }
}
