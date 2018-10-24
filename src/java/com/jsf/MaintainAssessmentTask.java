/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.Connection;
import java.sql.DriverManager;
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
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class MaintainAssessmentTask {

    private Connection con;

    private String status, state, school;

    private List<String> school_list = new ArrayList<>();

    private Boolean disabledDDL;

    //private List<SelectItem> school_list;
//    {
//    }
    public MaintainAssessmentTask() {
        disabledDDL = false;
    }

    

//    public List<SelectItem> getSchool_list() {
//        return school_list;
//    }
//
//    public void setSchool_list(List<SelectItem> school_list) {
//        this.school_list = school_list;
//    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    
    public Boolean changeDDLDisabled() {
        
        if(disabledDDL){
            disabledDDL = false;
        }else{
            disabledDDL = true;
        }

        return disabledDDL;
    }

    public void abcClick() {

        //  school = "SMJK Heng Yee";
             status = "xx";
//        state = "Kuala Lumpur";
//
//      //  setSchool("SMJK Heng Yee");
//        setStatus("yy");
//        setState("Kedah");
        changeDDLDisabled();
    }

//    public List<String> get_school() {
//
//        school_list.clear();
//
//        FacesContext context = FacesContext.getCurrentInstance();
//        int count = 1;
//        String tmp = "";
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT schoolName FROM school");
//            // ResultSet rs = st.executeQuery("SELECT CSLevelID FROM assessment WHERE year='2017'");
//
//            while (rs.next()) {
//                school_list.add(rs.getString("schoolName"));
//            }
//
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
//        return school_list;
//    }
    public void main(String args[]) {
        //tryclick();
    }

}
