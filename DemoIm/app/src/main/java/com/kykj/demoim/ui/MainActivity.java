package com.kykj.demoim.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kykj.demoim.R;
import com.kykj.demoim.presenter.MainPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_pwd)
    EditText et_pwd;

    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEventListener();
    }

    private void initEventListener(){
        mainPresenter = new MainPresenter(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    /**
     * µÇÂ¼
     */
    private void doLogin(){
        String username = et_username.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        mainPresenter.login(username,pwd);
    }


}
