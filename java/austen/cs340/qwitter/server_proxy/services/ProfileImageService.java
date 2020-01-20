package austen.cs340.qwitter.server_proxy.services;

import java.net.URL;

import austen.cs340.qwitter.model.requests_and_responses.request.ProfileImageRequest;
import austen.cs340.qwitter.server_proxy.tasks.ProfileImageTask;

public class ProfileImageService {

    private static final String CHANGE_PROFILE_URL =
            "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/change-profile-image";

    public ProfileImageService() {}

    public void changeProfileImage(ProfileImageRequest request) {
        ProfileImageTask task = new ProfileImageTask(request);
        try {
            URL url = new URL(CHANGE_PROFILE_URL);
            task.execute(url);
            task.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
