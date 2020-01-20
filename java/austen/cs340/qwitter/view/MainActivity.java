package austen.cs340.qwitter.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;

public class MainActivity extends AppCompatActivity {

    private boolean loggedIn = false;
    private Fragment logFrag;
    private Fragment feedFrag;
    private FragmentManager fragMag = getSupportFragmentManager();
    static final int POST_STATUS = 72;

    public void switchFragments() {

        invalidateOptionsMenu();
        if (!loggedIn) {
            if (feedFrag != null) {
                fragMag.beginTransaction()
                        .remove(feedFrag)
                        .commit();
            }
            logFrag = new LoginFragment();
            fragMag.beginTransaction()
                    .add(R.id.fragment_container, logFrag)
                    .commit();
        }
        else {
            if (logFrag != null) {
                fragMag.beginTransaction()
                        .remove(logFrag)
                        .commit();
            }
            feedFrag = new FeedFragment();
            fragMag.beginTransaction()
                    .add(R.id.fragment_container, feedFrag)
                    .commit();
        }
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logFrag = fragMag.findFragmentById(R.id.fragment_container);
        feedFrag = fragMag.findFragmentById(R.id.fragment_container);

        switchFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!loggedIn) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.user_profile) {
            String userAlias = Model.getInstance().getCurrentUserAlias();
            Intent profileIntent = ProfileActivity.newIntent(MainActivity.this,
                    userAlias, true);
            startActivity(profileIntent);
        }
        else if (item.getItemId() == R.id.post_status) {
            Intent postIntent = PostActivity.newIntent(MainActivity.this);
            startActivity(postIntent);
        }
        else {
            loggedIn = false;
            switchFragments();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Post new status */
        if (requestCode == POST_STATUS && resultCode == RESULT_OK) {
            loggedIn = true;
            switchFragments();
        }
    }
}
