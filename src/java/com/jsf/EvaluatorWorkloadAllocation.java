/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import java.util.*;
//import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private String school;
    private String csLevel;
    private String teacher;
    private String schoolID;
    private String schoolName;
    private List<String> evaluator_list = new ArrayList<>();
    private List<String> school_list = new ArrayList<>();
    private List<String> cslevel_list = new ArrayList<>();

    public EvaluatorWorkloadAllocation() {
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

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
            //PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolID = ?");
            //st.setString(1, schoolID);
            //ResultSet rs = st.executeQuery();
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

    public void main(String args[]) {

    }
}
