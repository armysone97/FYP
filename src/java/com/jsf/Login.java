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

    public Login() {
        role = "Admin";
//        username = null;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

            PreparedStatement st = con.prepareStatement("SELECT roleID FROM evaluatorroledetails WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count += 1;
                context.addMessage(null, new FacesMessage("success1"));

                roleList[tmp] = rs.getString("roleID");
                tmp++;

                verify1 = true;

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String roleIDFromDB = matchRoleID();

        for (int i = 0; i < roleList.length; i++) {

            if (roleList[i].equals(roleIDFromDB)) {
                roleTypes = role;
                
                if (role.equals("Admin")){
                    nextPage = "CSLevelSetting";
                }else if(role.equals("Evaluator")){
                     nextPage = "WorkloadClaimApplication";
                }
//                nextPage = "login" + role;

                break;
            }
        }

        return nextPage;

    }

    public String verifyUser() {

        FacesContext context = FacesContext.getCurrentInstance();
        Boolean verify = false;
        String staffID = "";
//        String nextPage = "";

      context.addMessage(null, new FacesMessage("xxxx"));
           

        if (username.equals("") || password.equals("")) {
            context.addMessage(null, new FacesMessage("Username or password cannot be empty, please try again!"));
            verify = false;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT staffID, username, password FROM evaluatorpersonaldetails");

                while (rs.next()) {
                    if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                        staffID = rs.getString("staffID");

                        context.addMessage(null, new FacesMessage("success"));

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
        reset();
        return "Login";
    }

    //reset page
    public void reset() {
        //set default value
        username = null;
        password = null;
        nextPage = null;
        role = "Admin";
    }

//    public void main(String args[]) {
//
//        verifyUser();
//    }

}
