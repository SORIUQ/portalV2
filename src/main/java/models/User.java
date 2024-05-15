package models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class User {
    private int id;
    private String name;
    private String userType;
    private int id_school;
    private int id_course;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userType='" + userType + '\'' +
                ", id_school=" + id_school +
                ", id_course=" + id_course +
                '}';
    }
}
