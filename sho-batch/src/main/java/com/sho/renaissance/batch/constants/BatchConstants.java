package com.sho.renaissance.batch.constants;

/**
 * @author gdhimate
 *
 * Batch Constants
 */
public class BatchConstants {
	// Common file partitioner query
	public static final String FILE_SELECT_CLAUSE = "SELECT bi.id FROM batch_item bi ";
	public static final String FILE_WHERE_CLAUSE = "WHERE bi.correlation_id = ':correlationId' AND entity_type =':entityType' AND bi.isValid =1 ";
	public static final String FILE_ORDER_BY_CLAUSE = "ORDER BY bi.id ";
	public static final String FILE_FIRST_RUN_CLAUSE = "AND bi.sent_to_target = 0 AND bi.sent_to_target_at IS NULL ";
	
	public static final String INV_FILE_SELECT_CLAUSE = "SELECT bi.id FROM inv_batch_item bi ";
	public static final String INV_FILE_WHERE_CLAUSE = "WHERE bi.correlation_id = ':correlationId' AND entity_type =':entityType' AND bi.isValid =1 ";
	public static final String INV_FILE_ORDER_BY_CLAUSE = "ORDER BY bi.id ";
	public static final String INV_FILE_FIRST_RUN_CLAUSE = "AND bi.sent_to_target = 0 AND bi.sent_to_target_at IS NULL ";
	
	// Common NetSuite dataload partitioner query
	public static final String TEMP_DATALOAD_SELECT_CLAUSE = "select id from temp_dataload_rpcm_clusterfeed_enriched ";
	public static final String TEMP_DATALOAD_WHERE_CLAUSE = "WHERE updated_in_netsuite = 0 and location_id is not null and input_data_error = 0 and lookup_failure = 0 ";
	public static final String TEMP_DATALOAD_ORDER_BY_CLAUSE = "ORDER BY id ";
	
	// Partitioner query for CNV Historical Inventory
	public static final String CNV_TEMP_DATALOAD_SELECT_CLAUSE = "select id ";
	public static final String CNV_TEMP_DATALOAD_FROM_CLAUSE = " from temp_dataload_shc_cnvhistoricalinventory_enriched ";
	public static final String CNV_TEMP_DATALOAD_OREDRBY_CLAUSE = " ORDER BY id ASC ";
	
	
	// Partitioner query for item
	public static final String ITEM_SELECT_CLAUSE = "select rownum r from ( ";
	public static final String ITEM_FROM_CLAUSE = "SELECT NAME ITEM_ID FROM ITEMS ";
	public static final String ITEM_WHERE_CLAUSE = "WHERE ITEM_ID >0 AND ITEM_DESIGNATION_ID in (SELECT list_id from ITEM_DESIGNATION_VALUES where list_item_name in ('NEW ITEM','AS-IS MASTER ITEM')) and type_name='Inventory Item' )";
	
	public static final String ITEM_DIRECTIVE = "item.je.directive";
	
	// Partitioner query for CnvItemBuild991
	//INVENTORYITEM
	public static final String CNV_TEMP_INVENTORYITEM_SELECT_CLAUSE = "select rownum r from ( ";
	public static final String CNV_TEMP_INVENTORYITEM_FROM_CLAUSE = "SELECT LIST_ITEM_NAME 'ITEM DESIGNATION', SHC_ITEM_NUMBER, item_id FROM ITEMS JOIN ITEM_DESIGNATION_VALUES ON ITEMS.ITEM_DESIGNATION_ID = ITEM_DESIGNATION_VALUES.LIST_ID ";
	public static final String CNV_TEMP_INVENTORYITEM_WHERE_CLAUSE = "WHERE LIST_ITEM_NAME IN ('AS-IS MASTER ITEM', 'NEW ITEM') ";
	public static final String CNV_TEMP_INVENTORYITEM_OREDRBY_CLAUSE = ") ORDER BY r ASC ";
	//INVENTORYADJUSTMENT
	public static final String CNV_TEMP_INVENTORYADJUSTMENT_SELECT_CLAUSE = "select rownum r from ( ";
	public static final String CNV_TEMP_INVENTORYADJUSTMENT_FROM_CLAUSE = "select l.location_id, l.name 'Location Name', l.pos_number,  sub.name 'Subsidiary',subsidiary_id 'Subsidiary Internal ID', BIN_ID 'Bin Internal ID', BIN_NUMBER from locations l left outer join  subsidiary_location_map slm  on slm.location_id=l.location_id join subsidiaries sub on sub.subsidiary_id = slm.subsidiary_id left outer join  BINS  on  LOCATIONS.LOCATION_ID  = BINS.LOCATION_ID ";
	public static final String CNV_TEMP_INVENTORYADJUSTMENT_WHERE_CLAUSE = "where BIN_NUMBER  LIKE '%Saleable%' ";
	public static final String CNV_TEMP_INVENTORYADJUSTMENT_OREDRBY_CLAUSE = ") ORDER BY r ASC ";
	//CADENCE
	public static final String CNV_TEMP_CADENCE_SELECT_CLAUSE = "select rownum r from ( ";
	public static final String CNV_TEMP_CADENCE_FROM_CLAUSE = "SELECT CADENCE_TABLE_LINE_ID 'Cadence Level Internal ID', CADENCE_TABLE_LINE_NAME 'Cadence Level Name' from CADENCE_TABLE_LINE ";
	public static final String CNV_TEMP_CADENCE_OREDRBY_CLAUSE = ") ORDER BY r ASC ";
	
	//PRICELIST
	public static final String CNV_TEMP_PRICELIST_SELECT_CLAUSE = "select rownum r from ( ";
	public static final String CNV_TEMP_PRICELIST_FROM_CLAUSE = "select distinct NAME 'Price Level',ITEM_PRICE_ID 'Price Level Internal ID' from item_prices ";
	public static final String CNV_TEMP_PRICELIST_WHERE_CLAUSE = "WHERE NAME IN ('Base Price', 'Alternate Price 2', 'Alternate Price 3', 'Alternate Price 1', '201', '202', '203', '204', '205', '206', 'Online Price') ";
	public static final String CNV_TEMP_PRICELIST_OREDRBY_CLAUSE = ") ORDER BY r ASC ";
	
	
	// partitioner query for site
	//public static final String SITE_WHERE_CLAUSE = "AND locations.isinactive = 'No'";
	//public static final String SITE_PRIMARY_QUERY = "SELECT locations.location_id " + "FROM locations LEFT OUTER JOIN contacts ON locations.location_contact_id = contacts.contact_id RIGHT OUTER JOIN location_details ON locations.location_id = location_details.location_id JOIN location_types ON locations.location_type_id = location_types.list_id AND location_types.list_item_name IN ('ORDC', 'Retail', 'DDC', 'RRC', 'DC') AND REGEXP_COUNT(location_details.location_details_name ,':') = 3 ";

	public static final String SITE_QUERY = "select rownum r from (SELECT locations.location_id,"+
			"       locations.name,   "+
			"       CASE  "+
			"       when   "+
			"       locations.location_desc is null  "+
			"       then locations.name  "+
			"       else  locations.location_desc  "+
			"       end 'Location Description',  "+
			"       locations.country,  "+
			"       locations.isinactive,  "+
			"       contacts.full_name,  "+
			"       locations.address_one,  "+
			"       locations.address_two,   "+
			"       locations.city, locations.state,  "+
			"       locations.zipcode,   "+
			"       locations.phone,   "+
			"       locations.fax_,   "+
			"       locations.location_email,  "+
			"        REGEXP_SUBSTR(locations.full_name, '[^:]+',1,1) SiteGroup1,  "+
			"        REGEXP_SUBSTR(locations.full_name, '[^:]+',1,2) SiteGroup2,  "+
			"        REGEXP_SUBSTR(locations.full_name, '[^:]+',1,3) SiteGroup3,  "+
			"        REGEXP_SUBSTR(locations.full_name, '[^:]+',1,5) SiteGroup4,  "+
			"        REGEXP_SUBSTR(locations.full_name, '[^:]+',1,6) SiteGroup5, "+
			"        NULL SiteGroup6  "+
			"        FROM locations LEFT OUTER JOIN contacts  "+
			"        ON locations.location_contact_id = contacts.contact_id  "+
			"        JOIN location_types  "+
			"        ON locations.location_type_id = location_types.list_id  "+
			"        AND location_types.list_item_name IN ('ORDC', 'Retail', 'SHO DDC', 'SHO RRC', 'DC','SHO RRC (3PL)','SHO DDC (3PL)')  )";
	
	public static final String SITE_VIRTUAL_STORE_QUERY = "select rownum r from ( select VIRTUAL_STORE.VIRTUAL_STORE_NAME, "+
			"CASE  "+
			"when   "+
			"VIRTUAL_STORE.LOCATION_DESCRIPTION is null  "+
			"then VIRTUAL_STORE.VIRTUAL_STORE_NAME "+
			"else  VIRTUAL_STORE.LOCATION_DESCRIPTION "+
			"end 'Location Description', "+
			"VIRTUAL_STORE.IS_INACTIVE, "+
			"VIRTUAL_STORE.ADDRESS_LINE_1, "+
			"VIRTUAL_STORE.ADDRESS_LINE_2, "+
			"VIRTUAL_STORE.ADDRESS_CITY, "+
			"VIRTUAL_STORE.ADDRESS_STATE, "+
			"VIRTUAL_STORE.ADDRESS_ZIP, "+
			"REGEXP_SUBSTR(VIRTUAL_STORE.SUBLOCATION_OF, '[^:]+',1,1) SiteGroup1, "+
			"REGEXP_SUBSTR(VIRTUAL_STORE.SUBLOCATION_OF, '[^:]+',1,2) SiteGroup2, "+
			"REGEXP_SUBSTR(VIRTUAL_STORE.SUBLOCATION_OF, '[^:]+',1,3) SiteGroup3, "+
			"REGEXP_SUBSTR(VIRTUAL_STORE.SUBLOCATION_OF, '[^:]+',1,5) SiteGroup4, "+
			"REGEXP_SUBSTR(VIRTUAL_STORE.SUBLOCATION_OF, '[^:]+',1,6) SiteGroup5, "+
			"NULL SiteGroup6 "+
			"from  VIRTUAL_STORE )";
	
	public static final String SITE_DIRECTIVE = "site.je.directive";
	public static final String VENDOR_DIRECTIVE = "vendor.je.directive";
	public static final String SITE_CURRENCY_CODE = "USD";
	public static final String SITE_COUNTRY_CODE ="US";

	// partitioner query for vendor
	
	public static final String VENDOR_QUERY = "select distinct(vendors.vendor_id) "+
			" from vendors left outer join vendor_ship_point on vendors.vendor_id = vendor_ship_point.vendor_id "+
			" left join companycontactmap on vendors.vendor_id = companycontactmap.company_id "+
			" left outer join contacts ON contacts.contact_id = companycontactmap.contact_id  "+
			" where vendors.vendor_type_id=(select vendor_type_id from vendor_types where upper(name) = upper('Merchandise')) "+ 
			" and (companycontactmap.contactrole_id = (select contactrole_id from contactrole where upper(contactrole.name) = upper('primary contact')) "+ 
			" or companycontactmap.contactrole_id is null) and vendors.vendor_id > 0 ORDER BY vendors.vendor_id ASC ";

	// partitioner query for supersession
	public static final String SUPERSESSION_WHERE_CLAUSE = " Where upper( ISINACTIVE ) = upper('No') and (LIKE_ITEM_ID is not null or SUPERSEDE_FROM_ID is not null)";
	public static final String SUPERSESSION_OREDRBY_CLAUSE = " ORDER BY ITEM_ID ASC ";
	public static final String SUPERSESSION_SELECT_CLAUSE = "Select ITEM_ID ";// ;NAME,SUPERSEDE_FROM_ID,
	public static final String SUPERSESSION_FROM_CLAUSE = " from ITEMS ";

	// partitioner query for site open closed

	public static final String SITE_OPEN_CLOSED_SELECT_CLAUSE = "SELECT locations.name ";
	public static final String SITE_OPEN_CLOSED_FROM_CLAUSE = "FROM location_details,locations ";
	public static final String SITE_OPEN_CLOSED_WHERE_CLAUSE = "WHERE location_details.location_id = locations.location_id AND ((location_details.close_date IS NOT NULL AND ROUND((datediff(day,sysdate(),location_details.CLOSE_DATE)/365.2425),0) <= 4 ) OR location_details.close_date IS NULL) ";
	public static final String SITE_OPEN_CLOSED_OREDRBY_CLAUSE = "ORDER BY locations.name ";
	
/*	public static final String SITE_OPEN_CLOSED_SELECT_CLAUSE = "select locations.name ";
	public static final String SITE_OPEN_CLOSED_FROM_CLAUSE = "from location_details,locations ";
	public static final String SITE_OPEN_CLOSED_WHERE_CLAUSE = "where location_details.location_id = locations.location_id and ((location_details.close_date is not null and round((datediff(day,sysdate(),location_details.CLOSE_DATE)/365.2425),0) <= 4 )or location_details.close_date is null) ";
	public static final String SITE_OPEN_CLOSED_OREDRBY_CLAUSE = "ORDER BY locations.name ASC ";
*/
	// partitioner query for financial plan site status
	public static final String FINANCIAL_PLAN_SITE_STATUS_SELECT_CLAUSE = "SELECT distinct(locations.name) ";
	public static final String FINANCIAL_PLAN_SITE_STATUS_FROM_CLAUSE = "FROM location_details,locations ";
	public static final String FINANCIAL_PLAN_SITE_STATUS_WHERE_CLAUSE = "where location_details.location_id = locations.location_id ";
	public static final String FINANCIAL_PLAN_SITE_STATUS_ORDERBY_CLAUSE = "ORDER BY locations.name ASC ";

	
	//partitioner query for ItemAttribute
	public static final String ITEM_ATTRIBUTE_SELECT_CLAUSE = "select items.item_id 'ItemId' ";
	public static final String ITEM_ATTRIBUTE_FROM_CLAUSE = " from items, items t2, items t3, vendors , brand_name , colors , color_family , FUEL_TYPE , buy_type, sell_through_rates,item_status, items t12, units_type ";
	public static final String ITEM_ATTRIBUTE_WHERE_CLAUSE = " where items.supersede_from_id = t2.item_id(+) and items.like_item_id = t3.item_id(+) and items.vendor_id = vendors.vendor_id(+) and items.item_brand_id = brand_name.brand_name_id(+) and items.color_id = colors.LIST_ID(+)  and " +
                                                              "items.color_family_id = color_family.list_id(+) and "+
                                                              	"items.FUEL_TYPE_id = FUEL_TYPE.list_id(+) and "+
                                                              	"items.buy_type_id = buy_type.list_id(+) and "+
                                                              	"items.sell_through_rate_id = sell_through_rates.list_id(+) and "+
                                                              	"items.LIFE_CYCLE_STATUS_ID = item_status.list_id(+) and "+
                                                              	"items.RELATED_NEW_ITEM_RECORD_ID = t12.item_id(+) and "+
                                                              	"items.UNITS_TYPE_ID = units_type.UNITS_TYPE_ID(+) and "+
                                                              	"items.item_designation_id in (select list_id from ITEM_DESIGNATION_VALUES where list_item_name in ('NEW ITEM','AS-IS MASTER ITEM')) and "+
                                                              	"parent_id is null and items.item_id > 0 ";
	public static final String ITEM_ATTRIBUTE_ORDERBY_CLAUSE = " ORDER BY ItemId ASC";
	
