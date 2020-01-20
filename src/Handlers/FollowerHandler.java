package Handlers;

import DataAccess.FollowerDAO;
import models.request.FollowerRequest;
import models.response.FollowerResponse;
import services.FollowerService;

public class FollowerHandler {

    public FollowerHandler() {}

    public FollowerResponse getSubscriptions(FollowerRequest request) {
        FollowerService service = new FollowerService();
        return service.getSubscriptions(request);
    }

    public FollowerResponse getFollowers(FollowerRequest request) {
        FollowerService service = new FollowerService();
        return service.getFollowers(request);
    }
}
