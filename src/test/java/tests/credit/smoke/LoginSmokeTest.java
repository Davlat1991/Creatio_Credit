package tests.credit.smoke;


import core.base.BaseTest;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.contacts.ContactData;
import core.data.mappers.LoginDataMapper;
import core.data.registration.RegistrationIncomeExpensesData;
import core.data.users.LoginData;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;

import flows.common.WorkspaceFlow;
import flows.credit.*;

import flows.credit.registration.client.BaseClientFlow;
import flows.credit.registration.client.EmployeeClientFlow;
import org.testng.annotations.Test;
import core.data.contacts.ContactDataFactory;




public class LoginSmokeTest extends BaseTest {

    @Test
    public void creditApplicationHappyPath() {

        // ============================================================
        // 1. TEST DATA (ТОЛЬКО ЗДЕСЬ)
        // ============================================================

        TestData data = TestDataLoader.load();

        LoginData retailManager1 =
                LoginDataMapper.from(data.user("retailManager1"));
        LoginData underwriter1 =
                LoginDataMapper.from(data.user("underwriter1"));
        LoginData ikok1 =
                LoginDataMapper.from(data.user("ikok1"));
        LoginData ikokgo =
                LoginDataMapper.from(data.user("ikokgo"));
        LoginData cashier1 =
                LoginDataMapper.from(data.user("cashier1"));

        ContactData contact =
                ContactDataFactory.selfdeposit1();

        RegistrationIncomeExpensesData incomeExpensesData =
                data.registrationIncomeExpenses();


        // ============================================================
        // 2. INFRASTRUCTURE FLOWS
        // ============================================================

        AuthorizationFlow authFlow = new AuthorizationFlow(ui);
        WorkspaceFlow workspaceFlow = new WorkspaceFlow(ui);

        // ============================================================
        // 3. BUSINESS FLOWS
        // ============================================================

        ClientSearchFlow clientSearchFlow = new ClientSearchFlow(ui);



        // 🔹 ВАЖНО: выбор типа клиента ТОЛЬКО ЗДЕСЬ
        //BaseClientFlow clientFlow = new SelfEmployedClientFlow(ui); //Тип клиента самозанятый
        BaseClientFlow clientFlow = new EmployeeClientFlow(ui);      //Тип клиента работает в организации
        //BaseClientFlow clientFlow = new OtherIncomeClientFlow(ui);    //Тип клиента имеет другой источник дохода


        // ============================================================
        // 4. RETAIL MANAGER
        // ============================================================

        authFlow.login(retailManager1);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);


        clientSearchFlow.searchClient(
                contact.getLastName(),
                contact.getFirstName(),
                contact.getMiddleName()
        );
    }
}