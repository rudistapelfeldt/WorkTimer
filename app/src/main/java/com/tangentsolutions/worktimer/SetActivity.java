package com.tangentsolutions.worktimer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SetActivity extends AppCompatActivity {

    EditText work;
    EditText rounds;
    EditText rest;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    Button ok;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        work = (EditText)findViewById(R.id.set_work);
        rounds = (EditText)findViewById(R.id.set_rounds);
        rest = (EditText)findViewById(R.id.set_rest);
        ok = (Button)findViewById(R.id.set_ok);
        cancel = (Button)findViewById(R.id.set_cancel);

        mPref = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        mEditor = mPref.edit();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (work.toString().isEmpty() ||
                        rounds.toString().isEmpty() ||
                        rest.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please set all values", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    mEditor.putInt("ROUNDS", Integer.parseInt(rounds.getText().toString())).apply();
                    mEditor.putString("WORK", work.getText().toString()).apply();
                    mEditor.putString("REST", rest.getText().toString()).apply();

                    Intent intent = new Intent(SetActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
