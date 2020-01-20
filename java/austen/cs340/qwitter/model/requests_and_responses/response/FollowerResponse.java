package austen.cs340.qwitter.model.requests_and_responses.response;

import java.util.List;

public class FollowerResponse {

    private List<String> aliases;
    private String lastkey;

    public FollowerResponse() {}

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getLastkey() {
        return lastkey;
    }

    public void setLastkey(String lastkey) {
        this.lastkey = lastkey;
    }
}
