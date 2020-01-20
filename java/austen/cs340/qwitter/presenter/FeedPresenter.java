package austen.cs340.qwitter.presenter;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import austen.cs340.qwitter.model.Feed;
import austen.cs340.qwitter.model.Hashtag;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.server_proxy.services.FeedService;

public class FeedPresenter {

    private Feed feed;
    private String userAlias;
    private int pageSize = 10;

    public FeedPresenter() {
        this.feed = new Feed();
    }

    public void loadMoreFeed() {
        FeedService service = new FeedService();
        String lastKey = Model.getInstance().getFeedLastKey();
        service.fetchFeed(userAlias, lastKey, pageSize);
        appendToFeed(Model.getInstance().getCurrentFeed());
    }

    public void refreshFeed() {
        FeedService service = new FeedService();
//        String lastKey = Model.getInstance().getFeedLastKey();
        service.fetchFeed(userAlias, null, pageSize);
        this.feed = Model.getInstance().getCurrentFeed();
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public int getPageSize() {
        return pageSize;
    }

    private void appendToFeed(Feed feed) {
        Status[] statusList = null;
        if (feed != null) {
            statusList = new Status[feed.getStatuses().size()];
            feed.getStatuses().toArray(statusList);
        }


        for (int i = 0; i < statusList.length; i++) {
            if (!this.feed.getStatuses().contains(statusList[i])) {
                this.feed.addStatus(statusList[i]);
            }
        }

    }
}
