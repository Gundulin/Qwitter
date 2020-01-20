package austen.cs340.qwitter.server_proxy.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import java.net.URL;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.request.GetUserByAliasRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.LoginRequest;
import austen.cs340.qwitter.model.requests_and_responses.response.LoginResponse;
import austen.cs340.qwitter.presenter.FeedPresenter;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;
import austen.cs340.qwitter.server_proxy.services.UserService;
import austen.cs340.qwitter.view.LoginFragment;
import austen.cs340.qwitter.view.MainActivity;

public class LoginTask extends AsyncTask<URL, Integer, Boolean> {

    private LoginRequest loginRequest;
    private LoginResponse response;

    public LoginTask(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {

        ServerProxyInterface server = new ServerProxy(urls[0]);
        response = server.login(loginRequest);

        if (response != null) {
            System.out.println("Logged In");
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            Model.getInstance().setCurrentUserAlias(response.getAlias());
            Model.getInstance().setCurrentAuthToken(response.getAuthToken());
        }
        else {
            Model.getInstance().setCurrentUserAlias(null);
        }
    }
}
