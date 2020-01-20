package austen.cs340.qwitter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {

    private String alias;
    private String message;
    private List<String> hashtags;
    private List<String> taggedUsers;
    private String timestamp;
    private String attachment;

    public Status(String alias, String message) {
        this.alias = alias;
        this.message = message;
        hashtags = new ArrayList<>();
        taggedUsers = new ArrayList<>();
        timestamp = new Date().toString();
        checkForHashTags(message);
        checkForUsers(message);
    }


    public void addHashtag(String hashtag) {
        hashtags.add(hashtag);
    }

    public void tagUser(String user) {
        taggedUsers.add(user);
    }

    public String getMessage() {
        return message;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public String getAlias() {
        return alias;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * Scans the input string for the '#' character then saves the following characters
     * as a hashtag object.
     * @param messageBody
     */
    private void checkForHashTags(String messageBody) {
        for (int c = 0; c < messageBody.length(); c++) {
            if (messageBody.charAt(c) == '#') {
                StringBuilder hashtagBody = new StringBuilder();
                while (messageBody.charAt(c) != ' ' && messageBody.charAt(c) != '\n') {
                    hashtagBody.append(messageBody.charAt(c));
                    c++;
                    if (c == messageBody.length()) {
                        break;
                    }
                }
                hashtags.add(hashtagBody.toString());
            }
        }
    }

    /**
     * Scans the input string for the '@' character then saves the following
     * characters as an alias then checks for the user via said alias
     * for authenticity.
     * @param messageBody
     */
    private void checkForUsers(String messageBody) {
        for (int c = 0; c < messageBody.length(); c++) {
            if (messageBody.charAt(c) == '@') {
                c++;
                StringBuilder userTag = new StringBuilder();
                while (messageBody.charAt(c) != ' ' && messageBody.charAt(c) != '\n') {
                    userTag.append(messageBody.charAt(c));
                    c++;
                    if (c == messageBody.length()) {
                        break;
                    }
                }
                /* Make sure the user is real */
                /*if (Model.getInstance().getUserByAlias(userTag.toString()) != null) {*/
                    //User user = Model.getInstance().getUserByAlias(userTag.toString());
                    taggedUsers.add(userTag.toString());
                //}
            }
        }
    }
}
