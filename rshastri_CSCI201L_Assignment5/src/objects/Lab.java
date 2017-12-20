package objects;

import java.util.ArrayList;
import java.util.List;

//lab extends GeneralObject because it has the exact same member variables as deliverables
public class Lab extends GeneralObject{
	private List<File> files;
	private Integer week;

	public List<File> getFiles(){
		return files;
	}

	public Integer getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}

	public void setLabFiles(ArrayList<File> matches) {
		files = matches;
	}

}