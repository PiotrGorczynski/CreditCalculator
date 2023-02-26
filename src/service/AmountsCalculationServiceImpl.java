package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountsCalculationServiceImpl implements AmountsCalculationService
{

    private static final BigDecimal YEAR = BigDecimal.valueOf(12);

    @Override
    public RateAmounts calculate(InputData inputData, Overpayment overpayment)
    {
        switch (inputData.getRateType())
        {
            case CONSTANT:
                return calculateConstantRate(inputData, overpayment);

            case DECREASING:
                return calculateDecreasingRate(inputData, overpayment);

            default:
                throw new RuntimeException("CASE NOT HANDLED");
        }
    }
    
    @Override
    public RateAmounts calculate(InputData inputData,Overpayment overpayment, Rate previousRate)
    {
        switch (inputData.getRateType())
        {
            case CONSTANT:
                return calculateConstantRate(inputData, overpayment, previousRate);

            case DECREASING:
                return calculateDecreasingRate(inputData, overpayment, previousRate);

            default:
                throw new RuntimeException("CASE NOT HANDLED");
        }

    }

    private RateAmounts calculateConstantRate(InputData inputData, Overpayment overpayment)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();
        BigDecimal q = calculateQ(inputData.getInterestPercent());

        BigDecimal rateAmount = calculateConstantRateAmount(q,residualAmount,monthsDuration);
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);
        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }


    private RateAmounts calculateConstantRate(InputData inputData, Overpayment overpayment, Rate previousRate)
    {
        BigDecimal interestPercent =  inputData.getInterestPercent();
        BigDecimal residualAmount =  inputData.getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();
        BigDecimal q = calculateQ(inputData.getInterestPercent());

        BigDecimal rateAmount = calculateConstantRateAmount(q , residualAmount, monthsDuration);
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent );
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData, Overpayment overpayment)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(residualAmount, monthsDuration);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);;

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData, Overpayment overpayment, Rate previousRate)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidiual().getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), monthsDuration);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount, overpayment);
    }


    private BigDecimal calculateQ(BigDecimal interestPercent)
    {
        return interestPercent.divide(YEAR,10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    private BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent)
    {
        return residualAmount.multiply(interestPercent).divide(YEAR,2,RoundingMode.HALF_UP);
    }

    private BigDecimal calculateConstantRateAmount(BigDecimal q, BigDecimal amount, BigDecimal monthsDuration)
    {
        return amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE),2,RoundingMode.HALF_UP);
    }


    private BigDecimal calculateConstantCapitalAmount(BigDecimal rateAmount, BigDecimal interestAmount)
    {
        return rateAmount.subtract(interestAmount);
    }


    private BigDecimal calculateDecreasingCapitalAmount(BigDecimal amount, BigDecimal monthsDuration)
    {
        return amount.divide(monthsDuration, 10, RoundingMode.HALF_UP);
    }
}
