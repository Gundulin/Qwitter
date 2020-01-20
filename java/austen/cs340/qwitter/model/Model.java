package austen.cs340.qwitter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    private static Model theData;
    private String imageURL;
    private String subscriptionLastKey;
    private String storyLastKey;
    private String followerLastKey;
    private String feedLastKey;
    private String lastkey;
    private User currentUser;
    private User currentViewUser;
    private String currentViewUserAlias;
    private String currentViewUserProfileImage;
    private String currentUserAlias;
    private String currentAuthToken;
    private Story currentStory;
    private Status currentViewStatus;
    private Map<String, User> usersByAlias = new HashMap<>();
    private Map<String, Status> statusByHashtag = new HashMap<String, Status>();
    private Map<String, Status> statusDataBase = new HashMap<>();
    private Feed currentFeed = new Feed();
    private List<String> followers;
    private List<String> subscriptions;

    public static Model getInstance() {
        if (theData == null) {
            theData = new Model();
        }
        return theData;
    }

    private Model() {
        this.followers = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.currentStory = new Story();
    }

    public Map<String, User> getUsers() {
        return usersByAlias;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getUserByAlias(String alias) {
        return usersByAlias.get(alias);
    }

    public Map<String, Status> getStatusByHashtag() {
        return statusByHashtag;
    }

    public Status getStatusByID(String statusID) {
        return statusDataBase.get(statusID);
    }

    public void addUserToDatabase(User user) {
        usersByAlias.put(user.getAlias(), user);
    }

    public void addStatusViaHashtag(Status status, String hashtag) {
        statusByHashtag.put(hashtag, status);
    }

    public String getCurrentUserAlias() {
        return currentUserAlias;
    }

    public void setCurrentUserAlias(String currentUserAlias) {
        this.currentUserAlias = currentUserAlias;
    }

    public User getCurrentViewUser() {
        return currentViewUser;
    }

    public void setCurrentViewUser(User currentViewUser) {
        this.currentViewUser = currentViewUser;
    }

    public String getCurrentAuthToken() {
        return currentAuthToken;
    }

    public void setCurrentAuthToken(String currentAuthToken) {
        this.currentAuthToken = currentAuthToken;
    }

    public Feed getCurrentFeed() {
        return currentFeed;
    }

    public void setCurrentFeed(Feed currentFeed) {
        this.currentFeed = currentFeed;
    }

    public Story getCurrentStory() {
        return currentStory;
    }

    public String getCurrentViewUserAlias() {
        return currentViewUserAlias;
    }

    public void setCurrentViewUserAlias(String currentViewUserAlias) {
        this.currentViewUserAlias = currentViewUserAlias;
    }

    public String getCurrentViewUserProfileImage() {
        return currentViewUserProfileImage;
    }

    public void setCurrentViewUserProfileImage(String currentViewUserProfileImage) {
        this.currentViewUserProfileImage = currentViewUserProfileImage;
    }

    public String getLastkey() {
        return lastkey;
    }

    public void setLastkey(String lastkey) {
        this.lastkey = lastkey;
    }

    public String getSubscriptionLastKey() {
        return subscriptionLastKey;
    }

    public void setSubscriptionLastKey(String subscriptionLastKey) {
        this.subscriptionLastKey = subscriptionLastKey;
    }

    public String getStoryLastKey() {
        return storyLastKey;
    }

    public void setStoryLastKey(String storyLastKey) {
        this.storyLastKey = storyLastKey;
    }

    public String getFollowerLastKey() {
        return followerLastKey;
    }

    public void setFollowerLastKey(String followerLastKey) {
        this.followerLastKey = followerLastKey;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getFeedLastKey() {
        return feedLastKey;
    }

    public void setFeedLastKey(String feedLastKey) {
        this.feedLastKey = feedLastKey;
    }

    public Status getCurrentViewStatus() {
        return currentViewStatus;
    }

    public void setCurrentViewStatus(Status currentViewStatus) {
        this.currentViewStatus = currentViewStatus;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
