/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
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
    private String mtHourlyRate, evHourlyRate, mileageRate;

    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private Boolean disabledTxt;

    private int counterReset; //growl purpose

    public RateSetting() {
        //system date
        String systemYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int yearSystem = Integer.valueOf(systemYear);

        this.year = yearSystem;
        this.disabledTxt = true;
        this.counterReset = 0;
        this.mtHourlyRate = "0.00";
        this.evHourlyRate = "0.00";
        this.mileageRate = "0.00";
        settingList();
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

    public String getMtHourlyRate() {
        return mtHourlyRate;
    }

    public void setMtHourlyRate(String mtHourlyRate) {
        this.mtHourlyRate = mtHourlyRate;
    }

    public String getEvHourlyRate() {
        return evHourlyRate;
    }

    public void setEvHourlyRate(String evHourlyRate) {
        this.evHourlyRate = evHourlyRate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMileageRate() {
        return mileageRate;
    }

    public void setMileageRate(String mileageRate) {
        this.mileageRate = mileageRate;
    }

    //count year in db
    public Integer get_yearCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        String systemYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT rateID FROM rate WHERE year = ?");
            st.setString(1, systemYear);
            ResultSet rs = st.executeQuery();

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

        String systemYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        Boolean verifyYear = verifyYear();

        if (verifyYear) { //system year got inside
            lengthYearListEnhance = lengthYearList;
        } else {
            lengthYearListEnhance = lengthYearList + 1;
        }

        int[] yearListDuplicate = new int[lengthYearListEnhance];
        int tmp = 0;

        if (verifyYear) { //2018 got inside
        } else {
            int yearSystem = Integer.valueOf(systemYear);
            yearListDuplicate[0] = yearSystem;
            tmp++;
        }

        int count = 1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        String systemYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int yearSystem = Integer.valueOf(systemYear);

        //when page onload, need to show previous record, so for year and yearComm temporaily become previous year
        if (year == yearSystem) {
            verifyRecord = verifyRecord();
            if (verifyRecord > 0) { //means got data inside
                tmpYear = year;
                disabledTxt = true;
            } else {
                tmpYear = yearSystem - 1;
                disabledTxt = false;
            }
        } else {
            tmpYear = year;
            disabledTxt = true;
        }

        //get data from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT numSampleAss, mtHourlyRate, evHourlyRate, mileageRate FROM rate WHERE year = ?");
            st.setInt(1, tmpYear);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                numSampleAss = rs.getInt("numSampleAss");
                mtHourlyRate = String.format("%.2f", rs.getDouble("mtHourlyRate"));
                evHourlyRate = String.format("%.2f", rs.getDouble("evHourlyRate"));
                mileageRate = String.format("%.2f", rs.getDouble("mileageRate"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    //auto generate rate ID
    public int autoGenerateID() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        FacesContext context = FacesContext.getCurrentInstance();

        int length = 0;
        String rtID = "";

        int verifyCounter = 0;
        boolean verifyFormat = false;

        String regexStr = "^[0-9\\.]*$";

        if (numSampleAss == 0 || mtHourlyRate.isEmpty() || evHourlyRate.isEmpty() || mileageRate.isEmpty()) {
            context.addMessage(null, new FacesMessage("Please fill in whole form! "));
        } else { //verify double and integer only
            if (!mtHourlyRate.matches("\\d+") || !evHourlyRate.matches("\\d+") || !mileageRate.matches("\\d+")) { //integer
                if (!mtHourlyRate.matches(regexStr) || !evHourlyRate.matches(regexStr) || !mileageRate.matches(regexStr)) { //double
                    verifyFormat = false;
                } else {
                    verifyFormat = true;
                }
            } else {
                verifyFormat = true;
            }

            String mtHourlyRateLength = "0";
            String evHourlyRateLength = "0";
            String mileageRateLength = "0";

            if (verifyFormat) { //true
                if (numSampleAss == 0 || Double.valueOf(mtHourlyRate) == 0 || Double.valueOf(evHourlyRate) == 0 || Double.valueOf(mileageRate) == 0) {
                    context.addMessage(null, new FacesMessage("Please fill in whole form!"));
                } else {

                    if (!mtHourlyRate.matches("\\d+")) { //verify integer only, if not integer only, means double
                        //verify digit after decimal point, money maximum 2 decimal places
                        String[] parts = mtHourlyRate.split("\\.");
                        mtHourlyRateLength = parts[1];
                    }

                    if (!evHourlyRate.matches("\\d+")) { //verify integer only, if not integer only, means double
                        String[] parts1 = evHourlyRate.split("\\.");
                        evHourlyRateLength = parts1[1];
                    }

                    if (!mileageRate.matches("\\d+")) { //verify integer only, if not integer only, means double
                        String[] parts2 = mileageRate.split("\\.");
                        mileageRateLength = parts2[1];
                    }

                    if (mtHourlyRateLength.length() > 2 || evHourlyRateLength.length() > 2 || mileageRateLength.length() > 2) {
                        context.addMessage(null, new FacesMessage("RM must at most 2 decimal places only! Please try again!"));
                    } else {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO rate (rateID, numSampleAss, mtHourlyRate, evHourlyRate, mileageRate, year) VALUES (?, ?, ?, ?, ?, ?)");

                            //insert rate setting
                            length = autoGenerateID();
                            length = length + 1;
                            rtID = "RT" + Integer.toString(length);

                            statement.setString(1, rtID);
                            statement.setInt(2, numSampleAss);
                            statement.setDouble(3, Double.valueOf(mtHourlyRate));
                            statement.setDouble(4, Double.valueOf(evHourlyRate));
                            statement.setDouble(5, Double.valueOf(mileageRate));
                            statement.setInt(6, year);

                            statement.executeUpdate();
                            statement.close();
                            con.close();

                            verifyCounter = 1;

                        } catch (Exception ex) {
                            System.out.println("Error: " + ex);
                        }

                        switch (verifyCounter) {
                            case 0:
                                context.addMessage(null, new FacesMessage("Add Rate Setting for year " + year + " not successful!"));
                                break;
                            case 1:
                                context.addMessage(null, new FacesMessage("Add Rate Setting for year " + year + " successful!"));
                                disabledTxt = true;
                                break;
                        }
                    }

                }

            } else {
                context.addMessage(null, new FacesMessage("Format Incorrect, please check again!"));
            }
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

        String systemYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int yearSystem = Integer.valueOf(systemYear);

        //set default value
        year = yearSystem;
        numSampleAss = 0;
        mtHourlyRate = "0.00";
        evHourlyRate = "0.00";
        mileageRate = "0.00";

        counterReset = 0;

        MaintainSchoolMenu.setGlobalCounter(0);

        //set default disabled
        disabledTxt = true;

        settingList();
    }

    //valuechangelistener purpose
    public void yearChanged() {
        settingList();
    }

}
