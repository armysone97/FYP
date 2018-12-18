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

public class MaintainTeacher {

    private Connection con;

    private List<String> school_list = new ArrayList<>();
    private List<String> state_list = new ArrayList<>();
    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db
    private List<String> teacher_list = new ArrayList<>();
    private List<String> newTeacherID_list = new ArrayList<>();
    private List<String> newTeacherIDName_list = new ArrayList<>();
    private List<String> newCSLevel_list = new ArrayList<>();

    private int studNum, year;
    private String school, state, cslevel, teacher, status;

    private int commYear;

    private Boolean disabledTxt, disabledDdl, disabledStatus;

    private String temp;

    private String newTeacherID, newTeacherIDName, newTeacherName, newTeacherStatus, newTeacherCSLevel, newTeacherSchoolState, newTeacherSchoolName;
    private int newTeacherYear, newTeacherStudNum;

    private int newCount;

    private Boolean disabledButton, disabledNewTeacher, disabledNewTeacherID, disabledNewTeacherIDName, disabledNewTeacherName;

    private int counterReset; //growl purpose

    //datatable
    private String cslevelNew;
    private int studNumNew;
    private ArrayList cslevelList;
    private MaintainTeacher studNumObj = null;

    private int counterDataTable;

    //valuechangelistener purpose
    private String schoolNameDefault, teacherNameDefault;
//    private int yearDefault;

    public MaintainTeacher() {
        this.state = "Pulau Pinang";
        this.school = "SJK Air Itam";
        this.year = 2018;
        this.commYear = 0;
        this.teacher = "Teoh Wei Ran";
        this.status = "Available";
        this.schoolNameDefault = "SJK Air Itam";
        this.teacherNameDefault = "Teoh Wei Ran";
//        this.cslevel = "CS Level 1";
        this.disabledTxt = true;
        this.disabledDdl = true;
        this.disabledStatus = true;
        this.disabledButton = false;
        this.disabledNewTeacher = true;
        this.disabledNewTeacherID = true;
        this.disabledNewTeacherName = true;
        this.disabledNewTeacherIDName = true;
        this.newTeacherID = null;
        this.newTeacherName = null;
        this.newTeacherIDName = "TH1 - Teoh Wei Ran";
        this.newCount = 0;
        this.counterReset = 0;
        this.counterDataTable = 0;
    }

    public String getCslevelNew() {
        return cslevelNew;
    }

    public void setCslevelNew(String cslevelNew) {
        this.cslevelNew = cslevelNew;
    }

    public int getStudNumNew() {
        return studNumNew;
    }

    public void setStudNumNew(int studNumNew) {
        this.studNumNew = studNumNew;
    }

    public String getNewTeacherIDName() {
        return newTeacherIDName;
    }

    public void setNewTeacherIDName(String newTeacherIDName) {
        this.newTeacherIDName = newTeacherIDName;
    }

    public Boolean getDisabledNewTeacherIDName() {
        return disabledNewTeacherIDName;
    }