	//partitioner query for Inventory
	public static final String INVENTORY_STORE_SELECT_CLAUSE ="select rownum r from ( ";
	public static final String INVENTORY_STORE_FROM_CLAUSE= "SELECT p.name 'SiteCode', items.name 'ItemCode', uom.plural_name,   "+
			"NULL 'FreightMethod', "+
			"NULL 'CreationDate', p.FIRST_STOCKED_DATE 'FirstStockedDate', NULL 'LastStockCountDate', NULL 'LastIssueDate', NULL 'LastReceiptDate',  "+
			"     'Y' 'StockingIndicator', "+
			"     NULL 'IssueMultipleUnits', "+
			"     DECODE(fp.LIST_ITEM_NAME,'Store Stock',0, p.MINIMUM_ON_HAND_QUANTITY) 'MinimumSafetyStockUnits', "+
			"     NULL 'MinimumSafetyStockDays', "+
			"     NULL 'UseMaximumStockFlag', "+
			"     ilm.NS_LEAD_TIME 'HostSystemLeadTimeDays', "+
			"     NULL 'UseMaximumOrderQuantityFlag', "+
			"     NULL 'MinimumOrderQuantity', "+
			"     NULL 'MaximumOrderQuantity', "+
			"   CASE "+
			"    WHEN p.LIST_ITEM_NAME <> 'Retail'  "+
			"    THEN 1 "+
			"    WHEN p.LIST_ITEM_NAME ='Retail' AND fp.LIST_ITEM_NAME in ('SHC RRC', 'SHC DDC', 'Vendor Direct', 'Store Stock') "+
			"     THEN 1 "+
			"    WHEN p.LIST_ITEM_NAME IN ('Retail') AND fp.LIST_ITEM_NAME in ('SHO RRC', 'SHO DDC', 'ORDC') "+
			"    THEN 0 "+
			"    ELSE 0 "+
			"     END 'FromSupplierFlag', "+
			"     DECODE(p.NONSTOCKED_ITEM, 'T', 1, 'F', 0, 0) 'ItemsInUse', "+
			"     NULL  'ItemMeanTimeBetweenFailure', "+
			"     CASE WHEN fp.LIST_ITEM_NAME IN ('SHO RRC', 'SHO DDC', 'ORDC')   "+
			"           AND p.LIST_ITEM_NAME ='Retail'  "+
			"       THEN location1.name  "+
			"       ELSE NULL  "+
			"     END 'SourceSite', "+
			"    CASE  "+
			"      WHEN fp.LIST_ITEM_NAME IN ('SHC RRC', 'SHC DDC', 'SHO RRC', 'SHO DDC', 'ORDC') "+
			"         THEN items.order_multiple__dc "+
			"      WHEN fp.LIST_ITEM_NAME IN ('Vendor Direct','Store Stock') "+
			"         THEN items.reorder_multiple "+
			"      ELSE 1 "+
			"     END 'OrderMultiple', "+
			"      NULL 'UnitofMeasure', "+
			"    CASE "+
			"      WHEN "+
			"       (RESTOCK_METHOD.list_id IS NOT NULL OR rm1.list_id IS NOT NULL) "+
			"   AND  REPLENISHMENT_STATUSES.list_id IS NOT NULL "+
			"      THEN  0 "+
			"      WHEN fp.LIST_ITEM_NAME ='Store Stock' THEN "+
			"            0 "+
			"      else "+
			"     p.MAXIMUM_ON_HAND_QUANTITY  "+
			"     end 'MaximumStockUnits', "+
			"     CASE "+
			"       WHEN p.LIST_ITEM_NAME <> 'Retail' or (p.LIST_ITEM_NAME ='Retail' AND fp.LIST_ITEM_NAME in ('SHC RRC', 'SHC DDC', 'Vendor Direct', 'Store Stock')) "+
			"       THEN  "+
			"         CASE WHEN fp.LIST_ITEM_NAME = 'SHC RRC' THEN "+
			"                   vendors.name ||':' || (SELECT shc_rrc FROM locations WHERE location_id = p.location_id) "+
			"              WHEN fp.LIST_ITEM_NAME = 'SHC DDC' THEN  "+
			"                   vendors.name ||':' || (SELECT shc_ddc FROM locations WHERE location_id = p.location_id) "+
			"              ELSE "+
			"                   vendors.name ||':' || '0000' "+
			"          END "+
			"     END  'suppliercode',    "+
			"    StockOnHand, "+
			"    DefaultCostPrice, "+
			"    DefaultSellingPrice, "+
			"StockReserved "+
			"from  "+
			"( "+
			"select parent_id, name,location_id,list_item_name,sho_ddc_id,sho_rrc_id, primary_ordc_id,MIN(FIRST_STOCKED_DATE) as FIRST_STOCKED_DATE ,MIN(MINIMUM_ON_HAND_QUANTITY) as MINIMUM_ON_HAND_QUANTITY, "+
			"NONSTOCKED_ITEM, MIN(MAXIMUM_ON_HAND_QUANTITY) as MAXIMUM_ON_HAND_QUANTITY,REPLENISHMENT_STATUS_ID, "+
			"SUM(StockOnHand) as StockOnHand,SUM(StockReserved) as StockReserved,(SUM(DefaultCostPrice)/ DECODE(SUM(child_cnt),0,1,SUM(child_cnt))) as DefaultCostPrice , "+
			"(SUM(DefaultSellingPrice)/DECODE(SUM(child_cnt),0,1,SUM(child_cnt))) as DefaultSellingPrice, SUM(child_cnt) as child_cnt   "+
			" from  "+
			"( "+
			"SELECT  "+
			"CASE "+
			"  WHEN location_types.LIST_ITEM_NAME = 'Retail' AND UPPER(BIN_NUMBER_COUNTS.bin_number) like '%SALEABLE%' "+
			"     THEN "+
			"        CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"        ELSE        "+
			"         BIN_NUMBER_COUNTS.available_count "+
			"       END "+
			"  ELSE 0 "+
			"END 'StockOnHand', "+
			"CASE "+
			"     WHEN location_types.LIST_ITEM_NAME = 'Retail' AND UPPER(BIN_NUMBER_COUNTS.bin_number) like '%SALEABLE%' "+
			"       THEN "+
			"            BIN_NUMBER_COUNTS.on_hand_count - BIN_NUMBER_COUNTS.available_count "+
			"       ELSE 0 "+
			"     END 'StockReserved', "+
			"CASE "+
			"WHEN (location_types.list_item_name = 'Retail' AND  flowpath.LIST_ITEM_NAME in ('Vendor Direct' ,'SHC DDC','SHC RRC', 'Store Stock')) "+
			"THEN   "+
			"   CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"        ELSE "+
			"           CASE WHEN NVL(itlm.available_count,0) = 0 THEN "+
			"                  0 "+
			"                ELSE "+
			"                  NVL(itlm.last_purchase_price,0) "+
			"           END "+
			"    END "+
			"ELSE  "+
			"   CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"        ELSE "+
			"           CASE WHEN NVL(itlm.available_count,0) = 0 THEN "+
			"                  0 "+
			"                ELSE         "+
			"                  NVL(itlm.average_cost,0) "+
			"           END "+
			"    END "+
			"END 'DefaultCostPrice', "+
			"CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"     WHEN NVL(item_prices.item_unit_price,0) = 0 THEN "+
			"             0 "+
			"     ELSE "+
			"         CASE WHEN NVL(itlm.available_count,0) = 0 THEN "+
			"              0 "+
			"              ELSE "+
			"                item_prices.item_unit_price "+
			"         END "+
			"END  'DefaultSellingPrice',  "+
			"CASE "+
			"   WHEN itm.related_asis_master_item_id IS NOT NULL "+
			"       AND idv.list_item_name = 'AS-IS INDIVIDUAL ITEM' "+
			"       THEN               "+
			"            itm.related_asis_master_item_id  "+
			"   ELSE itlm.item_ID   "+
			"END 'parent_id', "+
			"itlm.location_id , itlm.item_id,locations.name,location_types.list_item_name, "+
			"locations.sho_ddc_id,locations.sho_rrc_id, locations.primary_ordc_id, "+
			"CASE "+
			"   WHEN itm.related_asis_master_item_id IS NOT NULL "+
			"       AND idv.list_item_name = 'AS-IS INDIVIDUAL ITEM' AND NVL(itlm.available_count,0) > 0 "+
			"       THEN               "+
			"            1  "+
			"   ELSE 0   "+
			"END 'child_cnt', itlat.FIRST_STOCKED_DATE,itlat.MINIMUM_ON_HAND_QUANTITY,itlat.NONSTOCKED_ITEM,itlat.MAXIMUM_ON_HAND_QUANTITY, itlat.REPLENISHMENT_STATUS_ID "+
			"FROM ITEM_location_map itlm JOIN locations ON itlm.location_id = locations.location_id "+
			"JOIN (SELECT item_id, related_asis_master_item_id, FLOWPATH_ID, item_designation_id, type_name, "+
			"CASE WHEN related_asis_master_item_id IS NULL THEN "+
			"          item_id "+
			"     ELSE  "+
			"       related_asis_master_item_id "+
			"     END 'proxy_item_id' "+
			"FROM items "+
			"WHERE type_name = 'Inventory Item') itm ON itlm.item_id = itm.item_id "+
			"JOIN location_types ON locations.location_type_id = location_types.list_id  "+
			"JOIN (SELECT DISTINCT item_id, location_id, NVL(assorted_for,'F')  as assorted_for,FIRST_STOCKED_DATE as FIRST_STOCKED_DATE,MINIMUM_ON_HAND_QUANTITY, "+
			"NONSTOCKED_ITEM, MAXIMUM_ON_HAND_QUANTITY,REPLENISHMENT_STATUS_ID  FROM ITEM__LOCATION_LEVEL_ATTRIBUT) itlat  ON itm.proxy_item_id = itlat.item_id AND itlm.location_id = itlat.location_id  "+
			"AND location_types.list_item_name = 'Retail' AND itlat.assorted_for = 'T' "+
			"LEFT OUTER JOIN flowpath ON itm.FLOWPATH_ID = flowpath.list_id  "+
			"LEFT OUTER JOIN item_prices ON itlm.item_id = item_prices.item_id AND item_prices.item_price_id = locations.POS__PRICE_LEVEL_ID   "+
			"LEFT OUTER JOIN BIN_NUMBER_COUNTS ON itlm.location_id = BIN_NUMBER_COUNTS.location_id AND itlm.item_id = BIN_NUMBER_COUNTS.item_id AND (UPPER(BIN_NUMBER_COUNTS.bin_number) like '%SALEABLE%') "+
			"LEFT OUTER JOIN item_designation_values idv ON  idv.list_id = itm.item_designation_id AND idv.list_item_name IN ('AS-IS INDIVIDUAL ITEM','NEW ITEM','AS-IS MASTER ITEM' )  "+
			") "+
			"GROUP BY parent_id,location_id,name,list_item_name,sho_ddc_id,sho_rrc_id, primary_ordc_id,NONSTOCKED_ITEM, REPLENISHMENT_STATUS_ID ) p "+
			"JOIN items ON p.parent_id = items.item_id "+
			"JOIN ITEM_location_map ilm ON ilm.location_id = p.location_id AND p.parent_id = ilm.item_id "+
			"LEFT OUTER JOIN uom on items.sale_unit_id=uom.uom_id "+
			"LEFT OUTER JOIN REPLENISHMENT_STATUSES ON p.REPLENISHMENT_STATUS_ID=REPLENISHMENT_STATUSES.list_id and REPLENISHMENT_STATUSES.list_item_name in('Do Not Replenish','Discontinued','Do Not Order')  "+
			"LEFT OUTER JOIN RESTOCK_METHOD on  items.OUTLET_RESTOCK_METHOD_ID =restock_method.list_id and RESTOCK_METHOD.list_item_name='Replenishment'  "+
			"LEFT OUTER JOIN RESTOCK_METHOD rm1 on  items.N_3H_RESTOCK_METHOD_ID =rm1.list_id and rm1.list_item_name='Replenishment' "+
			"LEFT OUTER JOIN ITEM_VENDOR_MAP ivm ON items.item_id = ivm.item_id  "+
			"LEFT OUTER JOIN VENDORS ON ivm.VENDOR_ID = VENDORS.VENDOR_ID  "+
			"LEFT OUTER JOIN flowpath fp ON items.FLOWPATH_ID = fp.list_id  "+
			"LEFT OUTER JOIN locations location1 ON ((fp.LIST_ITEM_NAME  = 'SHO RRC' AND p.SHO_RRC_ID = location1.location_id)  "+
			"              OR (fp.LIST_ITEM_NAME  = 'SHO DDC'  AND p.SHO_DDC_ID = location1.location_id)  "+
			"              OR (fp.LIST_ITEM_NAME  = 'ORDC' AND p.PRIMARY_ORDC_ID =  location1.location_id)  "+
			"              ) "+
			"               ) ";

	
	
	public static final String INVENTORY_DC_SELECT_CLAUSE ="select rownum r from ( ";
	public static final String INVENTORY_DC_FROM_CLAUSE= "SELECT p.name 'SiteCode', items.name 'ItemCode', uom.plural_name,   "+
			"NULL 'FreightMethod', "+
			"NULL 'CreationDate', att.FIRST_STOCKED_DATE 'FirstStockedDate', NULL 'LastStockCountDate', NULL 'LastIssueDate', NULL 'LastReceiptDate',  "+
			"     'Y' 'StockingIndicator', "+
			"     NULL 'IssueMultipleUnits', "+
			"     DECODE(fp.LIST_ITEM_NAME,'Store Stock',0, att.MINIMUM_ON_HAND_QUANTITY) 'MinimumSafetyStockUnits', "+
			"     NULL 'MinimumSafetyStockDays', "+
			"     NULL 'UseMaximumStockFlag', "+
			"     ilm.NS_LEAD_TIME 'HostSystemLeadTimeDays', "+
			"     NULL 'UseMaximumOrderQuantityFlag', "+
			"     NULL 'MinimumOrderQuantity', "+
			"     NULL 'MaximumOrderQuantity', "+
			"   CASE "+
			"    WHEN p.LIST_ITEM_NAME <> 'Retail'  "+
			"    THEN 1 "+
			"    WHEN p.LIST_ITEM_NAME ='Retail' AND fp.LIST_ITEM_NAME in ('SHC RRC', 'SHC DDC', 'Vendor Direct', 'Store Stock') "+
			"     THEN 1 "+
			"    WHEN p.LIST_ITEM_NAME IN ('Retail') AND fp.LIST_ITEM_NAME in ('SHO RRC', 'SHO DDC', 'ORDC') "+
			"    THEN 0 "+
			"    ELSE 0 "+
			"     END 'FromSupplierFlag', "+
			"     DECODE(att.NONSTOCKED_ITEM, 'T', 1, 'F', 0, 0) 'ItemsInUse', "+
			"     NULL  'ItemMeanTimeBetweenFailure', "+
			"     CASE WHEN fp.LIST_ITEM_NAME IN ('SHO RRC', 'SHO DDC', 'ORDC')   "+
			"           AND p.LIST_ITEM_NAME ='Retail'  "+
			"       THEN location1.name  "+
			"       ELSE NULL  "+
			"     END 'SourceSite', "+
			"    CASE  "+
			"      WHEN fp.LIST_ITEM_NAME IN ('SHC RRC', 'SHC DDC', 'SHO RRC', 'SHO DDC', 'ORDC') "+
			"         THEN items.order_multiple__dc "+
			"      WHEN fp.LIST_ITEM_NAME IN ('Vendor Direct','Store Stock') "+
			"         THEN items.reorder_multiple "+
			"      ELSE 1 "+
			"     END 'OrderMultiple', "+
			"      NULL 'UnitofMeasure', "+
			"    CASE "+
			"      WHEN "+
			"       (RESTOCK_METHOD.list_id IS NOT NULL OR rm1.list_id IS NOT NULL) "+
			"   AND  REPLENISHMENT_STATUSES.list_id IS NOT NULL "+
			"      THEN  0 "+
			"      WHEN fp.LIST_ITEM_NAME ='Store Stock' THEN "+
			"            0 "+
			"      else "+
			"     att.MAXIMUM_ON_HAND_QUANTITY  "+
			"     end 'MaximumStockUnits', "+
			"     CASE "+
			"       WHEN p.LIST_ITEM_NAME <> 'Retail' or (p.LIST_ITEM_NAME ='Retail' AND fp.LIST_ITEM_NAME in ('SHC RRC', 'SHC DDC', 'Vendor Direct', 'Store Stock')) "+
			"       THEN  "+
			"         CASE WHEN fp.LIST_ITEM_NAME = 'SHC RRC' THEN "+
			"                   vendors.name ||':' || (SELECT shc_rrc FROM locations WHERE location_id = p.location_id) "+
			"              WHEN fp.LIST_ITEM_NAME = 'SHC DDC' THEN  "+
			"                   vendors.name ||':' || (SELECT shc_ddc FROM locations WHERE location_id = p.location_id) "+
			"              ELSE "+
			"                   vendors.name ||':' || '0000' "+
			"          END "+
			"     END  'suppliercode',    "+
			"    StockOnHand, "+
			"    DefaultCostPrice, "+
			"    DefaultSellingPrice, "+
			"StockReserved "+
			"from  "+
			"( "+
			"select parent_id, name,location_id,list_item_name,sho_ddc_id,sho_rrc_id, primary_ordc_id, "+
			"SUM(StockOnHand) as StockOnHand,SUM(StockReserved) as StockReserved,(SUM(DefaultCostPrice)/ DECODE(SUM(child_cnt),0,1,SUM(child_cnt))) as DefaultCostPrice , "+
			"(SUM(DefaultSellingPrice)/DECODE(SUM(child_cnt),0,1,SUM(child_cnt))) as DefaultSellingPrice, SUM(child_cnt) as child_cnt   "+
			" from  "+
			"( "+
			"SELECT "+
			"CASE "+
			"   WHEN NVL(location_types.LIST_ITEM_NAME, 'X') <> 'Retail' AND UPPER(bin_number.bin_number) = 'CARTON'  "+
			"     THEN "+
			"        CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"               0 "+
			"             ELSE  "+
			"               bin_number.available_count "+
			"        END "+
			"  ELSE 0 "+
			"END 'StockOnHand', "+
			"CASE "+
			"       WHEN NVL(location_types.LIST_ITEM_NAME, 'X') <> 'Retail' AND UPPER(bin_number.bin_number) ='CARTON'  "+
			"        THEN "+
			"         bin_number.on_hand_count - bin_number.available_count "+
			"       ELSE 0 "+
			"     END 'StockReserved', "+
			"CASE "+
			"WHEN  (location_types.list_item_name <> 'Retail') "+
			"THEN   "+
			"   CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"        ELSE "+
			"           CASE WHEN NVL(itlm.available_count,0) = 0 THEN "+
			"                  0 "+
			"                ELSE "+
			"                  NVL(itlm.last_purchase_price,0) "+
			"           END "+
			"    END "+
			"ELSE  "+
			"   CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"        ELSE "+
			"           CASE WHEN NVL(itlm.available_count,0) = 0 THEN "+
			"                  0 "+
			"                ELSE         "+
			"                  NVL(itlm.average_cost,0) "+
			"           END "+
			"    END "+
			"END 'DefaultCostPrice', "+
			"CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' THEN "+
			"             0 "+
			"     WHEN NVL(item_prices.item_unit_price,0) = 0 THEN "+
			"             0 "+
			"     ELSE "+
			"         CASE WHEN NVL(itlm.available_count,0) = 0 THEN "+
			"              0 "+
			"              ELSE "+
			"                item_prices.item_unit_price "+
			"         END "+
			"END  'DefaultSellingPrice',  "+
			"CASE "+
			"   WHEN itm.related_asis_master_item_id IS NOT NULL "+
			"       AND idv.list_item_name = 'AS-IS INDIVIDUAL ITEM' "+
			"       THEN               "+
			"            itm.related_asis_master_item_id  "+
			"   ELSE itlm.item_ID   "+
			"END 'parent_id', "+
			"itlm.location_id , itlm.item_id,locations.name,location_types.list_item_name, "+
			"locations.sho_ddc_id,locations.sho_rrc_id, locations.primary_ordc_id, "+
			"CASE "+
			"   WHEN itm.related_asis_master_item_id IS NOT NULL "+
			"       AND idv.list_item_name = 'AS-IS INDIVIDUAL ITEM' AND NVL(itlm.available_count,0) > 0 "+
			"       THEN               "+
			"            1  "+
			"   ELSE 0   "+
			"END 'child_cnt' "+
			"FROM ITEM_location_map itlm JOIN locations ON itlm.location_id = locations.location_id "+
			"JOIN (SELECT item_id, related_asis_master_item_id, FLOWPATH_ID, item_designation_id, type_name, "+
			"CASE WHEN related_asis_master_item_id IS NULL THEN "+
			"          item_id "+
			"     ELSE  "+
			"       related_asis_master_item_id "+
			"     END 'proxy_item_id' "+
			"FROM items "+
			"WHERE type_name = 'Inventory Item') itm ON itlm.item_id = itm.item_id "+
			"JOIN location_types ON locations.location_type_id = location_types.list_id  "+
			"AND  UPPER(location_types.list_item_name) in ('ORDC', 'SHO DDC', 'SHO RRC', 'DC','SHO RRC (3PL)','SHO DDC (3PL)') "+
			"LEFT OUTER JOIN flowpath ON itm.FLOWPATH_ID = flowpath.list_id  "+
			"LEFT OUTER JOIN item_prices ON itlm.item_id = item_prices.item_id AND item_prices.item_price_id = locations.POS__PRICE_LEVEL_ID   "+
			"LEFT OUTER JOIN (SELECT DISTINCT location_id, item_id,available_count,on_hand_count, CASE WHEN UPPER(bin_number) LIKE '%OUTOFCARTON%' OR  UPPER(bin_number) LIKE '%INCARTON%' THEN 'CARTON' "+
			"                                                   ELSE "+
			"                                                       NULL "+
			"                                                    END 'bin_number' FROM BIN_NUMBER_COUNTS WHERE UPPER(BIN_NUMBER_COUNTS.bin_number) like '%CARTON%' ) bin_number ON itlm.location_id = bin_number.location_id AND itlm.item_id = bin_number.item_id "+
			"LEFT OUTER JOIN item_designation_values idv ON  idv.list_id = itm.item_designation_id AND idv.list_item_name IN ('AS-IS INDIVIDUAL ITEM','NEW ITEM','AS-IS MASTER ITEM' )  "+
			") "+
			"GROUP BY parent_id,location_id,name,list_item_name,sho_ddc_id,sho_rrc_id, primary_ordc_id ) p "+
			"JOIN items ON p.parent_id = items.item_id "+
			"JOIN ITEM_location_map ilm ON ilm.location_id = p.location_id AND p.parent_id = ilm.item_id "+
			"LEFT OUTER JOIN uom on items.sale_unit_id=uom.uom_id "+
			"LEFT OUTER JOIN ITEM__LOCATION_LEVEL_ATTRIBUT att ON items.item_id = att.item_id AND p.location_id = att.location_id  "+
			"LEFT OUTER JOIN REPLENISHMENT_STATUSES ON att.REPLENISHMENT_STATUS_ID=REPLENISHMENT_STATUSES.list_id and REPLENISHMENT_STATUSES.list_item_name in('Do Not Replenish','Discontinued','Do Not Order')  "+
			"LEFT OUTER JOIN RESTOCK_METHOD on  items.OUTLET_RESTOCK_METHOD_ID =restock_method.list_id and RESTOCK_METHOD.list_item_name='Replenishment'  "+
			"LEFT OUTER JOIN RESTOCK_METHOD rm1 on  items.N_3H_RESTOCK_METHOD_ID =rm1.list_id and rm1.list_item_name='Replenishment' "+
			"LEFT OUTER JOIN ITEM_VENDOR_MAP ivm ON items.item_id = ivm.item_id  "+
			"LEFT OUTER JOIN VENDORS ON ivm.VENDOR_ID = VENDORS.VENDOR_ID  "+
			"LEFT OUTER JOIN flowpath fp ON items.FLOWPATH_ID = fp.list_id  "+
			"LEFT OUTER JOIN locations location1 ON ((fp.LIST_ITEM_NAME  = 'SHO RRC' AND p.SHO_RRC_ID = location1.location_id)  "+
			"              OR (fp.LIST_ITEM_NAME  = 'SHO DDC'  AND p.SHO_DDC_ID = location1.location_id)  "+
			"              OR (fp.LIST_ITEM_NAME  = 'ORDC' AND p.PRIMARY_ORDC_ID =  location1.location_id)  "+
			"              ) "+
			" )";
	
	
	public static final String INVENTORY_DIRECTIVE = "inventory.je.directive";
		
