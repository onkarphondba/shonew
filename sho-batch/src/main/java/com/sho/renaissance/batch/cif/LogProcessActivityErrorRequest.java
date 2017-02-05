
package com.sho.renaissance.batch.cif;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sho.renaissance.batch.cif.LogProcessActivityErrorRequest;
import com.sho.renaissance.batch.cif.ProcessActivityErrorType;
import com.sho.renaissance.batch.cif.ProcessActivityHeaderType;


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
 *         &lt;element name="processActivityHeader" type="{http://com.capgemini/cif}ProcessActivityHeaderType"/>
 *         &lt;element name="processActivityErrors">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="processActivityError" type="{http://com.capgemini/cif/iem}ProcessActivityErrorType" maxOccurs="unbounded"/>
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
@XmlType(name = "", propOrder = {
    "processActivityHeader",
    "processActivityErrors"
})
@XmlRootElement(name = "LogProcessActivityErrorRequest")
public class LogProcessActivityErrorRequest {

    @XmlElement(required = true)
    protected ProcessActivityHeaderType processActivityHeader;
    @XmlElement(required = true)
    protected LogProcessActivityErrorRequest.ProcessActivityErrors processActivityErrors;

    /**
     * Gets the value of the processActivityHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessActivityHeaderType }
     *     
     */
    public ProcessActivityHeaderType getProcessActivityHeader() {
        return processActivityHeader;
    }

    /**
     * Sets the value of the processActivityHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessActivityHeaderType }
     *     
     */
    public void setProcessActivityHeader(ProcessActivityHeaderType value) {
        this.processActivityHeader = value;
    }

    /**
     * Gets the value of the processActivityErrors property.
     * 
     * @return
     *     possible object is
     *     {@link LogProcessActivityErrorRequest.ProcessActivityErrors }
     *     
     */
    public LogProcessActivityErrorRequest.ProcessActivityErrors getProcessActivityErrors() {
        return processActivityErrors;
    }

    /**
     * Sets the value of the processActivityErrors property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogProcessActivityErrorRequest.ProcessActivityErrors }
     *     
     */
    public void setProcessActivityErrors(LogProcessActivityErrorRequest.ProcessActivityErrors value) {
        this.processActivityErrors = value;
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
     *         &lt;element name="processActivityError" type="{http://com.capgemini/cif/iem}ProcessActivityErrorType" maxOccurs="unbounded"/>
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
        "processActivityError"
    })
    public static class ProcessActivityErrors {

        @XmlElement(required = true)
        protected List<ProcessActivityErrorType> processActivityError;

        /**
         * Gets the value of the processActivityError property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the processActivityError property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProcessActivityError().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProcessActivityErrorType }
         * 
         * 
         */
        public List<ProcessActivityErrorType> getProcessActivityError() {
            if (processActivityError == null) {
                processActivityError = new ArrayList<ProcessActivityErrorType>();
            }
            return this.processActivityError;
        }

        /**
         * Sets the value of the processActivityError property.
         * 
         * @param processActivityError
         *     allowed object is
         *     {@link ProcessActivityErrorType }
         *     
         */
        public void setProcessActivityError(List<ProcessActivityErrorType> processActivityError) {
            this.processActivityError = processActivityError;
        }

    }

}
