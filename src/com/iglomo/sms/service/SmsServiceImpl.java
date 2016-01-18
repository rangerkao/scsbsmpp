package com.iglomo.sms.service;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.iglomo.sms.webservice.client.SMPPServicesStub;
import com.iglomo.sms.webservice.client.SMPPServicesStub.SendSMPP;
import com.iglomo.sms.webservice.client.SMPPServicesStub.SendSMPPResponse;

public class SmsServiceImpl implements SmsService {
	
	private SMPPServicesStub wsClient;
	
	
	@Override
	public String send( String requestXML) throws RemoteException{
		
		this.wsClient = new SMPPServicesStub();
		
		SendSMPP data = new SendSMPP();
		
		data.setArgs0( requestXML);
		
		SendSMPPResponse smppResponse = this.wsClient.sendSMPP(data);
		
		String responseXML = smppResponse.get_return();
		
		return responseXML;		
		
	}
	
	
	
	
}
