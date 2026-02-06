package tests.credit.regression.standard;

import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.ContactDataMapper;
import core.enums.ClientType;
import core.enums.Workspace;
import core.context.CreditTestContext;
import core.data.scenarios.ProductScenarios;
import core.data.scenarios.RolesScenarios;
import flows.credit.CreditApplicationFlow;
import org.testng.annotations.Test;

public class StandardRoutePositiveTestNEW extends BaseTest {

    @Test
    public void standard_route_credit_positive_dsl() {

        // ============================================================
        // 1. READ-ONLY TEST DATA
        // ============================================================

        TestData data = TestDataLoader.load();

        // ============================================================
        // 2. ROLES (DSL)
        // ============================================================

        ctx.apply(RolesScenarios.standardRetailFlow());
        ctx.operator = ctx.retailManager;

        // ============================================================
        // 3. CLIENT / WORKSPACE
        // ============================================================

        ctx.clientType = ClientType.OTHER_INCOME;
        ctx.workspace = Workspace.RETAIL_MANAGER;

        ctx.contact =
                ContactDataMapper.from(data.defaultContact());

        ctx.consultationThemeId =
                "consultation-theme-7a0f11cc-756d-474a-885f-1dd64eeca5b3";

        // ============================================================
        // 4. PRODUCT (DSL)
        // ============================================================

        ctx.apply(ProductScenarios.standardConsumer50k());
        // creditAmount = 50000 → STANDARD route automatically

        // ============================================================
        // 5. REGISTRATION
        // ============================================================

        ctx.incomeExpensesData =
                data.registrationIncomeExpenses();

        // ============================================================
        // 6. CLIENT NOTIFICATION
        // ============================================================

        ctx.clientNotificationReceiver =
                "Рустамова Саодатчон Валиевна";

        // ============================================================
        // 🚀 7. START FLOW
        // ============================================================

        new CreditApplicationFlow(ui).start(ctx);
    }
}
