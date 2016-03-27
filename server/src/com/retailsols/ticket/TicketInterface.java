package com.retailsols.ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicketInterface extends Remote{

	public boolean getTicketStatus(String ticketid) throws RemoteException;
	
}
