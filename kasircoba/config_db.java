/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasircoba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author YUMA
 */
public class config_db {
   Connection con;
   public static Connection config_db() {
       try{
           Class.forName("com.mysql.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project_pbo","root","");
           return con;
       } catch (ClassNotFoundException | SQLException e){
           JOptionPane.showMessageDialog(null, e);
           return null;
       }
   }
}
