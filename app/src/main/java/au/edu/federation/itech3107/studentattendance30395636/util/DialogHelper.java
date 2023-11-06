package au.edu.federation.itech3107.studentattendance30395636.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Field;

import au.edu.federation.itech3107.studentattendance30395636.R;

/**
 * Dialog tool
 * Created by mnnyang on 17-4-14.
 */

public class DialogHelper {

    //Easy access to the DialogHelper object outside the callback function to close the mCustomDialog
    private AlertDialog mCustomDialog;

    /**
     * General dialog box
     */
    public void showNormalDialog(@NonNull Activity activity, @NonNull String title,
                                 @NonNull String massage, @NonNull final DialogListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(massage)
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onPositive(dialog, which);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onNegative(dialog, which);
                            }
                        })
                .show();
    }


    /**
     * Customize the pop-up
     */
    public void showCustomDialog(@NonNull Context context, View dialogView, String title,
                                 final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(dialogView);


        if (title != null) {
            builder.setTitle(title);
        }

        if (listener != null) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onPositive(dialog, which);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onNegative(dialog, which);
                            dialog.dismiss();
                        }
                    });
        }

        //Set the location form size here
        mCustomDialog = builder.create();

        try {
            Field field = field = mCustomDialog.getClass().getDeclaredField("mAlert");
            field.setAccessible(true);

            //   Gets the value of mAlert variable
            Object obj = field.get(mCustomDialog);
            field = obj.getClass().getDeclaredField("mHandler");
            field.setAccessible(true);

            //   Modify the value of the mHandler variable, using the new ButtonHandler class
            field.set(obj, new ButtonHandler(mCustomDialog));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCustomDialog.show();
    }


    public void hideCustomDialog() {
        if (mCustomDialog != null && mCustomDialog.isShowing()) {
            mCustomDialog.dismiss();
            mCustomDialog = null;
        }
    }
}