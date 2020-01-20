package austen.cs340.qwitter.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    private List<Status> statuses;

    public Feed() {
        statuses = new ArrayList<>();
    }

    public void update(String userID) {

    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public void addStatus(Status status) {
        statuses.add(status);
    }


}
