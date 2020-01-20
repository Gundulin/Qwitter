package Handlers;

import models.Status;
import models.request.StoryRequest;
import models.response.StoryResponse;
import services.StoryService;

import java.util.ArrayList;
import java.util.List;

public class GetStoryHandler {

    private List<Status> statuses = new ArrayList<>();

    public StoryResponse handleRequest(StoryRequest request) {
        StoryService service = new StoryService();
        return service.getStory(request);
    }
}
