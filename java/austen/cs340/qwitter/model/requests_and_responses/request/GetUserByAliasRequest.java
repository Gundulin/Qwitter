package austen.cs340.qwitter.model.requests_and_responses.request;

import austen.cs340.qwitter.model.User;

public class GetUserByAliasRequest {

    private String alias;

    public GetUserByAliasRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
