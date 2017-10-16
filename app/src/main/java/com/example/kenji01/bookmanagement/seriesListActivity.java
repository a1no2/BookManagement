package com.example.kenji01.bookmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.kenji01.bookmanagement.R.id.listView;

public class seriesListActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DB_helper db_helper;
    String[] series;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_list);

        Intent intent = getIntent();
        series = new String[]{
                intent.getStringExtra("seriesID"),
                intent.getStringExtra("seriesName")
        };

        db_helper = new DB_helper(getApplicationContext());
        db = db_helper.getWritableDatabase();

        TextView title = (TextView)findViewById(R.id.title);
        listView = (ListView)findViewById(R.id.listView);
        String[] str = {"どひ１","どひ２","どひ3"};
        title.setText(series[1]);

//        ArrayList<Item> arrayList = new ArrayList<>();
//        for (int i=0; i<str.length; i++){
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);  // 今回はサンプルなのでデフォルトのAndroid Iconを利用
//            Item item = new Item(bmp, str[i]);
//            arrayList.add(item);
//        }
//
//        ItemAdapter adapter = new ItemAdapter(this, R.layout.item_layout, arrayList);
//        listView.setAdapter(adapter);


        setListView();
    }

    private void setListView(){
        ArrayList<String> id_arr = new ArrayList<>();
        ArrayList<String> name_arr = new ArrayList<>();
        ArrayAdapter<String> adpter;
        Cursor c = db.query(
                DB_helper.BOOK_TABLE,
                new String[]{DB_helper.BOOK_NAME,DB_helper.BOOK_ID},
                DB_helper.SERIES_ID + " = ?",
                new String[] {series[0]},
                null,
                null,
                DB_helper.BOOK_NAME + ""
        );
        ArrayList<Item> array = new ArrayList<>();
        String str = "";
        while (c.moveToNext()){
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);  // 今回はサンプルなのでデフォルトのAndroid Iconを利用
            Item item = new Item(bmp, c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
            array.add(item);

            str += c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME));
            id_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_ID)));
            name_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
        }
        c.close();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        ItemAdapter adapter = new ItemAdapter(this, R.layout.item_layout, array);
        listView.setAdapter(adapter);

    }


}
