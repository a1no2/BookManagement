package com.example.kenji01.bookmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PossessionListActivity extends Fragment{
    private ArrayList<String> title_arr;
    private ArrayList<String> id_arr;
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_possession_list, container, false);

        //インスタンス生成して読み書き可能で取得
        db_helper = new DB_helper(getContext());
        db = db_helper.getWritableDatabase();
        setAdapter();

        //書籍のリストのクリックイベント
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item)list.getItemAtPosition(position);
                if (item.getmFlag().equals("1")){
                    Intent i = new Intent(getActivity(), seriesListActivity.class);
                    i.putExtra("seriesName",item.getTitle());
                    startActivity(i);
                } else {
                    Intent i = new Intent(getActivity(), RegistrationActivity.class);
                    i.putExtra("ID", id_arr.get(position));
                    startActivity(i);
                }
            }
        });



        //削除 アイテムのロングクリック
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                Item item = (Item)list.getItemAtPosition(position);
                Bitmap bm = item.getThumbnail();
                if (item.getmFlag().equals("1")) {

                } else {
                    c.moveToPosition(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("項目の削除");
                    builder.setMessage("この項目を削除してよろしいですか？");
                    remove_id = position;
                    // アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.delete(
                                    db_helper.BOOK_TABLE,
                                    db_helper.BOOK_ID + " = ?",
                                    new String[] {id_arr.get(remove_id)}
                            );
                            setAdapter();
                            Toast.makeText(getContext(), "削除しました。", Toast.LENGTH_SHORT).show();

                        }
                    });
                    // アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getContext(), "キャンセルしました。", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
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
        list = (ListView) v.findViewById(R.id.list_p);
        title_arr = new ArrayList<>();
        id_arr = new ArrayList<>();
        ArrayList<Item> array = new ArrayList<>();

        Cursor c02 = db.query(
                DB_helper.SERIES_TABLE,
                new String[]{DB_helper.SERIES_ID,DB_helper.SERIES_NAME},
                null,
                null,
                null,
                null,
                null
        );
        while (c02.moveToNext()){
            title_arr.add(c02.getString(c02.getColumnIndexOrThrow(DB_helper.SERIES_NAME)));
            id_arr.add(c02.getString(c02.getColumnIndexOrThrow(DB_helper.SERIES_ID)));

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.folder01);
            Item item = new Item(bmp,c02.getString(c02.getColumnIndexOrThrow(DB_helper.SERIES_NAME)),"1");
            array.add(item);
        }
        c02.close();


        c = db.query(
                DB_helper.BOOK_TABLE,
                new String[]{DB_helper.BOOK_NAME,DB_helper.BOOK_ID,DB_helper.SERIES_ID},
                DB_helper.HAVE + " = ?",
                new String[] {"1"},
                null,
                null,
                DB_helper.BOOK_NAME + ""
        );
        while (c.moveToNext()){

            if (c.getInt(c.getColumnIndexOrThrow(DB_helper.SERIES_ID)) == 1) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.book);
                Item item = new Item(bmp, c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)), "2");
                array.add(item);
                title_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
                id_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_ID)));
            }
        }
        ItemAdapter adapter = new ItemAdapter(getContext(), R.layout.item_layout, array);
        list.setAdapter(adapter);
    }



}