    public void setDisabledNewTeacherIDName(Boolean disabledNewTeacherIDName) {
        this.disabledNewTeacherIDName = disabledNewTeacherIDName;
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

    public int retrieveTeacherIDFromSchoolLength() {
//         disabledDdl = true;
//        disabledTxt = true;

        String teacherIDFromDB = "", schoolIDFromDB = "", schoolCSMapIDFromDB = "";
        int countArr = 0;

        int tmpCount = 0, tmpCount1 = 0;

        //   FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        //get schoolID first, based on schoolName that is selected by user in dll
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        //sorting array 
        Arrays.sort(teacherIDListDuplicate);

        int length = teacherIDListDuplicate.length;

        //this function is to remove duplicate elements in array named "teacherIDListDuplicate"
        length = removeDuplicateElementsString(teacherIDListDuplicate, length);

        return length;
    }

    public String[] retrieveTeacherIDFromSchool() {
//         disabledDdl = true;
//        disabledTxt = true;

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        //sorting array 
        Arrays.sort(teacherIDListDuplicate);

        int length = teacherIDListDuplicate.length;

        //this function is to remove duplicate elements in array named "teacherIDListDuplicate"
        length = removeDuplicateElementsString(teacherIDListDuplicate, length);

        return teacherIDListDuplicate;
    }

    public List<String> get_newTeacherIDName() { //select
        newTeacherIDName_list.clear();

//        newTeacherSchoolName = "schoolxx: " + newCount;
        switch (newCount) {
            case 0:
                newTeacherIDName_list.add("---");
                newTeacherIDName = "---";
                break;
            case 1: //select
//                newTeacherIDName_list.add(" - lkjmmmmmmm");
//                break;
            case 3: //select
                int allTeacherIDListCount = retrieveAllTeacherIDCount();
                String[] allTeacherIDListDuplicate = new String[allTeacherIDListCount];

                allTeacherIDListDuplicate = showAllTeacher(allTeacherIDListCount); //get all teacher

//                     for (int i = 0; i < allTeacherIDListDuplicate.length; i++) {
//                    newTeacherID_list.add(teacherIDNotInSC[i]);
//  newTeacherIDName_list.add(allTeacherIDListCount + " - lkk");
//   newTeacherIDName_list.add(" - lkj");
//                }
                int teacherIDNotInSCCount = retrieveTeacherIDNotInSCCount(allTeacherIDListDuplicate);
                String[] teacherIDNotInSC = new String[teacherIDNotInSCCount];

                teacherIDNotInSC = showTeacherNotInSC(allTeacherIDListDuplicate, teacherIDNotInSCCount); //get teacher which not in db

                for (int i = 0; i < teacherIDNotInSC.length; i++) {

//                    newTeacherID_list.add(teacherIDNotInSC[i]);
//  newTeacherIDName_list.add(teacherIDNotInSC[i] + " - lkk");
                }

                int teacherIDForSchoolCount = retrieveTeacherIDFromSchoolLength();

                int finalTeacherIDCount = teacherIDNotInSCCount + teacherIDForSchoolCount;

                String[] finalTeacherID = new String[finalTeacherIDCount];
                String[] teacherIDFromSchool = new String[teacherIDForSchoolCount];

                teacherIDFromSchool = retrieveTeacherIDFromSchool();

                Arrays.sort(teacherIDNotInSC);
                for (int i = 0; i < teacherIDNotInSC.length; i++) {

                    //        String teacherName = retriveTeacherName(teacherIDNotInSC[i]);
                    finalTeacherID[i] = teacherIDNotInSC[i];
//                    newTeacherID_list.add(teacherIDNotInSC[i]);
//                    newTeacherID_list.add(finalTeacherID[i]);
                }

                int teacherIDCounter = 0;
                teacherIDCounter = teacherIDNotInSC.length;

//                newTeacherSchoolName = teacherIDFromSchool.length + " : " + teacherIDForSchoolCount + " schoolzz: " + teacherIDCounter + " : " + finalTeacherIDCount;
                for (int i = 0; i < teacherIDForSchoolCount; i++) {
                    finalTeacherID[teacherIDCounter] = teacherIDFromSchool[i];
                    teacherIDCounter++;
                }

                Arrays.sort(finalTeacherID);

                String teacherID = "",
                 teacherName = "";
                for (int i = 0; i < finalTeacherID.length; i++) {
                    teacherID = finalTeacherID[i];
                    teacherName = retriveTeacherName(teacherID);
                    newTeacherIDName_list.add(teacherID + " - " + teacherName);
                }

//                get_newCSLevel();
                break;
            case 2: //add new
//                  newTeacherName = "teacherName";
//                newTeacherSchoolName = "schoolyy";
//                newTeacherIDName_list.add(autoGenerateTeacherID());
                newTeacherIDName_list.add("---");
                break;
            default:
                newTeacherIDName_list.add("---");
        }
        return newTeacherIDName_list;

    }

    public List<String> get_newTeacherID() { //add new
        newTeacherID_list.clear();

//        newTeacherSchoolName = "schoolxx: " + newCount;
        switch (newCount) {
            case 1: //select
            case 3: //select
//                int allTeacherIDListCount = retrieveAllTeacherIDCount();
//                String[] allTeacherIDListDuplicate = new String[allTeacherIDListCount];
//
//                allTeacherIDListDuplicate = showAllTeacher(allTeacherIDListCount); //get all teacher
//
//                int teacherIDNotInSCCount = retrieveTeacherIDNotInSCCount(allTeacherIDListDuplicate);
//                String[] teacherIDNotInSC = new String[teacherIDNotInSCCount];
//
//                teacherIDNotInSC = showTeacherNotInSC(allTeacherIDListDuplicate, teacherIDNotInSCCount); //get teacher which not in db
//
//                for (int i = 0; i < teacherIDNotInSC.length; i++) {
//
////                    newTeacherID_list.add(teacherIDNotInSC[i]);
//                }
//
//                int teacherIDForSchoolCount = retrieveTeacherIDFromSchoolLength();
//
//                int finalTeacherIDCount = teacherIDNotInSCCount + teacherIDForSchoolCount;
//
//                String[] finalTeacherID = new String[finalTeacherIDCount];
//                String[] teacherIDFromSchool = new String[teacherIDForSchoolCount];
//
//                teacherIDFromSchool = retrieveTeacherIDFromSchool();
//
//                Arrays.sort(teacherIDNotInSC);
//                for (int i = 0; i < teacherIDNotInSC.length; i++) {
//
//                    //        String teacherName = retriveTeacherName(teacherIDNotInSC[i]);
//                    finalTeacherID[i] = teacherIDNotInSC[i];
////                    newTeacherID_list.add(teacherIDNotInSC[i]);
////                    newTeacherID_list.add(finalTeacherID[i]);
//                }
//
//                int teacherIDCounter = 0;
//                teacherIDCounter = teacherIDNotInSC.length;
//
////                newTeacherSchoolName = teacherIDFromSchool.length + " : " + teacherIDForSchoolCount + " schoolzz: " + teacherIDCounter + " : " + finalTeacherIDCount;
//                for (int i = 0; i < teacherIDForSchoolCount; i++) {
//                    finalTeacherID[teacherIDCounter] = teacherIDFromSchool[i];
//                    teacherIDCounter++;
//                }
//
//                Arrays.sort(finalTeacherID);
//
//                for (int i = 0; i < finalTeacherID.length; i++) {
//                    newTeacherID_list.add(finalTeacherID[i]);
//                }

//               
//                if (newTeacherIDName.isEmpty()) {
                newTeacherID_list.add("---");
//                newTeacherID_list.add(newTeacherIDName);
//                } else {
//                    String[] parts = newTeacherIDName.split(" - ");
//                    newTeacherID = parts[0];
//                    newTeacherName = parts[1];
//                }

//
                break;
            case 2: //add new
//                  newTeacherName = "teacherName";
//                newTeacherSchoolName = "schoolyy";
                newTeacherID_list.add(autoGenerateTeacherID());
                break;
        }

        return newTeacherID_list;

    }

    public int countSchoolCSMapIDNew(String schoolID) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID, CSLevelID FROM schoolcsmap WHERE schoolID = ? AND year = ?");
            st.setString(1, schoolID);
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

    public int countSchoolCSMapIDNew1(String[] schoolCSMapIDList) {

        int count = 0;
//        Boolean verifyTrue = false;

        String[] parts = newTeacherIDName.split(" - ");
        String newTeacherID1 = parts[0];

        for (int i = 0; i < schoolCSMapIDList.length; i++) {

            Boolean verifyTrue = false;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM teachercsmap WHERE teacherID = ? AND schoolCSMapID = ?");
                st.setString(1, newTeacherID1);
                st.setString(2, schoolCSMapIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    verifyTrue = true;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            if (!verifyTrue) {
                count++;
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();

//        context.addMessage(null, new FacesMessage(count + " hhh: " + newTeacherID1));
        return count;
    }

    public List<String> get_newCSLevel() {
        newCSLevel_list.clear();

        String schoolID = matchSchoolID();
        int countSchoolCSMapID = countSchoolCSMapIDNew(schoolID);

        String[] schoolCSMapIDList = new String[countSchoolCSMapID];
        String[] CSLevelIDList = new String[countSchoolCSMapID];

        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID, CSLevelID FROM schoolcsmap WHERE schoolID = ? AND year = ?");
            st.setString(1, schoolID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMapIDList[tmp] = rs.getString("schoolCSMapID");
                CSLevelIDList[tmp] = rs.getString("CSLevelID");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        switch (newCount) {
            case 1: //select (before select teacherID and Name)
//                for (int i = 0; i < CSLevelIDList.length; i++) {
//                    newCSLevel_list.add(matchCSLevelName(CSLevelIDList[i]));
//                }
//                int countSchoolCSMapID1x = countSchoolCSMapIDNew1(schoolCSMapIDList);
//                 newCSLevel_list.add(countSchoolCSMapID1x + "xxxxdsxx");

//                FacesContext context = FacesContext.getCurrentInstance();
//                context.addMessage(null, new FacesMessage("zz : " + countSchoolCSMapID + " : yy")); 
//                break;
            case 3: //select (after select teacherID and Name)

//        for (int i = 0; i < CSLevelIDList.length; i++) {
//            newCSLevel_list.add(CSLevelIDList[i]);
//        }
//                context.addMessage(null, new FacesMessage("xx : " + newTeacherIDName + " : yy"));
                String schoolCSMapID = "";
                int countSchoolCSMapID1 = countSchoolCSMapIDNew1(schoolCSMapIDList);
                String[] schoolCSMapIDList1 = new String[countSchoolCSMapID1];
                String[] CSLevelIDList1 = new String[countSchoolCSMapID1];

                int tmp1 = 0;

//        Boolean verifyTrue = false;
                String[] parts = newTeacherIDName.split(" - ");
                String newTeacherID1 = parts[0];

//                 context.addMessage(null, new FacesMessage(newTeacherID1 + "zz : " + countSchoolCSMapID1 + " : yy"));
                for (int i = 0; i < schoolCSMapIDList.length; i++) {

//                    context.addMessage(null, new FacesMessage("hhh"));
                    Boolean verifyTrue = false;
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                        PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM teachercsmap WHERE teacherID = ? AND schoolCSMapID = ?");
                        st.setString(1, newTeacherID1);
                        st.setString(2, schoolCSMapIDList[i]);
                        ResultSet rs = st.executeQuery();

                        while (rs.next()) {
//                    schoolCSMapIDList1[tmp1] = rs.getString("schoolCSMapID");
//                    tmp1++;
//                            context.addMessage(null, new FacesMessage("mmm"));
                            verifyTrue = true;
                        }

                        st.close();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println("Error: " + ex);
                    }

                    if (!verifyTrue) {
                        schoolCSMapIDList1[tmp1] = schoolCSMapIDList[i];
                        CSLevelIDList1[tmp1] = CSLevelIDList[i];

//                        context.addMessage(null, new FacesMessage("zz : " + schoolCSMapIDList1[tmp1] + " : yy"));
                        tmp1++;
                    }
                }

                for (int i = 0; i < CSLevelIDList1.length; i++) {

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                        PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
                        st.setString(1, CSLevelIDList1[i]);
                        ResultSet rs = st.executeQuery();
                        while (rs.next()) {
                            newCSLevel_list.add(rs.getString("CSLevelName"));
                        }

                        st.close();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println("Error: " + ex);
                    }
                }

                break;
            case 2: //add
                for (int i = 0; i < CSLevelIDList.length; i++) {
                    newCSLevel_list.add(matchCSLevelName(CSLevelIDList[i]));
                }
                break;
        }

        return newCSLevel_list;
    }

    public void showTeacherName() {

        newCount = 3;

        String[] parts = newTeacherIDName.split(" - ");
        String newTeacherID1 = parts[0];
        String newTeacherName1 = parts[1];

//        String teacherName = retriveTeacherName(newTeacherID);
//
//        newTeacherName = teacherName;
        String teacherStatus = retriveTeacherStatus(newTeacherID1);

        newTeacherStatus = teacherStatus;

        get_newCSLevel();
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        int schoolNameDefaultCount = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolName FROM school WHERE schoolState = ?");
            st.setString(1, state);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                school_list.add(rs.getString("schoolName"));

                //valuechangelistener purpose
                if (schoolNameDefaultCount == 0) {
                    schoolNameDefault = rs.getString("schoolName");
                    schoolNameDefaultCount++;
                }
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolState = ? AND schoolName = ?");
            st.setString(1, newTeacherSchoolState);
            st.setString(2, newTeacherSchoolName);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public String matchSchoolID1() {

        String schoolID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //get year from db 
    public List<Integer> get_year() {

        disabledDdl = true;
        disabledTxt = true;

        year_list.clear();

        //  int statusOfYearLength = 0;
        String schoolID = matchSchoolID1();

        int lengthYearList = get_yearCount(schoolID);
        int lengthYearListEnhance = 0;

        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("xxxyyy " + schoolID + " : x"));

        Boolean verifyRecord = verifyRecord(schoolID);

        if (verifyRecord) { //means got 2018 data 
            lengthYearListEnhance = lengthYearList;
//            context.addMessage(null, new FacesMessage("bbb"));
        } else {
            lengthYearListEnhance = lengthYearList + 1;
//            context.addMessage(null, new FacesMessage("ccc"));
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
//        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;

        // yearListDuplicate[0] = 2018;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public String retriveTeacherStatus(String teacherIDNotInSC) {

        String tStatus = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherStatus FROM teacher WHERE teacherID = ?");
            st.setString(1, teacherIDNotInSC);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                tStatus = rs.getString("teacherStatus");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return tStatus;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        int teacherNameDefaultCount = 0;

        //finally due to teacherID is an array, need to get the teacherName in for loop
        //last, add teacherName into ddl
        for (int i = 0; i < length; i++) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
                st.setString(1, teacherIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacher_list.add(rs.getString("teacherName"));

                    //valuechangelistener purpose
                    if (teacherNameDefaultCount == 0) {
                        teacherNameDefault = rs.getString("teacherName");
                        teacherNameDefaultCount++;
                    }
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolCSMapID = ? AND year = ?");
                st.setString(1, scList[i]);
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

        }

        return count;
    }

    public void callStatus() {
        String thID = matchTeacherID();
        status = matchStatus(thID);
        disabledStatus = true;
        counterDataTable = 1;

        getCslevelList();
    }

    public String matchStatus(String thID) {
        String status1 = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public int matchStudNum(String scID, String thID) {
        int studNum = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT enrolStudNum FROM teachercsmap WHERE schoolCSMapID = ? AND teacherID = ?");
            st.setString(1, scID);
            st.setString(2, thID);
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
        String thID = matchTeacherID1(); //1. find teacher id based on teacher name
        int scListLength = countSchoolCSMapID(thID); //2. declare school cs map id array length
        String[] scList = new String[scListLength]; //3. declare school cs map id array
        scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id

        int year1 = 0, tmpCount = 0;
        Boolean verify = false;

        for (int i = 0; i < scList.length; i++) { //5. find year based on school cs map id, if matched, means got data inside
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolCSMapID = ? AND year = ?");
                    st.setString(1, scList[i]);
                    st.setInt(2, year);
                    ResultSet rs = st.executeQuery();

                    while (rs.next()) {
                        csID = rs.getString("CSLevelID");
                        csName = matchCSLevelName(csID);
                        CSLevel_list.add(csName);

                        if (cslevel.equals(csName)) {
//                            String thID = matchTeacherID();
                            studNum = matchStudNum(scList[i], thID);

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
        }
//        else {
//
//            disabledDdl = false;
//            disabledTxt = false;
//
//            //  newTeacherName = year1 + "y";
//            //disabledTxt = false;
//            // disabledDdl = false;
//            int lengthCSIDList = get_csCount();
//
//            String[] CSIDListDuplicate = new String[lengthCSIDList];
//            int tmp = 0;
//
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//                PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
//                st.setInt(1, commYear);
//                st.setInt(2, year);
//                ResultSet rs = st.executeQuery();
//
//                while (rs.next()) {
//                    CSIDListDuplicate[tmp] = rs.getString("CSLevelID");
//                    tmp++;
//                }
//
//                st.close();
//                con.close();
//
//            } catch (Exception ex) {
//                System.out.println("Error: " + ex);
//            }
//
//            Arrays.sort(CSIDListDuplicate);//sorting array  
//            int length = CSIDListDuplicate.length;
//            length = removeDuplicateElementsString(CSIDListDuplicate, length);
//
//            String[] CSNameList = new String[length];
//            int tmp1 = 0;
//
//            //printing array elements  
//            for (int i = 0; i < length; i++) {
//                try {
//                    Class.forName("com.mysql.cj.jdbc.Driver");
//                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//                    PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
//                    st.setString(1, CSIDListDuplicate[i]);
//                    ResultSet rs = st.executeQuery();
//
//                    while (rs.next()) {
//                        CSNameList[tmp1] = rs.getString("CSLevelName");
//                        tmp1++;
//                    }
//
//                    st.close();
//                    con.close();
//
//                } catch (Exception ex) {
//                    System.out.println("Error: " + ex);
//                }
//            }
//
//            for (int i = 0; i < length; i++) {
//                CSLevel_list.add(CSNameList[i]);
//            }
//        }

        return CSLevel_list;
    }

    //auto generate year of study cs map ID
    public String autoGenerateTeacherCSMapID() {

        int count = 0;
        String tcID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
            st.setString(1, newTeacherCSLevel);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public String matchTeacherID1() {

        String thID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public String matchTeacherID() {

        String thID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teacher WHERE teacherName = ?");
            st.setString(1, newTeacherName);
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

//    public int matchStudNum(String scID, String thID) {
//        int studNum = 0;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            PreparedStatement st = con.prepareStatement("SELECT enrolStudNum FROM teachercsmap WHERE schoolCSMapID = ? AND teacherID = ?");
//            st.setString(1, scID);
//            st.setString(2, thID);
//            ResultSet rs = st.executeQuery();
//
//            while (rs.next()) {
//                studNum = rs.getInt("enrolStudNum");
//            }
//
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
//
//        return studNum;
//    }
    public int matchNumSampleAss(String scID, String newTeacherID) {

        int num = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

//        int studEnrol = matchStudNum(scID, newTeacherID);
        if (newTeacherStudNum < num) {
            num = newTeacherStudNum;
        }
//         else {
//            num = numSampleAssConstant;
//        }

        return num;

    }

    public void addSchoolCSMap() {
        String tcID = "", scID = "", thID = "";
        int numSampleAss = 0;

        tcID = autoGenerateTeacherCSMapID();
        scID = matchSchoolCSMapID();
        thID = matchTeacherID();
        numSampleAss = matchNumSampleAss(scID, thID);

//        updateTeacher(thID);
        //   tmp = "tcID : " + tcID + " scID : " + scID + " thID : " + thID + " numSampleAss : " + numSampleAss;
        //insert teachercsmap
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
    public void updateTeacher() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE teacher SET teacherName = ?, teacherStatus = ? WHERE teacherID = ?");

            statement.setString(1, newTeacherName);
            statement.setString(2, newTeacherStatus);
            statement.setString(3, newTeacherID);
            statement.executeUpdate();

            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    //update ttlenrolstud in schoolcsmap table
    public void updateTtlEnrolStud(String scID) {

        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;

        int originalStud = 0, latestStud = 0;

        //get the original ttlenrolstud num first
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        latestStud = originalStud + newTeacherStudNum;

//              
        //then update latest ttlstud inside schoolcsmap table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE schoolcsmap SET ttlEnrolStud = ? WHERE schoolCSMapID = ?");

            statement.setInt(1, latestStud);
            statement.setString(2, scID);
            statement.executeUpdate();

            statement.close();
            con.close();

            verifyCounter = 1;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

//        switch (verifyCounter) {
//            case 0:
//                context.addMessage(null, new FacesMessage("Total Enrolment student for school " + school + " updated not successful!"));
//                break;
//            case 1:
//                context.addMessage(null, new FacesMessage("Total Enrolment student for school " + school + " updated successful!"));
////                disabledTxt = true;
////                disabledStatus = true;
//                break;
//        }
        // temp = "lateststud: " + latestStud + " studNum: " + studNum + " originalStud: " + originalStud + " scID: " + scID;
    }

    //auto generate teacher ID
    public String autoGenerateTeacherID() {

        int count = 0;
        String thID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        newTeacherIDName = "TH1 - Teoh Wei Ran";

        // newTeacherID = "TH21";
//        newTeacherStatus = "Available";
        newTeacherYear = year;
        newTeacherSchoolState = state;
        newTeacherSchoolName = school;
//        newTeacherCSLevel = cslevel;

        newTeacherID = null;
        newTeacherName = null;
        disabledButton = true;
        disabledNewTeacher = false;
        disabledNewTeacherID = true;
        disabledNewTeacherName = true;
        disabledNewTeacherIDName = false;
        disabledTxt = true;
        disabledDdl = true;

        get_newTeacherID();
        get_newTeacherIDName();
    }

    public void addNewTeacher() {

        newCount = 2;

//        newTeacherID = autoGenerateTeacherID();
        newTeacherStatus = "Available";
        newTeacherYear = year;
        newTeacherSchoolState = state;
        newTeacherSchoolName = school;
//        newTeacherCSLevel = cslevel;
        newTeacherName = null;
        newTeacherIDName = "---";

        disabledButton = true;
        disabledNewTeacher = false;
        disabledNewTeacherID = true;
        disabledNewTeacherName = false;
        disabledNewTeacherIDName = true;
        disabledTxt = true;
        disabledDdl = true;

        get_newTeacherID();
    }

    public void addTeacher() {

        FacesContext context = FacesContext.getCurrentInstance();

        int verifyCounter = 0;

        // newCount = 0;
        //   newTeacherName = "newTeacherID: " + newTeacherID + " newTeacherName: " + ttt + " newTeacherStatus: " + newTeacherStatus + " newTeacherYear: " + newTeacherYear;
//        newTeacherStudNum = 65412;
//        newTeacherNameID;
        switch (newCount) {
            case 1: //select
            case 3: //select

                //split method
                String[] parts = newTeacherIDName.split(" - ");
                newTeacherID = parts[0];
                newTeacherName = parts[1];

                if (newTeacherName.isEmpty() || newTeacherStudNum == 0) {
                    context.addMessage(null, new FacesMessage("Please fill in whole form!"));
                } else {
                    updateTeacher();
                    addTeacherCSMap();
                }
                break;
            case 2: //add

                if (newTeacherName.isEmpty() || newTeacherStudNum == 0) {
                    context.addMessage(null, new FacesMessage("Please fill in whole form!"));
                } else {
                    newTeacherID = autoGenerateTeacherID();
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                        PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO teacher (teacherID, teacherName, teacherStatus, year) VALUES (?, ?, ?, ?)");

                        statement.setString(1, newTeacherID);
                        statement.setString(2, newTeacherName);
                        statement.setString(3, newTeacherStatus);
                        statement.setInt(4, newTeacherYear);
                        statement.executeUpdate();

                        verifyCounter = 1;
                        statement.close();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println("Error: " + ex);
                    }

                    switch (verifyCounter) {
                        case 0:
                            break;
                        case 1:
//                            context.addMessage(null, new FacesMessage("Add New Teacher name " + newTeacherName + " for school " + newTeacherSchoolName + " successful!"));
                            addTeacherCSMap();
                    }
                    break;
                }

        }

    }

    public void addTeacherCSMap() {

        FacesContext context = FacesContext.getCurrentInstance();

        int verifyCounter = 0;

        String tcID = "", scID = "", thID = "";
        int numSampleAss = 0;

        tcID = autoGenerateTeacherCSMapID();
        scID = matchSchoolCSMapID();
        //   thID = matchTeacherID();
        numSampleAss = matchNumSampleAss(scID, newTeacherID);

        //   tmp = "tcID : " + tcID + " scID : " + scID + " thID : " + thID + " numSampleAss : " + numSampleAss;
        //insert teachercsmap
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO teachercsmap (teacherCSMapID, enrolStudNum, numSampleAss, schoolCSMapID, teacherID) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, tcID);
            statement.setInt(2, newTeacherStudNum);
            statement.setInt(3, numSampleAss);
            statement.setString(4, scID);
            statement.setString(5, newTeacherID);
            statement.executeUpdate();

            statement.close();
            con.close();

            updateTtlEnrolStud(scID);

            verifyCounter = 1;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        switch (verifyCounter) {
            case 0:
                context.addMessage(null, new FacesMessage("Add New Teacher CS Map for school " + newTeacherSchoolName + " not successful!"));
                break;
            case 1:
                context.addMessage(null, new FacesMessage("Add New Teacher CS Map for school " + newTeacherSchoolName + " successful!"));

                disabledButton = false;
                disabledNewTeacher = true;
                disabledTxt = false;
                disabledDdl = false;
                disabledNewTeacherID = true;
                disabledNewTeacherName = true;
                disabledNewTeacherIDName = true;

                newTeacherID = null;
                newTeacherName = null;
                newTeacherIDName = null;
                newTeacherStatus = null;
                newTeacherCSLevel = null;
                newTeacherSchoolState = null;
                newTeacherSchoolName = null;
                newTeacherYear = 0;
                newTeacherStudNum = 0;
                newCount = 0;
                break;
        }
    }

    //cancel insert newTeacher
    public void cancel() {
        counterReset = 1;

        reset();
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();
        return "MaintainTeacher";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

        switch (counterReset) {
            case 0:
                context.addMessage(null, new FacesMessage("Reset successful!"));
                break;
        }

        disabledStatus = true;
        temp = null;

        //set default value
        state = "Pulau Pinang";
        school = "SJK Air Itam";
        schoolNameDefault = "SJK Air Itam";
        year = 2018;
        commYear = 0;
        teacher = "Teoh Wei Ran";
        studNum = 0;
        cslevel = "CS Level 1";
        status = "Available";

        newTeacherID = null;
        newTeacherName = null;
        newTeacherIDName = null;
        newTeacherStatus = null;
        newTeacherCSLevel = null;
        newTeacherSchoolState = null;
        newTeacherSchoolName = null;
        newTeacherYear = 0;
        newTeacherStudNum = 0;
        newCount = 0;

        counterReset = 0;

        //set default disabled
        disabledTxt = true;
        disabledDdl = true;
        disabledButton = false;
        disabledNewTeacher = true;
        disabledNewTeacherID = true;
        disabledNewTeacherName = true;
        disabledNewTeacherIDName = true;

        counterDataTable = 0;

        MaintainSchoolMenu.setGlobalCounter(0);
    }

    //reset newTeacher
    public void newReset() {

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Reset successful!"));

        if (newTeacherIDName.equals("---")) { //add new teacher
            newTeacherName = null;
            newTeacherStudNum = 0;
            newTeacherStatus = "Available";
            newTeacherCSLevel = "CS Level 1";
        } else { //select teacher
            newTeacherIDName = "TH1 - Teoh Wei Ran";
            newTeacherStudNum = 0;
            newTeacherCSLevel = "CS Level 1";
            teacherIDNameChanged();
            
//            newTeacherStatus = "Available";
//            get_newTeacherIDName();
        }

    }

    public ArrayList getCslevelList() {

        if (counterDataTable == 1) {
            //1. add button beside teacher, then use teacherID to find db teachercsmap
            //2. if got data, then declare array get schoolcsmap
            //3. go to schoolcsmap find csID, and go cs table find csName 
            //4. if not, then same as below
            //extra: if commercial year for a school is 2018. then go add new teacher, cs level same as 4.
            String thID = matchTeacherID1(); //1. find teacher id based on teacher name
            int scListLength = countSchoolCSMapID(thID); //2. declare school cs map id array length
            String[] scList = new String[scListLength]; //3. declare school cs map id array
            scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id

            int year1 = 0, tmpCount = 0;
            Boolean verify = false;

            for (int i = 0; i < scList.length; i++) { //5. find year based on school cs map id, if matched, means got data inside
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

            int[] studNumList = new int[csListLength];

            //   scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id
            if ((verify && year != 2018) || (verify && year == 2018)) {

                status = matchStatus(thID);
                disabledDdl = false;
                int tmp1 = 0, tmp2 = 0;

                //1. use school cs map id array and year go find cs id
                for (int i = 0; i < scList.length; i++) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                        PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolCSMapID = ? AND year = ?");
                        st.setString(1, scList[i]);
                        st.setInt(2, year);
                        ResultSet rs = st.executeQuery();

                        while (rs.next()) {
                            csID = rs.getString("CSLevelID");
                            csName = matchCSLevelName(csID);
                            csList[tmp1] = csName;
                            tmp1++;
//                        CSLevel_list.add(csName);
//                            if (csList[tmp1].equals(csName)) {
                            studNumList[tmp2] = matchStudNum(scList[i], thID);
                            tmp2++;
                            disabledTxt = true;
//                            }

                        }

                        st.close();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println("Error: " + ex);
                    }

                }

//                String[] sortArr = new String[csList.length];
//
//                for (int i = 0; i < csList.length; i++) {
//                    sortArr[i] = csList[i] + " - " + studNumList[i];
//                }
//
//                Arrays.sort(sortArr);
//
//                for (int i = 0; i < csList.length; i++) {
//                    String[] parts = sortArr[i].split(" - ");
//                    csList[i] = parts[0];
//                    studNumList[i] = parts[1]; //cannot work coz int cannot convert to string
//                }
                cslevelList = new ArrayList();

                for (int i = 0; i < csList.length; i++) {
                    studNumObj = new MaintainTeacher();
                    studNumObj.setCslevelNew(csList[i]);
                    studNumObj.setStudNumNew(studNumList[i]);
                    cslevelList.add(studNumObj);
                }
            } else {
                cslevelList = new ArrayList();

                studNumObj = new MaintainTeacher();
                studNumObj.setCslevelNew("CS Level");
                studNumObj.setStudNumNew(0);
                cslevelList.add(studNumObj);
            }
        } else {
            cslevelList = new ArrayList();

            studNumObj = new MaintainTeacher();
            studNumObj.setCslevelNew("CS Level");
            studNumObj.setStudNumNew(0);
            cslevelList.add(studNumObj);
        }

        return cslevelList;
    }

    //valuechangelistener purpose
    public void stateChanged() {
        get_school();

        school = schoolNameDefault;

        controlDisabledStatus();
        get_year();
        calculateCommYear();
        get_teacher();

        teacher = teacherNameDefault;

        String thID = "";
        thID = matchTeacherID1();

        status = matchStatus(thID);
    }

    public void schoolChanged() {
        controlDisabledStatus();
        get_year();

        calculateCommYear();
        get_teacher();

        teacher = teacherNameDefault;

        String thID = "";
        thID = matchTeacherID1();

        status = matchStatus(thID);
    }

    public void yearChanged() {
        calculateCommYear();
        get_teacher();

        teacher = teacherNameDefault;

        String thID = "";
        thID = matchTeacherID1();

        status = matchStatus(thID);
    }

    public void teacherIDNameChanged() {
        showTeacherName();
    }

    public void teacherChanged() {

        String thID = "";
        thID = matchTeacherID1();

        status = matchStatus(thID);
    }

}
