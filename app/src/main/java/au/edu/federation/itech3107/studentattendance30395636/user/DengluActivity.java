package au.edu.federation.itech3107.studentattendance30395636.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import au.edu.federation.itech3107.studentattendance30395636.R;
import au.edu.federation.itech3107.studentattendance30395636.databinding.ActivityLoginBinding;
import au.edu.federation.itech3107.studentattendance30395636.room.UserBean;
import au.edu.federation.itech3107.studentattendance30395636.room.UserDataBase;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DengluActivity extends AppCompatActivity {
    private ActivityLoginBinding inflate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        initView();
    }

    private void initView() {
        //Log in
        inflate.btnLogin.setOnClickListener(v -> {
            UserDataBase.getInstance(this).getUserDao().getUserByName(inflate.etAccount.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MaybeObserver<List<UserBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<UserBean> list) {
                            //Query result
                            if (list.size() != 0) {
                                //Check account
                                UserBean userBean = list.get(0);
                                if (userBean.getPwd().equals(inflate.etPassword.getText().toString())) {
                                    startActivity(new Intent(DengluActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    //Password error
                                    Toast.makeText(DengluActivity.this, getString(R.string.passwordNotice), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                //Do not have this account
                                Toast.makeText(DengluActivity.this, getString(R.string.passwordNotice), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                            //No result found
                            Toast.makeText(DengluActivity.this, getString(R.string.passwordNotice), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        //Sign in
        inflate.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, ZhuCeActivity.class));
        });
    }

}
