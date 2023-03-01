package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;
import utils.CalculationUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface AmountsCalculationService
{

    RateAmounts calculate(InputData inputData, Overpayment overpayment);

    RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate);


    static BigDecimal calculateQ(BigDecimal interestPercent)
    {
        return interestPercent.divide(CalculationUtils.YEAR,10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    static BigDecimal compareCapitalWithResidual(final BigDecimal capitalAmount, final BigDecimal residualAmount) {
        if (capitalAmount.compareTo(residualAmount) >= 0) {
            return residualAmount;
        }
        return capitalAmount;
    }
}