	// partitioner query for nightly receipts
	public static final String NIGHTLY_RECEIPTS_SELECT_CLAUSE = "select rownum r ";
	public static final String NIGHTLY_RECEIPTS_FROM_CLAUSE = "FROM (select GRVNumber, TransactionID, PurchaseOrderNumber, SiteCode, ItemCode, SUM(ReceivedQuantity) AS ReceivedQuantity, ActualReceiptDate, IBTSourceSite, vendor_name,ship_point_id "+
"FROM "+
"( "+
" SELECT trx1.tranid 'GRVNumber', trx1.TRANSACTION_ID 'TransactionID', "+
"CASE "+ 
"WHEN trx2.transaction_type ='Transfer Order' "+
" THEN "+
" 'TO-' ||trx2.tranid "+
" WHEN trx2.transaction_type ='Purchase Order' "+
" THEN "+
" 'PO-' || trx2.tranid "+
" ELSE "+
" 'SO-'|| trx2.tranid "+ 
" END 'PurchaseOrderNumber', "+ 
       " CASE "+
        " WHEN trx1.transaction_type = 'Item Receipt' "+
          "  THEN "+
              " loc.name "+
        " WHEN trx1.transaction_type =  'Invoice' "+
       " THEN "+
       " t8.name "+
     " END 'SiteCode', trxln.item_id, "+ 
     " CASE "+
       "  WHEN NVL(itm.related_asis_master_item_id, -1) <> -1 "+
           "  AND itm.item_designation_id IN (SELECT list_id FROM item_designation_values WHERE list_item_name = 'AS-IS INDIVIDUAL ITEM') "+
           " THEN"+
              " (SELECT name FROM items WHERE item_id = itm.related_asis_master_item_id)"+
       "  ELSE"+
           " itm.name"+
     " END 'ItemCode', "+
    " CASE "+
    " WHEN "+
    " trxln.item_count < 0 "+
    " THEN "+
    " (-1) * NVL(trxln.item_count,0) "+
    " ELSE "+
    " trxln.item_count "+
    " END  'ReceivedQuantity', "+
     
     " DECODE(trx1.transaction_type, 'Item Receipt', trx1.trandate, 'Invoice', trx2.trandate) 'ActualReceiptDate',"+
     " CASE "+
       "  WHEN trx1.transaction_type = 'Item Receipt' AND trx2.transaction_type = 'Transfer Order' "+
           " THEN "+
          " ( SELECT name FROM locations where location_id = trx1.LOCATION_ID ) "+
       "  WHEN trx1.transaction_type = 'Item Receipt' AND trx2.transaction_type = 'Purchase Order' "+
           " THEN "+
             "  NULL "+
        " WHEN trx1.transaction_type = 'Invoice' "+
           " THEN "+
             "  loc.name "+
       " ELSE NULL "+
    "  END IBTSourceSite, "+
    "  CASE "+
        " WHEN trx1.transaction_type = 'Item Receipt' AND trx2.transaction_type = 'Purchase Order' "+
          "  THEN "+
             "  v.name "+
       " ELSE NULL "+
    "  END vendor_name, "+
    "  CASE "+
       "  WHEN trx1.transaction_type = 'Item Receipt' AND trx2.transaction_type = 'Purchase Order' "+
           " THEN "+
             "   SUBSTR(trxln1.so_reservation_,1,4) "+
       " ELSE NULL "+
    "  END ship_point_id, "+
     " CASE "+
       "  WHEN NVL(itm.related_asis_master_item_id, -1) <> -1 "+
       "  AND itm.item_designation_id IN (SELECT list_id FROM item_designation_values WHERE list_item_name = 'AS-IS INDIVIDUAL ITEM') "+
            " THEN "+
                 " itm.related_asis_master_item_id "+
    "  END parent_item_id "+
"  FROM "+
    "  transactions trx1 JOIN transactions trx2 ON trx1.created_from_id = trx2.transaction_id AND NVL(trx2.transaction_type,'~') IN ('Transfer Order', 'Purchase Order', 'Sales Order') "+
 " LEFT OUTER JOIN transaction_lines trxln ON trx1.transaction_id = trxln.transaction_id AND trxln.item_id IS NOT NULL AND trxln.item_id > 0 AND DO_NOT_PRINT_LINE = 'No' "+
" LEFT OUTER JOIN  transaction_lines trxln1 on trxln1.transaction_id=trx2.transaction_id "+
 " LEFT OUTER  JOIN locations loc ON trxln.location_id = loc.location_id "+
 " LEFT OUTER JOIN items itm ON trxln.item_id = itm.item_id AND itm.type_name = 'Inventory Item' "+
" LEFT OUTER JOIN VENDORS v ON v.VENDOR_ID = trx1.entity_id "+
" LEFT OUTER JOIN SHIPPING_ITEMS t7 ON  trx2.shipping_item_id =t7.item_id "+
" LEFT OUTER JOIN DEPARTMENTS t8 ON trxln.department_id = t8.DEPARTMENT_ID "+
" WHERE NVL(trx1.transaction_type,'~') IN  ('Item Receipt','Invoice') "+
" AND CASE "+
       " WHEN NVL(trx1.transaction_type,'~') ='Invoice' AND NVL(trx2.TRANSACTION_TYPE,'~') IN ('Sales Order') AND (NVL(t7.name,'~')) LIKE '%TO%' THEN "+
           " 0 "+
       " ELSE "+
           " 1 "+
     " END  = 1 ) ";
    public static final String NIGHTLY_RECEIPTS_WHERE_CLAUSE = " where itemcode is not null ";
	
	public static final String NIGHTLY_RECEIPTS_GROUP_BY_CLAUSE = " GROUP BY GRVNumber, TransactionID, PurchaseOrderNumber, SiteCode, ItemCode, ActualReceiptDate, IBTSourceSite, vendor_name, ship_point_id ) temp ";
 
	public static final String NIGHTLY_DIRECTIVE = "nightlyreceipts.je.directive";
	
	// partitioner query for historical inventory

	public static final String HISTORICAL_INVENTORY_SELECT_CLAUSE = "SELECT ROWNUM r ";
	public static final String HISTORICAL_INVENTORY_FROM_CLAUSE = "FROM (SELECT  ItemCode, SiteCode,SUM(Units) Units,SUM(RetailDollars) RetailDollars ,SUM(CostDollars) CostDollars "+ 
		"FROM "+
			"( "+
			"SELECT "+
				"CASE "+
					"WHEN itm.related_asis_master_item_id IS NOT NULL "+
						"AND idv.list_item_name = 'AS-IS INDIVIDUAL ITEM' "+
							"THEN "+
			              		"(SELECT name from items where item_id = itm.related_asis_master_item_id ) "+ 
			         "ELSE "+
			         	"itm.name "+
			     "END 'ItemCode', "+
			     	"locations.name SiteCode, "+
			     "CASE "+
			     	"WHEN idv.list_item_name = 'AS-IS MASTER ITEM' "+
			     		"THEN 0 "+
			     	"ELSE "+
			     		"CASE "+
			     			"WHEN NVL(itlm.ON_HAND_COUNT ,0) = 0 "+
			     				"THEN 0 "+
			     			"ELSE "+
			     				"itlm.ON_HAND_COUNT "+
			     		"END "+
			     "END Units, "+
			     "CASE "+
			     	"WHEN idv.list_item_name = 'AS-IS MASTER ITEM' "+
			     		"THEN 0 "+
			     	"ELSE "+
			     		"CASE "+
			     			"WHEN NVL(itlm.ON_HAND_COUNT ,0) = 0 "+
			     				" THEN 0 "+
			     			"ELSE "+
			     				"(NVL(item_prices.item_unit_price,0)* NVL(itlm.ON_HAND_COUNT,0)) "+
			     		"END "+
			     "END RetailDollars, "+
			     "CASE "+
			     	"WHEN idv.list_item_name = 'AS-IS MASTER ITEM' "+
			     		"THEN 0 "+
			     	"ELSE "+
			     		"CASE "+
			     			"WHEN NVL(itlm.ON_HAND_COUNT ,0) = 0 "+
			     				"THEN 0 "+
			     			"ELSE "+
			     				"itlm.on_hand_value "+
			     		"END "+
			     	"END CostDollars "+
			    "FROM "+
			    	"ITEM_location_map itlm "+
			    	"JOIN locations ON itlm.location_id = locations.location_id "+
			    	"JOIN location_types ON locations.location_type_id = location_types.list_id "+
			    	"JOIN items itm ON itlm.item_id = itm.item_id "+
			    	"LEFT OUTER JOIN item_designation_values idv "+
			    	"ON  idv.list_id = itm.item_designation_id "+
			    	"AND idv.list_item_name "+
			    	"IN ('AS-IS INDIVIDUAL ITEM','NEW ITEM','AS-IS MASTER ITEM') "+
			    	"LEFT OUTER JOIN ITEM_PRICES ON itm.item_id = ITEM_PRICES.item_id and ITEM_PRICES.item_price_id = locations.POS__PRICE_LEVEL_ID "+
			    "WHERE "+
			    	"NVL(locations.country,'OH') NOT IN "+
			    	"(SELECT SHORT_NAME FROM COUNTRIES WHERE  NAME = 'Bermuda') "+
			    	"AND UPPER(location_types.list_item_name) in ('SHO DDC','SHO RRC (3PL)','ORDC','RETAIL') "+
			    	"AND items.type_name ='Inventory Item' "+
			    	"AND NVL(itlm.ON_HAND_COUNT,-1) <> -1 "+
			    	"AND  itlm.ON_HAND_COUNT <> '0' ";
	
	public static final String HISTORICAL_INVENTORY_GROUP_BY_CLAUSE = " ) GROUP BY ItemCode,SiteCode ) temp ";
	
	
	//partitioner query for Item Hierarchy
	public static final String ITEM_HIERARCHY_MASTER_SELECT_CLAUSE=" select rownum r ";
	public static final String ITEM_HIERARCHY_MASTER_FROM_CLAUSE= " from (SELECT "+
			" items.item_id, "+
			" vendors.full_name 'vendorname' "+
			" FROM "+
			" items LEFT OUTER JOIN item_vendor_map ON ITEMS.ITEM_ID = ITEM_VENDOR_MAP.ITEM_ID "+
			" LEFT OUTER JOIN vendors ON VENDORS.VENDOR_ID = ITEM_VENDOR_MAP.VENDOR_ID "+
			" LEFT OUTER JOIN classes ON classes.class_id = items.class_id ";
	public static final String ITEM_HIERARCHY_WHERE_CLAUSE = "where items.item_id >= 0 AND UPPER(ISINACTIVE) = 'NO' ";
	public static final String ITEM_HIERARCHY_MASTER_WHERE_CLAUSE=" )";
	
	public static final String ITEM_HIERARCHY_MASTER_N_SELECT_CLAUSE=" SELECT CLASS_ID";
	public static final String ITEM_HIERARCHY_MASTER_N_FROM_CLAUSE=" FROM CLASSES INNER JOIN MERCH_HIERARCHY_TYPES ON CLASSES.MERCH_HIERARCHY_TYPE_ID=MERCH_HIERARCHY_TYPES.LIST_ID ";
	public static final String ITEM_HIERARCHY_MASTER_N_WHERE_CLAUSE=" WHERE UPPER(MERCH_HIERARCHY_TYPES.LIST_ITEM_NAME)IN ('CATEGORY', 'DIVISION', 'LINE', 'SUB-LINE', 'CLASS')";
	
	
	public static final String ITEM_HIERARCHY_VENDOR_SELECT_CLAUSE= " select rownum r from ( SELECT distinct(vendors.vendor_id), vendors.COMPANYNAME";
	public static final String ITEM_HIERARCHY_VENDOR_FROM_CLAUSE= " FROM ITEMS,ITEM_VENDOR_MAP,VENDORS ";
	public static final String ITEM_HIERARCHY_VENDOR_WHERE_CLAUSE=" WHERE "+
                " ITEMS.ITEM_ID = ITEM_VENDOR_MAP.ITEM_ID "+ 
                " AND VENDORS.VENDOR_ID = ITEM_VENDOR_MAP.VENDOR_ID )";

