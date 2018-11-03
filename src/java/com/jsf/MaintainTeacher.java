/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.MaintainAssessmentTask.removeDuplicateElementsString;
import static com.jsf.MaintainSchoolDetails.removeDuplicateElementsString;
import static com.jsf.YearOfStudySetting.removeDuplicateElementsString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class MaintainTeacher {

    private Connection con;

    private List<String> school_list = new ArrayList<>();
    private List<String> state_list = new ArrayList<>();
    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db
    private List<String> teacher_list = new ArrayList<>();
    private List<String> newTeacherID_list = new ArrayList<>();

    private int studNum, year;
    private String school, state, cslevel, teacher, status;

    private int commYear;

    private Boolean disabledTxt, disabledDdl, disabledStatus;

    private String temp;

    private String newTeacherID, newTeacherName, newTeacherStatus, newTeacherCSLevel, newTeacherSchoolState, newTeacherSchoolName;
    private int newTeacherYear, newTeacherStudNum;

    private int newCount;

    private Boolean disabledButton, disabledNewTeacher, disabledNewTeacherID, disabledNewTeacherName;

    public MaintainTeacher() {
        this.state = "Pulau Pinang";
        this.school = "SJK Air Itam";
        this.year = 2018;
        this.commYear = 0;
        this.teacher = "Teoh Wei Ran";
//        this.cslevel = "CS Level 1";
        this.disabledTxt = true;
        this.disabledDdl = true;
        this.disabledStatus = true;
        this.disabledButton = false;
        this.disabledNewTeacher = true;
        this.disabledNewTeacherID = true;
        this.disabledNewTeacherName = true;
        this.newCount = 0;
    }

    public Boolean getDisabledNewTeacherID() {
        return disabledNewTeacherID;
    }

    public void setDisabledNewTeacherID(Boolean disabledNewTeacherID) {
        this.disabledNewTeacherID = disabledNewTeacherID;
    }

    public Boolean getDisabledNewTeacherName() {
        return disabledNewTeacherName;
    }

    public void setDisabledNewTeacherName(Boolean disabledNewTeacherName) {
        this.disabledNewTeacherName = disabledNewTeacherName;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public Boolean getDisabledStatus() {
        return disabledStatus;
    }

    public void setDisabledStatus(Boolean disabledStatus) {
        this.disabledStatus = disabledStatus;
    }

    public Boolean getDisabledButton() {
        return disabledButton;
    }

    public void setDisabledButton(Boolean disabledButton) {
        this.disabledButton = disabledButton;
    }

    public Boolean getDisabledNewTeacher() {
        return disabledNewTeacher;
    }

    public void setDisabledNewTeacher(Boolean disabledNewTeacher) {
        this.disabledNewTeacher = disabledNewTeacher;
    }

    public String getNewTeacherCSLevel() {
        return newTeacherCSLevel;
    }

    public void setNewTeacherCSLevel(String newTeacherCSLevel) {
        this.newTeacherCSLevel = newTeacherCSLevel;
    }

    public String getNewTeacherSchoolState() {
        return newTeacherSchoolState;
    }

    public void setNewTeacherSchoolState(String newTeacherSchoolState) {
        this.newTeacherSchoolState = newTeacherSchoolState;
    }

    public String getNewTeacherSchoolName() {
        return newTeacherSchoolName;
    }

    public void setNewTeacherSchoolName(String newTeacherSchoolName) {
        this.newTeacherSchoolName = newTeacherSchoolName;
    }

    public int getNewTeacherStudNum() {
        return newTeacherStudNum;
    }

    public void setNewTeacherStudNum(int newTeacherStudNum) {
        this.newTeacherStudNum = newTeacherStudNum;
    }

    public String getNewTeacherID() {
        return newTeacherID;
    }

    public void setNewTeacherID(String newTeacherID) {
        this.newTeacherID = newTeacherID;
    }

    public String getNewTeacherName() {
        return newTeacherName;
    }

    public void setNewTeacherName(String newTeacherName) {
        this.newTeacherName = newTeacherName;
    }

    public String getNewTeacherStatus() {
        return newTeacherStatus;
    }

    public void setNewTeacherStatus(String newTeacherStatus) {
        this.newTeacherStatus = newTeacherStatus;
    }

    public int getNewTeacherYear() {
        return newTeacherYear;
    }

    public void setNewTeacherYear(int newTeacherYear) {
        this.newTeacherYear = newTeacherYear;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Boolean getDisabledDdl() {
        return disabledDdl;
    }

    public void setDisabledDdl(Boolean disabledDdl) {
        this.disabledDdl = disabledDdl;
    }

    public Boolean getDisabledTxt() {
        return disabledTxt;
    }

    public void setDisabledTxt(Boolean disabledTxt) {
        this.disabledTxt = disabledTxt;
    }

    public int getCommYear() {
        return commYear;
    }

    public void setCommYear(int commYear) {
        this.commYear = commYear;
    }

    public int getStudNum() {
        return studNum;
    }

    public void setStudNum(int studNum) {
        this.studNum = studNum;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCslevel() {
        return cslevel;
    }

    public void setCslevel(String cslevel) {
        this.cslevel = cslevel;
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

    public List<String> get_newTeacherID() {
        newTeacherID_list.clear();

        switch (newCount) {
            case 1: //select
                int allTeacherIDListCount = retrieveAllTeacherIDCount();
                String[] allTeacherIDListDuplicate = new String[allTeacherIDListCount];

                allTeacherIDListDuplicate = showAllTeacher(allTeacherIDListCount); //get all teacher

                int teacherIDNotInSCCount = retrieveTeacherIDNotInSCCount(allTeacherIDListDuplicate);
                String[] teacherIDNotInSC = new String[teacherIDNotInSCCount];

                teacherIDNotInSC = showTeacherNotInSC(allTeacherIDListDuplicate, teacherIDNotInSCCount); //get teacher which not in db

                Arrays.sort(teacherIDNotInSC);

                for (int i = 0; i < teacherIDNotInSC.length; i++) {

                    //        String teacherName = retriveTeacherName(teacherIDNotInSC[i]);
                    newTeacherID_list.add(teacherIDNotInSC[i]);
                }

                break;
            case 2: //add new
                newTeacherID_list.add(autoGenerateTeacherID());
                break;
        }

        return newTeacherID_list;

    }

    public void showTeacherName() {
        String teacherName = retriveTeacherName(newTeacherID);

        newTeacherName = teacherName;
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

    //count state in db
    public Integer get_stateCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT schoolState FROM school");

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

    //get year from db 
    public List<String> get_state() {

        //  disabledDdl = true;
        //  disabledTxt = true;
        state_list.clear();

        int lengthYearList = get_stateCount();

        String[] stateListDuplicate = new String[lengthYearList];

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        int tmp = 0;

        // stateListDuplicate[0] = 2018;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT schoolState FROM school");

            while (rs.next()) {
                stateListDuplicate[tmp] = rs.getString("schoolState");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        Arrays.sort(stateListDuplicate);//sorting array  
        int length = stateListDuplicate.length;
        length = removeDuplicateElementsString(stateListDuplicate, length);

        //printing array elements  
        for (int i = 0; i < length; i++) {
            state_list.add(stateListDuplicate[i]);
        }
        return state_list;
    }

    public void controlDisabledStatus() {
        disabledStatus = true;
    }

    //get school list and put inside ddl based on the state that is selected by user in ddl
    public List<String> get_school() {

        //disabledStatus = true;
        disabledTxt = true;

        school_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //remove duplicate element for year array
    public static int removeDuplicateElements(int arr[], int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int[] temp = new int[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] != arr[i + 1]) {
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

    public String matchSchoolID() {

        String schoolID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolState = ? AND schoolName = ?");
            st.setString(1, state);
            st.setString(2, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolID = rs.getString("schoolID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return schoolID;
    }

    //count year in db
    public Integer get_yearCount(String schoolID) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT year FROM schoolcsmap WHERE schoolID = ?");
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

    public Boolean verifyRecord(String schoolID) {

        Boolean verify = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ? AND year = '2018'");
            st.setString(1, schoolID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                verify = true;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return verify;
    }

    //get year from db 
    public List<Integer> get_year() {

        disabledDdl = true;
        disabledTxt = true;

        year_list.clear();

        //  int statusOfYearLength = 0;
        String schoolID = matchSchoolID();

        int lengthYearList = get_yearCount(schoolID);
        int lengthYearListEnhance = 0;

        Boolean verifyRecord = verifyRecord(schoolID);

        if (verifyRecord) { //means got 2018 data 
            lengthYearListEnhance = lengthYearList;
        } else {
            lengthYearListEnhance = lengthYearList + 1;
        }

        int[] yearListDuplicate = new int[lengthYearListEnhance];

        int tmp = 0;

        if (verifyRecord) { //means got 2018 data 
            //  lengthYearListEnhance = lengthYearList;
        } else {
            yearListDuplicate[0] = 2018;
            tmp++;
        }

//        if (lengthYearList == 0) {
//            lengthYearList = 1;
//       //     statusOfYearLength = 1;
//        }
        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;

        // yearListDuplicate[0] = 2018;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT year FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                yearListDuplicate[tmp] = rs.getInt("year");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        for (int i = 0; i < yearListDuplicate.length; i++) {
            System.out.println(yearListDuplicate[i]);
        }

        //  if (statusOfYearLength == 1) {
        //        yearListDuplicate[0] = 2018;
        //        year_list.add(2018);
        //  } else {
        Arrays.sort(yearListDuplicate);//sorting array  
        int length = yearListDuplicate.length;
        length = removeDuplicateElements(yearListDuplicate, length);

        //printing array elements  
        for (int i = 0; i < length; i++) {
            year_list.add(yearListDuplicate[i]);
        }
        //    }

        return year_list;
    }

    //use in function get_teacher(), to count the teacher id in database
    public int retrieveTeacherIDCount(String[] schoolCSMapID) {

        int count = 0;

        for (int i = 0; i < schoolCSMapID.length; i++) {
            String teacherIDTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
    public int retrieveSchoolCSMapIDCount(String schoolID) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //use in function get_teacher(), to count the teacher id in database
    public int retrieveAllTeacherIDCount() {

        int count = 0;

        String teacherIDTmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM teacher");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    public String[] showAllTeacher(int allTeacherIDListCount) {

        String[] allTeacherIDListDuplicate = new String[allTeacherIDListCount];
        int i = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT teacherID FROM teacher");

            while (rs.next()) {
                allTeacherIDListDuplicate[i] = rs.getString("teacherID");
                i++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return allTeacherIDListDuplicate;
    }

    public int retrieveTeacherIDNotInSCCount(String[] allTeacherIDListDuplicate) {

        int j = 0;
        int count = 0;

        for (int i = 0; i < allTeacherIDListDuplicate.length; i++) {

            Boolean verify = false;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM teachercsmap WHERE teacherID = ?");
                st.setString(1, allTeacherIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    verify = true;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            if (!verify) {
                count++;
                j++;
            }
        }

        return count;
    }

    public String[] showTeacherNotInSC(String[] allTeacherIDListDuplicate, int teacherIDNotInSCCount) {
        String[] teacherIDNotInSC = new String[teacherIDNotInSCCount];

        int j = 0;

        for (int i = 0; i < allTeacherIDListDuplicate.length; i++) {

            Boolean verify = false;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM teachercsmap WHERE teacherID = ?");
                st.setString(1, allTeacherIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    verify = true;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            if (!verify) {
                teacherIDNotInSC[j] = allTeacherIDListDuplicate[i];
                j++;
            }
        }

        return teacherIDNotInSC;
    }

    public String retriveTeacherName(String teacherIDNotInSC) {

        String tName = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
            st.setString(1, teacherIDNotInSC);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                tName = rs.getString("teacherName");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return tName;
    }

    //get teacher list and put inside ddl based on the school that is selected by user in ddl
    public List<String> get_teacher() {

        disabledDdl = true;
        disabledTxt = true;

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        Boolean verifyTeacherRecord = false;

        //then get schoolCSMapID, based on schoolID which retrived on above
        //due to one schoolID has one or many schoolCSMapID, so schoolCSMapID stored in array named "schoolCSMapIDListDuplicate"
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMapIDFromDB = rs.getString("schoolCSMapID");
                schoolCSMapIDListDuplicate[tmpCount] = schoolCSMapIDFromDB;
                tmpCount++;
//                verifyTeacherRecord = true;
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolCSMapIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacherIDTmp = rs.getString("teacherID");
                    teacherIDListDuplicate[tmpCount1] = teacherIDTmp;
                    tmpCount1++;
//                    verifyTeacherRecord = true;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

//        if (!verifyTeacherRecord) {
//
//            //  newTeacherName = "";
//            // newTeacherName = "xx";
//            int allTeacherIDListCount = retrieveAllTeacherIDCount();
//            String[] allTeacherIDListDuplicate = new String[allTeacherIDListCount];
//
//            allTeacherIDListDuplicate = showAllTeacher(allTeacherIDListCount); //get all teacher
//
//            int teacherIDNotInSCCount = retrieveTeacherIDNotInSCCount(allTeacherIDListDuplicate);
//            String[] teacherIDNotInSC = new String[teacherIDNotInSCCount];
//
//            teacherIDNotInSC = showTeacherNotInSC(allTeacherIDListDuplicate, teacherIDNotInSCCount); //get teacher which not in db
//
//            Arrays.sort(teacherIDNotInSC);
//
//            for (int i = 0; i < teacherIDNotInSC.length; i++) {
//
//                String teacherName = retriveTeacherName(teacherIDNotInSC[i]);
//
//                teacher_list.add(teacherName);
//            }
//
//        } else {
        //     newTeacherName = "";
        //     newTeacherName = "yy";
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
//        }

        return teacher_list;
    }

    public void calculateCommYear() {

        disabledDdl = true;
        disabledTxt = true;

//        if (year == 2018) {
//            disabledTxt = false;
//            disabledDdl = false;
//        } else {
//            disabledTxt = true;
//            disabledDdl = true;
//        }
        String schoolID = matchSchoolID();

        commYear = 0;

        int numYearComm = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT commYearCS FROM school WHERE schoolID = ?");
            st.setString(1, schoolID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                numYearComm = rs.getInt("commYearCS");
                //   tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        commYear = year - numYearComm;

        // tmp = "commYear012: " + commYear + " numYearComm20182017: " + numYearComm + " year: " + year;
    }

    //count csID in db
    public int get_csCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
            st.setInt(1, commYear);
            st.setInt(2, year);
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

    public int countSchoolCSMapID(String thID) {
        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM teachercsmap WHERE teacherID = ?");
            st.setString(1, thID);
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

    public String[] matchSchoolCSMapID1(String thID, int length) {

        String[] scList = new String[length];
        int i = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM teachercsmap WHERE teacherID = ?");
            st.setString(1, thID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                scList[i] = rs.getString("schoolCSMapID");
                i++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        Arrays.sort(scList);//sorting array  
        int length1 = scList.length;
        length1 = removeDuplicateElementsString(scList, length1);

        return scList;
    }

    public int countCSID(String[] scList) {
        int count = 0;

        for (int i = 0; i < scList.length; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolCSMapID = ?");
                st.setString(1, scList[i]);
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

    public void callStatus() {
        String thID = matchTeacherID();
        status = matchStatus(thID);
        disabledStatus = false;
    }

    public String matchStatus(String thID) {
        String status1 = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherStatus FROM teacher WHERE teacherID = ?");
            st.setString(1, thID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                status1 = rs.getString("teacherStatus");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return status1;
    }

    public String matchCSLevelName(String csid) {

        String csName = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
            st.setString(1, csid);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                csName = rs.getString("CSLevelName");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return csName;

    }

    public int matchStudNum(String scID) {
        int studNum = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT enrolStudNum FROM teachercsmap WHERE schoolCSMapID = ?");
            st.setString(1, scID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                studNum = rs.getInt("enrolStudNum");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return studNum;
    }

    public List<String> get_CSLevel() {

        CSLevel_list.clear();

        //1. add button beside teacher, then use teacherID to find db teachercsmap
        //2. if got data, then declare array get schoolcsmap
        //3. go to schoolcsmap find csID, and go cs table find csName 
        //4. if not, then same as below
        //extra: if commercial year for a school is 2018. then go add new teacher, cs level same as 4.
        String thID = matchTeacherID(); //1. find teacher id based on teacher name
        int scListLength = countSchoolCSMapID(thID); //2. declare school cs map id array length
        String[] scList = new String[scListLength]; //3. declare school cs map id array
        scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id

        int year1 = 0, tmpCount = 0;
        Boolean verify = false;

        for (int i = 0; i < scList.length; i++) { //5. find year based on school cs map id, if matched, means got data inside
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT year FROM schoolcsmap WHERE schoolCSMapID = ?");
                st.setString(1, scList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    year1 = rs.getInt("year");

                    if (year1 == year) {
                        verify = true;
                        tmpCount++;
                    }
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        //    String[] scListNew = new String[tmpCount]; 
        //    tmp = "verify: " + verify + " thID: " + thID;
        int csListLength = countCSID(scList); //2. declare school cs map id array length
        String[] csList = new String[csListLength]; //3. declare school cs map id array
        String csID = "", csName = "";

        //   scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id
        if ((verify && year != 2018) || (verify && year == 2018)) {

            status = matchStatus(thID);
            disabledDdl = false;
            //  newTeacherName = year1 + "x";

            //  disabledTxt = true;
            //  disabledDdl = true;
            //1. use school cs map id array and year go find cs id
            for (int i = 0; i < scList.length; i++) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolCSMapID = ? AND year = ?");
                    st.setString(1, scList[i]);
                    st.setInt(2, year);
                    ResultSet rs = st.executeQuery();

                    while (rs.next()) {
                        csID = rs.getString("CSLevelID");
                        csName = matchCSLevelName(csID);
                        CSLevel_list.add(csName);

                        if (cslevel.equals(csName)) {
                            studNum = matchStudNum(scList[i]);

                            disabledTxt = true;
                        }
                    }

                    st.close();
                    con.close();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }

            }

            //    temp = "studNum: " + studNum + " status: " + status + " csID: " + csID + " cslevel: " + cslevel;
        } else {

            disabledDdl = false;
            disabledTxt = false;

            //  newTeacherName = year1 + "y";
            //disabledTxt = false;
            // disabledDdl = false;
            int lengthCSIDList = get_csCount();

            String[] CSIDListDuplicate = new String[lengthCSIDList];
            int tmp = 0;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
                st.setInt(1, commYear);
                st.setInt(2, year);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    CSIDListDuplicate[tmp] = rs.getString("CSLevelID");
                    tmp++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            Arrays.sort(CSIDListDuplicate);//sorting array  
            int length = CSIDListDuplicate.length;
            length = removeDuplicateElementsString(CSIDListDuplicate, length);

            String[] CSNameList = new String[length];
            int tmp1 = 0;

            //printing array elements  
            for (int i = 0; i < length; i++) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
                    st.setString(1, CSIDListDuplicate[i]);
                    ResultSet rs = st.executeQuery();

                    while (rs.next()) {
                        CSNameList[tmp1] = rs.getString("CSLevelName");
                        tmp1++;
                    }

                    st.close();
                    con.close();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }
            }

            for (int i = 0; i < length; i++) {
                CSLevel_list.add(CSNameList[i]);
            }
        }

        return CSLevel_list;
    }

    //auto generate year of study cs map ID
    public String autoGenerateTeacherCSMapID() {

        int count = 0;
        String tcID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM teachercsmap");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        count++;

        tcID = "TC" + count;

        return tcID;
    }

    public String matchCSLevelID() {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
            st.setString(1, cslevel);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CSID = rs.getString("CSLevelID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return CSID;

    }

    public String matchSchoolCSMapID() {

        String scID = "", csID = "", schoolID = "";
        csID = matchCSLevelID();
        schoolID = matchSchoolID();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE CSLevelID = ? AND schoolID = ? AND year = ?");
            st.setString(1, csID);
            st.setString(2, schoolID);
            st.setInt(3, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                scID = rs.getString("schoolCSMapID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return scID;
    }

    public String matchTeacherID() {

        String thID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teacher WHERE teacherName = ?");
            st.setString(1, teacher);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                thID = rs.getString("teacherID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return thID;
    }

    public int matchNumSampleAss() {

        int num = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT numSampleAss FROM rate WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                num = rs.getInt("numSampleAss");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return num;

    }

    public void addSchoolCSMap() {
        String tcID = "", scID = "", thID = "";
        int numSampleAss = 0;

        tcID = autoGenerateTeacherCSMapID();
        scID = matchSchoolCSMapID();
        thID = matchTeacherID();
        numSampleAss = matchNumSampleAss();

        updateTeacher(thID);

        //   tmp = "tcID : " + tcID + " scID : " + scID + " thID : " + thID + " numSampleAss : " + numSampleAss;
        //insert teachercsmap
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO teachercsmap (teacherCSMapID, enrolStudNum, numSampleAss, schoolCSMapID, teacherID) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, tcID);
            statement.setInt(2, studNum);
            statement.setInt(3, numSampleAss);
            statement.setString(4, scID);
            statement.setString(5, thID);
            statement.executeUpdate();

            statement.close();
            con.close();

            updateTtlEnrolStud(scID);

//
//            disabledTxt = true;
//            disabledAddButton = false;
//            disabledEditButton = false;
//
//            addButtonName = "New";
//            counterCSLevel(numYearComm);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    //update teacher
    public void updateTeacher(String thID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE teacher SET teacherName = ?, teacherStatus = ? WHERE teacherID = ?");

            statement.setString(1, teacher);
            statement.setString(2, status);
            statement.setString(3, thID);
            statement.executeUpdate();

            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    //update ttlenrolstud in schoolcsmap table
    public void updateTtlEnrolStud(String scID) {

        int originalStud = 0, latestStud = 0;

        //get the original ttlenrolstud num first
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT ttlEnrolStud FROM schoolcsmap WHERE schoolCSMapID = ?");
            st.setString(1, scID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                originalStud = rs.getInt("ttlEnrolStud");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //then original ttlstud add on with new ttlstud
        latestStud = originalStud + studNum;

        //then update latest ttlstud inside schoolcsmap table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE schoolcsmap SET ttlEnrolStud = ? WHERE schoolCSMapID = ?");

            statement.setInt(1, latestStud);
            statement.setString(2, scID);
            statement.executeUpdate();

            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        // temp = "lateststud: " + latestStud + " studNum: " + studNum + " originalStud: " + originalStud + " scID: " + scID;
    }

    //auto generate teacher ID
    public String autoGenerateTeacherID() {

        int count = 0;
        String thID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM teacher");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        count++;

        thID = "TH" + count;

        return thID;
    }

    public void selectNewTeacher() {

        newCount = 1;

        // newTeacherID = "TH21";
        newTeacherStatus = "Available";
        newTeacherYear = year;
        newTeacherSchoolState = state;
        newTeacherSchoolName = school;
        newTeacherCSLevel = cslevel;

        disabledButton = true;
        disabledNewTeacher = false;
        disabledNewTeacherID = false;
        disabledNewTeacherName = true;
        disabledTxt = true;
        disabledDdl = true;
    }

    public void addNewTeacher() {

        newCount = 2;

        newTeacherID = autoGenerateTeacherID();
        newTeacherStatus = "Available";
        newTeacherYear = year;
        newTeacherSchoolState = state;
        newTeacherSchoolName = school;
        newTeacherCSLevel = cslevel;

        disabledButton = true;
        disabledNewTeacher = false;
        disabledNewTeacherID = true;
        disabledNewTeacherName = false;
        disabledTxt = true;
        disabledDdl = true;
    }

    public void addTeacher() {

        newCount = 0;

        String ttt = "";

        ttt = newTeacherName;

        newTeacherID = autoGenerateTeacherID();

        //   newTeacherName = "newTeacherID: " + newTeacherID + " newTeacherName: " + ttt + " newTeacherStatus: " + newTeacherStatus + " newTeacherYear: " + newTeacherYear;
        newTeacherStudNum = 65412;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO teacher (teacherID, teacherName, teacherStatus, year) VALUES (?, ?, ?, ?)");

            statement.setString(1, newTeacherID);
            statement.setString(2, newTeacherName);
            statement.setString(3, newTeacherStatus);
            statement.setInt(4, newTeacherYear);
            statement.executeUpdate();

            //newTeacher 
            newTeacherStudNum = 12345;

            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String tcID = "", scID = "", thID = "";
        int numSampleAss = 0;

        tcID = autoGenerateTeacherCSMapID();
        scID = matchSchoolCSMapID();
        //   thID = matchTeacherID();
        numSampleAss = matchNumSampleAss();

        //   tmp = "tcID : " + tcID + " scID : " + scID + " thID : " + thID + " numSampleAss : " + numSampleAss;
        //insert teachercsmap
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO teachercsmap (teacherCSMapID, enrolStudNum, numSampleAss, schoolCSMapID, teacherID) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, tcID);
            statement.setInt(2, newTeacherStudNum);
            statement.setInt(3, numSampleAss);
            statement.setString(4, scID);
            statement.setString(5, newTeacherID);
            statement.executeUpdate();

            statement.close();
            con.close();

            disabledButton = false;
            disabledNewTeacher = true;
            disabledTxt = false;
            disabledDdl = false;
            disabledNewTeacherID = true;
            disabledNewTeacherName = true;

            newTeacherID = null;
            newTeacherName = null;
            newTeacherStatus = null;
            newTeacherCSLevel = null;
            newTeacherSchoolState = null;
            newTeacherSchoolName = null;
            newTeacherYear = 0;
            newTeacherStudNum = 0;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    //reset page
    public void reset() {

        //set default value
        state = "Pulau Pinang";
        school = "SMJK Heng Yee";
        year = 2018;
        commYear = 0;
        teacher = "Teoh Kok Xing";
        studNum = 0;
        cslevel = "CS Level 1";
        status = "Available";

        newTeacherID = null;
        newTeacherName = null;
        newTeacherStatus = null;
        newTeacherCSLevel = null;
        newTeacherSchoolState = null;
        newTeacherSchoolName = null;
        newTeacherYear = 0;
        newTeacherStudNum = 0;
        newCount = 0;

        //set default disabled
        disabledTxt = true;
        disabledDdl = true;
        disabledButton = false;
        disabledNewTeacher = true;
        disabledNewTeacherID = true;
        disabledNewTeacherName = true;
    }

}
