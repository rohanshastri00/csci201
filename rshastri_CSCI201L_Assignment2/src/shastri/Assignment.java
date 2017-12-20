
package shastri;

import java.util.List;




public class Assignment {

    private String number;
    private String assignedDate;
    private String dueDate;
    private String title;
    private String url;
    private List<File> files = null;
    private String gradePercentage;
    private List<Deliverable> deliverables = null;
    private List<GradingCriteriaFile> gradingCriteriaFiles = null;
    private List<SolutionFile> solutionFiles = null;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getGradePercentage() {
        return gradePercentage;
    }

    public void setGradePercentage(String gradePercentage) {
        this.gradePercentage = gradePercentage;
    }

    public List<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }
    public List<GradingCriteriaFile> getGradingCriteriaFiles() {
    		return gradingCriteriaFiles;
    	}
    
    public void setGradingCriteriaFiles(List<GradingCriteriaFile> gradingCriteriaFiles) {
    		this.gradingCriteriaFiles = gradingCriteriaFiles;
    	}

    public List<SolutionFile> getSolutionFiles() {
    		return solutionFiles;
    	}

    	public void setSolutionFiles(List<SolutionFile> solutionFiles) {
    		this.solutionFiles = solutionFiles;
    	}
}
