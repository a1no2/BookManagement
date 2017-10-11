package com.example.kenji01.bookmanagement;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    //DB
    private SQLiteDatabase db;
    private DB_helper db_helper;

    int radio_ID;

    //ボタン、テキストフィールド、ラジオボタン
    Button clear_btn,registration_btn;
    EditText bookName_editText,author_editText,
            code_editText,price_editText,purchaseDate_editText;
    RadioGroup radioGroup;
    RadioButton have_radio,went_radio;

    //スピーナー
    Spinner seriesSpinner;
    String item;
    int seriesID;
    int spinnerID;


    //新しく登録するならcreate　更新なら,そのIDが入る
    String id_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Intent i = getIntent();
        id_ = i.getStringExtra("ID");

        //ボタン、テキストフィールド、ラジオボタンの紐づけ
        clear_btn = (Button)findViewById(R.id.clear_btn);
        clear_btn.setOnClickListener(this);
        registration_btn = (Button)findViewById(R.id.registration_btn);
        registration_btn.setOnClickListener(this);

        bookName_editText = (EditText)findViewById(R.id.bookName_editText);
        author_editText = (EditText)findViewById(R.id.author_editText);
        code_editText = (EditText)findViewById(R.id.code_editText);
        price_editText = (EditText)findViewById(R.id.price_editText);
        purchaseDate_editText = (EditText)findViewById(R.id.purchaseDate_editText);

        //所持、欲しいもの関連
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        have_radio = (RadioButton)findViewById(R.id.have_radio);
        went_radio = (RadioButton)findViewById(R.id.went_radio);


        //DB準備
        db_helper = new DB_helper(getApplicationContext());
        db = db_helper.getWritableDatabase();        //読み書き

        seriesTable_show();

        //スピーナー関連
        seriesSpinner = (Spinner)findViewById(R.id.seriesSpinner);
        //スピナーのリスナー
        seriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            // アイテム選択時に呼び出される。
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id){
                Spinner spinner = (Spinner)parent;
                item = (String)spinner.getSelectedItem();
                spinnerID = position;
//                spinnerPosition = spinner.getSelectedItemPosition();
                Toast.makeText(RegistrationActivity.this, position + ":" + item, Toast.LENGTH_SHORT).show();
            }
            // 何も選択されなかったときに呼び出される。
            @Override
            public void onNothingSelected(AdapterView<?> arg0){
            }
        });






        //更新する場合そのデータをセット
        if (id_.equals("create")){
            spinnerID = 1;
        } else {
            Cursor c = db.query(
                    DB_helper.BOOK_TABLE,
                    new String []{DB_helper.BOOK_NAME, DB_helper.AUTHOR, DB_helper.CODE,
                            DB_helper.SERIES_ID,
                            String.valueOf(DB_helper.HAVE), String.valueOf(DB_helper.PRICE),
                            String.valueOf(DB_helper.PURCHASE_DATE)},
                    DB_helper.BOOK_ID + " = ?",
                    new String[] {""+String.valueOf(id_)},
                    null,
                    null,
                    null,
                    null
            );
            while (c.moveToNext()){
                bookName_editText.setText(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
                seriesID = c.getInt(c.getColumnIndexOrThrow(DB_helper.SERIES_ID));
                author_editText.setText(c.getString(c.getColumnIndexOrThrow(DB_helper.AUTHOR)));
                code_editText.setText(c.getString(c.getColumnIndexOrThrow(DB_helper.CODE)));
                price_editText.setText(c.getString(c.getColumnIndexOrThrow(DB_helper.PRICE)));
                purchaseDate_editText.setText(c.getString(c.getColumnIndexOrThrow(DB_helper.PURCHASE_DATE)));

                String have = c.getString(c.getColumnIndexOrThrow(DB_helper.HAVE));
                if (have.equals("1" )){
                    have_radio.setChecked(true);
                } else {
                    went_radio.setChecked(true);
                }
            }
            c.close();
            spinnerID = getSpinnerID(seriesID);
            toast("シリーズID:"+(seriesID)+"\nスピナーID:"+spinnerID,true);
        }
        setSpinner();
    }


    //ボタンのクリックイベント
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //クリアボタン
            case R.id.clear_btn:
                // 確認ダイアログの生成
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
                alertDlg.setTitle("クリアしてもよろしいですか？");
                alertDlg.setPositiveButton(
                        "クリア",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // OK ボタンクリック処理
                                bookName_editText.setText("");
                                author_editText.setText("");
                                code_editText.setText("");
                                price_editText.setText("");
                                purchaseDate_editText.setText("");
                                Toast.makeText(RegistrationActivity.this, "クリアしました。", Toast.LENGTH_SHORT).show();
                            }
                        });
                alertDlg.setNegativeButton(
                        "キャンセル",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Cancel ボタンクリック処理
                                Toast.makeText(RegistrationActivity.this, "キャンセルしました。", Toast.LENGTH_SHORT).show();
                            }
                        });

                // 表示
                alertDlg.create().show();
                break;

            //登録ボタン
            case R.id.registration_btn:
                radio_ID = radioGroup.getCheckedRadioButtonId();
