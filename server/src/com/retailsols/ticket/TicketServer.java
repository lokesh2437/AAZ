package com.retailsols.ticket;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import com.retailsols.dbutility.ValidateTicket;
import com.retailsols.dbutility.VerifyEmployee;

public class TicketServer{

	/**
	 * 
	 */
	static Logger log = Logger.getLogger(
			  TicketServer.class.getName());
	private static final long serialVersionUID = 1L;
	String address;
	int port=3232;
	Registry registry;
	
	public boolean getEmpStatus(String uname,String pass){
		boolean flag=false;
		try {		
			flag=VerifyEmployee.verifyEmplyee(uname, pass);	
		} catch (Exception e) {
			log.error(e.toString()+" --- "+e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean getStatus(){
		return true;
	}
	
	public boolean getTicketStatus(String ticketid){
		boolean flag=false;
		try {			
			flag=ValidateTicket.validateTicket(ticketid);
		} catch (Exception e) {
			log.error(e.toString()+" --- "+e.getMessage());
			e.printStackTrace();
		}
		return flag;		
	}

}
