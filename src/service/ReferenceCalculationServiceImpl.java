package service;

import model.*;
import utils.CalculationUtils;

import java.math.BigDecimal;
import java.util.List;

public class ReferenceCalculationServiceImpl implements ReferenceCalculationService
{
    @Override
    public MortgageReference calculate(RateAmounts rateAmounts, InputData inputData)
    {
        if (BigDecimal.ZERO.equals(inputData.getAmount())) {
            return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
        }
        return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
    }

    @Override
    public MortgageReference calculate(RateAmounts rateAmounts, InputData inputData, Rate previousRate)
    {
        if (BigDecimal.ZERO.equals(previousRate.getMortgageResidual().getResidualAmount())) {
            return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        switch (inputData.getOverpaymentReduceWay()) {
            case Overpayment.REDUCE_RATE:
                return reduceRateMortgageReference(rateAmounts, previousRate.getMortgageResidual());
            case Overpayment.REDUCE_PERIOD:
                return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
            default:
                throw new MortgageException();
        }
    }

    private MortgageReference reduceRateMortgageReference(RateAmounts rateAmounts, MortgageResidual previousResidual)
    {
        if (rateAmounts.getOverpayment().getAmount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal residualAmount = CalculationUtils.calculateResidualAmount(rateAmounts, previousResidual.getResidualAmount());
            return new MortgageReference(residualAmount, previousResidual.getResidualDuration().subtract(BigDecimal.ONE));
        }
        return new MortgageReference(previousResidual.getResidualAmount(), previousResidual.getResidualDuration());

    }

}
