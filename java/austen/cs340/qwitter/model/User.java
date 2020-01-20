package austen.cs340.qwitter.model;

import android.net.Uri;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private String alias;
    private String name;
    private String profileImage;
    private List<User> followedUsers;
    private List<User> followers;
    private String personID;

    public User(String alias, String name) {
        this.alias = alias;
        this.name = name;
        followedUsers = new ArrayList<User>();
        followers = new ArrayList<User>();
        UUID uuid = UUID.randomUUID();
        personID = uuid.toString();
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    public void follow(User user) {
        followedUsers.add(user);
        //user.addFollower(alias);
    }

    private void addFollower(User user) {
        followers.add(user);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public String getPersonID() {
        return personID;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
