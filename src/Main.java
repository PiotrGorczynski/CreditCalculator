import model.InputData;
import model.Rate;
import model.RateType;
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
                .withMonthsDuration(BigDecimal.valueOf(360))
                .withRateType(RateType.CONSTANT);


        PrintingService printingService = new PrintingServiceImpl();

        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(),
                new OverpaymentCalculationServiceImpl(),
                new ResidualCalculationServiceImpl(),
                new ReferenceCalculationServiceImpl();
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create());
        mortgageCalculationService.calculate(inputData);

    }
}
