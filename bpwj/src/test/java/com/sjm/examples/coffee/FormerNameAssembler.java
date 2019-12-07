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
 * This assembler pops a string, and sets the target coffee's
 * former name to this string.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class FormerNameAssembler extends Assembler {
/**
 * Pop a string, and set the target coffee's former name to
 * this string.
 *
 * @param   Assembly   the assembly to work on
 */
public void workOn(Assembly a) {
	Token t = (Token) a.pop();
	Coffee c = (Coffee) a.getTarget();
	c.setFormerName(t.sval().trim());
}
}
