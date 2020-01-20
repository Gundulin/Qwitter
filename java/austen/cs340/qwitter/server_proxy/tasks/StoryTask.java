package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.response.StoryResponse;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class StoryTask extends AsyncTask<URL, Integer, Boolean> {

    public StoryTask() {}

    @Override
    protected Boolean doInBackground(URL... urls) {
        ServerProxyInterface server = new ServerProxy(urls[0]);
        StoryResponse response = server.getStory("Nothing yet");
        Model.getInstance().setStoryLastKey(response.getLastkey());
        Model.getInstance().getCurrentStory().setStatuses(response.getStatuses());

        if (response != null) {
            System.out.println("Got Story");
            Model.getInstance().getCurrentStory().setStatuses(response.getStatuses());
        }

        return null;
    }
}
