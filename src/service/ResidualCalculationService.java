package service;

import model.InputData;
import model.MortgageResidiual;
import model.Rate;
import model.RateAmounts;

public interface ResidualCalculationService
{
    MortgageResidiual calculate(RateAmounts rateAmounts, InputData inputData);
    MortgageResidiual calculate(RateAmounts rateAmounts, Rate inputData);
}
