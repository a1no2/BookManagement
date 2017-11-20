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

import org.json.JSONObject;

import java.util.Objects;

import static java.util.Arrays.deepToString;

public class resultActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    String urlText;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = (TextView)findViewById(R.id.resultText);

        Intent i = getIntent();
        urlText = i.getStringExtra("url");
        if (!urlText.equals("")) {
//            codeSearch = true;
            getLoaderManager().restartLoader(1, null, this);
//            resultText.setText(urlText);
        } else {
            Toast.makeText(this, "からでござる", Toast.LENGTH_LONG).show();
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

            //JSONObject jsonObject = data.getJSONObject("request");
            //textView.setText(jsonObject.toString());
            //テキストビューに結果を表示
            resultText.setText(data.toString());
//            String tst = data.getString("")
        } else {
            Log.d("onLoadFinished", "onLoadFinished error!");
        }
    }

    //ロード内容が破棄された？
    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {
        // 処理なし
    }
}
