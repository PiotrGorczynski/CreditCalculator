package service;

import model.InputData;
import model.Rate;

import java.util.List;

public class PrintingServiceImpl implements PrintingService
{

    @Override
    public void printInputData(InputData inputData)
    {
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(MORTGAGE_AMOUNT).append(inputData.getAmount()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(MORTGATE_PERIOD).append(inputData.getMonthsDuration()).append(MONTHS);
        msg.append(NEW_LINE);
        msg.append(INTEREST).append(inputData.getInterestPercentDisplay()).append(PERCENT);
        msg.append(NEW_LINE);

        printMessage(msg);

    }

    private void printMessage(StringBuilder sb)
    {
        System.out.println(sb);
    }

    @Override
    public void printRates(List<Rate> rates)
    {
        String format = "%s %s" +
                "%s %s" +
                "%s %s" +
                "%s %s" +
                "%s %s" +
                "%s %s" +
                "%s %s" +
                "%s %s" +
                "%s %s";

        for (Rate rate : rates)
        {
            String message = String.format(format,
                    RATE_NUMBER, rate.getRateNumber(),
                    DATE, rate.getTimePoint().getDate(),
                    YEAR, rate.getTimePoint().getYear(),
                    MONTH, rate.getTimePoint().getMonth(),
                    RATE, rate.getRateAmounts().getRateAmount(),
                    INTEREST, rate.getRateAmounts().getInterestAmount(),
                    CAPITAL, rate.getRateAmounts().getCapitalAmount(),
                    LEFT, rate.getMortgageResidiual().getAmount(),
                    MONTHS, rate.getMortgageResidiual().getDuration()
            );
            printMessage(new StringBuilder(message));
        }

    }
}
