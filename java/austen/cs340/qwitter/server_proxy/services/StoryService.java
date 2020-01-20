package austen.cs340.qwitter.server_proxy.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import austen.cs340.qwitter.server_proxy.tasks.StoryTask;

public class StoryService {

    public StoryService() {}

    public void fetchStory(String alias, String lastkey, int page_size) {
        String STORY_URL;
        if (lastkey != null) {
            STORY_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" +
                    alias + "/" + lastkey + "/" + page_size;
        }
        else {
            STORY_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" +
                    alias + "//" + page_size;
        }


        StoryTask task = new StoryTask();

        try {
            URL url = new URL(STORY_URL);
            task.execute(url);
            task.get();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
