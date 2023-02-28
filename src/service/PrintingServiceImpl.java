package service;

import model.InputData;
import model.Overpayment;
import model.Rate;
import model.Summary;

import java.util.List;
import java.util.Optional;

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
        msg.append(INTEREST_).append(inputData.getInterestPercentDisplay()).append(PERCENT);
        msg.append(NEW_LINE);

        Optional.of(inputData.getOverpaymentSchema())
                        .filter(schema -> schema.size() > 0)
                        .ifPresent(schema -> logOverpayment(msg, inputData));

        printMessage(msg);

    }

    private void logOverpayment(StringBuilder msg, InputData inputData)
    {
        switch (inputData.getOverpaymentReduceWay())
        {
            case Overpayment.REDUCE_PERIOD:
                msg.append(OVERPAYMENT_REDUCE_PERIOD);
                break;
            case Overpayment.REDUCE_RATE:
                msg.append(OVERPAYMENT_REDUCE_RATE);
                break;
            default:
                throw new MortgageException();
        }

        msg.append(NEW_LINE);
        msg.append(OVERPAYMENT_FREQUENCY).append(inputData.getOverpaymentSchema());
        msg.append(NEW_LINE);
    }

    @Override
    public void printRates(List<Rate> rates)
    {
        String format =
                "%-4s %3s " +
                        "%-4s %3s " +
                        "%-7s %2s " +
                        "%-7s %3s " +
                        "%-4s %9s " +
                        "%-7s %8s " +
                        "%-7s %8s " +
                        "%-7s %8s " +
                        "%-8s %10s " +
                        "%-8s %5s ";

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
                    OVERPAYMENT, rate.getRateAmounts().getOverpayment().getAmount(),
                    LEFT, rate.getMortgageResidual().getResidualAmount(),
                    LEFT_MONTHS, rate.getMortgageResidual().getResidualDuration()
            );
            printMessage(new StringBuilder(message));

            if(rate.getRateNumber().intValue()%12==0)
            {
                System.out.println();
            }
        }


    }

    @Override
    public void printSummary(Summary summary)
    {
        StringBuilder msg = new StringBuilder(NEW_LINE);
        msg.append(INTEREST_SUM).append(summary.getInterestSum()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(OVERPAYMENT_PROVISION).append(summary.getOverpaymentProvisions()).append(CURRENCY);
        msg.append(NEW_LINE);
        msg.append(LOSTS_SUM).append(summary.getTotalLosts()).append(CURRENCY);
        msg.append(NEW_LINE);

        printMessage(new StringBuilder(msg.toString()));
    }

    private void printMessage(StringBuilder sb)
    {
        System.out.println(sb);
    }

}
