package it.faustino.foobarqix;

public class FooBarQix {


    public FooBarQix() {

    }

    public String emit(int param) {
        if (isDivisibleBy(param, 3)) {
            return "Foo";
        } else if (isDivisibleBy(param, 5)) {
            return "Bar";
        } else return String.valueOf(param);
    }


    public boolean isDivisibleBy(int dividend, int divisor) {
        return dividend % divisor == 0;
    }
}
