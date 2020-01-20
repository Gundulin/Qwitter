package austen.cs340.qwitter.view;

import android.app.Activity;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.server_proxy.services.UserService;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;

public class StatusListAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<Status> statuses;

    public StatusListAdapter(Activity context, List<Status> statuses) {
        super(context, R.layout.status_view, statuses);

        this.context = context;
        this.statuses = statuses;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View statusView = inflater.inflate(R.layout.status_view, null, true);
        TextView statusDateField = (TextView) statusView.findViewById(R.id.status_date);
        TextView userTextField = (TextView) statusView.findViewById(R.id.user_name);
        TextView statusTextField = (TextView) statusView.findViewById(R.id.status_message);
        ImageButton profilePicture = (ImageButton) statusView.findViewById(R.id.profile_picture);

        /* Set the values of the rows */
        statusDateField.setText(statuses.get(position).getTimestamp());
        userTextField.setText(statuses.get(position).getAlias());
        statusTextField.setText(statuses.get(position).getMessage());
        UserService service = new UserService();
        service.getUserByAlias(statuses.get(position).getAlias());
        GetImageTask getImageTask = new GetImageTask(profilePicture);
        getImageTask.execute(Model.getInstance().getCurrentViewUserProfileImage());

        /* Set up the button of the user Profile pic */
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userAlias = statuses.get(position).getAlias();
                Intent intent = ProfileActivity.newIntent(getContext(), userAlias, false);
                context.startActivity(intent);
            }
        });

        /* Set up the button for the status activity */
        statusTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setCurrentViewStatus(statuses.get(position));
                Intent intent = StatusActivity.newIntent(getContext());
                context.startActivity(intent);
            }
        });

        ImageView attachmentView = statusView.findViewById(R.id.status_attachment);
        if (statuses.get(position).getAttachment() != null) {
            getImageTask = new GetImageTask(attachmentView);
            getImageTask.execute(statuses.get(position).getAttachment());
        }
        if (statuses.get(position).getTaggedUsers() != null) {
                for (String u : statuses.get(position).getTaggedUsers()) {
                    String regex = "@" + u;
                    Pattern pattern = Pattern.compile(regex);
                    Linkify.addLinks(statusTextField, pattern, regex);
            }
        }
        /* Set up the links to the users */
        if (statuses.get(position).getHashtags() != null) {/* Set up the links for the hashtags */
            for (String h : statuses.get(position).getHashtags()) {
                Pattern patter = Pattern.compile(h);
                Linkify.addLinks(statusTextField, patter, h);
            }
        }

        return statusView;
    }


}
