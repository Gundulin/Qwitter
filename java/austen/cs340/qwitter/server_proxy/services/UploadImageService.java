package austen.cs340.qwitter.server_proxy.services;

import android.graphics.Bitmap;

import java.net.URL;

import austen.cs340.qwitter.model.requests_and_responses.request.UploadImageRequest;
import austen.cs340.qwitter.server_proxy.tasks.UploadImageTask;

public class UploadImageService {


    private static final String UPLOAD_URL =
            "https://kwnu454xlk.execute-api.us-east-2.amazonaws.com/user/upload";

    public UploadImageService() {}

    public boolean uploadImage(Bitmap image) {

        UploadImageTask task = new UploadImageTask(image);

        try {
            URL url = new URL(UPLOAD_URL);
            task.execute(url);
            return task.get();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }




}
