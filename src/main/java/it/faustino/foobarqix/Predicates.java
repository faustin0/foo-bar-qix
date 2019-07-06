package it.faustino.foobarqix;

import java.util.function.Predicate;

import static it.faustino.foobarqix.Functions.toStringStream;

public class Predicates {

    public static Predicate<Integer> isDivisibleBy(int divisor) {
        return dividend -> dividend % divisor == 0;
    }

    public static Predicate<Integer> containsNumber(int toSearch) {
        Predicate<String> numberSearched = Predicates.isNumberSearched(toSearch);
        return input -> toStringStream(input)
                .anyMatch(numberSearched);
    }

    public static Predicate<String> isNumberSearched(int condition) {
        return s -> s.equals(String.valueOf(condition));
    }
}
