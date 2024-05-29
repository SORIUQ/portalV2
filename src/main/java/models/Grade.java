package models;

import java.math.BigDecimal;

public class Grade {

    private int subject_id;
    private int teacher_id;
    private int student_id;
    private BigDecimal grade;
    private String grade_desc;

    public Grade() {

    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public String getGrade_desc() {
        return grade_desc;
    }

    public void setGrade_desc(String grade_desc) {
        this.grade_desc = grade_desc;
    }
}
