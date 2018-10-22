/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
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
    private String contactNum;
    private String branch;
    private String faculty;
    private String role;
    private String status;
    private Integer workloadLimit;
    private String roleID;
    private Integer count;
    private String rdID;

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
    public void evaluatorData(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
        
        verifyRole();
    }
    
    //verify role type
    public void verifyRole(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM roles");
            
            while(rs.next()){
              String rID = rs.getString("roleID");
              String rType = rs.getString("roleType");
                
                if(role.equals(rType)){
                    roleID = rID;
                    break;
                }
            }
            
            st.close();
            con.close();
            
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
        
        countRole(roleID);
    }
    
    //count role to auto-generate id
    public void countRole(String roleID){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM evaluatorroledetails");
            
            while(rs.next()){
              count = rs.getInt("COUNT(*)");
            }
            
            rs.close();
            st.close();
            con.close();
            
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
       
        evaluatorRole(roleID, count);
    }
    
    //save evaluator role
    public void evaluatorRole(String roleID, Integer count){
        
        count = count + 1;
        rdID = "RD" + Integer.toString(count);
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO evaluatorroledetails (RD_ID, roleID, staffID) VALUES (?, ?, ?)");
                      
            statement.setString(1, rdID);
            statement.setString(2, roleID);
            statement.setString(3, staffID);
            
            statement.executeUpdate();
            statement.close();
            con.close();
            
        }catch(Exception ex){
            System.out.println("Error: " + ex);
        }
        
    }
    
    public void main(String args[]){
        evaluatorData();
    }
}
