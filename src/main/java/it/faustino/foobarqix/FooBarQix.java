package it.faustino.foobarqix;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FooBarQix {

    private Map<Predicate<Integer>, Function<Integer, String>> decorations;

    private FooBarQix() {
        this.decorations = new LinkedHashMap<>();
    }

    public static FooBarQix create() {
        return new FooBarQix();
    }

    public FooBarQix when(Predicate<Integer> condition, Function<Integer, String> action) {
        this.decorations.put(condition, action);
        return this;
    }

    public String compute(int param) {
        return decorations.keySet().stream()
                .filter(condition -> condition.test(param))
                .map(decorations::get)
                .map(action -> action.apply(param))
                .reduce(String::concat)
                .orElseGet(() -> String.valueOf(param));
    }

    //TODO extract to class of function factory, and rename
    public static Function<Integer, String> decorateWith(String condition, String toAdd) {
        return i -> i.toString()
                .codePoints()
                .mapToObj(p -> (char) p)
                .map(String::valueOf)
                .filter(s -> s.equals(condition))
                .map(s -> toAdd)
                .reduce("", String::concat);
    }

    public static Predicate<Integer> containsNumber(int toSearch) {
        return input -> String.valueOf(input)
                .codePoints()
                .mapToObj(p -> (char) p)
                .map(String::valueOf)
                .anyMatch(s -> s.equals(String.valueOf(toSearch)));
    }
}