	public static final String SUPPLIER_ORDER_SELECT_QUERY = " select distinct(TransactionId) as TransactionId ";
	public static final String SUPPLIER_ORDER_FROM_QUERY = " from ( "+
	" SELECT "+
	" TransactionId, "+
	" PurchaseOrderNumber, "+
	" PurchaseOrderDate, "+
	" OrderCancelledDate, "+
	" IBTFlag, "+
	" SupplierOrderStatus, "+
	" ReceiveNoEarlierThan, "+
	" ReceiveNoLaterThan, "+
	" MAX(PurchaseOrderLineNumber) as PurchaseOrderLineNumber, "+
	" SiteCode, "+
	" ItemCode, "+
	" EstimatedReceiptDate, "+
	" SUM(OrderedQuantity) as OrderedQuantity, "+
	" MAX(CostPerUnit) as CostPerUnit, "+
	" OrderLineStatusIndicator, "+
	" SupplierCode, "+
	" CurrencyCode, "+
	" IBTSiteCode, "+
	" SUM(OutstandingQuantity) as OutstandingQuantity, "+
	" CustomSupplierOrderStatusCode, "+
	" FreightMethod, "+
	" InternalModifiedReceiptDate, "+
	" SupplierModifiedReceiptDate, "+
	" InitialReceiptDate, "+
	"MIN(sysdate) AS Sysdate "+
	" FROM "+
	" ( "+
	"   SELECT "+
	"   t1.transaction_id 'TransactionId', "+
	"new_time(sysdate,'PST','GMT') 'Sysdate', "+
	"   CASE "+
	"   WHEN t1.transaction_type = 'Transfer Order' then 'TO-'||t1.tranid "+
	"   WHEN t1.transaction_type = 'Purchase Order' then 'PO-'||t1.tranid "+
	"   ELSE 'SO-'||t1.tranid "+
	"   END 'PurchaseOrderNumber', "+
	"   t1.status, "+
	"   fp.list_item_name, "+
	"   t1.trandate 'PurchaseOrderDate', "+
	"   DECODE(t1.transaction_type, 'Purchase Order', t1.cancel_date, NULL) 'OrderCancelledDate', "+
	"   t1.transaction_type 'transactiontype', "+
	"   DECODE(t1.transaction_type, 'Purchase Order', '0', '1') 'IBTFlag', "+
	"   'Placed' 'SupplierOrderStatus', "+
	"   DECODE(t1.transaction_type, 'Purchase Order', T1.due_date, NULL) 'ReceiveNoEarlierThan', "+
	"   NULL 'ReceiveNoLaterThan', "+
	"   DECODE(t1.transaction_type, 'Transfer Order', t2.transfer_order_item_line, t2.transaction_line_id) 'PurchaseOrderLineNumber', "+
	"   t3.name 'SiteCode', "+
	"  CASE WHEN t4.related_asis_master_item_id IS NOT NULL "+
	"  AND t4.item_designation_id = (SELECT list_id FROM item_designation_values WHERE list_item_name = 'AS-IS INDIVIDUAL ITEM') "+
	"  THEN t6.name ELSE t4.name END 'ItemCode', "+
	"   DECODE(t1.transaction_type, 'Sales Order', t2.shipdate, t2.expected_receipt_date) 'EstimatedReceiptDate', "+
	"   ABS(t2.item_count) 'OrderedQuantity', "+
	"   DECODE(t1.transaction_type, 'Sales Order', t4.AVERAGECOST, t2.ITEM_UNIT_PRICE) 'CostPerUnit', "+
	"   CASE WHEN t1.transaction_type = 'Purchase Order' "+
	"  AND transaction_lines.item_received ='No' "+
	"  AND (ABS(DATEDIFF(day,sysdate,expected_receipt_date)))<=14 "+
	"  THEN 'Pending' ELSE 'Placed' END 'OrderLineStatusIndicator', "+
	"  CASE WHEN t1.transaction_type = 'Purchase Order' THEN "+
	"  DECODE(NVL(SUBSTR(t2.SO_RESERVATION_,1,4),'0'), 0, vendors.name, to_char(vendors.name) || ':' || to_char(SUBSTR(t2.SO_RESERVATION_,1,4))) ELSE NULL END "+
	"  SupplierCode, "+
	"    'USD' 'CurrencyCode', "+
	"    DECODE(t1.transaction_type, 'Purchase Order', NULL, t5.name) 'IBTSiteCode', "+
	"   DECODE(t1.transaction_type, 'Sales Order', ABS(t2.item_count), (t2.item_count - NVL(t2.quantity_received_in_shipment,0))) 'OutstandingQuantity' , "+
	"   NULL 'CustomSupplierOrderStatusCode', "+
	"   NULL 'FreightMethod', "+
	"   NULL 'InternalModifiedReceiptDate', "+
	"   NULL 'SupplierModifiedReceiptDate', "+
	"   DECODE "+
	"   ( "+
	"      t1.transaction_type, 'Purchase Order', t1.due_date, NULL "+
	"   ) "+
	"   'InitialReceiptDate' "+
	"   FROM transactions t1 "+
	"   LEFT OUTER JOIN vendors on transactions.entity_id=vendors.vendor_id "+
	"   JOIN transaction_lines t2 ON t1.transaction_id = t2.transaction_id "+
	"   JOIN items itm ON t2.item_id=itm.item_id "+
	"  LEFT OUTER JOIN flowpath fp ON itm.flowpath_id=fp.list_id "+
	"   LEFT OUTER JOIN locations t3 ON "+
	"   ( "+
	"      DECODE "+
	"      ( "+
	"         t1.transaction_type, "+
	"         'Purchase Order', "+
	"         NVL(t2.location_id, t1.location_id), "+
	"         'Transfer Order', "+
	"         t1.transfer_location, "+
	"         'Sales Order', "+
	"         t1.location_id "+
	"      ) "+
	"      = t3.location_id "+
	"   ) "+
	"  JOIN items t4 ON t2.item_id = t4.item_id LEFT OUTER "+
	"   JOIN LOCATIONS T5 ON "+
	"   ( "+
	"      DECODE "+
	"      ( "+
	"         t1.transaction_type, "+
	"       'Transfer Order', "+
	"         t1.location_id, "+
	"         'Sales Order', "+
	"         t2.location_id "+
	"      ) "+
	"      = t5.location_id "+
	"   ) "+
	"   LEFT OUTER "+
	"   JOIN items t6 ON t4.related_asis_master_item_id = t6.item_id "+
	"   LEFT OUTER "+
	"   JOIN SHIPPING_ITEMS t7 ON t1.shipping_item_id = t7.item_id "+
	"   WHERE t1.transaction_type IN ('Purchase Order', 'Transfer Order', 'Sales Order') "+
	"  AND t2.item_id IS NOT NULL "+
	"   AND t3.country IN "+
	"   ( "+
	"      SELECT "+
	"      short_name "+
	"      FROM countries "+
	"      WHERE name NOT IN ('Bermuda') "+
	"   ) "+
	" AND "+
	" ( "+
	"    ( "+
	"      t1.transaction_type IN ('Purchase Order' , 'Transfer Order') "+
	"   AND(UPPER(t1.status) NOT LIKE '%APPROVAL%' "+
	"   AND UPPER(t1.status) NOT LIKE '%REJECTED%' "+
	"   AND UPPER(t1.status) NOT LIKE '%CANCELLED%' "+
	"   AND UPPER(t1.status) NOT LIKE '%CLOSED%' "+
	"   AND UPPER(t1.status) NOT LIKE '%RECEIVED%') "+
	"     AND "+
	"      ( "+
	"         t1.transaction_type = 'Purchase Order' OR "+
	"         ( "+
	"            t1.transaction_type = 'Transfer Order' "+
	"           AND t1.status <> 'Received' "+
	"           AND ABS(t2.item_count) > 0 "+
	"            AND t4.TYPE_NAME = 'Inventory Item' "+
	"        ) "+
	"     ) "+
	"   AND t2.item_received IS NOT NULL "+
	"    AND (NVL(t2.item_count,'0') - NVL(t2.quantity_received_in_shipment,'0'))>0 "+
	"   ) "+
	"   OR "+
	"   ( "+
	"      t1.transaction_type = 'Sales Order' "+
	"      AND t1.status = 'Pending Fulfillment' "+
	"      AND ABS(t2.item_count) > 0 "+
	"      AND t2.account_id IS NOT NULL "+
	"      AND t4.TYPE_NAME = 'Inventory Item' "+
	"      AND ( fp.list_item_name LIKE '%SHO RRC%' "+ 
	"      OR  fp.list_item_name LIKE '%SHO DDC%' "+
	"      OR  fp.list_item_name LIKE '%ORDC%') "+
	"      AND UPPER(t7.name) NOT LIKE '%TO%' "+ 
	"   ) "+
	" ) " ;
	//" ) ";
	public static final String SUPPLIER_ORDER_BRACKET = " ) ";
	public static final String SUPPLIER_ORDER_GROUPBY_QUERY = " GROUP BY TransactionId, "+ 
			" PurchaseOrderNumber, "+ 
			" PurchaseOrderDate, "+ 
			" OrderCancelledDate, "+ 
			" transactiontype, "+ 
			" IBTFlag, "+ 
			" SupplierOrderStatus, "+ 
			" ReceiveNoEarlierThan, "+ 
			" ReceiveNoLaterThan, "+ 
			" SiteCode, "+ 
			" ItemCode, "+ 
			" EstimatedReceiptDate, "+ 
			" OrderLineStatusIndicator, "+ 
			" CurrencyCode, "+ 
			" IBTSiteCode, "+ 
			" CustomSupplierOrderStatusCode, "+ 
			" FreightMethod, "+ 
			" InternalModifiedReceiptDate, "+ 
			" IBTSiteCode, "+ 
			" CustomSupplierOrderStatusCode, "+ 
			" FreightMethod, "+ 
			" InternalModifiedReceiptDate, "+ 
			" SupplierModifiedReceiptDate, "+ 
			" InitialReceiptDate, "+ 
			" SupplierCode "+ 
 " ) "; 
	
	public static final String SUPPLIER_ORDER_ORDERBY_QUERY = " Order BY TransactionId ";
	
	public static final String SITE_ATTRIBUTE_SELECT_CLAUSE = " select location_id ";
	public static final String SITE_ATTRIBUTE_FROM_CLAUSE = " from locations ";
	public static final String SITE_ATTRIBUTE_ORDERBY_CLAUSE = " order by location_id ";
	
	// partitioner query for  inventory cost

	 public static final String INVENTORY_COST_SELECT_CLAUSE = "select rownum r from ( SELECT ItemCode,SiteCode,AVG(AverageCost) ";
     public static final String INVENTORY_COST_FROM_CLAUSE = " from (SELECT CASE WHEN items.RELATED_ASIS_MASTER_ITEM_ID IS NOT NULL "+
       " AND idv.list_item_name =  'AS-IS INDIVIDUAL ITEM' "+
    		" THEN   (select name from items where  item_id = items.related_asis_master_item_id) "+
        " ELSE   items.name  END 'ItemCode', t2.name 'SiteCode',  t2.LOCATION_TYPE_ID, "+
" items.name,  "+
" bin_number.bin_number, "+
" CASE WHEN idv.list_item_name = 'AS-IS MASTER ITEM' "+
                " THEN 0 "+
              " ELSE "+
          " CASE WHEN NVL(t1.average_cost ,0) = 0 THEN 0  ELSE  t1.average_cost END "+
          " END 'AverageCost' "+
          " FROM "+
          " items JOIN item_location_map t1 "+
          " ON items.item_id = t1.item_id "+
          " LEFT OUTER JOIN item_designation_values idv ON  idv.list_id = items.item_designation_id AND idv.list_item_name IN ('AS-IS INDIVIDUAL ITEM','NEW ITEM','AS-IS MASTER ITEM') "+
          " JOIN locations t2 "+
          " ON t1.location_id = t2.location_id "+
        		  " LEFT OUTER JOIN bin_number "+
        		 "  ON t1.location_id = bin_number.location_id AND t1.item_id = bin_number.item_id ";

     public static final String INVENTORY_COST_WHERE_CLAUSE = " WHERE average_cost is not null and items.type_name in ('Inventory Item','Gift Certificate') "+
    		 		" and t2.LOCATION_TYPE_ID in (select list_id from LOCATION_TYPES where list_item_name in ('Retail')) "+
    		 		" and bin_number.bin_number like  '%Saleable%')  ";                             
     public static final String INVENTORY_COST_GROUP_BY_CLAUSE = "GROUP BY ItemCode,SiteCode) temp";

	
	
	// partitioner query for weekly sales history.
	public static final String WEEKLY_SALES_HISTORY_SELECT_CLAUSE = "select rownum r from(SELECT ItemId,ItemCode, ReturnFlag, SiteCode, SalesTypeCode, to_char(salesdate,'YYYY-MM-DD HH24:MI:SS')SalesDate, "
			+ "SUM(item_count) item_count, SUM(average_cost) average_cost,SUM(net_amount) net_amount, SUM(gross_amount) gross_amount ";
	public static final String WEEKLY_SALES_HISTORY_FROM_CLAUSE=" FROM "
			+ "(" + "SELECT loc.country, " + "CASE " + "WHEN itm.parent_id IS NOT NULL "
			+ "AND itm.item_designation_id = (SELECT list_id FROM item_designation_values WHERE UPPER(list_item_name) = 'AS-IS INDIVIDUAL ITEM') "
			+ "THEN " + "t1.name " + "ELSE " + "itm.name " + "END 'ItemCode', " + "CASE "
			+ "WHEN itm.parent_id IS NOT NULL "
			+ "AND itm.item_designation_id = (SELECT list_id FROM item_designation_values WHERE UPPER(list_item_name) = 'AS-IS INDIVIDUAL ITEM') "
			+ "THEN " + "t1.item_id " + "ELSE " + "itm.item_id " + "END 'ItemId', " + "loc.full_name 'SiteCode', "
			+ "DECODE((SELECT COUNT(1) " + "FROM transaction_lines " + "WHERE transaction_id = trx.transaction_id "
			+ "AND trxln.item_id > 0" + "AND trxln.amount_foreign IS NOT NULL "
			+ "AND (UPPER(transaction_discount_line) = UPPER('Yes') OR price_type_id = (SELECT price_type_id FROM price_types WHERE name = 'Custom')) "
			+ "), 0, 'Regular', 'Promotion' " + ") AS SalesTypeCode, "
			+ "CASE WHEN UPPER(trx.transaction_type) = UPPER('Return Authorization') THEN '1' ELSE '0' END 'ReturnFlag', "
			+ "CASE " + "WHEN UPPER(trx.transaction_type) = UPPER('Sales Order') "
			+ "AND DATEDIFF(DAY, SYSDATE(), trx.trandate) > 21 " + "THEN "
			+ "NEXT_DAY(TRUNC(slstrx.applied_date_posted,'IW'), 'Saturday') " + "ELSE "
			+ "NEXT_DAY(TRUNC(trx.trandate,'IW'), 'Saturday') " + "END 'SalesDate', "
			+ "ABS(trxln.item_count) item_count, " + "ABS(trxln.item_count * ilm.average_cost) average_cost, "
			+ "ABS (trxln.net_amount) net_amount, " + "ABS (trxln.gross_amount) gross_amount "
			+ "FROM transactions trx LEFT OUTER JOIN transaction_lines trxln "
			+ "ON trx.transaction_id = trxln.transaction_id " + "LEFT OUTER JOIN locations loc "
			+ "ON DECODE(UPPER(trx.transaction_type), UPPER('Sales Order'), trxln.location_id, trx.location_id) = loc.location_id "
			+ "LEFT OUTER JOIN items itm " + "ON itm.item_id = trxln.item_id " + "LEFT OUTER JOIN price_types prtyp "
			+ "ON prtyp.price_type_id = trxln.price_type_id " + "LEFT OUTER JOIN item_location_map ilm "
			+ "ON ilm.item_id = trxln.item_id " + "AND ilm.location_id = trx.location_id "
			+ "LEFT OUTER JOIN (SELECT DISTINCT trxlnk.original_transaction_id, trxlnk.applied_date_posted "
			+ "FROM transaction_links trxlnk, transactions trx1, transactions trx2 "
	        + "WHERE trxlnk.original_transaction_id = trx1.transaction_id "
			+ "AND trxlnk.applied_transaction_id = trx2.transaction_id "
			+ "AND UPPER(trx1.transaction_type) = UPPER('Sales Order') " + "AND UPPER(trx2.status) = UPPER('Shipped') "
			+ "AND UPPER(trxlnk.link_type) = UPPER('Receipt/Fulfillment') " + ") slstrx "
			+ "ON trx.transaction_id = DECODE(UPPER(trx.transaction_type), UPPER('Sales Order'), slstrx.original_transaction_id) "
			+ "LEFT OUTER JOIN items t1 " + "ON items.parent_id = t1.item_id ";
	public static final String WEEKLY_SALES_HISTORY_WHERE_CLAUSE = 	"WHERE trxln.item_id > 0 "
			+ "AND trxln.amount_foreign IS NOT NULL "
			+ "AND NVL(loc.county, 'X') NOT IN (select SHORT_NAME from COUNTRIES where  NAME = 'Bermuda') "
			+ "AND ((UPPER(trx.transaction_type) = UPPER('Sales Order') AND datediff(day,NEXT_DAY((sysdate()-7), 'Saturday'),slstrx.APPLIED_DATE_POSTED) <= 21 AND slstrx.original_transaction_id IS NOT NULL) "
			+ "OR (UPPER(trx.TRANSACTION_TYPE) = UPPER('Cash Sale') and datediff(day,NEXT_DAY((sysdate()-7), 'Saturday'),trx.trandate) <= 21 AND trx.CREATED_FROM_ID is null AND UPPER(trx.status) = UPPER('Deposited')) "
			+ "OR (UPPER(trx.TRANSACTION_TYPE) = UPPER('Return Authorization') AND datediff(day,NEXT_DAY((sysdate()-7), 'Saturday'),trx.trandate) <= 21 AND UPPER(trx.status) = UPPER('Refunded'))) "
			+ ") " + "GROUP BY ItemId,ItemCode, SiteCode, SalesTypeCode, SalesDate, ReturnFlag) ";
	public static final String WEEKLY_SALES_HISTORY_ORDERBY_CLAUSE = " ItemId ";
	//Partition query for historical metrics
	
   public static final String HISTORICAL_METRICS_SELECT_CLAUSE = "select rownum r ";
   public static final String HISTORICAL_METRICS_FROM_CLAUSE=" from ("
		  + "SELECT ItemCode, SiteCode, "
	     + "SUM(InvenAdjustDamagUnits) InventAdjDamagedUnits, "
	      + "SUM(InvenAdjustShrinkUnits) InventAdjShrinkUnits,"
	      + "SUM(InvenAdjustDamagDollars) InventAdjDamagedCostDollars,"
	      +" SUM(InvenAdjustShrinkDollars) InventAdjShrinkCostDollars "
	  +"FROM "
	  +"(SELECT  t1.itemcode,   locations.name SiteCode,     CASE   WHEN reason_code.reason_code_name IN ('As-Is Item Creation', 'Damage', 'Non-Saleable - Vendor', 'Non-Saleable Junk') "
	  +"THEN      transaction_lines.item_count    ELSE 0    END InvenAdjustDamagUnits, CASE    WHEN reason_code.reason_code_name IN ('Cycle Count', 'Out of Stock', 'Physical Inventory Adjustment', 'Theft') "
	     +"THEN             transaction_lines.item_count   WHEN reason_code.reason_code_name = 'Extra Merchandise'   THEN     -(transaction_lines.item_count) "
       +"ELSE 0 END InvenAdjustShrinkUnits,  CASE  WHEN reason_code.reason_code_name IN ('As-Is Item Creation', 'Damage', 'Non-Saleable - Vendor', 'Non-Saleable Junk') "
       +"THEN      transaction_lines.gross_amount   ELSE 0   END InvenAdjustDamagDollars,    CASE  WHEN reason_code.reason_code_name IN ('Cycle Count', 'Out of Stock', 'Physical Inventory Adjustment', 'Theft') "
    +"THEN       transaction_lines.gross_amount WHEN reason_code.reason_code_name = 'Extra Merchandise' "
	   +"THEN   -(transaction_lines.gross_amount)  ELSE 0     END InvenAdjustShrinkDollars  FROM transactions JOIN transaction_lines ON transactions.transaction_id = transaction_lines.transaction_id  JOIN locations ON transactions.location_id = locations.location_id "
	     +"JOIN ( SELECT i.item_id, CASE WHEN i.related_asis_master_item_id IS NOT NULL AND idv.list_id IS NOT NULL THEN  "                     
	                                +"(SELECT name from items where item_id = i.related_asis_master_item_id )   ELSE   i.name  END 'itemcode' "
	         +"from  items i LEFT OUTER JOIN item_designation_values idv ON  idv.list_id = i.item_designation_id AND idv.list_item_name IN ('AS-IS INDIVIDUAL ITEM')) "
	  +"t1 ON transaction_lines.item_id = t1.item_id "
	       +" JOIN reason_code ON transactions.reason_code_id = reason_code.reason_code_id ";
public static final String HISTORICAL_METRICS_WHERE_CLAUSE= "  WHERE transactions.transaction_type = 'Inventory Adjustment' "
      +"AND transaction_lines.item_id IS NOT NULL "
      +"AND transaction_lines.item_count <> 0 "
   +")GROUP BY ItemCode, SiteCode) temp ";
		public static final String HISTORICAL_METRICS_ORDERBY_CLAUSE= " ORDER BY r ASC ";
	// weekly sales history xml root elements 
	
