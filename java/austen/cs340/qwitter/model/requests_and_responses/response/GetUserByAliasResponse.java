package austen.cs340.qwitter.model.requests_and_responses.response;

import austen.cs340.qwitter.model.User;

public class GetUserByAliasResponse {

    private User user;
    private String alias;
    private String profileImage;

    GetUserByAliasResponse() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
