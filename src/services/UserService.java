package services;

import DataAccess.UserDAO;
import models.request.GetUserByAliasRequest;
import models.request.LoginRequest;
import models.request.ProfileImageRequest;
import models.request.RegisterRequest;
import models.response.GetUserByAliasResponse;
import models.response.LoginResponse;
import models.response.RegisterResponse;

public class UserService {

    public UserService() {}

    public GetUserByAliasResponse getUserByAlias(GetUserByAliasRequest request) {
        UserDAO dao = new UserDAO();
        return dao.getUser(request);
    }

    public LoginResponse login(LoginRequest request) {
        UserDAO theDao = new UserDAO();
        return theDao.login(request);
    }

    public String changeProfileImage(ProfileImageRequest request) {
        UserDAO dao = new UserDAO();
        return dao.changeProfileImage(request.alias, request.profileImage);
    }

    public RegisterResponse register(RegisterRequest request) {
        UserDAO userDAO = new UserDAO();
        return userDAO.register(request);
    }
}
