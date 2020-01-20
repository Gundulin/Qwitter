package DataAccess;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

public class S3DAO {

    private static AmazonS3 s3 = AmazonS3ClientBuilder
            .standard().withRegion("us-east-2").build();
    private static final String bucketName = "cs340.qwitter";

    public String uploadImage(String imageString) {
        String fileName = UUID.randomUUID().toString();
        byte[] bytes = "poop".getBytes();
        try {
             bytes = Base64.getDecoder()
                    .decode(imageString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("imageString = " + imageString);
        }
        InputStream in = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType("image/jpeg");
//        metadata.setCacheControl("public");
        s3.putObject(new PutObjectRequest(bucketName, fileName, in, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3.getUrl(bucketName, fileName).toString();
    }
}
