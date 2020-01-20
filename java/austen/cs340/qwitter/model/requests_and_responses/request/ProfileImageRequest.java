package austen.cs340.qwitter.model.requests_and_responses.request;

public class ProfileImageRequest {

    private String alias;
    private String profileImage;

    public ProfileImageRequest(String alias, String profileImage) {
        this.alias = alias;
        this.profileImage = profileImage;
    }
}
