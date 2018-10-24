/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class CSLevelSetting {

    private Connection con;
    private Integer year;

    private List<Integer> year_list = new ArrayList<>(); //year list that retrieve from db
    private List<String> year_list1 = new ArrayList<>(); //year list that retrieve from db

    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db

    private String CSNo1, CSNo2, CSNo3, CSNo4, CSNo5, CSNo6, CSNo7, CSNo8, CSNo9, CSNo10;
    public String[] assessmentList;
    // private String Choice1;

    private int countClick; //count button click how many times
    private String newCSLevel;
    private Boolean disabledNewCSLevel;
    private int rowNum;

    public CSLevelSetting() {
        this.countClick = 0;
        this.disabledNewCSLevel = false;
        this.year = 2018;
    }

    private String[] Choice1;

    public String[] getChoice1() {
        return Choice1;
    }

    public void setChoice1(String[] Choice1) {
        this.Choice1 = Choice1;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    
      //count year in db
    public Integer get_yearCount(int yearNow) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
          PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM assessment WHERE year = ?");
            st.setInt(1, yearNow);
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

    //remove duplicate element for year array
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

    //default data when page load
    public void defaultList() {
        //set drop down list
        //   setCSNo1("CS Level 3"); 

        //set checkbox
        String tmp[] = new String[2];//declaration and instantiation  
        tmp[0] = "ProjectNo1";//initialization  
        tmp[1] = "PracticalNo1";

        setChoice1(tmp);

        //set textbox
        setRowNum(4);
        //   setNewCSLevel("x: " + year);

        csLevelGetList();
    }

    public void csLevelGetList() {
        //get 2017 data
        int count = 0;
        int yearNow = year;
     //   setNewCSLevel("xx: " + yearNow);
        
        if (yearNow == 2018){
            yearNow = 2016;
        }
        
        int yearCountList = get_yearCount(yearNow);

        String CSLevel[] = new String[yearCountList];

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            //  Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT CSLevelID FROM assessment WHERE year='2017'");
            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM assessment WHERE year = ?");
            st.setInt(1, yearNow);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                CSLevel[count] = rs.getString("CSLevelID");
                count++;
                //    setNewCSLevel("x : " + count);
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        Arrays.sort(CSLevel);//sorting array  
        int length = CSLevel.length;
        length = removeDuplicateElementsString(CSLevel, length);

        for (int i = 0; i < length; i++) {
            System.out.println("x : " + CSLevel[i] + " ");
        }

        String CSLevelIDList[] = new String[4];
        String CSLevelNameList[] = new String[4];
        int countList = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT CSLevelID, CSLevelName FROM cslevel");

            while (rs.next()) {
                CSLevelIDList[countList] = rs.getString("CSLevelID");
                CSLevelNameList[countList] = rs.getString("CSLevelName");
                countList++;

                //    setNewCSLevel("x : " + count);
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        // get_CSLevel();
        String csName1 = "CS Level 1", csName2 = "CS Level 2", csName3 = "CS Level 3", csName4 = "CS Level 4";
        String CSID1 = "CS1", CSID2 = "CS2", CSID3 = "CS3", CSID4 = "CS4";

        int countS = 0;

//        String CSLevelIDList1[] = new String[5];
//        CSLevelIDList1[0] = "CS1";
//        CSLevelIDList1[1] = "CS2";
//        CSLevelIDList1[2] = "CS5";
//        CSLevelIDList1[3] = "CS4";
//        CSLevelIDList1[4] = "CS3";
        //     System.out.println("CSLevelIDList1[0]: " + CSLevelIDList1[0]);
        System.out.println("countList: " + countList);
        int countList1 = 2;
        for (int i = 0; i < length; i++) {
            //  for (int j = 0; j < 4; j++) {
            //   if (countS <= countList1) {
            if (CSLevel[i].equals(CSLevelIDList[0])) {
                switch (countS) {
                    case 0:
                        setCSNo1(CSLevelNameList[0]);
                        break;
                    case 1:
                        setCSNo2(CSLevelNameList[0]);
                        break;
                    case 2:
                        setCSNo3(CSLevelNameList[0]);
                        break;
                    case 3:
                        setCSNo4(CSLevelNameList[0]);
                        break;
                }
                System.out.println("1. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[0] + "countS: " + countS);
                countS++;
            } else if (CSLevel[i].equals(CSLevelIDList[1])) {
                switch (countS) {
                    case 0:
                        setCSNo1(CSLevelNameList[1]);
                        break;
                    case 1:
                        setCSNo2(CSLevelNameList[1]);
                        break;
                    case 2:
                        setCSNo3(CSLevelNameList[1]);
                        break;
                    case 3:
                        setCSNo4(CSLevelNameList[1]);
                        break;
                }
                System.out.println("2. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[1] + "countS: " + countS);

                countS++;

            } else if (CSLevel[i].equals(CSLevelIDList[2])) {

                switch (countS) {
                    case 0:
                        setCSNo1(CSLevelNameList[2]);
                        break;
                    case 1:
                        setCSNo2(CSLevelNameList[2]);
                        break;
                    case 2:
                        setCSNo3(CSLevelNameList[2]);
                        break;
                    case 3:
                        setCSNo4(CSLevelNameList[2]);
                        break;
                }
                System.out.println("3. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[2] + "countS: " + countS);

                countS++;
            } else if (CSLevel[i].equals(CSLevelIDList[3])) {

                switch (countS) {
                    case 0:
                        setCSNo1(CSLevelNameList[3]);
                        break;
                    case 1:
                        setCSNo2(CSLevelNameList[3]);
                        break;
                    case 2:
                        setCSNo3(CSLevelNameList[3]);
                        break;
                    case 3:
                        setCSNo4(CSLevelNameList[3]);
                        break;
                }
                System.out.println("4. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[3] + "countS: " + countS);

                countS++;
            } else if (CSLevel[i].equals(CSLevelIDList[4])) {
                switch (countS) {
                    case 0:
                        setCSNo1(CSLevelNameList[4]);
                        break;
                    case 1:
                        setCSNo2(CSLevelNameList[4]);
                        break;
                    case 2:
                        setCSNo3(CSLevelNameList[4]);
                        break;
                    case 3:
                        setCSNo4(CSLevelNameList[4]);
                        break;
                }
                System.out.println("5. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[4] + "countS: " + countS);
                countS++;
            }

            //  }
        }

//        setCSNo1(CSLevel[0]);
//        setCSNo2(CSLevel[1]);
//        setCSNo3(CSLevel[2]);
//        setCSNo4("CS Level 4");
    }
    
   
    
    public void testingDDL() {
        if (year == 2015){
            setNewCSLevel("y: " + "xx");
        }else{
            setNewCSLevel("z: " + "xx");
        }
       // int yearNow = year;
         
    }
    
//     public void csLevelGetListForDDL(AjaxBehaviorEvent event) {
//        //get 2017 data
//        int count = 0;
//        int yearNow = year;
//        setNewCSLevel("y: " + yearNow);
//        
//        if (yearNow == 2018){
//            yearNow = 2017;
//        }
//        
//        int countArray = get_yearCount(yearNow);
//
//        String CSLevel[] = new String[countArray];
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            //  Statement st = con.createStatement();
////            ResultSet rs = st.executeQuery("SELECT CSLevelID FROM assessment WHERE year='2017'");
//            PreparedStatement st = con.prepareStatement("SELECT CSLevelID FROM assessment WHERE year = ?");
//            st.setInt(1, yearNow);
//            ResultSet rs = st.executeQuery();
//            
//            while (rs.next()) {
//                CSLevel[count] = rs.getString("CSLevelID");
//                count++;
//                //    setNewCSLevel("x : " + count);
//            }
//
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
//
//        Arrays.sort(CSLevel);//sorting array  
//        int length = CSLevel.length;
//        length = removeDuplicateElementsString(CSLevel, length);
//
//        for (int i = 0; i < length; i++) {
//            System.out.println("x : " + CSLevel[i] + " ");
//        }
//
//        String CSLevelIDList[] = new String[4];
//        String CSLevelNameList[] = new String[4];
//        int countList = 0;
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcstmp1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT CSLevelID, CSLevelName FROM cslevel");
//
//            while (rs.next()) {
//                CSLevelIDList[countList] = rs.getString("CSLevelID");
//                CSLevelNameList[countList] = rs.getString("CSLevelName");
//                countList++;
//
//                //    setNewCSLevel("x : " + count);
//            }
//
//            st.close();
//            con.close();
//
//        } catch (Exception ex) {
//            System.out.println("Error: " + ex);
//        }
//
//        // get_CSLevel();
//        String csName1 = "CS Level 1", csName2 = "CS Level 2", csName3 = "CS Level 3", csName4 = "CS Level 4";
//        String CSID1 = "CS1", CSID2 = "CS2", CSID3 = "CS3", CSID4 = "CS4";
//
//        int countS = 0;
//
////        String CSLevelIDList1[] = new String[5];
////        CSLevelIDList1[0] = "CS1";
////        CSLevelIDList1[1] = "CS2";
////        CSLevelIDList1[2] = "CS5";
////        CSLevelIDList1[3] = "CS4";
////        CSLevelIDList1[4] = "CS3";
//        //     System.out.println("CSLevelIDList1[0]: " + CSLevelIDList1[0]);
//        System.out.println("countList: " + countList);
//        int countList1 = 2;
//        for (int i = 0; i < length; i++) {
//            //  for (int j = 0; j < 4; j++) {
//            //   if (countS <= countList1) {
//            if (CSLevel[i].equals(CSLevelIDList[0])) {
//                switch (countS) {
//                    case 0:
//                        setCSNo1(CSLevelNameList[0]);
//                        break;
//                    case 1:
//                        setCSNo2(CSLevelNameList[0]);
//                        break;
//                    case 2:
//                        setCSNo3(CSLevelNameList[0]);
//                        break;
//                    case 3:
//                        setCSNo4(CSLevelNameList[0]);
//                        break;
//                }
//                System.out.println("1. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[0] + "countS: " + countS);
//                countS++;
//            } else if (CSLevel[i].equals(CSLevelIDList[1])) {
//                switch (countS) {
//                    case 0:
//                        setCSNo1(CSLevelNameList[1]);
//                        break;
//                    case 1:
//                        setCSNo2(CSLevelNameList[1]);
//                        break;
//                    case 2:
//                        setCSNo3(CSLevelNameList[1]);
//                        break;
//                    case 3:
//                        setCSNo4(CSLevelNameList[1]);
//                        break;
//                }
//                System.out.println("2. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[1] + "countS: " + countS);
//
//                countS++;
//
//            } else if (CSLevel[i].equals(CSLevelIDList[2])) {
//
//                switch (countS) {
//                    case 0:
//                        setCSNo1(CSLevelNameList[2]);
//                        break;
//                    case 1:
//                        setCSNo2(CSLevelNameList[2]);
//                        break;
//                    case 2:
//                        setCSNo3(CSLevelNameList[2]);
//                        break;
//                    case 3:
//                        setCSNo4(CSLevelNameList[2]);
//                        break;
//                }
//                System.out.println("3. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[2] + "countS: " + countS);
//
//                countS++;
//            } else if (CSLevel[i].equals(CSLevelIDList[3])) {
//
//                switch (countS) {
//                    case 0:
//                        setCSNo1(CSLevelNameList[3]);
//                        break;
//                    case 1:
//                        setCSNo2(CSLevelNameList[3]);
//                        break;
//                    case 2:
//                        setCSNo3(CSLevelNameList[3]);
//                        break;
//                    case 3:
//                        setCSNo4(CSLevelNameList[3]);
//                        break;
//                }
//                System.out.println("4. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[3] + "countS: " + countS);
//
//                countS++;
//            } else if (CSLevel[i].equals(CSLevelIDList[4])) {
//                switch (countS) {
//                    case 0:
//                        setCSNo1(CSLevelNameList[4]);
//                        break;
//                    case 1:
//                        setCSNo2(CSLevelNameList[4]);
//                        break;
//                    case 2:
//                        setCSNo3(CSLevelNameList[4]);
//                        break;
//                    case 3:
//                        setCSNo4(CSLevelNameList[4]);
//                        break;
//                }
//                System.out.println("5. CSLevel[i]: " + CSLevel[i] + " CSLevelIDList1 : " + CSLevelIDList[4] + "countS: " + countS);
//                countS++;
//            }
//
//            //  }
//        }
//
////        setCSNo1(CSLevel[0]);
////        setCSNo2(CSLevel[1]);
////        setCSNo3(CSLevel[2]);
////        setCSNo4("CS Level 4");
//    }

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

    public void addCS() {
        // setYear("Admin");

        setNewCSLevel(assessmentList[2]); //checkbox value

    }

    //default set the dropdownlist choice
    public void addCS1() {
        //  setYear("Master Trainer");
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

    //change text box disabled when click the button
    public Boolean changeTextboxDisabled() {
        if (countClick == 0) {
            disabledNewCSLevel = false;
        } else if (countClick == 1) {
            disabledNewCSLevel = true;
        }

        return disabledNewCSLevel;
    }

    //add row number when click the button
    public void addRowNum() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (rowNum < 10) {

            rowNum++;
        }
        context.addMessage(null, new FacesMessage("success" + rowNum));
        setRowNum(rowNum);

    }

    public void main(String args[]) {
//        addCS();
        //addRowNum();
        get_year();
    }
}
