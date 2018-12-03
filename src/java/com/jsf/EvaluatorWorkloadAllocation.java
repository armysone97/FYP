/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.io.PrintWriter;
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
    private String staffID;
    private String school;
    private String csLevel;
    private String teacher;
    private int workloadLimit;
    private int totalStudent;
    private String assType;
    private int workloadMin;
    private double workloadAssigned;
    private int year;
    private String result;
    private String schoolID;
    private String totalWorkloadAssigned;
    private int workloadCount;
    private String waID;

    private List<String> evaluator_list = new ArrayList<>();
    private List<String> school_list = new ArrayList<>();
    private List<String> cslevel_list = new ArrayList<>();
    private List<String> teacher_list = new ArrayList<>();
    private List<String> assessment_list = new ArrayList<>();

    private int counterReset; //growl purpose
    
    //for add workload
    private String school_ID;
    private String schoolCSMap_ID;
    private String teacher_ID;
    private String teacherCSMap_ID;
    private String staff_ID;
    private double verifyWorkloadLimit;
    
    //datatable
    private ArrayList workloadList = null;
    private EvaluatorWorkloadAllocation evaWAobj1 = null;
    private int workload_Count;
    private String evaluator_DT = "";
    private String assessment_DT = "";
    private String staffID_DT;

    public EvaluatorWorkloadAllocation(String evaluator, String school) {
        super();
        this.evaluator = evaluator;
        this.school = school;
    }

    public EvaluatorWorkloadAllocation() {
        super();
        this.counterReset = 0;
        this.year = 2018;
        this.workloadAssigned = 0;
        this.totalWorkloadAssigned = "0.0";
        this.result = "0";
        this.verifyWorkloadLimit = 0;
    }

    public String getTotalWorkloadAssigned() {
        return totalWorkloadAssigned;
    }

    public void setTotalWorkloadAssigned(String totalWorkloadAssigned) {
        this.totalWorkloadAssigned = totalWorkloadAssigned;
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

    public String getEvaluator_DT() {
        return evaluator_DT;
    }

    public void setEvaluator_DT(String evaluator_DT) {
        this.evaluator_DT = evaluator_DT;
    }

    public String getAssessment_DT() {
        return assessment_DT;
    }

    public void setAssessment_DT(String assessment_DT) {
        this.assessment_DT = assessment_DT;
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

    public void setWorkloadList(ArrayList workloadList) {
        this.workloadList = workloadList;
    }

    public double getVerifyWorkloadLimit() {
        return verifyWorkloadLimit;
    }

    public void setVerifyWorkloadLimit(double verifyWorkloadLimit) {
        this.verifyWorkloadLimit = verifyWorkloadLimit;
    }

    public List<EvaluatorWorkloadAllocation> findAll() {
        List<EvaluatorWorkloadAllocation> listWorkload = new ArrayList<EvaluatorWorkloadAllocation>();

        return listWorkload;
    }
    
    public void callStatus() {
        getWorkloadList();
    }
    
    //display data
    public ArrayList getWorkloadList() {
        
        //retrieve staff ID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT staffID FROM evaluatorpersonaldetails WHERE name = ?");
            st.setString(1, evaluator);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                staffID = rs.getString("staffID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM workloadallocation WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                workload_Count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        String[] evaluatorList = new String[workload_Count];
        String[] assessmentList = new String[workload_Count];
        int tmp = 0;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherCSMapID, assessment FROM workloadallocation WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                evaluatorList[tmp] = rs.getString("teacherCSMapID");
                assessmentList[tmp] = rs.getString("assessment");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        workloadList = new ArrayList();

        for (int i = 0; i < workload_Count; i++) {
            evaWAobj1 = new EvaluatorWorkloadAllocation();
            evaWAobj1.setEvaluator_DT(evaluatorList[i]);
            evaWAobj1.setAssessment_DT(assessmentList[i]);
            workloadList.add(evaWAobj1);
        }
        
        return workloadList;
    }

    //retrieve total number of student
    public void retrieveTotalStudent() {

        String schoolIDFromDB = "", teacherIDFromDB = "", CSLevelIDFromDB = "", schoolCSMapIDFromDB = "";

        //retrieve schoolID based on schoolName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
//            st.setString(1, csLevel);
//            ResultSet rs = st.executeQuery();
//
//            while (rs.next()) {
//                CSLevelIDFromDB = rs.getString("CSLevelID");
//            }
//
//            rs.close();
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
        //retrieve teacherID based on teacherName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ? AND CSLevelID = ?");
            st.setString(1, schoolIDFromDB);
            st.setString(2, csLevel);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        
        //retrieve total workload assign
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT SUM(workloadAssigned) FROM workloadallocation WHERE staffID = ?");
            st.setString(1, staffIDFromDB);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setTotalWorkloadAssigned(String.format("%.2f", rs.getDouble("SUM(workloadAssigned)")));
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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


    public List<String> get_CSLevelList() {

//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("xxxx"));
        cslevel_list.clear();

        // String scID = "";
        // verifySchoolID();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolName = ?");
            st.setString(1, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolID = rs.getString("schoolID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolID = ? AND year = ?");
            st.setString(1, schoolID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                cslevel_list.add(rs.getString("CSLevelID"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return cslevel_list;
    }

    //use in function get_teacher(), to count the schoolCSMap id in database
    public int retrieveSchoolCSMapIDCount(String schoolIDFromDB) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ? AND CSLevelID = ? AND year = ?");
            st.setString(1, schoolIDFromDB);
            st.setString(2, csLevel);
            st.setInt(3, year);
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

    public int retrieveTeacherIDCount(String[] schoolCSMapIDListDuplicate) {

        int count = 0;

        for (int i = 0; i < schoolCSMapIDListDuplicate.length; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolCSMapIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    count++;
                }

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        return count;
    }

    public List<String> get_TeacherList() {

        teacher_list.clear();

        //1. get schoolID and csLevelID from user that is selected in ddl
        //2. get schoolCSMapID based on 1. (in array)
        //3. get teacherID based on 2. (in array)
        //4. get teacherName based on 3. (in array)
        String schoolIDFromDB = "", teacherIDFromDB = "", CSLevelIDFromDB = "", schoolCSMapIDFromDB = "";

        //retrieve schoolID based on schoolName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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


        int schoolCSMapListCount = retrieveSchoolCSMapIDCount(schoolIDFromDB);
        String[] schoolCSMapIDListDuplicate = new String[schoolCSMapListCount];

        int tmp = 0;

        //retrieve schoolCSMaplID based on schoolID and CSLevelID which retrieved on above
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ? AND CSLevelID = ? AND year = ?");

            st.setString(1, schoolIDFromDB);
            st.setString(2, csLevel);
            st.setInt(3, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMapIDListDuplicate[tmp] = rs.getString("schoolCSMapID");
                tmp++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        int teacherIDListCount = retrieveTeacherIDCount(schoolCSMapIDListDuplicate);
        String[] teacherIDListDuplicate = new String[teacherIDListCount];

        int tmp1 = 0;

        //retrieve teacherCSMapID based on schoolCSMaplID and teacherID which retrieved on above
        //and get the total number of student
        for (int i = 0; i < schoolCSMapIDListDuplicate.length; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolCSMapIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
//                    totalStudent = rs.getInt("numSampleAss");
                    teacherIDListDuplicate[tmp1] = rs.getString("teacherID");
                    tmp1++;
                }

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        Arrays.sort(teacherIDListDuplicate);

        for (int i = 0; i < teacherIDListDuplicate.length; i++) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
                st.setString(1, teacherIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
//                    totalStudent = rs.getInt("numSampleAss");
//                    teacherIDListDuplicate[tmp1] = rs.getString("teacherID");
//                    tmp1++;
                    teacher_list.add(rs.getString("teacherName"));
                }

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

        }

        return teacher_list;
    }

    public List<String> get_AssessmentList() {

        assessment_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
    
    //check validation
    public void validationCheck(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(evaluator == null || school == null || csLevel == null || teacher == null || assType == null){
            context.addMessage(null, new FacesMessage("All field are required to fill in!"));
            reset();
        }
        else{
            addWorkload();
        }
    }
    
    public void addWorkload(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        //count workload index
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM workloadallocation");

            while (rs.next()) {
                workloadCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        workloadCount = workloadCount + 1;
        waID = "WA" + Integer.toString(workloadCount);
        
        //retrieve staffID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT staffID FROM evaluatorpersonaldetails WHERE name = ?");
            st.setString(1, evaluator);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                staff_ID = rs.getString("staffID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //retrieve schoolcsmapID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolName = ?");
            st.setString(1, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                school_ID = rs.getString("schoolID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE schoolID = ? AND CSLevelID = ? AND year = ?");
            st.setString(1, school_ID);
            st.setString(2, csLevel);
            st.setInt(3, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolCSMap_ID = rs.getString("schoolCSMapID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //retrieve teacherID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherID FROM teacher WHERE teacherName = ?");
            st.setString(1, teacher);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teacher_ID = rs.getString("teacherID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //retrieve teachercsmapID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherCSMapID FROM teachercsmap WHERE schoolCSMapID = ? AND teacherID = ?");
            st.setString(1, schoolCSMap_ID);
            st.setString(2, teacher_ID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teacherCSMap_ID = rs.getString("teacherCSMapID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        checkDuplicateRecord();
    }
    
    //check duplicate record
    public void checkDuplicateRecord(){
        FacesContext context = FacesContext.getCurrentInstance();
        boolean check = false;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT teacherCSMapID, staffID, assessment FROM workloadallocation");

            while (rs.next()) {
                String teacherCSMapIDDB = rs.getString("teacherCSMapID");
                String staffIDDB = rs.getString("staffID");
                String assessment_Type = rs.getString("assessment");

                if (teacherCSMap_ID.equals(teacherCSMapIDDB) && staff_ID.equals(staffIDDB) && assType.equals(assessment_Type)) {
                    check = false;
                    break;
                }else{
                    check = true;
                }
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        if(check == true){
            saveWorkloadAllocation();
        }else{
            context.addMessage(null, new FacesMessage("Record already existed!"));
        }
    }
    
    public void saveWorkloadAllocation(){
        FacesContext context = FacesContext.getCurrentInstance();
        //insert workload allocation
        if(((Double.valueOf(result))+(Double.valueOf(totalWorkloadAssigned))) <= Double.valueOf(workloadLimit)){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO workloadallocation (WA_ID, workloadAssigned, assessment, teacherCSMapID, staffID) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, waID);
            statement.setDouble(2, Double.valueOf(result));
            statement.setString(3, assType);
            statement.setString(4, teacherCSMap_ID);
            statement.setString(5, staff_ID);

            statement.executeUpdate();
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        context.addMessage(null, new FacesMessage("Added successfully!"));

        reset();
        }
        else{
            context.addMessage(null, new FacesMessage("Workload limit exceed!"));
            reset();
        }
    }
    
    //display data table
    public void displayData(){
        
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();
        return "EvaluatorWorkloadAllocationApplication";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

//        switch (counterReset) {
//            case 0:
//                context.addMessage(null, new FacesMessage("Reset successful!"));
//                break;
//        }

        //set default value
        evaluator = null;
        school = null;
        csLevel = null;
        teacher = null;
        workloadLimit = 0;
        totalStudent = 0;
        assType = null;
        workloadMin = 0;
        workloadAssigned = 0;
        totalWorkloadAssigned = null;
        year = 2018;
        result = "0.0";

        counterReset = 0;
    }

    //valuechangelistener purpose
    public void evaluatorChanged(){
        get_EvaluatorList();
    }
    
    public void schoolChanged(){
        get_CSLevelList();
    }
    
    public void csLevelChanged(){
        get_TeacherList();
    }
    
    public void teacherChanged(){
        retrieveTotalStudent();
    }
    
    public void assessmentChanged(){
        retrieveNumSamAss();
    }
    
    public void main(String args[]) {

    }
}
