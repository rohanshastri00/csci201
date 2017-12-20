
package shastri;

import java.util.List;

public class Schedule {


    private List<Textbook> textbooks = null;
    private List<Week> weeks = null;

    public List<Textbook> getTextbooks() {
        return textbooks;
    }

    public void setTextbooks(List<Textbook> textbooks) {
        this.textbooks = textbooks;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }

}
