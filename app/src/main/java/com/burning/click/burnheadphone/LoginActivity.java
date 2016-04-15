package com.burning.click.burnheadphone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.burning.click.burnheadphone.Log.LogUtil;
import com.burning.click.burnheadphone.ResponseHandler.LoginResponseHandler;
import com.burning.click.burnheadphone.common.SecurityLib;
import com.burning.click.burnheadphone.constant.Constant;
import com.burning.click.burnheadphone.net.BHPHttpClient;
import com.burning.click.burnheadphone.net.build.LoginBuild;
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.ProgressUtil;
import com.burning.click.burnheadphone.util.SpkeyName;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {
    // UI references.

    @Bind(R.id.email)
    AutoCompleteTextView mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    View mProgreddlay;
    @Bind(R.id.login_sign_in_button)
    Button mLogin;
    private LoginResponseHandler loginResponseHandler;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initResponseHandler();
        initView();

    }

    @Override
    protected void initResponseHandler() {
        super.initResponseHandler();
        loginResponseHandler = new LoginResponseHandler(LoginActivity.this) {
            @Override
            public void onSuccess(Response response) {
                super.onSuccess(response);
                LogUtil.d(TAG, "reponses=" + response.message());
                if ("OK".equals(response.message())) {
                    myHandler.sendEmptyMessage(Constant.WHAT.EMPTY_SUCCESS);
                }
            }

            @Override
            public void onFailure(Response response) {
                super.onFailure(response);
            }
        };
    }

    @OnClick(R.id.login_sign_in_button)
    void sign() {
        attemptLogin();
    }

    @Override
    protected void initView() {
        super.initView();
        mProgreddlay = findViewById(R.id.progress);
        mProgressView = (ProgressBar) mProgreddlay.findViewById(R.id.progress_);
        mLogin.setOnClickListener(this);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mProgressView.setAlpha(0.5f);
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
            ProgressUtil.showProgress(mProgressView, true);
            LogUtil.d(9999999);
            BHPHttpClient.getInstance().enque(LoginBuild.test("http://115.28.39.41/indexs.html"), loginResponseHandler);
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


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.WHAT.EMPTY_SUCCESS:
                LogUtil.d(177);
                ProgressUtil.showProgress(mProgressView, false);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                UserNode userNode = new UserNode();
                userNode.setLogin_status(1);
                userNode.setPassword(SecurityLib.EncryptToSHA(mPasswordView.getText().toString()));
                userNode.setEmail(mEmailView.getText().toString());
                SpUtils.put(LoginActivity.this, SpUtils.BHP_SHARF, SpkeyName.USER_NODE, userNode.toJson());
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_sign_in_button:
                attemptLogin();
                break;
            default:
                break;
        }
    }
}

