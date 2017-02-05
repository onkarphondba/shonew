
package com.sho.renaissance.batch.cif;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProcessActivityHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessActivityHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processActivityHeaderId" type="{http://com.capgemini/cif}ProcessActivityHeaderIdType" minOccurs="0"/>
 *         &lt;element name="organizationId" type="{http://com.capgemini/cif}IdType"/>
 *         &lt;element name="originalProcessDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="processId" type="{http://com.capgemini/cif}IdType"/>
 *         &lt;element name="sourceSystemId" type="{http://com.capgemini/cif}IdType"/>
 *         &lt;element name="destinationSystemId" type="{http://com.capgemini/cif}IdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessActivityHeaderType", namespace = "http://com.capgemini/cif", propOrder = {
    "processActivityHeaderId",
    "organizationId",
    "originalProcessDatetime",
    "processId",
    "sourceSystemId",
    "destinationSystemId"
})
public class ProcessActivityHeaderType implements Serializable{

    protected String processActivityHeaderId;
    @XmlElement(required = true)
    protected String organizationId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected transient XMLGregorianCalendar originalProcessDatetime;
    @XmlElement(required = true)
    protected String processId;
    @XmlElement(required = true)
    protected String sourceSystemId;
    @XmlElement(required = true)
    protected String destinationSystemId;

    /**
     * Gets the value of the processActivityHeaderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessActivityHeaderId() {
        return processActivityHeaderId;
    }

    /**
     * Sets the value of the processActivityHeaderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessActivityHeaderId(String value) {
        this.processActivityHeaderId = value;
    }

    /**
     * Gets the value of the organizationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets the value of the organizationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationId(String value) {
        this.organizationId = value;
    }

    /**
     * Gets the value of the originalProcessDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOriginalProcessDatetime() {
        return originalProcessDatetime;
    }

    /**
     * Sets the value of the originalProcessDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOriginalProcessDatetime(XMLGregorianCalendar value) {
        this.originalProcessDatetime = value;
    }

    /**
     * Gets the value of the processId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * Sets the value of the processId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessId(String value) {
        this.processId = value;
    }

    /**
     * Gets the value of the sourceSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystemId() {
        return sourceSystemId;
    }

    /**
     * Sets the value of the sourceSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystemId(String value) {
        this.sourceSystemId = value;
    }

    /**
     * Gets the value of the destinationSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationSystemId() {
        return destinationSystemId;
    }

    /**
     * Sets the value of the destinationSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationSystemId(String value) {
        this.destinationSystemId = value;
    }

}
