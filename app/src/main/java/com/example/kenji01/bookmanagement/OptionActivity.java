package com.example.kenji01.bookmanagement;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }


    public void series_click(View v){
        Intent i = new Intent(OptionActivity.this,SeriesOptionActivity.class);
        startActivity(i);
    }
}
