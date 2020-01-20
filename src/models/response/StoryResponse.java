package models.response;

import models.Status;

import java.util.ArrayList;
import java.util.List;

public class StoryResponse {

    public String lastkey;

    public List<Status> statuses;

    public StoryResponse() {
        statuses = new ArrayList<>();
    }
}
