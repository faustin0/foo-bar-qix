package it.faustino.foobarqix;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FooBarQixTest {

    FooBarQix sut;

    @Mock
    Predicate<String> mockStringPredicate;
    @Mock
    Predicate<Integer> mockIntPredicate;
    @Mock
    Function<Integer, String> mockFun;

    @BeforeEach
    void setUp() {
        Predicate<Integer> isDivisibleByThree = n -> n % 3 == 0;
        Function<Integer, String> writeFoo = s -> "Foo";

        Predicate<Integer> isDivisibleByFive = n -> n % 5 == 0;
        Function<Integer, String> addBar = s -> "Bar";

        Predicate<Integer> isDivisibleBySeven = n -> n % 7 == 0;
        Function<Integer, String> addQuix = s -> "Qix";

        Predicate<Integer> containsThree = n -> FooBarQix.containsNumber(3).test(n);
        Function<Integer, String> addFoo = s -> FooBarQix.decorateWith("3", "Foo").apply(s);

        sut = FooBarQix
                .create()
                .when(isDivisibleByThree, writeFoo)
                .when(isDivisibleByFive, addBar)
                .when(isDivisibleBySeven, addQuix)
                .when(containsThree, addFoo);
    }

    @AfterEach
    void tearDown() {
        sut = FooBarQix.create();
    }

    @Test
    void shouldCreateFooBarQix() {
        assertThat(sut).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4})
    void shouldWriteTheNumber(int number) {
        String result = sut.emit(number);
        assertThat(result).isEqualTo(String.valueOf(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 9, 12})
    void shouldWrite_Foo_divisibleByThree(int number) {
        String result = sut.emit(number);
        assertThat(result).isEqualTo("Foo");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 20})
    void shouldWrite_Bar_divisibleByFive(int number) {
        String result = sut.emit(number);
        assertThat(result).isEqualTo("Bar");
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 14, 28})
    void shouldWrite_Qix(int number) {
        String result = sut.emit(number);
        assertThat(result).isEqualTo("Qix");
    }

    @ParameterizedTest
    @ValueSource(ints = {21})
    void shouldAdd_Qix(int number) {
        String result = sut.emit(number);
        assertThat(result).isEqualTo("FooQix");
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
                .emit(5);

        Mockito.verify(mockIntPredicate).test(Mockito.any());
        Mockito.verify(mockFun).apply(Mockito.any());
    }

    @Test
    void shouldBeTrue_IfContainsNumber() {
        int input = 35643;
        int toSearch = 3;
        boolean test = FooBarQix.containsNumber(toSearch).test(input);
        assertThat(test).isTrue();
    }

    @Test
    void shouldBeFalse_IfNotContainsNumber() {
        int input = 35643;
        int toSearch = 1;
        boolean test = FooBarQix.containsNumber(toSearch).test(input);
        assertThat(test).isFalse();
    }

    @Test
    void shouldReturn_FooFooFoo() {
        String result = sut.emit(33);

        assertThat(result).isEqualTo("FooFooFoo");
    }
}
