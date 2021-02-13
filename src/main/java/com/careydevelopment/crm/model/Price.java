package com.careydevelopment.crm.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Price {

    private PriceType priceType;
    private UnitType unitType;
    private CurrencyType currencyType;
    private Integer amount;
    
    public PriceType getPriceType() {
        return priceType;
    }
    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }
    public UnitType getUnitType() {
        return unitType;
    }
    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
    public CurrencyType getCurrencyType() {
        return currencyType;
    }
    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
        result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
        result = prime * result + ((unitType == null) ? 0 : unitType.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Price other = (Price) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (currencyType != other.currencyType)
            return false;
        if (priceType != other.priceType)
            return false;
        if (unitType != other.unitType)
            return false;
        return true;
    }
}
