package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;
import java.util.concurrent.ExecutionException;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.response.FeedResponse;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class FeedTask extends AsyncTask<URL, Integer, Boolean> {

    public FeedTask() {}

    @Override
    protected Boolean doInBackground(URL... urls) {
        ServerProxyInterface server = new ServerProxy(urls[0]);

        FeedResponse response = server.getFeed("nothing yet");

        if (response != null) {
            System.out.println("Got Feed");
            Model.getInstance().setFeedLastKey(response.getLastkey());
            Model.getInstance().getCurrentFeed().setStatuses(response.getStatuses());
            return true;
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
