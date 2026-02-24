package flows.credit.collateral.base;

import core.base.UiContext;
import io.qameta.allure.Step;

/**
 * Базовый flow для любого типа залога.
 *
 * Определяет контракт:
 * каждый залог ОБЯЗАН уметь заполняться через fill().
 */
public abstract class BaseCollateralFlow {

    protected final UiContext ui;

    protected BaseCollateralFlow(UiContext ui) {
        this.ui = ui;
    }

    /**
     * Заполнение конкретного типа залога.
     * Реализация — в наследниках.
     */
    @Step("Заполнение данных залога")
    public abstract void fill();
}
