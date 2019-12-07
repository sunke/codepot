package com.sjm.examples.coffee;

import java.util.*;
/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 * 
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */

/**
 * This helper sets a target coffee object's <code>price
 * </code> attribute.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class PriceHelper extends Helper {
/**
 * Sets a target coffee object's <code>price</code> 
 * attribute to the double value of the given string.
 * The target coffee is the last coffee in a Vector of 
 * coffees.
 */
public void characters(String s, Object target) {
	Coffee c = (Coffee) ((Vector) target).lastElement();
	c.setPrice(Double.valueOf(s).doubleValue());
}
}
