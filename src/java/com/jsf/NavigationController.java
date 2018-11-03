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
 * @author hsuhong1210
 */

@ManagedBean
@SessionScoped

public class NavigationController {

    public NavigationController() {
    }
    
    public String processCSLevelSettingPage(){
        return "CSLevelSetting";
    }
    
}
