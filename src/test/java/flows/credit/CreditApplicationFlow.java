package flows.credit;

import core.base.UiContext;
import core.context.CreditTestContext;
import core.enums.ClientType;
import core.enums.RouteType;
import core.enums.Workspace;
import flows.common.AuthorizationFlow;
import flows.common.WorkspaceFlow;
import flows.credit.registration.RegistrationStageFlow;
import flows.credit.registration.client.*;

public class CreditApplicationFlow {

    private final UiContext ui;
    private final AuthorizationFlow authorizationFlow;
    private final WorkspaceFlow workspaceFlow;

    private final ClientSearchFlow clientSearchFlow;
    private final ConsultationStartFlow consultationStartFlow;
    private final ProductSelectionFlow productSelectionFlow;
    private final ApplicationCreationFlow applicationCreationFlow;

    private final RegistrationStageFlow registrationStageFlow;
    private final PreliminaryCheckStageFlow preliminaryCheckStageFlow;
    private final DocumentsStageFlow documentsStageFlow;
    private final ReviewStageRetailFlow reviewStageRetailFlow;
    private final ReviewStageUnderwriterFlow reviewStageUnderwriterFlow;
    private final ClientNotificationStageFlow clientNotificationStageFlow;
    private final LoanIssuanceFlow loanIssuanceFlow;
    private final SigningStageFlow signingStageFlow;
    private final ApplicationFinishFlow applicationFinishFlow;

    public CreditApplicationFlow(UiContext ui) {
        this.ui = ui;
        this.authorizationFlow = new AuthorizationFlow(ui);
        this.workspaceFlow = new WorkspaceFlow(ui);

        this.clientSearchFlow = new ClientSearchFlow(ui);
        this.consultationStartFlow = new ConsultationStartFlow(ui);
        this.productSelectionFlow = new ProductSelectionFlow(ui);
        this.applicationCreationFlow = new ApplicationCreationFlow(ui);

        this.registrationStageFlow = new RegistrationStageFlow(ui);
        this.preliminaryCheckStageFlow = new PreliminaryCheckStageFlow(ui);
        this.documentsStageFlow = new DocumentsStageFlow(ui);
        this.reviewStageRetailFlow = new ReviewStageRetailFlow(ui);
        this.reviewStageUnderwriterFlow = new ReviewStageUnderwriterFlow(ui);
        this.clientNotificationStageFlow = new ClientNotificationStageFlow(ui);
        this.loanIssuanceFlow = new LoanIssuanceFlow(ui);
        this.signingStageFlow = new SigningStageFlow(ui);
        this.applicationFinishFlow = new ApplicationFinishFlow(ui);
    }

    // ============================================================
    // 🚀 ENTRY POINT
    // ============================================================

    public void start(CreditTestContext ctx) {

        validateContext(ctx);

        RouteType routeType = resolveRouteType(ctx);
        System.out.println("▶ Route resolved: " + routeType);

        startCommonPart(ctx);

        switch (routeType) {
            case SIMPLE -> runSimpleRoute(ctx);
            case STANDARD -> runStandardRoute(ctx);
        }
    }

    // ============================================================
    // 🔹 COMMON PART
    // ============================================================

    private void startCommonPart(CreditTestContext ctx) {

        authorizationFlow.login(ctx.operator);
        workspaceFlow.select(ctx.workspace);

        clientSearchFlow.searchClient(
                ctx.contact.getLastName(),
                ctx.contact.getFirstName(),
                ctx.contact.getMiddleName()
        );

        consultationStartFlow.startConsultation(ctx.consultationThemeId);

        productSelectionFlow.selectProduct(
                ctx.productGroup,
                ctx.productName,
                ctx.creditAmount,
                ctx.creditTerm,
                ctx.currency
        );

        applicationCreationFlow.createApplication(
                ctx.paymentDay,
                ctx.gracePeriod,
                ctx.repaymentType,
                ctx.applicationTerm
        );

        registrationStageFlow.completeRegistrationStage(
                ctx.incomeExpensesData,
                resolveClientFlow(ctx)
        );
    }

    // ============================================================
    // 🔀 ROUTES
    // ============================================================

    private void runSimpleRoute(CreditTestContext ctx) {

        preliminaryCheckStageFlow.completePreliminaryCheckStage();

        completeClientNotification(ctx);
        completeLoanIssuance(ctx);
        completeSigningStage(ctx);
        completeApplicationFinish(ctx);

        System.out.println("✔ SIMPLE route finished");
    }

    private void runStandardRoute(CreditTestContext ctx) {

        preliminaryCheckStageFlow.completePreliminaryCheckStage();
        documentsStageFlow.uploadDocumentsLegacy();

        reviewStageRetailFlow.completeReview();

        authorizationFlow.logout();
        authorizationFlow.login(ctx.underwriter);
        workspaceFlow.select(Workspace.UNDERWRITER);

        reviewStageUnderwriterFlow.approveReview("КК4 по заявке");

        completeClientNotification(ctx);
        completeLoanIssuance(ctx);
        completeSigningStage(ctx);
        completeApplicationFinish(ctx);

        System.out.println("✔ STANDARD route finished");
    }

    // ============================================================
    // 🧠 HELPERS
    // ============================================================

    private BaseClientFlow resolveClientFlow(CreditTestContext ctx) {
        return switch (ctx.clientType) {
            case SELF_EMPLOYED -> new SelfEmployedClientFlow(ui);
            case EMPLOYEE -> new EmployeeClientFlow(ui);
            case OTHER_INCOME -> new OtherIncomeClientFlow(ui);
        };
    }

    private RouteType resolveRouteType(CreditTestContext ctx) {

        int amount = Integer.parseInt(ctx.creditAmount);

        if (amount >= 500 && amount <= 30000) {
            return RouteType.SIMPLE;
        }

        if (amount >= 30001 && amount <= 100000) {
            return RouteType.STANDARD;
        }

        throw new IllegalStateException(
                "Unsupported credit amount: " + amount
        );
    }

    private void validateContext(CreditTestContext ctx) {

        if (ctx.clientType == null) {
            throw new IllegalStateException("ClientType must be set");
        }
        if (ctx.operator == null) {
            throw new IllegalStateException("Operator must be set");
        }
        if (ctx.contact == null) {
            throw new IllegalStateException("Contact must be set");
        }
    }

    private void completeClientNotification(CreditTestContext ctx) {

        if (ctx.clientNotificationReceiver == null) {
            System.out.println("ℹ Client notification skipped");
            return;
        }

        authorizationFlow.logout();
        authorizationFlow.login(ctx.retailManager);
        workspaceFlow.select(Workspace.RETAIL_MANAGER);

        clientNotificationStageFlow.completeClientNotification(
                ctx.clientNotificationReceiver
        );
    }

    private void completeLoanIssuance(CreditTestContext ctx) {

        authorizationFlow.logout();
        authorizationFlow.login(ctx.ikok);
        workspaceFlow.select(Workspace.IKOK);

        loanIssuanceFlow.issueLoan();
    }

    private void completeSigningStage(CreditTestContext ctx) {

        authorizationFlow.logout();
        authorizationFlow.login(ctx.cashier);
        workspaceFlow.select(Workspace.CASHIER);

        signingStageFlow.completeSigningStage();
    }

    private void completeApplicationFinish(CreditTestContext ctx) {

        authorizationFlow.logout();
        authorizationFlow.login(ctx.ikok);
        workspaceFlow.select(Workspace.IKOK);

        applicationFinishFlow.completeApplicationFinish();
        authorizationFlow.logout();
    }
}
