package model;

import java.math.BigDecimal;

public class MortgageReference
{
    private BigDecimal referenceAmount;

    private BigDecimal referenceDuration;

    public MortgageReference(BigDecimal referenceAmount, BigDecimal referenceDuration)
    {
        this.referenceAmount = referenceAmount;
        this.referenceDuration = referenceDuration;
    }

    public BigDecimal getReferenceAmount()
    {
        return referenceAmount;
    }

    public BigDecimal getReferenceDuration()
    {
        return referenceDuration;
    }
}
