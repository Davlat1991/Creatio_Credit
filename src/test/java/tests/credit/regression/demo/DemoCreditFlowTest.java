package tests.credit.regression.demo;

import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.LoginDataMapper;
import core.enums.ClientType;
import core.enums.Workspace;
import flows.credit.CreditApplicationFlow;
import org.testng.annotations.Test;
import core.data.contacts.ContactDataFactory;


public class DemoCreditFlowTest extends BaseTest {

    @Test
    public void demo_credit_flow_with_ctx() {

        TestData data = TestDataLoader.load();

        ctx.retailManager = LoginDataMapper.from(data.user("retailManager"));
        ctx.underwriter = LoginDataMapper.from(data.user("underwriter"));
        ctx.ikok = LoginDataMapper.from(data.user("ikok"));
        ctx.cashier = LoginDataMapper.from(data.user("cashier"));

        ctx.operator = ctx.retailManager;

        ctx.clientType = ClientType.OTHER_INCOME;
        ctx.workspace = Workspace.RETAIL_MANAGER;

        ctx.contact = ContactDataFactory.defaultContact();
        ctx.consultationThemeId =
                "consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3";

        ctx.productGroup = "Карзхои гуногунмаксад";
        ctx.productName = "Барои эхтиёчоти оилави";
        ctx.creditAmount = "50000"; // 🔥 STANDARD автоматически
        ctx.creditTerm = "24";
        ctx.currency = "Сомони Чумхурии Точикистон";

        ctx.paymentDay = "3";
        ctx.gracePeriod = "2";
        ctx.repaymentType = "Аннуитетный";
        ctx.applicationTerm = "24";

        ctx.incomeExpensesData = data.registrationIncomeExpenses();
        ctx.clientNotificationReceiver = "Рустамова Саодатчон Валиевна";

        new CreditApplicationFlow(ui).start(ctx);
    }

}
