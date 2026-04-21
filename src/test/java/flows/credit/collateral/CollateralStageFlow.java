package flows.credit.collateral;

import com.codeborne.selenide.Selenide;
import core.base.UiContext;
import core.data.collateral.CollateralData;
import core.enums.CollateralType;
import flows.credit.collateral.base.BaseCollateralFlow;
import flows.credit.collateral.types.*;
import io.qameta.allure.Step;

import java.util.List;

import static org.openqa.selenium.support.Colors.GOLD;

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

    @Step("Этап залога: заполнение нескольких залогов")
    public void completeCollateralStage(List<CollateralData> collaterals) {


        for (CollateralData data : collaterals) {

            BaseCollateralFlow flow =
                    resolveCollateralFlow(data.getType());

            flow.fill(data);
        }

        completeCollateralAndGuaranteeTask();
    }

    // ==============================

    private BaseCollateralFlow resolveCollateralFlow(
            CollateralType collateralType
    ) {
        return switch (collateralType) {

            case VEHICLE -> new VehicleCollateralFlow(ui);
            case GOODS -> new GoodsCollateralFlow(ui);
            case EQUIPMENT -> new EquipmentCollateralFlow(ui);
            case CASH_DEPOSIT -> new CashDepositCollateralFlow(ui);
            case REAL_ESTATE -> new RealEstateCollateralFlow(ui);
            case GOLD -> new GoldCollateralFlow(ui);
            case MOVABLE_PROPERTY -> new MovablePropertyCollateralFlow(ui);
            case ACQUIRED_PROPERTY -> new AcquiredPropertyCollateralFlow(ui);
            case COTTON -> new CottonCollateralFlow(ui);
            case FUTURE_HARVEST -> new FutureHarvestCollateralFlow(ui);
        };
    }

    private void completeCollateralAndGuaranteeTask() {

        ui.basePage
                .doubleClickByMarker("Обеспечение.Подтип");

        ui.dashboardComponent.clickElementDashboardCheck(
                "Заполните данные обеспечения и поручительства",
                "Execute",
                "//*[@data-item-marker='MiniPage']"
        );

        ui.contractPage
                .setfieldScheduleDetailByDIM("ProcessResult", "Выполнена");
        ui.menuComponent
                .clickButtonByLiName("Выполнена");
        ui.basePage
               .clickButtonByDataItemMaker("SaveEditButton");

        ui.basePage.waitForDocumentsStage();
    }
}
