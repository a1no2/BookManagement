package com.example.kenji01.bookmanagement;

import android.graphics.Bitmap;

/**
 * Created by kenji01 on 2017/10/16.
 */

public class Item {
    private Bitmap mThumbnail = null;
    private String mTitle = null;
    private String mSeriesID = null;

    /**
     * 空のコンストラクタ
     */
    public Item() {};

    /**
     * コンストラクタ
     * @param thumbnail サムネイル画像
     * @param title タイトル
     * @param seriesID シリーズid
     *
     */
    public Item(Bitmap thumbnail, String title, String seriesID) {
        mThumbnail = thumbnail;
        mTitle = title;
        mSeriesID = seriesID;
    }

    /**
     * サムネイル画像を設定
     * @param thumbnail サムネイル画像
     */
    public void setThumbnail(Bitmap thumbnail) {
        mThumbnail = thumbnail;
    }

    /**
     * タイトルを設定
     * @param title タイトル
     */
    public void setmTitle(String title) {
        mTitle = title;
    }

    public void setmSeriesID(String seriesID){
        mSeriesID = seriesID;
    }

    /**
     * サムネイル画像を取得
     * @return サムネイル画像
     */
    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    /**
     * タイトルを取得
     * @return タイトル
     */
    public String getTitle() {
        return mTitle;
    }

    public String  getSeriesID(){
        return mSeriesID;
    }
}