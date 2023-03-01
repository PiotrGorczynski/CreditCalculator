package utils;

import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils
{
    public static BigDecimal YEAR = BigDecimal.valueOf(12);

    private CalculationUtils()
    {

    }

    public static BigDecimal calculateResidualAmount(RateAmounts rateAmounts, BigDecimal amount)
    {
        return amount
                .subtract(rateAmounts.getCapitalAmount())
                .subtract(rateAmounts.getOverpayment().getAmount())
                .max(BigDecimal.ZERO);
    }

    public static BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent)
    {
        return residualAmount.multiply(interestPercent).divide(CalculationUtils.YEAR,2, RoundingMode.HALF_UP);
    }
}
