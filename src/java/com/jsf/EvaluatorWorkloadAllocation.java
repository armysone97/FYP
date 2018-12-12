/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
    private double ttlWorkloadAssign;
    private int workloadCount;
    private String waID;
    private double ttlWorkload_Assign;
    private String total;
    private int assID_count;

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
    private String school_DT = "";
    private String csLevel_DT = "";
    private String teacher_DT = "";
    private String assessment_DT = "";

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
        this.workloadList = null;
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

    public String getSchool_DT() {
        return school_DT;
    }

    public void setSchool_DT(String school_DT) {
        this.school_DT = school_DT;
    }

    public String getAssessment_DT() {
        return assessment_DT;
    }

    public void setAssessment_DT(String assessment_DT) {
        this.assessment_DT = assessment_DT;
    }

    public String getCsLevel_DT() {
        return csLevel_DT;
    }

    public void setCsLevel_DT(String csLevel_DT) {
        this.csLevel_DT = csLevel_DT;
    }

    public String getTeacher_DT() {
        return teacher_DT;
    }

    public void setTeacher_DT(String teacher_DT) {
        this.teacher_DT = teacher_DT;
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

    //display data table
    public ArrayList getWorkloadList() {

        //retrieve staff ID from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        //retrieve count of workload from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM workloadallocation WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
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

        String[] teacherCSMapList = new String[workload_Count];
        String[] schoolCSMapList = new String[workload_Count];
        String[] schoolIDList = new String[workload_Count];
        String[] csLevelIDList = new String[workload_Count];
        String[] teacherIDList = new String[workload_Count];
        String[] schoolNameList = new String[workload_Count];
        String[] teacherNameList = new String[workload_Count];
        String[] assessmentList = new String[workload_Count];

        //retrieve teachercsmapID and assessment from db
        int tmp = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherCSMapID, assessment FROM workloadallocation WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                teacherCSMapList[tmp] = rs.getString("teacherCSMapID");
                assessmentList[tmp] = rs.getString("assessment");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //retrieve teacherID and schoolcsmapID from db
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 0; i < workload_Count; i++) {
            String teacherIDTmp = "";
            String schoolCSMapIDTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherID, schoolCSMapID FROM teachercsmap WHERE teacherCSMapID = ?");
                st.setString(1, teacherCSMapList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacherIDTmp = rs.getString("teacherID");
                    teacherIDList[tmp1] = teacherIDTmp;
                    tmp1++;

                    schoolCSMapIDTmp = rs.getString("schoolCSMapID");
                    schoolCSMapList[tmp2] = schoolCSMapIDTmp;
                    tmp2++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        //retrieve schoolID and cslevelID from db
        int tmp3 = 0;
        int tmp4 = 0;
        for (int i = 0; i < workload_Count; i++) {
            String schoolIDTmp = "";
            String csLevelIDTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT schoolID, CSLevelID FROM schoolcsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolCSMapList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    schoolIDTmp = rs.getString("schoolID");
                    schoolIDList[tmp3] = schoolIDTmp;
                    tmp3++;

                    csLevelIDTmp = rs.getString("CSLevelID");
                    csLevelIDList[tmp4] = csLevelIDTmp;
                    tmp4++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        //retrieve schoolname from db
        int tmp5 = 0;
        for (int i = 0; i < workload_Count; i++) {
            String schoolNameTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT schoolName FROM school WHERE schoolID = ?");
                st.setString(1, schoolIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    schoolNameTmp = rs.getString("schoolName");
                    schoolNameList[tmp5] = schoolNameTmp;
                    tmp5++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        //retrieve teachername from db
        int tmp6 = 0;
        for (int i = 0; i < workload_Count; i++) {
            String teacherNameTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ?");
                st.setString(1, teacherIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacherNameTmp = rs.getString("teacherName");
                    teacherNameList[tmp6] = teacherNameTmp;
                    tmp6++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
        
        String[] sortArr = new String[workload_Count];
        
        for (int i = 0; i < workload_Count; i++) {
            sortArr[i] = schoolNameList[i] + " - " + csLevelIDList[i] + " - " + teacherNameList[i] + " - " + assessmentList[i];
        }

        Arrays.sort(sortArr);
        
         for (int i = 0; i < workload_Count; i++) {
            String[] parts = sortArr[i].split(" - ");
            schoolNameList[i] = parts[0];
            csLevelIDList[i] = parts[1];
            teacherNameList[i] = parts[2];
            assessmentList[i] = parts[3];
        }

        workloadList = new ArrayList();

        for (int i = 0; i < workload_Count; i++) {
            evaWAobj1 = new EvaluatorWorkloadAllocation();
            evaWAobj1.setSchool_DT(schoolNameList[i]);
            evaWAobj1.setCsLevel_DT(csLevelIDList[i]);
            evaWAobj1.setTeacher_DT(teacherNameList[i]);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        //retrieve teacherID based on teacherName that is selected by user in ddl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        //retrieve teacherCSMapID and get the total number of student
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //retrieve workload limit 
    public void retrieveWorkloadLimit() {

        String staffIDFromDB = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT workloadLimit FROM workloadlimit WHERE staffID = ? AND year = ?");
            st.setString(1, staffIDFromDB);
            st.setInt(2, year);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT SUM(workloadAssigned) FROM workloadallocation WHERE staffID = ? AND year = ?");
            st.setString(1, staffIDFromDB);
            st.setInt(2, year);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT schoolName FROM school WHERE schoolStatus = 'Active'");

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherName FROM teacher WHERE teacherID = ? AND teacherStatus = 'Available'");
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

        //count assID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM assessment WHERE year = ? AND CSLevelID = ?");
            st.setInt(1, year);
            st.setString(2, csLevel);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                assID_count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String[] assIDList = new String[assID_count];
        int tmp7 = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliant=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT assActivityID FROM assessment WHERE year = ? AND CSLevelID = ?");
            st.setInt(1, year);
            st.setString(2, csLevel);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                assIDList[tmp7] = rs.getString("assActivityID");
                tmp7++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        for (int i = 0; i < assID_count; i++) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliant=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT assActivityName FROM assessmentactivity WHERE assActivityID = ?");
                st.setString(1, assIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    assessment_list.add(rs.getString("assActivityName"));
                }

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

        }
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT assActivityName FROM assessmentactivity");
//
//            while (rs.next()) {
//                assessment_list.add(rs.getString("assActivityName"));
//            }
//
//            rs.close();
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }

        return assessment_list;
    }

    //check validation
    public void validationCheck() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (evaluator == null || school == null || csLevel == null || teacher == null || assType == null) {
            context.addMessage(null, new FacesMessage("All field are required to fill in!"));
            reset();
        } else {
            addWorkload();
        }
    }

    public void addWorkload() {
        FacesContext context = FacesContext.getCurrentInstance();

        //count workload index
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
    public void checkDuplicateRecord() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean check = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT teacherCSMapID, staffID, assessment FROM workloadallocation");

            while (rs.next()) {
                String teacherCSMapIDDB = rs.getString("teacherCSMapID");
                String assessment_Type = rs.getString("assessment");

                if (teacherCSMap_ID.equals(teacherCSMapIDDB) && assType.equals(assessment_Type)) {
                    check = false;
                    break;
                } else {
                    check = true;
                }
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        if (check == true) {
            saveWorkloadAllocation();
        } else {
            context.addMessage(null, new FacesMessage("Record already existed!"));
        }
    }

    public void saveWorkloadAllocation() {
        FacesContext context = FacesContext.getCurrentInstance();
        //insert workload allocation
        if (((Double.valueOf(result)) + (Double.valueOf(totalWorkloadAssigned))) <= Double.valueOf(workloadLimit)) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO workloadallocation (WA_ID, workloadAssigned, assessment, teacherCSMapID, staffID, specialID, year) VALUES (?, ?, ?, ?, ?, ?, ?)");

                statement.setString(1, waID);
                statement.setDouble(2, Double.valueOf(result));
                statement.setString(3, assType);
                statement.setString(4, teacherCSMap_ID);
                statement.setString(5, staff_ID);
                statement.setString(6, assType + " - " + school);
                statement.setInt(7, year);

                statement.executeUpdate();
                statement.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            //retrieve ttlWorkloadAssigned
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT ttlWorkloadAssigned FROM workloadlimit WHERE staffID = ? AND year = ?");
                st.setString(1, staff_ID);
                st.setInt(2, year);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    ttlWorkloadAssign = rs.getDouble("ttlWorkloadAssigned");
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            ttlWorkload_Assign = Double.valueOf(result) + ttlWorkloadAssign;

            total = String.format("%.2f", ttlWorkload_Assign);

            //update ttlWorkloadAssigned in db
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = (PreparedStatement) con.prepareStatement("UPDATE workloadlimit SET ttlWorkloadAssigned = ? WHERE staffID = ? AND year = ?");
                st.setString(1, total);
                st.setString(2, staffID);
                st.setInt(3, year);
                st.executeUpdate();

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            context.addMessage(null, new FacesMessage("Added successfully!"));
            reset();
        } else {
            context.addMessage(null, new FacesMessage("Workload limit exceed!"));
            reset();
        }
    }

    //display data table
    public void displayData() {

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
        workloadList = null;

        counterReset = 0;

        MaintainSchoolMenu.setGlobalCounter(0);
    }

    //valuechangelistener purpose
    public void evaluatorChanged() {
        get_EvaluatorList();
    }

    public void schoolChanged() {
        get_CSLevelList();
    }

    public void csLevelChanged() {
        get_TeacherList();
    }

    public void teacherChanged() {
        retrieveTotalStudent();
    }

    public void assessmentChanged() {
        retrieveNumSamAss();
    }

    public void main(String args[]) {

    }
}
