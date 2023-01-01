package com.example.acropolisadmin.Modelpack;

public class AddStudentModel {

    String studentName , studentEnroll , studentEmail , department ;

    public AddStudentModel() {
    }

    public AddStudentModel(String studentName, String studentEnroll, String studentEmail, String department) {
        this.studentName = studentName;
        this.studentEnroll = studentEnroll;
        this.studentEmail = studentEmail;
        this.department = department;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEnroll() {
        return studentEnroll;
    }

    public void setStudentEnroll(String studentEnroll) {
        this.studentEnroll = studentEnroll;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
