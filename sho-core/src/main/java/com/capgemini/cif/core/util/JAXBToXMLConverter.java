package com.capgemini.cif.core.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.capgemini.cif.core.validation.ConcreteComponentProduct;

public final class JAXBToXMLConverter {

	private static Log logger = LogFactory.getLog(JAXBToXMLConverter.class);
	
	public static String jaxbObjectToXML(ConcreteComponentProduct product) throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(product.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			m.marshal(product, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			
			throw e;
		}
	}
	
	public static ConcreteComponentProduct jaxbXMLToObject(Object product, String xml)
			throws JAXBException {

		try {
			JAXBContext context = JAXBContext.newInstance(product.getClass());
			Unmarshaller m = context.createUnmarshaller();

			ConcreteComponentProduct object = (ConcreteComponentProduct) m.unmarshal(new StringReader(xml));
			return object;
		} catch (JAXBException e) {
			
			throw e;
		}
	}
	
}
