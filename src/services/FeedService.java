package services;

import DataAccess.FeedDAO;
import models.request.FeedRequest;
import models.response.FeedResponse;

public class FeedService {

    public FeedService() {}

    public FeedResponse getFeed(FeedRequest request) {
        FeedDAO dao = new FeedDAO();
        return dao.getFeed(request);
    }
}
