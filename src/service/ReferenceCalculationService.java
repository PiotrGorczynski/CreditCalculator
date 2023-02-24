package service;

import model.MortgageReference;
import model.Rate;

public interface ReferenceCalculationService
{
    MortgageReference calculate();

    MortgageReference calculate(Rate previousRate);
}
