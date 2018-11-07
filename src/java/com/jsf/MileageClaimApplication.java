/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    private double rate;
    private double toll;
    private double parking;
    private double accomodation;
    private double mileage;
    private double totalClaim;

    public MileageClaimApplication() {
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
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
    
    
}
