package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;

import java.net.URL;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.request.RegisterRequest;
import austen.cs340.qwitter.model.requests_and_responses.response.RegisterResponse;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class RegisterTask extends AsyncTask<URL, Integer, Boolean> {

    private RegisterRequest registerRequest;

    public RegisterTask(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {


        ServerProxyInterface server = new ServerProxy(urls[0]);
        RegisterResponse response = server.register(registerRequest);

        if (response != null) {
            System.out.println("Registered");
            Model.getInstance().setCurrentUserAlias(response.getAlias());
            Model.getInstance().setCurrentAuthToken(response.getAuthToken());

            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {


    }
}
