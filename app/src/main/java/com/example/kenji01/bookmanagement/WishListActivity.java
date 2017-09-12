package com.example.kenji01.bookmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        v = inflater.inflate(R.layout.activity_wish_list, container, false);

        //インスタンス生成して読み書き可能で取得
        db_helper = new DB_helper(getContext());
        db = db_helper.getWritableDatabase();
        setAdapter();

        //インスタンス生成して読み書き可能で取得
        db_helper = new DB_helper(getContext());
        db = db_helper.getWritableDatabase();
        setAdapter();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position + "：フラグメント", Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), id_arr.get(position) + ":" + title_arr.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), RegistrationActivity.class);
//                Intent i = new Intent(MainActivity.class,PossessionListActivity.class);
                i.putExtra("ID",id_arr.get(position));
                startActivity(i);


            }
        });



        //削除 アイテムのロングクリック
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {

                c.moveToPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("項目の削除");
                builder.setMessage("この項目を削除してよろしいですか？");
//                builder.setCancelable(false);
                remove_id = position;
                // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(
                                db_helper.TABLE_NAME,
                                db_helper.BOOK_ID + " = ?",
                                new String[] {id_arr.get(remove_id)}
//                                new String[] {c.getString(c.getColumnIndex(db_helper.BOOK_ID))}
                        );
                        setAdapter();

                        Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();

                    }
                });
                // アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

        return v;
    }




    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }
    //リスト表示
    private void setAdapter() {
        list = (ListView) v.findViewById(R.id.list_w);
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


    public void tst01(View v){
        Toast.makeText(getContext(), "wish", Toast.LENGTH_SHORT).show();
    }
}
