package austen.cs340.qwitter.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.presenter.ChangeProfilePresenter;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;


public class ChangeProfileImageActivity extends AppCompatActivity {

    private Button changeProfileButton;
    private Button uploadButton;
    private ChangeProfilePresenter presenter = new ChangeProfilePresenter();
    private ImageView image;
    private static final String PROFILE_IMAGE = "profile changed";
    private static final int UPLOAD_ATTACHMENT = 72;

    public static Intent newIntent (Context packageContext) {
        Intent intent = new Intent(packageContext, ChangeProfileImageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setContext(this);

        setContentView(R.layout.activity_change_pic);
        getSupportActionBar().setTitle("Change Profile Pic");

        image = (ImageView) findViewById(R.id.change_profile_image);
        GetImageTask task = new GetImageTask(image);
        task.execute(Model.getInstance().getCurrentViewUserProfileImage());

        changeProfileButton = (Button) findViewById(R.id.change_profile_button);
        changeProfileButton.setEnabled(false);
        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeProfileImage();
                Intent intent = new Intent();
                intent.putExtra(PROFILE_IMAGE, presenter.getProfileImage());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        uploadButton = (Button) findViewById(R.id.change_profile_upload);
//        uploadButton.setEnabled(false);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadIntent, UPLOAD_ATTACHMENT);
                GetImageTask task = new GetImageTask(image);
                task.execute(presenter.getProfileImage());
                changeProfileButton.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPLOAD_ATTACHMENT && resultCode == RESULT_OK && data != null) {
            image.setImageURI(data.getData());
            presenter.setProfileImage(data.getData().toString());
            Bitmap bmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            presenter.setProfileBitmap(bmap);
        }
    }


}
