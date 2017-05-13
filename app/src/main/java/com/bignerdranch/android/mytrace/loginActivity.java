package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by bly on 2016/12/22.
 */

public class loginActivity extends Activity {
  private Button mLoginButton;
    OkHttpClient mOkHttpClient=new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton=(Button)findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(loginActivity.this,RecentTraceActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
