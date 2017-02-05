package com.sho.renaissance.batch.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.capgemini.cif.core.domain.NetSuiteWebService;
import com.capgemini.cif.core.exception.ExceptionHandler;
import com.capgemini.cif.core.exception.GenericCoreException;
import com.netsuite.webservices.platform.core_2016_1.DataCenterUrls;
import com.netsuite.webservices.platform.core_2016_1.GetDataCenterUrlsResult;
import com.netsuite.webservices.platform.core_2016_1.Passport;
import com.netsuite.webservices.platform.core_2016_1.RecordRef;
import com.netsuite.webservices.platform.messages_2016_1.ApplicationInfo;
import com.netsuite.webservices.platform.messages_2016_1.Preferences;
import com.netsuite.webservices.platform_2016_1.NetSuiteBindingStub;
import com.netsuite.webservices.platform_2016_1.NetSuitePortType;
import com.netsuite.webservices.platform_2016_1.NetSuiteServiceLocator;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.listener.DimProcessExcecutionListner;

public class NetSuiteWebServiceUtils {

	private static Log logger = LogFactory.getLog(NetSuiteWebServiceUtils.class);

	public NetSuitePortType _port = null;
	DimProcessExcecutionListner dimProcessExcecutionListner;
	private NetSuiteWebService netSuiteWebService;
	private String SOAPHeader="urn:messages{version}.platform.webservices.netsuite.com";

	public NetSuiteWebServiceUtils() {

	}

	public NetSuitePortType getPort()
			throws IOException, SOAPException, InterruptedException, GenericCoreException, ServiceException {

		String retryCount = CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.NETSUITE_CONNECTIVITY_RETRY_COUNT);

		Integer sleepTime = Integer.valueOf(CommonUtils.getPropertyFromClassPath(
				BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.NETSUITE_CONNECTIVITY_SLEEP_COUNT));

		for (Integer retryCounter = 0; retryCounter < Integer.valueOf(retryCount); retryCounter++) {
			this.configureService();
			if (null != _port) {
				break;
			}
			Thread.sleep(sleepTime);
			sleepTime *= 2;
		}

		if (null == _port) {
			throw ExceptionHandler.createException("sho.common.error.technical.endpointconnection.axisfault",
					"Could not get port");
		}

		this.setPreferences();
		return _port;
	}

	private void configureService() throws IOException, ServiceException, GenericCoreException, InterruptedException {
		System.setProperty("axis.socketSecureFactory", "org.apache.axis.components.net.SunFakeTrustSocketFactory");

		NetSuiteServiceLocator service1 = new NetSuiteServiceLocator();

		String account = netSuiteWebService.getNsAccountId();
		service1.setMaintainSession(true);
		service1.setNetSuitePortEndpointAddress(netSuiteWebService.getNsEndpointUrl());

		URL url = new URL(netSuiteWebService.getNsEndpointUrl());

		_port = service1.getNetSuitePort(url);

		// NetSuite Timeout is 60 sec
		((NetSuiteBindingStub) _port).setTimeout(BatchConstants.NS_TIMEOUT * 60 * 60);

		GetDataCenterUrlsResult dataCenterUrlsResult = null;
		DataCenterUrls urls = null;
		Integer sleepTime = Integer.valueOf(CommonUtils.getPropertyFromClassPath(
				BatchConstants.APPLICATION_PROPERTY_FILE, BatchConstants.NETSUITE_CONNECTIVITY_SLEEP_COUNT));
		int retry_count = Integer.valueOf(CommonUtils.getPropertyFromClassPath(BatchConstants.APPLICATION_PROPERTY_FILE,
				BatchConstants.NETSUITE_CONNECTIVITY_RETRY_COUNT));

		int retry_counter = 0;
		do {
			logger.debug("Init dataCenterUrlsResult Retry count: " + retry_counter);
			dataCenterUrlsResult = _port.getDataCenterUrls(account);

			retry_counter++;
			if (dataCenterUrlsResult == null) {
				Thread.sleep(sleepTime);
			} else {
				break;
			}
		} while (retry_counter <= retry_count);

		if (dataCenterUrlsResult == null) {
			
			throw ExceptionHandler.createException("sho.common.error.technical.endpointconnection.axisfault",
					"Could not get GetDataCenterUrlsResult");

		}

		retry_counter = 0;

		do {
			logger.debug("Init DataCenterUrls Retry count: " + retry_counter);
			urls = dataCenterUrlsResult.getDataCenterUrls();
			retry_counter++;
			if (urls == null) {
				Thread.sleep(sleepTime);
			} else {
				break;
			}
		} while (retry_counter <= retry_count);

		if (urls == null) {
			
			throw ExceptionHandler.createException("sho.common.error.technical.endpointconnection.axisfault",
					"Could not get DataCentralUrls");

		}

		String wsDomain = urls.getWebservicesDomain();
		if (wsDomain.isEmpty()) {
			
			throw ExceptionHandler.createException("sho.common.error.technical.endpointconnection.axisfault",
					"Webservice domain is empty");

		}
		
		String new_url = wsDomain.concat(url.getPath());
		logger.error("Url : " + new_url);
		_port = service1.getNetSuitePort(new URL(new_url));

	}

	private void setPreferences() throws SOAPException, UnsupportedEncodingException {
		NetSuiteBindingStub stub = (NetSuiteBindingStub) _port;
		stub.clearHeaders();
		SOAPHeaderElement userPassportHeader = new SOAPHeaderElement(SOAPHeader.replace("{version}", ""),
				"passport");
		userPassportHeader.setObjectValue(prepareLoginPassport());
		stub.setHeader(userPassportHeader);
		stub.setHeader(createApplicationIdHeaders());
		SOAPHeaderElement platformPrefHeader = new SOAPHeaderElement(SOAPHeader.replace("{version}", ""),
				"preferences");
		Preferences pref = new Preferences();
		pref.setIgnoreReadOnlyFields(Boolean.TRUE);
		platformPrefHeader.setObjectValue(pref);
		stub.setHeader(platformPrefHeader);
		
		_port = (NetSuitePortType)stub;
		
	}

	private Passport prepareLoginPassport() {
		Passport passport = new Passport();
		RecordRef role = new RecordRef();
		passport.setEmail(netSuiteWebService.getNsUserName());
		passport.setPassword(netSuiteWebService.getNsPassKey());
		role.setInternalId(netSuiteWebService.getNsRoleId());
		passport.setRole(role);
		passport.setAccount(netSuiteWebService.getNsAccountId());
		return passport;
	}

	/**
	 * Creates header element for application id
	 * 
	 * @return header element with application id
	 * @throws UnsupportedEncodingException
	 */
	private SOAPHeaderElement createApplicationIdHeaders() throws UnsupportedEncodingException {
		ApplicationInfo applicationInfo = new ApplicationInfo();
		applicationInfo.setApplicationId(netSuiteWebService.getNsApplicationId());
		return new SOAPHeaderElement(SOAPHeader.replace("{version}", "_"+netSuiteWebService.getNsCurrentVersion()),
				/*"urn:messages_" + netSuiteWebService.getNsCurrentVersion() + ".platform.webservices.netsuite.com",*/
				"applicationInfo", applicationInfo);
	}

	public void setNetSuiteWebService(NetSuiteWebService netSuiteWebService) {
		this.netSuiteWebService = netSuiteWebService;
	}

	public void setDimProcessExcecutionListner(DimProcessExcecutionListner dimProcessExcecutionListner) {
		this.dimProcessExcecutionListner = dimProcessExcecutionListner;
	}

}
