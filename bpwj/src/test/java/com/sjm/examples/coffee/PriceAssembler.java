package com.sjm.examples.coffee;

import com.sjm.parse.*;
import com.sjm.parse.tokens.*;
/*
 * Copyright (c) 2000 Steven J. Metsker. All Rights Reserved.
 * 
 * Steve Metsker makes no representations or warranties about
 * the fitness of this software for any particular purpose, 
 * including the implied warranty of merchantability.
 */
 
/**
 * Pops a number and sets the target coffee's price to this
 * number.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class PriceAssembler extends Assembler {
/**
 * Pop a number, and set the target coffee's price to this
 * string.
 *
 * @param   Assembly   the assembly to work on
 */
public void workOn(Assembly a) {
	Token t = (Token) a.pop();
	Coffee c = (Coffee) a.getTarget();
	c.setPrice(t.nval());
}
}
