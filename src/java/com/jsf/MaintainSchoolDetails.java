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

public class MaintainSchoolDetails {

    private Connection con;

    private int year, commYear;
    private String schoolID, name, address, contactNo, state, status;

    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private Boolean disabledTxt, disabledCommYear, disabledStatus, disabledAddButton, disabledEditButton, disabledResetButton;

    private String addButtonName, editButtonName;

    private String testing;

    private String cslevel;
    private int ttlEnrolStud;
    private Boolean disabledEnrol;

    private int counterReset; //growl purpose

    //datatable
    private String cslevelNew;
    private int totalStudNew;
    private ArrayList cslevelList;
    private MaintainSchoolDetails totalStudObj = null;

    private int counterDataTable; //datatable

    public MaintainSchoolDetails() {
        this.counterDataTable = 0;
    }

    public String getCslevelNew() {
        return cslevelNew;
    }

    public void setCslevelNew(String cslevelNew) {
        this.cslevelNew = cslevelNew;
    }

    public int getTotalStudNew() {
        return totalStudNew;
    }

    public void setTotalStudNew(int totalStudNew) {
        this.totalStudNew = totalStudNew;
    }

    public String getCslevel() {
        return cslevel;
    }

    public void setCslevel(String cslevel) {
        this.cslevel = cslevel;
    }

    public int getTtlEnrolStud() {
        return ttlEnrolStud;
    }

    public void setTtlEnrolStud(int ttlEnrolStud) {
        this.ttlEnrolStud = ttlEnrolStud;
    }

    public Boolean getDisabledEnrol() {
        return disabledEnrol;
    }

    public void setDisabledEnrol(Boolean disabledEnrol) {
        this.disabledEnrol = disabledEnrol;
    }

    public Boolean getDisabledResetButton() {
        return disabledResetButton;
    }

    public void setDisabledResetButton(Boolean disabledResetButton) {
        this.disabledResetButton = disabledResetButton;
    }

    public String getTesting() {
        return testing;
    }

    public void setTesting(String testing) {
        this.testing = testing;
    }

    public String getAddButtonName() {
        return addButtonName;
    }

    public void setAddButtonName(String addButtonName) {
        this.addButtonName = addButtonName;
    }

    public String getEditButtonName() {
        return editButtonName;
    }

