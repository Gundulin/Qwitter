package Handlers;

import DataAccess.FeedDAO;
import models.request.FeedRequest;
import models.response.FeedResponse;
import models.Hashtag;
import models.Status;
import services.FeedService;

import java.util.ArrayList;
import java.util.List;

public class FeedHandler {

    private List<Status> statuses = new ArrayList<>();

    public FeedResponse handleRequest(FeedRequest request) {
        FeedService service = new FeedService();
        return service.getFeed(request);
    }
}
