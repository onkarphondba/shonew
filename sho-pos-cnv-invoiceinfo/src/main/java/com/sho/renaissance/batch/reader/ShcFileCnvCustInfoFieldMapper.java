package com.sho.renaissance.batch.reader;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import com.capgemini.cif.core.util.DateUtils;
import com.sho.renaissance.batch.constants.BatchConstants;
import com.sho.renaissance.batch.domain.ShcFileCnvCustInfo;


/**
 * @author ophondba
 * This class is a mapper for data coming from SHC. 
 */
public class ShcFileCnvCustInfoFieldMapper implements FieldSetMapper<ShcFileCnvCustInfo>{

	private static Log logger = LogFactory.getLog(ShcFileCnvCustInfoFieldMapper.class);
	private String sourceSystem;
	private String correlationId;
	
	@Override
	public ShcFileCnvCustInfo mapFieldSet(FieldSet fieldSet) {
		
		ShcFileCnvCustInfo cnvCustInfo = new ShcFileCnvCustInfo();
		String[] values = fieldSet.getValues();
		
		if(values.length == 0)
		{
			return null;
		}
		
		if(values.length < 25){
			int length = values.length;
			values = Arrays.copyOf(values, 25);
			for(int i=length; i<25 ;i++)
				values[i] = null;
		}	
		
		cnvCustInfo.setCustomerId(values[0]);
		cnvCustInfo.setCustomerIdentifier(values[1]);
		cnvCustInfo.setCustomerType(values[2]);
		cnvCustInfo.setFirstName(values[3]);
		cnvCustInfo.setMiddleName(values[4]);
		cnvCustInfo.setLastName(values[5]);
		cnvCustInfo.setCompanyName(values[6]);
		cnvCustInfo.setCategoryId(values[7]);
		cnvCustInfo.setComments(values[8]);
		cnvCustInfo.setEmail(values[9]);
		cnvCustInfo.setPhone(values[10]);
		cnvCustInfo.setSubsidaryId(values[11]);
		cnvCustInfo.setUrl(values[12]);
		cnvCustInfo.setShippingAddrLine1(values[13]);
		cnvCustInfo.setShippingAddrLine2(values[14]);
		cnvCustInfo.setShippingCity(values[15]);
		cnvCustInfo.setShippingState(values[16]);
		cnvCustInfo.setShippingZip(values[17]);
		cnvCustInfo.setIsDefaultShippingAddress(values[18]);
		cnvCustInfo.setBillAddrLine1(values[19]);
		cnvCustInfo.setBillAddrLine2(values[20]);
		cnvCustInfo.setBillCity(values[21]);
		cnvCustInfo.setBillState(values[22]);
		cnvCustInfo.setBillZip(values[23]);
		cnvCustInfo.setIsDefaultBillAddress(values[24]);
		cnvCustInfo.setIsValid("Yes");
		cnvCustInfo.setSource(sourceSystem);
		cnvCustInfo.setCorrelationid(correlationId);
		
		if(cnvCustInfo.getCustomerIdentifier()==null || cnvCustInfo.getSubsidaryId()==null){
			cnvCustInfo.setIsValid("No");
		}
		
		
		return cnvCustInfo;
		
	}


	

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}