/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    private Integer contactNum;
    private String branch;
    private String faculty;
    private String role;
    private String status;
    private Integer workloadLimit;

    public EvaluatorPersonalDetails() {
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

    public Integer getContactNum() {
        return contactNum;
    }

    public void setContactNum(Integer contactNum) {
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
    
//    public void connection(){
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        }catch(Exception ex){
//            System.out.println("Error:" + ex);
//        }
//      
//    }
    
    public void createData(){
//        String host = "jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//        String username = "root";
//        String password = "";
        System.out.println("Connected1");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            System.out.println("Connected");
           // con.setAutoCommit(false);
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO evaluatorpersonaldetails (staffID, name, campus, faculty, contactNo, status, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            
//            con = DriverManager.getConnection(host, username, password);
//            String query = "INSERT INTO evaluatorpersonaldetails (staffID, name, campus, faculty, contactNo, status, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//            statement = (PreparedStatement) con.prepareStatement(query);
//            
            statement.setString(1, staffID);
            statement.setString(2, evaName);
            statement.setString(3, branch);
            statement.setString(4, faculty);
            statement.setInt(5, contactNum);
            statement.setString(6, status);
            statement.setString(7, staffID);
            statement.setString(8, Integer.toString(contactNum));
            
            statement.executeUpdate();
            //con.commit();
            statement.close();
            con.close();
            
        }catch(Exception ex){
            System.out.println("Error:" + ex);
            //throw new FacesException(e);
        }
        
//        try{
//            Connection con1 = DriverManager.getConnection(host, username, password);
//            String query = "INSERT INTO evaluatorroledetails (RD_ID, roleID, staffID) VALUES (?, ?, ?)";
//            PreparedStatement statement = (PreparedStatement) con1.prepareStatement(query);
//            
//            statement.setString(1, "R1");
//            statement.setString(2, "r2");
//            statement.setString(3, staffID);
//
//            statement.executeUpdate();
//            statement.close();
//            con1.close();
//            
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
    }
    
    public void main(String args[]){
        createData();
    }
}
