package com.example.kenji01.bookmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TARO on 2018/01/2.
 */

public class ResultListAdapter extends ArrayAdapter<ResultListItem> {
    private Context context;
    private int mResource;
    private List<ResultListItem> mItems;
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    public ResultListAdapter(Context context, int resource, List<ResultListItem> items) {
        super(context, resource, items);

        this.context = context;
        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("ListViewTest", "アダプタ生成完了");
    }
    private void setListData(List<ResultListItem> data){
        //データ内容を保持しておく
        mItems = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        }
        else {
            view = mInflater.inflate(mResource, null);
        }

        // リストビューに表示する要素を取得
        ResultListItem item = mItems.get(position);

        // サムネイル画像を設定
        ImageView thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
        //thumbnail.setImageBitmap(item.getThumbnail());


        // 非同期で画像読込を実行
        try{
            Log.d("ListViewTest", position + "の画像読み込みを開始");

            DownloadImageTask task
                    = new DownloadImageTask(thumbnail, context);
            task.execute(item.getThumbnailUrl());
        }
        catch(Exception e){
            //
            Log.d("ListViewTest", position + "の画像読み込みに失敗");
        }

        // タイトルを設定
        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(item.getTitle());

//        TextView seriesID = (TextView)view.findViewById(R.id.seriesID);
//        seriesID.setText(item.getSeriesID());

        return view;
    }
}
