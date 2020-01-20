package services;

import DataAccess.PostDAO;
import QueueAccess.QueueLoader;
import models.Status;
import models.request.StatusAndFollowerBatchRequest;

public class PostalService {

    public PostalService() {}

    public String postToStory(Status status) {
        PostDAO dao = new PostDAO();
        return dao.postToStory(status);
    }

    public String postToQueue(Status status) {
        QueueLoader queueLoader = new QueueLoader();
        queueLoader.loadToQueue(status);
        return "success!";
    }

    public void postToFeed(StatusAndFollowerBatchRequest request) {
        PostDAO dao = new PostDAO();
        dao.postToFeed(request);
    }
}
