package com.capgemini.cif.core.constants;

public enum NetSuiteSiteAttributeEnum {
	Location_ID("Location ID","Value_Longstring"),Sublocation_Of("Sublocation of","Value_Longstring"),Inactive("Inactive","Value_Bit"),Location_Type("Location Type","AttributeLookupCode"),Store_Type("Store Type","AttributeLookupCode"),Ownership_Type("Ownership Type","AttributeLookupCode"),Location_Description("Location Description","Value_Longstring"),Sister_Store("Sister Store","Value_Longstring"),Franchisee_Dealer("Franchisee/Dealer","Value_Longstring"),Store_Size("Store Size","Value_Shortstring"),Store_Size_Unit("Store Size Unit","AttributeLookupCode"),Address_City("Address - City","Value_Longstring"),Address_State("Address - State","Value_Longstring"),Address_Zip("Address - Zip","Value_Longstring"),Test_Store("Test Store","Value_Bit"),Comp_Store("Comp Store","Value_Bit"),Primary_DDC("Primary DDC","Value_Longstring"),Primary_RRC("Primary RRC","Value_Longstring"),Primary_MDO("Primary MDO","Value_Longstring"),Climate_Zone("Climate Zone","AttributeLookupCode"),Open_Date("Open Date","Value_Datetime"),Comp_Store_Date("Comp Store Date","Value_Datetime"),Close_Date("Close Date","Value_Datetime"),Going_Out_of_Business_Date("Going Out of Business (GOB) Date","Value_Datetime");

	//EnumMap<String, String> map =new EnumMap<String, String>(NetSuiteSiteAttributeEnum.class);
	private String siteAttributeType;
	private String value;

	NetSuiteSiteAttributeEnum(String siteAttributeType, String value) {
		this.siteAttributeType = siteAttributeType;
		this.value = value;

	}

	public String siteAttributeType() {
		return siteAttributeType;
	}
	public String value() {
		return value;
	}
	
	
	
}
