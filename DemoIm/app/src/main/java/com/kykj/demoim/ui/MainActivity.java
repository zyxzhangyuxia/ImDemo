package com.kykj.demoim.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kykj.demoim.base.BaseActivity;
import com.kykj.demoim.R;
import com.kykj.demoim.mode.permission.MPermission;
import com.kykj.demoim.mode.permission.MessageEvent;
import com.kykj.demoim.mode.permission.annotation.OnMPermissionDenied;
import com.kykj.demoim.mode.permission.annotation.OnMPermissionGranted;
import com.kykj.demoim.mode.permission.annotation.OnMPermissionNeverAskAgain;
import com.kykj.demoim.presenter.MainPresenter;
import com.kykj.demoim.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_pwd)
    EditText et_pwd;

    MainPresenter mainPresenter;

    private final int BASIC_PERMISSION_REQUEST_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEventListener();
        EventBus.getDefault().register(this);
    }

    private void initEventListener(){
        mainPresenter = new MainPresenter(this);
        mainPresenter.requestBasicPermission();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    /**
     * 登录
     */
    private void doLogin(){
        String username = et_username.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        mainPresenter.login(username,pwd);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(String msg){
       if(MessageEvent.TAG_LOGIN_SUCCESS.equals(msg)){
           LogUtil.D("接受登录成功的事件" +msg);
           HomeActivity.startActivity(this,null);
           finish();
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
