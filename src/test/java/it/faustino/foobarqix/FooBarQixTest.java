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
    Function<String, String> mockFun;

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

    @AfterEach
    void tearDown() {
        sut = FooBarQix.create();
    }

    @Test
    void shouldCreateFooBarQix() {
        assertThat(sut).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9, 12})
    void shouldWrite_Foo(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("Foo");

    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 20})
    void shouldWrite_Bar(int number) {
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
    void shouldWrite_FooBar(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("FooBar");
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 14, 28})
    void shouldWrite_Qix(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("Qix");
    }

    @ParameterizedTest
    @ValueSource(ints = {21})
    void shouldAdd_Qix(int number) {
        var result = sut.emit(number);
        assertThat(result).isEqualTo("FooQix");
    }

    @Test
    void shouldExecuteRegisteredDecorations() {

        Mockito.doReturn(true).
                when(mockIntPredicate)
                .test(Mockito.any());

        FooBarQix.create()
                .when(mockIntPredicate, mockFun)
                .emit(5);

        Mockito.verify(mockIntPredicate).test(Mockito.any());
        Mockito.verify(mockFun).apply(Mockito.any());
    }

    @Test
    void shouldCreateDecoratorFunction() {
        Mockito.doReturn(true).
                when(mockStringPredicate)
                .test(Mockito.any());
        Mockito.doReturn("").
                when(mockFun)
                .apply(Mockito.anyString());

        var decorator = sut.decorateWith(mockStringPredicate, mockFun);
        decorator.apply(3);

        Mockito.verify(mockStringPredicate).test(Mockito.any());
        Mockito.verify(mockFun).apply(Mockito.any());
    }

   /* @ParameterizedTest
    @ValueSource(ints = {3345, 33, 1133, 345365})
    void shouldReturn_FooForEachThree(int number) {
        var result = sut.addsFoo(number);
        System.out.println(result);
        assertThat(result).isEqualTo("FooFoo");
    }*/
}
