DROP DATABASE IF EXISTS rshastri_201_site;
CREATE DATABASE rshastri_201_site;

USE rshastri_201_site;

CREATE TABLE Schools (
	schoolName VARCHAR(20) PRIMARY KEY NOT NULL,
    imageLink VARCHAR(500) NOT NULL
);

    
CREATE TABLE Departments (
	deptName VARCHAR(20) PRIMARY KEY NOT NULL,
    prefix VARCHAR(5) NOT NULL,
	schoolName VARCHAR (20) NOT NULL,
    FOREIGN KEY key1(schoolName) REFERENCES Schools(schoolName)
);


CREATE TABLE Courses (
	courseNumber VARCHAR(10) PRIMARY KEY NOT NULL,
    courseName VARCHAR(100) NOT NULL, 
    term VARCHAR(15) NOT NULL,
    courseYear INT (5) NOT NULL,
    units INT(5) NOT NULL,
    syllabus VARCHAR (500) NOT NULL,
    deptName VARCHAR(20) NOT NULL,
    FOREIGN KEY key1(deptName) REFERENCES Departments(deptName)
);

  
CREATE TABLE Meetings (
	section VARCHAR(10) PRIMARY KEY NOT NULL,
	meetingType VARCHAR(10) NOT NULL,
    room VARCHAR (10) NOT NULL,
    courseNumber VARCHAR(10) NOT NULL,
	FOREIGN KEY key1(courseNumber) REFERENCES Courses(courseNumber)
);


CREATE TABLE MeetingPeriods (
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	meetingDay VARCHAR(10) NOT NULL,
    meetingStart VARCHAR(10) NOT NULL,
    meetingEnd VARCHAR (10) NOT NULL,
    section VARCHAR (10) NOT NULL,
	FOREIGN KEY key1(section) REFERENCES Meetings(section)
);


CREATE TABLE Assistants (
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	staffMemberID INT(5) NOT NULL,
    section VARCHAR (10) NOT NULL,
	FOREIGN KEY key1(section) REFERENCES Meetings(section)
);








CREATE TABLE StaffMembers (
    staffMemberID INT(5) PRIMARY KEY NOT NULL,
    staffType VARCHAR(40)  NOT NULL,
    firstName VARCHAR (20) NOT NULL,
    lastName VARCHAR (20) NOT NULL,
    email VARCHAR (100) NOT NULL,
    image VARCHAR (500) NOT NULL,
    phone VARCHAR (20),
    office VARCHAR (10),
    courseNumber VARCHAR(10) NOT NULL,
	FOREIGN KEY key1(courseNumber) REFERENCES Courses(courseNumber)
);


CREATE Table OfficeHours (
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	staffMemberID INT(5) NOT NULL,
	FOREIGN KEY key1(staffMemberID) REFERENCES StaffMembers(staffMemberID),
	startTime VARCHAR(10) NOT NULL,
    endTime VARCHAR(10) NOT NULL,
    OHday VARCHAR (10) NOT NULL,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL
);








CREATE TABLE Textbooks (
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	textBookNum INT(5) NOT NULL,
    author VARCHAR(40) NOT NULL,
    title VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    bookYear INT (10) NOT NULL,
    isbn VARCHAR (40) NOT NULL,
    courseNumber VARCHAR(10) NOT NULL,
	FOREIGN KEY key1(courseNumber) REFERENCES Courses(courseNumber)
);

CREATE TABLE Weeks(
	weekID INT(5) PRIMARY KEY NOT NULL,
    courseNumber VARCHAR(10) NOT NULL,
	FOREIGN KEY key1(courseNumber) REFERENCES Courses(courseNumber)
);


CREATE TABLE Labs(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	weekID INT(5) NOT NULL,
    labTitle VARCHAR(50) NOT NULL,
	labID INT (5) NOT NULL,
    labURL VARCHAR (500) NOT NULL
);

CREATE TABLE LabFiles(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	fileNumber INT (5) NOT NULL,
    fileTitle VARCHAR(50) NOT NULL,
	fileURL VARCHAR(500) NOT NULL,
    labTitle VARCHAR(50) NOT NULL
);


