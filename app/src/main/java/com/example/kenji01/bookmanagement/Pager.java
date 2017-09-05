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
                return new PossessionListActivity();
            default:
                return new WishListActivity();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageName;
        if (position == 0){
            pageName = "所持リスト";
        } else if (position == 1){
            pageName = "欲しい物リスト";
        } else {
            pageName = "???";
        }
        return pageName;
    }
}
