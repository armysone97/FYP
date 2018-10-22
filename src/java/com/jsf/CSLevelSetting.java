/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author hsuhong1210
 */
@ManagedBean
@SessionScoped

public class CSLevelSetting {

    private Connection con;
    private String year;
    // private List<String> years;
    private List<String> year_list = new ArrayList<>();
    private List<String> cities = new LinkedList<String>();
    private List<String> year_list1 = new ArrayList<>();
    private String CS1;
    private String CS2;
//    private String get_year2;
//
//    public String getGet_year2() {
//        return get_year2;
//    }
//
//    public void setGet_year2(String get_year2) {
//        this.get_year2 = get_year2;
//    }

    public CSLevelSetting() {
    }
//    
//      @EJB
//    private NameService nameService;
//
//    @PostConstruct
//    public void init() {
//        years = nameService.list();
//    }

    public String getCS1() {
        return CS1;
    }

    public void setCS1(String CS1) {
        this.CS1 = CS1;
    }

    public String getCS2() {
        return CS2;
    }

    public void setCS2(String CS2) {
        this.CS2 = CS2;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

//    public List<String> get_year1() {
//        year_list1.add("roleType");
//        year_list1.add("roleType1");
//
//        return year_list1;
//    }
    
    
    
    public List<String> get_year() {
// 	try {
// 	 	Connection connection=null;
// 	 	Class.forName("com.mysql.jdbc.Driver");
// 	 	connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
// 	 	PreparedStatement ps=null;
// 	 	ps=connection.prepareStatement("select * from categories");
// 	 	ResultSet rs=ps.executeQuery();
// 	 	while(rs.next()){
// 	 	// 	category_list.add(rs.getString("category_name"));
// 	 	}
// 	} catch (Exception e) {
// 	 	 System.out.println(e);
// 	}

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT roleType FROM roles");

            while (rs.next()) {

                year_list.add(rs.getString("roleType"));
                // context.addMessage(null, new FacesMessage("a : " + count));

            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return year_list;
    }

    public String get_year2() {

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stem_cs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT roleType FROM roles");

            while (rs.next()) {

                tmp = rs.getString("roleType");
                
             //   String ss = setCS1;
                
                if(count == 1){
                     setCS1(tmp + count);
                     context.addMessage(null, new FacesMessage("a : " + count + tmp));
                }else if (count == 2){
                    setCS2(tmp  + count);
                    context.addMessage(null, new FacesMessage("a : " + count + tmp));
                }
              //  for(int i = 1; i < 3; i++){
                   
               // }
                count += 1;
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

        return tmp;
    }
//    
//      public String get_year3() {
//          return get_year2(0);
//      }
//      
//        public String get_year4() {
//          return get_year2(1);
//      }
//        
//         public String get_year5() {
//          return get_year2(2);
//      }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String addCS() {
        cities.add("");
        return "refreshTable";
    }

    public void main(String args[]) {
        addCS();
    }
}
