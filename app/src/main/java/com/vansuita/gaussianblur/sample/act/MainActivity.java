package com.vansuita.gaussianblur.sample.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vansuita.gaussianblur.GaussianBlur;
import com.vansuita.gaussianblur.sample.R;
import com.vansuita.gaussianblur.sample.adpt.DinosaurPagerAdapter;
import com.vansuita.gaussianblur.sample.lis.IOnSettingsChanged;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private List<IOnSettingsChanged> listeners = new ArrayList();

    private AppCompatSeekBar sbRadius;
    private AppCompatSeekBar sbSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new DinosaurPagerAdapter(getSupportFragmentManager(), this));
        ((TabLayout) findViewById(R.id.tabs)).setupWithViewPager(viewPager);

        sbRadius = (AppCompatSeekBar) findViewById(R.id.radius);
        sbSize = (AppCompatSeekBar) findViewById(R.id.size);

        sbRadius.setTag(R.id.label, findViewById(R.id.radius_label));
        sbSize.setTag(R.id.label, findViewById(R.id.size_label));

        sbRadius.setTag(R.id.label_title, R.string.radius);
        sbSize.setTag(R.id.label_title, R.string.size);

        sbRadius.setOnSeekBarChangeListener(this);
        sbSize.setOnSeekBarChangeListener(this);


        sbSize.setMax(GaussianBlur.MAX_RADIUS);
        sbSize.setMax(GaussianBlur.MAX_SIZE);
        sbRadius.setProgress(GaussianBlur.MAX_RADIUS);
        sbSize.setProgress(GaussianBlur.MAX_SIZE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        ((TextView) seekBar.getTag(R.id.label)).setText(getString((Integer) seekBar.getTag(R.id.label_title), progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //No code, not used.
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        for (IOnSettingsChanged l : listeners)
            l.onChanged();
    }

    public int getActualRadius() {
        return sbRadius.getProgress();
    }

    public int getActualMaxSize() {
        return sbSize.getProgress();
    }

    public void addOnSettingsChanged(IOnSettingsChanged listener) {
        listeners.add(listener);
    }

    public void removeOnSettingsChanged(IOnSettingsChanged listener) {
        listeners.remove(listener);
    }

}
