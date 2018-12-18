/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.awt.HeadlessException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import static javax.swing.JOptionPane.showMessageDialog;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import org.jboss.weld.context.RequestContext;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class Login {

    private Connection con;
    private String username;
    private String password;
    private String role;
    private String nextPage;
    private int counterReset; //growl purpose
    private static int globalCounter;

    private static String globalStaffID;

    public Login() {
        this.role = "Admin";
        this.username = null;
        this.counterReset = 0;
        this.globalCounter = 0;

    }

    public static int getGlobalCounter() {
        return globalCounter;
    }

    public static void setGlobalCounter(int globalCounter) {
        Login.globalCounter = globalCounter;
    }

    public static String getGlobalStaffID() {
        return globalStaffID;
    }

    public static void setGlobalStaffID(String globalStaffID) {
        Login.globalStaffID = globalStaffID;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //count role in db
    public int get_roleCount(String staffID) {

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

    public String matchRoleID() {

        String roleID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT roleID FROM roles WHERE roleType = ?");
            st.setString(1, role);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                roleID = rs.getString("roleID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return roleID;

    }

    public String verifyRole(String staffID) {

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 0;
        String roleType = "";
        Boolean verify = false;
        Boolean verify1 = false;
        String roleTypes = "";
//        String nextPage = "";

        int lengthRoleList = get_roleCount(staffID);

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
                //  context.addMessage(null, new FacesMessage("success1"));

                roleList[tmp] = rs.getString("roleID");
                tmp++;

                verify1 = true;

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String roleIDFromDB = matchRoleID();
        int counter = 0;

        for (int i = 0; i < roleList.length; i++) {

            if (roleList[i].equals(roleIDFromDB)) {
                roleTypes = role;
                counter = 1;

                if (role.equals("Admin")) {
                    nextPage = "CSLevelSetting";
                    globalStaffID = username;
                    globalCounter = 1;

                    context.addMessage(null, new FacesMessage("Login Successful!"));

                } else if (role.equals("Evaluator")) {
//                    nextPage = "WorkloadClaimApplication";
                    nextPage = "ProfileManagement";
                    globalStaffID = username;
                    globalCounter = 1;

                    context.addMessage(null, new FacesMessage("Login Successful!"));

                }
//                nextPage = "login" + role;

                break;
            }
        }

        if (counter == 0) {
            context.addMessage(null, new FacesMessage("Invalid role, please try again!"));
        }

        return nextPage;

    }

    public String verifyUser() {

        FacesContext context = FacesContext.getCurrentInstance();
        Boolean verify = false;
        String staffID = "";
//        String nextPage = "";

        if (username.equals("") || password.equals("")) {
            context.addMessage(null, new FacesMessage("Username or password cannot be empty, please try again!"));
            verify = false;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT staffID, username, password FROM evaluatorpersonaldetails");

                while (rs.next()) {
                    if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                        staffID = rs.getString("staffID");

                        verify = true;
                        nextPage = verifyRole(staffID);
                        break;
                    }
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            if (!verify) {
                context.addMessage(null, new FacesMessage("Invalid username or password, please try again!"));
            }
        }

        return nextPage;
    }

    //navigation bar purpose
    public String goToNextPage() {

//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("Logout successful!"));
        counterReset = 1;

        reset();

        return "Login";
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
        username = null;
        password = null;
        nextPage = null;
        role = "Admin";
        globalStaffID = null;
        counterReset = 0;
        globalCounter = 0;

        MaintainSchoolMenu.setGlobalCounter(0);
    }

//    public void main(String args[]) {
//
//        verifyUser();
//    }
}
