package com.retailsols.ticket;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EmployeeInterface extends Remote{

	public boolean getEmpStatus(String uname,String pass) throws RemoteException;
	
}
