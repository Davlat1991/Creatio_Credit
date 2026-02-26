package core.data.collateral.types;

public class GoldData {

    private final String weight;        // вес
    private final String purity;        // проба
    private final String description;   // описание

    public GoldData(String weight, String purity, String description) {
        this.weight = weight;
        this.purity = purity;
        this.description = description;
    }

    public String getWeight() { return weight; }
    public String getPurity() { return purity; }
    public String getDescription() { return description; }
}
