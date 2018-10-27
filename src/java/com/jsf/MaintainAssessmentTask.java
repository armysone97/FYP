/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class MaintainAssessmentTask {

    private Connection con;

    private String status, state, school, teacher;

    private List<String> school_list = new ArrayList<>();
    private List<String> teacher_list = new ArrayList<>();

    private Boolean disabledDDL;

    public MaintainAssessmentTask() {
        disabledDDL = false;
        this.state = "Pulau Pinang";
        this.school = "SMK Air Itam";
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Boolean changeDDLDisabled() {

        if (disabledDDL) {
            disabledDDL = false;
        } else {
            disabledDDL = true;
        }

        return disabledDDL;
    }

    //remove duplicate element for teacher name array
    public static int removeDuplicateElementsString(String arr[], int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        String[] temp = new String[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (!arr[i].equals(arr[i + 1])) {
                temp[j++] = arr[i];
            }
        }
        temp[j++] = arr[n - 1];
        
        // Changing original array  
        for (int i = 0; i < j; i++) {
            arr[i] = temp[i];
        }
        return j;
    }

    //get school list and put inside ddl based on the state that is selected by user in ddl
    public List<String> get_school() {

        school_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolName FROM school WHERE schoolState = ?");
            st.setString(1, state);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                school_list.add(rs.getString("schoolName"));
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return school_list;
    }

    //use in function get_teacher(), to count the teacher id in database
    public int retrieveTeacherIDCount(String[] schoolCSMapID){
        
        int count = 0;
       
        for (int i = 0; i < schoolCSMapID.length; i++) {
            String teacherIDTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolCSMapID[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    count++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
        
        return count;
    }
    
    //use in function get_teacher(), to count the schoolCSMap id in database
    public int retrieveSchoolCSMapIDCount(String schoolID){
        
        int count = 0;
        
          try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
          
          return count;
    }

    //get teacher list and put inside ddl based on the school that is selected by user in ddl
    public List<String> get_teacher() {

        String teacherIDFromDB = "", schoolIDFromDB = "", schoolCSMapIDFromDB = "";
        int countArr = 0;
        
        int tmpCount = 0, tmpCount1 = 0;

        teacher_list.clear();

     //   FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

     //get schoolID first, based on schoolName that is selected by user in dll
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolName = ?");
            st.setString(1, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolIDFromDB = rs.getString("schoolID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        
        int schoolCSMapListCount = retrieveSchoolCSMapIDCount(schoolIDFromDB);
        String[] schoolCSMapIDListDuplicate = new String[schoolCSMapListCount];

        //then get schoolCSMapID, based on schoolID which retrived on above
        //due to one schoolID has one or many schoolCSMapID, so schoolCSMapID stored in array named "schoolCSMapIDListDuplicate"
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMapIDFromDB = rs.getString("schoolCSMapID");
                schoolCSMapIDListDuplicate[tmpCount] = schoolCSMapIDFromDB;
                tmpCount++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        
        int teacherIDListCount = retrieveTeacherIDCount(schoolCSMapIDListDuplicate);
        String[] teacherIDListDuplicate = new String[teacherIDListCount];
        
        //due to schoolCSMapIDListDuplicate is an array, need to get the teacherID in for loop
        //after that, stored teacherID into an array named "teacherIDListDuplicate"
        for (int i = 0; i < schoolCSMapIDListDuplicate.length; i++) {

            String teacherIDTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolCSMapIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacherIDTmp = rs.getString("teacherID");
                    teacherIDListDuplicate[tmpCount1] = teacherIDTmp;
                    tmpCount1++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        //sorting array 
        Arrays.sort(teacherIDListDuplicate); 

        int length = teacherIDListDuplicate.length;
        
        //this function is to remove duplicate elements in array named "teacherIDListDuplicate"
        length = removeDuplicateElementsString(teacherIDListDuplicate, length); 

        //finally due to teacherID is an array, need to get the teacherName in for loop
        //last, add teacherName into ddl
        for (int i = 0; i < length; i++) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
                st.setString(1, teacherIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacher_list.add(rs.getString("teacherName"));
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

        }

        return teacher_list;
    }

    public void abcClick() {

        //  school = "SMJK Heng Yee";
        status = "yy1";
//        state = "Kuala Lumpur";
//
//      //  setSchool("SMJK Heng Yee");
//        setStatus("yy");
//        setState("Kedah");
        changeDDLDisabled();
    }

    public void hahax() {
        school = "SMK Heng Yee";
        status = "yy3";
    }

    public void main(String args[]) {
        //tryclick();
    }

}
