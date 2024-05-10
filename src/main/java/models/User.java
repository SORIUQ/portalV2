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
    private Integer id_school;
    private Integer id_course;

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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getId_school() {
		return id_school;
	}

	public void setId_school(Integer id_school) {
		this.id_school = id_school;
	}

	public Integer getId_course() {
		return id_course;
	}

	public void setId_course(Integer id_course) {
		this.id_course = id_course;
	}
    
    
}
