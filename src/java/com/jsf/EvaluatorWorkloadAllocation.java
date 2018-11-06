/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
//import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
//import javax.faces.context.FacesContext;

/**
 *
 * @author ruenyenchin
 */
@ManagedBean
@SessionScoped

public class EvaluatorWorkloadAllocation {

    private Connection con;
    private String evaluator;
    //private String takeEvaluator;
    private String school;
    private String csLevel;
    private String teacher;
    private int workloadLimit;
    private int totalStudent;
    private String assType;
    private int workloadMin;
    private double workloadAssigned;
    private int year = 2018;
    private String result;

//    private String name;
//    private String schoolName;
    private List<String> evaluator_list = new ArrayList<>();
    private List<String> school_list = new ArrayList<>();
    private List<String> cslevel_list = new ArrayList<>();
    private List<String> teacher_list = new ArrayList<>();
    private List<String> assessment_list = new ArrayList<>();

    public EvaluatorWorkloadAllocation(String evaluator, String school) {
        super();
        this.evaluator = evaluator;
        this.school = school;
    }

    public EvaluatorWorkloadAllocation() {
        super();
    }

    public int getWorkloadLimit() {
        return workloadLimit;
    }

    public void setWorkloadLimit(int workloadLimit) {
        this.workloadLimit = workloadLimit;
    }

