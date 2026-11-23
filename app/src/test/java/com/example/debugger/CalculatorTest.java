package com.example.debugger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * JUnit4 unit tests for the calculator logic.
 */
@RunWith(JUnit4.class)
public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    public void addTwoNumbers() {
        double resultAdd = mCalculator.add(55d, 25d);
        assertThat(resultAdd, is(equalTo(80d)));
    }

    @Test
    public void addTwoNumbersNegative() {
        double resultAdd = mCalculator.add(-15d, 20d);
        assertThat(resultAdd, is(equalTo(5d)));
    }

    @Test
    public void addTwoNumbersFloats() {
        double resultAdd = mCalculator.add(1.111d, 1.111d);
        assertThat(resultAdd, is(equalTo(2.222d)));
    }

    @Test
    public void subTwoNumbers() {
        double resultSub = mCalculator.sub(55d, 25d);
        assertThat(resultSub, is(equalTo(30d)));
    }

    @Test
    public void subWorksWithNegativeResult() {
        double resultSub = mCalculator.sub(20d, 50d);
        assertThat(resultSub, is(equalTo(-30d)));
    }

    @Test
    public void mulTwoNumbers() {
        double resultMul = mCalculator.mul(55d, 25d);
        assertThat(resultMul, is(equalTo(1375d)));
    }

    @Test
    public void mulTwoNumbersZero() {
        double resultMul = mCalculator.mul(42d, 0d);
        assertThat(resultMul, is(equalTo(0d)));
    }

    @Test
    public void divTwoNumbers() {
        double resultDiv = mCalculator.div(55d, 25d);
        assertThat(resultDiv, is(equalTo(2.2d)));
    }

    @Test
    public void divDivideByZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> mCalculator.div(55d, 0d));
    }
}
