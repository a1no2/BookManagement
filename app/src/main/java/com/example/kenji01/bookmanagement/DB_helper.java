package com.example.kenji01.bookmanagement;

/**
 * Created by kenji01 on 2017/08/31.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kenji01 on 2017/07/10.
 */

public class DB_helper extends SQLiteOpenHelper{
    //DBの名前 バージョン
    private static final String DB_NAME = "client_DB";
    private static final int DB_VERSION = 1;

    //書籍テーブル
    public static final String BOOK_TABLE = "book_table";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "book_name";
    public static final String AUTHOR = "author";
    public static final String CODE = "code";
    public static final String HAVE = "have";
    public static final String PRICE = "price";
    public static final String PURCHASE_DATE = "purchase_date";

    //シリーズテーブル
    public static final String SERIES_TABLE = "series_table";
    public static final String SERIES_ID = "series_id";
    public static final String SERIES_NAME = "series_name";


    public DB_helper(Context content){
        super(content,DB_NAME,null,DB_VERSION);
    }

    //テーブル作成
    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql文実行
        db.execSQL("create table " + BOOK_TABLE + "("
                + BOOK_ID           +" integer primary key autoincrement, "
                + BOOK_NAME         +" text, "
                + SERIES_ID         +" integer,"
                + AUTHOR            +" text, "
                + CODE              +" text, "
                + HAVE              +" integer, "
                + PRICE             +" integer, "
                + PURCHASE_DATE     +" integer"
                + ");");

        db.execSQL("create table " + SERIES_TABLE + "("
                + SERIES_ID         +" integer primary key autoincrement, "
                + SERIES_NAME       +" text"
                + ");");
        db.execSQL("insert into "+ SERIES_TABLE+ " ("+ SERIES_NAME + " ) values ( '単発' );");
    }

    //DBのバージョンが変わった時の処理
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + BOOK_TABLE);
        db.execSQL("drop table if exists " + SERIES_TABLE);
        onCreate(db);
    }
}
