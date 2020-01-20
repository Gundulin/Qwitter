package models.response;

import models.Status;

import java.util.ArrayList;
import java.util.List;

public class FeedResponse {


    public String lastkey;
    public List<Status> statuses;

    public FeedResponse() {
        statuses = new ArrayList<>();
    }
}
