package objects;

import java.util.List;

public class Topic extends GeneralObject {
	private String chapter;
	private List<Program> programs;
	private String date;
	
	public String getChapter() {
		return chapter;
	}

	public List<Program> getPrograms() {
		return programs;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

}
