package core.base;

import core.assertions.FieldAssertions;
import core.assertions.GridAssertions;
import core.base.common.HeaderPage;
import core.base.common.activity.ActivityComponent;
import core.base.common.address.AddressComponent;
import core.base.common.components.*;
import core.base.common.helpers.DomActions;
import core.base.common.utils.FieldUtils;
import core.base.common.utils.FieldValueResolver;
import core.data.documents.DocumentsTestData;
import core.pages.contacts.ContactAddressPage;
import core.pages.credit.ConsultationPanelPage;
import core.pages.credit.ContractCreditApplicationPage;
import core.pages.credit.RegistrationAdditionalInfoPage;
import core.pages.login.LoginPage;
import core.pages.ui.ContactCommunicationDetailPage;
import core.pages.ui.DetailPage;
import core.pages.ui.ProjectsPage;
import flows.common.AuthorizationFlow;
import flows.common.WorkspaceFlow;
import steps.credit.CreditApplicationAssertions;
import steps.login.LoginSteps;
import steps.workspace.WorkspaceSteps;



public class TestContext {


    public final LoginPage loginPage;
    public final LoginSteps loginSteps;
    public final WorkspaceSteps workspaceSteps;
    public final BasePage basePage;
    public final LookupComponent lookupComponent;
    public final FieldComponent fieldPage;
    public final ContractCreditApplicationPage contractPage;
    public final ConsultationPanelPage consultationPanel;
    public final DetailPage detailPage;
    public final HeaderPage headerPage;
    public final MessageBoxComponent messageBoxComponent;
    public final ButtonsComponent buttonsComponent;
    public final MenuComponent menuComponent;
    public final GridComponent gridComponent;
    public final FieldValueResolver fieldValueResolver;
    public final ContactAddressPage contactAddressPage;
    public final DateFieldComponent dateFieldComponent;
    public final CheckboxComponent checkboxComponent;
    public final DomActions domActions;
    public final DetailComponent detailComponent;
    public final DashboardComponent dashboardComponent;
    public final FileUploadComponent fileUploadComponent;
    public final DocumentsTestData documentTestData;
    public final ProjectsPage projectsPage;
    public final FiltersComponent filtersComponent;
    public final AuthorizationFlow authorizationFlow;
    public final WorkspaceFlow workspaceFlow;
    public final FieldUtils fieldUtils;
    public final FieldAssertions fieldAssertions;
    public final CreditApplicationAssertions creditApplicationAssertions;
    public final PrintComponent printComponent;
    public final ContactCommunicationDetailPage contactCommunicationDetailPage;
    public final RegistrationAdditionalInfoPage additionalInfoPage;
    public final AddressComponent addressComponent;
    public final ActivityComponent activityComponent;















    // Assertions
    public final GridAssertions gridAssertions;



    public TestContext() {
        this.loginPage = new LoginPage();
        this.loginSteps = new LoginSteps();
        this.workspaceSteps = new WorkspaceSteps();
        this.basePage = new BasePage();
        this.lookupComponent = new LookupComponent();
        this.fieldPage = new FieldComponent();
        this.contractPage = new ContractCreditApplicationPage();
        this.consultationPanel = new ConsultationPanelPage();
        this.detailPage = new DetailPage();
        this.headerPage = new HeaderPage();
        this.messageBoxComponent = new MessageBoxComponent();
        this.buttonsComponent = new ButtonsComponent();
        this.menuComponent = new MenuComponent();
        this.gridComponent = new GridComponent();
        this.fieldValueResolver = new FieldValueResolver();
        this.contactAddressPage = new ContactAddressPage();
        this.dateFieldComponent = new DateFieldComponent();
        this.checkboxComponent = new CheckboxComponent();
        this.domActions = new DomActions();
        this.detailComponent = new DetailComponent();
        this.dashboardComponent = new DashboardComponent();
        this.fileUploadComponent = new FileUploadComponent();
        this.documentTestData = new DocumentsTestData();
        this.gridAssertions = new GridAssertions();
        this.filtersComponent = new FiltersComponent();
        this.projectsPage = new ProjectsPage();
        this.authorizationFlow = new AuthorizationFlow(this);
        this.workspaceFlow = new WorkspaceFlow(this);
        this.fieldUtils = new FieldUtils();
        this.fieldAssertions = new FieldAssertions();
        this.printComponent = new PrintComponent();
        this.creditApplicationAssertions = new CreditApplicationAssertions(contractPage);
        this.contactCommunicationDetailPage = new ContactCommunicationDetailPage();
        this.additionalInfoPage = new RegistrationAdditionalInfoPage();
        this.addressComponent = new AddressComponent();
        this.activityComponent = new ActivityComponent();







    }


}
