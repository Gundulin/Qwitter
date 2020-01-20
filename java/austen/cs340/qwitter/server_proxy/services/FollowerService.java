package austen.cs340.qwitter.server_proxy.services;

import java.net.URL;

import austen.cs340.qwitter.server_proxy.tasks.FollowerTask;

public class FollowerService {

    public FollowerService() {}

    public void getSubscriptions(String alias, String lastkey, int page_size) {
        String SUB_URL;
        if (lastkey != null) {
            SUB_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" +
                    "subscriptions/" + alias + "/" + lastkey + "/" + page_size;
        }
        else {
            SUB_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" +
                    "subscriptions/"+ alias + "//" + page_size;
        }

        FollowerTask task = new FollowerTask(false);
        try {
            URL url = new URL(SUB_URL);
            task.execute(url);
            task.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void getFollowers(String alias, String lastkey, int page_size) {
        String FOLLOWER_URL;
        if (lastkey != null) {
            FOLLOWER_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" +
                    "followers/" + alias + "/" + lastkey + "/" + page_size;
        }
        else {
            FOLLOWER_URL = "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/" +
                    "followers/"+ alias + "//" + page_size;
        }

        FollowerTask task = new FollowerTask(true);
        try {
            URL url = new URL(FOLLOWER_URL);
            task.execute(url);
            task.get();
            System.out.println("Got Followers!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
