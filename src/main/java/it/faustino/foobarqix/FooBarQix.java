package it.faustino.foobarqix;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class FooBarQix {


    private Map<Predicate<Integer>, Function<String, String>> decorations;

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

        Function<String, String> stringStringFunction = decorations.keySet().stream()
                .filter(condition -> condition.test(param))
                .map(condition -> decorations.get(condition))
                .reduce(Function::andThen)
                .orElseGet(() -> s -> String.valueOf(param));

        return stringStringFunction.apply("");


        /*var result = "";

        result = validateFor3(param, result);
        result = validateFor5(param, result);
        result = validateFor7(param, result);

        if (result.isEmpty()) {
            result = String.valueOf(param);
        }
        return result;*/
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


}
