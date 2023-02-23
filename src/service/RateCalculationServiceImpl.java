package service;

import model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImpl implements RateCalculationService
{

    private final TimePointService timePointService;

    private final AmountsCalculationService amountsCalculationService;

    private final ResidualCalculationService residualCalculationService;


    public RateCalculationServiceImpl(TimePointService timePointService,
                                      AmountsCalculationService amountsCalculationService,
                                      ResidualCalculationService residualCalculationService)
    {
        this.timePointService = timePointService;
        this.amountsCalculationService = amountsCalculationService;
        this.residualCalculationService = residualCalculationService;
    }

    @Override
    public List<Rate> calculate(InputData inputData)
    {
         List<Rate> rates = new LinkedList<>();

        BigDecimal rateNumber = BigDecimal.ONE;

        Rate firstRate = calculateRate(rateNumber,inputData);
        Rate previousRate = firstRate;
        rates.add(firstRate);


        for (BigDecimal index = rateNumber.add(BigDecimal.ONE);
             index.compareTo(inputData.getMonthsDuration()) <= 0;
             index = index.add(BigDecimal.ONE))
        {
            Rate nextRate = calculateRate(index, inputData, previousRate);
            previousRate = nextRate;
            rates.add(nextRate);
        }

        return rates;
    }

    private Rate calculateRate(BigDecimal rateNumber, InputData inputData)
    {

        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountsCalculationService.calculate(inputData);
        MortgageResidiual mortgageResidiual = residualCalculationService.calculate(rateAmounts, inputData);

        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidiual);
    }

    private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate)
    {


        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountsCalculationService.calculate(inputData,previousRate);
        MortgageResidiual mortgageResidiual = residualCalculationService.calculate(rateAmounts, previousRate);

        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidiual);
    }
}
