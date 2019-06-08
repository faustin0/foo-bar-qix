package it.faustino.foobarqix;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FooBarQix {


    private Map<Predicate<Integer>, Function<String, String>> decorations;
    public static final String startingString = "";

    private FooBarQix() {
        this.decorations = new HashMap<>();
    }

    public static FooBarQix create() {
        return new FooBarQix();
    }

    public FooBarQix when(Predicate<Integer> condition, Function<String, String> action) {
        this.decorations.put(condition, action);
        return this;
    }

    public String emit(int param) {

        Function<String, String> fooBarQixDecorator = decorations.keySet().stream()
                .filter(condition -> condition.test(param))
                .map(condition -> decorations.get(condition))
                .reduce(Function::andThen)
                .orElseGet(() -> s -> String.valueOf(param));

        return fooBarQixDecorator.apply(startingString);
    }

    public String validateFor5(int toValidate, String currentResult) {
        if (isDivisibleBy(toValidate, 5)) {
            currentResult += "Bar";
            return currentResult;
        }
        return currentResult;
    }

    public String validateFor3(int toValidate, String currentResult) {
        if (isDivisibleBy(toValidate, 3)) {
            currentResult = "Foo";
            return currentResult;
        }
        return currentResult;
    }

    public String validateFor7(int toValidate, String currentResult) {
        if (isDivisibleBy(toValidate, 7)) {
            currentResult += "Qix";
            return currentResult;
        }
        return currentResult;
    }


    public boolean isDivisibleBy(int dividend, int divisor) {
        return dividend % divisor == 0;
    }


    public String addsFoo(int i, Predicate<String> isSearchedNumber, Function<String, String> func) {
        return String.valueOf(i).codePoints()
                .mapToObj(p -> (char) p)
                .map(String::valueOf)
                .filter(s -> s.equals("3"))
                //.filter(isSearchedNumber)
                .map(s -> "Foo")
                .reduce(String::concat)
                .orElse("");
    }

    public Function<Integer, String> decorateWith(Predicate<String> condition, Function<String, String> func) {
        return i -> String.valueOf(i)
                .codePoints()
                .mapToObj(p -> (char) p)
                .map(String::valueOf)
                .filter(condition)
                .map(func)
                .reduce(String::concat)
                .orElse("");
    }
}
