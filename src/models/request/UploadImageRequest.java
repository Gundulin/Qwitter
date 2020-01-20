package models.request;

public class UploadImageRequest {

    private String encodedImage;

    public UploadImageRequest() {}

    public String getImage() {
        return encodedImage;
    }

    public void setImage(String image) {
        this.encodedImage = image;
    }
}
