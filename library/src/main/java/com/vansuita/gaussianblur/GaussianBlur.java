package com.vansuita.gaussianblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by jrvansuita on 09/11/16.
 */

public class GaussianBlur {
    private final int DEFAULT_RADIUS = 25;
    private final float DEFAULT_MAX_SIZE = 400;

    private Context context;
    private int radius;
    private float maxImageSize;
    private boolean noScaleDown = false;

    public static GaussianBlur with(Context context) {
        return new GaussianBlur(context);
    }

    public GaussianBlur(Context context) {
        this.context = context;
        radius(DEFAULT_RADIUS);
        maxSixe(DEFAULT_MAX_SIZE);
    }

    public Bitmap render(int res) {
        return render(BitmapFactory.decodeResource(context.getResources(), res));
    }

    public Bitmap render(Bitmap bitmap) {
        RenderScript rs = RenderScript.create(context);

        if (!isNoScaleDown())
            bitmap = scaleDown(bitmap);

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Allocation inAlloc = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_GRAPHICS_TEXTURE);
        Allocation outAlloc = Allocation.createFromBitmap(rs, output);

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, inAlloc.getElement()); // Element.U8_4(rs));
        script.setRadius(getRadius());
        script.setInput(inAlloc);
        script.forEach(outAlloc);
        outAlloc.copyTo(output);

        rs.destroy();

        return output;
    }

    public void put(Bitmap bitmap, ImageView imageView) {
        new BitmapGaussianAsync(imageView).execute(bitmap);
    }

    public void put(int res, ImageView imageView) {
        new ResourceGaussianAsync(imageView).execute(res);
    }

    public Bitmap scaleDown(int res) {
        return scaleDown(BitmapFactory.decodeResource(context.getResources(), res));
    }

    public Bitmap scaleDown(Bitmap input) {
        float ratio = Math.min(getMaxSize() / input.getWidth(), getMaxSize() / input.getHeight());
        int width = Math.round(ratio * input.getWidth());
        int height = Math.round(ratio * input.getHeight());

        return Bitmap.createScaledBitmap(input, width, height, true);
    }

    public int getRadius() {
        return radius;
    }

    /**
     * @param radius Set the gaussian blur radius.
     */
    public GaussianBlur radius(int radius) {
        this.radius = radius;
        return this;
    }

    public float getMaxSize() {
        return maxImageSize;
    }


    /**
     * @param maxSize Set an float value put define the max image size.
     */
    public GaussianBlur maxSixe(float maxSize) {
        this.maxImageSize = maxSize;
        return this;
    }

    public boolean isNoScaleDown() {
        return noScaleDown;
    }

    /**
     * @param noScaleDown Set true if you want apply blur put the entire image.
     */
    public GaussianBlur noScaleDown(boolean noScaleDown) {
        this.noScaleDown = noScaleDown;
        return this;
    }


    /**
     * Async load and apply gaussian blur on an image from resource
     */
    class ResourceGaussianAsync extends GaussianAsync<Integer> {

        public ResourceGaussianAsync(ImageView imageView) {
            super(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            return render(params[0]);
        }
    }


    /**
     * Async load and apply gaussian blur on an image from bitmap
     */
    class BitmapGaussianAsync extends GaussianAsync<Bitmap> {

        public BitmapGaussianAsync(ImageView imageView) {
            super(imageView);
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            return render(params[0]);
        }
    }


    /**
     * Async base class
     */
    abstract class GaussianAsync<T> extends AsyncTask<T, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public GaussianAsync(ImageView imageView) {
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}