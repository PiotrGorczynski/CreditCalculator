package service;

import model.InputData;
import model.MortgageReference;
import model.Rate;
import model.RateAmounts;

import java.util.List;

public class ReferenceCalculationServiceImpl implements ReferenceCalculationService
{
    @Override
    public MortgageReference calculate(RateAmounts rateAmounts, InputData inputData)
    {
        return null;
    }

    @Override
    public MortgageReference calculate(RateAmounts rateAmounts, InputData inputData, Rate previousRate)
    {
        return null;
    }
}
