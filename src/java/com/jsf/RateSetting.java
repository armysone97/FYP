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
import java.util.Arrays;
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

public class RateSetting {

    private Connection con;

    private int numSampleAss, year;
    private double mtHourlyRate, evHourlyRate, mileageRate;

    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private Boolean disabledTxt;
    
    private int counterReset; //growl purpose

    public RateSetting() {
        this.year = 2018;
        this.disabledTxt = true;
        this.counterReset = 0;
    }

    public Boolean getDisabledTxt() {
        return disabledTxt;
    }

    public void setDisabledTxt(Boolean disabledTxt) {
        this.disabledTxt = disabledTxt;
    }

    public int getNumSampleAss() {
        return numSampleAss;
    }

    public void setNumSampleAss(int numSampleAss) {
        this.numSampleAss = numSampleAss;
    }

    public double getMtHourlyRate() {
        return mtHourlyRate;
    }

    public void setMtHourlyRate(double mtHourlyRate) {
        this.mtHourlyRate = mtHourlyRate;
    }

    public double getEvHourlyRate() {
        return evHourlyRate;
    }

    public void setEvHourlyRate(double evHourlyRate) {
        this.evHourlyRate = evHourlyRate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMileageRate() {
        return mileageRate;
    }

    public void setMileageRate(double mileageRate) {
        this.mileageRate = mileageRate;
    }

    //count year in db
    public Integer get_yearCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM rate");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    public Boolean verifyYear() {

        Boolean verify = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT rateID FROM rate WHERE year = '2018'");

            while (rs.next()) {

                verify = true;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return verify;

    }

    //get year from db 
    public List<Integer> get_year() {
        year_list.clear();
        int lengthYearList = get_yearCount();
        int lengthYearListEnhance = 0;

        Boolean verifyYear = verifyYear();

        if (verifyYear) { //2018 got inside
            lengthYearListEnhance = lengthYearList;
        } else {
            lengthYearListEnhance = lengthYearList + 1;
        }

        int[] yearListDuplicate = new int[lengthYearListEnhance];
        int tmp = 0;

        if (verifyYear) { //2018 got inside
            //    lengthYearListEnhance = lengthYearList;
        } else {
            yearListDuplicate[0] = 2018;
            tmp++;
        }

        int count = 1;
        

//        yearListDuplicate[0] = 2018;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT year FROM rate");

            while (rs.next()) {
                yearListDuplicate[tmp] = rs.getInt("year");
                 tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        Arrays.sort(yearListDuplicate);//sorting array  

        //printing array elements  
        for (int i = 0; i < yearListDuplicate.length; i++) {
            year_list.add(yearListDuplicate[i]);
        }
        return year_list;
    }

    public int verifyRecord() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT numSampleAss, mtHourlyRate, evHourlyRate, mileageRate FROM rate WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    public void settingList() {
        int tmpYear = 0;

        int verifyRecord = 0;

        //when page onload, need to show previous(2017) record, so for year and yearComm 2018 temporaily become 2017
        if (year == 2018) {

            verifyRecord = verifyRecord();

            if (verifyRecord > 0) { //means got data inside
                tmpYear = year;
                disabledTxt = true;
            } else {
                tmpYear = 2017;
                disabledTxt = false;
            }
        } else {
            tmpYear = year;
            disabledTxt = true;
        }

        //get data from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT numSampleAss, mtHourlyRate, evHourlyRate, mileageRate FROM rate WHERE year = ?");
            st.setInt(1, tmpYear);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                numSampleAss = rs.getInt("numSampleAss");
                mtHourlyRate = rs.getDouble("mtHourlyRate");
                evHourlyRate = rs.getDouble("evHourlyRate");
                mileageRate = rs.getDouble("mileageRate");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        // changeDDLDisabled();
    }

    //auto generate rate ID
    public int autoGenerateID() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM rate");

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

    public void addSetting() {

        int length = 0;
        String rtID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO rate (rateID, numSampleAss, mtHourlyRate, evHourlyRate, mileageRate, year) VALUES (?, ?, ?, ?, ?, ?)");

            //insert rate setting
            length = autoGenerateID();
            length = length + 1;
            rtID = "RT" + Integer.toString(length);

            statement.setString(1, rtID);
            statement.setInt(2, numSampleAss);
            statement.setDouble(3, mtHourlyRate);
            statement.setDouble(4, evHourlyRate);
            statement.setDouble(5, mileageRate);
            statement.setInt(6, year);

            statement.executeUpdate();
            statement.close();
            con.close();
            
            disabledTxt = true;

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }
    
     //navigation bar purpose
    public String goToNextPage() {
        
        counterReset = 1;
        
        reset();
        return "RateSetting";
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
        year = 2018;
        numSampleAss = 0;
        mtHourlyRate = 0;
        evHourlyRate = 0;
        mileageRate = 0;
        
        counterReset = 0;

        //set default disabled
        disabledTxt = true;
    }

}
