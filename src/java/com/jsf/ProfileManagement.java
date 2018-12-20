package com.jsf;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class ProfileManagement {

    private Connection con;
    private String evaName;
    private String staffID;
    private String contactNum;
    private String branch;
    private String faculty;
    private String role;
    private String status;
    private int year;
    private int workloadLimit;
    private String roleID;
    private Integer evaCount;
    private Integer workloadLimitCount;
    private String rdID;
    private String wlID;
    private int counterReset; //growl purpose

    private List<String> branch_list = new ArrayList<>();
    private List<String> faculty_list = new ArrayList<>();
    private List<String> status_list = new ArrayList<>();

    private boolean disabledTxt, disabledButtonEdit, disabledButtonConfirm;
    private int counterWL, counterWL1;

    public ProfileManagement() {
        this.counterReset = 0;
        this.disabledTxt = true;
        this.disabledButtonEdit = false;
        this.disabledButtonConfirm = true;
        this.counterWL = 0;
        this.workloadLimit = 0;
        this.counterWL1 = 0;
        this.year = 2018;
    }

    public boolean isDisabledButtonEdit() {
        return disabledButtonEdit;
    }

    public void setDisabledButtonEdit(boolean disabledButtonEdit) {
        this.disabledButtonEdit = disabledButtonEdit;
    }

    public boolean isDisabledButtonConfirm() {
        return disabledButtonConfirm;
    }

    public void setDisabledButtonConfirm(boolean disabledButtonConfirm) {
        this.disabledButtonConfirm = disabledButtonConfirm;
    }

    public boolean isDisabledTxt() {
        return disabledTxt;
    }

    public void setDisabledTxt(boolean disabledTxt) {
        this.disabledTxt = disabledTxt;
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

    public int getWorkloadLimit() {
        return workloadLimit;
    }

    public void setWorkloadLimit(int workloadLimit) {
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

    //retrieve branch list
    public List<String> get_BranchList() {

        branch_list.clear();

        //retrieve branch from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //retrieve faculty list
    public List<String> get_FacultyList() {

        faculty_list.clear();

        //retrieve faculty from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //retrieve status list
    public List<String> get_StatusList() {

        status_list.clear();

        //retrieve status from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public void defaultStaffList() {

        this.workloadLimit = 0;
        switch (Login.getGlobalCounter()) {
            case 1:
                this.staffID = Login.getGlobalStaffID();
                retrievePersonalDetails();
                retrieveRole();
                retrieveWorkloadLimit();
                break;
        }
    }

    //retrieve personal details from db
    public void retrievePersonalDetails() {

        //retrieve personal details
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT name, campus, faculty, contactNo, status FROM evaluatorpersonaldetails WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                evaName = rs.getString("name");
                branch = rs.getString("campus");
                faculty = rs.getString("faculty");
                contactNum = rs.getString("contactNo");
                status = rs.getString("status");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    //retrive workload limit from db
    public void retrieveWorkloadLimit() {
        workloadLimit = 0;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT workloadLimit FROM workloadlimit WHERE staffID = ?");
            st.setString(1, staffID);
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
    }

    //count role in db
    public int get_roleCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT roleID FROM evaluatorroledetails WHERE staffID = ?");
            st.setString(1, staffID);
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

    //verify role type
    public String matchRoleID(String roleID) {

        String roleType = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT roleType FROM roles WHERE roleID = ?");
            st.setString(1, roleID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                roleType = rs.getString("roleType");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return roleType;

    }

    //check role type
    public void retrieveRole() {

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 0;

        Boolean verify = false;
        Boolean verify1 = false;
        String roleTypes = "";

        int lengthRoleList = get_roleCount();

        String[] roleList = new String[lengthRoleList];
        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            PreparedStatement st = con.prepareStatement("SELECT roleID FROM evaluatorroledetails WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count += 1;
                roleList[tmp] = rs.getString("roleID");
                tmp++;

                verify1 = true;

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        for (int i = 0; i < roleList.length; i++) {
            String roleIDFromDB = matchRoleID(roleList[i]);
            if (roleIDFromDB.equals("Admin")) {
                role = "Master Trainer";
                break;
            } else if (roleIDFromDB.equals("Evaluator")) {
                role = "Evaluator";
            } else if (roleIDFromDB.equals("Master Trainer")) {
                role = "Master Trainer";
                break;
            }
        }
    }

    //called when edit button selected
    public void editProfile() {
        disabledTxt = false;
        disabledButtonEdit = true;
        disabledButtonConfirm = false;
    }

    //check validation
    public void validationCheck() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        String ttlWorkloadDB = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            PreparedStatement st = con.prepareStatement("SELECT ttlWorkloadAssigned FROM workloadlimit WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ttlWorkloadDB = rs.getString("ttlWorkloadAssigned");
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        if (contactNum == "" || workloadLimit == 0 || branch == null || faculty == null || status == null) {
            context.addMessage(null, new FacesMessage("All field are required to fill in!"));
        } 
        else if(workloadLimit < Double.valueOf(ttlWorkloadDB)){
            context.addMessage(null, new FacesMessage("Workload limit invalid!"));
        }
        else if(workloadLimit > 40){
            context.addMessage(null, new FacesMessage("Maximum workload limit is 40 hours!"));
        }
        else if (!(String.valueOf(workloadLimit)).matches("-?\\d+")) {
            context.addMessage(null, new FacesMessage("Workload Limit must be in integer only! Please try again!"));
        }
        else {
            updateProfile();
        }
    }

    //update personal details in db
    public void updateProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;

        if (!contactNum.matches("-?\\d+")) { // any positive or negetive integer or not!
            context.addMessage(null, new FacesMessage("Contact Number must be in integer only! Please try again!"));
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE evaluatorpersonaldetails SET campus = ?, faculty = ?, contactNo = ?, status = ?, password = ? WHERE staffID = ?");

                //update school
                statement.setString(1, branch);
                statement.setString(2, faculty);
                statement.setString(3, contactNum);
                statement.setString(4, status);
                statement.setString(5, contactNum);
                statement.setString(6, staffID);
                statement.executeUpdate();

                verifyCounter = 1;

                statement.close();
                con.close();
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        switch (verifyCounter) {
            case 0:
                context.addMessage(null, new FacesMessage("Update evaluator name " + evaName + " not successful!"));
                break;
            case 1:
                context.addMessage(null, new FacesMessage("Update evaluator name " + evaName + " successful!"));
                disabledTxt = true;
                disabledButtonEdit = false;
                disabledButtonConfirm = true;
                updateWorkloadLimit();
                break;
        }
    }

    //update workload limit in db
    public void updateWorkloadLimit() {
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE workloadlimit SET workloadLimit = ? WHERE staffID = ?");

            statement.setInt(1, workloadLimit);
            statement.setString(2, staffID);
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
        return "ProfileManagement";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

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
        MaintainSchoolMenu.setGlobalCounter(0);
    }

}
