package Handlers;

import models.request.LoginRequest;
import models.response.LoginResponse;
import services.UserService;

public class LoginHandler {

    public LoginHandler() {}

    public LoginResponse handleRequest(LoginRequest request) {
        UserService service = new UserService();
        return service.login(request);
    }
}
