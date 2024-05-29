package models;

public class Internship {
    private User tutor;
    private User student;
    private float grade;

    public Internship(User tutor, User student, float grade) {
        this.tutor = tutor;
        this.student = student;
        this.grade = grade;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Internship{" +
                "tutor=" + tutor +
                ", student=" + student +
                ", grade=" + grade +
                '}';
    }
}
