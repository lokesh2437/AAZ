package com.retailsols.dbutility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DB {
    
	static Logger log = Logger.getLogger(
			  DB.class.getName());
    private static Connection con=null;
    private DB(){
        try {
        	log.info("Trying to get the DataSource Object");
//        	Properties p=new Properties();
//        	p.load(this.getClass().getResourceAsStream("db.properties"));
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//        	con=DriverManager.getConnection("jdbc:oracle:thin:@"+p.getProperty("host")+":"+p.getProperty("port")+":"+p.getProperty("sid"),p.getProperty("username"),p.getProperty("password"));
         	
        	Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("jdbc/DimpDataSource");			
			con= dataSource.getConnection();
		
        	 log.info("Data Source Object Acquired Sucessfully");
                
        } catch (Exception e) {
        	log.error(e.toString()+" --- "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        if(con==null){
            new DB();
            return con;
        }
        else{
            return con;
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn)
	{
	    if (rs!=null)
	    {
	        try
	        {
	            rs.close();

	        }
	        catch(SQLException e)
	        {
	            log.error("The result set cannot be closed.", e);
	        }
	    }
	    if (ps != null)
	    {
	        try
	        {
	            ps.close();
	        } catch (SQLException e)
	        {
	            log.error("The statement cannot be closed.", e);
	        }
	    }
	    if (conn != null)
	    {
	        try
	        {
	            conn.close();
	        } catch (SQLException e)
	        {
	            log.error("The data source connection cannot be closed.", e);
	        }
	    }

	}
    
}

