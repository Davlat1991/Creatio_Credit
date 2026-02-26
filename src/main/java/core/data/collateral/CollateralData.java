package core.data.collateral;

import core.enums.CollateralType;

public class CollateralData {

    private final CollateralType type;

    // Общие поля
    private final String marketValue;
    private final String liquidationValue;
    private final String currency;

    // Универсальное поле JSON-like (для специфики)
    private final Object specificData;

    public CollateralData(
            CollateralType type,
            String marketValue,
            String liquidationValue,
            String currency,
            Object specificData
    ) {
        this.type = type;
        this.marketValue = marketValue;
        this.liquidationValue = liquidationValue;
        this.currency = currency;
        this.specificData = specificData;
    }

    public CollateralType getType() { return type; }
    public String getMarketValue() { return marketValue; }
    public String getLiquidationValue() { return liquidationValue; }
    public String getCurrency() { return currency; }
    public Object getSpecificData() { return specificData; }
}