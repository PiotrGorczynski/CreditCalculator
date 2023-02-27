package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface AmountsCalculationService
{
    BigDecimal YEAR = BigDecimal.valueOf(12);

    RateAmounts calculate(InputData inputData, Overpayment overpayment);

    RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate);

    static BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent)
    {
        return residualAmount.multiply(interestPercent).divide(YEAR,2, RoundingMode.HALF_UP);
    }

    static BigDecimal calculateQ(BigDecimal interestPercent)
    {
        return interestPercent.divide(AmountsCalculationService.YEAR,10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    static BigDecimal compareCapitalWithResidual(final BigDecimal capitalAmount, final BigDecimal residualAmount) {
        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return residualAmount;
        }
        return capitalAmount;
    }
}
