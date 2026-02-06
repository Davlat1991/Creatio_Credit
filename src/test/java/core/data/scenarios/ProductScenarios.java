// core/data/scenarios/ProductScenarios.java
package core.data.scenarios;

public class ProductScenarios {

    private ProductScenarios() {}

    public static ProductScenario standardConsumer50k() {
        return new ProductScenario(
                "Карзхои гуногунмаксад",
                "Барои эхтиёчоти оилави",
                "50000",
                "24",
                "Сомони Чумхурии Точикистон",
                "3",
                "2",
                "Аннуитетный",
                "24"
        );
    }
}
