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
 * @author ruenyenchin
 */

@ManagedBean
@SessionScoped

//mileage claim report
public class MileageClaimReport {
    
    private Connection con;
    private int year;
    private String evaluator;
    private String staffID;
    private int mileageClaimCount;
    private int counterReset;
    
    private List<String> year_list = new ArrayList<>();
    private List<String> evaluator_list = new ArrayList<>();
    
    private ArrayList mileageClaimList = null;
    private MileageClaimReport mileageClaimRepobj1 = null;
    private int number_DT = 0;
    private String toll_DT = "";
    private String parking_DT = "";
    private String accomodation_DT = "";
    private String mileage_DT = "";
    private String ttlMileage_DT = "";
    private String assessment_DT = "";
    private String school_DT = "";

    public MileageClaimReport() {
        this.counterReset = 0;
        this.year = 2018;
    }

    public int getYear() {
        return year;
    }

    public int getNumber_DT() {
        return number_DT;
    }

    public void setNumber_DT(int number_DT) {
        this.number_DT = number_DT;
    }

    public String getToll_DT() {
        return toll_DT;
    }

    public void setToll_DT(String toll_DT) {
        this.toll_DT = toll_DT;
    }

    public String getParking_DT() {
        return parking_DT;
    }

    public void setParking_DT(String parking_DT) {
        this.parking_DT = parking_DT;
    }

    public String getAccomodation_DT() {
        return accomodation_DT;
    }

    public void setAccomodation_DT(String accomodation_DT) {
        this.accomodation_DT = accomodation_DT;
    }

    public String getMileage_DT() {
        return mileage_DT;
    }

    public void setMileage_DT(String mileage_DT) {
        this.mileage_DT = mileage_DT;
    }

    public String getTtlMileage_DT() {
        return ttlMileage_DT;
    }

    public void setTtlMileage_DT(String ttlMileage_DT) {
        this.ttlMileage_DT = ttlMileage_DT;
    }

    public String getAssessment_DT() {
        return assessment_DT;
    }

    public void setAssessment_DT(String assessment_DT) {
        this.assessment_DT = assessment_DT;
    }

    public String getSchool_DT() {
        return school_DT;
    }

