package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;

import austen.cs340.qwitter.model.requests_and_responses.request.ProfileImageRequest;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class ProfileImageTask extends AsyncTask<URL, Integer, Boolean> {

    private ProfileImageRequest request;

    public ProfileImageTask(ProfileImageRequest request) {
        this.request = request;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {

        ServerProxyInterface server = new ServerProxy(urls[0]);
        server.changeProfileImage(request);

        return null;
    }
}
