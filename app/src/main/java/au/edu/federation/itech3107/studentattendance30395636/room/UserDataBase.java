package au.edu.federation.itech3107.studentattendance30395636.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {UserBean.class,CourseV2.class,CourseGroupBean.class, ClassBean.class,StudentBean.class}, version = 16, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    private static final String DB_NAME = "UserDataBase.db";
    private static volatile UserDataBase instance;

    public static synchronized UserDataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static UserDataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDataBase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()//数据库更新时删除数据重新创建
                .build();
    }

    public abstract UserDao getUserDao();
    public abstract CourseDao getCourseDao();
    public abstract CourseGroupDao getCourseGroupDao();
    public abstract ClassDao getClassDao();
    public abstract StudentDao getStudentDao();
}
