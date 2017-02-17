package utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import model.Person;

/**
 * Created by athom909 on 4/9/16.
 */
public class GraphicsUtils {

    public static void drawGenderIcon(Person.Gender g, ImageView imageView, Context context) {

        switch (g) {
            case M:
                Drawable maleIcon1 = new IconDrawable(context, Iconify.IconValue
                        .fa_male).color(Color.BLUE).sizeDp(40);
                imageView.setImageDrawable(maleIcon1);
                break;
            case F:
                Drawable femaleIcon1 = new IconDrawable(context, Iconify.IconValue
                        .fa_female).color(Color.MAGENTA).sizeDp(40);
                imageView.setImageDrawable(femaleIcon1);
                break;
            default: assert false;
        }
    }

    public static void drawMaleIcon(Person.Gender g, ImageView imageView, Context context) {
        Drawable maleIcon = new IconDrawable(context, Iconify.IconValue.fa_male)
                .color(Color.BLUE).sizeDp(40);
        imageView.setImageDrawable(maleIcon);
    }

    public static void drawFemaleIcon(Person.Gender g, ImageView imageView, Context context) {
        Drawable femaleIcon = new IconDrawable(context, Iconify.IconValue.fa_female)
                .color(Color.MAGENTA).sizeDp(40);
        imageView.setImageDrawable(femaleIcon);
    }

    public static void drawMapMarkerIcon(ImageView imageView, Context context) {
        Drawable eventIcon = new IconDrawable(context, Iconify.IconValue.fa_map_marker)
                .sizeDp(40).color(Color.GRAY);
        imageView.setImageDrawable(eventIcon);
    }
}
