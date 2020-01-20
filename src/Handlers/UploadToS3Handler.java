package Handlers;

import models.request.UploadImageRequest;
import models.response.UploadImageResponse;
import services.UploadImageService;

public class UploadToS3Handler {

    public UploadToS3Handler() {}

    public UploadImageResponse handleRequest(UploadImageRequest image) {

        UploadImageService service = new UploadImageService();
        return new UploadImageResponse(service.uploadImage(image.getImage()));
    }

//    public UploadImageResponse handleRequest(String image) {
//
//        UploadImageService service = new UploadImageService();
//        return new UploadImageResponse(service.uploadImage(image));
//    }
}
