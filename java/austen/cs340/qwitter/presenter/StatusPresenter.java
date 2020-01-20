package austen.cs340.qwitter.presenter;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;

public class StatusPresenter {

    private Status status;

    public StatusPresenter() {
        this.status = Model.getInstance().getCurrentViewStatus();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