	public static final String  START_ELEMENT_SALES_HISTORY_BY_TYPE_LIST="<SalesHistoryByTypeList>";
	public static final String END_ELEMENT_SALES_HISTORY_BY_TYPE_LIST="</SalesHistoryByTypeList>";
	// partitioner query for store inventory local db

	public static final String STORE_INVENTORY_ENRICHER_SELECT_CLAUSE = "select  @rownum:=@rownum +1 as r  from ( select * ";
	public static final String STORE_INVENTORY_ENRICHER_FROM_CLAUSE = "from temp_dataload_ns_onhandquantity_enriched ";
	public static final String STORE_INVENTORY_ENRICHER_WHERE_CLAUSE = "WHERE update_flag = 1 AND present_in_netsuite = 1) temp, "
			+ "(SELECT @rownum := 0) r";

	// partitioner query for store inventory
//	public static final String STORE_INVENTORY_SELECT_CLAUSE = "select rownum r from(SELECT locations.name 'Location Name',locations.location_id 'Location Id',items.shc_item_number, "
//			+ "items.name 'Items Name',items.item_id 'Item Id' ,t3.bin_id 'Bin Id', "
//			+ "item_location_map.on_hand_count 'On Hand Quantity' ";
//	
//	 public static final String STORE_INVENTORY_FROM_CLAUSE = "FROM locations LEFT OUTER JOIN item_location_map ON (locations.location_id = item_location_map.location_id)LEFT OUTER JOIN items "+
//		       "ON (item_location_map.item_id = items.item_id)LEFT OUTER JOIN item_designation_values ON (items.item_designation_id = item_designation_values.list_id) "+
//		       "LEFT OUTER JOIN store_types ON (locations.store_type_id = store_types.list_id) LEFT OUTER JOIN bin_number_counts t3 ON (ITEM_LOCATION_MAP.ITEM_ID = t3.item_id and ITEM_LOCATION_MAP.location_id = t3.location_id) ";
//			
//			 public static final String STORE_INVENTORY_WHERE_CLAUSE = "WHERE item_location_map.on_hand_count is not null "+
//		   "AND items.isinactive = 'No' "+
//		   "AND item_designation_values.list_item_name(+) = 'NEW ITEM' "+
//		   "AND store_types.list_item_name IN ('Hardware', 'Home Appliance', 'Hometown'))";

	public static final String CLUSTERFEED_LOCATION_SELECT_CLAUSE = "select location_id ";
	public static final String CLUSTERFEED_LOCATION_FROM_CLAUSE = "from locations ";
	public static final String CLUSTERFEED_LOCATION_ORDERBY_CLAUSE = "order by location_id ";

	public static final String CLUSTERFEED_CLUSTER_SELECT_CLAUSE = " select price_type_id ";
	public static final String CLUSTERFEED_CLUSTER_FROM_CLAUSE = "from price_types ";
	public static final String CLUSTERFEED_CLUSTER_WHERE_CLAUSE = "where name in ('Online Price','201','202','203','204','205') ";
	public static final String CLUSTERFEED_CLUSTER_ORDERBY_CLAUSE = "order by price_type_id ";

	public static final String TRUNCATE_LOCATION_LOOKUP_TABLE = "TRUNCATE TABLE temp_lookup_netsuite_location";
	public static final String TRUNCATE_CLUSTER_LOOKUP_TABLE = "TRUNCATE TABLE temp_lookup_netsuite_cluster";
	public static final String TRUNCATE_CLUSTERFEED_LOOKUP_TABLE = "TRUNCATE TABLE temp_dataload_rpcm_clusterfeed";
	

	/*public static final String TRUNCATE_LOCATION_LOOKUP_TABLE = "TRUNCATE TABLE temp_lookup_netsuite_location";
	public static final String TRUNCATE_CLUSTER_LOOKUP_TABLE = "TRUNCATE TABLE temp_lookup_netsuite_cluster";
	public static final String TRUNCATE_CLUSTERFEED_LOOKUP_TABLE = "TRUNCATE TABLE temp_dataload_rpcm_clusterfeed";*/
	
	// partitioner query for PRICEFEED
	public static final String PRICEFEED_SELECT_CLAUSE = "select r ";// ;NAME,SUPERSEDE_FROM_ID,
	public static final String PRICEFEED_FROM_CLAUSE = " from (select rownum r, WEBSITE_ITEM_PRICING.PRICE_LEVEL_ID, "+
				"    WEBSITE_ITEM_PRICING.WEBSITE_ITEM_PRICING_ID 'ItemPricingInternalId', "+
				"    price_types.name 'priceTypes',"+
				" items.SHC_ITEM_NUMBER,"+
				"    items.name,"+
				"    items.item_id,"+
				"    items.ISONLINE,"+
				"    WEBSITE_ITEM_PRICING.REGULAR_PRICE,"+
				"    WEBSITE_ITEM_PRICING.CURRENT_SELLING_PRICE,"+
				"    WEBSITE_ITEM_PRICING.PERCENT_DISCOUNT,"+
				"    WEBSITE_ITEM_PRICING.MAP_VIOLATION_FLAG"+
				"    from "+
				"    WEBSITE_ITEM_PRICING right outer join items "+
				"    on  WEBSITE_ITEM_PRICING.item_id = items.item_id"+
				"    left outer join price_types "+
				"    on WEBSITE_ITEM_PRICING.PRICE_LEVEL_ID = price_types.price_type_id"+
				"    LEFT OUTER JOIN item_designation_values"+
				"    ON (items.item_designation_id = item_designation_values.list_id)"+
				"    where"+
				"    items.isinactive = 'No'"+
				"    AND item_designation_values.list_item_name(+) = 'NEW ITEM'"+
				"   AND price_types.name IN ('Online Price','201','202','203','204','205'))temp ";
	public static final String PRICEFEED_OREDRBY_CLAUSE = " ORDER BY R ASC ";

	public static final String PRICEFEED_PROC_SELECT_CLAUSE = "select id ";// ;NAME,SUPERSEDE_FROM_ID,
	public static final String PRICEFEED_PROC_FROM_CLAUSE = " from temp_dataload_prcm_pricefeed_enriched ";
	public static final String PRICEFEED_PROC_OREDRBY_CLAUSE = " ORDER BY id ASC ";
	public static final String TEMP_DATALOAD_PRCM_PRICEFEED = "TRUNCATE TABLE temp_dataload_prcm_pricefeed";
	public static final String TEMP_DATALOAD_NETSUITE_PRICEFEED = "TRUNCATE TABLE temp_dataload_netsuite_pricefeed";
	public static final String TEMP_LOOKUP_NS_PRICETYPE = "TRUNCATE TABLE temp_lookup_netsuite_cluster";
	

	public static final String TEMP_DATALOAD_SHC_CNVHISTORICALMETRICS = "TRUNCATE TABLE temp_dataload_shc_cnvhistoricalmetrics";
	public static final String CNVHISTORICALMETRICS_PROC_SELECT_CLAUSE = "select id ";
	public static final String CNVHISTORICALMETRICS_PROC_FROM_CLAUSE = " from temp_dataload_shc_cnvhistoricalmetrics_enriched ";
	public static final String CNVHISTORICALMETRICS_PROC_ORDERBY_CLAUSE = " ORDER BY id ASC ";
	public static final String TEMP_DATALOAD_PRCM_CNVHISTORICALINVENTORY = "TRUNCATE TABLE temp_dataload_shc_cnvhistoricalinventory ";
	public static final String TEMP_DATALOAD_TRUNCATE_CNVITEMBUILD991_INV_ITEMS = "TRUNCATE TABLE temp_dataload_inv_items ";
	public static final String TEMP_DATALOAD_TRUNCATE_CNVITEMBUILD991_NS_INV_ITEMS = "TRUNCATE TABLE temp_lookup_ns_inv_items ";
	public static final String TEMP_DATALOAD_TRUNCATE_CNVITEMBUILD991_NS_INV_ADJUST = "TRUNCATE TABLE temp_lookup_ns_inv_adjust ";
	public static final String TEMP_DATALOAD_TRUNCATE_CNVITEMBUILD991_NS_CADENCE = "TRUNCATE TABLE temp_lookup_ns_cadence ";
	public static final String TEMP_DATALOAD_TRUNCATE_CNVITEMBUILD991_NS_PRICELIST = "TRUNCATE TABLE temp_lookup_ns_pricelist ";
	
	
	
	
	public static final String CNVRECEIPTS_PROC_SELECT_CLAUSE = "select id ";
	public static final String CNVRECEIPTS_PROC_FROM_CLAUSE = " from temp_dataload_shc_cnvreceipts ";
	public static final String CNVRECEIPTS_PROC_OREDRBY_CLAUSE = " ORDER BY id ASC ";
	
	public static final String ITEMDESCATTR_PROC_SELECT_CLAUSE = "select id ";// ;NAME,SUPERSEDE_FROM_ID,
	public static final String ITEMDESCATTR_PROC_FROM_CLAUSE = " from temp_dataload_netsuite_spindescattribute_enriched ";
	public static final String ITEMDESCATTR_PROC_WHERE_CLAUSE = " where updated_in_netsuite = 0 ";
	public static final String ITEMDESCATTR_PROC_OREDRBY_CLAUSE = " ORDER BY id ASC ";
	
	// Partition query for SPIN ASSETS
	public static final String SPINASSETS_PROC_SELECT_CLAUSE = "select row_id ";
	public static final String SPINASSETS_PROC_FROM_CLAUSE = " from temp_dataload_netsuite_spinassets_enriched ";
	public static final String SPINASSETS_PROC_OREDRBY_CLAUSE = " ORDER BY row_id ASC ";
	
	// Partition query for SPIN ITEM
		public static final String SPINITEM_PROC_SELECT_CLAUSE = "select id  ";
		public static final String SPINITEM_PROC_FROM_CLAUSE = " from temp_dataload_netsuite_spinitem_enriched ";
		public static final String SPINITEM_PROC_OREDRBY_CLAUSE = " ORDER BY id  ASC ";
		public static final String CONDITIONAL_DELETE_TABLE_SPINITEM = "DELETE FROM temp_dataload_ecomm_spinitem WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY)";		
			
			
			 // Partition query for SPIN IMAGES
       public static final String SPINIMAGES_PROC_SELECT_CLAUSE = "select row_id ";
       public static final String SPINIMAGES_PROC_FROM_CLAUSE = " from temp_dataload_netsuite_spinimages_enriched ";
       public static final String SPINIMAGES_PROC_OREDRBY_CLAUSE = " ORDER BY row_id ASC ";
   
    // Partitioner query for Cnv Sales History
   	public static final String CNV_SALESHISTORY_DATALOAD_SELECT_CLAUSE = "select id";
   	public static final String CNV_SALESHISTORY_DATALOAD_FROM_CLAUSE =" from temp_dataload_shc_cnvsaleshistory_enriched ";
   	public static final String CNV_SALESHISTORY_DATALOAD_WHERE_CLAUSE =" where sent_to_je=0 ";
   	public static final String CNV_SALESHISTORY_DATALOAD_ORDER_BY_CLAUSE = " ORDER BY id ASC ";     
	
	// Constants for Jobs
	public static final String IS_FIRST_RUN = "isFirstRun";
	public static final String NO = "No";
	public static final String TO_DELETE = "__toDelete";
	public static final String FAILED = "FAILED";
	public static final String CREATED = "CREATED";
	public static final String SUCCESS = "SUCCESS";
	public static final String SITE_OPEN_CLOSED_OPEN_STATUS = "OPEN";
	public static final String SITE_OPEN_CLOSED_CLOSED_STATUS = "CLOSED";
	public static final String FINANCIAL_PLAN_SITE_STATUS_COMP_STATUS = "Comparative";
	public static final String FINANCIAL_PLAN_SITE_STATUS_NON_COMP_STATUS = "NonComparative";

	//Interface Friendly names
	public static final String ITEM_FRIENDLY_NAME = "INT-232 Item";
	public static final String SUPERSESSION_FRIENDLY_NAME = "INT-328 Supersession";
	public static final String VENDORUPDATE_FRIENDLY_NAME = "INT-019 Vendor Update";
	public static final String SITEOPENCLOSED_FRIENDLY_NAME = "INT-319 Site Open Closed";
	public static final String INVENTORY_FRIENDLY_NAME = "INT-238 Inventory";
	public static final String HISTORICALINVENTORY_FRIENDLY_NAME = "INT-315 Historical Inventory";
	public static final String FINANCIAL_PLAN_SITE_STATUS_FRIENDLY_NAME = "INT-317 Financial Plan Site Status";
	public static final String NIGHTLY_RECEIPTS_FRIENDLY_NAME = "INT-441 Nightly Receipts";
	public static final String ITEM_ATTRIBUTE_FRIENDLY_NAME = "INT- 248 Item Attribute";
	public static final String SITE_FRIENDLY_NAME = "INT-233_Site";
	public static final String ITEM_HIERARCHY_FRIENDLY_NAME = "INT-247 Item Hierarchy";
	public static final String SITE_ATTRIBUTE_FRIENDLY_NAME = "INT-250 Site Attributes";
	public static final String SUPPLIER_ORDER_FRIENDLY_NAME = "INT-240 Supplier Order";
	public static final String PRICE_FEED_FRIENDLY_NAME = "INT-562 Pricing Feed";
	public static final String CNVHISTINVENT_FRIENDLY_NAME = "CNV-071 Historical Inventory";
	public static final String CNVITEMBUILD991_FRIENDLY_NAME = "CNV-055 ItemBuild991";
	
	public static final String STORE_INV_FEED_FRIENDLY_NAME = "INT-563 Store Inv Feed";
	public static final String CLUSTER_FEED_FRIENDLY_NAME = "INT-590 Cluster Feed";
	public static final String PO_AND_TRANSFERS_FRIENDLY_NAME = "INT-244 PO And Transfers";
	public static final String SPIN_SITE_CATEGORIES_FRIENDLY_NAME = "INT-631 Spin Site Categories";
	public static final String SPIN_ASSETS_FRIENDLY_NAME = "INT-629 Spin Assets";
	public static final String SPIN_ITEM_FRIENDLY_NAME = "INT-630 Spin Item";
	public static final String ITEM_DESCRIPTIVE_ATTRIBUTES_FRIENDLY_NAME="INT-633 Item Descriptive Attribtues";
	public static final String CNV_HISTORICAL_METRICS_FRIENDLY_NAME = "CNV-072 Historical Metrics";
	
	//public static final String SPIN_RELATED_ITEMS_FRIENDLY_NAME = "INT-628 Related Items";
	public static final String SPIN_PARTS_AND_ACCESSORIES_FRIENDLY_NAME = "INT-628A Parts and Accessories";
	public static final String SPIN_PROTECTION_AGREEMENTS_FRIENDLY_NAME = "INT-628B Protection Agreements";
	public static final String HISTORICAL_METRICS_FRIENDLY_NAME = "INT-316 Historical Metrics";
	public static final String WEEKLY_SALES_HISTORY_FRIENDLY_NAME="INT-230 Weekly Sales History";
	public static final String INVENTORY_COST_FRIENDLY_NAME="INT-323 Inventory Cost";
	public static final String CNV_SALES_HISTORY_FRIENDLY_NAME = "CNV-046_Initial Sales History";
	public static final String CNV_INVENTORY_ONHAND_FRIENDLY_NAME = "CNV-001 InventoryOnHand_NIB_RIM";

   public static final String SPIN_IMAGES_FRIENDLY_NAME = "INT-632 Spin Images";

	//constants for item attribute
	public static final String ATTRIBUTE_LOOKUP_CODE = "AttributeLookupCode";
	public static final String ATTRIBUTE_TREE_LOOKUP_CODE = "AttributeTreeLookupCode";
	public static final String PARENT_ATTRIBUTE_TREE_LOOKUP_CODE= "ParentAttributeTreeLookupCode";
	public static final String VALUE_SHORT_STRING =  "Value_Shortstring";
	public static final String VALUE_DATETIME = "Value_Datetime";
	public static final String VALUE_FLOAT = "Value_Float";
	public static final String VALUE_INT =  "Value_Int";
	public static final String VALUE_BIT = "Value_Bit";
	public static final String VALUE_XML =  "Value_XML";
	public static final String VALUE_LONGSTRING = "Value_Longstring";
	public static final String VALUE_OBJECT = "Value_Object";
	public static final String ITEM = "ITEM";
	
	//constants for site attribute
	public static final String KCD_ITEM = "KCD Item";
	public static final String SUPERSEEDED_FORM = "Superseeded From";
	public static final String LIKE_ITEM = "Like Item";
	public static final String VENDOR = "Vendor";
	public static final String MANUFACTURER = "Manufacturer";
	public static final String SAFETY_STOCK_LEVEL_DAYS = "Safety Stock Level Days";
	public static final String REORDER_MULTIPLE = "Reorder Multiple";
	public static final String INACTIVE = "Inactive";
	public static final String MUNIMUM_QUANTITY = "Minimum Quantity";
	public static final String BRAND = "Brand";
	public static final String COLOR = "Color";
	public static final String COLOR_FAMILY = "Color Family";
	public static final String FUEL_TYPE = "Fuel Type";
	public static final String DISPOSITION = "Disposition";
	public static final String CAPACITY = "Capacity";
	public static final String SELL_THROUGH_RATE = "Sell Through Rate";
	public static final String LIFE_CYCLE_STATUS = "Life Cycle Status";
	public static final String RELATED_NEW_ITEM = "Related New Item";
	public static final String SALE_UNITS = "Sale Units";
	public static final String PURCHASE_UNIT = "Purchase Units";
	public static final String UNIT_OF_MEASURE = "Unit of Measure";
	public static final String STOCK_UNITS = "Stock Units";
	
	
	//Date formats
	public static final String DATE_TIME_FORMAT_FILE = "yyMMdd_hh_mm_ss_SSS";
	public static final String DATE_TIME_FORMAT_S3_FILE = "yyyyMMdd_hhmmssSSS";
	
	//Amazon s3 constants
	public static final String S3_ACCESS_KEY="accessKey";
	public static final String S3_SECRET_KEY="secretKey";
	
	//Properties constatns
	public static final String TARGET_ENV="target_env"; 
	public static final String APPLICATION_PROPERTY_FILE="sho_"+System.getProperty(TARGET_ENV)+".properties"; 
	public static final String SHO_APPLICATION_PROPERTY_FILE="messages_en.properties";
	public static final String AWS_PROPERTY_FILE="sho_"+System.getProperty(TARGET_ENV)+".properties"; 
	
