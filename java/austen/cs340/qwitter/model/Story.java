package austen.cs340.qwitter.model;

import java.util.ArrayList;
import java.util.List;

public class Story {

    private List<Status> statuses;

    public Story() {
        statuses = new ArrayList<>();
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
