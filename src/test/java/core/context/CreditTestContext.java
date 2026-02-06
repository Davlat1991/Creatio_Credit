package core.context;

import core.data.contacts.ContactData;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.scenarios.*;
import core.data.users.LoginData;
import core.enums.ClientType;
import core.enums.Workspace;

public class CreditTestContext {

    // ============================================================
    // 🔹 SCENARIO DEFINITION
    // ============================================================
    public ClientType clientType;

    // ============================================================
    // 🔐 ROLES
    // ============================================================
    public LoginData operator;        // стартовый пользователь
    public LoginData retailManager;
    public LoginData underwriter;
    public LoginData ikok;
    public LoginData cashier;

    // ============================================================
    // 🧭 NAVIGATION
    // ============================================================
    public Workspace workspace;

    // ============================================================
    // 👤 CLIENT
    // ============================================================
    public ContactData contact;
    public String consultationThemeId;

    // ============================================================
    // 📦 PRODUCT
    // ============================================================
    public String productGroup;
    public String productName;
    public String creditAmount;
    public String creditTerm;
    public String currency;

    // ============================================================
    // 📝 APPLICATION
    // ============================================================
    public String paymentDay;
    public String gracePeriod;
    public String repaymentType;
    public String applicationTerm;

    // ============================================================
    // 🧾 REGISTRATION
    // ============================================================
    public RegistrationIncomeExpensesData incomeExpensesData;

    // ============================================================
    // 📌 RUNTIME DATA (filled by Flow, NOT by tests)
    // ============================================================
    public String applicationNumber;
    public String applicationId;

    // ============================================================
    // 📢 CLIENT NOTIFICATION
    // ============================================================
    public String clientNotificationReceiver;

    // ============================================================
    // 📢 COLLATERAL
    // ============================================================
    public CollateralTestContext collateral;


    // CreditTestContext.java
    public void apply(ProductScenario scenario) {
        this.productGroup = scenario.productGroup;
        this.productName = scenario.productName;
        this.creditAmount = scenario.creditAmount;
        this.creditTerm = scenario.creditTerm;
        this.currency = scenario.currency;

        this.paymentDay = scenario.paymentDay;
        this.gracePeriod = scenario.gracePeriod;
        this.repaymentType = scenario.repaymentType;
        this.applicationTerm = scenario.applicationTerm;
    }

    public void apply(RolesScenario scenario) {
        this.operator = scenario.operator;
        this.retailManager = scenario.retailManager;
        this.underwriter = scenario.underwriter;
        this.cashier = scenario.cashier;
        this.ikok = scenario.ikok;
    }

    public void apply(IncomeScenario scenario) {
        this.incomeExpensesData = scenario.data;
    }

    public void apply(NotificationScenario scenario) {
        this.clientNotificationReceiver = scenario.receiver;
    }

    // ============================================================
    // 🤝 GUARANTOR
    // ============================================================
    public ContactData guarantor;

    public void apply(GuarantorScenario scenario) {
        this.guarantor = scenario.guarantor;
    }

    // ============================================================
    // 🏠 COLLATERAL
    // ============================================================
    public String collateralType;
    public String collateralDescription;

    public void apply(CollateralScenario scenario) {
        this.collateralType = scenario.type;
        this.collateralDescription = scenario.description;
    }



}
