package services;

import DataAccess.StoryDAO;
import models.request.StoryRequest;
import models.response.StoryResponse;

public class StoryService {

    public StoryService() {}

    public StoryResponse getStory(StoryRequest request) {
        StoryDAO dao = new StoryDAO();
        return dao.getStory(request);
    }
}
