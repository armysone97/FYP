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

public class YearOfStudySetting {

    private Connection con;

    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private String standard1, standard2, standard3, standard4, standard5, standard6;

    private int year, yearComm;

    private Boolean disabledDDL;

    private int counterReset; //growl purpose

    public YearOfStudySetting() {
        this.year = 2018;
        this.yearComm = 2018;
        this.disabledDDL = true;
        this.counterReset = 0;
    }

    public Boolean getDisabledDDL() {
        return disabledDDL;
    }

    public void setDisabledDDL(Boolean disabledDDL) {
        this.disabledDDL = disabledDDL;
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

    //remove duplicate element for year array
    public static int removeDuplicateElements(int arr[], int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int[] temp = new int[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] != arr[i + 1]) {
                temp[j++] = arr[i];
            }
        }
        temp[j++] = arr[n - 1];
        // Changing original array  
        for (int i = 0; i < j; i++) {
            arr[i] = temp[i];
        }
        return j;
    }

    //count year in db
    public Integer get_yearCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT year FROM assessment");

            while (rs.next()) {
                count++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    //get year from db 
    public List<Integer> get_year() {
        year_list.clear();
        int lengthYearList = get_yearCount();

        int[] yearListDuplicate = new int[lengthYearList];

//        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        int tmp = 0;

        yearListDuplicate[0] = 2018;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT year FROM assessment");

            while (rs.next()) {
                tmp++;
                yearListDuplicate[tmp] = rs.getInt("year");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        for (int i = 0; i < yearListDuplicate.length; i++) {
            System.out.println(yearListDuplicate[i]);
        }

        Arrays.sort(yearListDuplicate);//sorting array  
        int length = yearListDuplicate.length;
        length = removeDuplicateElements(yearListDuplicate, length);

        //printing array elements  
        for (int i = 0; i < length; i++) {
            year_list.add(yearListDuplicate[i]);
        }
        return year_list;
    }

    //get cs level in db
    public List<String> get_CSLevel() {

        CSLevel_list.clear();

//        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public String matchCSLevelID(String csid) {

        String CSName = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
            st.setString(1, csid);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CSName = rs.getString("CSLevelName");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return CSName;
    }

    public int verifyRecord() {

        int count = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID, yearOfStudyID FROM yearofstudycsmap WHERE year = ? and numYearComm = 0");
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

    //get year of study cs map list when page onload and when button click based on year and yearComm
    public void yearOfStudyCSMapList() {

//        FacesContext context = FacesContext.getCurrentInstance();
        int tmpYear = 0, tmpyearComm;
        int numYearComm = 0;
        int verifyRecord = 0;

        String csid = "", csname = "", yosid = "";

        //when page onload, need to show previous(2017) record, 
        //so for year and yearComm 2018 which have not record in db temporaily become 2017
        if (year == 2018 && yearComm == 2018) {
            verifyRecord = verifyRecord();

            if (verifyRecord > 0) {
                tmpYear = year;
                tmpyearComm = yearComm;
                disabledDDL = true;
            } else {
                tmpYear = 2017;
                tmpyearComm = 2017;
                disabledDDL = false;
            }
        } else {
            tmpYear = year;
            tmpyearComm = yearComm;
            disabledDDL = true;
        }

        numYearComm = tmpYear - tmpyearComm;
//        context.addMessage(null, new FacesMessage(year + " : " + yearComm + " : " + tmpYear + " : " + tmpyearComm + " : " + numYearComm));

        //get data from db
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID, yearOfStudyID FROM yearofstudycsmap WHERE year = ? and numYearComm = ?");
            st.setInt(1, tmpYear);
            st.setInt(2, numYearComm);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                csid = rs.getString("CSLevelID");
                csname = matchCSLevelID(csid); //match cs id with cs name

                yosid = rs.getString("yearOfStudyID");

                switch (yosid) {
                    case "YO1":
                        standard1 = csname;
                        break;
                    case "YO2":
                        standard2 = csname;
                        break;
                    case "YO3":
                        standard3 = csname;
                        break;
                    case "YO4":
                        standard4 = csname;
                        break;
                    case "YO5":
                        standard5 = csname;
                        break;
                    case "YO6":
                        standard6 = csname;
                        break;
                }

//                context.addMessage(null, new FacesMessage("x : " + csid + " : " + csname + " : " + yosid));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    //auto generate year of study cs map ID
    public int autoGenerateID() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
            st.setString(1, standard);
            ResultSet rs = st.executeQuery();

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
        int verifyCounter = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO yearofstudycsmap (yearOfStudyCSMapID, CSLevelID, yearOfStudyID, year, numYearComm) VALUES (?, ?, ?, ?, ?)");

            //insert standard 1
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard1);
            yosID = "YO1";

//            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));
            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

            //   autoAddSchoolCSMap(numYearComm, cslevelid);
            //insert standard 2
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard2);
            yosID = "YO2";

//            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));
            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

            //   autoAddSchoolCSMap(numYearComm, cslevelid);
            //insert standard 3
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard3);
            yosID = "YO3";

//            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));
            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

            //   autoAddSchoolCSMap(numYearComm, cslevelid);
            //insert standard 4
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard4);
            yosID = "YO4";

