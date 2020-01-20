package austen.cs340.qwitter.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.model.Story;
import austen.cs340.qwitter.model.User;
import austen.cs340.qwitter.server_proxy.services.FollowerService;
import austen.cs340.qwitter.server_proxy.services.StoryService;
import austen.cs340.qwitter.server_proxy.services.UserService;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;

public class ProfilePresenter {

    private String currentViewUserAlias;
    private String currentViewUserProfileImage;
    private String userAlias;
    private int page_size = 10 ;
    private Story story;
    private List<String> subscriptions;
    private List<String> followers;
    private int currentSelection = 0;

    public ProfilePresenter() {
        this.followers = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.story = new Story();
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Story getStory() {
        return story;
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

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public void getUserByAlias(String userAlias) {
        UserService service = new UserService();
        service.getUserByAlias(userAlias);
        this.currentViewUserAlias = Model.getInstance().getCurrentViewUserAlias();
        this.currentViewUserProfileImage = Model.getInstance().getCurrentViewUserProfileImage();
    }

    public void fetchStory() {
        StoryService service = new StoryService();
        String lastkey = Model.getInstance().getStoryLastKey();
        service.fetchStory(userAlias, lastkey, page_size);
        this.story = Model.getInstance().getCurrentStory();
    }

    public void appendToStory() {
        StoryService service = new StoryService();
        String lastkey = Model.getInstance().getStoryLastKey();
        service.fetchStory(userAlias, lastkey, page_size);
        combineStories(this.story, Model.getInstance().getCurrentStory());
    }

    public void getUserProfilePicture(ImageView imageView) {
        GetImageTask task = new GetImageTask(imageView);
        task.execute(this.currentViewUserProfileImage);
    }

    public void fetchSubscriptions() {
        FollowerService service = new FollowerService();
        String lastkey = Model.getInstance().getSubscriptionLastKey();
        service.getSubscriptions(userAlias, lastkey, page_size);
        combineStringList(this. subscriptions, Model.getInstance().getSubscriptions());
    }

    public void appendToSubscriptions() {
        FollowerService service = new FollowerService();
        String lastkey = Model.getInstance().getSubscriptionLastKey();
        service.getSubscriptions(userAlias, lastkey, page_size);
        combineStringList(Model.getInstance().getSubscriptions(), this.subscriptions);
    }

    public void fetchFollowers() {
        FollowerService service = new FollowerService();
        String lastkey = Model.getInstance().getFollowerLastKey();
        service.getFollowers(userAlias, lastkey, page_size);
        this.followers = Model.getInstance().getFollowers();
    }

    public void appendToFollowers() {
        FollowerService service = new FollowerService();
        String lastkey = Model.getInstance().getFollowerLastKey();
        service.getFollowers(userAlias, lastkey, page_size);
        combineStringList(Model.getInstance().getFollowers(), this.followers);
    }

    private List<String> combineStringList(List<String> list1, List<String> list2) {
        String[] stringList = new String[list2.size()];
        list2.toArray(stringList);
        for (int i = 0; i < stringList.length; i++) {
            if (!list1.contains(stringList[i])) {
                list1.add(stringList[i]);
            }
        }
        return list1;
    }

    private Story combineStories(Story story1, Story story2) {
        Status[] statusList = new Status[story2.getStatuses().size()];
        story2.getStatuses().toArray(statusList);
        for (int i = 0; i < statusList.length; i++) {
            story1.getStatuses().add(statusList[i]);
        }
        return story1;
    }

    public int getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(int currentSelection) {
        this.currentSelection = currentSelection;
    }
}
