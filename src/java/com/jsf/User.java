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

public class User {
    
    private Connection con;
    
    private String name;

    public User() {
    }
    
    public void insertData(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("INSERT INTO try (name) VALUES (?)");
            statement.setString(1, name);
            statement.executeUpdate();
            statement.close();
            con.close();
        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name1) {
        this.name = name1;
    }
    
    public void main(String args[]){
        insertData();
    }
}
