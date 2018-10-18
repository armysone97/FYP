
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
    public String name;
    public String dname;
    
    //public DBConnect(){
        
        //try{
        //    Class.forName("com.mysql.cj.jdbc.Driver");
            
        //    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
        //    st = con.createStatement();
            
       // }catch(Exception ex){
       //     System.out.println("Error:" + ex);
    //   }
    //}
    
    public void getData(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            st = con.createStatement();
            String query = "select name from try";
            rs = st.executeQuery(query);
            while(rs.next()){
                name = rs.getString("name");
                //String age = rs.getString("age");
                //System.out.println("Name: " + name + "      " + "Age: " + age)
            
        }
            st.close();
            con.close();
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        name = dname;
    }
    
    public void main(String[] args){
        getData();
    }
}
