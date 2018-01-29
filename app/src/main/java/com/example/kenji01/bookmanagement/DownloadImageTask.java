package com.example.kenji01.bookmanagement;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
/**
 * Created by TARO3 on 2018/01/3.
 */

class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
    private ImageView imageView;
    private Context context;
    private String tag;

    // 初期化
    public DownloadImageTask(ImageView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
        this.tag = imageView.getTag().toString();
    }

    // execute時のタスク本体。画像をビットマップとして読み込んで返す
    @Override
    protected Bitmap doInBackground(String... params) {
        synchronized (context) {
            try {
                //キャッシュより画像データを取得
                Bitmap bm = ImageCache.getImage(params[0]);
                if (bm == null) {
                    //キャッシュにデータが存在しない場合はwebより画像データを取得
                    String str_url = params[0];
                    URL imageUrl = new URL(str_url);
                    InputStream imageIs;

                    // 読み込み実行
                    imageIs = imageUrl.openStream();
                    bm = BitmapFactory.decodeStream(imageIs);
                    Log.d("ListViewTest", "画像読み込み完了");
                    //取得した画像データをキャッシュに保持
                    ImageCache.setImage(params[0], bm);
                }else {
                    Log.d("ListViewTest", "画像のキャッシュあり");
                }
                return bm;
            } catch (MalformedURLException e) {
                Log.d("ListViewTest", "画像読み込みタスクで例外発生："
                        + e.toString());
                return null;
            } catch (IOException e) {
                Log.d("ListViewTest", "画像読み込みタスクで例外発生："
                        + e.toString());
                return null;
            }
        }
    }

    // タスク完了時
    @Override
    protected void onPostExecute(Bitmap result) {
        if (this.tag.equals(this.imageView.getTag())) {
            if (result != null) {
                Log.d("ListViewTest", "ビューに画像をセット");
                this.imageView.setImageBitmap(result);
            } else {
                this.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.book));
            }
            this.imageView.setVisibility(View.VISIBLE);
        }
    }
}