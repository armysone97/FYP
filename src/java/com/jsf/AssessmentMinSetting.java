/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.CSLevelSetting.removeDuplicateElements;
import static com.jsf.MaintainAssessmentTask.removeDuplicateElementsString;
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

public class AssessmentMinSetting {

    private Connection con;

    private int year;
    private String cslevel;
    private int project, collaboration, practical, groupwork;
    
    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
     private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private Boolean disabledDDL, disabledProject, disabledCollaboration, disabledPractical, disabledGroupwork;

    public AssessmentMinSetting() {
        this.year = 2018;
        this.disabledDDL = false;
        this.disabledProject = false;
        this.disabledCollaboration = false;
        this.disabledPractical = false;
        this.disabledGroupwork = false;
    }

    public Boolean getDisabledProject() {
        return disabledProject;
    }

    public void setDisabledProject(Boolean disabledProject) {
        this.disabledProject = disabledProject;
    }

    public Boolean getDisabledCollaboration() {
        return disabledCollaboration;
    }

    public void setDisabledCollaboration(Boolean disabledCollaboration) {
        this.disabledCollaboration = disabledCollaboration;
    }

    public Boolean getDisabledPractical() {
        return disabledPractical;
    }

    public void setDisabledPractical(Boolean disabledPractical) {
        this.disabledPractical = disabledPractical;
    }

    public Boolean getDisabledGroupwork() {
        return disabledGroupwork;
    }

