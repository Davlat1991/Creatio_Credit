package flows.credit.registration.client;

import core.base.UiContext;

public abstract class BaseClientFlow {

    protected final UiContext ctx;

    protected BaseClientFlow(UiContext ctx) {
        this.ctx = ctx;
    }

    public abstract void fill();
}
