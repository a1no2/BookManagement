package com.example.kenji01.bookmanagement;

/**
 * Created by kenji01 on 2017/09/12.
 */
import java.util.EventListener;

public interface DialogListener extends EventListener {

    /**
     * okボタンが押されたイベントを通知する
     */
    public void onPositiveClick();

    /**
     * cancelボタンが押されたイベントを通知する
     */
    public void onNegativeClick();
}