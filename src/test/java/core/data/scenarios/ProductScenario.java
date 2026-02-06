// core/data/scenarios/ProductScenario.java
package core.data.scenarios;

public class ProductScenario {

    public final String productGroup;
    public final String productName;
    public final String creditAmount;
    public final String creditTerm;
    public final String currency;

    public final String paymentDay;
    public final String gracePeriod;
    public final String repaymentType;
    public final String applicationTerm;

    public ProductScenario(
            String productGroup,
            String productName,
            String creditAmount,
            String creditTerm,
            String currency,
            String paymentDay,
            String gracePeriod,
            String repaymentType,
            String applicationTerm
    ) {
        this.productGroup = productGroup;
        this.productName = productName;
        this.creditAmount = creditAmount;
        this.creditTerm = creditTerm;
        this.currency = currency;
        this.paymentDay = paymentDay;
        this.gracePeriod = gracePeriod;
        this.repaymentType = repaymentType;
        this.applicationTerm = applicationTerm;
    }
}
