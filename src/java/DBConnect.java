
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ruenyenchin
 */
public class DBConnect {
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public DBConnect(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            st = con.createStatement();
            
        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }
    
    public void getData(){
        try{
            String query = "select * from persons";
            rs = st.executeQuery(query);
            System.out.println("Records from database:");
            while(rs.next()){
                String name = rs.getString("name");
                String age = rs.getString("age");
                System.out.println("Name: " + name + "      " + "Age: " + age);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
