package br.com.springrestful.controllers;

import br.com.springrestful.exceptions.ResourceNotFoundException;
import br.com.springrestful.utils.MathOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

import static br.com.springrestful.utils.MathCalc.convertToDouble;
import static br.com.springrestful.utils.MathCalc.isNumeric;

@RestController
public class MathController {

    private final AtomicLong counter = new AtomicLong();
    private MathOperations mathOperations = new MathOperations();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
        method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
        ) throws RuntimeException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Coloca um número, dumbass");
        } return mathOperations.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}",
            method = RequestMethod.GET)
    public Double subtraction(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws RuntimeException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Coloca um número, dumbass");
        } return mathOperations.subtraction(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/division/{numberOne}/{numberTwo}",
            method = RequestMethod.GET)
    public Double division(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws RuntimeException {
        if (numberTwo.equals("0")){
            throw new ResourceNotFoundException("Não se pode dividir por 0");
        }
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Coloca um número, dumbass");
        } return mathOperations.division(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/mean/{numberOne}/{numberTwo}",
            method = RequestMethod.GET)
    public Double mean(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws RuntimeException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Coloca um número, dumbass");
        } return mathOperations.mean(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}",
            method = RequestMethod.GET)
    public Double multiplication(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo
    ) throws RuntimeException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Coloca um número, dumbass");
        } return mathOperations.multiplication(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @RequestMapping(value = "/squareroot/{numberOne}",
            method = RequestMethod.GET)
    public Double squareroot(
            @PathVariable(value = "numberOne") String numberOne
    ) throws RuntimeException {
        if (!isNumeric(numberOne)) {
            throw new ResourceNotFoundException("Coloca um número, dumbass");
        } return mathOperations.squareroot(convertToDouble(numberOne));
    }

}
