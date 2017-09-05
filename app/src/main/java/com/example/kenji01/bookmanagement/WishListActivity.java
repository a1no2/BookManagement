package com.example.kenji01.bookmanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WishListActivity extends Fragment {
    private ArrayList<String> title_arr;
    private ArrayList<String> id_arr;
    private ArrayAdapter<String> adapter;
    private ListView list;

    //DBを使う準備
    private SQLiteDatabase db;
    private DB_helper db_helper;
    //データベースの参照位置を保持
    private Cursor c;

    //くそこーど 削除
    public static int remove_id;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_possession_list, container, false);

        //インスタンス生成して読み書き可能で取得
        db_helper = new DB_helper(getContext());
        db = db_helper.getWritableDatabase();
        setAdapter();

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    //リスト表示
    private void setAdapter() {
        list = (ListView) v.findViewById(R.id.list);
        title_arr = new ArrayList<>();
        id_arr = new ArrayList<>();

        c = db.query(
                DB_helper.TABLE_NAME,
                new String[]{DB_helper.BOOK_NAME,DB_helper.BOOK_ID},
                DB_helper.HAVE + " = ?",
                new String[] {"0"},
                null,
                null,
                DB_helper.BOOK_NAME + ""
        );
        while (c.moveToNext()){
            title_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
            id_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_ID)));

        }
        adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                title_arr
        );
        list.setAdapter(adapter);
    }
}
