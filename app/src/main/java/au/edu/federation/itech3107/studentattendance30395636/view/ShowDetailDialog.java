package au.edu.federation.itech3107.studentattendance30395636.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395636.util.ChangLiang;
import au.edu.federation.itech3107.studentattendance30395636.R;
import au.edu.federation.itech3107.studentattendance30395636.course.CourseOneActivity;
import au.edu.federation.itech3107.studentattendance30395636.room.CourseV2;


public class ShowDetailDialog {

   private PopupWindow mPopupWindow;

   /**
    * @param course Time information must be complete
    */
   @SuppressLint("SetTextI18n")
   public void show(final Activity activity, final CourseV2 course,
                    final PopupWindow.OnDismissListener dismissListener) {
      if (null == course) {
         return;
      }

      WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
      lp.alpha = 0.5f;
      activity.getWindow().setAttributes(lp);

      mPopupWindow = new PopupWindow(
              ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

      final View popupView = LayoutInflater.from(activity).inflate(R.layout.dialog_detail_course,
              null);

      TextView tvTitle = popupView.findViewById(R.id.tv_title);
      TextView tvClassroom = popupView.findViewById(R.id.tv_calssroom);
      TextView tvTeacher = popupView.findViewById(R.id.tv_teacher);
      TextView tvNode = popupView.findViewById(R.id.tv_node);
      TextView tvWeekRange = popupView.findViewById(R.id.tv_week_range);

      StringBuilder nodeInfo = getNodeInfo(course);
      tvNode.setText(nodeInfo);

      tvTitle.setText(course.getCouName());
//      tvClassroom.setText(course.getCouLocation());
      tvTeacher.setText(course.getCouTeacher());

      List<Integer> showIndexes = course.getShowIndexes();
      if (showIndexes != null && showIndexes.size() != 0) {
         tvWeekRange.setText(course.getShowIndexes().get(0) + "-" +
                 course.getShowIndexes().get(course.getShowIndexes().size() - 1) + "周");
      }

      View close = popupView.findViewById(R.id.iv_close);
      close.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            mPopupWindow.dismiss();
         }
      });
      final View edit = popupView.findViewById(R.id.iv_eidt);
      edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            edit(activity, course);
         }
      });
      popupView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            edit(activity, course);
         }
      });

      initWindow(activity, dismissListener, popupView);
   }

   private void edit(Activity activity, CourseV2 course) {
      Intent intent = new Intent(activity, CourseOneActivity.class);
      intent.putExtra(ChangLiang.INTENT_EDIT_COURSE, course);
      activity.startActivity(intent);
      dismiss();
   }

   private void initWindow(final Activity activity, final PopupWindow.OnDismissListener dismissListener, View popupView) {
      mPopupWindow.setContentView(popupView);
      mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
      mPopupWindow.setFocusable(true);
      mPopupWindow.setTouchable(true);
      mPopupWindow.setOutsideTouchable(true);
      mPopupWindow.setClippingEnabled(true);
      mPopupWindow.setAnimationStyle(R.style.animZoomIn);

      mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
      mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

      mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
         @Override
         public void onDismiss() {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1.0f;
            activity.getWindow().setAttributes(lp);
            dismissListener.onDismiss();
         }
      });
   }

   @NonNull
   private StringBuilder getNodeInfo(CourseV2 course) {
      StringBuilder nodeInfo = new StringBuilder();
      if (course.getCouNodeCount() == 1) {
         nodeInfo.append("Section ");
      }
      nodeInfo.append(course.getCouStartNode());
      if (course.getCouNodeCount() > 1) {
         nodeInfo.append("-");
         nodeInfo.append(course.getCouStartNode() + course.getCouNodeCount() - 1);
      }
      return nodeInfo;
   }

   public void dismiss() {
      if (mPopupWindow != null) {
         mPopupWindow.dismiss();
      }
   }
}
