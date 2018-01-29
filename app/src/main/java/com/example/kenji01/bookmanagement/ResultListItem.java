package com.example.kenji01.bookmanagement;
import android.graphics.Bitmap;
/**
 * Created by TARO on 2018/01/2.
 */

public class ResultListItem {
    private String mThumbnail = null;
    private String mTitle = null;
    private String mIsbn = null;
    private String mAuthor = null;
    private String mAffiliateUrl = null;
    private String mItemPrice = null;
    private String mFlag = null;

    /**
     * 空のコンストラクタ
     */
    public ResultListItem() {};

    /**
     * コンストラクタ
     * @param thumbnail サムネイル画像
     * @param title タイトル
     * @param flag flag
     */
    public ResultListItem(String thumbnail, String title,String isbn,String author,String itemPrice, String flag) {
        mThumbnail = thumbnail;
        mTitle = title;
        mIsbn =isbn;
        mAuthor = author;
        mItemPrice = itemPrice;
        mFlag = flag;
    }

    /**
     * サムネイル画像を設定
     * @param thumbnail サムネイル画像
     */
    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    /**
     * タイトルを設定
     * @param title タイトル
     */
    public void setmTitle(String title) {
        mTitle = title;
    }

    /**
     * サムネイル画像を取得
     * @return サムネイル画像
     */
    public String getThumbnail() {
        return mThumbnail;
    }

    /**
     * タイトルを取得
     * @return タイトル
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * サムネ
     * @return サムネ
     */
    public String getThumbnailUrl() {
        return mThumbnail;
    }
    /**
     * isbn
     * @return isbn
     */
    public String getIsbn() {
        return mIsbn;
    }
    /**
     * isbn
     * @return author
     */
    public String getAuthor() {
        return mAuthor;
    }
    /**
     * isbn
     * @return price
     */
    public String getItemPrice() {
        return mItemPrice;
    }
}
