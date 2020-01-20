package austen.cs340.qwitter.model;

import java.util.UUID;

public class Hashtag {

    private String hashTagBody;
    private String hashTagID;

    public Hashtag(String body) {
        this.hashTagBody = body;
        UUID uuid = UUID.randomUUID();
        hashTagID = uuid.toString();
    }

    public String getHashTagID() {
        return hashTagID;
    }

    public String getHashTagBody() {
        return hashTagBody;
    }
}
