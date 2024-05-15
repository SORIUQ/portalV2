package models;

public class Course {
	
	Integer id_course;
	String nameCourse;
	
	public Course() {}

	public Course(Integer id_course, String nameCourse) {
		this.id_course = id_course;
		this.nameCourse = nameCourse;
	}
	
	public Integer getId_course() {
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

	@Override
	public String toString() {
		return "Course{" +
				"id_course=" + id_course +
				", nameCourse='" + nameCourse + '\'' +
				'}';
	}
}
