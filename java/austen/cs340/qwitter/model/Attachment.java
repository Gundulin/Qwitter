package austen.cs340.qwitter.model;

public class Attachment {

    private String type;
    private String attachmentURL;

    public Attachment() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }
}
