package com.vansuita.gaussianblur.sample.adpt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vansuita.gaussianblur.sample.R;
import com.vansuita.gaussianblur.sample.frag.DinosaurFragment;

/**
 * Created by jrvansuita on 18/02/17.
 */

public class DinosaurPagerAdapter extends FragmentStatePagerAdapter {

    private String[] dinosaurs;

    public DinosaurPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.dinosaurs = context.getResources().getStringArray(R.array.dinosaurs);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(DinosaurFragment.TAG, position);

        Fragment frag = new DinosaurFragment();
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public int getCount() {
        return dinosaurs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dinosaurs[position];
    }
}
