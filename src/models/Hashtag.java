package models;

import java.util.UUID;

public class Hashtag {

    public String hashTagBody;
    public String hashTagID;

    public Hashtag(String body) {
        this.hashTagBody = body;
        UUID uuid = UUID.randomUUID();
        this.hashTagID = uuid.toString();
    }
}
