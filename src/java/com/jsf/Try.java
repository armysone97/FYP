/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



/**
 *
 * @author ruenyenchin
 */

@ManagedBean
@ViewScoped

public class Try{
    
    private Connection con;
    private String evaluator;
    private List<String> evaluatorlist = new ArrayList<>();

    public List<String> get_Evaluatorlist() {

        evaluatorlist.clear();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM evaluatorpersonaldetails WHERE status='Available'");

            while (rs.next()) {
                evaluatorlist.add(rs.getString("name"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return evaluatorlist;
    }

    public void setEvaluatorlist(List<String> evaluatorlist) {
        this.evaluatorlist = evaluatorlist;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }
    
    public void retrieveEvaluator(){
//        evaluatorlist = new ArrayList<>();
//        
//        evaluatorlist.clear();
//        
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT name FROM evaluatorpersonaldetails WHERE status='Available'");
//
//            while (rs.next()) {
//                evaluatorlist.add(rs.getString("name"));
//            }
//
//            rs.close();
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
    }
    
    public void main(String args[]){
        retrieveEvaluator();
    }
}
