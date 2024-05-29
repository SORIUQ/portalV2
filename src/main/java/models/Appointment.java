package models;

public class Appointment {

    private String id;
    private int teacherID;
    private Integer studentID;
    private String date;
    private String time;

    public Appointment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

//    public String getDate() {
//        return date;
//    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", teacherID=" + teacherID +
                ", studentID=" + studentID +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
