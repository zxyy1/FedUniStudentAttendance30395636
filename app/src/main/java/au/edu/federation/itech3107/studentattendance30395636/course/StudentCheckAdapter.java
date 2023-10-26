package au.edu.federation.itech3107.studentattendance30395636.course;


import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395636.util.ChangLiang;
import au.edu.federation.itech3107.studentattendance30395636.R;
import au.edu.federation.itech3107.studentattendance30395636.room.StudentBean;
import au.edu.federation.itech3107.studentattendance30395636.util.StringUtil;



public class StudentCheckAdapter extends BaseQuickAdapter<StudentBean, BaseViewHolder> {

    public StudentCheckAdapter(int layoutResId, @Nullable List<StudentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StudentBean item) {
        helper.setText(R.id.tv_item,item.getName());
        CheckBox view = helper.getView(R.id.cb);
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = item.getId();
                if (b) {
                    if (!ChangLiang.select_student.contains(id+",")) {
                        ChangLiang.select_student = ChangLiang.select_student + id + ",";
                        Log.e("hao", "选中: "+ ChangLiang.select_student);
                    }
                }else {
                    if (ChangLiang.select_student.contains(id+",")) {
                        String[] split = ChangLiang.select_student.split(",");
                        String newDes = "";
                        for (String i : split){
                            if (!StringUtil.isEmpty(i)){
                                int i1 = Integer.parseInt(i);
                                if (id != i1){
                                    newDes = newDes + i1+",";
                                }
                            }
                        }
                        ChangLiang.select_student = newDes;
                        Log.e("hao", "不选中: "+ ChangLiang.select_student);
                    }
                }
            }
        });
        view.setChecked(false);
        String selectClass = ChangLiang.select_student;
        if (!StringUtil.isEmpty(selectClass)) {
            String[] split = selectClass.split(",");
            for (String s : split) {
                if (!StringUtil.isEmpty(s)) {
                    if ((s+",").equals(item.getId()+",")) {
                        view.setChecked(true);
                    }
                }
            }
        }
    }
}
