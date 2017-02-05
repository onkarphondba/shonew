package com.sho.renaissance.batch.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.concretepage.wsdl.AuthenticateUser;
import com.concretepage.wsdl.AuthenticateUserResponse;
import com.concretepage.wsdl.BeginBatchResponse;
import com.concretepage.wsdl.EndBatch;
import com.concretepage.wsdl.EndBatchResponse;
import com.concretepage.wsdl.ImportData;
import com.concretepage.wsdl.ImportDataResponse;

public class JEClient extends WebServiceGatewaySupport  {
	
    private static Log logger = LogFactory.getLog(JEClient.class);
	
	public AuthenticateUserResponse AuthenticateUser(AuthenticateUser user) {
		return (AuthenticateUserResponse) getWebServiceTemplate().marshalSendAndReceive(
				user, new SoapActionCallback("http://tempuri.org/IJustEnoughAPIService/AuthenticateUser"));
	}
	
	
	public BeginBatchResponse BeginBatch(com.concretepage.wsdl.BeginBatch batch) {
		return (BeginBatchResponse) getWebServiceTemplate().marshalSendAndReceive(
				batch, new SoapActionCallback("http://tempuri.org/IJustEnoughAPIService/BeginBatch"));
	}
	
	public ImportDataResponse ImportData(ImportData data){
		return (ImportDataResponse) getWebServiceTemplate().marshalSendAndReceive(
				data, new SoapActionCallback("http://tempuri.org/IJustEnoughAPIService/ImportData"));
	}
	
	
	public EndBatchResponse EndBatch(EndBatch endBatch){
		return (EndBatchResponse) getWebServiceTemplate().marshalSendAndReceive(
				endBatch, new SoapActionCallback("http://tempuri.org/IJustEnoughAPIService/EndBatch"));
	}
}