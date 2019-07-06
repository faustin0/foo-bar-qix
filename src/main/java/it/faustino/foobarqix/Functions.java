package it.faustino.foobarqix;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Functions {

    private Functions() {
    }

    public static Function<Integer, String> createFunction(int condition, String toAdd) {
        return new Functions()
                .replace(condition, toAdd);
    }

    public static Function<Integer, String> replace(int toReplace, String replacement) {
        Predicate<String> numberSearched = Predicates.isNumberSearched(toReplace);
        return i -> toStringStream(i)
                .filter(numberSearched)
                .map(s -> replacement)
                .reduce("", String::concat);
    }

    public static  Stream<String> toStringStream(Integer i) {
        return i.toString()
                .codePoints()
                .mapToObj(p -> (char) p)
                .map(String::valueOf);
    }
}
