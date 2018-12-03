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
    private int workloadCount;
    private String reportTitle;
    private int counterReset;
    
    private List<String> report_list = new ArrayList<>();
    private List<String> year_list = new ArrayList<>();


    //datatable
    private ArrayList workloadList = null;
    private GenerateReport generateReportobj1 = null;
    private Integer number_DT = 0;
    private String school_DT = "";
    private String csLevel_DT = "";
    private String teacher_DT = "";
    private String assessment_DT = "";

    public GenerateReport() {
        this.counterReset = 0;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public Integer getNumber_DT() {
        return number_DT;
    }

    public void setNumber_DT(Integer number_DT) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setYear_list(List<String> year_list) {
        this.year_list = year_list;
    }

    public void setReport_list(List<String> report_list) {
        this.report_list = report_list;
    }
    
    //generate workload allocation report
    public List<String> get_ReportList(){
        report_list.clear();
        
        report_list.add("Workload Allocation Report");
        report_list.add("Workload Claim Processing Report");
        report_list.add("Mileage Claim Processing Report");
        
        return report_list;
    }
    
    public void setWorkloadList(ArrayList workloadList){
        this.workloadList = workloadList;
    }

    public List<String> get_YearList() {
        year_list.clear();
        
        year_list.add("2016");
        year_list.add("2017");
        year_list.add("2018");
        
        return year_list;
    }
   
    public void reportTitle(){
        reportTitle = reportType + String.valueOf(year);
        setReportTitle(reportTitle);
    }
    
    public void callStatus(){
        getWorkloadList();
    }
    
    public ArrayList getWorkloadList() {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM workloadallocation WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                workloadCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String[] staffIDList = new String[workloadCount];
        String[] teacherCSMapList = new String[workloadCount];
        String[] schoolCSMapList = new String[workloadCount];
        String[] schoolIDList = new String[workloadCount];
        String[] csLevelIDList = new String[workloadCount];
        String[] teacherIDList = new String[workloadCount];
        String[] schoolNameList = new String[workloadCount];
        String[] teacherNameList = new String[workloadCount];
        String[] assessmentList = new String[workloadCount];
        int tmp = 0;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT teacherCSMapID, assessment FROM workloadallocation WHERE year = ?");
            st.setInt(1, year);
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
        for (int i = 0; i < workloadCount; i++) {
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
        for (int i = 0; i < workloadCount; i++) {
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
        for (int i = 0; i < workloadCount; i++) {
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
        for (int i = 0; i < workloadCount; i++) {
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
        
        int tmp7 = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT staffID FROM workloadallocation WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                staffIDList[tmp7] = rs.getString("staffID");
                tmp7++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        int tmp8 = 0;
        for (int i = 0; i < workloadCount; i++) {
            String staffNameTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT name FROM evaluatorpersonaldetails WHERE staffID = ?");
                st.setString(1, staffIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    staffNameTmp = rs.getString("name");
                    teacherNameList[tmp8] = staffNameTmp;
                    tmp8++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
        
        workloadList = new ArrayList();

        for (int i = 0; i < workloadCount; i++) {
            generateReportobj1 = new GenerateReport();
            generateReportobj1.setNumber_DT(i+1);
            generateReportobj1.setSchool_DT(schoolNameList[i]);
            generateReportobj1.setCsLevel_DT(csLevelIDList[i]);
            generateReportobj1.setTeacher_DT(teacherNameList[i]);
            generateReportobj1.setAssessment_DT(assessmentList[i]);
            workloadList.add(generateReportobj1);
        }
        
        return workloadList;
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
