package au.edu.federation.itech3107.studentattendance30395636.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
//Course group table
@Entity
public class CourseGroupBean {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CourseGroupBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

