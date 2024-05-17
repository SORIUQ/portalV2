package models;



public class School {
	
	private int idSchool;
	private String schoolName;
	private String tlfSchool;
	private String email;
	private String scheduleSchool;
	private String locSchool;
	private String mapLink;

	public School() {

	}

	public School(int idSchool, String nombreSchool, String tlfSchool, String email, String scheduleSchool,
			String locSchool, String mapLink) {
		this.idSchool = idSchool;
		this.schoolName = nombreSchool;
		this.tlfSchool = tlfSchool;
		this.email = email;
		this.scheduleSchool = scheduleSchool;
		this.locSchool = locSchool;
		this.mapLink = mapLink;
	}

	
	//GETTERS Y SETTERS 
	
	
	public int getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(int idSchool) {
		this.idSchool = idSchool;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getTlfSchool() {
		return tlfSchool;
	}

	public void setTlfSchool(String tlfSchool) {
		this.tlfSchool = tlfSchool;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getScheduleSchool() {
		return scheduleSchool;
	}

	public void setScheduleSchool(String scheduleSchool) {
		this.scheduleSchool = scheduleSchool;
	}

	public String getLocSchool() {
		return locSchool;
	}

	public void setLocSchool(String locSchool) {
		this.locSchool = locSchool;
	}

	public String getMapLink() {
		return mapLink;
	}

	public void setMapLink(String mapLink) {
		this.mapLink = mapLink;
	}
}
