package austen.cs340.qwitter.model.requests_and_responses.request;

import austen.cs340.qwitter.model.Status;

public class PostRequest {

    private Status status;

    public PostRequest(Status status) {
        this.status = status;
    }
}
