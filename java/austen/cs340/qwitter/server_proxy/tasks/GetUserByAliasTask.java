package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.request.GetUserByAliasRequest;
import austen.cs340.qwitter.model.requests_and_responses.response.GetUserByAliasResponse;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class GetUserByAliasTask extends AsyncTask<URL, Integer, Boolean> {

    private GetUserByAliasRequest request;

    public GetUserByAliasTask(GetUserByAliasRequest request) {
        this.request = request;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {

        ServerProxyInterface server = new ServerProxy(urls[0]);
        GetUserByAliasResponse response = server.getUser("Nothing yet");

        if (response != null) {
            System.out.println("Got user");
//            Model.getInstance().setCurrentViewUser(response.getUser());
            Model.getInstance().setCurrentViewUserAlias(response.getAlias());
            Model.getInstance().setCurrentViewUserProfileImage(response.getProfileImage());
        }

        return null;
    }
}
