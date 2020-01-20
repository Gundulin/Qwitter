package austen.cs340.qwitter.presenter;

import android.widget.ImageView;

import austen.cs340.qwitter.model.Attachment;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.server_proxy.services.PostService;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;

public class PostPresenter {

    private String statusMessage;
    private boolean messageFilled = false;
    private String attachmentType;
    private String attachmentURL;

    public PostPresenter() {
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isMessageFilled() {
        return messageFilled;
    }

    public void setMessageFilled(boolean messageFilled) {
        this.messageFilled = messageFilled;
    }

    public void post() {
        String alias = Model.getInstance().getCurrentUserAlias();
        Status status = new Status(alias, statusMessage);
        if (attachmentURL != null) {
            status.setAttachment(attachmentURL);
        }
        PostService service = new PostService();
        service.post(status);
    }

    public void getAttachmentImage(ImageView imageView) {
        GetImageTask task = new GetImageTask(imageView);
        task.execute(this.attachmentURL);
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }
}
