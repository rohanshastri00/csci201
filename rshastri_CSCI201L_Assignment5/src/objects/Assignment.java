package objects;

import java.util.List;

public class Assignment extends GeneralAssignment {
	
	private String url;
	private List<File> gradingCriteriaFiles;
	private List<File> solutionFiles;
	private List<Deliverable> deliverables;

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public List<Deliverable> getDeliverables() {
		return deliverables;
	}
	
	public void setDeliverables(List<Deliverable> deliverables) {
		this.deliverables = deliverables;
	}

	public List<File> getGradingCriteriaFiles() {
		return gradingCriteriaFiles;
	}

	public List<File> getSolutionFiles() {
		return solutionFiles;
	}

	public void setGCF(List<File> gradingCriteriaFiles) {
		this.gradingCriteriaFiles = gradingCriteriaFiles;
	}

	public void setSF(List<File> solutionFiles) {
		this.solutionFiles = solutionFiles;
	}
	
	


}
