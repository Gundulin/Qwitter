package models.response;

public class UploadImageResponse {

    private String imageURL;

    public UploadImageResponse(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
