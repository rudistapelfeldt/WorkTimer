package com.tangentsolutions.worktimer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView mTimer, mRestTimer;
    private Button mBtnSet, mBtnRound;

    private Date date, restDate;
    private CountDownTimer timer;
    private CountDownTimer restTimer;
    private Time time;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private int mRoundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTimer = (TextView)findViewById(R.id.timer);
        mRestTimer = (TextView)findViewById(R.id.rest_timer);
        mBtnRound = (Button)findViewById(R.id.timer_round);
        mBtnSet = (Button)findViewById(R.id.timer_set);

        mPref = getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        mRoundCount = mPref.getInt("ROUNDS", 0);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        try {
            date = format.parse(mPref.getString("WORK", ""));
            restDate = format.parse(mPref.getString("REST", ""));

        }catch(ParseException e){
            Log.e(TAG, e.getMessage());
        }

        mTimer.setText(mPref.getString("WORK", ""));
        mRestTimer.setText(mPref.getString("REST", ""));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar calendarrest = Calendar.getInstance();
        calendar.setTime(restDate);



        restTimer = new CountDownTimer(calendarrest.getTimeInMillis(), 1000) {

            public void onTick(long millisUntilFinished) {
                long second = (millisUntilFinished / 1000) % 60;
                long minute = (millisUntilFinished / (1000 * 60)) % 60;
                mRestTimer.setText(minute + ":" + second);
            }

            public void onFinish() {
                mRoundCount -= 1;
                if (mRoundCount != 0){
                    if (timer != null){
                        timer.start();
                    }
                }
            }
        };

        timer = new CountDownTimer(calendar.getTimeInMillis(), 1000) {

            public void onTick(long millisUntilFinished) {
                long second = (millisUntilFinished / 1000) % 60;
                long minute = (millisUntilFinished / (1000 * 60)) % 60;
                mTimer.setText(minute + ":" + second);
            }

            public void onFinish() {
                if (mRoundCount != 0) {
                    restTimer.start();
                }
            }
        }.start();




        mBtnRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mBtnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent set = new Intent(MainActivity.this, SetActivity.class);
                startActivity(set);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
