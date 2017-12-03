package kciter.famuosfood.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by kciter on 2017. 12. 1..
 */

public class ImageUtil {
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);

    }
}