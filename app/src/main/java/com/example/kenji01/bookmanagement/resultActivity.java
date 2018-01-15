package com.example.kenji01.bookmanagement;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            urlText = i.getStringExtra("url");
            if (!urlText.equals("")) {
                getLoaderManager().restartLoader(1, null, this);
                resultText.setText("この画面が長く続く場合、インターネット繋がってない\nまたは、読み取ったデータが存在しない可能性があります。");
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
                resultText.setText("検索結果");

//                        あ
//                        あ
                t("キーワード");


            }

        } else {
            Log.d("onLoadFinished", "onLoadFinished error!");
        }

    }


    //ロード内容が破棄された？
    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        // 処理なし
    }

    private void t(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
