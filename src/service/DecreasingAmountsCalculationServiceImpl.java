package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;
import utils.CalculationUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecreasingAmountsCalculationServiceImpl implements DecreasingAmountsCalculationService
{
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();

        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal residualDuration = inputData.getMonthsDuration();

        BigDecimal interestAmount = CalculationUtils.calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = AmountsCalculationService.compareCapitalWithResidual(
                calculateDecreasingCapitalAmount(residualAmount, residualDuration),residualAmount);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidual().getResidualAmount();
        BigDecimal referenceAmount = previousRate.getMortgageReference().getReferenceAmount();
        BigDecimal referenceDuration = previousRate.getMortgageReference().getReferenceDuration();

        BigDecimal interestAmount = CalculationUtils.calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = AmountsCalculationService.compareCapitalWithResidual(calculateDecreasingCapitalAmount(
                referenceAmount, referenceDuration),residualAmount);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateDecreasingCapitalAmount(BigDecimal residualAmount, BigDecimal residualDuration)
    {
        return residualAmount.divide(residualDuration, 10, RoundingMode.HALF_UP);
    }
}
