package Handlers;

import DataAccess.UserDAO;
import models.request.RegisterRequest;
import models.response.RegisterResponse;
import services.UserService;

public class RegisterHandler {

    public RegisterHandler() {}

    public RegisterResponse handleRequest(RegisterRequest request) {
        UserService service = new UserService();
        return service.register(request);
    }
}
