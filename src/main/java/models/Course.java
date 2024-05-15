package models;

public class Course {
	
	int id_course;
	String nameCourse;
	
	
	public Course(int id_course, String nameCourse) {
		this.id_course = id_course;
		this.nameCourse = nameCourse;
	}
	
	public int getId_course() {
		return id_course;
	}
	public void setId_course(int id_course) {
		this.id_course = id_course;
	}
	public String getNameCourse() {
		return nameCourse;
	}
	public void setNameCourse(String nameCourse) {
		this.nameCourse = nameCourse;
	}
	
	
}
