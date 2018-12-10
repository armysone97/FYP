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
import java.util.List;
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

public class GenerateReport {
    
    private Connection con;
    private String reportType;
    private int year;
    private String evaluator;
    private String staffID;
    private int evaWorkloadCount;
    
    private ArrayList evaluatorWorkloadList = null;
    private GenerateReport genRepobj1 = null;
    private int number_DT = 0;
    private String school_DT = "";
    private String csLevel_DT = "";
    private String teacher_DT = "";
    private String assessment_DT = "";
    
    private int counterReset;
    
    private List<String> report_list = new ArrayList<>();
    private List<String> year_list = new ArrayList<>();
    private List<String> evaluator_list = new ArrayList<>();
    
    public GenerateReport() {
        this.counterReset = 0;
    }

    public String getReportType() {
        return reportType;
    }

    public int getYear() {
        return year;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public int getNumber_DT() {
        return number_DT;
    }

    public void setNumber_DT(int number_DT) {
        this.number_DT = number_DT;
    }

    public String getSchool_DT() {
        return school_DT;
    }

    public void setSchool_DT(String school_DT) {
        this.school_DT = school_DT;
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

    public String getAssessment_DT() {
        return assessment_DT;
    }

    public void setAssessment_DT(String assessment_DT) {
        this.assessment_DT = assessment_DT;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setReport_list(List<String> report_list) {
        this.report_list = report_list;
    }

    public void setYear_list(List<String> year_list) {
        this.year_list = year_list;
    }

    public void setEvaluator_list(List<String> evaluator_list) {
        this.evaluator_list = evaluator_list;
    }

    public void setEvaluatorWorkloadList(ArrayList evaluatorWorkloadList) {
        this.evaluatorWorkloadList = evaluatorWorkloadList;
    }
    
    //display report type
    public List<String> get_ReportList() {

        report_list.clear();
        
        report_list.add("School Workload Allocation Report");
        report_list.add("Evaluator Workload Allocation Report");
        report_list.add("Workload Claim Report");
        report_list.add("Mileage Claim Report");

        return report_list;
    }
    
    //display year list
    public List<String> get_YearList() {
        year_list.clear();
        
        year_list.add("2016");
        year_list.add("2017");
        year_list.add("2018");
        
        return year_list;
    }
    
    //display evaluator list
    public List<String> get_EvaluatorList() {
        evaluator_list.clear();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM evaluatorpersonaldetails WHERE status = 'Available'");

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

    public void callEvaluatorWorkload() {
        getEvaluatorWorkloadList();
    }
    
    //retrieve evaluator workload
    public ArrayList getEvaluatorWorkloadList() {
        //retrieve staff ID
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
        
        //count amt workload 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM workloadallocation WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                evaWorkloadCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        String[] teacherCSMapList = new String[evaWorkloadCount];
        String[] schoolCSMapList = new String[evaWorkloadCount];
        String[] schoolIDList = new String[evaWorkloadCount];
        String[] csLevelIDList = new String[evaWorkloadCount];
        String[] teacherIDList = new String[evaWorkloadCount];
        String[] schoolNameList = new String[evaWorkloadCount];
        String[] teacherNameList = new String[evaWorkloadCount];
        String[] assessmentList = new String[evaWorkloadCount];
        
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
        
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 0; i < evaWorkloadCount; i++) {
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
        
        int tmp3 = 0;
        int tmp4 = 0;
        for (int i = 0; i < evaWorkloadCount; i++) {
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
        
        int tmp5 = 0;
        for (int i = 0; i < evaWorkloadCount; i++) {
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
        
        int tmp6 = 0;
        for (int i = 0; i < evaWorkloadCount; i++) {
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
        
        evaluatorWorkloadList = new ArrayList();

        for (int i = 0; i < evaWorkloadCount; i++) {
            genRepobj1 = new GenerateReport();
            genRepobj1.setNumber_DT(i+1);
            genRepobj1.setSchool_DT(schoolNameList[i]);
            genRepobj1.setCsLevel_DT(csLevelIDList[i]);
            genRepobj1.setTeacher_DT(teacherNameList[i]);
            genRepobj1.setAssessment_DT(assessmentList[i]);
            evaluatorWorkloadList.add(genRepobj1);
        }
        
        return evaluatorWorkloadList;
    }
    
    //navigation bar purpose
    public String goToNextPage() {
        
        counterReset = 1;

        reset();
               
        return "GenerateReport";
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
        //year = 2018;
//        reportType = "";
        
        counterReset = 0;
    }
    
    
}
