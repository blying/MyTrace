package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bly on 2016/12/6.
 */

public class CreateTrace extends Activity {
    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trace);
        startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(CreateTrace.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
