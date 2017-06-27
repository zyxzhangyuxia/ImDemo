package com.kykj.demoim.presenter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.kykj.demoim.R;
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
        AbortableFuture<LoginInfo> loginRequest = NIMClient.getService(AuthService.class).login(loginInfo);

        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.D("param = "+"account = "+param.getAccount()+""+param.getToken());
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
                return appInfo.metaData.getString("com.kykj.demoim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showMsg(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
