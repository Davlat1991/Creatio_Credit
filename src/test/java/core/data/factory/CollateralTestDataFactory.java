package core.data.factory;

import core.data.collateral.CollateralData;
import core.data.collateral.CollateralDataBuilder;
import core.data.collateral.types.CottonData;
import core.data.collateral.types.FutureHarvestData;
import core.data.collateral.types.MovablePropertyData;
import core.data.collateral.types.RealEstateData;
import core.enums.CollateralType;
import core.enums.CurrencyType;

public class CollateralTestDataFactory {

    // =========================
    // EQUIPMENT
    // =========================
    public static CollateralData equipment(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.EQUIPMENT)
                .withMarketValue("500000")
                .withLiquidationValue("450000")
                .withCurrency(currency.getUiValue())
                .build();
    }

    // =========================
    // VEHICLE
    // =========================
    public static CollateralData vehicle(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.VEHICLE)
                .withLiquidationValue("250000")
                .withMarketValue("300000")
                .withCurrency(currency.getUiValue())
                .build();
    }

    // =========================
    // REAL ESTATE
    // =========================
    public static CollateralData realEstate(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.REAL_ESTATE)
                .withLiquidationValue("100000")
                .withMarketValue("1100000")
                .withCurrency(currency.getUiValue())
                .withSpecificData(
                        new RealEstateData(
                                "80",
                                "2015",
                                "3",
                                "Квартира в центре",
                                "Квартира",
                                "100"
                        )
                )
                .build();
    }


    public static CollateralData gold(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.GOLD)
                .withCurrency(currency.getUiValue())

                .build();
    }

    public static CollateralData movableProperty(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.MOVABLE_PROPERTY)
                .withCurrency(currency.getUiValue())

                .build();
    }


    public static CollateralData acquiredProperty(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.ACQUIRED_PROPERTY)
                .withCurrency(currency.getUiValue())

                .build();
    }


    public static CollateralData cotton(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.COTTON)
                .withCurrency(currency.getUiValue())

                .build();
    }

    public static CollateralData futureHarvest(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.FUTURE_HARVEST)
                .withCurrency(currency.getUiValue())

                .build();
    }

    public static CollateralData goods(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.GOODS)
                .withCurrency(currency.getUiValue())

                .build();
    }

    public static CollateralData cashDeposit(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.CASH_DEPOSIT)
                .withCurrency(currency.getUiValue())

                .build();
    }



    /* // Пример для добавления нового метода запуска тестов

    public static CollateralData acquiredProperty(CurrencyType currency) {
        return CollateralDataBuilder
                .ofType(CollateralType.ACQUIRED_PROPERTY)
                .withCurrency(currency.getUiValue())
                .withSpecificData(
                        new MovablePropertyData(
                                "Приобретённый станок",
                                "80000",
                                "1",
                                "Новое оборудование"
                        )
                )
                .build();
    }*/
}