package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bignerdranch.android.model.Point;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;

/**
 * Created by bly on 2016/11/25.
 */
public class AddPointActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AddPointActivity";

    private Button completeButton;//完成按钮
    private Button cancelButton;//取消按钮
    private Button addVoiceButton;
    private Button addImageButton;
    private Button voiceOne, voiceTwo, voiceThree, voiceFour;
    private ImageView imageOne, imageTwo, imageThree, imageFour;
    private boolean longClicked = false;
    private boolean isPlayed = false;
    private int current = 0;
    // 播放器
    private MediaPlayer mMediaPlayer;

    // “录音机”
    private MediaRecorder mMediaRecorder;

    // 录制的音频文件
    private File[] audioFile = new File[4];

    // 根目录
    private File sdcard = Environment.getExternalStorageDirectory();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        completeButton = (Button) findViewById(R.id.completeButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        addVoiceButton = (Button) findViewById(R.id.addVoiceButton);
        addImageButton = (Button) findViewById(R.id.addImageButton);
        completeButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        addVoiceButton.setOnClickListener(this);
        addImageButton.setOnClickListener(this);

        voiceOne = (Button) findViewById(R.id.voiceOne);
        voiceOne.setOnClickListener(this);
        voiceTwo = (Button) findViewById(R.id.voiceTwo);
        voiceTwo.setOnClickListener(this);
        voiceThree = (Button) findViewById(R.id.voiceThree);
        voiceThree.setOnClickListener(this);
        voiceFour = (Button) findViewById(R.id.voiceFour);
        voiceFour.setOnClickListener(this);
        imageOne = (ImageView) findViewById(R.id.imageOne);
        imageTwo = (ImageView) findViewById(R.id.imageTwo);
        imageThree = (ImageView) findViewById(R.id.imageThree);
        imageFour = (ImageView) findViewById(R.id.imageFour);
        longClicked = true;

        addVoiceButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return longClicked;
            }
        });
        addVoiceButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (current < 4) {
                        //开始录音
                        try {
                            Log.d(TAG, "执行startRecorder()");
                            initRadio(current);
                            startRecorder(current);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    if (current < 4) {
                        Log.d(TAG, "执行stopRecorder()");
                        stopRecorder();
                        if (current == 0) {
                            voiceOne.setVisibility(View.VISIBLE);
                            isPlayed = true;
                        } else if (current == 1) {
                            voiceTwo.setVisibility(View.VISIBLE);
                            isPlayed = true;
                        } else if (current == 2) {
                            voiceThree.setVisibility(View.VISIBLE);
                            isPlayed = true;
                        } else if (current == 3) {
                            voiceFour.setVisibility(View.VISIBLE);
                            isPlayed = true;
                        }
                    }
                    current++;
                    longClicked = false;
                }
                return false;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //初始化录音及播放设备
    public void initRadio(int current) {
        // 播放器
        mMediaPlayer = new MediaPlayer();
        // “录音机”
        mMediaRecorder = new MediaRecorder();
        // 麦克风源录音
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        // 输出格式
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        // 编码格式
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        // 将录制的音频文件存储到SDCard根目录下
        //   for (int i=0;i<4;i++){
        //audioFile = new File(sdcard, "voice"+i+".am");
        //  audioFile=new ArrayList<File>();
        audioFile[current] = new File(sdcard, "voice1." + current + ".amr");
        //  }

        try {
            //   for (int i=0;i<4;i++) {
            audioFile[current].createNewFile();
            //   }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 开始录音
    private void startRecorder(int current) throws Exception {
//        for (int i = 0; i < 4; i++) {
            // 设置录制音频的输出存放文件
            mMediaRecorder.setOutputFile(audioFile[current].getAbsolutePath());
//        }

        // 预备！
        mMediaRecorder.prepare();

        // 开始录音！
        mMediaRecorder.start();
    }

    // 停止录音
    private void stopRecorder() {
        if (mMediaRecorder!=null) {
            // 停止录音
            mMediaRecorder.stop();
        }
        mMediaPlayer.reset();
        // 释放资源
        mMediaRecorder.release();
    }

    // 开始播放声音音频
    private void startPlay(int current) throws Exception {
        // 重置
        mMediaPlayer.reset();
//        for (int i = 0; i < 4; i++) {
            // 设置播放器的声音源
            mMediaPlayer.setDataSource(audioFile[current].getAbsolutePath());
//        }
        // 也可以从一个静态资源文件中加载音频数据源
        // mMediaPlayer.create(this, R.raw.xxx);

        Log.d(TAG, "in startplay()");
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.prepare();
            mMediaPlayer.start();

            // 如果设置循环true，那么将循环播放
            // mMediaPlayer.setLooping(true);
        } else {
            mMediaPlayer.pause();
        }
    }

    // 停止播放
    private void stopPlay() {
        // 如果播放器在播放声音，停止
        mMediaPlayer.stop();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.voiceOne) {
            Log.d(TAG, "点击按钮 isPlayed="+isPlayed);
            if (isPlayed == true) {
                try {
                    startPlay(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPlayed = false;
            } else {
                stopPlay();
                isPlayed = true;
            }
        }
        if (view.getId() == R.id.voiceTwo) {
            if (isPlayed == true) {
                try {
                    startPlay(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPlayed = false;
            } else {
                stopPlay();
                isPlayed = true;
            }
        }
        if (view.getId() == R.id.voiceThree) {
            if (isPlayed == true) {
                try {
                    startPlay(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPlayed = false;
            } else {
                stopPlay();
                isPlayed = true;
            }
        }
        if (view.getId() == R.id.voiceFour) {
            if (isPlayed == true) {
                try {
                    startPlay(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isPlayed = false;
            } else {
                stopPlay();
                isPlayed = true;
            }
        }
        if (view.getId() == R.id.addImageButton) {

        }
        if (view.getId() == R.id.completeButton) {
            //在地图上标记改点 并将改点信息保存
            Intent intent = new Intent();
            Bundle mBundle=new Bundle();
            Point mPoint=new Point(45,62);//这个地方传回去的应该是点的其他信息 不包括坐标 坐标直接在定位的地方添加进去
            mBundle.putSerializable("point",mPoint);
            intent.putExtras(mBundle);
            intent.putExtra("number", "5");//把这是第几个点传回去
            setResult(RESULT_OK, intent);
            finish();
        }
        if (view.getId() == R.id.cancelButton) {
            //取消
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddPoint Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }
}
