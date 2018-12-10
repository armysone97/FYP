/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import java.util.ArrayList;
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

public class MileageClaimApplication {
    
    private Connection con;
    private String staffID;
    private String name;
    private String role;
    private String rate;
    private double toll;
    private double parking;
    private double accomodation;
    private double mileage;
    private double totalClaim;
    private int year;
    private String result;
    private String workload;
    private List<String> workload_list = new ArrayList<>();
    
    private int counterReset;
    
    //for add mileage claim
    private int mileageClaimCount;
    private String mcID;

    public MileageClaimApplication() {
        this.counterReset = 0;
        this.year = 2018;
        this.toll = 0.00;
        this.parking = 0.00;
        this.accomodation = 0.00;
        this.mileage = 0.00;
        this.result = "0.00";
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getToll() {
        return toll;
    }

    public void setToll(double toll) {
        this.toll = toll;
    }

    public double getParking() {
        return parking;
    }

    public void setParking(double parking) {
        this.parking = parking;
    }

    public double getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(double accomodation) {
        this.accomodation = accomodation;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getTotalClaim() {
        return totalClaim;
    }

    public void setTotalClaim(double totalClaim) {
        this.totalClaim = totalClaim;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public void setWorkload_list(List<String> workload_list) {
        this.workload_list = workload_list;
    }
    
    public void defaultStaffList() {
        switch (Login.getGlobalCounter()) {
            case 1:
                this.staffID = Login.getGlobalStaffID();
                retrievePersonalDetails();
                break;
        }
    }
    
    public List<String> get_WorkloadList() {

        workload_list.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliant=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT specialID FROM workloadallocation WHERE staffID = ? AND (assessment = 'Collaboration' OR assessment = 'Groupwork') AND year = ?");
            st.setString(1, staffID);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                workload_list.add(rs.getString("specialID"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return workload_list;
    }
    
     public void retrievePersonalDetails() {

        //retrieve personal details
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT name FROM evaluatorpersonaldetails WHERE staffID = ?");
            st.setString(1, staffID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setName(rs.getString("name"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        //retrieve mileage rate
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement st = con.prepareStatement("SELECT mileageRate FROM rate WHERE year = ?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                setRate(String.format("%.2f", rs.getDouble("mileageRate")));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }
     
     //calculate total mileage claim
     public void calculateMileageClaim(){
         
        totalClaim = ((Double.valueOf(rate))*mileage)+toll+parking+accomodation;
        
        result = String.format("%.2f", totalClaim);
     }
     
     //check validation
    public void validation_Check(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(workload == null || mileage == 0.0){
            context.addMessage(null, new FacesMessage("All field are required to fill in!"));
        }else if(totalClaim == 0.0){
            context.addMessage(null, new FacesMessage("Click 'CALCULATE' to check total claim!"));
        }
        else{
            check_DuplicateRecord();
        }
    }
     
     //check duplicate record
     public void check_DuplicateRecord(){
        FacesContext context = FacesContext.getCurrentInstance();
        boolean check = true;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT year, staffID, claimRecord FROM mileageclaimprocessing");

            while (rs.next()) {
                int yearDB = rs.getInt("year");
                String staffIDDB = rs.getString("staffID");
                String record_Type = rs.getString("claimRecord");

                if (year == yearDB && staffID.equals(staffIDDB) && workload.equals(record_Type)) {
                    check = false;
                    break;
                }else{
                    check = true;
                }
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        if(check == true){
            addMileageClaim();
        }else{
            context.addMessage(null, new FacesMessage("Record already existed!"));
        }
    }
     
     //add mileage claim
     public void addMileageClaim(){
         FacesContext context = FacesContext.getCurrentInstance();
         //count mileage claim index
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM mileageclaimprocessing");

            while (rs.next()) {
                mileageClaimCount = rs.getInt("COUNT(*)");
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        mileageClaimCount = mileageClaimCount + 1;
        mcID = "MC" + Integer.toString(mileageClaimCount);
        
        //insert mileage claim
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/try1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO mileageclaimprocessing (MC_ID, toll, parking, accomodation, mileage, totalMileageClaim, year, staffID, claimRecord) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, mcID);
            statement.setDouble(2, toll);
            statement.setDouble(3, parking);
            statement.setDouble(4, accomodation);
            statement.setDouble(5, mileage);
            statement.setDouble(6, totalClaim);
            statement.setInt(7, year);
            statement.setString(8, staffID);
            statement.setString(9, workload);

            statement.executeUpdate();
            statement.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        
        context.addMessage(null, new FacesMessage("Added successful!"));
     }
    
    //navigation bar purpose
    public String goToNextPage() {

        counterReset = 1;

        reset();

        return "MileageClaimApplication";
    }

    //reset page
    public void reset() {

        FacesContext context = FacesContext.getCurrentInstance();

//        switch (counterReset) {
//            case 0:
//                context.addMessage(null, new FacesMessage("Reset successful!"));
//                break;
//        }

        //set default value
//        staffID = null;
//        name = null;
        role = null;
        rate = "0.0";
        toll = 0.00;
        parking = 0.00;
        accomodation = 0.00;
        mileage = 0.00;
        totalClaim = 0.00;
        year = 2018;
        result = "0.00";

        counterReset = 0;
    }
    
    
}
