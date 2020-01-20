package austen.cs340.qwitter.server_proxy.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import austen.cs340.qwitter.model.User;
import austen.cs340.qwitter.model.requests_and_responses.request.GetUserByAliasRequest;
import austen.cs340.qwitter.server_proxy.tasks.GetUserByAliasTask;

public class UserService {

    public UserService() {}

    public void getUserByAlias(String alias) {
        String USER_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" + alias;
        GetUserByAliasRequest request = new GetUserByAliasRequest(alias);
        GetUserByAliasTask task = new GetUserByAliasTask(request);

        try {
            URL url = new URL(USER_URL);
            task.execute(url);
            task.get();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
}
