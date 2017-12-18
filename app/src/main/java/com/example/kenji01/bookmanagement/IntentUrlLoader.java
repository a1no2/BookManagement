package com.example.kenji01.bookmanagement;
/**
 * Created by TARO3 on 2017/09/16.
 * 図書館検索を行う際のURLを発行するオーバーロードクラス
 */

public class IntentUrlLoader {

    //ベースURL
    String str = "https://www.library.pref.kyoto.jp/cross/cross.html";
    //処理結果を入れる変数
    String result;

    //検索オプション
    // q= フリー検索。半角スペースで複数ワード
    // isbn= ISBNコード検索
    // title= 本のタイトル検索
    //URL発行
    void createURL(){
        result = str;
    }
    //フリーワード検索URL
    void createURL(String keyword){
        result = str + "?q=" + keyword;
    }
    //詳細検索URL発行
    void createURL(String title,String isbn,String author,String publisher){
        result = str + "?title=" + title + "&isbn=" + isbn + "&author=" + author + "&publisher=" + publisher;
    }

}
