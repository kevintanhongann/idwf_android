package org.idwfed.app;

import android.app.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.idwfed.app.api.ApiFactory;
import org.idwfed.app.api.IDWFApi;
import org.idwfed.app.callback.LoginCallback;
import org.idwfed.app.exception.LoginException;
import org.idwfed.app.response.LoginResponse;

import butterknife.InjectView;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world", "admin:admin"
    };

    // UI references.


    @InjectView(R.id.editText_username)
    EditText tvUsername;

    @InjectView(R.id.editText_password)
    EditText tvPassword;

    @InjectView(R.id.editText_serverUrl)
    EditText tvServerUrl;

    @InjectView(R.id.email_sign_in_button)
    Button mEmailSignInButton;

    private IDWFApi idwfApi;

    private OnClickListener onSignInClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(tvUsername.getText())) {
                tvUsername.setError(getString(R.string.text_required_field));
            } else if (TextUtils.isEmpty(tvPassword.getText())) {
                tvPassword.setError(getString(R.string.text_required_field));
            } else if (TextUtils.isEmpty(tvServerUrl.getText())) {
                tvServerUrl.setError(getString(R.string.text_required_field));
            } else {
                idwfApi.login(getApplicationContext(), tvUsername.getText().toString(), tvPassword.getText().toString(), tvServerUrl.getText().toString(), new LoginCallback() {
                    @Override
                    public void onFail(LoginException ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {

                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idwfApi = ApiFactory.INSTANCE.getIDWFApi();
        mEmailSignInButton.setOnClickListener(onSignInClick);
    }
}



