package com.burning.click.burnheadphone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.ResponseHandler.LoginResponseHandler;
import com.burning.click.burnheadphone.net.BHPHttpClient;
import com.burning.click.burnheadphone.net.build.LoginBuild;
import com.burning.click.burnheadphone.util.ToastUtil;

import okhttp3.Response;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private LoginResponseHandler loginResponseHandler;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        initResponseHandler();
        initView();

    }

    @Override
    protected void initResponseHandler() {
        super.initResponseHandler();
        loginResponseHandler=  new LoginResponseHandler(LoginActivity.this){
            @Override
            public void onSuccess(Response response) {
                super.onSuccess(response);
                LogUtil.d(TAG,"reponses="+response.body().charStream());
                LogUtil.d(TAG,"reponses="+response.message());
            }

            @Override
            public void onFailure(Response response) {
                super.onFailure(response);
            }
        };
    }

    @Override
    protected void initView() {
        super.initView();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mLogin = (Button) findViewById(R.id.login_sign_in_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG,"sdfaaaaaaaaaad");
                attemptLogin();
            }
        });
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }





    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            LogUtil.d(9999999);
            ToastUtil.makeText("1634");
            Toast.makeText(LoginActivity.this,"107",Toast.LENGTH_LONG).show();
            BHPHttpClient.getInstance().enque(LoginBuild.test("www.baidu.com"),loginResponseHandler);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    public  void aaaa(){
        LoginResponseHandler loginResponseHandler =  new LoginResponseHandler(LoginActivity.this){
            @Override
            public void onSuccess(Response response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(Response response) {
                super.onFailure(response);
            }
        };

    }




    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.login_sign_in_button:
                attemptLogin();
                break;


            default:
                break;
        }
    }
}

