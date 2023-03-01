package service;

import model.InputData;
import model.MortgageResidual;
import model.Rate;
import model.RateAmounts;
import utils.CalculationUtils;

import java.math.BigDecimal;

public class ResidualCalculationServiceImpl implements ResidualCalculationService
{
    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData)
    {
        BigDecimal residualAmount = CalculationUtils.calculateResidualAmount(rateAmounts, inputData.getAmount());
        BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE);


        return new MortgageResidual(residualAmount,residualDuration);
    }

    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate)
    {
        MortgageResidual residual = previousRate.getMortgageResidual();
        BigDecimal previousDuration = residual.getResidualDuration();
        BigDecimal residualAmount = CalculationUtils.calculateResidualAmount(rateAmounts, residual.getResidualAmount());
        BigDecimal residualDuration = residual.getResidualDuration().subtract(BigDecimal.ONE);


        return new MortgageResidual(residualAmount,residualDuration);
    }

}
