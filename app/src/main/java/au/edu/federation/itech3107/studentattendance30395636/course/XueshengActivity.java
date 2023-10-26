package au.edu.federation.itech3107.studentattendance30395636.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395636.R;
import au.edu.federation.itech3107.studentattendance30395636.databinding.ActivityStudentBinding;
import au.edu.federation.itech3107.studentattendance30395636.room.StudentBean;
import au.edu.federation.itech3107.studentattendance30395636.room.UserDataBase;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class XueshengActivity extends AppCompatActivity {
    private ActivityStudentBinding bind;
    private StudentAdapter mSpq;
    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityStudentBinding.inflate(getLayoutInflater());
        mId = getIntent().getIntExtra("id", 0);
        setContentView(bind.getRoot());
        initView();
    }

    private void initView() {
        bind.add.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentManActivity.class);
            intent.putExtra("id", mId);
            intent.putExtra("add", true);
            startActivity(intent);
        });

        bind.rv.setLayoutManager(new LinearLayoutManager(this));
        mSpq = new StudentAdapter(R.layout.item_course, new ArrayList<>());
        mSpq.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StudentBean studentBean = mSpq.getData().get(position);
                Intent intent = new Intent(XueshengActivity.this, StudentManActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("add", false);
                intent.putExtra("bean", studentBean);
                startActivity(intent);
            }
        });
        bind.rv.setAdapter(mSpq);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchData();
    }

    private void searchData() {
        UserDataBase.getInstance(this).getStudentDao().getAllUsers(mId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<StudentBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<StudentBean> list) {
                        //Query result
                        if (list.size() != 0) {
                            mSpq.setNewData(list);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
