package services;

import DataAccess.FollowDAO;
import models.request.FollowRequest;
import models.response.FollowResponse;

public class FollowUserService {

    public FollowUserService() {}

    public FollowResponse followerUser(FollowRequest request) {
        FollowDAO dao = new FollowDAO();
        return dao.follow(request);
    }
}
