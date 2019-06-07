package it.faustino.foobarqix;

public class FooBarQix {


    public FooBarQix() {

    }

    public String emit(int param) {
        var result = "";

        result = validateFor3(param, result);
        result = validateFor5(param, result);

        if (result.isEmpty()) {
            result = String.valueOf(param);
        }
        return result;
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


    public boolean isDivisibleBy(int dividend, int divisor) {
        return dividend % divisor == 0;
    }


}
