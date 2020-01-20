package austen.cs340.qwitter.server_proxy.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import austen.cs340.qwitter.server_proxy.tasks.FeedTask;

public class FeedService {

    public FeedService() {}

    public void fetchFeed(String alias, String key, int page_size) {

        String FEED_URL;
        if (key != null) {
            FEED_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/feed/" +
                    alias + "/" + key + "/" + page_size;
        }
        else {
            FEED_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/feed/" +
                    alias + "//" + page_size;
        }

        FeedTask task = new FeedTask();

        try {
            URL url = new URL(FEED_URL);
            task.execute(url);
            task.get();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.getMessage();
        } catch (ExecutionException e) {
            e.getMessage();
        }
    }
}
