package com.burning.click.burnheadphone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.node.UserNodes;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.ProgressUtil;
import com.burning.click.burnheadphone.util.SpkeyName;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {
    // UI references.
    private android.support.v7.app.AlertDialog.Builder builder;
    @Bind(R.id.email)
    AutoCompleteTextView mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    View mProgreddlay;
    @Bind(R.id.login_sign_in_button)
    Button mLogin;
    private LoginResponseHandler loginResponseHandler;
    private ProgressBar mProgressView;

    private UserNodes userList = null;
    private Button login_ok;
    private Button login_no;
    private Dialog dialog;


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
        String tempJson = SpUtils.getString(LoginActivity.this, SpUtils.BHP_SHARF, SpkeyName.USER_LIST, "");
        LogUtil.d(TAG,"tempJson="+tempJson);
        if (TextUtils.isEmpty(tempJson)) {
            userList = new UserNodes();
        } else {
            Gson gson = new Gson();
            userList = gson.fromJson(tempJson, UserNodes.class);
        }
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
        builder = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.login_alert, null);
        login_ok = (Button) view.findViewById(R.id.login_ok);
        login_no = (Button) view.findViewById(R.id.login_no);
        login_no.setOnClickListener(this);
        login_ok.setOnClickListener(this);
        builder.setView(view);
        dialog = builder.create();
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
            String id = SecurityLib.EncryptToSHA(mEmailView.getText().toString());
            String pwd = SecurityLib.EncryptToSHA(mPasswordView.getText().toString());
            LogUtil.d("id="+id);
            LogUtil.d("pwd="+pwd);
//            BHPHttpClient.getInstance().enque(LoginBuild.test("http://115.28.39.41/indexs.html"), loginResponseHandler);
            // 用户登陆的时候进行的校验
            LogUtil.d(userList.getDatas().size());
            if (userList.getDatas().size() > 0) {
                for (int i = 0; i < userList.getDatas().size(); i++) {
                    LogUtil.d("userList.getDatas().get(i).getUid()="+userList.getDatas().get(i).getUid());
                    LogUtil.d("userList.getDatas().get(i).getPassword()="+userList.getDatas().get(i).getPassword());
                    if (userList.getDatas().get(i).getUid().equals(id) && userList.getDatas().get(i).getPassword().equals(pwd)) {
                        myHandler.sendEmptyMessage(Constant.WHAT.EMPTY_SUCCESS);
                        return;
                    }
                }
            }
            // 没用匹配的用户时候
            dialog.show();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
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
                userNode.setUid(SecurityLib.EncryptToSHA(mEmailView.getText().toString()));
                userNode.setPassword(SecurityLib.EncryptToSHA(mPasswordView.getText().toString()));
                userNode.setEmail(mEmailView.getText().toString());
                // 将用户的个人下信息进行保存
                SpUtils.put(LoginActivity.this, SpUtils.BHP_SHARF, SpkeyName.USER_NODE, UserNode.toJson(userNode));
                // 保存用户到用户列表中
                userList.getDatas().add(userNode);
                SpUtils.put(LoginActivity.this, SpUtils.BHP_SHARF, SpkeyName.USER_LIST, UserNodes.toJson(userList));
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
            case R.id.login_ok:
                myHandler.sendEmptyMessage(Constant.WHAT.EMPTY_SUCCESS);
                break;
            case R.id.login_no:
                finish();
                break;
            default:
                break;
        }
    }
}

