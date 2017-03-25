package com.boboyuwu.voiceviewsimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.boboyuwu.voiceview.VoiceView;

public class MainActivity extends AppCompatActivity {

    private VoiceView mVoiceView;

    int volumLength=0;
    private boolean isFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVoiceView = (VoiceView) findViewById(R.id.vv);
        mVoiceView.setSpeedLength(8);

    }

    public void onClick(View view){
        isFlag=!isFlag;
        if(isFlag){

            mVoiceView.setRightImage(R.mipmap.speed_img);

        }
        else{
            mVoiceView.setRightImage(null);

        }



    }


}
