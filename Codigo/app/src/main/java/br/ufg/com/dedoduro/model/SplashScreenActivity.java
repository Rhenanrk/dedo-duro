package br.ufg.com.dedoduro.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.com.dedoduro.R;
import br.ufg.com.dedoduro.auth.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
        tv.startAnimation(myAnim);
        iv.startAnimation(myAnim);
        final Intent openLogin = new Intent(this, LoginActivity.class);


        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(openLogin);
                    finish();
                }
            }
        };
        timer.start();
    }
}
