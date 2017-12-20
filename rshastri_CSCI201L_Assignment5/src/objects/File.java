package objects;

//file inherits from GeneralObject because labs have the exact same member variables
public class File extends GeneralObject{
	private String parent;
	private String year;
	private String semester;
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
	
}
