package austen.cs340.qwitter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.presenter.StatusPresenter;
import austen.cs340.qwitter.server_proxy.services.UserService;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;

public class StatusActivity extends AppCompatActivity {


    private StatusPresenter presenter = new StatusPresenter();

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, StatusActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_view);

        TextView dateView = (TextView) findViewById(R.id.status_date);
        dateView.setText(presenter.getStatus().getTimestamp());

        TextView aliasView = (TextView) findViewById(R.id.user_name);
        aliasView.setText(presenter.getStatus().getAlias());

        TextView messageView = (TextView) findViewById(R.id.status_message);
        messageView.setText(presenter.getStatus().getMessage());

        ImageButton profilePicView = (ImageButton) findViewById(R.id.profile_picture);
        UserService service = new UserService();
        service.getUserByAlias(presenter.getStatus().getAlias());
        GetImageTask getImageTask = new GetImageTask(profilePicView);
        getImageTask.execute(Model.getInstance().getCurrentViewUserProfileImage());

        ImageView attachmentView = (ImageView) findViewById(R.id.status_attachment);
        getImageTask = new GetImageTask(attachmentView);
        getImageTask.execute(presenter.getStatus().getAttachment());

        profilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAlias = presenter.getStatus().getAlias();
                Intent intent = ProfileActivity.newIntent(StatusActivity.this, userAlias, false);
                startActivity(intent);
            }
        });

        /* Tagged users are links */
//        for (final User u : Model.getInstance().getStatusByID(statusID).getTaggedUsers()) {
//            String regex = "@" + u.getAlias();
//            Pattern pattern = Pattern.compile(regex);
//            Linkify.addLinks(messageView, pattern, regex);
//        }

        /* Set up the links for the hashtags */
//        for (String h : Model.getInstance().getStatusByID(statusID).getHashtags()) {
//            Pattern pattern = Pattern.compile(h);
//            Linkify.addLinks(messageView, pattern, h);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}