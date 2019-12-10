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
 * This helper sets a target coffee object's <code>
 * formerName</code> attribute.
 *
 * @author Steven J. Metsker
 *
 * @version 1.0 
 */
public class FormerNameHelper extends Helper {
/**
 * Sets a target coffee object's <code>formerName</code> 
 * attribute to the given string. The target coffee is
 * the last coffee in a List of coffees.
 */
public void characters(String s, Object target) {
	List<Coffee> coffees = (List) target;
	Coffee c = coffees.get(coffees.size() - 1);
	c.setFormerName(s);
}
}
