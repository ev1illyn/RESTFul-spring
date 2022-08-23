package br.com.springrestful.utils;

import static br.com.springrestful.utils.MathCalc.convertToDouble;

public class MathOperations {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtraction(Double numberOne, Double numberTwo
    ) {
        return numberOne - numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo) {
        return numberOne / numberTwo;
    }

    public Double mean(Double numberOne, Double numberTwo) {
        return (numberOne + numberTwo) /2;
    }

    public Double multiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double squareroot(Double numberOne) {
        return Math.sqrt(numberOne);
    }

}
