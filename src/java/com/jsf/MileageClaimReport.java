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

public class MileageClaimReport {
    
    private Connection con;
    private int year;
    private String evaluator;
    private String staffID;
    private int mileageClaimCount;
    private int counterReset;
    
    private List<String> year_list = new ArrayList<>();
    private List<String> evaluator_list = new ArrayList<>();
    
    private ArrayList mileageClaimList = null;
    private MileageClaimReport mileageClaimRepobj1 = null;

    public MileageClaimReport() {
        this.counterReset = 0;
        this.year = 2018;
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

    public void setEvaluator_list(List<String> evaluator_list) {
        this.evaluator_list = evaluator_list;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setMileageClaimList(ArrayList mileageClaimList) {
        this.mileageClaimList = mileageClaimList;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }
    
    //display year
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
    
    //retrieve evaluator workload
    public ArrayList getMileageClaimList() {
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
        
        //count amt workload claim
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM mileageclaimprocessing WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                mileageClaimCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        String[] claimRecordList = new String[mileageClaimCount];
        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliant=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT claimRecord FROM mileageclaimprocessing WHERE year = ? AND staffID = ?");
            st.setInt(1, year);
            st.setString(2, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                claimRecordList[tmp] = rs.getString("claimRecord");
                tmp++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        String[] schoolNameList = new String[mileageClaimCount];
        String[] assessmentList = new String[mileageClaimCount];

        for (int i = 0; i < mileageClaimCount; i++) {
            String[] parts = claimRecordList[i].split(" - ");
            schoolNameList[i] = parts[0];
            assessmentList[i] = parts[1];
        }
        
        return mileageClaimList;
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();

        return "MileageClaimReport";
    }

    //reset page
    public void reset() {

        counterReset = 0;
        MaintainSchoolMenu.setGlobalCounter(0);
    }
}
