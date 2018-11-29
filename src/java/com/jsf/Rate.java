package com.jsf;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean

public class Rate {

    private RateObject rateObj = null;
    private ArrayList rateList = null;
    private Connection con;

    private Rate rateObj1 = null;

    private String rateID = "";
    private int numSampleAss = 0;

    public Rate() {
    }

    public String getRateID() {
        return rateID;
    }

    public void setRateID(String rateID) {
        this.rateID = rateID;
    }

    public int getNumSampleAss() {
        return numSampleAss;
    }

    public void setNumSampleAss(int numSampleAss) {
        this.numSampleAss = numSampleAss;
    }

    public ArrayList getRateList() {

        String[] rateIDList = new String[4];
        int[] numSampleAssList = new int[4];
        int tmp = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT rateID, numSampleAss FROM rate");

            while (rs.next()) {
                rateIDList[tmp] = rs.getString("rateID");
                numSampleAssList[tmp] = rs.getInt("numSampleAss");
                tmp++;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        rateList = new ArrayList();

        for (int i = 0; i < 4; i++) {
            rateObj1 = new Rate();
            rateObj1.setRateID(rateIDList[i]);
            rateObj1.setNumSampleAss(numSampleAssList[i]);
            rateList.add(rateObj1);
        }

        return rateList;

    }

}
