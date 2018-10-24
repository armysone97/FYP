/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

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
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class YearOfStudySetting {

    private Connection con;

    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private String standard1, standard2, standard3, standard4, standard5, standard6;

    private int year, yearComm;

    public YearOfStudySetting() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYearComm() {
        return yearComm;
    }

    public void setYearComm(int yearComm) {
        this.yearComm = yearComm;
    }

    public String getStandard1() {
        return standard1;
    }

    public void setStandard1(String standard1) {
        this.standard1 = standard1;
    }

    public String getStandard2() {
        return standard2;
    }

    public void setStandard2(String standard2) {
        this.standard2 = standard2;
    }

    public String getStandard3() {
        return standard3;
    }

    public void setStandard3(String standard3) {
        this.standard3 = standard3;
    }

    public String getStandard4() {
        return standard4;
    }

    public void setStandard4(String standard4) {
        this.standard4 = standard4;
    }

    public String getStandard5() {
        return standard5;
    }

    public void setStandard5(String standard5) {
        this.standard5 = standard5;
    }

    public String getStandard6() {
        return standard6;
    }

    public void setStandard6(String standard6) {
        this.standard6 = standard6;
    }

    //get cs level in db
    public List<String> get_CSLevel() {

        CSLevel_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT CSLevelName FROM cslevel");
            // ResultSet rs = st.executeQuery("SELECT CSLevelID FROM assessment WHERE year='2017'");

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

    //auto generate year of study cs map ID
    public int autoGenerateID() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM yearofstudycsmap");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    public String matchCSLevelName(String standard) {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            //     Statement st = con.createStatement();
            //    ResultSet rs = st.executeQuery("SELECT CSLevelName FROM cslevel");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
            st.setString(1, standard);
            ResultSet rs = st.executeQuery();
            // ResultSet rs = st.executeQuery("SELECT CSLevelID FROM assessment WHERE year='2017'");

            while (rs.next()) {
                CSID = rs.getString("CSLevelID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return CSID;

    }

    //add year of study cs map in db
    public void addYearOfStudyCSMap() { //if need redirect to another xhtml, need change void to String and return keyword

        FacesContext context = FacesContext.getCurrentInstance();

        int length = 0;

        int numYearComm = year - yearComm;
        String ycID = "", cslevelid = "", yosID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO yearofstudycsmap (yearOfStudyCSMapID, CSLevelID, yearOfStudyID, year, numYearComm) VALUES (?, ?, ?, ?, ?)");

            //insert standard 1
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard1);
            yosID = "YO1";

            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));

            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

             //insert standard 2
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard2);
            yosID = "YO2";

            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));

            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();
            
               //insert standard 3
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard3);
            yosID = "YO3";

            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));

            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();
            
               //insert standard 4
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard4);
            yosID = "YO4";

            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));

            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();
            
               //insert standard 5
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard5);
            yosID = "YO5";

            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));

            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();
            
               //insert standard 6
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard6);
            yosID = "YO6";

            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));

            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();
            
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void main(String args[]) {

    }
}
