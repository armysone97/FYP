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

    private List<String> branch_list = new ArrayList<>();
    private List<String> faculty_list = new ArrayList<>();
    private List<String> status_list = new ArrayList<>();

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

    public void setBranch_list(List<String> branch_list) {
        this.branch_list = branch_list;
    }

    public void setFaculty_list(List<String> faculty_list) {
        this.faculty_list = faculty_list;
    }

    public void setStatus_list(List<String> status_list) {
        this.status_list = status_list;
    }

    //retrieve branch from db
    public List<String> get_BranchList() {

        branch_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT branch FROM branch");

            while (rs.next()) {
                branch_list.add(rs.getString("branch"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return branch_list;
    }

    //retrieve faculty from db
    public List<String> get_FacultyList() {

        faculty_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT faculty FROM faculty");

            while (rs.next()) {
                faculty_list.add(rs.getString("faculty"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return faculty_list;
    }
    
    //retrieve status from db
    public List<String> get_StatusList() {

        status_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT status FROM status");

            while (rs.next()) {
                status_list.add(rs.getString("status"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return status_list;
    }

    //check validation
    public void validationCheck() {
        
        FacesContext context = FacesContext.getCurrentInstance();

        if (evaName == null || staffID == null || contactNum == null || branch == null || faculty == null || role == null || status == null || workloadLimit == null) {
            context.addMessage(null, new FacesMessage("All field are required to fill in!"));
        } else if (!contactNum.matches("-?\\d+")) {
            context.addMessage(null, new FacesMessage("Contact Number must be in integer only! Please try again!"));
        } else {
            duplicateRecordCheck();
        }
    }

    //check duplicate record
    public void duplicateRecordCheck() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        boolean check = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM roles");

            while (rs.next()) {
                String role_ID = rs.getString("roleID");
                String role_Type = rs.getString("roleType");

                if (role.equals(role_Type)) {
                    roleID = role_ID;
                    break;
                }
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT roleID, staffID FROM evaluatorroledetails");

            while (rs.next()) {
                String staff_ID = rs.getString("staffID");
                String rolesID = rs.getString("roleID");

                if (staffID.equals(staff_ID) && roleID.equals(rolesID)) {

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
            evaluatorData();
        } else {
            context.addMessage(null, new FacesMessage("Record already existed!"));
        }
    }

    //save evaluator personal details
    public void evaluatorData() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO evaluatorpersonaldetails (staffID, name, campus, faculty, contactNo, status, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, staffID);
            statement.setString(2, evaName);
            statement.setString(3, branch);
            statement.setString(4, faculty);
            statement.setString(5, contactNum);
            statement.setString(6, status);
            statement.setString(7, staffID);
            statement.setString(8, String.valueOf(contactNum));

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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        if (roleID.equals("RO2")) {
            evaCount = evaCount + 1;
            rdID = "RD" + Integer.toString(evaCount);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO evaluatorroledetails (RD_ID, roleID, staffID) VALUES (?, ?, ?)");

                statement.setString(1, rdID);
                statement.setString(2, "RO1");
                statement.setString(3, staffID);

                statement.executeUpdate();
                statement.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        countWorkloadLimit();
    }

    //retrieve workload count from db
    public void countWorkloadLimit() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //save workload limit to db
    public void addWorkloadLimit(Integer workloadLimitCount) {

        FacesContext context = FacesContext.getCurrentInstance();

        workloadLimitCount = workloadLimitCount + 1;
        wlID = "WL" + Integer.toString(workloadLimitCount);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        context.addMessage(null, new FacesMessage("Added successful!"));

        reset();
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

        evaName = null;
        staffID = null;
        contactNum = null;
        branch = null;
        faculty = null;
        role = null;
        status = null;
        workloadLimit = null;

        counterReset = 0;
        MaintainSchoolMenu.setGlobalCounter(0);
    }
}
