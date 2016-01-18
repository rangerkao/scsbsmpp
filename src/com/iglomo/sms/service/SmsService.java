package com.iglomo.sms.service;

import java.rmi.RemoteException;

public interface SmsService {

	public abstract String send(String requestXML) throws RemoteException;

}