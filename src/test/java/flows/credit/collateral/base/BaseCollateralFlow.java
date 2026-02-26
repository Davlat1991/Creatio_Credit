package flows.credit.collateral.base;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import io.qameta.allure.Step;

/**
 * Базовый flow для любого типа залога.
 *
 * Определяет контракт:
 * каждый залог ОБЯЗАН уметь заполняться через fill().
 */
public abstract class BaseCollateralFlow {

    protected final UiContext ui;

    public BaseCollateralFlow(UiContext ui) {
        this.ui = ui;
    }

    public abstract void fill(CollateralData data);
}
