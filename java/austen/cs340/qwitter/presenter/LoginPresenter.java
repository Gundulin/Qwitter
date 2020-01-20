package austen.cs340.qwitter.presenter;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URI;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.User;
import austen.cs340.qwitter.model.requests_and_responses.request.LoginRequest;
import austen.cs340.qwitter.model.requests_and_responses.request.RegisterRequest;
import austen.cs340.qwitter.server_proxy.services.LoginService;
import austen.cs340.qwitter.server_proxy.services.RegisterService;
import austen.cs340.qwitter.server_proxy.services.UserService;

public class LoginPresenter implements Presenter {

    private String alias;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImageURL;

    private boolean firstNameFill = false;
    private boolean lastNameFill = false;
    private boolean userNameFill = false;
    private boolean passwordFill = false;
    private boolean profilePicFill = false;

    public LoginPresenter() {}

    public void register() {
        String saltedPassword = saltAndPepper(alias, password);
        RegisterRequest registerRequest = new RegisterRequest(
                firstName, lastName, alias, saltedPassword, profileImageURL);
        Model.getInstance().setCurrentUserAlias(alias);
        RegisterService service = new RegisterService();
        service.register(registerRequest);
        UserService service1 = new UserService();
        service1.getUserByAlias(alias);
    }

    public boolean login() {
        String parsedPassword = saltAndPepper(alias, password);
        LoginRequest loginRequest = new LoginRequest(alias, parsedPassword);
        LoginService service = new LoginService();
        if (service.login(loginRequest)) {
            UserService userService = new UserService();
            userService.getUserByAlias(alias);
            return true;
        }
        return false;
    }

    private String saltAndPepper(String alias, String password) {
        StringBuilder combi = new StringBuilder();

        int last_position = 0;

        for (int i = 0; i < alias.length(); i++) {
            last_position = i;
            if (i > password.length() - 1) {
                break;
            }
            combi.append(alias.charAt(i));
            combi.append(password.charAt(i));
            last_position = i;
        }
        if (last_position < alias.length() - 1) {
            combi.append(alias.substring(last_position));
        }
        if (last_position < password.length() - 1) {
            combi.append(password.substring(last_position));
        }
        int pepper = combi.toString().hashCode();
        pepper = (pepper >> 4) ^ 17*7;
        String salt = Integer.toHexString(pepper);
        System.out.println(salt);

        return salt;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstNameFill(boolean firstNameFill) {
        this.firstNameFill = firstNameFill;
    }

    public void setLastNameFill(boolean lastNameFill) {
        this.lastNameFill = lastNameFill;
    }

    public void setUserNameFill(boolean userNameFill) {
        this.userNameFill = userNameFill;
    }

    public void setPasswordFill(boolean passwordFill) {
        this.passwordFill = passwordFill;
    }

    public boolean logButtonActive() {
        return userNameFill && passwordFill;
    }

    public boolean regButtonActive() {
        return userNameFill && passwordFill && firstNameFill && lastNameFill && profilePicFill;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setProfilePicFill(boolean profilePicFill) {
        this.profilePicFill = profilePicFill;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getAlias() {
        return alias;
    }
}
