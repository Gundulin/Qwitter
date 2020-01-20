package austen.cs340.qwitter.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.User;
import austen.cs340.qwitter.presenter.Presenter;
import austen.cs340.qwitter.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity {

    private static final String PROFILE_ACTIVITY_ID = "austen.cs340.qwitter.view_model.ProfileActivity";
    private static final String FROM_MAIN_PAGE = "this came from the main page";
    private static final int UPLOAD_RESULT = 31;
    private static final String PROFILE_IMAGE = "profile changed";
    private Button followerButton;
    private Button subscriptionsButton;
    private ImageButton editProfileButton;
    private Button storyButton;
    private Button appendButton;
    private ProfilePresenter presenter = new ProfilePresenter();

    public static Intent newIntent (Context packageContext, String alias, boolean came) {
        Intent intent = new Intent(packageContext, ProfileActivity.class);
        intent.putExtra(PROFILE_ACTIVITY_ID, alias);
        intent.putExtra(FROM_MAIN_PAGE, came);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setUserAlias(getIntent().getStringExtra(PROFILE_ACTIVITY_ID));
        presenter.getUserByAlias(presenter.getUserAlias());


        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile: " + presenter.getUserAlias());

        appendButton = (Button) findViewById(R.id.profile_append_button);
        appendButton.setVisibility(View.INVISIBLE);

        editProfileButton = (ImageButton) findViewById(R.id.edit_profile_pic);
        presenter.getUserProfilePicture(editProfileButton);

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ChangeProfileImageActivity.newIntent(ProfileActivity.this);
                startActivityForResult(intent, UPLOAD_RESULT);
//                Intent uploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(uploadIntent, UPLOAD_RESULT);
            }
        });

        TextView aliasView = (TextView) findViewById(R.id.profile_user_alias);
        aliasView.setText(presenter.getUserAlias());

        followerButton = (Button) findViewById(R.id.follower_button);
        followerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendButton.setVisibility(View.VISIBLE);
                presenter.setCurrentSelection(1);
                presenter.fetchFollowers();
                UserListAdapter adapter = new UserListAdapter(ProfileActivity.this, presenter.getFollowers());
                ListView followerListView = (ListView) findViewById(R.id.profile_activity_list_view);
                followerListView.setAdapter(adapter);

            }
        });

        subscriptionsButton = (Button) findViewById(R.id.subscription_button);
        subscriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendButton.setVisibility(View.VISIBLE);
                presenter.setCurrentSelection(2);
                presenter.fetchSubscriptions();
                UserListAdapter adapter = new UserListAdapter(ProfileActivity.this, presenter.getSubscriptions());
                ListView subscriptionListView = (ListView) findViewById(R.id.profile_activity_list_view);
                subscriptionListView.setAdapter(adapter);
            }
        });

        storyButton = (Button) findViewById(R.id.story_button);
        storyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendButton.setVisibility(View.VISIBLE);
                presenter.setCurrentSelection(3);
                presenter.fetchStory();
//                if (presenter.getStory() == null) {
//                    presenter.fetchStory();
//                }
                StatusListAdapter adapter = new StatusListAdapter(ProfileActivity.this,
                        Model.getInstance().getCurrentStory().getStatuses());
                ListView storyListView = (ListView) findViewById(R.id.profile_activity_list_view);
                storyListView.setAdapter(adapter);
            }
        });

        appendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(presenter.getCurrentSelection()) {
                    case 1: presenter.appendToFollowers();
                    break;
                    case 2: presenter.appendToSubscriptions();
                    break;
                    case 3: presenter.appendToStory();
                    break;
                    default: //do nothing
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            presenter.setCurrentViewUserProfileImage(data.getStringExtra(PROFILE_IMAGE));
            presenter.getUserProfilePicture(editProfileButton);
        }
    }
}
