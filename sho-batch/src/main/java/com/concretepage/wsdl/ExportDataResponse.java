//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.28 at 02:19:50 PM IST 
//


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
 *         &lt;element name="ExportDataResult" type="{http://schemas.datacontract.org/2004/07/JustEnough.API.ImportExport.Objects.Model}ArrayOfResult" minOccurs="0"/&gt;
 *         &lt;element name="exportInstruction" type="{http://schemas.datacontract.org/2004/07/JustEnough.API.ImportExport.Objects.Model}Instruction" minOccurs="0"/&gt;
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
    "exportDataResult",
    "exportInstruction"
})
@XmlRootElement(name = "ExportDataResponse")
public class ExportDataResponse {

    @XmlElementRef(name = "ExportDataResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfResult> exportDataResult;
    @XmlElementRef(name = "exportInstruction", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<Instruction> exportInstruction;

    /**
     * Gets the value of the exportDataResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfResult }{@code >}
     *     
     */
    public JAXBElement<ArrayOfResult> getExportDataResult() {
        return exportDataResult;
    }

    /**
     * Sets the value of the exportDataResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfResult }{@code >}
     *     
     */
    public void setExportDataResult(JAXBElement<ArrayOfResult> value) {
        this.exportDataResult = value;
    }

    /**
     * Gets the value of the exportInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Instruction }{@code >}
     *     
     */
    public JAXBElement<Instruction> getExportInstruction() {
        return exportInstruction;
    }

    /**
     * Sets the value of the exportInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Instruction }{@code >}
     *     
     */
    public void setExportInstruction(JAXBElement<Instruction> value) {
        this.exportInstruction = value;
    }

}
