package com.example.kenji01.bookmanagement;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LibraryIntentMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_intent_main);

        //URL発行用のインスタンス生成
        final IntentUrlLoader objURL = new IntentUrlLoader();

        Button btn01 = (Button)findViewById(R.id.bt01);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // インテントのインスタンス生成
                Intent intent = new Intent();
                // インテントにアクション及びURLをセット
                intent.setAction(Intent.ACTION_VIEW);

                objURL.createURL();
                intent.setData(Uri.parse(objURL.result));
                // ブラウザ起動
                startActivity(intent);
            }
        });
        Button btn02 = (Button)findViewById(R.id.bt02);
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // インテントのインスタンス生成
                Intent intent = new Intent();
                // インテントにアクション及びURLをセット
                intent.setAction(Intent.ACTION_VIEW);

                //フリーワード
                EditText editText1 = (EditText) findViewById(R.id.txt01);
                String text1 = editText1.getText().toString();

                objURL.createURL(text1);
                intent.setData(Uri.parse(objURL.result));
                // ブラウザ起動
                startActivity(intent);
            }
        });
        Button btn03 = (Button)findViewById(R.id.bt03);
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // インテントのインスタンス生成
                Intent intent = new Intent();
                // インテントにアクション及びURLをセット
                intent.setAction(Intent.ACTION_VIEW);

                //タイトル
                EditText editText2 = (EditText) findViewById(R.id.txt02);
                String text2 = editText2.getText().toString();
                //ISBN
                EditText editText3 = (EditText) findViewById(R.id.txt03);
                String text3 = editText3.getText().toString();
                //著者
                EditText editText4 = (EditText) findViewById(R.id.txt04);
                String text4 = editText4.getText().toString();
                //出版社
                EditText editText5 = (EditText) findViewById(R.id.txt05);
                String text5 = editText5.getText().toString();

                objURL.createURL(text2,text3,text4,text5);
                intent.setData(Uri.parse(objURL.result));
                // ブラウザ起動
                startActivity(intent);
            }
        });
    }
}
