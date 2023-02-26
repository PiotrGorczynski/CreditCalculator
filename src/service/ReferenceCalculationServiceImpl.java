package service;

import model.InputData;
import model.MortgageReference;
import model.Rate;

import java.util.List;

public class ReferenceCalculationServiceImpl implements ReferenceCalculationService
{
    @Override
    public MortgageReference calculate(InputData inputData)
    {
        return new MortgageReference(inputData.getAmount(), inputData.getMonthsDuration());
    }
}