    public void setSchool_DT(String school_DT) {
        this.school_DT = school_DT;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setYear_list(List<String> year_list) {
        this.year_list = year_list;
    }

    public void setEvaluator_list(List<String> evaluator_list) {
        this.evaluator_list = evaluator_list;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setMileageClaimList(ArrayList mileageClaimList) {
        this.mileageClaimList = mileageClaimList;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }
    
    //display year
    public List<String> get_YearList() {
        year_list.clear();

        year_list.add("2016");
        year_list.add("2017");
        year_list.add("2018");

        return year_list;
    }
    
    //display evaluator list
    public List<String> get_EvaluatorList() {
        evaluator_list.clear();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM evaluatorpersonaldetails WHERE status = 'Available'");

            while (rs.next()) {
                evaluator_list.add(rs.getString("name"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        return evaluator_list;
    }
    
    public void callMileage() {
        getMileageClaimList();
        
        if(mileageClaimList.isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("No record to be displayed!"));
        }
    }
    
    //retrieve evaluator workload
    public ArrayList getMileageClaimList() {
        //retrieve staff ID
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT staffID FROM evaluatorpersonaldetails WHERE name = ?");
            st.setString(1, evaluator);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                staffID = rs.getString("staffID");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //count amt workload claim
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM mileageclaimprocessing WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                mileageClaimCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //retrieve mcID
        String[] mcIDList = new String[mileageClaimCount];
        int tmp = 0;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT MC_ID FROM mileageclaimprocessing WHERE staffID = ? AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                mcIDList[tmp] = rs.getString("MC_ID");
                tmp++;
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        Double[] tollList = new Double[mileageClaimCount];
        Double[] parkingList = new Double[mileageClaimCount];
        Double[] accomodationList = new Double[mileageClaimCount];
        Double[] mileageList = new Double[mileageClaimCount];
        Double[] ttlMileageClaimList = new Double[mileageClaimCount];
        String[] claimRecordList = new String[mileageClaimCount];
        
        int tmp1 = 0;
        int tmp2 = 0;
        int tmp3 = 0;
        int tmp4 = 0;
        int tmp5 = 0;
        int tmp6 = 0;
        
        for (int i = 0; i < mileageClaimCount; i++) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                PreparedStatement st = con.prepareStatement("SELECT toll, parking, accomodation, mileage, totalMileageClaim, claimRecord FROM mileageclaimprocessing WHERE MC_ID = ?");
                st.setString(1, mcIDList[i]);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    tollList[tmp1] = rs.getDouble("toll");
                    tmp1++;
                    
                    parkingList[tmp2] = rs.getDouble("parking");
                    tmp2++;
                    
                    accomodationList[tmp3] = rs.getDouble("accomodation");
                    tmp3++;
                    
                    mileageList[tmp4] = rs.getDouble("mileage");
                    tmp4++;
                    
                    ttlMileageClaimList[tmp5] = rs.getDouble("totalMileageClaim");
                    tmp5++;
                    
                    claimRecordList[tmp6] = rs.getString("claimRecord");
                    tmp6++;
                }

                st.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
        
        String[] schoolNameList = new String[mileageClaimCount];
        String[] assessmentList = new String[mileageClaimCount];

        for (int i = 0; i < mileageClaimCount; i++) {
            String[] parts = claimRecordList[i].split(" - ");
            schoolNameList[i] = parts[1];
            assessmentList[i] = parts[0];
        }
        
         String[] sortArr = new String[mileageClaimCount];
         
          for (int i = 0; i < mileageClaimCount; i++) {
            sortArr[i] = schoolNameList[i] + " - " + assessmentList[i] + " - " + tollList[i] + " - " + parkingList[i] + " - " + accomodationList[i] + " - " + mileageList[i] + " - " + ttlMileageClaimList[i];
        }

        Arrays.sort(sortArr);
        
        for (int i = 0; i < mileageClaimCount; i++) {
            String[] parts = sortArr[i].split(" - ");
            schoolNameList[i] = parts[0];
            assessmentList[i] = parts[1];
            tollList[i] = Double.valueOf(parts[2]);
            parkingList[i] = Double.valueOf(parts[3]);
            accomodationList[i] = Double.valueOf(parts[4]);
            mileageList[i] = Double.valueOf(parts[5]);
            ttlMileageClaimList[i] = Double.valueOf(parts[6]);
        }
        
        mileageClaimList = new ArrayList();

        for (int i = 0; i < mileageClaimCount; i++) {
            mileageClaimRepobj1 = new MileageClaimReport();
            mileageClaimRepobj1.setNumber_DT(i + 1);
            mileageClaimRepobj1.setSchool_DT(schoolNameList[i]);
            mileageClaimRepobj1.setAssessment_DT(assessmentList[i]);
            mileageClaimRepobj1.setToll_DT("RM" + String.format("%.2f", tollList[i]));
            mileageClaimRepobj1.setParking_DT("RM" + String.format("%.2f", parkingList[i]));
            mileageClaimRepobj1.setAccomodation_DT("RM" + String.format("%.2f", accomodationList[i]));
            mileageClaimRepobj1.setMileage_DT(String.format("%.2f", mileageList[i]));
            mileageClaimRepobj1.setTtlMileage_DT("RM" + String.format("%.2f", ttlMileageClaimList[i]));
            mileageClaimList.add(mileageClaimRepobj1);
        }
        
        return mileageClaimList;
    }

    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();

        return "MileageClaimReport";
    }

    //reset page
    public void reset() {

        counterReset = 0;
        MaintainSchoolMenu.setGlobalCounter(0);
    }
}
