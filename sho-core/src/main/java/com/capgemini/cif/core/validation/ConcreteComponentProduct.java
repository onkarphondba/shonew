package com.capgemini.cif.core.validation;

import java.util.List;

/**
 * @author snkesarw
 *
 */
public interface ConcreteComponentProduct {
	
	/**
	 * This method returns the child objects associated to main entity.
	 * @return  List<ConcreteComponentProduct>
	 */
	List<ConcreteComponentProduct> getAllChildren();

	/**
	 * This method returns the interface name of each validated Entity.
	 * @return
	 */
	String getFriendlyName();

	/**
	 * This method returns the Internal ID /Unique Id of an Entity.
	 * @return
	 */
	Long getInternalId();
}