	//Listener constants
	public static final String FILE_NAME ="fileName";
	public static final String SOURCE_SYSTEM = "sourceSystem";
	public static final String DESTINATION_SYSTEM = "destinationSystem";
	public static final String CORRELATION_ID="correlationId";
	public static final String ENTITY_TYPE="entityType";
	public static final String PREDECESSOR_OBJECT_ID = "PREDECESSOR";
	public static final String STATUS_COMPLETED = "COMPLETED";
	public static final String IS_RESTARTABLE = "isRestartable";
	
	//Source and target system
	public static final String ECOM_SOURCE_SYSTEM_ID = "ECOMM";
	public static final String ECOM_DESTINATION_SYSTEM_ID = "NETSUITE";
	public static final String DEFAULT_SOURCE_SYSTEM_ID = "NETSUITE";
	public static final String DEFAULT_DEST_SYSTEM_ID = "JE";
	public static final String SOURCE_SYSTEM_NETSUITE="NETSUITE";
	public static final String DESTINATION_SYSTEM_JUSTENOUGH="JUSTENOUGH";
	public static final String RELATEDITEMS_SOURCE_SYSTEM = "ECOMM";
	public static final String RELATEDITEMS_DEST_SYSTEM = "NETSUITE";
	public static final String SOURCE_SYSTEM_JUSTENOUGH = "JE";
	public static final String CNV_SOURCE_SYSTEM_ID = "SHC";
	public static final String CNV_DESTINATION_SYSTEM_ID = "JE";
	
	//DIM & IEM constants
	public static final String QUEUE_URL="sho.process.queue.uri";
	public static final String SQS_URI="sho.process.queue.uri";
	public static final String DIM_QUEUE="sho.dim.queue";
	public static final String IEM_QUEUE="sho.iem.queue";
	public static final String DATA_ERROR="DATA";
	public static final String TECHNICAL_ERROR="TECHNICAL";
	public static final String CIF_ERROR= "ERROR";
	public static final String CIF_INPROCESS = "INPROCESS";
	public static final String CIF_COMPLETE= "COMPLETE";
	public static final String CIF_PROCESSED = "PROCESSED";
	public static final String CIF_PARAMETER_HOSTNAME = "cif.parameter.hostname";
	public static final String CIF_PARAMETER_URI = "cif.parameter.uri";
	public static final String CIF_DIM_ACTIVITY_URI = "cif.dim.activity.uri";
	public static final String OBJECT_ID="THRESHOLD";
			
	//Batch Files constants
	public static final String DEFAULT_LINE_SEPARATOR = "\n";
	public static final String FILE_SEPARATOR = ".";
	public static final String FILE_NAME_SEPARATOR = "_";
	public static final String CREATE_FILE="_createfile";
	public static final String JE_API_CALL="_jeapicall";
	
	public static final String CREATE_FILE_PARAMETER="CREATEFILE";
	public static final String JE_API_CALL_PARAMETER="JECALL";
	
	public static final String BATCH_PROCESS="dataload";
	public static final String BATCH_CREATE_FILE="process";
	public static final String BATCH_UPLOAD_TO_NS="process";
	public static final String BATCH_SEND_TO_JE="process";
	public static final String BATCH_DATA_LOAD="dataload";
	public static final String SUPERSESSION_PROCESS_QUEUE="sho.supersession.process.queue";
	public static final String INTERFACE_ID ="interfaceId";
	public static final String BATCH_RIM_DATA_LOAD="rim_dataload";
	public static final String BATCH_LOC_DATA_LOAD="loc_dataload";

	
	//ENTITY NAMES FOR INTERFACE
	public static final String SUPERSESSION_ENTITY="Sup_Supersession";
	public static final String SITEATTRIBUTE_ENTITY="Sup_SiteAttribute";
	public static final String HISTORICALINVENTORY_ENTITY="Sup_HistInventory";
	public static final String CLUSTERFEED_ENTITY="SYNCNSCLUSTERFEED";
	public static final String PRICINGFEED_ENTITY = "SYNCNSPRICEFEED";
	public static final String CNVHISTINVENT_ENTITY="Cnv_HistInventory";
	public static final String CNVITEMBUILD991_ENTITY="Cnv_ItemBuild991";
	public static final String ITEM_DESCRIPTIVE_ATTRIBUTES_ENTITY = "SYNCITEMDESCRATTR";
	public static final String SITECATEGORIES_ENTITY = "SYNCNSSITECATEGORIES";
	public static final String SITE_ENTITY = "Sup_Site";
	public static final String VENDOR_ENTITY = "Sup_Vendor";
	//public static final String NIGHTLY_RECEIPTS = "SYNCNSNIGHTLYRECEIPT";
	public static final String ITEM_ENTITY = "Sup_Item";
	public static final String ITEM_HIERARCHY_ENTITY="Sup_ItemHierarchy";
	public static final String POANDTRANSFERS_ENTITY="Sup_POAndTransfers";
	public static final String STOREINVENTORY_ENTITY="SYNCNSSTOREINVFEED";
	public static final String SPINASSETS_ENTITY="SYNCNSSPINASSETS";
	public static final String SPINITEM_ENTITY="SYNCNSSPINITEM";
	public static final String INVENTORY_ENTITY = "Sup_Inventory";
	public static final String HISTORICAL_METRICS_ENTITY = "Sup_HistMetrics";
	public static final String SITEOPENCLOSED_ENTITY="Sup_SiteOpenClosed";
	public static final String CNV_HISTORICAL_METRICS_ENTITY = "Cnv_HistMetrics";
	//public static final String RELATEDITEMS_ENTITY = "SYNCNSRELATEDITEMS";
	public static final String PARTS_AND_ACCESSORIES_ENTITY = "SYNCNSPARTSACCESS";
	public static final String PROTECTION_AGREEMENTS_ENTITY = "SYNCNSPROTARGMT";
    public static final String SPINIMAGES_ENTITY="SYNCNSSPINIMAGES";
    public static final String FINANCIAL_PLAN_SITE_STATUS_ENTITY = "Sup_FinPlanSiteStat";
    public static final String SUPPLIER_ORDER_ENTITY = "Sup_SupplierOrder";
	public static final String CNV_INVENTORYONHAND_ENTITY="Mer_Cnv_InventoryOH";
	public static final String CNV_SALESHISTORY_ENTITY="Cnv_SalesHistory";
	//Exception constants
	public static  final String RUNTIME_ERROR_CODE="sho.common.error.technical.runtime";
	public static  final String SQL_ERROR_CODE="sho.common.error.technical.sql";
	public static  final String FIELDVALIDATION_ERROR_CODE ="sho.data.error.business.fieldvalidation";
	public static  final String THRESHOLDBREACH_ERROR_CODE ="sho.technical.error.business.thresholdbreach";
	public static final String ERROR_MESSAGE ="ErrorMessage";
	public static final String ERROR_CODE ="ErrorCode";
	
	//NetSuite connection constants
	public static final String  NS_USERNAME = "netSuite.webService.userName";
	public static final String  NS_PASS_KEY	= "netSuite.webService.password";
	public static final String	NS_ACCOUNT_ID = "netSuite.webService.accountID";
	public static final String	NS_ROLE_ID	= "netSuite.webService.roleID";
	public static final String	NS_APPLICATION_ID ="netSuite.webService.applicationID";
	public static final String	NS_CONNECTION_TIMEOUT = "netSuite.webService.connectionTimeout";
	public static final String	NS_RECEIVE_TIMEOUT	= "netSuite.webService.receiveTimeout";
	public static final String	NS_ENDPOINT_URL = "netSuite.webService.endpointURL";
	public static final String	NS_BATCH_SIZE = "netSuite.webService.batchSize";
	public static final String	NS_CURRENT_VERSION	= "netSuite.webService.currentVersion";
	public static final String	NS_PAGE_SIZE = "netSuite.webService.pageSize";
	public static final int	NS_TIMEOUT = 60;

	public static final String STOREINVENTORY_SOURCE_SYSTEM = "ECOMM";
	public static final String STOREINVENTORY_DEST_SYSTEM = "NETSUITE";

	public static final String SUPERSESSION_SOURCE_SYSTEM = "NETSUITE";
	public static final String SUPERSESSION_DEST_SYSTEM = "JE";
	
	public static final String PRICINGFEED_SOURCE_SYSTEM = "ECOMM";
	public static final String PRICINGFEED_DEST_SYSTEM = "NETSUITE";
	
	public static final String CNVHISTINVENT_SOURCE_SYSTEM = "SHC";
	public static final String CNVHISTINVENT_DEST_SYSTEM = "JE";
	
	public static final String CNVITEMBUILD991_SOURCE_SYSTEM = "SHO";
	public static final String CNVITEMBUILD991_DEST_SYSTEM = "NETSUITE";
	

	public static final String SPINASSETS_SOURCE_SYSTEM = "ECOMM";
	public static final String SPINASSETS_DEST_SYSTEM = "NETSUITE";
	
	public static final String SPINITEM_SOURCE_SYSTEM = "ECOMM";
	public static final String SPINITEM_DEST_SYSTEM = "NETSUITE";
	
	public static final String SPINIMAGES_SOURCE_SYSTEM = "SPIN";
    public static final String SPINIMAGES_DEST_SYSTEM = "NETSUITE";

    public static final String CNVHISTORICALMETRICS_SOURCE_SYSTEM = "SHC";
	public static final String CNVHISTORICALMETRICS_DEST_SYSTEM = "JE";
	
	// Custom record constants for pricefeed	
	public static final String PRICEFEED_NS_WEB_PRICE = "custrecord_sho_parent_item_web_price";
	public static final String PRICEFEED_NS_PRICING_SELL = "custrecord_sho_website_item_pricing_sell";
	public static final String PRICEFEED_NS_PRICING_REG = "custrecord_sho_website_item_pricing_reg";
	public static final String PRICEFEED_NS_PRICING_DISC = "custrecord_sho_website_item_pricing_disc";
	public static final String PRICEFEED_NS_MAP_FLAG = "custrecord_sho_website_item_map_flag";
	public static final String PRICEFEED_NS_PRICING_LEVEL = "custrecord_sho_website_item_pricelevel";
		

	// Custom record constants for spinassets
	public static final String SPINASSETS_NS_CUSTOM_RECORD_ROW_ID = "custrecord_sho_spin_row_id";
	public static final String SPINASSETS_NS_CUSTOM_RECORD_ITEM = "custrecord_sho_spin_item";
	public static final String SPINASSETS_NS_CUSTOM_RECORD_VALUE1 = "custrecord_sho_spin_value_1";
	public static final String SPINASSETS_NS_CUSTOM_RECORD_VALUE2 = "custrecord_sho_spin_value_2";
	public static final String SPINASSETS_NS_CUSTOM_RECORD_VALUE3 = "custrecord_sho_spin_value_3";
	public static final String SPINASSETS_NS_CUSTOM_RECORD_ACTION = "custrecord_sho_spin_action";
	public static final String SPINASSETS_NS_CUSTOM_RECORD_FILE_TYPE = "custrecord_sho_spin_file_type";
	public static final String CORRELATIONID_OR_FILENAME_NOT_FOUND = "correlationId or filName not found in message";
			
	public static final String STATUS_PROCESSED = "Processed";
	
	// Site categories
	public static final String SITECATEGORIES_BUCKET_NAME = "sitecategories.bucketname";
	public static final String SITECATEGORIES_TEMP_DATALOAD_SELECT_CLAUSE = "SELECT id ";
	public static final String SITECATEGORIES_TEMP_DATALOAD_FROM_CLAUSE = "FROM temp_dataload_netsuite_sitecategories_enriched ";
	public static final String SITECATEGORIES_TEMP_DATALOAD_WHERE_CLAUSE = "WHERE  updated_in_netsuite = 0 ";
	public static final String SITECATEGORIES_TEMP_DATALOAD_ORDER_CLAUSE = "ORDER BY id ";

	public static final String CUSTRECORD_SHO_SPIN_ACTION = "custrecord_sho_spin_action";
	public static final String CUSTRECORD_SHO_SPIN_VALUE_1 = "custrecord_sho_spin_value_1";
	public static final String CUSTRECORD_SHO_SPIN_VALUE_2 = "custrecord_sho_spin_value_2";
	public static final String CUSTRECORD_SHO_SPIN_VALUE_3 = "custrecord_sho_spin_value_3";
	public static final String CUSTRECORD_SHO_SPIN_VALUE_4 = "custrecord_sho_spin_value_4";
	public static final String CUSTRECORD_SHO_SPIN_FILE_TYPE = "custrecord_sho_spin_file_type";
	public static final String CUSTRECORD_SHO_SPIN_ITEM = "custrecord_sho_spin_item";
	
	public static final String CUSTRECORD_SHO_SPIN_ROW_ID = "custrecord_sho_spin_row_id";

	public static final String MULE_INTEG_INTG_ID = "custrecord_sho_mule_integ_ricf_intg_id";
	public static final String MULE_INTEG_REC_IMPORTED = "custrecord_sho_mule_integ_rec_imported_m";
	public static final String MULE_INTEG_BATCH_STATUS = "custrecord_sho_mule_integ_mule_batch_sta";
	public static final String MULE_INTEG_BATCH_ID = "custrecord_sho_mule_integ_batch_id";

	public static final String SITECATEGORIES_DELETE_FROM_TEMP_DATALOAD_ECOMM = "DELETE FROM temp_dataload_ecomm_sitecategories WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY)";
	public static final String SITECATEGORIES_TEMP_ENRICHED_TABLE_NAME = "temp_dataload_netsuite_sitecategories_enriched";
	
	public static final String SPINASSETS_DELETE_FROM_TEMP_DATALOAD_ECOMM = "DELETE FROM temp_dataload_ecomm_spinassets WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY)";
	
	
	// Related Items
	//public static final String RELATEDITEMS_PROC_SELECT_CLAUSE = "select distinct(id) ";
	//public static final String RELATEDITEMS_PROC_FROM_CLAUSE = "from temp_dataload_netsuite_spinrelateditems_enriched ";
	//public static final String RELATEDITEMS_PROC_ORDER_BY_CLAUSE = " ORDER BY id ";
	//public static final String CONDITIONAL_DELETE_TABLE = "DELETE FROM temp_dataload_ecomm_spinrelateditems WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY)";		
	
	//Parts and Accessories
	public static final String PARTSACCESSORIES_PROC_SELECT_CLAUSE = "select distinct(id) ";
	public static final String PARTSACCESSORIES_PROC_FROM_CLAUSE = "from temp_dataload_netsuite_spinpartsaccessories_enriched ";
	public static final String PARTSACCESSORIES_PROC_ORDER_BY_CLAUSE = " ORDER BY id ";
	public static final String PARTSACCESSORIES_CONDITIONAL_DELETE_TABLE = "DELETE FROM temp_dataload_ecomm_spinpartsaccessories WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY)";		

	//Protection Agreements
	public static final String PROTECTIONAGREEMENTS_PROC_SELECT_CLAUSE = "select distinct(id) ";
	public static final String PROTECTIONAGREEMENTS_PROC_FROM_CLAUSE = "from temp_dataload_netsuite_spinprotectionagreement_enriched ";
	public static final String PROTECTIONAGREEMENTS_PROC_ORDER_BY_CLAUSE = " ORDER BY id ";
	public static final String PROTECTIONAGREEMENTS_CONDITIONAL_DELETE_TABLE = "DELETE FROM temp_dataload_ecomm_spinprotectionagreement WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY)";

	
	// Source and Target system
	public static final String ORGANIZATION_ID = "SHO";
	public static final String NETSUITE = "NETSUITE";
	public static final String ECOMM = "ECOMM";
	public static final String JUSTENOUGH = "JE";
	public static final String RIM = "RIM";
	public static final String SHC = "SHC";
	
	//Query for store inv feed
	
	// Store inventory feed dataload partitioner query
		
		public static final String STOREINVFEED_TEMP_DATALOAD_SELECT_CLAUSE_UPDATE = "SELECT @rownum:=@rownum + 1 as r ";
		public static final String STOREINVFEED_TEMP_DATALOAD_FROM_CLAUSE_UPDATE = "FROM ( SELECT * FROM temp_dataload_ns_onhandquantity_enriched where operation_type in('U')) t, (SELECT @rownum := 0) r";
		
		public static final String STOREINVFEED_TEMP_DATALOAD_SELECT_CLAUSE_CREATE = "SELECT @rownum:=@rownum + 1 as r ";
		public static final String STOREINVFEED_TEMP_DATALOAD_FROM_CLAUSE_CREATE = "FROM ( SELECT * FROM temp_dataload_ns_onhandquantity_enriched where operation_type in('C')) t, (SELECT @rownum := 0) r";

		
		public static final String STORE_INVENTORY_SELECT_CLAUSE = "select rownum r from(SELECT items.shc_item_number, "
       +"items.item_id 'Item Id', "
       +"locations.name, "
       +"t4.location_id 'Location Id', "
       +"t4.WEBSITE_AVAILABLE_INVENTORY 'Website Available Inventory', "
       +"NVL2(t4.WEBSITE_THRESHOLD_QUANTITY,t4.WEBSITE_THRESHOLD_QUANTITY,0)'Website Threashold Quantity', "
       +"t4.ITEM__LOCATION_LEVEL_ATTRIB_ID 'Item/Loc Custom Record Id' ";
	
	        public static final String STORE_INVENTORY_FROM_CLAUSE = "FROM items "
		       +"LEFT OUTER JOIN item_designation_values "
		       +"ON (items.item_designation_id = item_designation_values.list_id) "
		       +"JOIN ITEM__LOCATION_LEVEL_ATTRIBUT t4 "
		       +"on items.item_id = t4.item_id "
		       +"LEFT OUTER JOIN Locations "
		       +"ON t4.location_id = Locations.LOCATION_ID "
		       +"LEFT OUTER JOIN store_types "
		       +"ON (locations.store_type_id = store_types.list_id)";
			
			 public static final String STORE_INVENTORY_WHERE_CLAUSE = " WHERE  items.isinactive = 'No' AND t4.IS_INACTIVE = 'F' "
					   +" AND item_designation_values.list_item_name(+) = 'NEW ITEM' "
					   +" AND store_types.list_item_name IN ('AHS', 'HAS', 'HTS'))";

		
            public static final String TEMP_DATALOAD_ECOMM_STOREINVFEED = "TRUNCATE TABLE temp_dataload_ecomm_storeinventoryfeed";
		public static final String TEMP_DATALOAD_NETSUITE_STOREINVFEED = "TRUNCATE TABLE temp_dataload_ns_onhandquantity";
		public static final String TEMP_ONHANDQUANTITY_STOREINVFEED_ENRICH = "TRUNCATE TABLE temp_dataload_ns_onhandquantity_enriched";
		
		
		//Constats for store inv feed reader/mapper
		
