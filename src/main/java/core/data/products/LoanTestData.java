package core.data.products;


public class LoanTestData {

    private final String productName;
    private final double loanAmount;
    private final int loanTermMonths;
    private final double interestRate;
    private final String currency;
    private final double initialPayment;
    private final String productId;

    public LoanTestData(String productName,
                        double loanAmount,
                        int loanTermMonths,
                        double interestRate,
                        String currency,
                        double initialPayment,
                        String productId) {

        this.productName = productName;
        this.loanAmount = loanAmount;
        this.loanTermMonths = loanTermMonths;
        this.interestRate = interestRate;
        this.currency = currency;
        this.initialPayment = initialPayment;
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getLoanTermMonths() {
        return loanTermMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getCurrency() {
        return currency;
    }

    public double getInitialPayment() {
        return initialPayment;
    }

    public String getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "LoanTestData{" +
                "productName='" + productName + '\'' +
                ", loanAmount=" + loanAmount +
                ", loanTermMonths=" + loanTermMonths +
                ", interestRate=" + interestRate +
                ", currency='" + currency + '\'' +
                '}';
    }
}

