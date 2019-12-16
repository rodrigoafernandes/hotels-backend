package br.com.gigiodesenvolvimento.hotelsbackend.comission;

import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.stereotype.Component;

@Component
public class ComissionCalculator {

    private static final BigDecimal MARKUP = createBigDecimal("0.7");
    private static final Integer PRECISION = createInteger("8");
    private static final Integer SCALE = createInteger("4");

    public BigDecimal markup(BigDecimal valueToMarkup) {
        return valueToMarkup.divide(MARKUP, new MathContext(PRECISION)).setScale(SCALE, HALF_UP);
    }

}