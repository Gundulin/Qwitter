package austen.cs340.qwitter.model.requests_and_responses.request;

public class UploadImageRequest {

    private String encodedImage;

    public UploadImageRequest(String image) {
        this.encodedImage = image;
    }

    public String getImage() {
        return encodedImage;
    }

    public void setImage(String image) {
        this.encodedImage = image;
    }
}
