
package com.retailsols.dbutility;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import com.retailsols.ticket.TicketServer;

public class ValidateTicket {
	static Logger log = Logger.getLogger(
			ValidateTicket.class.getName());
    private static boolean b=false;
    private static Connection con=null;
    private static PreparedStatement ps=null;
    private static ResultSet rs=null;
    private static Date start_date=null;
    private static Date end_date=null;
    private static String flag=null;
    private static String tkttype=null;
    //private static Date today_date=null;
    private static SimpleDateFormat sdf=null;
    public static boolean validateTicket(String ticket_id){
        try {
            con=DB.getConnection();
            ps=con.prepareStatement(QueryConstants.getTicketDetails);
            ps.setString(1, ticket_id.trim());
            rs=ps.executeQuery();
            if(rs.next()){
            	// Ticket is Issued but need to Check (Returned / Validity)
            	
            	// generate attributes used further
            	 flag=rs.getString(1).trim();
                 start_date=rs.getDate(2);
                 end_date=rs.getDate(3);            
                 sdf=new SimpleDateFormat("yyyy-MM-dd");
                 tkttype=rs.getString(4);
                 java.util.Date  today_date=new java.util.Date();
                 java.sql.Date  ss= new java.sql.Date(today_date.getTime());
                 String td=sdf.format(today_date);
                 String st=sdf.format(start_date);
                 String end=sdf.format(end_date);
               // end of Generating Attibutes
                 
            	if(flag.equals("-1")){
            		// Ticket Already Returned
            		b =false;
            	}            	
            	else{         
            		// Ticket is Valid Need to Check Single Use ( used or unsed) or Multiple Use Ticket
//            		if(start_date.compareTo(end_date)==0){
            		if(tkttype.equalsIgnoreCase("Single Entry")){
            			// Ticket is Single Entry Ticket & Verify whether it is used or not  &  Verify whether the ticket is today's ticket or not 
            			if(Integer.parseInt(flag)>0){
            				b=false;
            			}
            			else if(td.equals(st) && td.equals(end)){
            				// Ticket is today's ticket
            				 PreparedStatement ps1=con.prepareStatement(QueryConstants.updateFlag);
                             ps1.setString(1, ticket_id);
                             ps1.executeUpdate();
                             DB.close(null, ps1, null);
                            // Ticket Validated and Flag Status Updated to 1.
                             // change status to true;
                             b=true;
            			}
            			else{
            				// Ticket is Valid But not Today's Ticket
            				// change status to false
            				b=false;
            			}
            			
            	}
            	else{
            			// Ticket is Multiple Entry Ticket
            			  if((st.equals(td) || ss.after(start_date)) && (end.equals(td) || ss.before(end_date))){
            				  // Multiple entry ticket valid
            				  // increase the count.......
            				  int ct=Integer.parseInt(flag)+1;
            				  PreparedStatement ps1=con.prepareStatement(QueryConstants.updateFlag1);
            				  ps1.setString(1, String.valueOf(ct));
                            ps1.setString(2, ticket_id);
                            ps1.executeUpdate();
                            DB.close(null, ps1, null);
            				  b=true;
            			  }
            			  else{
            				  // multiple entry ticket invalid
            				  b=false;
            			  }
            			
            		}  
            }            
           
            }
            else{
            	// Ticket is Not Yet Issued
                b=false;
            }


        } catch (Exception e) {
        	log.error(e.toString()+" --- "+e.getMessage());
            e.printStackTrace();
        }finally {
        	DB.close(rs, ps, null);
        }
        return b;
    }


}
