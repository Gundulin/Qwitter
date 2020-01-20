package austen.cs340.qwitter.server_proxy.services;

import java.net.URL;

import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.server_proxy.tasks.PostTask;

public class PostService {

    private static final String POST_URL =
            "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/post";

    public PostService() {
    }

    public void post(Status status) {
        PostTask task = new PostTask(status);
        try {
            URL url = new URL(POST_URL);
            task.execute(url);
            task.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
