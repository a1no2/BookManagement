package com.example.kenji01.bookmanagement;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class SeriesOptionActivity extends AppCompatActivity {

    ListView listView;
    private ArrayList<String> arr_id;
    private ArrayList<String> arr_name;

    private SQLiteDatabase db;
    private DB_helper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_option);

        db_helper = new DB_helper(getApplicationContext());
        db = db_helper.getWritableDatabase();        //読み書き

        listView = (ListView)findViewById(R.id.listView);
        setListView();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String str = arr_id.get(position) + "\n" + arr_name.get(position);
                t(str);
                create_alertDialog(position);
            }
        });


    }

    private void setListView(){
        try {
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
            arr_id = new ArrayList<String>();
            arr_name = new ArrayList<String>();
            while (c.moveToNext()){
                arr_name.add(c.getString(c.getColumnIndexOrThrow(DB_helper.SERIES_NAME)));
                arr_id.add(c.getString(c.getColumnIndexOrThrow(DB_helper.SERIES_ID)));
            }
            ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arr_name);
            listView.setAdapter(array);
            c.close();

        } catch (SQLException e){

        }
    }

    private void t(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

    }

    private void create_alertDialog(final int i){
        final EditText editView = new EditText(getApplicationContext());
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("シリーズの名前を入力してください。");      //タイトル設定
        alertDialog.setView(editView);
        // OK(肯定的な)ボタンの設定
        alertDialog.setPositiveButton("登録", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OKボタン押下時の処理
                ContentValues valuse = new ContentValues();
                valuse.put(db_helper.SERIES_NAME, editView.getText().toString());
                try{
                    db.update(
                            db_helper.SERIES_TABLE,
                            valuse,
                            db_helper.SERIES_ID + " = " + arr_id.get(i),
                            null
                    );
                    setListView();
                }catch (SQLException e) {
                    t("SQLException\n" + e);
                }
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


}
