package services;

import DataAccess.FollowerDAO;
import models.request.FollowerRequest;
import models.response.FollowerResponse;

public class FollowerService {

    public FollowerService() {}

    public FollowerResponse getFollowers(FollowerRequest request) {
        FollowerDAO dao = new FollowerDAO();
        return dao.getFollowers(request);
    }

    public FollowerResponse getSubscriptions(FollowerRequest request) {
        FollowerDAO dao = new FollowerDAO();
        return dao.getSubscriptions(request);
    }

    public FollowerResponse getAllFollowers(FollowerRequest request) {
        FollowerDAO dao = new FollowerDAO();
        return dao.getAllFollowers(request);
    }
}
