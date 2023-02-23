package service;

import model.InputData;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountsCalculationServiceImpl implements AmountsCalculationService
{

    private static final BigDecimal YEAR = BigDecimal.valueOf(12);

    @Override
    public RateAmounts calculate(InputData inputData)
    {
        switch (inputData.getRateType())
        {
            case CONSTANT:
                return calculateConstantRate(inputData);

            case DECREASING:
                return calculateDecreasingRate(inputData);

            default:
                throw new RuntimeException("CASE NOT HANDLED");
        }
    }
    
    @Override
    public RateAmounts calculate(InputData inputData, Rate previousRate)
    {
        switch (inputData.getRateType())
        {
            case CONSTANT:
                return calculateConstantRate(inputData, previousRate);

            case DECREASING:
                return calculateDecreasingRate(inputData, previousRate);

            default:
                throw new RuntimeException("CASE NOT HANDLED");
        }

    }

    private RateAmounts calculateConstantRate(InputData inputData)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();
        BigDecimal q = calculateQ(inputData.getInterestPercent());

        BigDecimal rateAmount = calculateConstantRateAmount(q,residualAmount,monthsDuration);
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);
        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }


    private RateAmounts calculateConstantRate(InputData inputData, Rate previousRate)
    {
        BigDecimal interestPercent =  inputData.getInterestPercent();
        BigDecimal residualAmount =  inputData.getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();
        BigDecimal q = calculateQ(inputData.getInterestPercent());

        BigDecimal rateAmount = calculateConstantRateAmount(q , residualAmount, monthsDuration);
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent );
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(residualAmount, monthsDuration);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);;

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData, Rate previousRate)
    {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidiual().getAmount();
        BigDecimal monthsDuration = inputData.getMonthsDuration();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), monthsDuration);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
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
