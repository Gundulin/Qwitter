package Handlers;

import DataAccess.UserDAO;
import models.request.ProfileImageRequest;
import services.UserService;

public class ProfileImageHandler {

    public ProfileImageHandler() {}

    public String handleRequest(ProfileImageRequest request) {
        UserService service = new UserService();
        return service.changeProfileImage(request);
    }
}
