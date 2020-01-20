package austen.cs340.qwitter.model.requests_and_responses.request;

public class LoginRequest implements Request {

    private String alias;
    private String password;

    public LoginRequest(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }
}
