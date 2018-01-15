package com.example.kenji01.bookmanagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kenji01 on 2017/08/31.
 */

public class Pager extends FragmentStatePagerAdapter {

    public Pager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new WishListActivity();
            default:
                return new PossessionListActivity();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    //ページをセット
    @Override
    public CharSequence getPageTitle(int position) {
        String pageName;
        if (position == 0){
            pageName = "欲しい物リスト";
        } else if (position == 1){
            pageName = "所持,シリーズリスト";
        } else {
            pageName = "???";
        }
        return pageName;
    }
}
