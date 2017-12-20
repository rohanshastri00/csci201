package shastri;

import java.util.List;

public class Department {

private String longName;
private String prefix;
private List<Course> courses = null;

public String getLongName() {
return longName;
}

public void setLongName(String longName) {
this.longName = longName;
}

public String getPrefix() {
return prefix;
}

public void setPrefix(String prefix) {
this.prefix = prefix;
}

public List<Course> getCourses() {
return courses;
}

public void setCourses(List<Course> courses) {
this.courses = courses;
}

}