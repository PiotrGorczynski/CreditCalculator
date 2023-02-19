import model.InputData;
import model.Rate;
import service.*;

import java.math.BigDecimal;
import java.util.List;

public class Main
{
    private PrintingService printingService;

    public static void main(String[] args)
    {
        InputData inputData = new InputData()
                .withAmount(new BigDecimal(298000))
                .withMonthsDuration(BigDecimal.valueOf(160));


        PrintingService printingService = new PrintingServiceImpl();

        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(),
                new ResidualCalculationServiceImpl()
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService);
        mortgageCalculationService.calculate(inputData);

    }
}
