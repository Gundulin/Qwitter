package models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

public class Attachment {
    private String type;
    private String attachmentURL;

    public Attachment() {}

    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "attachmentURL")
    public String getAttachmentURL() {
        return attachmentURL;
    }
    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }
}
