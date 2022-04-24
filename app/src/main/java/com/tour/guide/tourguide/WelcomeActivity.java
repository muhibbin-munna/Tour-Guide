package com.tour.guide.tourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView welcomeText,welcomeSummeryText;
    private WelcomeThread welcomeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initializeAll();

        if (welcomeThread!=null){
            welcomeThread.interrupt();
            welcomeThread=null;
        }
        welcomeThread=new WelcomeThread();
        welcomeThread.start();

        initializeAnimation();
    }


    class WelcomeThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(4000);
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeAnimation() {
        Animation imageAnimation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        Animation welcomeAnimation= AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        Animation rewardAnimation= AnimationUtils.loadAnimation(this,R.anim.righttoleft);

        imageView.setAnimation(imageAnimation);
        welcomeText.setAnimation(welcomeAnimation);
        welcomeSummeryText.setAnimation(rewardAnimation);
    }

    private void initializeAll(){
        imageView=findViewById(R.id.welcomeActivityImageViewId);
        welcomeText=findViewById(R.id.welcomeActivityWelcomeTextViewId);
        welcomeSummeryText=findViewById(R.id.welcomeActivityRewardTextViewId);
    }

    @Override
    protected void onDestroy() {
        if (welcomeThread!=null){
            welcomeThread.interrupt();
            welcomeThread=null;
        }
        super.onDestroy();
    }
}
