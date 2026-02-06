package tests.credit.resume;

import core.base.BaseTest;
import core.config.Environment;
import core.data.TestData;
import core.data.TestDataLoader;
import core.data.mappers.LoginDataMapper;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;
import flows.common.NavigationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.LoanIssuanceFlow;
import flows.credit.ReviewStageRetailFlow;
import org.testng.annotations.Test;

public class ResumeFromDocumentsStageTest extends BaseTest {

    @Test
    public void resume_from_documents_stage() {

        // === Test data ===
        TestData data = TestDataLoader.load();

        AuthorizationFlow authFlow = new AuthorizationFlow(ui);
        WorkspaceFlow workspaceFlow = new WorkspaceFlow(ui);
        NavigationFlow navigationFlow = new NavigationFlow(ui);
        ReviewStageRetailFlow reviewStageRetailFlow =
                new ReviewStageRetailFlow(ui);
        LoanIssuanceFlow loanIssuanceFlow =
                new LoanIssuanceFlow(ui);


        authFlow.login(LoginDataMapper.from(data.user("retailManager")));
        workspaceFlow.select(Workspace.RETAIL_MANAGER);

        // 🔑 ЛОГИНИМСЯ ПОД IKOK
        authFlow.login(LoginDataMapper.from(data.user("ikok")));
        workspaceFlow.select(Workspace.IKOK);

        navigationFlow.open(
                Environment.BASE_URL +
                        "0/Nui/ViewModule.aspx#CardModuleV2/FinApplicationPage/edit/" +
                        ctx.applicationId
        );

        // продолжаем сценарий
        //reviewStageRetailFlow.completeReview();
        loanIssuanceFlow.issueLoan();
    }
}
