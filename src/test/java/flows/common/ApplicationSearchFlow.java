package flows.common;

import core.base.UiContext;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * открытие кредитной заявки по сохранённому номеру
 */
public class ApplicationSearchFlow {

    private final UiContext ui;

    public ApplicationSearchFlow(UiContext ui) {
        this.ui = ui;
    }

    @Step("Открыть заявку по сохранённому номеру")
    public void openBySavedNumber() {

        ui.basePage
                .clickByDataMarker("Заявки");

        ui.filtersComponent
                .removeFilterIfExists();

        ui.basePage
                .clickButtonByNameCheck("Фильтры/группы");

        ui.menuComponent
                .clickButtonByLiName("Добавить условие");

        ui.lookupComponent
                //.setFieldScheduleDetailByDIMCheckNEW("Номер");
                .setFieldScheduleDetailByDIMCheck(
                        "columnEdit",
                        "Номер"
                );

        ui.contractPage
                .applySavedValueIntoField(
                        "searchEdit",
                        "applyButton"
                );

        ui.gridComponent
                .clickFirstRowInGridAndWaitButton(
                        "grid-FinApplicationSectionDataGridGrid-wrap",
                        "Открыть"
                );

        ui.basePage
                .clickButtonByDataItemMakerCheck("Открыть");
    }


    @Step("Открыть заявку по сохранённому номеру")
    public void openBySavedСontracts() {

        ui.basePage
                .clickByDataMarker("Договоры");

        ui.filtersComponent
                .removeFilterIfExists();

        ui.basePage
                .clickButtonByNameCheck("Фильтры/группы");

        ui.menuComponent
                .clickButtonByLiName("Добавить условие");

        ui.lookupComponent
                //.setFieldScheduleDetailByDIMCheckNEW("Номер");
                .setFieldScheduleDetailByDIMCheck(
                        "columnEdit",
                        "Номер"
                );

        ui.contractPage
                .applySavedValueIntoField(
                        "searchEdit",
                        "applyButton"
                );

        ui.gridComponent
                .clickFirstRowInGridAndWaitButton(
                        "grid-ContractSectionV2DataGridGrid-wrap",
                        "Открыть"
                );

        ui.basePage
                .clickButtonByDataItemMakerCheck("Открыть");
    }
}
