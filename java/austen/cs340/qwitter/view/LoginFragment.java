package austen.cs340.qwitter.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.URI;
import java.net.URISyntaxException;

import austen.cs340.qwitter.R;
import austen.cs340.qwitter.model.Model;
import austen.cs340.qwitter.presenter.LoginPresenter;
import austen.cs340.qwitter.server_proxy.tasks.GetImageTask;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends Fragment {

    /* Buttons */
    private Button loginButton;
    private Button registerButton;
    private Button uploadProfilePicButton;
    /* EditText */
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText userNameField;
    private EditText passwordField;
    private ImageView profileImagePreview;
    private EditText profileURL;
    /* Presenter */
    private LoginPresenter presenter = new LoginPresenter();
    /* Result ID */
    private static final int UPLOAD_RESULT = 17;

    private void activateLoginButton(boolean active) {
        if (active)
        {
            loginButton.setEnabled(true);
        }
        else {
            loginButton.setEnabled(false);
        }
    }

    private void activateRegisterButton(boolean active) {
        if (active)
        {
            registerButton.setEnabled(true);
        }
        else {
            registerButton.setEnabled(false);
        }
    }

    public void switchMainFragment() {
        ((MainActivity) getActivity()).setLoggedIn(true);
        ((MainActivity) getActivity()).switchFragments();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        firstNameField = (EditText) v.findViewById(R.id.firstNameInput);
        firstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    presenter.setFirstNameFill(true);
                }
                else {
                    presenter.setFirstNameFill(false);
                }
                activateRegisterButton(presenter.regButtonActive());
                presenter.setFirstName(s.toString());
            }
        });

        lastNameField = (EditText) v.findViewById(R.id.lastNameInput);
        lastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    presenter.setLastNameFill(true);
                }
                else {
                    presenter.setLastNameFill(false);
                }
                activateRegisterButton(presenter.regButtonActive());
                presenter.setLastName(s.toString());
            }
        });

        userNameField = (EditText) v.findViewById(R.id.userNameInput);
        userNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    presenter.setUserNameFill(true);
                }
                else {
                    presenter.setUserNameFill(false);
                }
                activateLoginButton(presenter.logButtonActive());
                activateRegisterButton(presenter.regButtonActive());
                presenter.setAlias(s.toString());
            }
        });

        passwordField = (EditText) v.findViewById(R.id.passWordInput);
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    presenter.setPasswordFill(true);
                }
                else {
                    presenter.setPasswordFill(false);
                }
                activateLoginButton(presenter.logButtonActive());
                activateRegisterButton(presenter.regButtonActive());
                presenter.setPassword(s.toString());
            }
        });

        loginButton = (Button) v.findViewById(R.id.loginButton);
        loginButton.setEnabled(false);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(presenter.login()) {

//                    Toast.makeText(getContext(), "Welcome back, " + presenter.getAlias(),
//                            Toast.LENGTH_SHORT).show();
                    switchMainFragment();
                }
                else {
                    Toast.makeText(getContext(), "Invalid username or password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton = (Button) v.findViewById(R.id.registerButton);
        registerButton.setEnabled(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                presenter.register();
                Toast.makeText(getContext(), "Welcome to Qwitter, " +
                        presenter.getFirstName() + "!", Toast.LENGTH_SHORT).show();

                switchMainFragment();
            }
        });

        profileImagePreview = (ImageView) v.findViewById(R.id.profile_image_preview);

        uploadProfilePicButton = (Button) v.findViewById(R.id.upload_image_button);
        uploadProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadIntent, UPLOAD_RESULT);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPLOAD_RESULT && resultCode == RESULT_OK && data != null) {
            Uri profileImage = data.getData();
            presenter.setProfileImageURL(profileImage.toString());
            profileImagePreview.setImageURI(profileImage);
            presenter.setProfilePicFill(true);
            activateRegisterButton(presenter.regButtonActive());
        }
    }
}