    public void setDisabledGroupwork(Boolean disabledGroupwork) {
        this.disabledGroupwork = disabledGroupwork;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCslevel() {
        return cslevel;
    }

    public void setCslevel(String cslevel) {
        this.cslevel = cslevel;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(int collaboration) {
        this.collaboration = collaboration;
    }

    public int getPractical() {
        return practical;
    }

    public void setPractical(int practical) {
        this.practical = practical;
    }

    public int getGroupwork() {
        return groupwork;
    }

    public void setGroupwork(int groupwork) {
        this.groupwork = groupwork;
    }


    //   change text box disabled when click the button
    public Boolean changeDDLDisabled() {
        if (year == 2018) {
            disabledDDL = false;
        } else {
            disabledDDL = true;
        }

        return disabledDDL;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        int tmp = 0;

        yearListDuplicate[0] = 2018;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //use in function get_CSLevel(), to count the cs level id in database
    public int retrieveCSLevelIDCount(int year) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM assessment WHERE year = ?");
            st.setInt(1, year);
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

    //get cs level in db
    public List<String> get_CSLevel() {

        CSLevel_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();

        String tmp = "";

        int CSLevelIDListCount = retrieveCSLevelIDCount(year);
        String[] CSLevelIDListDuplicate = new String[CSLevelIDListCount];
        int tmpCount = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM assessment WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // CSLevel_list.add(rs.getString("CSLevelID"));
                CSLevelIDListDuplicate[tmpCount] = rs.getString("CSLevelID");
                tmpCount++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //sorting array 
        Arrays.sort(CSLevelIDListDuplicate);

        int length = CSLevelIDListDuplicate.length;

        //this function is to remove duplicate elements in array named "teacherIDListDuplicate"
        length = removeDuplicateElementsString(CSLevelIDListDuplicate, length);

        for (int i = 0; i < length; i++) {
            //CSLevel_list.add(CSLevelIDListDuplicate[i]);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT CSLevelName FROM cslevel WHERE CSLevelID = ?");
                st.setString(1, CSLevelIDListDuplicate[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    CSLevel_list.add(rs.getString("CSLevelName"));
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

        }

        return CSLevel_list;
    }

    public String matchAssID(String CSID, String assActivityID, int tmpYear) {

        String assID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT assID FROM assessment WHERE year = ? AND CSLevelID = ? AND assActivityID = ?");
            st.setInt(1, tmpYear);
            st.setString(2, CSID);
            st.setString(3, assActivityID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // CSLevel_list.add(rs.getString("CSLevelID"));
                assID = rs.getString("assID");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return assID;
    }

    public String matchCSLevelName(String cslevel, String assActivityID, int tmpYear) {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        return matchAssID(CSID, assActivityID, tmpYear);

    }

    public String matchCSLevelID() {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public int matchMinPerStud(String assID) {

        int minPerStud = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT minPerStud FROM assessment WHERE assID = ?");
            st.setString(1, assID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                minPerStud = rs.getInt("minPerStud");
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return minPerStud;
    }

    //get assessment list when page onload and when button click based on year and cs level
    public void assessmentList() {

        FacesContext context = FacesContext.getCurrentInstance();

        int tmpYear = 0, tmpyearComm;
        int numYearComm = 0;

        String csid = "", assactivityid = "", assidP = "", assid = "";
        int minPerStud = 0;

        //when page onload, need to show previous(2017) record, so for year and yearComm 2018 temporaily become 2017
        if (year == 2018) {
            tmpYear = 2017;
            disabledProject = false;
            disabledCollaboration = false;
            disabledPractical = false;
            disabledGroupwork = false;

        } else {
            tmpYear = year;
            disabledProject = true;
            disabledCollaboration = true;
            disabledPractical = true;
            disabledGroupwork = true;
        }

        //get project minPerStud
        csid = matchCSLevelID();
        assactivityid = "AA1";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        assid = matchAssID(csid, assactivityid, year);
        minPerStud = 0;

        if (!assid.isEmpty()) {
            minPerStud = matchMinPerStud(assidP);
        }else {
                disabledProject = true;
        }

        project = minPerStud;

        //get collaboration minPerStud
        csid = matchCSLevelID();
        assactivityid = "AA2";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        assid = matchAssID(csid, assactivityid, year);
        minPerStud = 0;

        if (!assid.isEmpty()) {
            minPerStud = matchMinPerStud(assidP);
        }else {
                disabledCollaboration = true;
        }

        collaboration = minPerStud;

        //get practical minPerStud
        csid = matchCSLevelID();
        assactivityid = "AA3";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        assid = matchAssID(csid, assactivityid, year);
        minPerStud = 0;

        if (!assid.isEmpty()) {
            minPerStud = matchMinPerStud(assidP);
        }else {
                disabledPractical = true;
        }

        practical = minPerStud;

        //get groupwork minPerStud
        csid = matchCSLevelID();
        assactivityid = "AA4";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        assid = matchAssID(csid, assactivityid, year);
        minPerStud = 0;

        if (!assid.isEmpty()) {
            minPerStud = matchMinPerStud(assidP);
        }else {
                disabledGroupwork = true;
        }

        groupwork = minPerStud;

        //   practical = csid + " : " + assactivityid + " : " + assid;
    }

    //add year of study cs map in db
    public void updateMinPerStud() { //if need redirect to another xhtml, need change void to String and return keyword

        FacesContext context = FacesContext.getCurrentInstance();

        int length = 0;
        String tsID = "", assID = "", csID = "", assActivityID = "", assTypeID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("UPDATE assessment SET minPerStud = ? WHERE assID = ?");

            //update project
            assID = matchCSLevelName(cslevel, "AA1", year);

            statement.setDouble(1, project);
            statement.setString(2, assID);
            statement.executeUpdate();

            //update collaboration
            assID = matchCSLevelName(cslevel, "AA2", year);

           statement.setDouble(1, collaboration);
            statement.setString(2, assID);
            statement.executeUpdate();

            //update practical
            assID = matchCSLevelName(cslevel, "AA3", year);

            statement.setDouble(1, practical);
            statement.setString(2, assID);
            statement.executeUpdate();

            //update groupwork
            assID = matchCSLevelName(cslevel, "AA4", year);

             statement.setDouble(1, groupwork);
            statement.setString(2, assID);
            statement.executeUpdate();

            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

}