CREATE TABLE Lectures(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	weekID INT(5) NOT NULL,
    lectureDate VARCHAR(20) NOT NULL,
	lectureID INT (5) NOT NULL,
    lectureDay VARCHAR (20) NOT NULL
);

CREATE TABLE Topics(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
    topicTitle VARCHAR(50) NOT NULL,
	topicNumber INT (5) NOT NULL,
    topicURL VARCHAR(500) NOT NULL,
	topicChapter VARCHAR (20),
    lectureDate VARCHAR(20) NOT NULL
);

CREATE TABLE Programs(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
    topicTitle VARCHAR(50) NOT NULL
);

CREATE TABLE LectureFiles(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	lectureDate VARCHAR(20) NOT NULL,
	fileNumber INT (5) NOT NULL,
    fileTitle VARCHAR(50) NOT NULL,
	fileURL VARCHAR(500) NOT NULL,
	topicTitle VARCHAR(50) NOT NULL
);







  
CREATE TABLE Assignments (
	assignmentNumber VARCHAR (30) PRIMARY KEY,
	assignedDate VARCHAR(10),
    dueDate VARCHAR (10),
    assignTitle VARCHAR(50),
    assignURL VARCHAR(500),
    gradePercentage VARCHAR (10),
	courseNumber VARCHAR(10),
	FOREIGN KEY key1(courseNumber) REFERENCES Courses(courseNumber)
);

CREATE TABLE AssignmentFiles(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	fileNumber INT (5) NOT NULL,
    fileTitle VARCHAR(50) NOT NULL,
	fileURL VARCHAR(500) NOT NULL,
	assignmentNumber VARCHAR (30) NOT NULL,
	FOREIGN KEY key1(assignmentNumber) REFERENCES Assignments(assignmentNumber)
);

CREATE TABLE AssignmentGCFs(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	GCFileNumber INT (5) NOT NULL,
    GCFileTitle VARCHAR(50) NOT NULL,
	GCFileURL VARCHAR(500) NOT NULL,
	assignmentNumber VARCHAR (30) NOT NULL,
	FOREIGN KEY key1(assignmentNumber) REFERENCES Assignments(assignmentNumber)
);

CREATE TABLE AssignmentSFs(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	SFileNumber INT (5) NOT NULL,
    SFileTitle VARCHAR(50) NOT NULL,
	SFileURL VARCHAR(500) NOT NULL,
	assignmentNumber VARCHAR (30) NOT NULL,
	FOREIGN KEY key1(assignmentNumber) REFERENCES Assignments(assignmentNumber)
);


CREATE TABLE Deliverables(
	delivNumber VARCHAR (20) PRIMARY KEY,
    dueDate VARCHAR(20), 
    title VARCHAR(200), 
    gradePercentage VARCHAR (10), 
	assignmentNumber VARCHAR (30), 
	FOREIGN KEY key1(assignmentNumber) REFERENCES Assignments(assignmentNumber)
);

CREATE TABLE DeliverableFiles(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	delivFileNum INT (5),
    delivFileTitle VARCHAR(50),
	delivFileURL VARCHAR(500),
	delivNumber VARCHAR (20)
);



CREATE TABLE Exams(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
    examYear VARCHAR(10) NOT NULL,
	examSemester VARCHAR (10) NOT NULL,
	courseNumber VARCHAR(10) NOT NULL,
	FOREIGN KEY key1(courseNumber) REFERENCES Courses(courseNumber)
);

CREATE TABLE Tests(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	testTitle VARCHAR (50) NOT NULL,
    examYear VARCHAR(10) NOT NULL,
	examSemester VARCHAR (10) NOT NULL
);

CREATE TABLE TestFiles(
	rowID INT(5) PRIMARY KEY AUTO_INCREMENT,
	testFileNumber INT (5) NOT NULL,
    testFileTitle VARCHAR(50) NOT NULL,
	testFileURL VARCHAR(500) NOT NULL,
	testTitle VARCHAR (50) NOT NULL,
	examYear VARCHAR(10) NOT NULL,
	examSemester VARCHAR (10) NOT NULL
);

