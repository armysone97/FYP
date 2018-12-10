/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ruenyenchin
 */

@ManagedBean
@SessionScoped

public class WorkloadReport {
    private Connection con;
    private int counterReset;
    
    private List<String> year_list = new ArrayList<>();
    private List<String> school_list = new ArrayList<>();

    public WorkloadReport() {
        this.counterReset = 0;
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
    
    //retrive school list
    public List<String> get_SchoolList() {
        school_list.clear();
        
        
        return school_list;
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
