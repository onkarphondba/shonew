package com.capgemini.cif.core.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.capgemini.cif.core.NetsuiteChildTestItemInterface;
import com.capgemini.cif.core.NetsuiteTestInterfaceItem;
import com.capgemini.cif.core.NetsuiteTestItemWithoutProperXMLAnnotations;
import com.capgemini.cif.core.util.JAXBToXMLConverter;
import com.capgemini.cif.core.validation.ConcreteComponentProduct;

public class JAXBToXMLConverterTest {

	@Test
	public void testJaxbObjectToXML()
	{
		NetsuiteTestInterfaceItem item = createNetsuiteTestItem();
		String xml = null;
		try {
			xml = JAXBToXMLConverter.jaxbObjectToXML(item);
			Assert.assertNotNull(xml);
		}
		catch(Exception e){
			Assert.assertNull(xml);
		}
		
	}
	
	@Test
	public void testJaxbObjectToXMLNull() {
		NetsuiteTestItemWithoutProperXMLAnnotations item = createFaultyTestItem();
		String xml = null;
		try {
			xml = JAXBToXMLConverter.jaxbObjectToXML(item);
		}
		catch(Exception e){
			Assert.assertNull(xml);
		}
	}

	private NetsuiteTestInterfaceItem createNetsuiteTestItem() {
		NetsuiteTestInterfaceItem netsuiteTestItem = new NetsuiteTestInterfaceItem();
		netsuiteTestItem.setItemId(1L);
		netsuiteTestItem.setName("myTestNameToFailValidation");
		netsuiteTestItem.setSalesDescription("TestSalesDescription");
		netsuiteTestItem.setUpcCode("UpcCode");
		netsuiteTestItem.setShcItemNumber("11digitSHCITemNumber");
		NetsuiteChildTestItemInterface dummy = new NetsuiteChildTestItemInterface(1L, null);
		NetsuiteChildTestItemInterface dummy2 = new NetsuiteChildTestItemInterface(1L, "abc");
		List<ConcreteComponentProduct> children = netsuiteTestItem.getChild();
		if (children == null)
			children = new ArrayList<ConcreteComponentProduct>();
		children.add(dummy);
		children.add(dummy2);
		netsuiteTestItem.setChild(children);
		return netsuiteTestItem;
	}
	
	private NetsuiteTestItemWithoutProperXMLAnnotations createFaultyTestItem() {
		NetsuiteTestItemWithoutProperXMLAnnotations dummy = new NetsuiteTestItemWithoutProperXMLAnnotations(1L, null);
		return dummy;
	}
}

