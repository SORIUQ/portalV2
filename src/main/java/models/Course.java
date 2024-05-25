package models;

public class Course {
	
	private Integer id_course;
	private String nameCourse;
	private String acronym;
	private String courseDescription;

	public Course() {}

//	public Course(Integer id_course, String nameCourse, String acronym, String courseDescription) {
//		this.id_course = id_course;
//		this.nameCourse = nameCourse;
//		this.acronym = acronym;
//		this.courseDescription = courseDescription;
//	}
	
//	public Integer getId_course() {
//		return id_course;
//	}
	public void setId_course(int id_course) {
		this.id_course = id_course;
	}
	public String getNameCourse() {
		return nameCourse;
	}
	public void setNameCourse(String nameCourse) {
		this.nameCourse = nameCourse;
	}

//	public void setId_course(Integer id_course) {
//		this.id_course = id_course;
//	}

//	public String getAcronym() {
//		return acronym;
//	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	@Override
	public String toString() {
		return "Course{" +
				"id_course=" + id_course +
				", nameCourse='" + nameCourse + '\'' +
				'}';
	}
}
