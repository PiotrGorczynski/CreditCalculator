package service;

import model.InputData;
import model.Rate;
import model.Summary;

import java.util.List;

public interface PrintingService
{
    String INTEREST_SUM = "INTEREST SUM: ";
    String RATE_NUMBER = " RATE NR:";
    String YEAR = " YEAR:";
    String MONTH = " MONTH:";
    String DATE = " DATE:";
    String LEFT_MONTHS = " MONTHS:";
    String MONTHS = " MONTHS";
    String RATE = " RATE:";
    String CAPITAL = " CAPITAL:";
    String LEFT = " LEFT:";
    String MORTGAGE_AMOUNT = "MORTGAGE AMOUNT: ";
    String MORTGATE_PERIOD = "MORTGAGE PERIOD: ";
    String INTEREST_ = "INTEREST: ";
    String INTEREST = " INTEREST:";


    String CURRENCY = " ZL ";
    String NEW_LINE = "\n";
    String PERCENT = "% ";

    void printInputData(final InputData inputData);

    void printRates(List<Rate> rates);

    void printSummary(Summary summary);
}