//                ArrayList<ArrayList<String>> series = getSeries();


                if (bookName_editText.getText().toString().equals("")) {
                    Toast.makeText(this, "書籍名が空白です", Toast.LENGTH_SHORT).show();
                    break;
                } else if (radio_ID == -1){
                    Toast.makeText(this, "欲しいものリストか所持リストを選択してください", Toast.LENGTH_SHORT).show();


                    //新規登録
                } else if (id_.equals("create")){
                    db_helper = new DB_helper(getApplicationContext());
                    db = db_helper.getWritableDatabase();

                    //所持していたら1 欲しいものリスト0
                    RadioButton radio = (RadioButton)findViewById(radio_ID);
                    String radio_text = radio.getText().toString();
                    if (radio_text.equals("欲しいものリスト")){
                        radio_ID = 0;
                    } else if (radio_text.equals("所持リスト")) {
                        radio_ID = 1;
                    } else {
                        //ありえない値
                        radio_ID = 999;
                        Toast.makeText(this, "error:radioID", Toast.LENGTH_SHORT).show();
                    }
                    seriesID = getSeriesID(spinnerID);
                    String SQL_str = "insert into "
                            + db_helper.BOOK_TABLE + " ( "
                            + db_helper.BOOK_NAME + "," + db_helper.AUTHOR + "," + db_helper.SERIES_ID + "," + db_helper.CODE + ","
                            + db_helper.HAVE + "," + db_helper.PRICE + "," + db_helper.PURCHASE_DATE + ") "
                            + "values ("
                            + "'" + bookName_editText.getText().toString() + "',"
                            + "'" + author_editText.getText().toString() + "',"
                            + "'" + seriesID +"',"
                            + "'" + code_editText.getText().toString() + "',"
                            + "'" + radio_ID + "',"
                            + "'" + price_editText.getText().toString() + "',"
                            + "'" + purchaseDate_editText.getText().toString() + "'"
                            + " );";


                    try{
                        db.execSQL(SQL_str);
                    }catch (SQLException e) {
                        Toast.makeText(getApplicationContext(), "SQLexecSQL\n" + e, Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(), "登録完了しました！", Toast.LENGTH_SHORT).show();
                    this.finish();



                    //更新
                } else {
                    RadioButton radio = (RadioButton)findViewById(radio_ID);
                    String radio_text = radio.getText().toString();

                    //所持していたら1 欲しいものリスト0
                    if (radio_text.equals("欲しいものリスト")){
                        radio_ID = 0;
                    } else if (radio_text.equals("所持リスト")) {
                        radio_ID = 1;
                    } else {
                        radio_ID = 999;     //ありえない値
                        Toast.makeText(this, "error:radio", Toast.LENGTH_SHORT).show();
                    }
                    seriesID = getSeriesID(spinnerID);
                    ContentValues valuse = new ContentValues();
                    valuse.put(db_helper.BOOK_NAME, bookName_editText.getText().toString());
                    valuse.put(db_helper.SERIES_ID, String.valueOf(seriesID));
                    valuse.put(db_helper.AUTHOR, author_editText.getText().toString());
                    valuse.put(db_helper.CODE, code_editText.getText().toString());
                    valuse.put(db_helper.HAVE, radio_ID);
                    valuse.put(db_helper.PRICE, price_editText.getText().toString());
                    valuse.put(db_helper.PURCHASE_DATE, purchaseDate_editText.getText().toString());

                    String where = db_helper.BOOK_ID + " = " + id_;
                    db.update(
                            db_helper.BOOK_TABLE,
                            valuse,
                            where,
                            null
                    );
                    Toast.makeText(this, "更新完了しました！", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                break;
        }
    }



    //スピナーにシリーズをセット
    private void setSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayList<ArrayList<String>> series = getSeries();
        for (int j = 0 ; j < series.get(0).size() ; j++){
            adapter.add(series.get(1).get(j));
        }
        seriesSpinner.setAdapter(adapter);
        seriesSpinner.setSelection((spinnerID));
//            seriesSpinner.setSelection(1);

    }



    //シリーズテーブル出力
    private void seriesTable_show(){
        //シリーズテーブル一覧を表示
        ArrayList<ArrayList<String>> series = getSeries();
        String str = "";
        for (int tst = 0 ; tst < series.get(0).size() ; tst++){
            str += series.get(0).get(tst);
            str += series.get(1).get(tst) + "\n";
        }
        Toast.makeText(RegistrationActivity.this, str, Toast.LENGTH_LONG).show();
    }
    //シリーズテーブル返す
    private ArrayList<ArrayList<String>> getSeries(){
        ArrayList<ArrayList<String>> series = new ArrayList<ArrayList<String>>();
        Cursor c = db.query(
                DB_helper.SERIES_TABLE,
                new String[]{String.valueOf(DB_helper.SERIES_ID),DB_helper.SERIES_NAME},
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<String> id = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();
        while (c.moveToNext()){
            id.add(c.getString(c.getColumnIndexOrThrow(DB_helper.SERIES_ID)) + ":");
            name.add(c.getString(c.getColumnIndexOrThrow(DB_helper.SERIES_NAME)));
        }
        c.close();
        series.add(id);
        series.add(name);
        return series;
    }


    //トースト発行するだけ
    private void toast(String str,boolean lengthShort){
        if (lengthShort){
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
    }


    //シリーズを新規登録する処理
    public void newSeries(View v){
        final EditText editView = new EditText(getApplicationContext());
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("シリーズの名前を入力してください。");      //タイトル設定
        alertDialog.setView(editView);
        // OK(肯定的な)ボタンの設定
        alertDialog.setPositiveButton("登録", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OKボタン押下時の処理
                toast(editView.getText().toString(), true);

                String SQL_str = "insert into "
                        + db_helper.SERIES_TABLE + " ( " + db_helper.SERIES_NAME + ") "
                        + "values ( '" + editView.getText().toString() + "' );";
                try{
                    db.execSQL(SQL_str);
                }catch (SQLException e) {
                    toast("SQLException\n" + e, true);
                }
                toast("登録完了しました", true);
                setSpinner();
            }
        });

        // NG(否定的な)ボタンの設定
        alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // NGボタン押下時の処理
            }
        });
        alertDialog.show();

    }




    //シリーズのid貰ったら、何番目のレコードか(spinnerID)を返す
    private int getSpinnerID(int id){
//        String name;
        Cursor c = db.query(
                DB_helper.SERIES_TABLE,
                new String []{DB_helper.SERIES_ID, DB_helper.SERIES_NAME},
                null,
                null,
                null,
                null,
                null,
                null
        );
        int recordNumber = 0;
        while (c.moveToNext()){
            if (id == c.getInt(c.getColumnIndexOrThrow(DB_helper.SERIES_ID))){
                break;
            }
            recordNumber++;
        }
        c.close();
        return recordNumber;
    }
    //シリーズのレコードナンバー(spinnerID)を貰ったら、その番号のシリーズidを返す
    private int getSeriesID(int recordNumber){
        recordNumber++;
        Cursor c = db.query(
                DB_helper.SERIES_TABLE,
                new String []{DB_helper.SERIES_ID, DB_helper.SERIES_NAME},
                null,
                null,
                null,
                null,
                null,
                null
        );
        int id = 0;
        while (c.moveToNext()){
            id++;
            if (recordNumber == id){
                id = c.getInt(c.getColumnIndexOrThrow(DB_helper.SERIES_ID));
                break;
            }
        }
        c.close();
        return id;
    }

}