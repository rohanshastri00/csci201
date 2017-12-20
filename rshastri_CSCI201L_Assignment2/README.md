# Rohan Shastri
# Student ID: 29300976094
# CSCI 201L Homework 2
# September 24th, 2017
# rshastri@usc.edu
# Section: 30389
# CSCI 201L Website (JSON-Populated)

Builds on Assignment 1 by taking the 
Java objects created by parsing JSON file
and then populates the USC CSCI 201 Website.
Further website functionality added in later assignments.

Run index.jsp to begin the program. 
Will not take in any non JSON files, 
even if different file type is selected. If
session expires, will be redirected to index.jsp
where another or the same JSON file can be re-selected.


#JSONImport.java 

Servlet for program. Json file is selected from
index.jsp, sent through JSONImport, and then 
populated within frontpage.jsp, lectures.jsp,
exams.jsp, and assignments.jsp.


#Assignment2Rohan.json

This includes an additional lecture time which 
coincides with Assignment 1's due date as well as
a Deliverable, allowing to see "ASSIGNMENT 1 DUE" 
and "(Deliverable name) DUE" appear on lectures.jsp.

#No additional notes to provide graders
