package com.example.kenji01.bookmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class seriesListActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DB_helper db_helper;
    Cursor c;
    String[] series;

    ArrayList<String> id_arr;
    ArrayList<String> name_arr;
    ArrayList<String> have_arr;

    ListView listView;
    int remove_id;

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
        title.setText("所持チェック　　" + series[1]);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                i.putExtra("ID",id_arr.get(position));
                startActivity(i);
            }
        });
        setListView();



        //削除 アイテムのロングクリック
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                c.moveToPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(seriesListActivity.this);
                builder.setTitle("項目の削除");
                builder.setMessage("この項目を削除してよろしいですか？");
//                builder.setCancelable(false);
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
                        setListView();
                        Toast.makeText(seriesListActivity.this, "削除しました。", Toast.LENGTH_SHORT).show();

                    }
                });
                // アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(seriesListActivity.this, "キャンセルしました。", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
        setListView();
    }




    private void setListView(){
        id_arr = new ArrayList<>();
        name_arr = new ArrayList<>();

        c = db.query(
                DB_helper.BOOK_TABLE,
                new String[]{DB_helper.BOOK_NAME,DB_helper.BOOK_ID,DB_helper.HAVE},
                DB_helper.SERIES_ID + " = ?",
                new String[] {series[0]},
                null,
                null,
                DB_helper.BOOK_NAME + ""
        );
        ArrayList<Item> array = new ArrayList<>();
        String str = "";
        while (c.moveToNext()){
            Bitmap bmp;
            if (c.getInt(c.getColumnIndexOrThrow(DB_helper.HAVE)) == 1){
                bmp = BitmapFactory.decodeResource(getResources(), android.R.drawable.checkbox_on_background);
            } else {
                bmp = BitmapFactory.decodeResource(getResources(), android.R.drawable.checkbox_off_background);
            }  // 今回はサンプルなのでデフォルトのAndroid Iconを利用
            Item item = new Item(bmp, c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
            array.add(item);

            str += c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME));
            id_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_ID)));
            name_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.BOOK_NAME)));
//            have_arr.add(c.getString(c.getColumnIndexOrThrow(DB_helper.HAVE)));
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        ItemAdapter adapter = new ItemAdapter(this, R.layout.item_layout, array);
        listView.setAdapter(adapter);

    }


}
