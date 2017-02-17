package utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bignerdranch.android.familymap3.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

/**
 * Created by athom909 on 4/7/16.
 */
public class NavUtils {

    public static Drawable getSettingsIcon(Context context) {
        return new IconDrawable(context, Iconify.IconValue.fa_gear)
                .colorRes(R.color.colorLightGrey)
                .sizeDp(40);
    }

    public static Drawable getFilterIcont(Context context) {
        return new IconDrawable(context, Iconify.IconValue.fa_filter)
                .colorRes(R.color.colorLightGrey)
                .sizeDp(40);
    }

    public static Drawable getSearchIcon(Context context) {
        return new IconDrawable(context, Iconify.IconValue.fa_search)
                .colorRes(R.color.colorLightGrey)
                .sizeDp(40);
    }

    public static Drawable getDoubleUpIcon(Context context) {
        return new IconDrawable(context, Iconify.IconValue.fa_angle_double_up)
                .colorRes(R.color.colorLightGrey)
                .sizeDp(40);
    }
}
