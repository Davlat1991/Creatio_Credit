package core.base;

import core.base.common.HeaderPage;
import core.base.common.components.FieldComponent;
import core.pages.credit.ConsultationPanelPage;
import core.pages.credit.ContractCreditApplicationPage;
import core.pages.login.LoginPage;
import core.pages.ui.DetailPage;
import steps.login.LoginSteps;
import steps.workspace.WorkspaceSteps;

public class TestContext {

    public final LoginPage loginPage;
    public final LoginSteps loginSteps;
    public final WorkspaceSteps workspaceSteps;
    public final BasePage basePage;

    public final FieldComponent fieldPage;
    public final ContractCreditApplicationPage contractPage;
    public final ConsultationPanelPage consultationPanel;
    public final DetailPage detailPage;
    public final HeaderPage headerPage;

    public TestContext() {
        this.loginPage = new LoginPage();
        this.loginSteps = new LoginSteps();
        this.workspaceSteps = new WorkspaceSteps();
        this.basePage = new BasePage();

        this.fieldPage = new FieldComponent();
        this.contractPage = new ContractCreditApplicationPage();
        this.consultationPanel = new ConsultationPanelPage();
        this.detailPage = new DetailPage();
        this.headerPage = new HeaderPage();
    }
}
