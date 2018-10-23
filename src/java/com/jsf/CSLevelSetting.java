/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

public class CSLevelSetting {

    private Connection con;
    private String year;

    private List<String> year_list = new ArrayList<>(); //year list that retrieve from db
    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db

    private String CSNo1, CSNo2, CSNo3, CSNo4, CSNo5, CSNo6, CSNo7, CSNo8, CSNo9, CSNo10;
    public String[] assessmentList;

    private int countClick; //count button click how many times
    private String newCSLevel;
    private Boolean disabledNewCSLevel;
    private int rowNum;

    public CSLevelSetting() {
        this.countClick = 0;
        this.disabledNewCSLevel = false;
        this.rowNum = 0;
    }

    public String[] getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(String[] assessmentList) {
        this.assessmentList = assessmentList;
    }

    public String getAssessmentList1() {
        return Arrays.toString(assessmentList);
    }

    public String getNewCSLevel() {
        return newCSLevel;
    }

    public void setNewCSLevel(String newCSLevel) {
        this.newCSLevel = newCSLevel;
    }

    public Boolean getDisabledNewCSLevel() {
        return disabledNewCSLevel;
    }

    public void setDisabledNewCSLevel(Boolean disabledNewCSLevel) {
        this.disabledNewCSLevel = disabledNewCSLevel;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getCSNo1() {
        return CSNo1;
    }

    public void setCSNo1(String CSNo1) {
        this.CSNo1 = CSNo1;
    }

    public String getCSNo2() {
        return CSNo2;
    }

    public void setCSNo2(String CSNo2) {
        this.CSNo2 = CSNo2;
    }

    public String getCSNo3() {
        return CSNo3;
    }

    public void setCSNo3(String CSNo3) {
        this.CSNo3 = CSNo3;
    }

    public String getCSNo4() {
        return CSNo4;
    }

    public void setCSNo4(String CSNo4) {
        this.CSNo4 = CSNo4;
    }

    public String getCSNo5() {
        return CSNo5;
    }

    public void setCSNo5(String CSNo5) {
        this.CSNo5 = CSNo5;
    }

    public String getCSNo6() {
        return CSNo6;
    }

    public void setCSNo6(String CSNo6) {
        this.CSNo6 = CSNo6;
    }

    public String getCSNo7() {
        return CSNo7;
    }

    public void setCSNo7(String CSNo7) {
        this.CSNo7 = CSNo7;
    }

    public String getCSNo8() {
        return CSNo8;
    }

    public void setCSNo8(String CSNo8) {
        this.CSNo8 = CSNo8;
    }

    public String getCSNo9() {
        return CSNo9;
    }

    public void setCSNo9(String CSNo9) {
        this.CSNo9 = CSNo9;
    }
    
    public String getCSNo10() {
        return CSNo10;
    }

    public void setCSNo10(String CSNo10) {
        this.CSNo10 = CSNo10;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> get_year() {
        year_list.clear();
        rowNum = 0;

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT roleType FROM roles");

            while (rs.next()) {
                year_list.add(rs.getString("roleType"));
            //    rowNum++;
            //    setRowNum(rowNum);
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return year_list;
    }

    public List<String> get_CSLevel() {

        CSLevel_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT CSLevelName FROM cslevel");

            while (rs.next()) {
                CSLevel_list.add(rs.getString("CSLevelName"));
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return CSLevel_list;
    }

    public void addCS() {
        setYear("Admin");
      
        setNewCSLevel(assessmentList[2]); //checkbox value
    
    }

    //default set the dropdownlist choice
    public void addCS1() {
        setYear("Master Trainer");
         setNewCSLevel(Integer.toString(rowNum)); 
    }

    //change button name when click the button
    public String changeButtonName() {

        String clickName = "";

        if (countClick == 0) {
            countClick++;
            clickName = "Add new CS Level";
        } else if (countClick == 1) {
            countClick--;
            clickName = "Confirm";
        }

        return clickName;

    }

    //change button action when click the button
    public void changeButtonAction() {
        if (countClick == 0) {
            addCS();
        } else if (countClick == 1) {
            addCS1();
        }
    }

    public Boolean changeTextboxDisabled() {
        if (countClick == 0) {
            disabledNewCSLevel = false;
        } else if (countClick == 1) {
            disabledNewCSLevel = true;
        }

        return disabledNewCSLevel;
    }
    
    public void addRowNum(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        rowNum++;
        context.addMessage(null, new FacesMessage("success" + rowNum));
        setRowNum(rowNum);
        
    }

    public void main(String args[]) {
//        addCS();
        addRowNum();
    }
}
