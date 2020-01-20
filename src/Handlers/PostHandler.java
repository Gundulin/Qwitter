package Handlers;

import models.Status;
import services.PostalService;

public class PostHandler {

    public PostHandler() {}

    public String handleRequest(Status status) {
        PostalService postalService = new PostalService();
        return postalService.postToQueue(status);
    }
}