    public int getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(int totalStudent) {
        this.totalStudent = totalStudent;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCsLevel() {
        return csLevel;
    }

    public void setCsLevel(String csLevel) {
        this.csLevel = csLevel;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getAssType() {
        return assType;
    }

    public void setAssType(String assType) {
        this.assType = assType;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public double getWorkloadAssigned() {
        return workloadAssigned;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setWorkloadAssigned(double workloadAssigned) {
        this.workloadAssigned = workloadAssigned;
    }

    public void setEvaluator_list(List<String> evaluator_list) {
        this.evaluator_list = evaluator_list;
    }

    public void setSchool_list(List<String> school_list) {
        this.school_list = school_list;
    }

    public void setCslevel_list(List<String> cslevel_list) {
        this.cslevel_list = cslevel_list;
    }

    public void setTeacher_list(List<String> teacher_list) {
        this.teacher_list = teacher_list;
    }

    public void setAssessment_list(List<String> assessment_list) {
        this.assessment_list = assessment_list;
    }

    public List<EvaluatorWorkloadAllocation> findAll() {
        List<EvaluatorWorkloadAllocation> listWorkload = new ArrayList<EvaluatorWorkloadAllocation>();

        return listWorkload;
    }

    //retrieve total number of student
    public void retrieveTotalStudent() {

        String schoolIDFromDB = "", teacherIDFromDB = "", CSLevelIDFromDB = "", schoolCSMapIDFromDB = "";

        //retrieve schoolID based on schoolName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolName = ?");
            st.setString(1, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolIDFromDB = rs.getString("schoolID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //retrieve CSLevelID based on CSLevelName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
            st.setString(1, csLevel);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CSLevelIDFromDB = rs.getString("CSLevelID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //retrieve teacherID based on teacherName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teacher WHERE teacherName = ?");
            st.setString(1, teacher);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teacherIDFromDB = rs.getString("teacherID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //retrieve schoolCSMaplID based on schoolID and CSLevelID which retrieved on above
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ? AND CSLevelID = ?");
            st.setString(1, schoolIDFromDB);
            st.setString(2, CSLevelIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMapIDFromDB = rs.getString("schoolCSMapID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //retrieve teacherCSMapID based on schoolCSMaplID and teacherID which retrieved on above
        //and get the total number of student
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT numSampleAss FROM teachercsmap WHERE schoolCSMapID = ? AND teacherID = ?");
            st.setString(1, schoolCSMapIDFromDB);
            st.setString(2, teacherIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                totalStudent = rs.getInt("numSampleAss");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void retrieveWorkloadLimit() {

        //  workloadLimit = "xx";
        String staffIDFromDB = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT staffID FROM evaluatorpersonaldetails WHERE name = ?");
            st.setString(1, evaluator);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                staffIDFromDB = rs.getString("staffID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT workloadLimit FROM workloadlimit WHERE staffID = ?");
            st.setString(1, staffIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                workloadLimit = rs.getInt("workloadLimit");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void retrieveNumSamAss() {

        String assActivityIDFromDB = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT assActivityID FROM assessmentactivity WHERE assActivityName = ?");
            st.setString(1, assType);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                assActivityIDFromDB = rs.getString("assActivityID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT minPerStud FROM assessment WHERE assActivityID = ? AND year = ?");
            st.setString(1, assActivityIDFromDB);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                workloadMin = rs.getInt("minPerStud");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        calculateWorkload();
    }

    public void calculateWorkload() {

        workloadAssigned = workloadMin * totalStudent;

        result = String.format("%.2f", (workloadAssigned / 60));
    }

    public List<String> get_EvaluatorList() {

        evaluator_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM evaluatorpersonaldetails WHERE status='Available'");

            while (rs.next()) {
                evaluator_list.add(rs.getString("name"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        retrieveWorkloadLimit();

        return evaluator_list;
    }

    public List<String> get_SchoolList() {

        school_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT schoolName FROM school");

            while (rs.next()) {
                school_list.add(rs.getString("schoolName"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return school_list;
    }

//    public void verifySchoolID(){
//        
//        schoolName = this.school;
//        
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT schoolName, schoolID FROM school");
//            //context.addMessage(null, new FacesMessage("yyy"));
//            while (rs.next()) {
//                if (schoolName.equals("schoolName")) {
//                    schoolID = rs.getString("schoolID");
////                    scID = rs.getString("schoolID");
////                    context.addMessage(null, new FacesMessage("xxxx" + scID));
//                   
//                    break;
//
//                }
//            }
//
//            rs.close();
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
//    }
    public List<String> get_CSLevelList() {

//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("xxxx"));
        cslevel_list.clear();

        // String scID = "";
        // verifySchoolID();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            //ResultSet rs = st.executeQuery("SELECT school.schoolID, school.schoolName, schoolcsmap.CSLevelID FROM school INNER JOIN schoolcsmap ON school.schoolID = schoolcsmap.schoolID");
            //ResultSet rs = st.executeQuery("SELECT CSLevelID FROM schoolcsmap WHERE schoolID = ?");
            //st.setString(1, schoolID);
//            ResultSet rs = st.executeQuery();
            ResultSet rs = st.executeQuery("SELECT CSLevelName FROM cslevel");

            while (rs.next()) {
                cslevel_list.add(rs.getString("CSLevelName"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return cslevel_list;
    }

    public List<String> get_TeacherList() {

        teacher_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT teacherName FROM teacher");

            while (rs.next()) {
                teacher_list.add(rs.getString("teacherName"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return teacher_list;
    }

//    public void addEvaluator(){
//        
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO testing (school) VALUES (?)");
//                      
//            statement.setString(1, evaluator);
//            
//            statement.executeUpdate();
//            statement.close();
//            con.close();
//            
//        }catch(Exception ex){
//            System.out.println("Error: " + ex);
//        }
//        
//    }
    public List<String> get_AssessmentList() {

        assessment_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT assActivityName FROM assessmentactivity");

            while (rs.next()) {
                assessment_list.add(rs.getString("assActivityName"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return assessment_list;
    }

    //navigation bar purpose
    public String goToNextPage() {
        reset();
        return "EvaluatorWorkloadAllocationApplication";
    }

    //reset page
    public void reset() {
        school = null;
        csLevel = null;
        teacher = null;
        workloadLimit = 0;
        totalStudent = 0;
        assType = null;
        workloadMin = 0;
        workloadAssigned = 0;
        year = 2018;
    }

    public void main(String args[]) {

    }
}
