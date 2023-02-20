package model;

import java.math.BigDecimal;

public class Rate
{
    private final BigDecimal rateNumber;

    private final TimePoint timePoint;

    private final RateAmounts rateAmounts;

    private final MortgageResidiual mortgageResidiual;

    public Rate(BigDecimal rateNumber, TimePoint timePoint, RateAmounts rateAmounts, MortgageResidiual mortgageResidiual)
    {
        this.rateNumber = rateNumber;
        this.timePoint = timePoint;
        this.rateAmounts = rateAmounts;
        this.mortgageResidiual = mortgageResidiual;
    }


    public BigDecimal getRateNumber()
    {
        return rateNumber;
    }

    public TimePoint getTimePoint()
    {
        return timePoint;
    }

    public RateAmounts getRateAmounts()
    {
        return rateAmounts;
    }

    public MortgageResidiual getMortgageResidiual()
    {
        return mortgageResidiual;
    }

    @Override
    public String toString()
    {
        return "Rate{" +
                "rateAmounts=" + rateAmounts +
                ", mortgageResidiual=" + mortgageResidiual +
                '}';
    }
}
