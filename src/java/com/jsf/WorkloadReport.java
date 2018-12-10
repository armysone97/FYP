/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import static com.jsf.MaintainTeacher.removeDuplicateElementsString;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ruenyenchin
 */
@ManagedBean
@SessionScoped

public class WorkloadReport {

    private Connection con;
    private int year;
    private String school;
    private int specialID_Count;
    private int schoolID_Count;
    private String schoolID;
    private int counterReset;

    private List<String> year_list = new ArrayList<>();
    private List<String> school_list = new ArrayList<>();

    private ArrayList schoolWorkloadList = null;

    public WorkloadReport() {
        this.counterReset = 0;
        this.year = 2018;
    }

    public int getYear() {
        return year;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSchool_list(List<String> school_list) {
        this.school_list = school_list;
    }

    public void setYear_list(List<String> year_list) {
        this.year_list = year_list;
    }

    //display year
    public List<String> get_YearList() {
        year_list.clear();

        year_list.add("2016");
        year_list.add("2017");
        year_list.add("2018");

        return year_list;
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

    //retrive school list
    public List<String> get_SchoolList() {
        school_list.clear();

        //count specialID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM workloadallocation WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                specialID_Count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String[] specialIDList = new String[specialID_Count];
        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliant=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT specialID FROM workloadallocation WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                specialIDList[tmp] = rs.getString("specialID");
                tmp++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String[] schoolNameList = new String[specialID_Count];

        for (int i = 0; i < specialID_Count; i++) {
            String[] parts = specialIDList[i].split(" - ");
            schoolNameList[i] = parts[1];
//            school_list.add(parts[1]);
        }

        Arrays.sort(schoolNameList);//sorting array  
        int length = specialIDList.length;
        length = removeDuplicateElementsString(schoolNameList, length);

        for (int i = 0; i < length; i++) {
            school_list.add(schoolNameList[i]);
        }

        return school_list;
    }

    public ArrayList getSchoolWorkloadList() {
        //retrieve staff ID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolID FROM school WHERE schoolName = ?");
            st.setString(1, school);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolID = rs.getString("schoolID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        //count schoolID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM schoolcsmap WHERE year = ? AND schoolID = ?");
            st.setInt(1, year);
            st.setString(2, schoolID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolID_Count = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        String[] schoolIDList = new String[schoolID_Count];
        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliant=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT schoolCSMapID FROM schoolcsmap WHERE year = ? AND schoolID = ?");
            st.setInt(1, year);
            st.setString(2, schoolID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                schoolIDList[tmp] = rs.getString("schoolCSMapID");
                tmp++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

//        String[] teacherCSMapIDList = new String[schoolID_Count];
//        int tmp1 = 0;
        int teacherCSMapIDCount = 0;
        for (int i = 0; i < schoolID_Count; i++) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    teacherCSMapIDCount = teacherCSMapIDCount + rs.getInt("COUNT(*)");
                    //                   teacherIDList[tmp1] = teacherCSMapIDTmp;
//                    tmp1++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        String[] teacherCSMapIDList = new String[teacherCSMapIDCount];
        int tmp1 = 0;

        for (int i = 0; i < schoolID_Count; i++) {
//            String teacherCSMapIDTmp = "";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT teacherCSMapID FROM teachercsmap WHERE schoolCSMapID = ?");
                st.setString(1, schoolIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
//                    teacherCSMapIDTmp = rs.getString("teacherCSMapID");
                    teacherCSMapIDList[tmp1] = rs.getString("teacherCSMapID");
                    //                   teacherIDList[tmp1] = teacherCSMapIDTmp;
                    tmp1++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }

        return schoolWorkloadList;
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();

        return "WorkloadReport";
    }

    //reset page
    public void reset() {

        counterReset = 0;
        MaintainSchoolMenu.setGlobalCounter(0);
    }
}
