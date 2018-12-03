/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
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

public class WorkloadClaimApplication {

    private Connection con;
    private String staffID;
    private String name;
    private String branch;
    private String faculty;
    private String role;
    private String hourlyRate;
    private String workHours;
    private double totalClaim;
    private int year;
    private String result;

    private int counterReset;
    
    //for add workload claim
    private int workloadClaimCount;
    private String wcID;
    private String role_ID;
    private String rdID;

    public WorkloadClaimApplication() {
        this.counterReset = 0;
        this.year = 2017;
        this.hourlyRate = "0.0";
        this.workHours = "0.0";
        this.result = "0.0";
//        this.staffID = Login.getGlobalStaffID();
//        retrievePersonalDetails();
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public double getTotalClaim() {
        return totalClaim;
    }

    public void setTotalClaim(double totalClaim) {
        this.totalClaim = totalClaim;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void defaultStaffList() {
        switch (Login.getGlobalCounter()) {
            case 1:
                this.staffID = Login.getGlobalStaffID();
                retrievePersonalDetails();
                break;
        }
    }

    public void retrievePersonalDetails() {

        //retrieve personal details
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT name, campus, faculty FROM evaluatorpersonaldetails WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setName(rs.getString("name"));
                setBranch(rs.getString("campus"));
                setFaculty(rs.getString("faculty"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //retrieve total workload
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT SUM(workloadAssigned) FROM workloadallocation WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setWorkHours(String.format("%.2f", rs.getDouble("SUM(workloadAssigned)")));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }
    
    //retrieve hourly rate
    public void retrieveHourlyRate(){
        
        //retrieve hourly rate
        if(role.equals("Evaluator")){
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT evHourlyRate FROM rate WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setHourlyRate(String.format("%.2f", rs.getDouble("evHourlyRate")));
            }

            rs.close();
            st.close();
            con.close();
            
            } 
            catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
            
        }
        else{
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT mtHourlyRate FROM rate WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setHourlyRate(String.format("%.2f", rs.getDouble("mtHourlyRate")));
            }

            rs.close();
            st.close();
            con.close();
            
            } 
            catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
    }
    
    //calculate total workload claim
    public void calculateWorkloadClaim(){
        totalClaim = (Double.valueOf(hourlyRate))*(Double.valueOf(workHours));
        
        result = String.format("%.2f", totalClaim);
    }
    
    //save workload claim
    public void addWorkloadClaim(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        //count workload claim index
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM workloadclaim");

            while (rs.next()) {
                workloadClaimCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        workloadClaimCount = workloadClaimCount + 1;
        wcID = "WC" + Integer.toString(workloadClaimCount);
        
        //retrieve role ID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT roleID FROM roles WHERE roleType = ?");
            st.setString(1, role);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                role_ID = rs.getString("roleID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //retrieve rdID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT RD_ID FROM evaluatorroledetails WHERE staffID = ? AND roleID = ?");
            st.setString(1, staffID);
            st.setString(2, role_ID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                rdID = rs.getString("RD_ID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //insert worload claim
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO workloadclaim (WC_ID, totalWorkload, totalWorkloadClaim, year, RD_ID) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, wcID);
            statement.setDouble(2, Double.valueOf(workHours));
            statement.setDouble(3, Double.valueOf(result));
            statement.setInt(4, year);
            statement.setString(5, rdID);

            statement.executeUpdate();
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        context.addMessage(null, new FacesMessage("Added successful!"));
        
    }
    
    public void roleChanged(){
        retrieveHourlyRate();
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();

        return "WorkloadClaimApplication";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

        role = null;
        hourlyRate = "0.0";
        workHours = "0.0";
        totalClaim = 0;
        result = "0.0";
        year = 2018;

        counterReset = 0;
    }

}
