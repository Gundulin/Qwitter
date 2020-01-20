package austen.cs340.qwitter.model.requests_and_responses.request;

public class StoryRequest {

    private String userAlias;
    private int key;
    private int page_size;

    public StoryRequest(String userAlias, int key, int page_size) {
        this.userAlias = userAlias;
        this.key = key;
        this.page_size = page_size;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
