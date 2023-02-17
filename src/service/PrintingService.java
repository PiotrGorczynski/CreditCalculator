package service;

import model.InputData;

public interface PrintingService
{
    String INTEREST_SUM = "INTEREST SUM: ";
    String RATE_NUMBER = "RATE NR: ";
    String YEAR = "YEAR: ";
    String MONTH = "MONTH: ";
    String DATE = "DATE: ";
    String MONTHS = " MONTHS";
    String RATE = "RATE: ";
    String INTEREST = "INTEREST: ";
    String CAPITAL = "CAPITAL: ";
    String LEFT = "LEFT: ";
    String MORTGAGE_AMOUNT = "MORTGAGE AMOUNT: ";
    String MORTGATE_PERIOD = "MORTGAGE PERIOD: ";


    String CURRENCY = " ZL ";
    String NEW_LINE = "\n";
    String PERCENT = "% ";

    void printInputData(final InputData inputData);
}
