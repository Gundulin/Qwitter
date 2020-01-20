package austen.cs340.qwitter.server_proxy.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.request.UploadImageRequest;
import austen.cs340.qwitter.model.requests_and_responses.response.UploadImageResponse;
import austen.cs340.qwitter.server_proxy.ServerProxy;
import austen.cs340.qwitter.server_proxy.ServerProxyInterface;

public class UploadImageTask extends AsyncTask<URL, Integer, Boolean> {

    private Bitmap image;

    public UploadImageTask(Bitmap image) {
        this.image = image;
    }

    @Override
    protected Boolean doInBackground(URL... urls) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, out);
//        encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
//        byte[] bytes = Base64.encode(out.toByteArray(), Base64.DEFAULT);
        String encodedImage = Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);

        ServerProxyInterface server = new ServerProxy(urls[0]);
        UploadImageRequest request = new UploadImageRequest(encodedImage);
        UploadImageResponse response = server.uploadImage(request);

        if (response != null) {
            System.out.println("Got new image URL");
            Model.getInstance().setImageURL(response.getImageURL());
            return true;
        }
        return false;
    }
}
