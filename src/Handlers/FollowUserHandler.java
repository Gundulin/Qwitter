package Handlers;

import models.request.FollowRequest;
import models.response.FollowResponse;
import services.FollowUserService;

public class FollowUserHandler {

    public FollowUserHandler() {}

    public FollowResponse handleRequest(FollowRequest request) {

        FollowUserService service = new FollowUserService();
        return service.followerUser(request);
    }
}
