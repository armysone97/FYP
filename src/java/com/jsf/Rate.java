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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
//@ViewScoped
@SessionScoped

public class Rate {

//    private RateObject rateObj = null;
    private ArrayList rateList = null;
    private Connection con;

    private Rate rateObj1 = null;

    private String rateID = "";
    private int numSampleAss = 0;

    private String input, output;
    private int year;

    public Rate() {
//        this.output = "xxxxxx";
//        this.input = "yyyyy";
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

//    datatable
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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
//    valuechangelistener
    public void yearChanged(){
         output = "Hello, you selected " + year;
    }

    public void namedChanged() {
//        output = "mjj";
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(input));
        output = "Hello, you entered " + input;
    }

    public void damnit() {
        output = "xx";
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(" hhh: "));
    }

//    public void namedChanged(ValueChangeEvent event){
//        
//        FacesContext context = FacesContext.getCurrentInstance();
//        
////        context.getViewRoot().set
//        
//        output = (String) event.getNewValue();
//        
//        context.renderResponse();
//        
//    }
}
