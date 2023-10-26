package au.edu.federation.itech3107.studentattendance30395636.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Maybe;


@Dao
public interface StudentDao {
    @Query("SELECT * FROM studentbean where classId = :id")
    Maybe<List<StudentBean>> getAllUsers(int id);

    @Insert()
    void insert(StudentBean... users);

    @Update
    void update(StudentBean... users);

    @Delete
    void delete(StudentBean... users);

}
