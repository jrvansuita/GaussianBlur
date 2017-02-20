package com.vansuita.gaussianblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by jrvansuita on 09/11/16.
 */

public class GaussianBlur {
    public static final int MIN_RADIUS = 0;
    public static final int MAX_RADIUS = 25;

    public static final int MIN_SIZE = 0;
    public static final int MAX_SIZE = 800;

    private Context context;
    private int radius;
    private float size;

    private GaussianBlur(Context context) {
        this.context = context;
        radius(MAX_RADIUS);
        size(MAX_SIZE);
    }

    public static GaussianBlur with(Context context) {
        return new GaussianBlur(context);
    }

    public Bitmap render(int res) {
        return render(BitmapFactory.decodeResource(context.getResources(), res));
    }

    public Bitmap render(Drawable drawable) {
        return render(((BitmapDrawable) drawable).getBitmap());
    }

    public Bitmap render(Bitmap bitmap) {
        RenderScript rs = RenderScript.create(context);

        if (getSize() > 0)
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

    public void put(Drawable drawable, ImageView imageView) {
        new BitmapGaussianAsync(imageView).execute(((BitmapDrawable) drawable).getBitmap());
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
        float ratio = Math.min(getSize() / input.getWidth(), getSize() / input.getHeight());
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
    public GaussianBlur radius(@IntRange(from = MIN_RADIUS, to = MAX_RADIUS) int radius) {
        this.radius = radius;
        return this;
    }

    public float getSize() {
        return size;
    }

    /**
     * This method is provided to speed up the process. Once the image will be blurred,
     * there's no need to keep the original image size.
     * The smaller, the fastest.
     * @param maxSize Set an float value to define the image size. Zero, means the image will be keep with original size.
     */
    public GaussianBlur size(@FloatRange(from = MIN_SIZE, to = MAX_SIZE) float maxSize) {
        this.size = maxSize;
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