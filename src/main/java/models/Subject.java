package models;

public class Subject {
    private Integer subjectId;
    private String name;
    private int weeklyHours;
    private int totalHours;

    public Subject(Integer subjectId, String name, int weeklyHours, int totalHours) {
        this.subjectId = subjectId;
        this.name = name;
        this.weeklyHours = weeklyHours;
        this.totalHours = totalHours;
    }

    public Subject() {}

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", name='" + name + '\'' +
                ", weeklyHours=" + weeklyHours +
                ", totalHours=" + totalHours +
                '}';
    }
}
