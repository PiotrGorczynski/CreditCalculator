package service;

import model.InputData;
import model.MortgageResidiual;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl implements ResidualCalculationService
{
    @Override
    public MortgageResidiual calculate(RateAmounts rateAmounts, InputData inputData)
    {
        BigDecimal residualAmount = inputData.getAmount().subtract(rateAmounts.getCapitalAmount().max(BigDecimal.ZERO) );
        BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE);


        return new MortgageResidiual(residualAmount,residualDuration);
    }

    @Override
    public MortgageResidiual calculate(RateAmounts rateAmounts, Rate previousRate)
    {
        MortgageResidiual residual = previousRate.getMortgageResidiual();
        BigDecimal previousDuration = residual.getDuration();
        BigDecimal residualAmount = residual.getAmount().subtract(rateAmounts.getCapitalAmount()).max(BigDecimal.ZERO);
        BigDecimal residualDuration = residual.getDuration().subtract(BigDecimal.ONE);


        return new MortgageResidiual(residualAmount,residualDuration);
    }
}
