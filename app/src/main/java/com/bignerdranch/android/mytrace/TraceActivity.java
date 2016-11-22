package com.bignerdranch.android.mytrace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TraceActivity extends AppCompatActivity {

    private Button mRecordTraceButton;
    private Button mStopServiceButton;
    private static int sNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        mRecordTraceButton=(Button)findViewById(R.id.recordTreaceButton);
        mStopServiceButton=(Button)findViewById(R.id.stopServiceButton);
        final Intent intent=new Intent();
        intent.setAction("com.bignerdranch.android.mytrace");
        mRecordTraceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("imei","phone");
                //sNumber=sNumber+1;
                startService(intent);
                Intent i=new Intent();
                i.setClass(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        mStopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyService.stopTrace();
//                stopService(intent);
            }
        });

    }

}
