package com.example.kenji01.bookmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    ViewPager viewPager;
    static String tag = "Project";
    private String code;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ページャー
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(
                new Pager(getSupportFragmentManager())
        );
        resultText = (TextView)findViewById(R.id.tst);
    }


    //バーコード
    public void scanBarcode(View v) {
        new IntentIntegrator(this).setBeepEnabled(false).initiateScan();
    }
    //バーコード読み取ったのを受け取る
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                code = result.getContents();
                resultText.setText(code);

                Intent i = new Intent(MainActivity.this, RakutenSearch.class);
                i.putExtra("code",code);
                startActivity(i);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //新しく書籍を登録する
    public void RegistrationIntent(View v){
        Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
        i.putExtra("ID","create");
        startActivity(i);
    }


    public void search(View v) {
        Intent i = new Intent(MainActivity.this, RakutenSearch.class);
        i.putExtra("code","");
        startActivity(i);
    }

    //未実装のボタンの処理
    public void tst(View v) {
        Toast.makeText(this, "未実装", Toast.LENGTH_SHORT).show();
    }

}