		public static final String STOREINV_ENRICHED_MAPPER_LOCATION_ID = "location_id";
		public static final String STOREINV_ENRICHED_MAPPER_LOCATION_NAME = "location_name";
		public static final String  STOREINV_ENRICHED_MAPPER_ITEM_ID = "item_id";
		public static final String  STOREINV_ENRICHED_MAPPER_ITEM_NAME = "item_name";
		public static final String STOREINV_ENRICHED_MAPPER_SHC_ITEM_NUMBER = "shc_item_number";
		public static final String STOREINV_ENRICHED_MAPPER_WEBSITE_AVAIL_INVENTORY ="website_available_inventory";
		public static final String STOREINV_ENRICHED_MAPPER_WEBSITE_THRESHOLD_QUANTITY = "website_threshold_quantity";
		public static final String STOREINV_ENRICHED_MAPPER_ITEM_LOCATION_ATTRIBUTE_ID = "item_location_attribute_id";
		public static final String  STOREINV_ENRICHED_MAPPER_OPERATION_TYPE = "operation_type";
		public static final String  STOREINV_ENRICHED_MAPPER_UPDATE_FLAG= "update_flag";
		public static final String  STOREINV_ENRICHED_MAPPER_PRESENT_IN_NETSUITE = "present_in_netsuite";
		public static final String STOREINV_ENRICHED_MAPPER_UPDATED_IN_NETSUITE = "updated_in_netsuite";
		
		
		public static final String STOREINV_NETSUITE_MAPPER_SHC_ITEM_NUMBER = "shc_item_number";
		public static final String STOREINV_NETSUITE_MAPPER_ITEM_ID = "Item Id";
		public static final String STOREINV_NETSUITE_MAPPER_LOCATION_NAME = "name";
		public static final String STOREINV_NETSUITE_MAPPER_NAME = "name";
		public static final String STOREINV_NETSUITE_MAPPER_LOCATION_ID = "Location Id";
		public static final String STOREINV_NETSUITE_MAPPER_WEBSITE_AVAIL_INV = "Website Available Inventory";
		public static final String STOREINV_NETSUITE_MAPPER_WEBSITE_THRESHOLD_QUANTITY = "Website Threashold Quantity";
		public static final String STOREINV_NETSUITE_MAPPER_CUST_REC_ID = "Item/Loc Custom Record Id";
		public static final String STOREINV_NETSUITE_MAPPER_ITEM_NAME = "Item Name";

		public static final String STOREINV_ENRICHED_TABLE = "temp_dataload_ns_onhandquantity_enriched";
		public static final String STOREINV_INTERFACE_ID = "563";
		public static final String STOREINV_UPDATE_BATCH_INTERNAL_ID = "535";
		
		public static final String STOREINV_CREATE_CUSTRECORD_SHO_WEBSITE_AVAIL_INVENTORY = "custrecord_sho_website_avail_inventory";
		public static final String STOREINV_CREATE_CUSTRECORD_SHO_ITEM_LOC_ITEM = "custrecord_sho_item_loc_item";
		public static final String STOREINV_CREATE_CUSTRECORD_SHO_WEBSITE_THRESHOLD_QTY = "custrecord_sho_website_threshold_qty";
		public static final String STOREINV_CREATE_CUSTRECORD_SHO_ITEM_LOC = "custrecord_sho_item_loc";
       
		public static final String STOREINV_UPDATE_CUSTRECORD_SHO_WEBSITE_AVAIL_INVENTORY = "custrecord_sho_website_avail_inventory";
		public static final String INVENTORYCOST_ENTITY="Sup_InventoryCost";
		
		//Ecom Item validator messages
		public static final String ROW_ID_NULL_MESSAGES="RowId should not be null or empty";
		public static final String NS_ITEM_ID_NULL_MESSAGES="NS ItemId should not be null or empty";
		public static final String SOURCEKEY_NULL_MESSAGES="SourceKey should not be null or empty";
		public static final String LINKED_NS_ITEM_ID_NULL_MESSAGES="Linked NS ItemId should not be null or empty";
		public static final String PS_NS_ITEM_ID_NULL_MESSAGES="pa_ns_item_id should not be null or empty";
		public static final String LINKED_BRAND_MODEL_ITEM_NAME_NULL_MESSAGES ="Linked Brand Model Item Name should not be null or empty";
		public static final String PS_NS_ITEM_NAME_NULL_MESSAGES="pa_ns_item_name should not be null or empty";
		public static final String NS_SITE_NAME_NULL_MESSAGES="NS SiteName should not be null or empty";
		public static final String NS_HIERARCHY_DETAILS_NULL_MESSAGES="NS HierarchyDetails should not be null or empty";
		public static final String GROUP_NAME_NULL_MESSAGES="NS groupname should not be null or empty";
		public static final String NAME_NULL_MESSAGES="Name should not be null or empty";
		public static final String RANK_NULL_MESSAGES="Rank should not be null or empty";
		public static final String ATTRIBUTE_VALUE_NULL_MESSAGES="Attribute value should not be null or empty";
		public static final String ACTION_NULL_MESSAGES="Action value should not be null or empty";
		
		//public static final String SPINIMAGES_NS_CUSTOM_RECORD_ROW_ID = "custrecord_sho_spin_row_id";
	//CNV HistoricalInvnetory messages
		public static final String WEEK_END_DAY="week end day value should not be null or empty";
		public static final String LOC_NBR="location no value should not be null or empty";
		public static final String PRD_IRL_NO="PrdIrl no value should not be null or empty";
		public static final String DIV_NO="Div No value should not be null or empty";
		public static final String ITM_NO="Itm no value should not be null or empty";
		public static final String SKU_NO="Sku no value should not be null or empty";
		public static final String INS_SUBTYP="InsSubTyp  value should not be null or empty";
		public static final String ITM_PRGDT="ItmPrgDt  value should not be null or empty";
		public static final String OH_UNQTY="Unit Qty  value should not be null or empty";
		public static final String OH_CSTDLLR="cost dollar value should not be null or empty";
		public static final String OH_SLLDLLR="Sll dollar value should not be null or empty";
		public static final String CNV_HISTORICAL_INVENTORY_DATE_FORMAT = "MM/dd/yyyy";//="location no value should not be null or empty";
		public static final String CNV_STOREDPROCEDURE_ERROR = "sho.data.error.business.exception.stored_procedure_error";
		public static final String CNV_QUEUE_MISSING_ERROR = "sho.common.error.technical.endpointconnection.queue_url_missing";
		//public static final String SPINIMAGES_NS_CUSTOM_RECORD_ITEM = "custrecord_sho_spin_item";
		//public static final String SPINIMAGES_NS_CUSTOM_RECORD_FILE_TYPE = "custrecord_sho_spin_file_type";
		
		//CNV salesHistory messages
		public static final String SLS_UNQTY=" slsUnQty value should not be null or empty";
		public static final String TRS_TYPCD=" trsTypCd value should not be null or empty";
		public static final String SLS_TYPCD=" slsUnQty value should not be null or empty";
		public static final String SLS_CSTDLR=" slsCstDlr value should not be null or empty";
		public static final String SLS_SLLDLR=" slsSllDlr value should not be null or empty";
		public static final String MDS_STS=" mdsSts value should not be null or empty";
		
		//Receipts
		
		public static final String RCP_TYP_CP ="rcpTypCd value should not be null or empty";
		public static final String RCP_UN_QT ="rcpUnQt value should not be null or empty";
		public static final String RCP_CST_DLR="rcpCstDlr value should not be null or empty";
		public static final String RCP_SLL_DLR="rcpSllDlr value should not be null or empty";
		
		//CNV Historical Metrics
		public static final String TYPE_CODE = "invAdjTypeCode value should not be null or empty";
		public static final String INV_ADJ_UNITS_QUANTITY = "invAdjUnitsQty value should not be null or empty";
		public static final String INV_ADJ_COST_DOLLAR = "invAdjCostDollar value should not be null or empty";
		public static final String INV_ADJ_SLL_DOLLAR = "invAdjSllDollar value should not be null or empty";
		
		// Custom record constants for spinitem
		public static final String SPINITEM_NS_CUSTOM_RECORD_NS_ITEM_ID = "custrecord_sho_ns_item_id";
		public static final String SPINITEM_NS_CUSTOM_RECORD_DIVISION_NBR = "custrecord_sho_spin_division_number";
		public static final String SPINITEM_NS_CUSTOM_RECORD_ITEM_NBR = "custrecord_sho_spin_item_number";
		public static final String SPINITEM_NS_CUSTOM_RECORD_OUTLET_ITEM_ID = "custrecord_sho_spin_legacy_outlet_id";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHC_DIV_ITEM_NUMBER = "custrecord_sho_spin_shc_item_number";
		public static final String SPINITEM_NS_CUSTOM_RECORD_MODEL_NUMBER = "custrecord_sho_spin_man_mod_num";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SPIN_UNIQUE_ID = "custrecord_sho_spin_spin_item_id";
		public static final String SPINITEM_NS_CUSTOM_RECORD_UPC = "custrecord_sho_spin_upc_code";
		public static final String SPINITEM_NS_CUSTOM_RECORD_BRAND_NAME = "custrecord_sho_spin_item_brand";
		public static final String SPINITEM_NS_CUSTOM_RECORD_ITEM_NAME = "custrecord_sho_spin_disp_name";
		public static final String SPINITEM_NS_CUSTOM_RECORD_ITEM_NAME_FULL = "custrecord_sho_spin_item_name_full";
		public static final String SPINITEM_NS_CUSTOM_RECORD_BRAND_MODEL_ITEM_NAME = "custrecord_sho_spin_brand_model_item_nam";
		public static final String SPINITEM_NS_CUSTOM_RECORD_BRAND_MODEL_ITEM_NAME_FUL= "custrecord_sho_spin_brand_model_item_ful";
		public static final String SPINITEM_NS_CUSTOM_RECORD_URL_COMPONENT = "custrecord_sho_spin_url_component";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHORT_DESCRIPTION = "custrecord_sho_spin_featured_description";
		public static final String SPINITEM_NS_CUSTOM_RECORD_LONG_DESCRIPTION = "custrecord_sho_spin_detail_description";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SEARCH_KEYWORDS = "custrecord_sho_spin_search_key";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHIPPING_LENGTH = "custrecord_sho_spin_shipping_depth";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHIPPING_HEIGHT = "custrecord_sho_spin_shipping_height";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHIPPING_WEIGHT = "custrecord_sho_spin_shipping_weight";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHIPPING_WIDTH = "custrecord_sho_spin_shipping_width";
		public static final String SPINITEM_NS_CUSTOM_RECORD_DELIVERY_AVAILABLE_FLAG = "custrecord_sho_spin_item_delivery_eligib";
		public static final String SPINITEM_NS_CUSTOM_RECORD_SHIPPING_AVAILABLE_FLAG = "custrecord_sho_spin_item_ship_eligible";
		public static final String SPINITEM_NS_CUSTOM_RECORD_MAP_RULE_ID = "custrecord_sho_spin_map_rule";
		public static final String SPINITEM_NS_CUSTOM_RECORD_MAP_TEXT = "custrecord_sho_spin_map_rule_message";
		public static final String SPINITEM_NS_CUSTOM_RECORD_MAP_DESCRIPTION = "custrecord_sho_spin_map_rule_description";
		public static final String SPINITEM_NS_CUSTOM_RECORD_LIST_PRICE = "custrecord_sho_spin_list_price";
		public static final String SPINITEM_NS_CUSTOM_RECORD_AVERAGE_RATING = "custrecord_sho_spin_average_rating";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_WIDTH = "custrecord_sho_spin_width";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_HEIGHT = "custrecord_sho_spin_height";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_DEPTH = "custrecord_sho_spin_depth";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_WEIGHT = "custrecord_sho_spin_weight";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_CAPACITY = "custrecord_sho_spin_capacity";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_COLOR = "custrecord_sho_spin_color_family";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_HIGHEFFICIENCY = "custrecord_sho_spin_high_efficiency";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_ENERGYSTARCOMPLIANT = "custrecord_sho_spin_energy_star";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_WARRANTYLENGTH = "custrecord_sho_spin_warranty_length";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_MATERIAL = "custrecord_sho_spin_material";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_RANGESTYLE = "custrecord_sho_spin_range_style";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_COOKINGSURFACE = "custrecord_sho_spin_cooking_surface";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_NUMBEROFBURNERS = "custrecord_sho_spin_no_burners";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_NUMBEROFOVENS = "custrecord_sho_spin_no_ovens";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_OVENTYPE = "custrecord_sho_spin_oven_type";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_VENTING = "custrecord_sho_spin_venting";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_EXHAUSTMETHOD = "custrecord_sho_spin_exhaust_method";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_FUELTYPE = "custrecord_sho_spin_fuel_type";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_CONVECTIONCOOKING = "custrecord_sho_spin_convection_cooking";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_DEFROSTSYSTEM = "custrecord_sho_spin_defrost_system";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_ICEMAKER = "custrecord_sho_spin_ice_maker";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_BUILTIN = "custrecord_sho_spin_built_in";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_WASHCYCLES = "custrecord_sho_spin_wash_cycles";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_DRYCYCLES = "custrecord_sho_spin_dry_cycles";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_CONTROLLOCATION = "custrecord_sho_spin_control_loc";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_DRYERINTERIORSIZE = "custrecord_sho_spin_dry_interior_size";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_DRYERPOWERSOURCE = "custrecord_sho_spin_dryer_power_source";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_TUBMATERIAL = "custrecord_sho_spin_tub_material";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_COMFORT = "custrecord_sho_spin_comfort";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_MATTRESSTOP = "custrecord_sho_spin_mattress_top";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_STANDARDORCOUNTERDEPTH = "custrecord_sho_spin_standard_counter_dep";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_FEATURES = "custrecord_sho_spin_features";
		public static final String SPINITEM_NS_CUSTOM_RECORD_ENERGY_GUIDE_URL = "custrecord_sho_spin_energy_guide_url";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_WATTAGERANGE = "custrecord_sho_spin_wattage_range";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_THRUDOORDISPENSER = "custrecord_sho_spin_thru_door_dispenser";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_COLLECTION = "custrecord_sho_spin_collection";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FACET_UPHOLSTERY_TYPE = "custrecord_sho_spin_upholstery_type";
		public static final String SPINITEM_NS_CUSTOM_RECORD_FILE1_ACTION = "custrecord_sho_spin_file1_action";
		public static final String SPINITEM_NS_CUSTOM_RECORD__FILE1_BATCH_RECORD = "custrecord_sho_spin_file1_batch_record";
		public static final String SPIN_NS_CUSTOM_RECORD_BATCH_RECORD="custrecord_sho_spin_batch_record";
		
		// Custom record constants for clusterfeed
		public static final String CLUSTERFEED_NS_CUSTOM_RECORD_LOCATION_PRICELEVEL ="custrecord_ra_location_pricelevel";
		
		 public static final String SPINIMAGES_NS_CUSTOM_RECORD_FILE_TYPE = "custrecord_sho_spin_file_type";
         public static final String SPINIMAGES_NS_CUSTOM_RECORD_VALUE1 ="custrecord_sho_spin_value_1";
         public static final String SPINIMAGES_NS_CUSTOM_RECORD_VALUE2 ="custrecord_sho_spin_value_2";
         public static final String SPINIMAGES_NS_CUSTOM_RECORD_ACTION = "custrecord_sho_spin_action";
         public static final String SPINIMAGES_NS_CUSTOM_RECORD_ROW_ID = "custrecord_sho_spin_row_id";
		 public static final String SPINIMAGES_NS_CUSTOM_RECORD_ITEM = "custrecord_sho_spin_item";
		 
		 //ECOMM FILE PREFIX Constants
		 public static final String SPIN_ASSETS_FILE_PREFIX="sho.assetInfoFeed.prefix";
		 public static final String CLUSTERFEED_FILE_PREFIX="sho.clusterFeeed.prefix";
		 public static final String PRICEFEED_FILE_PREFIX="sho.priceFeed.prefix";
		 public static final String CNVHISTINVENT_FILE_PREFIX="sho.cnvhistinvent.prefix";
		 public static final String SPIN_IMAGES_FILE_PREFIX="sho.imageFeed.prefix";
		 public static final String SPIN_ITEM_FILE_PREFIX="sho.itemFeed.prefix"; 
		 public static final String SPIN_SITECATEGORIES_FILE_PREFIX="sho.siteCategory.prefix";
		 public static final String STOREINVFEED_FILE_PREFIX="sho.storeInvFeed.prefix";
		 //public static final String RELATEDITEMS_FILE_PREFIX="sho.relatedItemFeed.prefix";
		  public static final String PARTS_AND_ACCESSORIES_FILE_PREFIX="sho.partsAccessories.prefix";
		 public static final String PROTECTION_AGREEMENTS_FILE_PREFIX="sho.protectionAgreements.prefix";
		 public static final String ITEMDESCATTRIBUTES_FILE_PREFIX="sho.itemDescAttribute.prefix"; 
		 public static final String ONHAND_INVTR_LOC_FILE_PREFIX="sho.cnv.onhandinvtr.loc.prefix";
		 public static final String ONHAND_INVTR_RIM_FILE_PREFIX="sho.cnv.onhandinvtr.rim.prefix";
		 
		 //CONVERSION FILE PREFIX Constants
		 public static final String CNV_HISTORICAL_METRICS_FILE_PREFIX="sho.cnvhistmetrics.prefix";
		 public static final String CVNSALESHISTORY_FILE_PREFIX="sho.cnvsaleshistory.prefix";
		 
		 //NS Internal Id constants
		 public static final String CUSTRECORD_SHO_MULE_INTEG_BATCH_DETAIL ="customrecord_sho_mule_integ_batch_detail"; //upda
		 public static final String CUSTRECORD_SHO_WEBSITE_ITEM_PRICING ="customrecord_sho_website_item_pricing"; //pricefeed
		 public static final String CUSTRECORD_SHO_SPIN_INTEG_BATCH_DETAIL ="customrecord_sho_spin_staging_file1"; //spin item
		 public static final String CUSTRECORD_SHO_SPIN_STAGING ="customrecord_sho_spin_staging";
		 public static final String CUSTRECORD_SHO_ITEM_LOCATION="customrecord_sho_item_location"; //invfeed
		 //public static final String CUSTRECORD_SHO_ITEM_DESC_ATT ="customrecord_sho_item_desc_attributes";
		 //public static final String CUSTRECORD_SHO_LOC_DETAILS ="customrecord_sho_loc_details";
		 public static final String CUSTRECORD_SHO_POANDTRANSFERS_INTEG_BATCH_DETAIL ="customrecord_sho_poandtransfers_staging_file"; //po and transfers
		 
