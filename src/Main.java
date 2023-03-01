import model.InputData;
import model.Overpayment;
import model.RateType;
import service.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class Main
{
    private PrintingService printingService;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        InputData inputData = new InputData()
                .withAmount(new BigDecimal(150000))
                .withMonthsDuration(BigDecimal.valueOf(180))
                .withRateType(RateType.DECREASING);


        PrintingService printingService = new PrintingServiceImpl();

        RateCalculationService rateCalculationService = new RateCalculationServiceImpl(
                new TimePointServiceImpl(),
                new AmountsCalculationServiceImpl(new ConstantAmountsCalculationServiceImpl(),
                        new DecreasingAmountsCalculationServiceImpl()
                ),
                new OverpaymentCalculationServiceImpl(),
                new ResidualCalculationServiceImpl(),
                new ReferenceCalculationServiceImpl()
        );

        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImpl(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create());
        mortgageCalculationService.calculate(inputData);

    }
}
