package com.concretepage.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://schemas.datacontract.org/2004/07/JustEnough.API.ImportExport.Objects.Model}AuthenticationToken" minOccurs="0"/&gt;
 *         &lt;element name="importInstruction" type="{http://schemas.datacontract.org/2004/07/JustEnough.API.ImportExport.Objects.Model}Instruction" minOccurs="0"/&gt;
 *         &lt;element name="batchId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "importInstruction",
    "batchId"
})
@XmlRootElement(name = "ImportData")
public class ImportData {

    @XmlElementRef(name = "token", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<AuthenticationToken> token;
    @XmlElementRef(name = "importInstruction", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<Instruction> importInstruction;
    @XmlElementRef(name = "batchId", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<Long> batchId;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link AuthenticationToken }{@code >}
     *     
     */
    public JAXBElement<AuthenticationToken> getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link AuthenticationToken }{@code >}
     *     
     */
    public void setToken(JAXBElement<AuthenticationToken> value) {
        this.token = value;
    }

    /**
     * Gets the value of the importInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Instruction }{@code >}
     *     
     */
    public JAXBElement<Instruction> getImportInstruction() {
        return importInstruction;
    }

    /**
     * Sets the value of the importInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Instruction }{@code >}
     *     
     */
    public void setImportInstruction(JAXBElement<Instruction> value) {
        this.importInstruction = value;
    }

    /**
     * Gets the value of the batchId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getBatchId() {
        return batchId;
    }

    /**
     * Sets the value of the batchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setBatchId(JAXBElement<Long> value) {
        this.batchId = value;
    }

}
