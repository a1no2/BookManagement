package com.example.kenji01.bookmanagement;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

/**
 * Created by on 2018/01/15.
 */


public class ImageCache {
    private static HashMap<String,Bitmap> cache = new HashMap<String,Bitmap>();

    //キャッシュより画像データを取得
    public static Bitmap getImage(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
//存在しない場合はNULLを返す
        return null;
    }

    //キャッシュに画像データを設定
    public static void setImage(String key, Bitmap image) {
        cache.put(key, image);
    }

    //キャッシュの初期化（リスト選択終了時に呼び出し、キャッシュで使用していたメモリを解放する）
    public static void clearCache(){
        cache = null;
        cache = new HashMap<String,Bitmap>();
    }
}