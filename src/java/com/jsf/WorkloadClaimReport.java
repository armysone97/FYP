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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ruenyenchin
 */

@ManagedBean
@SessionScoped

public class WorkloadClaimReport {

    private Connection con;
    private String evaluator;
    private int year;
    private String staffID;
    private int workloadClaimCount;
    private int counterReset;
    
    private List<String> year_list = new ArrayList<>();
    private List<String> evaluator_list = new ArrayList<>();
    
    private ArrayList workloadClaimList = null;
    private WorkloadClaimReport workClaimRepobj1 = null;
    private String claimCount_DT = "";
    private String ttlWorkload_DT = "";
    private String ttlWorkloadClaim_DT = "";

    public WorkloadClaimReport() {
        this.counterReset = 0;
        this.year = 2018;
    }

    public int getYear() {
        return year;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public String getClaimCount_DT() {
        return claimCount_DT;
    }

    public void setClaimCount_DT(String claimCount_DT) {
        this.claimCount_DT = claimCount_DT;
    }

    public String getTtlWorkload_DT() {
        return ttlWorkload_DT;
    }

    public void setTtlWorkload_DT(String ttlWorkload_DT) {
        this.ttlWorkload_DT = ttlWorkload_DT;
    }

    public String getTtlWorkloadClaim_DT() {
        return ttlWorkloadClaim_DT;
    }

    public void setTtlWorkloadClaim_DT(String ttlWorkloadClaim_DT) {
        this.ttlWorkloadClaim_DT = ttlWorkloadClaim_DT;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public void setWorkloadClaimList(ArrayList workloadClaimList) {
        this.workloadClaimList = workloadClaimList;
    }
    
    public void setYear(int year) {
        this.year = year;
    }

    public void setYear_list(List<String> year_list) {
        this.year_list = year_list;
    }

    //display year
    public List<String> get_YearList() {
        year_list.clear();

        year_list.add("2016");
        year_list.add("2017");
        year_list.add("2018");

        return year_list;
    }

    public void setEvaluator_list(List<String> evaluator_list) {
        this.evaluator_list = evaluator_list;
    }
    
    //display evaluator list
    public List<String> get_EvaluatorList() {
        evaluator_list.clear();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
    
    public void callWorkloadClaim() {
        getWorkloadClaimList();
    }
    
    //retrieve evaluator workload
    public ArrayList getWorkloadClaimList() {
        //retrieve staff ID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        
        //count amt workload claim
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM workloadclaim WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                workloadClaimCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        String[] claimCountList = new String[workloadClaimCount];
        Double[] totalWorkloadList = new Double[workloadClaimCount];
        Double[] totalWorkloadClaimList = new Double[workloadClaimCount];
        
        int tmp = 0;
        int tmp1 = 0;
        int tmp2 = 0;
        
        String claimCountTmp = "";
        double totalWorkloadTmp = 0.0;
        double totalWorkloadClaimTmp = 0.0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT claimCount, totalWorkload, totalWorkloadClaim FROM workloadclaim WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                claimCountTmp = rs.getString("claimCount");
                claimCountList[tmp] = claimCountTmp;
                tmp++;

                totalWorkloadTmp = rs.getDouble("totalWorkload");
                totalWorkloadList[tmp1] = totalWorkloadTmp;
                tmp1++;
                
                totalWorkloadClaimTmp = rs.getDouble("totalWorkloadClaim");
                totalWorkloadClaimList[tmp2] = totalWorkloadClaimTmp;
                tmp2++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        workloadClaimList = new ArrayList();

        for (int i = 0; i < workloadClaimCount; i++) {
            workClaimRepobj1 = new WorkloadClaimReport();
            workClaimRepobj1.setClaimCount_DT(claimCountList[i]);
            workClaimRepobj1.setTtlWorkload_DT(String.format("%.2f", totalWorkloadList[i]));
            workClaimRepobj1.setTtlWorkloadClaim_DT("RM" + String.format("%.2f", totalWorkloadClaimList[i]));
            workloadClaimList.add(workClaimRepobj1);
        }
        
        return workloadClaimList;
    }
    
    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();

        return "WorkloadClaimReport";
    }

    //reset page
    public void reset() {

        counterReset = 0;
        MaintainSchoolMenu.setGlobalCounter(0);
    }
}