		public static final String SPIN_ASSETS_NS_PROFILE = "spinassets.ns.profile";
		 public static final String PRICEFEED_NS_PROFILE = "pricefeed.ns.profile";
		 public static final String CNVHISTINVENT_NS_PROFILE = "cnvhistinvent.ns.profile";
		 public static final String CLUSTERFEED_NS_PROFILE = "clusterfeed.ns.profile";
		 public static final String STOREINVFEED_NS_PROFILE = "storeinvfeed.ns.profile";
		 public static final String SPIN_IMAGES_NS_PROFILE = "spinimages.ns.profile";
		 public static final String SPIN_ITEM_NS_PROFILE = "spinitem.ns.profile";
		 public static final String SPIN_SITECATEGORIES_NS_PROFILE = "spincategories.ns.profile";
		 //public static final String RELATEDITEMS_NS_PROFILE = "relateditems.ns.profile";
		 public static final String PARTS_AND_ACCESSORIES_NS_PROFILE = "partsaccessories.ns.profile";
		 public static final String PROTECTION_AGREEMENTS_NS_PROFILE = "protectionagreements.ns.profile";
		 public static final String ITEMDESCATTRIBUTES_NS_PROFILE = "itemdescriptiveattributes.ns.profile";
		 public static final String POANDTRANSFERS_NS_PROFILE = "poandtransfers.ns.profile";
		 public static final String SALES_HISTORY_NS_PROFILE = "saleshistory.ns.profile";
		 public static final String CNV_HISTORICAL_METRICS_SHC_PROFILE = "cnvhistoricalmetrics.ns.profile";
		 public static final String INVTR_ONHAND_NS_PROFILE = "invntonhand.ns.profile";
		 
		 public static final String JE_COMPANY ="justEnough.webService.company";
		 public static final String JE_USERNAME ="justEnough.webService.username";
		 public static final String JE_PASSWORD ="justEnough.webService.password";
		
		//ITEMATTRIBUTE
		public static final String ITEMATTRIBUTE_ENTITY="Sup_ItemAttribute";
	    public static final String COMMONENTITYATTRIBUTE_ENTITY="Sup_ComEntAttribute";
	    
	    
		//WEEKLYSALESHISTORY
		public static final String WEEKLYSALESHISTORY_ENTITY="Sup_WeeklySalesHist";
		
		//NIGHTLYRECEIPTS
		public static final String NIGHTLYRECEIPTS_ENTITY="Sup_NightlyReceipt";
	    	    
	    public static final String ITEMATTRIBUTE_ITEMID="ItemId";
	    public static final String ITEMATTRIBUTE_ITEMCODE="ItemCode";
	    public static final String ITEMATTRIBUTE_SUPERSEEDED_FROM ="Superseeded From";
	    public static final String ITEMATTRIBUTE_LIKE_ITEM="Like Item";
	    public static final String ITEMATTRIBUTE_KCD_ITEM="KCD Item";
	    public static final String ITEMATTRIBUTE_VENDOR="Vendor";
	    public static final String ITEMATTRIBUTE_SAFETYSTOCKLEVEL_DAYS="Safety Stock Level Days";
	    public static final String ITEMATTRIBUTE_MANUFACTURER="Manufacturer";
	    public static final String ITEMATTRIBUTE_REORDER_MULTIPLE="Reorder Multiple";
	    public static final String ITEMATTRIBUTE_INACTIVE="Inactive";
	    public static final String ITEMATTRIBUTE_MINIMUM_QUANTITY="Minimum Quantity";
	    public static final String ITEMATTRIBUTE_BRAND="Brand";
	    public static final String ITEMATTRIBUTE_COLOR="Color";
	    public static final String ITEMATTRIBUTE_COLOR_FAMILY="Color Family";
	    public static final String ITEMATTRIBUTE_FUEL_TYPE="Fuel Type";
	    public static final String ITEMATTRIBUTE_DISPOSITION="Disposition";
	    public static final String ITEMATTRIBUTE_CAPACITY="Capacity";
	    public static final String ITEMATTRIBUTE_SELL_THROUGH_RATE="Sell Through Rate";
	    public static final String ITEMATTRIBUTE_LIFE_CYCLE_STATUS="Life Cycle Status";
	    public static final String ITEMATTRIBUTE_RELATED_NEW_ITEM="Related New Item";
	    public static final String ITEMATTRIBUTE_UNIT_OF_MEASURE="Unit of Measure";
	    public static final String ITEMATTRIBUTE_SALE_UNITS="Sale Units";
	    public static final String ITEMATTRIBUTE_PURCHASE_UNITS="Purchase Units";
	    public static final String ITEMATTRIBUTE_STOCK_UNITS="Stock Units";
	    
	    public static final String JESQLTYPE_VALUEFLOAT="valueFloat";
	    public static final String JESQLTYPE_VALUELONGSTRING="valueLongstring";
	    public static final String JESQLTYPE_VALUEINT="valueInt";
	   
	    
		
		//Calendar dates for JE
		public static final String JUSTENOUGH_FIRST_CALENDAR_DATE = "justEnough.firstCalender.date";
		public static final String JUSTENOUGH_LAST_CALENDAR_DATE = "justEnough.lastCalender.date";
		public static final String INVENTORY_COST_ENDDATE="inventorycost.enddate";
		
		
		public static final String CATEGORY = "CATEGORY";
		public static final String DIVISION = "DIVISION";
		public static final String LINE ="LINE";
		public static final String SUBLINE ="SUBLINE";
		public static final String CLASS ="CLASS";
		public static final String VENDOR_ITEM ="VENDOR";
		
		// Custom record constants for po and transfers
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDERHEADER_ID = "custrecord_sho_je_orderheader_id";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_LINE_NUMBER = "custrecord_sho_je_order_line_number";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_ITEM_NUMBER = "custrecord_sho_je_order_item_number";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_VENDOR_SHIP_FROM = "custrecord_sho_je_vendor_ship_from";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_VENDOR_NAME_FIELD = "custrecord_sho_je_vendor_name_field";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_LOCATION_TO_FIELD = "custrecord_sho_je_location_to_field";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_QUANTITY = "custrecord_sho_je_order_quantity";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORDER_DATE_FIELD = "custrecord_sho_je_order_date_field";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ARRIVAL_DATE_FIELD = "custrecord_sho_je_arrival_date_field";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_PO_TO_FLAG = "custrecord_sho_je_po_to_flag";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_ORIGINAL_PO_NUM = "custrecord_sho_je_original_po_num";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_COST_PRICE_FIELD = "custrecord_sho_je_cost_price_field";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_CLASS_FIELD = "custrecord_sho_je_class_field";
		public static final String POANDTRANSFERS_JE_CUSTOM_RECORD_JE_COLOR_FAMILY = "custrecord_sho_je_color_family";
				
		public static final String TRUNCATE_POANDTRANSFERS_LOOKUP_TABLE = "TRUNCATE TABLE temp_lookup_netsuite_poAndTransfers";
		public static final String  PO_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		public static final String  PO_TIMEZONE = "US/Central";
		
		public static final String TRUNCATE_CNV_SALESHISTORY_DATALOAD_TABLE = "TRUNCATE TABLE temp_dataload_shc_cnvsaleshistory";
		
		public static final String CNV_INVENTORY_ONHAND_PROC_SELECT_CLAUSE = "select id ";
     	public static final String CNV_INVENTORY_ONHAND_PROC_FROM_CLAUSE = " from merch_cnvinventoryonhand_dataload_enriched_positive ";
   	    public static final String CNV_INVENTORY_ONHAND_PROC_OREDRBY_CLAUSE = " ORDER BY id ASC ";
   	    
   	    
   	    public static final String CUSTRECORD_SHO_INV_ADJ_ONHAND_QTY = "custrecord_sho_inv_adj_onhand_qty";
   	    public static final String CUSTRECORD_SHO_INV_ADJ_STORE_NBR = "custrecord_sho_inv_adj_store_nbr";
   	    public static final String CUSTRECORD_SHO_INV_ADJ_SHC_ITEM_NBR = "custrecord_sho_inv_adj_shc_item_nbr";
   	    public static final String CUSTRECORD_SHO_INV_ADJ_DIVISION  = "custrecord_sho_inv_adj_division";
   	    public static final String CUSTRECORD_SHO_CNV_INTRONHAND_INTEG_BATCH_DETAIL = "customrecord_sho_cnv_inventory_onhand";
	   	    
   	    
   	 public static final String POS_NULL_MESSAGES = "POS should not be null or empty";
   	 public static final String EIS_NULL_MESSAGES = "EIS should not be null or empty";
   	 public static final String STORE_NUMBER_NULL_MESSAGES = "Store number should not be null or empty";
   	 public static final String DIVISION_NUMBER_NULL_MESSAGES = "Division number should not be null or empty";
   	 public static final String ITEM_NUMBER_NULL_MESSAGES = "Item number should not be null or empty";
   	 public static final String ONHAND_QUANTITY_NULL_MESSAGES = "OnHand quantity should not be null or empty";
	public static final String PO_AND_TRANSFERS_STORED_PROCEDURE = "{call cusp_populate_ctbl_PO_Load_Temp(?,?)}";
	public static final Object PO_AND_TRANSFERS_SELECT_QUERY = " select JEOrderLineNumber ";
	public static final Object PO_AND_TRANSFERS_FROM_QUERY = " from ctbl_PO_Load_Temp ";
	public static final Object PO_AND_TRANSFERS_ORDERBY_QUERY = " ORDER BY JEOrderLineNumber ASC ";
	
   	public static final String LOC_ = "loc_";
   	public static final String RIM_ = "rim_";
	//Partition query for CNVReceipts Header
		
		public static final String CNVRECEIPTS_HEADER_PROC_SELECT_CLAUSE = "select id  ";
		public static final String CNVRECEIPTS_HEADER_PROC_FROM_CLAUSE = " from temp_dataload_shc_cnv_supplier_order_header_enriched ";
		public static final String CNVRECEIPTS_HEADER_PROC_OREDRBY_CLAUSE = " ORDER BY id  ASC ";
	
	
	
	//Partition query for CNVReceipts Line

		public static final String CNVRECEIPTS_LINE_PROC_SELECT_CLAUSE = "select id  ";
		public static final String CNVRECEIPTS_LINE_PROC_FROM_CLAUSE = " from temp_dataload_shc_cnv_supplier_order_line_enriched ";
		public static final String CNVRECEIPTS_LINE_PROC_OREDRBY_CLAUSE = " ORDER BY id  ASC ";
		
	//Partition query for CNVReceipts Receipts
		
		public static final String CNVRECEIPTS_SUPPLIER_PROC_SELECT_CLAUSE = "select id  ";
		public static final String CNVRECEIPTS_SUPPLIER_PROC_FROM_CLAUSE = " from temp_dataload_shc_cnv_supplier_receipts_enriched ";
		public static final String CNVRECEIPTS_SUPPLIER_PROC_OREDRBY_CLAUSE = " ORDER BY id  ASC ";

		
		
		public static final String CNV_RECEIPTS_FRIENDLY_NAME = "INT-019 Cnv Receipts";

			
		public static final String CNVRECEIPTS_ENTITY="Cnv_Receipts";
				
				
		public static final String CNVRECEIPTS_SOURCE_SYSTEM = "SHC";
	    public static final String CNVRECEIPTS_DEST_SYSTEM = "JE";
	
	
	
	 //CNV RECEIPTS PREFIX Constants
		public static final String CNV_RECEIPTS_FILE_PREFIX="sho.cnvReceipts.prefix";
		 
		public static final String CNV_RECEIPTS_NS_PROFILE = "cnvreceipts.ns.profile";
		 
		 
		public static final String TEMP_DATALOAD_SHC_CNVRECEIPTS = "TRUNCATE TABLE temp_dataload_shc_cnvreceipts";
		public static final String STOREINV_LOCAL_FILE_PATH = "sho.storeInvFeed.writeFilePath";
		
		
		public static final String NETSUITE_CONNECTIVITY_RETRY_COUNT="sho.netsuite.retry.count";
		public static final String NETSUITE_CONNECTIVITY_SLEEP_COUNT="sho.netsuite.connectivity.sleepTmeMiliSeconds";
		public static final int GET_DATA_CENTER_URLS_RESULT_RETRY_COUNT = 5;
		public static final int DATA_CENTER_URLS_RETRY_COUNT = 5;
		
		public static final String S3_BUCKET_NAME ="sho.s3Bucket.name";
		
		public static final String RECORD_COUNT = "processedRecordCount";
        public static final String UNPROCESSED_RECORD_COUNT= "unprocessedRecCount";
        public static final String BATCH_ITEM = "batch_item";
        public static final String INV_BATCH_ITEM = "inv_batch_item";
		public static final String CNV_DATE_FORMAT = "MM/dd/yyyy";//="location no value should not be null or empty";
		public static final String STRING_REPLACE = "\"^\"|\"$\"";

		
		public static final String PREDECESSOR_INCOMPLETE_ERROR_KEY = "sho.data.error.business.exception.predecessor.incomplete";
		public static final String THRESHOLD_BREACHED_ERROR_KEY = "sho.technical.error.business.thresholdbreach";

		public static final String CNV_HISTORICAL_METRICS_TABLE = "temp_dataload_shc_cnvhistoricalmetrics_enriched";
		public static final String CNV_HISTORICAL_INVENTORY_TABLE = "temp_dataload_shc_cnvhistoricalinventory_enriched";
		public static final String CNV_SALES_HISTORY_TABLE = "temp_dataload_shc_cnvsaleshistory_enriched";
		
	//constants for sho-pos-cnv-invoiceinfo
		public static final String POS_CNVINVOICEINFO_ENTITY = "Pos_CnvInvoiceInfo";
		public static final String POS_CNVINVOICEINFO_FRIENDLY_NAME = "POS_CNV-089 Invoice Info";
		
	//constants for sho-pos-cnv-custinfo
		 public static final String CUSTCNVINFO_FILE_PREFIX="sho.custInfo.prefix";
		 public static final String CUSTCNVINFO_FILE2_PREFIX="sho.custInfo2.prefix";
		 public static final String CNVCUSTINFO_NS_PROFILE = "cnvcustinfo.ns.profile";
		 
		 public static final String  CUSTCNVINFO_ENRICHED_SELECT_CLAUSE = "SELECT id ";
		 public static final String CUSTCNVINFO_ENRICHED_FROM_CLAUSE = "FROM pos_cnvcustinfo_enrich where operation_type=('U') AND correlationid = ':correlationId';";
		 
		 public static final String CUSTCNVINFO_ENRICHED_CREATE_FROM_CLAUSE = "FROM pos_cnvcustinfo_enrich where operation_type=('C') AND correlationid = ':correlationId';";

		 public static final String  CUSTCNVINFO_SUBSIDIARY_SELECT_CLAUSE = " SELECT SUBSIDIARY_ID ";
		 public static final String CUSTCNVINFO_SUBSIDIARY_FROM_CLAUSE = " FROM subsidiaries ";
		 public static final String CUSTCNVINFO_SUBSIDIARY_ORDERBY_CLAUSE = " order by SUBSIDIARY_ID ";
		 
		 public static final String  CUSTCNVINFO_CATEGORY_SELECT_CLAUSE = " SELECT customer_type_id ";
		 public static final String CUSTCNVINFO_CATEGORY_FROM_CLAUSE = " FROM CUSTOMER_TYPES ";
		 public static final String CUSTCNVINFO_CATEGORY_ORDERBY_CLAUSE = " order by customer_type_id ";
		 
		 public static final String POS_CNV_CUSTINFO_ENTITY = "Pos_CnvCustInfo";
		 public static final String POS_CNV_CUSTINFO_FRIENDLY_NAME = "POS_CNV-089 Cust Info";
			
		 public static final String CUSTCNVINFO_SELECT_CLAUSE = "select rownum r ";
		 public static final String CUSTCNVINFO_FROM_CLAUSE = "from (select  customers.CUSTOMER_ID,customers.SHC_CUSTOMER_IDENTIFIER,customers.IS_PERSON,customers.FIRSTNAME,customers.MIDDLENAME,customers.LASTNAME, "+
"customers.COMPANYNAME,CUSTOMER_TYPES.name'CATEGORY NAME', " +
"CUSTOMER_TYPES.customer_type_id'CATEGORY ID',customers.COMMENTS,customers.EMAIL, "+
"customers.PHONE,subsidiaries.name'SUBSIDIARY NAME',subsidiaries.SUBSIDIARY_ID'SUBSIDIARY ID', "+
"customers.URL,ad1.ADDRESS_LINE_1'BILL_ADDRESS_LINE_1',ad1.ADDRESS_LINE_2'BILL_ADDRESS_LINE_2',ad1.IS_DEFAULT_BILL_ADDRESS, "+
"ad1.CITY'BILL_CITY',ad1.STATE'BILL_STATE',ad1.ZIP'BILL_ZIP',ad2.ADDRESS_LINE_1'SHIP_ADDRESS_LINE_1',ad2.ADDRESS_LINE_2'SHIP_ADDRESS_LINE_2', "+
"ad2.IS_DEFAULT_SHIP_ADDRESS,ad2.CITY'SHIP_CITY',ad2.STATE'SHIP_STATE',ad2.ZIP'SHIP_ZIP' "+
"from customers "+
"LEFT OUTER JOIN address_book ad1 "+
"on ad1.ENTITY_ID = customers.CUSTOMER_ID "+
"LEFT OUTER JOIN  address_book ad2 "+
"on ad1.entity_id = ad2.entity_id "+
"LEFT OUTER JOIN subsidiaries "+
"on customers.SUBSIDIARY_ID = subsidiaries.SUBSIDIARY_ID "+
"LEFT OUTER JOIN CUSTOMER_TYPES " +
"on customers.CUSTOMER_TYPE_ID = CUSTOMER_TYPES.customer_type_id " +
"where (ad1.is_default_bill_address='Yes' or ad1.is_default_bill_address is NULL) and (ad2.is_default_ship_address='Yes' or ad2.is_default_ship_address is NULL)) temp";
		
		 
		 

}		