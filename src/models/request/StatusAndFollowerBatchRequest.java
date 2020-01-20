package models.request;

import models.Status;

import java.util.List;

public class StatusAndFollowerBatchRequest {

    public Status status;
    public List<String> aliases;

    public StatusAndFollowerBatchRequest() {}

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
