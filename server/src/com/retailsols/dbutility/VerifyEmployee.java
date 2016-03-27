/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.retailsols.dbutility;

import com.retailsols.encryption.BaseKeyStoreEncryptionService;
import com.retailsols.ticket.TicketServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class VerifyEmployee {
	static Logger log = Logger.getLogger(
			VerifyEmployee.class.getName());
    private static boolean b=false;
    private static String emp_salt=null;
    private static String emp_enc_db=null;
    private static String emp_enc_prg=null;
    private static Connection con=null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    public static boolean verifyEmplyee(String emp_id,String emp_pass){
        try {
        	
            con=DB.getConnection();
            ps=con.prepareStatement(QueryConstants.getEmpSalt);
            ps.setString(1, emp_id);
            rs=ps.executeQuery();
          
            if(rs.next()){
            	
                emp_salt=rs.getString(1);
                emp_enc_db=rs.getString(2);
                byte[] encBytes=new BaseKeyStoreEncryptionService().superHash(emp_pass.getBytes(),emp_salt, false);
                emp_enc_prg=new BaseKeyStoreEncryptionService().getBase64encode(encBytes);
                if(emp_enc_db.equals(emp_enc_prg)){                	
                    b=true;
                }
                else{
                    b=false;
                }
            }
            else{
                b=false;
            }
            
        } catch (Exception e) {
        	log.error(e.toString()+" --- "+e.getMessage());
            e.printStackTrace();
        } 
        finally{
        	DB.close(rs, ps, null);
        }
        return b;
    }
}
