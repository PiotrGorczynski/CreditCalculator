package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static service.AmountsCalculationService.calculateInterestAmount;
import static service.AmountsCalculationService.calculateQ;

public class ConstantAmountsCalculationServiceImpl implements ConstantAmountsCalculationService
{
    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal q = calculateQ(interestPercent);
        BigDecimal monthsDuration = inputData.getMonthsDuration();

        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal rateAmount =calculateConstantRateAmount(q, interestAmount, residualAmount, residualAmount, inputData.getMonthsDuration());
        BigDecimal capitalAmount =  AmountsCalculationService.compareCapitalWithResidual(rateAmount.subtract(interestAmount), residualAmount);


        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment, Rate previousRate)
    {
        BigDecimal interestPercent =  inputData.getInterestPercent();
        BigDecimal q = AmountsCalculationService.calculateQ(inputData.getInterestPercent());

        BigDecimal residualAmount =previousRate.getMortgageResidual().getResidualAmount();
        BigDecimal referenceAmount = previousRate.getMortgageReference().getReferenceAmount();
        BigDecimal referenceDuration = previousRate.getMortgageReference().getReferenceDuration();

        BigDecimal interestAmount = AmountsCalculationService.calculateInterestAmount(residualAmount, interestPercent );
        BigDecimal rateAmount = calculateConstantRateAmount(q , interestAmount, residualAmount, referenceAmount, referenceDuration);
        BigDecimal capitalAmount = AmountsCalculationService.compareCapitalWithResidual(rateAmount.subtract(interestAmount), residualAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private BigDecimal calculateConstantRateAmount
            (BigDecimal q,
             BigDecimal interestAmount,
             BigDecimal residualAmount,
             BigDecimal referenceAmount,
             BigDecimal referenceDuration)
    {
        BigDecimal rateAmount = referenceAmount
                .multiply(q.pow(referenceDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(referenceDuration.intValue()).subtract(BigDecimal.ONE),2, RoundingMode.HALF_UP);

        return compareRateWithResidual(rateAmount, interestAmount, residualAmount);
    }


    private BigDecimal compareRateWithResidual(BigDecimal rateAmount, BigDecimal interestAmount, BigDecimal residualAmount)
    {
        if(rateAmount.subtract(interestAmount).compareTo(residualAmount) >= 0)
        {
            return residualAmount.add(interestAmount);
        }
        return rateAmount;
    }

}
