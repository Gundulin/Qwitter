package models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "Story")
public class Status {

    private String alias;
    private String message;
    private List<String> hashtags;
    private List<String> taggedUsers;
    private String timestamp;
//    private Attachment attachment;
    private String attachment;

    public Status() {}


    @DynamoDBHashKey(attributeName = "alias")
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @DynamoDBHashKey(attributeName = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = "message")
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
        this.taggedUsers = new ArrayList<>();
        this.hashtags = new ArrayList<>();
//        checkForHashTags(message);
//        checkForUsers(message);
    }

    @DynamoDBAttribute(attributeName = "hashtags")
    public List<String> getHashtags() {
        return hashtags;
    }
    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    @DynamoDBAttribute(attributeName = "taggedUsers")
    public List<String> getTaggedUsers() {
        return taggedUsers;
    }
    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    @DynamoDBAttribute(attributeName = "attachment")
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }


//
//
//    /**
//     * Scans the input string for the '#' character then saves the following characters
//     * as a hashtag object.
//     * @param messageBody
//     */
//    private void checkForHashTags(String messageBody) {
//        for (int c = 0; c < messageBody.length(); c++) {
//            if (messageBody.charAt(c) == '#') {
//                StringBuilder hashtagBody = new StringBuilder();
//                while (messageBody.charAt(c) != ' ' && messageBody.charAt(c) != '\n') {
//                    hashtagBody.append(messageBody.charAt(c));
//                    c++;
//                    if (c == messageBody.length()) {
//                        break;
//                    }
//                }
//                hashtags.add(hashtagBody.toString());
//            }
//        }
//    }
//
//    /**
//     * Scans the input string for the '@' character then saves the following
//     * characters as an alias then checks for the user via said alias
//     * for authenticity.
//     * @param messageBody
//     */
//    private void checkForUsers(String messageBody) {
//        for (int c = 0; c < messageBody.length(); c++) {
//            if (messageBody.charAt(c) == '@') {
//                c++;
//                StringBuilder userTag = new StringBuilder();
//                while (messageBody.charAt(c) != ' ' && messageBody.charAt(c) != '\n') {
//                    userTag.append(messageBody.charAt(c));
//                    c++;
//                    if (c == messageBody.length()) {
//                        break;
//                    }
//                }
//                /* Make sure the user is real */
//                /*if (Model.getInstance().getUserByAlias(userTag.toString()) != null) {*/
//                //User user = Model.getInstance().getUserByAlias(userTag.toString());
//                taggedUsers.add(userTag.toString());
//                //}
//            }
//        }
//    }
}
