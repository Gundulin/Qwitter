package austen.cs340.qwitter.model.requests_and_responses.response;

public class RegisterResponse implements ResponseInterface {

    private String alias;
    private String authToken;

    public RegisterResponse(String alias, String authToken) {
        this.alias = alias;
        this.authToken = authToken;
    }

    public String getAlias() {
        return alias;
    }

    public String getAuthToken() {
        return authToken;
    }
}
