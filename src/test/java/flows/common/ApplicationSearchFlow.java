package flows.common;

import core.base.UiContext;
import io.qameta.allure.Step;

/**
 * Infrastructure flow:
 * открытие кредитной заявки по сохранённому номеру
 */
public class ApplicationSearchFlow {

    private final UiContext ctx;

    public ApplicationSearchFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    @Step("Открыть заявку по сохранённому номеру")
    public void openBySavedNumber() {

        ctx.basePage
                .clickByDataMarker("Заявки");

        ctx.filtersComponent
                .removeFilterIfExists();

        ctx.basePage
                .clickButtonByNameCheck("Фильтры/группы");

        ctx.menuComponent
                .clickButtonByLiName("Добавить условие");

        ctx.lookupComponent
                //.setFieldScheduleDetailByDIMCheckNEW("Номер");
                .setFieldScheduleDetailByDIMCheck(
                        "columnEdit",
                        "Номер"
                );

        ctx.contractPage
                .applySavedValueIntoField(
                        "searchEdit",
                        "applyButton"
                );

        ctx.gridComponent
                .clickFirstRowInGridAndWaitButton(
                        "grid-FinApplicationSectionDataGridGrid-wrap",
                        "Открыть"
                );

        ctx.basePage
                .clickButtonByDataItemMakerCheck("Открыть");
    }
}
