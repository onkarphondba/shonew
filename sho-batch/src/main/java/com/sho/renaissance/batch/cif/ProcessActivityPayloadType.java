//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.07 at 02:53:36 PM IST 
//


package com.sho.renaissance.batch.cif;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessActivityPayloadType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessActivityPayloadType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="payloadText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessActivityPayloadType", namespace = "http://com.capgemini/cif/dim", propOrder = {
    "payloadText"
})
public class ProcessActivityPayloadType {

    @XmlElement(required = true)
    protected String payloadText;

    /**
     * Gets the value of the payloadText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayloadText() {
        return payloadText;
    }

    /**
     * Sets the value of the payloadText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayloadText(String value) {
        this.payloadText = value;
    }

}
