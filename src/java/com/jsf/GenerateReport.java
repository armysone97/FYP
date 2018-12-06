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

public class GenerateReport {
    
    private Connection con;
    private int counterReset;
    
    public GenerateReport() {
        this.counterReset = 0;
    }
    
    
    
    //navigation bar purpose
    public String goToNextPage() {
        
        counterReset = 1;

        reset();
               
        return "GenerateReport";
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
        //year = 2018;
//        reportType = "";
        
        counterReset = 0;
    }
    
    
}
