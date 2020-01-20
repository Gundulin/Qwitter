package austen.cs340.qwitter.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.requests_and_responses.request.ProfileImageRequest;
import austen.cs340.qwitter.server_proxy.services.ProfileImageService;
import austen.cs340.qwitter.server_proxy.services.UploadImageService;

public class ChangeProfilePresenter {

    private String imageName;
    private String profileImage;
    private Bitmap profileBitmap;
    private Context context;

    public ChangeProfilePresenter() {}

    public void changeProfileImage() {

        UploadImageService service = new UploadImageService();
        service.uploadImage(profileBitmap);

//        ProfileImageRequest request =
//                new ProfileImageRequest(Model.getInstance().getCurrentUserAlias(), profileImageURL);
//        ProfileImageService service2 = new ProfileImageService();
//        service2.changeProfileImage(request);
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getProfileBitmap() {
        return profileBitmap;
    }

    public void setProfileBitmap(Bitmap profileBitmap) {
        this.profileBitmap = profileBitmap;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
