package objects;

import java.util.List;

public class Test {
	private String title;
	private String year;
	private String semester;
	private List<File> files;

	public String getTitle() {
		return title;
	}

	public List<File> getFiles() {
		return files;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

}
