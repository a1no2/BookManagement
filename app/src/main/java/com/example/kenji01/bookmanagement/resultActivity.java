package com.example.kenji01.bookmanagement;

        import android.graphics.Bitmap;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
        import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
        import java.util.ArrayList;
import java.util.Objects;
import static java.util.Arrays.deepToString;

public class resultActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    String urlText;
    TextView resultText;
    String id_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = (TextView)findViewById(R.id.resultText);

        Intent i = getIntent();
        id_ = i.getStringExtra("ID");

            //ISBNの場合
        if (id_.equals("isbn")){
            urlText = i.getStringExtra("url");
            if (!urlText.equals("")) {
//            codeSearch = true;
                getLoaderManager().restartLoader(1, null, this);
                resultText.setText("この画面が長く続く場合、インターネット繋がってない\nまたは、読み取ったデータが存在しない可能性があります。");
            } else {
                Toast.makeText(this, "からでござる", Toast.LENGTH_LONG).show();
            }

            //キーワード検索の場合
        } else if (id_.equals("keyword")){
//            ここにかく
            urlText = i.getStringExtra("url");
            if (!urlText.equals("")) {
//            codeSearch = true;
                getLoaderManager().restartLoader(1, null, this);
                resultText.setText("この画面が長く続く場合、インターネット繋がってない\nまたは、データが存在しない可能性があります。");
            } else {
                Toast.makeText(this, "からでござる", Toast.LENGTH_LONG).show();
            }

        }



    }




    //ロード起動
    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        JsonLoader jsonLoader = new JsonLoader(this, urlText);
        jsonLoader.forceLoad();
        return jsonLoader;
    }

    //ロードが完了
    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {


        //リストビューの取得
        ListView listView = (ListView)findViewById(R.id.resultListView);

        listView.setOnItemClickListener(onItemClickListener);  // タップ時のイベントを追加

        if (data != null) {
            if (id_.equals("isbn")) {
                try {
                    JSONObject json = new JSONObject(data.toString());

                    JSONArray items = json.getJSONArray("Items");
                    JSONObject item = items.getJSONObject(0);
                    JSONObject raw = item.getJSONObject("Item");

                    Intent i = new Intent(resultActivity.this, RegistrationActivity.class);
                    i.putExtra("ID", "isbn");

                    String title = raw.getString("title");
                    String author = raw.getString("author");
                    String isbn = raw.getString("isbn");
                    String itemPrice = raw.getString("itemPrice");

                    i.putExtra("title", title);
                    i.putExtra("author", author);
                    i.putExtra("isbn", isbn);
                    i.putExtra("itemPrice", itemPrice);

                    Log.d("tag", "message");

                    startActivity(i);
                    this.finish();
                } catch (JSONException e) {
                    Log.d("onLoadFinished", "JSONのパースに失敗しました");
                }
            }else if (id_.equals("keyword")){
                //検索結果のjson配列を取り出す
                try {
                    JSONObject json = new JSONObject(data.toString());

                    JSONArray items = json.getJSONArray("Items");
                    //System.out.println(items);
                    //resultText.setText(items.toString());
                    //JSONArray eventArray  = new JSONArray(items);

                    // リストビューに表示する要素を設定
                    ArrayList<ResultListItem> listItems = new ArrayList<>();

                    if(items.length() > 0) {
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject eventObj = items.getJSONObject(i);
                            JSONObject event = eventObj.getJSONObject("Item");
                            Log.d("title", event.getString("title"));
                            //list.add(event.getString("title"));
                            ResultListItem item = new ResultListItem(event.getString("smallImageUrl"), event.getString("title"), event.getString("isbn"), event.getString("author"), event.getString("itemPrice"), "1");
                            listItems.add(item);
                        }
                        Log.d("onLoadFinished", "JSONパース:" + items.length());

                        // 出力結果をリストビューに表示
                        ResultListAdapter adapter = new ResultListAdapter(this, R.layout.list_item, listItems);
                        listView.setAdapter(adapter);
                    }else {
                        listView.setVisibility(View.GONE);
                        resultText.setText("検索結果が0件でした。");
                    }


                } catch (JSONException e) {
                    Log.d("onLoadFinished", "JSONのパースに失敗しました");
                }
            }

        } else {
            listView.setVisibility(View.GONE);
            resultText.setText("インターネットの接続状況が悪いかアクセスが集中している可能性があります。");
            Log.d("onLoadFinished", "onLoadFinished error!");
        }

    }

    /**
     * リストビューのタップイベント
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // タップしたアイテムの取得
            ListView listView = (ListView)parent;
            ResultListItem item = (ResultListItem)listView.getItemAtPosition(position);  // SampleListItemにキャスト

            Intent i = new Intent(resultActivity.this, RegistrationActivity.class);
            i.putExtra("ID", "isbn");

            String title = item.getTitle();
            String isbn = item.getIsbn();
            String author = item.getAuthor();
            String itemPrice = item.getItemPrice();

            i.putExtra("title", title);
            i.putExtra("author", author);
            i.putExtra("isbn", isbn);
            i.putExtra("itemPrice", itemPrice);

            Log.d("tag", "message");

            startActivity(i);
            //finish();

        }
    };


    //ロード内容が破棄された？
    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        // 処理なし
    }

    private void t(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
