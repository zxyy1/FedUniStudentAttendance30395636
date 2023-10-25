package au.edu.federation.itech3107.studentattendance30395636.course;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395636.util.ChangLiang;
import au.edu.federation.itech3107.studentattendance30395636.R;
import au.edu.federation.itech3107.studentattendance30395636.databinding.ActivityCheckBinding;
import au.edu.federation.itech3107.studentattendance30395636.room.ClassBean;
import au.edu.federation.itech3107.studentattendance30395636.room.CourseV2;
import au.edu.federation.itech3107.studentattendance30395636.room.UserDataBase;
import au.edu.federation.itech3107.studentattendance30395636.util.StringUtil;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DakaActivity extends AppCompatActivity {
    private ActivityCheckBinding inflate;
    private CheckAdapter mSpq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityCheckBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        initView();
    }

    private void initView() {
        inflate.rv.setLayoutManager(new LinearLayoutManager(this));
        inflate.rv.setNestedScrollingEnabled(false);

        mSpq = new CheckAdapter(R.layout.item_check, new ArrayList<>(),  DakaActivity.this);
        inflate.rv.setAdapter(mSpq);

        CourseV2 bean = (CourseV2) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            UserDataBase.getInstance(this).getCourseDao().getCourseById(bean.getCouId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MaybeObserver<CourseV2>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(CourseV2 list) {
                            //find result
                            if (!StringUtil.isEmpty(list.getJoinClassId())) {
                                ChangLiang.select_class = list.getJoinClassId();
                            }
                            if (!StringUtil.isEmpty(list.getCheckInStudentIds())) {
                                ChangLiang.select_student = list.getCheckInStudentIds();
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

        inflate.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setJoinClassId(ChangLiang.select_class);
                bean.setCheckInStudentIds(ChangLiang.select_student);
                Log.e("hao", "DakaActivity onClick(): "+ ChangLiang.select_student);
                UserDataBase.getInstance(DakaActivity.this).getCourseDao().update(bean);
                Toast.makeText(DakaActivity.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        UserDataBase.getInstance(this).getClassDao().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<ClassBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ClassBean> list) {
                        //find result
                        if (list.size() != 0) {
                            for (int i = 0; i < list.size(); i++) {
                                if (ChangLiang.select_class.contains(list.get(i).getId() + ",")) {
                                    mSpq.addData(list.get(i));
                                }
                            }
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
