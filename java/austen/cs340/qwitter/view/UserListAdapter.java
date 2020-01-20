package austen.cs340.qwitter.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.User;
import austen.cs340.qwitter.server_proxy.services.UserService;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;

public class UserListAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<String> users;

    public UserListAdapter(Activity context, List<String> users) {
        super(context, R.layout.user_view, users);

        this.context = context;
        this.users = users;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View userView = inflater.inflate(R.layout.user_view, null, false);
        TextView aliasView = (TextView) userView.findViewById(R.id.user_view_alias);
        ImageButton profilePicture = (ImageButton) userView.findViewById(R.id.user_view_profile_picture);

        /* Set the values of the rows */
        aliasView.setText(users.get(position));
        UserService service = new UserService();
        service.getUserByAlias(users.get(position));
        GetImageTask task = new GetImageTask(profilePicture);
        task.execute(Model.getInstance().getCurrentViewUserProfileImage());

        /* Set up the button of the user Profile pic */
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAlias = users.get(position);
                Intent intent = ProfileActivity.newIntent(getContext(), userAlias, false);
                context.startActivity(intent);
            }
        });
        return userView;
    }


}

