/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ruenyenchin
 */

@ManagedBean
@SessionScoped

public class MileageClaimReport {
     private int counterReset;

    public MileageClaimReport() {
        this.counterReset = 0;
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