//            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));
            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

            // autoAddSchoolCSMap(numYearComm, cslevelid);
            //insert standard 5
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard5);
            yosID = "YO5";

//            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));
            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

            // autoAddSchoolCSMap(numYearComm, cslevelid);
            //insert standard 6
            length = autoGenerateID();
            length = length + 1;
            ycID = "YC" + Integer.toString(length);
            cslevelid = matchCSLevelName(standard6);
            yosID = "YO6";

//            context.addMessage(null, new FacesMessage(ycID + " : " + cslevelid + " : " + yosID + " : " + numYearComm + " : " + year));
            statement.setString(1, ycID);
            statement.setString(2, cslevelid);
            statement.setString(3, yosID);
            statement.setInt(4, year);
            statement.setInt(5, numYearComm);
            statement.executeUpdate();

            statement.close();
            con.close();

            verifyCounter = 1;

            counterCSLevel(numYearComm);

            // autoAddSchoolCSMap(numYearComm, cslevelid);
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        switch (verifyCounter) {
            case 0:
                context.addMessage(null, new FacesMessage("Add Year Of Study CS Map Setting for year " + year + " with commercial year " + yearComm + " not successful!"));
                break;
            case 1:
                context.addMessage(null, new FacesMessage("Add Year Of Study CS Map Setting for year " + year + " with commercial year " + yearComm + " successful!"));
                break;
        }
    }

    //remove duplicate element for cs level id array
    public static int removeDuplicateElementsString(String arr[], int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        String[] temp = new String[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (!arr[i].equals(arr[i + 1])) {
                temp[j++] = arr[i];
            }
        }
        temp[j++] = arr[n - 1];

        // Changing original array  
        for (int i = 0; i < j; i++) {
            arr[i] = temp[i];
        }
        return j;
    }

    //count csID in db
    public int get_csCount(int numYearComm) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
            st.setInt(1, numYearComm);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    public void counterCSLevel(int numYearComm) {

        int lengthCSIDList = get_csCount(numYearComm);

        String[] CSIDListDuplicate = new String[lengthCSIDList];
        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
            st.setInt(1, numYearComm);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                CSIDListDuplicate[tmp] = rs.getString("CSLevelID");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        Arrays.sort(CSIDListDuplicate);//sorting array  
        int length = CSIDListDuplicate.length;
        length = removeDuplicateElementsString(CSIDListDuplicate, length);

        autoAddSchoolCSMap(numYearComm, CSIDListDuplicate);
    }

    //count schoolid in db
    public int get_commYearCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE commYearCS = ?");
            st.setInt(1, yearComm);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                count++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return count;
    }

    //auto generate year of study cs map ID
    public String autoGenerateSchoolCSMapID() {

        int count = 0;
        String scID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM schoolcsmap");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        count++;

        scID = "SC" + count;

        return scID;
    }

    //auto add school cs map in db based on commercial year
    public void autoAddSchoolCSMap(int numYearComm, String[] CSIDListDuplicate) { //if need redirect to another xhtml, need change void to String and return keyword

        //1. find commYear (eg. 2016) from school table and compare with numYearComm
        int lengthCommYearList = get_commYearCount();

        String[] schoolListDuplicate = new String[lengthCommYearList];
        int tmp = 0;
        int verifyCounter = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE commYearCS = ?");
            st.setInt(1, yearComm);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolListDuplicate[tmp] = rs.getString("schoolID");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String scID = "", shID = "", csID = "";
        int ttlStud = 0;

        for (int i = 0; i < schoolListDuplicate.length; i++) {
            for (int j = 0; j < CSIDListDuplicate.length; j++) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO schoolcsmap (schoolCSMapID, ttlEnrolStud, schoolID, CSLevelID, year) VALUES (?, ?, ?, ?, ?)");

                    //insert standard 1
                    scID = autoGenerateSchoolCSMapID();
                    ttlStud = 0;
                    shID = schoolListDuplicate[i];
                    csID = CSIDListDuplicate[j];

                    statement.setString(1, scID);
                    statement.setInt(2, ttlStud);
                    statement.setString(3, shID);
                    statement.setString(4, csID);
                    statement.setInt(5, year);
                    statement.executeUpdate();

                    verifyCounter = 1;

                    statement.close();
                    con.close();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }
            }

        }

        FacesContext context = FacesContext.getCurrentInstance();

         switch (verifyCounter) {
            case 0:
                context.addMessage(null, new FacesMessage("Add School CS Map Setting for year " + year + " with commercial year " + yearComm + " not successful!"));
                break;
            case 1:
                context.addMessage(null, new FacesMessage("Add School CS Map Setting for year " + year + " with commercial year " + yearComm + " successful!"));
                break;
        }

    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();
        return "YearOfStudySetting";
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
        yearComm = 2018;
        standard1 = "CS Level 1";
        standard2 = "CS Level 1";
        standard3 = "CS Level 1";
        standard4 = "CS Level 1";
        standard5 = "CS Level 1";
        standard6 = "CS Level 1";

        counterReset = 0;

        //set default disabled
        disabledDDL = false;

    }

    public void main(String args[]) {

    }
}
