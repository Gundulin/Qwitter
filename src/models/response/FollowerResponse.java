package models.response;

import models.request.FollowerRequest;

import java.util.ArrayList;
import java.util.List;

public class FollowerResponse {

    public List<String> aliases;
    public String lastkey;
    public FollowerResponse() {
        aliases = new ArrayList<>();
    }
}
