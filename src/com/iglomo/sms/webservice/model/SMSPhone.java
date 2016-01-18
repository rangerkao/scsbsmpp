package com.iglomo.sms.webservice.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PHONE")
@XmlType(propOrder = { "schedule", "isMultiple", "message" })
public class SMSPhone {

}
