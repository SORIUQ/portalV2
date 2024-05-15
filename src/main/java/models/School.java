package models;



public class School {
	
	int idSchool;
	String nameSchool;
	String tlfSchool; 
	String email;
	String scheduleSchool;
	String locSchool;

	public School() {}

	public School(int idSchool, String nombreSchool, String tlfSchool, String email, String scheduleSchool,
			String locSchool) {
		this.idSchool = idSchool;
		this.nameSchool = nombreSchool;
		this.tlfSchool = tlfSchool;
		this.email = email;
		this.scheduleSchool = scheduleSchool;
		this.locSchool = locSchool;
	}

	
	//GETTERS Y SETTERS 
	
	
	public int getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(int idSchool) {
		this.idSchool = idSchool;
	}

	public String getNameSchool() {
		return nameSchool;
	}

	public void setNameSchool(String nombreSchool) {
		this.nameSchool = nombreSchool;
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


	@Override
	public String toString() {
		return "School{" +
				"idSchool=" + idSchool +
				", nameSchool='" + nameSchool + '\'' +
				", tlfSchool='" + tlfSchool + '\'' +
				", email='" + email + '\'' +
				", scheduleSchool='" + scheduleSchool + '\'' +
				", locSchool='" + locSchool + '\'' +
				'}';
	}
}
