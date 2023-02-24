package model;

import java.math.BigDecimal;

public class Rate
{
    private final BigDecimal rateNumber;

    private final TimePoint timePoint;

    private final RateAmounts rateAmounts;

    private final MortgageResidiual mortgageResidiual;

    private final MortgageReference mortgageReference;

    public Rate(BigDecimal rateNumber, TimePoint timePoint, RateAmounts rateAmounts, MortgageResidiual mortgageResidiual, MortgageReference mortgageReference)
    {
        this.rateNumber = rateNumber;
        this.timePoint = timePoint;
        this.rateAmounts = rateAmounts;
        this.mortgageResidiual = mortgageResidiual;
        this.mortgageReference = mortgageReference;
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

    public MortgageReference getMortgageReference()
    {
        return mortgageReference;
    }
}
