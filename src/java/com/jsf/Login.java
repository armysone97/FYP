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

    public Login() {
        role = "1";
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

    public void verifyRole() {
        FacesContext context = FacesContext.getCurrentInstance();

        Boolean verify = false;
        Boolean verify1 = false;
        String roleID = "", staffID = "";
        String roleTypes = "";

        if (role.equals("")) {
            context.addMessage(null, new FacesMessage("Role must be selected, please try again!"));
        } else {
            switch (role) {
                case "1":
                    roleTypes = "Admin";
                    break;
                case "2":
                    roleTypes = "Evaluator";
                    break;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM roles");

                while (rs.next()) {
                    if (roleTypes.equals(rs.getString("roleType"))) {
                        roleID = rs.getString("roleID");
                        context.addMessage(null, new FacesMessage("success"));
                        verify = true;
                        break;
                    }
                }

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            if (!verify) {
                context.addMessage(null, new FacesMessage("Invalid role."));
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

                    PreparedStatement st = con.prepareStatement("SELECT staffID, roleID FROM evaluatorroledetails WHERE roleID = ?");
                    st.setString(1, roleID);
                    ResultSet rs = st.executeQuery();

                    while (rs.next()) {
                        staffID = rs.getString("staffID");
                        context.addMessage(null, new FacesMessage("success1"));
                        verify1 = true;
                        verifyUser(staffID);
                        break;
                    }
                } catch (Exception ex) {
                    // context.addMessage(null, new FacesMessage("successaxxxx"));
                    System.out.println("Error: " + ex);
                }

                if (!verify1) {
                    context.addMessage(null, new FacesMessage(roleTypes + " don't have an account, please try again!"));
                }
            }
        }
    }

    public void verifyUser(String staffID) {

//         RequestContext.getCurrentInstance().execute("alert('peek-a-boo');");
//         PrimeFaces.current().executeScript("alert('peek-a-boo');");
//         
//        UIComponent source = event.getCOmponent();
//        
//        FacesMessage message = com.corejsf.util.Messages.getMessage("com.corejsf.messages", "invalidDate", null);
//        message.setSeverity(FacesMessage.SEVERITY_ERROR);
//        
//        UIComponent container = event.getComponent().getNamingContainer();
//        String name = (String) ((UIInput) container.findComponent("form:lblUsername")).getValue();
//        
//
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(source.getClientId());
//        context.renderResponse();
//        JOptionPane.showMessageDialog(null, "My Goodness, this is so concise");
//        showMessageDialog(null, "This is even shorter");     
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean verify = false;

        if (username.equals("") || password.equals("")) {
            context.addMessage(null, new FacesMessage("Username or password cannot be empty, please try again!"));
            verify = false;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT username, password FROM evaluatorpersonaldetails WHERE staffID = ?");
                st.setString(1, staffID);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                        context.addMessage(null, new FacesMessage("success"));
                        verify = true;
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

//   int input = JOptionPane.showConfirmDialog(null, 
//                "Click ok if you are ok", "Be ok!", JOptionPane.DEFAULT_OPTION);
//        // 0=ok
//        System.out.println(input);
    }

    public void main(String args[]) {

        verifyRole();
    }

}
