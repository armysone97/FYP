/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.CSLevelSetting.removeDuplicateElements;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped
//@ViewScoped

public class MaintainSchoolMenu {

    private Connection con;

    private String action, state, school;

    private List<String> school_list = new ArrayList<>();
    private List<String> state_list = new ArrayList<>();

    private static int globalCounter;
    private static String globalState, globalSchool, globalAction, globalSchoolID;

    public MaintainSchoolMenu() {
        this.state = "Pulau Pinang";
        this.globalCounter = 0;
        this.action = "View";
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public static int getGlobalCounter() {
        return globalCounter;
    }

    public static void setGlobalCounter(int globalCounter) {
        MaintainSchoolMenu.globalCounter = globalCounter;
    }

    public static String getGlobalState() {
        return globalState;
    }

    public static void setGlobalState(String globalState) {
        MaintainSchoolMenu.globalState = globalState;
    }

    public static String getGlobalSchool() {
        return globalSchool;
    }

    public static void setGlobalSchool(String globalSchool) {
        MaintainSchoolMenu.globalSchool = globalSchool;
    }

    public static String getGlobalAction() {
        return globalAction;
    }

    public static void setGlobalAction(String globalAction) {
        MaintainSchoolMenu.globalAction = globalAction;
    }

    public static String getGlobalSchoolID() {
        return globalSchoolID;
    }

    public static void setGlobalSchoolID(String globalSchoolID) {
        MaintainSchoolMenu.globalSchoolID = globalSchoolID;
    }

    //remove duplicate element for teacher name array
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

    //count state in db
    public Integer get_stateCount() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT schoolState FROM school");

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
    public List<String> get_state() {

        state_list.clear();

        int lengthYearList = get_stateCount();

        String[] stateListDuplicate = new String[lengthYearList];

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        int tmp = 0;

        // stateListDuplicate[0] = 2018;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT schoolState FROM school");

            while (rs.next()) {
                stateListDuplicate[tmp] = rs.getString("schoolState");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        Arrays.sort(stateListDuplicate);//sorting array  
        int length = stateListDuplicate.length;
        length = removeDuplicateElementsString(stateListDuplicate, length);

        //printing array elements  
        for (int i = 0; i < length; i++) {
            state_list.add(stateListDuplicate[i]);
        }
        return state_list;
    }

    //get school list and put inside ddl based on the state that is selected by user in ddl
    public List<String> get_school() {

        school_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolName FROM school WHERE schoolState = ?");
            st.setString(1, state);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                school_list.add(rs.getString("schoolName"));
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return school_list;
    }

    public String nextPageToSchoolDetail() {

        String nextPage = "MaintainSchoolDetails"; //link to next page purpose

        globalAction = action;
        globalSchool = school;
        globalState = state;
        globalCounter = 1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolState = ? AND schoolName = ?");
            st.setString(1, state);
            st.setString(2, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                globalSchoolID = rs.getString("schoolID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return nextPage;
    }
    
     //navigation bar purpose
    public String goToNextPage() {
        reset();
        return "MaintainSchoolMenu";
    }

    //reset page
    public void reset() {

        //set default value
        state = "Pulau Pinang";
        globalCounter = 0;
        action = "View";
    }

}
