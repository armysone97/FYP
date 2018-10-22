/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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

    private List<String> year_list = new ArrayList<>(); //year list that retrieve from db
    private List<String> CSLevel_list = new ArrayList<>(); //CS level list that retrieve from db

    private String CSNo1, CSNo2, CSNo3, CSNo4, CSNo5, CSNo6, CSNo7, CSNo8, CSNo9, CSNo10;
    public String[] assessmentList;

    //  private List<String> assessment_list_selected;
    private int countClick; //count button click how many times
    private String newCSLevel;
    private Boolean disabledNewCSLevel;

    private List<String> cities = new LinkedList<String>();
    private List<String> year_list1 = new ArrayList<>();

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
        //  year = "Admin";
        this.countClick = 0;
        this.disabledNewCSLevel = false;
//        
//        assessment_list_selected = new ArrayList<String>();
//        assessment_list_selected.add("1");
//        assessment_list_selected.add("2");
//        assessment_list_selected.add("3");

    }
//    
//      @EJB
//    private NameService nameService;
//
//    @PostConstruct
//    public void init() {
//        years = nameService.list();
//    }

//    public String[] getAssessment_list_selected() {
//        return assessment_list_selected;
//    }
//
//    public void setAssessment_list_selected(String[] assessment_list_selected) {
//        this.assessment_list_selected = assessment_list_selected;
//    }
    public String[] getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(String[] assessmentList) {
        this.assessmentList = assessmentList;
    }

    public String getAssessmentList1() {
        return Arrays.toString(assessmentList);
    }

    public String getNewCSLevel() {
        return newCSLevel;
    }

    public void setNewCSLevel(String newCSLevel) {
        this.newCSLevel = newCSLevel;
    }

    public Boolean getDisabledNewCSLevel() {
        return disabledNewCSLevel;
    }

    public void setDisabledNewCSLevel(Boolean disabledNewCSLevel) {
        this.disabledNewCSLevel = disabledNewCSLevel;
    }

    public String getCSNo1() {
        return CSNo1;
    }

    public void setCSNo1(String CSNo1) {
        this.CSNo1 = CSNo1;
    }

    public String getCSNo2() {
        return CSNo2;
    }

    public void setCSNo2(String CSNo2) {
        this.CSNo2 = CSNo2;
    }

    public String getCSNo3() {
        return CSNo3;
    }

    public void setCSNo3(String CSNo3) {
        this.CSNo3 = CSNo3;
    }

    public String getCSNo4() {
        return CSNo4;
    }

    public void setCSNo4(String CSNo4) {
        this.CSNo4 = CSNo4;
    }

    public String getCSNo5() {
        return CSNo5;
    }

    public void setCSNo5(String CSNo5) {
        this.CSNo5 = CSNo5;
    }

    public String getCSNo6() {
        return CSNo6;
    }

    public void setCSNo6(String CSNo6) {
        this.CSNo6 = CSNo6;
    }

    public String getCSNo7() {
        return CSNo7;
    }

    public void setCSNo7(String CSNo7) {
        this.CSNo7 = CSNo7;
    }

    public String getCSNo8() {
        return CSNo8;
    }

    public void setCSNo8(String CSNo8) {
        this.CSNo8 = CSNo8;
    }

    public String getCSNo10() {
        return CSNo10;
    }

    public void setCSNo10(String CSNo10) {
        this.CSNo10 = CSNo10;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

        year_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT roleType FROM roles");

            while (rs.next()) {

                year_list.add(rs.getString("roleType"));

//                String y = "";
//                
//                y = rs.getString("roleType");
//              //  year_list.add(y);
//                
//                // context.addMessage(null, new FacesMessage("a : " + count));
//                
//                if(y.equals("Admin")){
//                    year = y;
//                }
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

//        String y = "";
//        
//        for (int i = 0; i < 4; i++){
//            y = year_list.get(2);
//        }
        // year = "Admin";
        //setYear("Admin");
        return year_list;
    }

    public List<String> get_CSLevel() {

        year_list.clear();

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT CSLevelName FROM cslevel");

            while (rs.next()) {
                CSLevel_list.add(rs.getString("CSLevelName"));
            }

            st.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return CSLevel_list;
    }

    public String get_year2() {

        FacesContext context = FacesContext.getCurrentInstance();
        int count = 1;
        String tmp = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stemcs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT roleType FROM roles");

            while (rs.next()) {

                tmp = rs.getString("roleType");

                //   String ss = setCS1;
                if (count == 1) {
                    // setCS1(tmp + count);
                    context.addMessage(null, new FacesMessage("a : " + count + tmp));
                } else if (count == 2) {
                    //     setCS2(tmp + count);
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

    public void addCS() {
        setYear("Admin");

        //  assessment_list.get(1).toString();
        //   setNewCSLevel(Arrays.toString(assessment_list));
//        for (int i = 0; i < 5; i++) {
//            String a = assessment_list[i];
//        }
        // String a = assessment_list[2];
        
        setNewCSLevel(assessmentList[2]); //checkbox value
      
        // setCS1("xx");
//        cities.add("");
//        return "refreshTable";
      //  getAssessmentList1();
      
//      String a = "";
//      
//      for (int i = 0; i < 10; i++){
//          a = "CSNo"+ i;
//          
//          if(a.equals(CS))
//      }
//      
//      a = CSNo2;
//      
     // setNewCSLevel(a);

    }

    //default set the dropdownlist choice
    public void addCS1() {
        setYear("Master Trainer");

        //  return true;
    }

    //change button name when click the button
    public String changeButtonName() {

        String clickName = "";
        //  String actionKeyword = "";

        if (countClick == 0) {
            countClick++;
            clickName = "Add new CS Level";
        } else if (countClick == 1) {
            countClick--;
            clickName = "Confirm";
        }

        return clickName;

    }

    //change button action when click the button
    public void changeButtonAction() {
        if (countClick == 0) {
            addCS();
        } else if (countClick == 1) {
            addCS1();
        }
    }

    public Boolean changeTextboxDisabled() {
        if (countClick == 0) {
            disabledNewCSLevel = false;
            //addCS();
        } else if (countClick == 1) {
            disabledNewCSLevel = true;
            // addCS1();
        }

        return disabledNewCSLevel;
    }

    public void main(String args[]) {
        addCS();
    }
}
