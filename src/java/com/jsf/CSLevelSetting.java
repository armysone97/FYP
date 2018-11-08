/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.AssessmentTaskSetting.removeDuplicateElementsString;
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

public class CSLevelSetting {

    private Connection con;

    private int year;
    private String cslevel;
    private Boolean project, collaboration, practical, groupwork;

    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db
    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db

    private Boolean disabledDDL, disabledProject, disabledCollaboration, disabledPractical, disabledGroupwork;
    private Boolean disabledNewCS, disabledButton;
    private Boolean disabledReset;

    private String newcslevelname, newcslevelid;

    private int counterReset; //growl purpose

    public CSLevelSetting() {
        this.year = 2018;
        this.disabledDDL = false;
        this.disabledProject = true;
        this.disabledCollaboration = true;
        this.disabledPractical = true;
        this.disabledGroupwork = true;
        this.disabledReset = true;
        this.disabledNewCS = true;
        this.disabledButton = false;
        this.counterReset = 0;
    }

    public Boolean getDisabledReset() {
        return disabledReset;
    }

    public void setDisabledReset(Boolean disabledReset) {
        this.disabledReset = disabledReset;
    }

    public Boolean getDisabledNewCS() {
        return disabledNewCS;
    }

    public void setDisabledNewCS(Boolean disabledNewCS) {
        this.disabledNewCS = disabledNewCS;
    }

    public Boolean getDisabledButton() {
        return disabledButton;
    }

    public void setDisabledButton(Boolean disabledButton) {
        this.disabledButton = disabledButton;
    }

    public String getNewcslevelname() {
        return newcslevelname;
    }

    public void setNewcslevelname(String newcslevelname) {
        this.newcslevelname = newcslevelname;
    }

    public String getNewcslevelid() {
        return newcslevelid;
    }

