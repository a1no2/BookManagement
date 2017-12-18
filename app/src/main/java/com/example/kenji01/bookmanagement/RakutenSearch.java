package com.example.kenji01.bookmanagement;

import android.app.LoaderManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;
import android.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Objects;

import static java.util.Arrays.deepToString;

public class RakutenSearch extends AppCompatActivity{
    String code;
//    boolean codeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rakuten_search);

        Intent i = getIntent();
        code = i.getStringExtra("code");
//        String id = i.getStringExtra("ID");

        TextView text2 = (EditText) findViewById(R.id.txt02);
        text2.setText(code);

        if (!code.equals("")){
            getUrl();
        }


        //textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.bt01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrl();
            }
        });
        findViewById(R.id.bt02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearEditText();
            }
        });

        // JSONの取得
        // getLoaderManager().restartLoader(1, null,this);
    }



    //テキストの取得処理
    public String[] GetTextBox(){
        //タイトル
        EditText editText1 = (EditText) findViewById(R.id.txt01);
        String text1 = editText1.getText().toString();
        //ISBN
        EditText editText2 = (EditText) findViewById(R.id.txt02);
        String text2 = editText2.getText().toString();
        //著者
        EditText editText3 = (EditText) findViewById(R.id.txt03);
        String text3 = editText3.getText().toString();
        //出版社
        EditText editText4 = (EditText) findViewById(R.id.txt04);
        String text4 = editText4.getText().toString();
        //ページ数
        EditText editText5 = (EditText) findViewById(R.id.txt05);
        String text5 = editText5.getText().toString();
        //それぞれの項目を配列に格納
        String[] str = new String[]{text1,text2,text3,text4,text5};
        System.out.println(deepToString(str));

        return str;
    }

    //テキスト内の初期化
    private void ClearEditText() {
        // 処理
        //タイトル
        EditText editText1 = (EditText) findViewById(R.id.txt01);
        editText1.getText().clear();
        //ISBN
        EditText editText2 = (EditText) findViewById(R.id.txt02);
        editText2.getText().clear();
        //著者
        EditText editText3 = (EditText) findViewById(R.id.txt03);
        editText3.getText().clear();
        //出版社
        EditText editText4 = (EditText) findViewById(R.id.txt04);
        editText4.getText().clear();
        //ページ数
        EditText editText5 = (EditText) findViewById(R.id.txt05);
        editText5.getText().clear();
    }



    private void getUrl(){
        String urlText;
        String keyword="";
        String flag ="";
        String page="";

        // ラジオグループのオブジェクトを取得
        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group);
        // チェックされているラジオボタンの ID を取得
        int radioId = rg.getCheckedRadioButtonId();
        // チェックされているラジオボタンオブジェクトを取得
        // RadioButton radioButton = (RadioButton)findViewById(radioId);
        System.out.println("ID:"+radioId);
        //選択されているラジオボタンを判定
        switch (radioId) {
            case R.id.radio_and:
                //AND検索パラメータを付加
                flag = "&orFlag=0";
                System.out.println("and");
                break;
            case R.id.radio_or:
                //OR検索パラメータを付加
                flag = "&orFlag=1";
                System.out.println("or");
                break;
        }


        //フォームデータの取得
        String[] str = GetTextBox();
        //ISBN判定

        Intent i = new Intent(RakutenSearch.this, resultActivity.class);
        if(!Objects.equals(str[1], "")){
            //ISBN検索
            //urlText = "http://192.168.0.3:8080/API/item_info.php?ISBN="+str[1];
            urlText = "http://hiyoko.softether.net:8080/API/item_info.php?ISBN="+str[1];
            i.putExtra("ID","isbn");

        }else {
            i.putExtra("ID","keyword");
            //タイトル判定
            if (!Objects.equals(str[0], "")) {
                keyword = keyword + str[0] + " ";
            }
            //著者判定
            if (!Objects.equals(str[2], "")) {
                keyword = keyword + str[2] + " ";
            }
            //出版社判定
            if (!Objects.equals(str[3], "")) {
                keyword = keyword + str[3];
            }
            //ページ数カウンタ（デバッグ用
            if (!Objects.equals(str[4], "")) {
                //ページ検索パラメータを付加
                page = "&page=" + str[4];
            }
            //ベースURL＋?request=キーワードで1～30件目までを表示
            //キーワードは半角スペースで区切ることで複数指定可能
            //オプション「page=2」で検索結果の31件目～60件目を表示
            //オプション「orFlag=1」でOR検索が可能
            //urlText = "http://192.168.0.3:8080/API/item_search.php?request=" + keyword + page + flag;
            urlText = "http://hiyoko.softether.net:8080/API/item_search.php?request=" + keyword + page + flag;
//            System.out.println(urlText);
        }


        i.putExtra("url",urlText);
        startActivity(i);
        finish();

    }


}