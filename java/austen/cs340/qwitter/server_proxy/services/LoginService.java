package austen.cs340.qwitter.server_proxy.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import austen.cs340.qwitter.model.requests_and_responses.request.LoginRequest;
import austen.cs340.qwitter.server_proxy.tasks.LoginTask;

public class LoginService {

    private static final String LOGIN_URL =
            "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/login";

    public LoginService() {}

    public boolean login(LoginRequest request) {
        LoginTask task = new LoginTask(request);

        try {
            URL url = new URL(LOGIN_URL);
            task.execute(url);
            return task.get();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
