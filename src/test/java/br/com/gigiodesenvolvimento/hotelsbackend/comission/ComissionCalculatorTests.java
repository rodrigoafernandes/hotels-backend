package br.com.gigiodesenvolvimento.hotelsbackend.comission;

import static java.math.BigDecimal.TEN;
import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.jupiter.api.Test;

public class ComissionCalculatorTests {

    private static final BigDecimal MARKUP = createBigDecimal("0.7");
    private static final Integer PRECISION = createInteger("8");
    private static final Integer SCALE = createInteger("4");

    private ComissionCalculator comissionCalculator = new ComissionCalculator();

    @Test
    public void givenValidValue_whenMarkup_thenShouldReturnValueMarkuped() {
        BigDecimal expectedValue = TEN.divide(MARKUP, new MathContext(PRECISION)).setScale(SCALE, HALF_UP);
        BigDecimal valueWithMarkup = comissionCalculator.markup(TEN);
        assertEquals(expectedValue, valueWithMarkup);
    }

}