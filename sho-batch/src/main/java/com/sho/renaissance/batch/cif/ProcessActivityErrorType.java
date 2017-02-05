
package com.sho.renaissance.batch.cif;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sho.renaissance.batch.cif.ProcessActivityErrorDetailType;
import com.sho.renaissance.batch.cif.ProcessActivityErrorType;


/**
 * <p>Java class for ProcessActivityErrorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessActivityErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processSequence" type="{http://com.capgemini/cif}CountType"/>
 *         &lt;element name="processIndex" type="{http://com.capgemini/cif}CountType"/>
 *         &lt;element name="processDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="sourceSystemRefId" type="{http://com.capgemini/cif}IdType" minOccurs="0"/>
 *         &lt;element name="payload" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusId" type="{http://com.capgemini/cif}IdType"/>
 *         &lt;element name="processActivityErrorDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processActivityErrorDetail" type="{http://com.capgemini/cif/iem}ProcessActivityErrorDetailType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessActivityErrorType", propOrder = {
    "processSequence",
    "processIndex",
    "processDate",
    "sourceSystemRefId",
    "payload",
    "errorMessage",
    "statusId",
    "errorType",
    "processActivityErrorDetails"
})
public class ProcessActivityErrorType {

    @XmlElement(defaultValue = "0")
    protected int processSequence;
    @XmlElement(defaultValue = "0")
    protected int processIndex;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar processDate;
    protected String sourceSystemRefId;
    @XmlElement(required = true)
    protected String payload;
    protected String errorMessage;
    @XmlElement(required = true)
    protected String statusId;
    @XmlElement(required= true)
    protected String errorType;
	@XmlElement(required = true)
    protected ProcessActivityErrorType.ProcessActivityErrorDetails processActivityErrorDetails;

    /**
     * Gets the value of the processSequence property.
     * 
     */
    public int getProcessSequence() {
        return processSequence;
    }

    /**
     * Sets the value of the processSequence property.
     * 
     */
    public void setProcessSequence(int value) {
        this.processSequence = value;
    }

    /**
     * Gets the value of the processIndex property.
     * 
     */
    public int getProcessIndex() {
        return processIndex;
    }

    /**
     * Sets the value of the processIndex property.
     * 
     */
    public void setProcessIndex(int value) {
        this.processIndex = value;
    }

    /**
     * Gets the value of the processDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProcessDate() {
        return processDate;
    }

    /**
     * Sets the value of the processDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProcessDate(XMLGregorianCalendar value) {
        this.processDate = value;
    }

    /**
     * Gets the value of the sourceSystemRefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystemRefId() {
        return sourceSystemRefId;
    }

    /**
     * Sets the value of the sourceSystemRefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystemRefId(String value) {
        this.sourceSystemRefId = value;
    }

    /**
     * Gets the value of the payload property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayload(String value) {
        this.payload = value;
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

    public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
    /**
     * Gets the value of the processActivityErrorDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessActivityErrorType.ProcessActivityErrorDetails }
     *     
     */
    public ProcessActivityErrorType.ProcessActivityErrorDetails getProcessActivityErrorDetails() {
        return processActivityErrorDetails;
    }

    /**
     * Sets the value of the processActivityErrorDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessActivityErrorType.ProcessActivityErrorDetails }
     *     
     */
    public void setProcessActivityErrorDetails(ProcessActivityErrorType.ProcessActivityErrorDetails value) {
        this.processActivityErrorDetails = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="processActivityErrorDetail" type="{http://com.capgemini/cif/iem}ProcessActivityErrorDetailType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "processActivityErrorDetail"
    })
    public static class ProcessActivityErrorDetails {

        @XmlElement(required = true)
        protected List<ProcessActivityErrorDetailType> processActivityErrorDetail;

        /**
         * Gets the value of the processActivityErrorDetail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processActivityErrorDetail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcessActivityErrorDetail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProcessActivityErrorDetailType }
         * 
         * 
         */
        public List<ProcessActivityErrorDetailType> getProcessActivityErrorDetail() {
            if (processActivityErrorDetail == null) {
                processActivityErrorDetail = new ArrayList<ProcessActivityErrorDetailType>();
            }
            return this.processActivityErrorDetail;
        }

        /**
         * Sets the value of the processActivityErrorDetail property.
         * 
         * @param processActivityErrorDetail
         *     allowed object is
         *     {@link ProcessActivityErrorDetailType }
         *     
         */
        public void setProcessActivityErrorDetail(List<ProcessActivityErrorDetailType> processActivityErrorDetail) {
            this.processActivityErrorDetail = processActivityErrorDetail;
        }

    }

}
