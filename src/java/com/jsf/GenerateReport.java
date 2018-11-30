/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

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
    
    private String reportType;
    private Integer year;
    
    private int counterReset;

    public GenerateReport() {
        this.counterReset = 0;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
        year = 2018;
//        reportType = "";
        
        counterReset = 0;
    }
    
    
}