    public void setNewcslevelid(String newcslevelid) {
        this.newcslevelid = newcslevelid;
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

    public Boolean getProject() {
        return project;
    }

    public void setProject(Boolean project) {
        this.project = project;
    }

    public Boolean getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(Boolean collaboration) {
        this.collaboration = collaboration;
    }

    public Boolean getPractical() {
        return practical;
    }

    public void setPractical(Boolean practical) {
        this.practical = practical;
    }

    public Boolean getGroupwork() {
        return groupwork;
    }

    public void setGroupwork(Boolean groupwork) {
        this.groupwork = groupwork;
    }

    public Boolean getDisabledDDL() {
        return disabledDDL;
    }

    public void setDisabledDDL(Boolean disabledDDL) {
        this.disabledDDL = disabledDDL;
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

        FacesContext context = FacesContext.getCurrentInstance();
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

    //use in function get_CSLevel(), to count the cs level id in database
    public int retrieveCSLevelIDCount(int year) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
        int tmpCount = 0, tmpYear = 0;

        //when page onload, need to show previous(2017) record, so for year and yearComm 2018 temporaily become 2017
        if (year == 2018) {
            tmpYear = 2017;

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

        } else {
            tmpYear = year;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

        }

        return CSLevel_list;
    }

    public String matchAssID(String CSID, String assActivityID, int tmpYear) {

        String assID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public String matchCSLevelID() {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    public int verifyRecord(String CSID) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT assID FROM assessment WHERE year = ? AND CSLevelID = ?");
            st.setInt(1, year);
            st.setString(2, CSID);
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

    //get assessment list when page onload and when button click based on year and cs level
    public void assessmentList() {

        FacesContext context = FacesContext.getCurrentInstance();

        int tmpYear = 0, tmpyearComm;
        int numYearComm = 0;

        String csid = "", assactivityid = "", assidP = "", assid = "";
        Boolean checkboxChecked = false;
        String csid1 = "";
        int verifyRecord = 0;

        //when page onload, need to show previous(2017) record, 
        //so for year 2018 which have not record in db temporaily become 2017
        if (year == 2018) {

            csid1 = matchCSLevelID();

            verifyRecord = verifyRecord(csid1);

            if (verifyRecord > 0) {
                tmpYear = year;
                disabledProject = true;
                disabledCollaboration = true;
                disabledPractical = true;
                disabledGroupwork = true;
                disabledReset = true;
            } else {
                tmpYear = 2017;
                disabledProject = false;
                disabledCollaboration = false;
                disabledPractical = false;
                disabledGroupwork = false;
                disabledReset = false;
            }

        } else {
            tmpYear = year;
            disabledProject = true;
            disabledCollaboration = true;
            disabledPractical = true;
            disabledGroupwork = true;
            disabledReset = true;
        }

        //newcslevelname = tmpYear + "x";
        //get project checked
        csid = matchCSLevelID();
        assactivityid = "AA1";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        checkboxChecked = false;

        if (!assidP.isEmpty()) {
            checkboxChecked = true;
        }

        project = checkboxChecked;

        //get collaboration checked
        csid = matchCSLevelID();
        assactivityid = "AA2";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        checkboxChecked = false;

        if (!assidP.isEmpty()) {
            checkboxChecked = true;
        }

        collaboration = checkboxChecked;

        //get practical checked
        csid = matchCSLevelID();
        assactivityid = "AA3";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        checkboxChecked = false;

        if (!assidP.isEmpty()) {
            checkboxChecked = true;
        }

        practical = checkboxChecked;

        //get groupwork checked
        csid = matchCSLevelID();
        assactivityid = "AA4";
        assidP = matchAssID(csid, assactivityid, tmpYear);
        assid = matchAssID(csid, assactivityid, year);
        checkboxChecked = false;

        if (!assidP.isEmpty()) {
            checkboxChecked = true;
        }
        groupwork = checkboxChecked;

    }

    //auto generate task ID
    public int autoGenerateID() {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM assessment");

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

    public String matchCSLevelName(String cslevel) {

        String CSID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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

    //add assessment in db
    public void addAssessment() { //if need redirect to another xhtml, need change void to String and return keyword

        FacesContext context = FacesContext.getCurrentInstance();

        int length = 0;
        String asID = "", assID = "", csID = "", assActivityID = "";

        int minPerStud = 0;
        int verifyCounter = 0;

        if (project == false && collaboration == false && practical == false && groupwork == false) {
            context.addMessage(null, new FacesMessage("At least one of the assessment must be checked! Please try again!"));
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO assessment (assID, CSLevelID, assActivityID, year, minPerStud) VALUES (?, ?, ?, ?, ?)");

                //insert project
                if (project == true) {
                    length = autoGenerateID();
                    length = length + 1;
                    asID = "AS" + Integer.toString(length);
                    csID = matchCSLevelName(cslevel);
                    assActivityID = "AA1";
                    minPerStud = 0;

                    statement.setString(1, asID);
                    statement.setString(2, csID);
                    statement.setString(3, assActivityID);
                    statement.setInt(4, year);
                    statement.setInt(5, minPerStud);
                    statement.executeUpdate();
                }

                //insert collaboration
                if (collaboration == true) {
                    length = autoGenerateID();
                    length = length + 1;
                    asID = "AS" + Integer.toString(length);
                    csID = matchCSLevelName(cslevel);
                    assActivityID = "AA2";
                    minPerStud = 0;

                    statement.setString(1, asID);
                    statement.setString(2, csID);
                    statement.setString(3, assActivityID);
                    statement.setInt(4, year);
                    statement.setInt(5, minPerStud);
                    statement.executeUpdate();
                }

                //insert practical
                if (practical == true) {
                    length = autoGenerateID();
                    length = length + 1;
                    asID = "AS" + Integer.toString(length);
                    csID = matchCSLevelName(cslevel);
                    assActivityID = "AA3";
                    minPerStud = 0;

                    statement.setString(1, asID);
                    statement.setString(2, csID);
                    statement.setString(3, assActivityID);
                    statement.setInt(4, year);
                    statement.setInt(5, minPerStud);
                    statement.executeUpdate();
                }

                //insert groupwork
                if (groupwork == true) {
                    length = autoGenerateID();
                    length = length + 1;
                    asID = "AS" + Integer.toString(length);
                    csID = matchCSLevelName(cslevel);
                    assActivityID = "AA4";
                    minPerStud = 0;

                    statement.setString(1, asID);
                    statement.setString(2, csID);
                    statement.setString(3, assActivityID);
                    statement.setInt(4, year);
                    statement.setInt(5, minPerStud);
                    statement.executeUpdate();
                }

                disabledCollaboration = true;
                disabledProject = true;
                disabledPractical = true;
                disabledGroupwork = true;
                disabledReset = true;

                verifyCounter = 1;

                statement.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            switch (verifyCounter) {
                case 0:
                    context.addMessage(null, new FacesMessage("CS Level Setting for " + cslevel + " in year " + year + " not successful!"));
                    break;
                case 1:
                    context.addMessage(null, new FacesMessage("CS Level Setting for " + cslevel + " in year " + year + " successful!"));
                    break;
            }
        }

    }

    //auto generate cs level ID
    public String autoGenerateCSLevelID() {

        int count = 0;
        String csID = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM cslevel");

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

        csID = "CS" + count;

        return csID;
    }

    public void addNewCS() {
        newcslevelid = autoGenerateCSLevelID();

        disabledNewCS = false;
        disabledCollaboration = true;
        disabledProject = true;
        disabledPractical = true;
        disabledGroupwork = true;
        disabledButton = true;
        disabledReset = true;
    }

    public void addCSLevel() {

        //growl purpose
        FacesContext context = FacesContext.getCurrentInstance();
        int verifyCounter = 0;
        String tmpCSName = "";

        if (newcslevelname.isEmpty()) {
            context.addMessage(null, new FacesMessage("CS Level Name cannot be empty! Please try again!"));
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcsdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO cslevel (CSLevelID, CSLevelName) VALUES (?, ?)");

                statement.setString(1, newcslevelid);
                statement.setString(2, newcslevelname);
                statement.executeUpdate();

                statement.close();
                con.close();

                tmpCSName = newcslevelname; //growl purpose

                verifyCounter = 1;

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }

            switch (verifyCounter) {
                case 0:
                    context.addMessage(null, new FacesMessage("Add New CS Level " + tmpCSName + " not successful!"));
                    break;
                case 1:
                    context.addMessage(null, new FacesMessage("Add New CS Level " + tmpCSName + " successful!"));

                    disabledNewCS = true;
                    disabledCollaboration = true;
                    disabledProject = true;
                    disabledPractical = true;
                    disabledGroupwork = true;
                    disabledButton = false;
                    disabledReset = false;

                    newcslevelname = null;
                    newcslevelid = null;
                    break;
            }
        }

    }

    //cancel insert newCSLevel
    public void cancel() {
        counterReset = 1;

        reset();
    }

    //navigation bar purpose
    public String goToNextPage() {

//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajax Update"));
        counterReset = 1;

        reset();
        return "CSLevelSetting";
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
        cslevel = "CS Level 1";
        project = false;
        collaboration = false;
        practical = false;
        groupwork = false;
        newcslevelname = null;
        newcslevelid = null;

        counterReset = 0;

        //set default disabled
        disabledProject = true;
        disabledCollaboration = true;
        disabledPractical = true;
        disabledGroupwork = true;
        disabledNewCS = true;
        disabledButton = false;
        disabledReset = true;
    }

    //reset newCSLevel
    public void resetNew() {

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Reset successful!"));

        newcslevelname = null;
    }

}
