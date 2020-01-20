package austen.cs340.qwitter.model.requests_and_responses.request;

public class FollowerRequest {

    private String follower;
    private int page_size;

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
