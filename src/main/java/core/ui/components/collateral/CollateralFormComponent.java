package core.ui.components.collateral;

public class CollateralFormComponent {

    private final UiContext ui;

    public CollateralFormComponent(UiContext ui) {
        this.ui = ui;
    }

    public void setValue(String value) {
        ui.fieldComponent.setField("Стоимость залога", value);
    }
}
