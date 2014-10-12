package org.idwfed.app;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.idwfed.app.api.ApiFactory;
import org.idwfed.app.api.IDWFApi;
import org.idwfed.app.callback.LoginCallback;
import org.idwfed.app.callback.ValidatorCallback;
import org.idwfed.app.exception.LoginException;
import org.idwfed.app.exception.ValidatorException;
import org.idwfed.app.response.LoginResponse;
import org.idwfed.app.response.ValidatorResponse;
import org.idwfed.app.util.Consts;
import org.idwfed.app.util.LoginSuccessEvent;
import org.idwfed.app.util.PrefUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.editText_username)
    EditText etUsername;

    @InjectView(R.id.editText_password)
    EditText etPassword;

    @InjectView(R.id.editText_serverUrl)
    EditText etServerUrl;

    @InjectView(R.id.email_sign_in_button)
    Button mEmailSignInButton;

    private IDWFApi idwfApi;

    private OnClickListener onSignInClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(etUsername.getText())) {
                etUsername.setError(getString(R.string.text_required_field));
            } else if (TextUtils.isEmpty(etPassword.getText())) {
                etPassword.setError(getString(R.string.text_required_field));
            } else if(URLUtil.isValidUrl(etServerUrl.getText().toString())){
                etServerUrl.setError(getString(R.string.text_invalid_url));
            }else {
                String serverUrl = null;
                if (!TextUtils.isEmpty(etServerUrl.getText().toString())) {
                    serverUrl = etServerUrl.getText().toString();
                } else {
                    serverUrl = getString(R.string.server_url) + getString(R.string.login_path);
                }

                Log.d(Consts.LOGTAG, "serverUrl "+serverUrl);

                if (serverUrl != null) {
                    final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging In", false, false);
                    idwfApi.login(getApplicationContext(), etUsername.getText().toString(), etPassword.getText().toString(), serverUrl, new LoginCallback() {
                        @Override
                        public void onFail(LoginException ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onSuccess(final LoginResponse loginResponse) {
                            if (!loginResponse.getItems().isEmpty()) {

                                String validatorUrl = getString(R.string.server_url) + getString(R.string.login_validator_path);
                                idwfApi.validateLogin(LoginActivity.this, validatorUrl, new ValidatorCallback() {
                                    @Override
                                    public void onFail(ValidatorException ex) {
                                        Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onSuccess(ValidatorResponse response) {
                                        for (ValidatorResponse.User user : response.getUsers()) {
                                            /*if(!user.getUsername().contains("Anonymous User")){

                                            }*/
                                            EventBus.getDefault().postSticky(new LoginSuccessEvent(loginResponse.getItems()));
                                            PrefUtils.setUsername(LoginActivity.this, etUsername.getText().toString());
                                            PrefUtils.setPassword(LoginActivity.this, etPassword.getText().toString());
                                            PrefUtils.setUserDocs(LoginActivity.this, loginResponse.getItems());
                                            finish();
                                            NavUtils.navigateUpTo(LoginActivity.this, new Intent(LoginActivity.this, SharedListActivity.class));
                                        }
                                    }
                                });
                            }
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        idwfApi = ApiFactory.INSTANCE.getIDWFApi();
        mEmailSignInButton.setOnClickListener(onSignInClick);
    }
}



