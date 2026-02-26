package core.data.collateral;

import core.enums.CollateralType;

public class CollateralDataBuilder {

    private CollateralType type;
    private String marketValue;
    private String liquidationValue;
    private String currency;
    private Object specificData;

    private CollateralDataBuilder(CollateralType type) {
        this.type = type;
    }

    public static CollateralDataBuilder ofType(CollateralType type) {
        return new CollateralDataBuilder(type);
    }

    public CollateralDataBuilder withMarketValue(String marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public CollateralDataBuilder withLiquidationValue(String liquidationValue) {
        this.liquidationValue = liquidationValue;
        return this;
    }

    public CollateralDataBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public CollateralDataBuilder withSpecificData(Object specificData) {
        this.specificData = specificData;
        return this;
    }

    public CollateralData build() {
        return new CollateralData(
                type,
                liquidationValue,
                marketValue,
                currency,
                specificData
        );
    }
}