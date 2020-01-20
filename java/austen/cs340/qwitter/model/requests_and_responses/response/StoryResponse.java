package austen.cs340.qwitter.model.requests_and_responses.response;

import java.util.List;

import austen.cs340.qwitter.model.Status;

public class StoryResponse {

    private String lastkey;

    private List<Status> statuses;

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public String getLastkey() {
        return lastkey;
    }

    public void setLastkey(String lastkey) {
        this.lastkey = lastkey;
    }
}
