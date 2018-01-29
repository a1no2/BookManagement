package com.example.kenji01.bookmanagement;


import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TARO3 on 2017/08/22.
 * サーバにJSONファイルを問い合わせ
 */

public class JsonLoader extends AsyncTaskLoader<JSONObject> {
    private String urlText;

    public JsonLoader(Context context, String urlText){
        super(context);
        this.urlText = urlText;
    }

    @Override
    public JSONObject loadInBackground(){
        HttpURLConnection connection = null;

        //サーバに問い合わせ処理
        try{
            URL url = new URL(urlText);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
        }
        catch (MalformedURLException exception){
            // 処理なし
        }
        catch (IOException exception){
            // 処理なし
        }

        try{

            /*
            final InputStream in = connection.getInputStream();
            final String encoding = connection.getContentEncoding();
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            JSONObject json = new JSONObject(new String(String.valueOf(bufReader)));
            return json;
            */

            //JSONファイルのDL処理
            BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1){
                if (length > 0){
                    outputStream.write(buffer, 0, length);
                }
            }

            //JSONObject json = new JSONObject(new String(outputStream.toByteArray()));
            return new JSONObject(outputStream.toString());
            //JSONObject json = outputStream.toString();
            //return json;


        }
        catch (IOException exception){
            // 処理なし
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}