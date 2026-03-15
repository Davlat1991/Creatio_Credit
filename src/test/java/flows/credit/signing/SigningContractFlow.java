package flows.credit.signing;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import io.qameta.allure.Step;

public class SigningContractFlow {

    private final UiContext ui;

    public SigningContractFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Создание договора обеспечения")
    public void createContract(CollateralData collateral) {

        ui.buttonsComponent
                .clickButtonByContainName("Параметры договора");

        ui.gridComponent
                .findRowByText(collateral.getType().getUiName())
                .doubleClick();

        ui.buttonsComponent
                .doubleclickButtonByName("Регистрация");

        ui.buttonsComponent
                .clickButtonByContainName("Действия");

        ui.menuComponent
                .clickButtonByLiName("Создание договора обеспечения");

        ui.messageBoxComponent
                .shouldSeeModalWithText(
                        "Договор успешно создан"
                );
    }
}