package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.response.FollowerResponse;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class FollowerTask extends AsyncTask<URL, Integer, Boolean> {

    private boolean fromGetFollowers;

    public FollowerTask(boolean follower) {
        this.fromGetFollowers = follower;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {
        ServerProxyInterface server = new ServerProxy(urls[0]);
        FollowerResponse response = server.getSubscriptions();

        if (response != null) {
            if (fromGetFollowers) {
                Model.getInstance().setFollowers(response.getAliases());
                Model.getInstance().setFollowerLastKey(response.getLastkey());
            }
            else {
                Model.getInstance().setSubscriptionLastKey(response.getLastkey());
                Model.getInstance().setSubscriptions(response.getAliases());
            }
        }
        return null;
    }
}
