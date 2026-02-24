package flows.credit.collateral;

import core.base.UiContext;
import core.enums.CollateralType;
import flows.credit.collateral.base.BaseCollateralFlow;
import flows.credit.collateral.types.*;
import io.qameta.allure.Step;

/**
 * Orchestrator flow для этапа "Залог".
 *
 * Отвечает ТОЛЬКО за:
 * - вход на этап залога
 * - выбор типа залога
 * - делегирование заполнения конкретному flow
 */
public class CollateralStageFlow {

    private final UiContext ui;

    public CollateralStageFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Этап залога: заполнение залога типа {collateralType}")
    public void completeCollateralStage(CollateralType collateralType) {

        BaseCollateralFlow collateralFlow =
                resolveCollateralFlow(collateralType);

        collateralFlow.fill();
        completeCollateralAndGuaranteeTask();
    }

    // =====================================================
    // INTERNAL
    // =====================================================


    private BaseCollateralFlow resolveCollateralFlow(
            CollateralType collateralType
    ) {
        return switch (collateralType) {

            case VEHICLE ->
                    new VehicleCollateralFlow(ui);

            case GOODS ->
                    new GoodsCollateralFlow(ui);

            case EQUIPMENT ->
                    new EquipmentCollateralFlow(ui);

            case CASH_DEPOSIT ->
                    new CashDepositCollateralFlow(ui);
        };
    }


    private void completeCollateralAndGuaranteeTask() {

        ui.dashboardComponent.clickElementDashboardCheck(
                "Заполните данные обеспечения и поручительства",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );

        /*ui.contractPage
                .setfieldScheduleDetailByDIM("ProcessResult", "Выполнена");
        ui.menuComponent
                .clickButtonByLiName("Выполнена");
        ui.basePage
               .clickButtonByDataItemMaker("SaveEditButton");*/
    }
}
