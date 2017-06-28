package com.kykj.demoim.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.kykj.demoim.R;
import com.kykj.demoim.mode.permission.MPermission;
import com.kykj.demoim.utils.LogUtil;
import com.kykj.demoim.utils.MD5;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by vectoria on 2017/6/27.
 */

public class MainPresenter {
    private Context context;
    private final int BASIC_PERMISSION_REQUEST_CODE = 110;
    public MainPresenter(Context context){
        this.context = context;
    }

    public void login(String account, String token){
        if(TextUtils.isEmpty(account)){
            showMsg(context.getResources().getString(R.string.empty_username));
            return;
        }
        if(TextUtils.isEmpty(token)){
            showMsg(context.getResources().getString(R.string.empty_pwd));
            return;
        }
        LoginInfo loginInfo = new LoginInfo(account,tokenFromPassword(token));
        LogUtil.D("loginInfo.getAccount = "+loginInfo.getAccount());
        LogUtil.D("loginInfo.getToken = "+loginInfo.getToken());
        AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class).login(loginInfo);

        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.D("param = "+param.getAccount()+"--account = "+""+param.getToken());
                //��¼�ɹ�����������
            }

            @Override
            public void onFailed(int code) {
                LogUtil.D("onFailed.code = "+code);
            }

            @Override
            public void onException(Throwable exception) {
                LogUtil.D("exception.tostring= "+exception.toString());
            }
        };
        loginRequest.setCallback(callback);
    }


    public String tokenFromPassword(String password) {
        String appKey = readAppKey(context);
        boolean isDemo = "ab73599e8a6efec7abb0142449aa147f".equals(appKey);
        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                LogUtil.D("appkey = "+appInfo.metaData.getString("com.kykj.demoim.appKey"));
                return appInfo.metaData.getString("com.kykj.demoim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ����Ȩ��
     */
    public void requestBasicPermission() {
        MPermission.with((Activity) context)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
    }

    /**
     * ����Ȩ�޹���
     */

    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private void showMsg(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
