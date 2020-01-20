package austen.cs340.qwitter.server_proxy.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import austen.cs340.qwitter.model.requests_and_responses.request.RegisterRequest;
import austen.cs340.qwitter.server_proxy.tasks.RegisterTask;

public class RegisterService {

    private static final String REGISTER_URL =
            "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/register";

    public RegisterService() {}

    public void register(RegisterRequest request) {
        RegisterTask task = new RegisterTask(request);

        try {
            URL url = new URL(REGISTER_URL);
            task .execute(url);
            task.get();
        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }
}
