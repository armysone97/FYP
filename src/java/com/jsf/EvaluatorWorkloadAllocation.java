/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ruenyenchin
 */

@ManagedBean
@SessionScoped

public class EvaluatorWorkloadAllocation {
    
    private Connection con;
    private String evaluator;
    private String school;
    private String csLevel;
    private String teacher;
    private List<String> evaluator_list = new ArrayList<>();

    public EvaluatorWorkloadAllocation() {
    }
    
    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCsLevel() {
        return csLevel;
    }

    public void setCsLevel(String csLevel) {
        this.csLevel = csLevel;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setEvaluator_list(List<String> evaluator_list) {
        this.evaluator_list = evaluator_list;
    }
    
     public List<String> get_EvaluatorList() {
         
         evaluator_list.clear();
      
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM evaluatorpersonaldetails WHERE status='Available'");

            while (rs.next()) {
                evaluator_list.add(rs.getString("name"));
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
         
         return evaluator_list;
     }
     
    public void main(String args[]){
        get_EvaluatorList();
    }
}
