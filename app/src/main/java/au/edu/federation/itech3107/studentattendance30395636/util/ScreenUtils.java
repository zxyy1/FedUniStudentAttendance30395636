package au.edu.federation.itech3107.studentattendance30395636.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Size screen related tool class<br>
 */
public class ScreenUtils {

    public static Context context;

    public static void init(Context context) {
        ScreenUtils.context = context.getApplicationContext();
    }

    /**
     * From dp to px(pixels) depending on the phone's resolution
     */
    public static int dp2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * From px to dp depending on the phone's resolution
     */
    public static int px2dp(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Convert the sp value to a px value, keeping the text size unchanged
     */
    public static int sp2px(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}