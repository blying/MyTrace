package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bly on 2016/11/25.
 */
public class AddPointActivity extends Activity {

    private Button completeButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        completeButton=(Button)findViewById(R.id.completeButton);
        cancelButton=(Button)findViewById(R.id.cancelButton);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在地图上标记改点 并将改点信息保存
                Intent intent=new Intent();
                intent.putExtra("point","zuobiao");//把这个点的其他信息传回去
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }


}
