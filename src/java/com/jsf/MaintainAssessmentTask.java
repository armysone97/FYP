/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.CSLevelSetting.removeDuplicateElementsString;
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

    //private List<SelectItem> school_list;
//    {
//    }
    public MaintainAssessmentTask() {
        disabledDDL = false;
        this.state = "Pulau Pinang";
        this.school = "SMK Air Itam";
    }

//    public List<SelectItem> getSchool_list() {
//        return school_list;
//    }
//
//    public void setSchool_list(List<SelectItem> school_list) {
//        this.school_list = school_list;
//    }
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
    
      public Integer retrieveTeacherNameCount(String teacherID) {
          
          int count = 0;
          
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
            st.setString(1, teacherID);
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
      
      int countA = 0;

    public void retrieveTeacherName(String teacherID, int countArr) {
        
        int lengthForTeacherNameArr = retrieveTeacherNameCount(teacherID);
         
        
         String teacherNameList[] = new String[8];
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
            st.setString(1, teacherID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
           //     teacher_list.add("x: " + countA + " : ");
                teacherNameList[countA] = rs.getString("teacherName");
         //       teacher_list.add(teacherNameList[countA] + countA);
                countA++;
                
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        teacher_list.add("x: " + teacherNameList.length);
        
       //  Arrays.sort(teacherNameList);//sorting array  
         
//                 for (int i = 0; i < teacherNameList.length; i++) {
//           // System.out.println("x : " + teacherNameList[i] + " ");
//           teacher_list.add(teacherNameList[i]);
//        }
         
         
//        int length = teacherNameList.length;
//        length = removeDuplicateElementsString(teacherNameList, length);
//
//        for (int i = 0; i < length; i++) {
//           // System.out.println("x : " + teacherNameList[i] + " ");
//           teacher_list.add(teacherNameList[i]);
//        }


        
        
    }

    public void retrieveTeacherID(String schoolCSMapID, int countArr) {

        String teacherIDTmp = "";
       

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teachercsmap WHERE schoolCSMapID = ?");
            st.setString(1, schoolCSMapID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teacherIDTmp = rs.getString("teacherID");
                retrieveTeacherName(teacherIDTmp, countArr);
                //   teacher_list.add(teacherIDTmp);
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //   return teacherIDTmp;
    }

    public List<String> get_teacher() {

        String teacherIDFromDB = "", schoolIDFromDB = "", schoolCSMapIDFromDB = "";
         int countArr = 0;

        teacher_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolName = ?");
            st.setString(1, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolIDFromDB = rs.getString("schoolID");
                //   teacher_list.add(schoolIDFromDB);
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMapIDFromDB = rs.getString("schoolCSMapID");
                //teacher_list.add(rs.getString("schoolCSMapID"));
                retrieveTeacherID(schoolCSMapIDFromDB, countArr);
                countArr++;
                //   teacher_list.add(teacherIDFromDB);
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
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
