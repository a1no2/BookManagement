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


//    //DBを使う準備
//    private SQLiteDatabase db;
//    private DB_helper db_helper;
//    //データベースの参照位置を保持
//    private Cursor c_p;
//    private Cursor c_w;
//
//    private ArrayList<String> title_arr_p;
//    private ArrayList<String> title_arr_w;
//    private ArrayList<String> id_arr_p;
//    private ArrayList<String> id_arr_w;
//    private ArrayAdapter<String> adapter_p;
//    private ArrayAdapter<String> adapter_w;
//    ListView list_p;
//    ListView list_w;




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

    //未実装のボタンの処理
    public void tst(View v) {
        Toast.makeText(this, "未実装", Toast.LENGTH_SHORT).show();
    }

    private void setAdapter_p() {
//        title_arr_p = new ArrayList<>();
//        id_arr_p = new ArrayList<>();
//
//        c_p = db.query(
//                DB_helper.TABLE_NAME,
//                new String[]{DB_helper.BOOK_NAME,DB_helper.BOOK_ID},
//                null,null,null,null,DB_helper.BOOK_NAME + ""
//        );
//        while (c_p.moveToNext()){
//            title_arr_p.add(c_p.getString(c_p.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
//            id_arr_p.add(c_p.getString(c_p.getColumnIndexOrThrow(DB_helper.BOOK_ID)));
//
//        }
//        adapter_p = new ArrayAdapter<String>(
//                getApplicationContext(),
//                android.R.layout.simple_dropdown_item_1line,
//                title_arr_p
//        );
//        list_p.setAdapter(adapter_p);
    }

}
