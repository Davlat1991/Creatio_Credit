package flows.credit.signing;

import core.base.UiContext;
import core.data.collateral.CollateralData;
import io.qameta.allure.Step;

public class SigningPrintFlow {

    private final UiContext ui;

    public SigningPrintFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Печать договора")
    public void printContract(CollateralData collateral) {

        ui.buttonsComponent
                .clickButtonByContainName("Печать");

        ui.menuComponent
                .clickButtonByLiName(
                        collateral.getType().getPrintForm()
                );

        ui.lookupComponent
                .selectResponsiblePerson();

        ui.basePage
                .clickButtonByName("Выбрать");
    }
}