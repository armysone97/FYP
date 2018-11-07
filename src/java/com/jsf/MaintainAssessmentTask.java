/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.MaintainTeacher.removeDuplicateElements;
import static com.jsf.MaintainTeacher.removeDuplicateElementsString;
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

    private String status, state, school, teacher, cslevel;
    private int studEnrol, numSampleAss, year;

    private List<String> school_list = new ArrayList<>();
    private List<String> teacher_list = new ArrayList<>();
    private List<String> state_list = new ArrayList<>();
    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private Boolean disabledtxt, disabledNumSampleAss;

    private int counterReset; //growl purpose

    public MaintainAssessmentTask() {
        this.state = "Pulau Pinang";
        this.school = "SMJK Heng Yee";
        this.year = 2018;
        this.teacher = "Teoh Kok Xing";
        this.studEnrol = 0;
        this.numSampleAss = 0;
        this.disabledtxt = true;
        this.disabledNumSampleAss = true;
        this.counterReset = 0;
    }

    public Boolean getDisabledNumSampleAss() {
        return disabledNumSampleAss;
    }

    public void setDisabledNumSampleAss(Boolean disabledNumSampleAss) {
        this.disabledNumSampleAss = disabledNumSampleAss;
    }

    public Boolean getDisabledtxt() {
        return disabledtxt;
    }

    public void setDisabledtxt(Boolean disabledtxt) {
        this.disabledtxt = disabledtxt;
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

    public String getCslevel() {
        return cslevel;
    }

    public void setCslevel(String cslevel) {
        this.cslevel = cslevel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStudEnrol() {
        return studEnrol;
    }

    public void setStudEnrol(int studEnrol) {
        this.studEnrol = studEnrol;
    }

    public int getNumSampleAss() {
        return numSampleAss;
    }

    public void setNumSampleAss(int numSampleAss) {
        this.numSampleAss = numSampleAss;
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

    //get school list and put inside ddl based on the state that is selected by user in ddl
    public List<String> get_school() {

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

        return teacher_list;
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

    //get year from db 
    public List<Integer> get_year() {

        year_list.clear();

        //  int statusOfYearLength = 0;
        String schoolID = matchSchoolID();

        int lengthYearList = get_yearCount(schoolID);

        int[] yearListDuplicate = new int[lengthYearList];

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        int tmp = 0;

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

        Arrays.sort(yearListDuplicate);//sorting array  
        int length = yearListDuplicate.length;
        length = removeDuplicateElements(yearListDuplicate, length);

        //printing array elements  
        for (int i = 0; i < length; i++) {
            year_list.add(yearListDuplicate[i]);
        }

        return year_list;
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

    public int matchAssNum(String thID, String scID) {
        int assNum = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT numSampleAss FROM teachercsmap WHERE schoolCSMapID = ? AND teacherID = ?");
            st.setString(1, scID);
            st.setString(2, thID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                assNum = rs.getInt("numSampleAss");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return assNum;
    }

    public int matchStudNum(String thID, String scID) {
        int studNum = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        // status = "xxx";
        //1. add button beside teacher, then use teacherID to find db teachercsmap
        //2. if got data, then declare array get schoolcsmap
        //3. go to schoolcsmap find csID, and go cs table find csName 
        String thID = "";

        thID = matchTeacherID(); //1. find teacher id based on teacher name
        int scListLength = countSchoolCSMapID(thID); //2. declare school cs map id array length
        String[] scList = new String[scListLength]; //3. declare school cs map id array
        scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id

        //   status = thID + " x";
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
        String[] csList = new String[csListLength]; //3. declare cs id array
        String csID = "", csName = "";
        int tmp1 = 0;

        //   scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id
        //    if (verify && year != 2018) {
        status = matchStatus(thID);
        //  disabledTxt = true;
        //  disabledDdl = true;
        //1. use school cs map id array and year go find cs id
        for (int i = 0; i < scList.length; i++) {
            //  status = "xxy" + i;
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

                    csList[tmp1] = csName;
                    tmp1++;

                    //   CSLevel_list.add(csName);
                    if (cslevel.equals(csName)) {
                        //     status = "thID : " + thID + " scList[i] : " + scList[i];
                        //studEnrol = matchStudNum(thID, scList[i]);
                        //numSampleAss = matchAssNum(thID, scList[i]);
                    }
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

        }

        Arrays.sort(csList);//sorting array  

        for (int i = 0; i < csList.length; i++) {
            CSLevel_list.add(csList[i]);
        }

        int numSampleAssConstant = 0;

        String scID = matchSchoolCSMapID();
        studEnrol = matchStudNum(thID, scID);
        numSampleAssConstant = matchAssNum(thID, scID);

        if (studEnrol < numSampleAssConstant) {
            numSampleAss = studEnrol;
        } else {
            numSampleAss = numSampleAssConstant;
        }

        return CSLevel_list;
    }

    public void disabledOrEnable() {
        if (year == 2018) {
            disabledNumSampleAss = false;
        } else {
            disabledNumSampleAss = true;
        }
    }

    //update numSampleAss in teachercsmap table
    public void updateNumSampleAss() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;

        String thID = matchTeacherID();
        String scID = matchSchoolCSMapID();

        //then update latest ttlstud inside schoolcsmap table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE teachercsmap SET numSampleAss = ? WHERE teacherID = ? AND schoolCSMapID = ?");

            statement.setInt(1, numSampleAss);
            statement.setString(2, thID);
            statement.setString(3, scID);
            statement.executeUpdate();

            statement.close();
            con.close();
            
            verifyCounter = 1;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        
        switch (verifyCounter) {
            case 0:
                context.addMessage(null, new FacesMessage("Update Number Samples Assessment for teacher " + teacher + " in year " + year + " not successful!"));
                break;
            case 1:
                context.addMessage(null, new FacesMessage("Update Number Samples Assessment for teacher " + teacher + " in year " + year + " successful!"));
                 disabledNumSampleAss = true;
                break;
        }
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();
        return "MaintainAssessmentTask";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

        switch (counterReset) {
            case 0:
                context.addMessage(null, new FacesMessage("Reset successful!"));
                break;
        }

        //set default value
        state = "Pulau Pinang";
        school = "SMJK Heng Yee";
        year = 2018;
        teacher = "Teoh Kok Xing";
        studEnrol = 0;
        numSampleAss = 0;
        status = "Available";
        cslevel = "CS Level 1";

        counterReset = 0;

        //set default disabled
        disabledtxt = true;
        disabledNumSampleAss = true;

    }

    public void abcClick() {

        //  school = "SMJK Heng Yee";
        //  status = "yy1";
//        state = "Kuala Lumpur";
//
//      //  setSchool("SMJK Heng Yee");
//        setStatus("yy");
//        setState("Kedah");
        //    changeDDLDisabled();
        String thID = "";

        thID = matchTeacherID(); //1. find teacher id based on teacher name
        //  int scListLength = countSchoolCSMapID(thID); //2. declare school cs map id array length
        //   String[] scList = new String[scListLength]; //3. declare school cs map id array
        //   scList = matchSchoolCSMapID1(thID, scListLength); //4. find school cs map id based on teacher id

        // status = thID + " x";
        //   String csID = matchCSLevelID();
        //   status = thID + " y : " + scID + " : xhhh : " + csID;
    }

    public void hahax() {
        school = "SMK Heng Yee";
        status = "yy3";
    }

    public void main(String args[]) {
        //tryclick();
    }

}
