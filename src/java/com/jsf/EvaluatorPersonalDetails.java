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

public class EvaluatorPersonalDetails {

    private Connection con;
    private String evaName;
    private String staffID;
    private String contactNum;
    private String branch;
    private String faculty;
    private String role;
    private String status;
    private Integer workloadLimit;
    private String roleID;
    private Integer evaCount;
    private Integer workloadLimitCount;
    private String rdID;
    private String wlID;
    private int counterReset; //growl purpose

    public EvaluatorPersonalDetails() {
        this.counterReset = 0;
    }

    public String getEvaName() {
        return evaName;
    }

    public void setEvaName(String evaName) {
        this.evaName = evaName;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWorkloadLimit() {
        return workloadLimit;
    }

    public void setWorkloadLimit(Integer workloadLimit) {
        this.workloadLimit = workloadLimit;
    }

    //save evaluator personal details
    public void evaluatorData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO evaluatorpersonaldetails (staffID, name, campus, faculty, contactNo, status, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, staffID);
            statement.setString(2, evaName);
            statement.setString(3, branch);
            statement.setString(4, faculty);
            statement.setString(5, contactNum);
            statement.setString(6, status);
            statement.setString(7, staffID);
            statement.setString(8, contactNum);

            statement.executeUpdate();
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        verifyRole();
    }

    //verify role type
    public void verifyRole() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM roles");

            while (rs.next()) {
                String rID = rs.getString("roleID");
                String rType = rs.getString("roleType");

                if (role.equals(rType)) {
                    roleID = rID;
                    break;
                }
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        countRole(roleID);
    }

    //count role to auto-generate id
    public void countRole(String roleID) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM evaluatorroledetails");

            while (rs.next()) {
                evaCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        evaluatorRole(roleID, evaCount);
    }

    //save evaluator role
    public void evaluatorRole(String roleID, Integer evaCount) {

        evaCount = evaCount + 1;
        rdID = "RD" + Integer.toString(evaCount);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO evaluatorroledetails (RD_ID, roleID, staffID) VALUES (?, ?, ?)");

            statement.setString(1, rdID);
            statement.setString(2, roleID);
            statement.setString(3, staffID);

            statement.executeUpdate();
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        countWorkloadLimit();
    }

    public void countWorkloadLimit() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM workloadlimit");

            while (rs.next()) {
                workloadLimitCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        addWorkloadLimit(workloadLimitCount);
    }

    public void addWorkloadLimit(Integer workloadLimitCount) {
        workloadLimitCount = workloadLimitCount + 1;
        wlID = "WL" + Integer.toString(workloadLimitCount);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO workloadlimit (WL_ID, workloadLimit, ttlWorkloadAssigned, year, staffID) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, wlID);
            statement.setInt(2, workloadLimit);
            statement.setInt(3, 0);
            statement.setInt(4, 2018);
            statement.setString(5, staffID);

            statement.executeUpdate();
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();
        return "EvaluatorPersonalDetails";
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
        evaName = null;
        staffID = null;
        contactNum = null;
        branch = "Kuala Lumpur Main Campus";
        faculty = "FOCS";
        role = null;
        status = "Available";
        workloadLimit = 0;

        counterReset = 0;
    }

    public void main(String args[]) {
        evaluatorData();
    }
}
