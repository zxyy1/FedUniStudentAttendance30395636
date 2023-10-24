package au.edu.federation.itech3107.studentattendance30395636.course;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395636.R;
import au.edu.federation.itech3107.studentattendance30395636.room.ClassBean;


public class ClassAdapter extends BaseQuickAdapter<ClassBean, BaseViewHolder> {

    public ClassAdapter(int layoutResId, @Nullable List<ClassBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBean item) {
        helper.setText(R.id.tv_item,"Class Name: "+item.getName());
    }
}
