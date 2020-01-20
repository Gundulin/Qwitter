package austen.cs340.qwitter.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.model.Status;
import austen.cs340.qwitter.presenter.PostPresenter;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private PostPresenter presenter = new PostPresenter();
    private String statusMessage;
    private boolean messageFilled = false;
    private Button postButton;
    static final String POST_STATUS = "status posted";
    private static final int UPLOAD_ATTACHMENT = 61;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PostActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post");

        EditText messageEditText = findViewById(R.id.status_message_input);
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    presenter.setMessageFilled(true);
                }
                else {
                    presenter.setMessageFilled(false);
                }

                activatePostButton();
                presenter.setStatusMessage(s.toString());
            }
        });

        final ImageView attachmentPreview = findViewById(R.id.attachment_preview);

        final Button uploadButton = findViewById(R.id.upload);
        uploadButton.setEnabled(false);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent uploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(uploadIntent, UPLOAD_ATTACHMENT);
                if (presenter.getAttachmentType().equals("Image")) {
                    presenter.getAttachmentImage(attachmentPreview);
                }
            }
        });

        EditText attachmentEditText = findViewById(R.id.post_attachment_url);
        attachmentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    presenter.setAttachmentURL(s.toString());
                    uploadButton.setEnabled(true);
                }
            }
        });



        Spinner typeSpinner = findViewById(R.id.attachment_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.attachment_type_spinner, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(arrayAdapter);
        typeSpinner.setOnItemSelectedListener(this);



        postButton = findViewById(R.id.post_button);
        postButton.setEnabled(false);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.post();

                /* Return to the main activity */
                Intent intent = new Intent();
                intent.putExtra(POST_STATUS, "post");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPLOAD_ATTACHMENT && resultCode == RESULT_OK && data != null) {
            Uri profileImage = data.getData();
            System.out.println(profileImage.toString());
        }
    }

    private void activatePostButton() {
        postButton.setEnabled(presenter.isMessageFilled());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.setAttachmentType(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
