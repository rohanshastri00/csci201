package com.jsonparse;

import java.util.List;


public class Meeting {

private String type;
private String section;
private String room;
private List<MeetingPeriod> meetingPeriods = null;
private List<Assistant> assistants = null;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getSection() {
return section;
}

public void setSection(String section) {
this.section = section;
}

public String getRoom() {
return room;
}

public void setRoom(String room) {
this.room = room;
}

public List<MeetingPeriod> getMeetingPeriods() {
return meetingPeriods;
}

public void setMeetingPeriods(List<MeetingPeriod> meetingPeriods) {
this.meetingPeriods = meetingPeriods;
}

public List<Assistant> getAssistants() {
return assistants;
}

public void setAssistants(List<Assistant> assistants) {
this.assistants = assistants;
}

}