    public void setEditButtonName(String editButtonName) {
        this.editButtonName = editButtonName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCommYear() {
        return commYear;
    }

    public void setCommYear(int commYear) {
        this.commYear = commYear;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDisabledTxt() {
        return disabledTxt;
    }

    public void setDisabledTxt(Boolean disabledTxt) {
        this.disabledTxt = disabledTxt;
    }

    public Boolean getDisabledCommYear() {
        return disabledCommYear;
    }

    public void setDisabledCommYear(Boolean disabledCommYear) {
        this.disabledCommYear = disabledCommYear;
    }

    public Boolean getDisabledStatus() {
        return disabledStatus;
    }

    public void setDisabledStatus(Boolean disabledStatus) {
        this.disabledStatus = disabledStatus;
    }

    public Boolean getDisabledAddButton() {
        return disabledAddButton;
    }

    public void setDisabledAddButton(Boolean disabledAddButton) {
        this.disabledAddButton = disabledAddButton;
    }

    public Boolean getDisabledEditButton() {
        return disabledEditButton;
    }

    public void setDisabledEditButton(Boolean disabledEditButton) {
        this.disabledEditButton = disabledEditButton;
    }

    public Boolean actionSelectionDisable(String action) {

        Boolean disable = false;

        switch (action) {
            case "View":
                disable = true;
                disabledAddButton = false;
                editButtonName = "Edit";
                disabledEnrol = false;
                break;
            case "Update":
                disable = false;
                disabledAddButton = true;
                editButtonName = "Update";
                disabledEnrol = true;
                break;
        }

        return disable;
    }

    //auto generate school ID
    public String autoGenerateID() {

        int count = 0;
        String shID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM school");

            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        count = count + 1;
        shID = "SH" + Integer.toString(count);

        return shID;
    }

    //show school details by default when user select view or update in maintainschoolmenu page
    public void schoolDetailsList(String schoolID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolAddress, schoolContactNo, commYearCS, schoolStatus FROM school WHERE schoolID = ?");
            st.setString(1, schoolID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                address = rs.getString("schoolAddress");
                contactNo = rs.getString("schoolContactNo");
                commYear = rs.getInt("commYearCS");
                status = rs.getString("schoolStatus");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT year FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolID);
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

    //get year from db 
    public List<Integer> get_year() {

        FacesContext context = FacesContext.getCurrentInstance();

        year_list.clear();

        int statusOfYearLength = 0;

        int lengthYearList = get_yearCount();
//        if (lengthYearList == 0) {
//            lengthYearList = 1;
//            statusOfYearLength = 1;
//        }

        int[] yearListDuplicate = new int[lengthYearList];

        int count = 1;
        int tmp = 0;

        // yearListDuplicate[0] = 2018;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT year FROM schoolcsmap WHERE schoolID = ?");
            st.setString(1, schoolID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                yearListDuplicate[tmp] = rs.getInt("year");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        for (int i = 0; i < yearListDuplicate.length; i++) {
            System.out.println(yearListDuplicate[i]);
        }

//        if (statusOfYearLength == 1) {
//            yearListDuplicate[0] = 2018;
//            year_list.add(2018);
//        }
//        else {
        Arrays.sort(yearListDuplicate);//sorting array  
        int length = yearListDuplicate.length;
        length = removeDuplicateElements(yearListDuplicate, length);

        //printing array elements  
        for (int i = 0; i < length; i++) {
            year_list.add(yearListDuplicate[i]);
        }
//        }

        return year_list;
    }

    //change button action when click the button
    public void changeAddButtonAction() {

        switch (addButtonName) {
            case "New":
                MaintainSchoolMenu.setGlobalCounter(0);
                newSchool();
                break;
            case "Confirm":
                // ttlEnrolStud = 67890;
                addSchool();
                break;
        }
    }

    public void newSchool() {

        //reset page
        commYear = 2018;
        schoolID = autoGenerateID();
        name = null;
        address = null;
        contactNo = null;
        state = "Johor";
        status = "Active";
        disabledTxt = false;
        disabledCommYear = true;
        disabledStatus = true;

        addButtonName = "Confirm";
        disabledEditButton = true;
        disabledResetButton = false;

        disabledEnrol = true;
        ttlEnrolStud = 0;
        cslevel = null;
        year = 0;

    }

    public void addSchool() {

        status = "Active"; //due to ddl can't accept the value if user didn't select it
        int numYearComm = 0;

        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;

        if (name.isEmpty() || address.isEmpty() || contactNo.isEmpty()) {
            context.addMessage(null, new FacesMessage("Please fill in whole form!"));
        } else {

            String pattern = "-?\\d+";

            if (!contactNo.matches("-?\\d+")) { // any positive or negetive integer or not!
                context.addMessage(null, new FacesMessage("Contact Number must be in integer only! Please try again!"));
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO school (schoolID, schoolName, schoolAddress, schoolState, schoolContactNo, commYearCS, schoolStatus) VALUES (?, ?, ?, ?, ?, ?, ?)");

                    //insert school
                    statement.setString(1, schoolID);
                    statement.setString(2, name);
                    statement.setString(3, address);
                    statement.setString(4, state);
                    statement.setString(5, contactNo);
                    statement.setInt(6, commYear);
                    statement.setString(7, status);
                    statement.executeUpdate();

                    statement.close();
                    con.close();

                    verifyCounter = 1;

                    MaintainSchoolMenu.setGlobalCounter(2);

//                    MaintainSchoolMenu.setGlobalAction("View");
////                    disabledTxt = actionSelectionDisable(MaintainSchoolMenu.getGlobalAction());
//name = MaintainSchoolMenu.getGlobalAction();
//
//                    disabledTxt = true;
//                    disabledAddButton = false;
//                    disabledEditButton = false;
//                    disabledResetButton = true;
//
//                    addButtonName = "New";
//                    disabledEnrol = false;
                    counterCSLevel(numYearComm);

                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }

                switch (verifyCounter) {
                    case 0:
                        context.addMessage(null, new FacesMessage("New school " + name + " added not successful!"));
                        break;
                    case 1:
                        context.addMessage(null, new FacesMessage("New school " + name + " added successful!"));
                        break;
                }
            }
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
            st.setInt(1, numYearComm);
            st.setInt(2, commYear);
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM yearofstudycsmap WHERE numYearComm = ? AND year = ?");
            st.setInt(1, numYearComm);
            st.setInt(2, commYear);
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

    //auto generate year of study cs map ID
    public String autoGenerateSchoolCSMapID() {

        int count = 0;
        String scID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        // int lengthCommYearList = get_commYearCount();
        //   String[] schoolListDuplicate = new String[lengthCommYearList];
        int tmp = 0;

        String scID = "", shID = "", csID = "";
        int ttlStud = 0;

        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;

        for (int j = 0; j < CSIDListDuplicate.length; j++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO schoolcsmap (schoolCSMapID, ttlEnrolStud, schoolID, CSLevelID, year) VALUES (?, ?, ?, ?, ?)");

                //insert standard 1
                scID = autoGenerateSchoolCSMapID();
                ttlStud = 0;
                shID = schoolID;
                csID = CSIDListDuplicate[j];

                statement.setString(1, scID);
                statement.setInt(2, ttlStud);
                statement.setString(3, shID);
                statement.setString(4, csID);
                statement.setInt(5, commYear);
                statement.executeUpdate();

                statement.close();
                con.close();

                verifyCounter = 1;

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

//        switch (verifyCounter) {
//            case 0:
//                context.addMessage(null, new FacesMessage("School CS Map for school " + name + " automatic added not successful!"));
//                break;
//            case 1:
//                context.addMessage(null, new FacesMessage("School CS Map for school " + name + " automatic added successful!"));
//                break;
//        }
    }

    //change button action when click the button
    public void changeEditButtonAction() {

        switch (editButtonName) {
            case "Edit":
                MaintainSchoolMenu.setGlobalCounter(1);
                editSchool();
                break;
            case "Update":
                updateSchool();
                break;
        }
    }

    public void editSchool() {
        //reset page
//        disabledTxt = false;
        disabledCommYear = true;
        disabledStatus = false;
//        editButtonName = "Update";
//        disabledAddButton = true;
        MaintainSchoolMenu.setGlobalAction("Update");
        disabledTxt = actionSelectionDisable(MaintainSchoolMenu.getGlobalAction());
        disabledEnrol = true;
//        ttlEnrolStud = 0;
//        cslevel = null;
//        year = 0;
    }

    public void updateSchool() {

        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;

        int length = 0;
        String tsID = "", assID = "", csID = "", assActivityID = "", assTypeID = "";

        if (name.isEmpty() || address.isEmpty() || contactNo.isEmpty()) {
            context.addMessage(null, new FacesMessage("Please fill in whole form!"));
        } else {

            String pattern = "-?\\d+";

            if (!contactNo.matches("-?\\d+")) { // any positive or negetive integer or not!
                context.addMessage(null, new FacesMessage("Contact Number must be in integer only! Please try again!"));
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE school SET schoolName = ?, schoolAddress = ?, schoolState = ?, schoolContactNo = ?, schoolStatus = ? WHERE schoolID = ?");

                    //update school
                    statement.setString(1, name);
                    statement.setString(2, address);
                    statement.setString(3, state);
                    statement.setString(4, contactNo);
                    statement.setString(5, status);
                    statement.setString(6, schoolID);
                    statement.executeUpdate();

                    statement.close();
                    con.close();

                    verifyCounter = 1;

                    int[] yearListDuplicate = new int[2];
                    yearListDuplicate[0] = 2222;
                    yearListDuplicate[1] = 3333;

//                    testing = yearListDuplicate[0] + "\n" + yearListDuplicate[1]; //output: 2222 3333
//            for (int i = 0; i < 2; i++) {
//                testing = yearListDuplicate[i] + "x";
//            }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }

                switch (verifyCounter) {
                    case 0:
                        context.addMessage(null, new FacesMessage("Update school name " + name + " not successful!"));
                        break;
                    case 1:
                        context.addMessage(null, new FacesMessage("Update school name " + name + " successful!"));

                        disabledAddButton = false;
                        disabledEditButton = false;
                        editButtonName = "Edit";
//                        disabledTxt = true;
                        disabledStatus = true;
                        MaintainSchoolMenu.setGlobalAction("View");
                        disabledTxt = actionSelectionDisable(MaintainSchoolMenu.getGlobalAction());

                        disabledAddButton = false;
                        disabledEditButton = false;

                        disabledEnrol = false;

                        break;
                }
            }
        }
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();
        return "MaintainSchoolDetails";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

        switch (counterReset) {
            case 0:
                context.addMessage(null, new FacesMessage("Reset successful!"));
//                context.addMessage(null, new FacesMessage("x: " + MaintainSchoolMenu.getGlobalCounter()));
                break;
        }

        //set default value
        switch (MaintainSchoolMenu.getGlobalCounter()) {
            case 0: //add
//                year = 2018;
                state = "Johor";
                disabledTxt = false;
                schoolID = autoGenerateID();
                commYear = 2018;
                addButtonName = "Confirm";
                editButtonName = "Edit";
                name = null;
                address = null;
                contactNo = null;
                status = "Active";

                ttlEnrolStud = 0;
                cslevel = null;
                year = 0;

                MaintainSchoolMenu.setGlobalCounter(0);

                counterReset = 0;

                //set default disabled
                disabledStatus = true;
                disabledCommYear = true;
                disabledAddButton = false;
                disabledEditButton = true;

                disabledEnrol = true;
//                context.addMessage(null, new FacesMessage("yy"));
                break;
            case 1:
//                context.addMessage(null, new FacesMessage("zz"));
                break;

        }

    }

    //default school list when page load
    public void defaultSchoolList() {

        FacesContext context = FacesContext.getCurrentInstance();

//        context.addMessage(null, new FacesMessage("xx"));
//        context.addMessage(null, new FacesMessage("xx: " + MaintainSchoolMenu.getGlobalCounter()));
        this.disabledStatus = true;
        this.disabledCommYear = true;
//        this.year = 2018;
        this.counterReset = 0;
//        this.counterDataTable = 0;

        switch (MaintainSchoolMenu.getGlobalCounter()) {
            case 0: //add
                this.state = "Johor";
                this.disabledTxt = false;
                this.schoolID = autoGenerateID();
                this.commYear = 2018;
                this.disabledAddButton = false;
                this.disabledEditButton = true;
                this.addButtonName = "Confirm";
                this.editButtonName = "Edit";
                this.disabledResetButton = false;

                disabledEnrol = true;
                ttlEnrolStud = 0;
                cslevel = null;
                year = 0;

                MaintainSchoolMenu.setGlobalCounter(0);

                break;
            case 1: //view or update
                this.state = MaintainSchoolMenu.getGlobalState();
                this.name = MaintainSchoolMenu.getGlobalSchool();
                this.schoolID = MaintainSchoolMenu.getGlobalSchoolID();
                this.disabledEditButton = false;
                this.addButtonName = "New";
                this.disabledResetButton = true;

//                MaintainSchoolMenu.setGlobalCounter(0);
                schoolDetailsList(schoolID);

                this.disabledTxt = actionSelectionDisable(MaintainSchoolMenu.getGlobalAction());
                break;
            case 2:
                MaintainSchoolMenu.setGlobalAction("View");
//                    disabledTxt = actionSelectionDisable(MaintainSchoolMenu.getGlobalAction());
//                name = MaintainSchoolMenu.getGlobalAction();

                disabledTxt = true;
                disabledAddButton = false;
                disabledEditButton = false;
                disabledResetButton = true;

                addButtonName = "New";
                disabledEnrol = false;
                break;

            default: //direct come in without going maintainschoolmenu, so the globalcounter is null, not 0
                this.state = "Johor";
                this.disabledTxt = false;
                this.schoolID = autoGenerateID();
                this.commYear = 2018;
                this.disabledAddButton = false;
                this.disabledEditButton = true;
                this.addButtonName = "Confirm";
                this.editButtonName = "Edit";
                this.disabledResetButton = false;
//                this.counterDataTable = 0;

                MaintainSchoolMenu.setGlobalCounter(0);
        }
    }

    public void callCSLevel() {
//      get_CSLevel();

        counterDataTable = 1;

        getCslevelList();

    }

    public int retrieveCSIDCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolID = ? AND year = ?");
            st.setString(1, schoolID);
            st.setInt(2, year);
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

    //get cs level in db
    public List<String> get_CSLevel() {

        CSLevel_list.clear();

//       testing = year + schoolID;
        int CSIDListCount = retrieveCSIDCount();
        String[] CSIDList = new String[CSIDListCount];

        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM schoolcsmap WHERE schoolID = ? AND year = ?");
            st.setString(1, schoolID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                //                CSLevel_list.add(rs.getString("CSLevelID"));
                CSIDList[tmp] = rs.getString("CSLevelID");
                tmp++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        Arrays.sort(CSIDList);

        for (int i = 0; i < CSIDList.length; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
                st.setString(1, CSIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
//                    totalStudent = rs.getInt("numSampleAss");
//                    teacherIDListDuplicate[tmp1] = rs.getString("teacherID");
//                    tmp1++;
                    CSLevel_list.add(rs.getString("CSLevelName"));
                }

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        return CSLevel_list;
    }

    public String matchCSLevelID() {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM cslevel WHERE CSLevelName = ?");
            st.setString(1, cslevel);
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

    public void retrieveTtlEnrolStud() {

        String CSID = matchCSLevelID();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT ttlEnrolStud FROM schoolcsmap WHERE schoolID = ? AND year = ? AND CSLevelID = ?");
            st.setString(1, schoolID);
            st.setInt(2, year);
            st.setString(3, CSID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ttlEnrolStud = rs.getInt("ttlEnrolStud");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

    public ArrayList getCslevelList() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (counterDataTable == 1) {
//            context.addMessage(null, new FacesMessage("mmm"));
            context.addMessage(null, new FacesMessage(schoolID + year));

            int CSIDListCount = retrieveCSIDCount();

            if (CSIDListCount > 0) {
                String[] CSIDList = new String[CSIDListCount];
                int[] totalStudList = new int[CSIDListCount];

                int tmp = 0;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    PreparedStatement st = con.prepareStatement("SELECT CSLevelID, ttlEnrolStud FROM schoolcsmap WHERE schoolID = ? AND year = ?");
                    st.setString(1, schoolID);
                    st.setInt(2, year);
                    ResultSet rs = st.executeQuery();

                    while (rs.next()) {
                        //                CSLevel_list.add(rs.getString("CSLevelID"));
                        CSIDList[tmp] = rs.getString("CSLevelID");
                        totalStudList[tmp] = rs.getInt("ttlEnrolStud");
                        tmp++;
                    }

                    rs.close();
                    st.close();
                    con.close();

                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }

                Arrays.sort(CSIDList);

                String[] CSNameList = new String[CSIDListCount];

                for (int i = 0; i < CSIDList.length; i++) {

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                        PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
                        st.setString(1, CSIDList[i]);
                        ResultSet rs = st.executeQuery();

                        while (rs.next()) {
//                    CSLevel_list.add(rs.getString("CSLevelName"));
                            CSNameList[i] = rs.getString("CSLevelName");
//                         context.addMessage(null, new FacesMessage(CSNameList[i] + " : x"));
                        }

                        rs.close();
                        st.close();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println("Error: " + ex);
                    }
                }

                cslevelList = new ArrayList();
                for (int i = 0; i < CSIDListCount; i++) {
                    totalStudObj = new MaintainSchoolDetails();
                    totalStudObj.setCslevelNew(CSNameList[i]);
                    totalStudObj.setTotalStudNew(totalStudList[i]);
                    cslevelList.add(totalStudObj);
                }

            } else {
                cslevelList = new ArrayList();
                totalStudObj = new MaintainSchoolDetails();
                totalStudObj.setCslevelNew("CS Level");
                totalStudObj.setTotalStudNew(0);
                cslevelList.add(totalStudObj);
            }
//            counterDataTable = 0;
        } 
        else {
            cslevelList = new ArrayList();
            totalStudObj = new MaintainSchoolDetails();
            totalStudObj.setCslevelNew("CS Level");
            totalStudObj.setTotalStudNew(0);
            cslevelList.add(totalStudObj);
        }

        return cslevelList;
    }

}
