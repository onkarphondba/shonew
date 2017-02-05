
package com.sho.renaissance.batch.cif;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessActivityErrorDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessActivityErrorDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processLevel" type="{http://com.capgemini/cif}WholeNumberType"/>
 *         &lt;element name="processSequence" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusId" type="{http://com.capgemini/cif}IdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessActivityErrorDetailType", propOrder = {
    "processLevel",
    "processSequence",
    "errorMessage",
    "statusId"
})
public class ProcessActivityErrorDetailType {

    protected int processLevel;
    @XmlElement(required = true)
    protected String processSequence;
    @XmlElement(required = true)
    protected String errorMessage;
    @XmlElement(required = true)
    protected String statusId;

    /**
     * Gets the value of the processLevel property.
     * 
     */
    public int getProcessLevel() {
        return processLevel;
    }

    /**
     * Sets the value of the processLevel property.
     * 
     */
    public void setProcessLevel(int value) {
        this.processLevel = value;
    }

    /**
     * Gets the value of the processSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessSequence() {
        return processSequence;
    }

    /**
     * Sets the value of the processSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessSequence(String value) {
        this.processSequence = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the statusId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusId() {
        return statusId;
    }

    /**
     * Sets the value of the statusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusId(String value) {
        this.statusId = value;
    }

}
