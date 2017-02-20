package com.vansuita.gaussianblur.sample.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vansuita.gaussianblur.GaussianBlur;
import com.vansuita.gaussianblur.sample.R;
import com.vansuita.gaussianblur.sample.act.MainActivity;
import com.vansuita.gaussianblur.sample.anim.Animate;
import com.vansuita.gaussianblur.sample.lis.IOnSettingsChanged;

import java.util.Arrays;


/**
 * Created by jrvansuita on 18/02/17.
 */

public class DinosaurFragment extends Fragment implements View.OnTouchListener {
    public static final String TAG = "TAG";

    private ImageView ivRawImage;
    private ImageView ivBlurredImage;

    private int dinosaurId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        getMain().addOnSettingsChanged(onSettingsChanged);
    }

    private MainActivity getMain() {
        return ((MainActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dinosaur_fragment, null);
        bind(view);
        init();

        setHasOptionsMenu(true);
        return view;
    }

    private void bind(View view) {
        ivRawImage = (ImageView) view.findViewById(R.id.raw_image);
        ivBlurredImage = (ImageView) view.findViewById(R.id.blurred_image);
    }

    private int getResource() {
        int position = getArguments().getInt(TAG);
        String dinosaurName = getResources().getStringArray(R.array.dinosaurs)[position];
        return getResources().getIdentifier(dinosaurName.replace(' ', '_').toLowerCase(), "mipmap", getActivity().getPackageName());
    }

    private void init() {
        dinosaurId = getResource();

        ivRawImage.setImageResource(dinosaurId);
        ivBlurredImage.setOnTouchListener(this);

        applyBlur();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_unblur:
                Animate.with(ivBlurredImage).toggleVisibility();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (Arrays.asList(MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP).contains(motionEvent.getAction()))
            Animate.with(ivBlurredImage).toggleVisibility();

        return true;
    }

    private IOnSettingsChanged onSettingsChanged = new IOnSettingsChanged() {
        @Override
        public void onChanged() {
            applyBlur();
        }
    };


    private void applyBlur() {
        GaussianBlur.with(getActivity())
                .size(getMain().getActualMaxSize())
                .radius(getMain().getActualRadius())
                .put(dinosaurId, ivBlurredImage);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        getMain().removeOnSettingsChanged(onSettingsChanged);
    }
}
