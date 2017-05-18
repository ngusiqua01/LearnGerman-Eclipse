package com.ufo.learngerman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.TextView;

import com.ufo.learngerman.database.Database;
import com.ufo.learngerman.utils.Utils;

/**
 * Created by sev_user on 11/07/2016.
 */
public class SplashActivity extends Activity {
    Database mWorkoutDatabase;
    Handler mHandler;
    private  final int SPLASH_DURATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mWorkoutDatabase = Database.newInstance(this, Utils.PHRASE_DATABASE_NAME);
//        mWorkoutDatabase.close();
//        mWorkoutDatabase = null;
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();

            }
        }, SPLASH_DURATION_TIME);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
