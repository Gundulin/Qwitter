package services;

import DataAccess.S3DAO;

public class UploadImageService {

    public UploadImageService() {}

    public String uploadImage(String image) {
        S3DAO dao = new S3DAO();
        return dao.uploadImage(image);
    }
}
