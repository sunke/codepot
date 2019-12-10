package com.sjm.examples.logic;

import java.util.*;
import com.sjm.engine.*;
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
 * Pops the terms and functor of a structure from an assembly's
 * stack, builds a structure, and pushes it.
 * 
 * @author Steven J. Metsker
 *
 * @version 1.0
 */
public class StructureWithTermsAssembler extends Assembler {
/**
 * Reverse a List into an array of terms.
 * 
 * @param   v   the List to reverse
 *
 * @return   Term[]   the List, reversed
 */
public static Term[] ListReversedIntoTerms(List v) {
	int size = v.size();
	Term[] terms = new Term[size];
	for (int i = 0; i < size; i++) {
		terms[size - 1 - i] = (Term) v.get(i);
	}
	return terms;
}
/**
 * Pops the terms and functor of a structure from an assembly's
 * stack, builds a structure, and pushes it.
 * <p>
 * This method expects a series of terms to lie on top of a 
 * stack, with an open paren token lying underneath. If there 
 * is no '(' marker, this class will throw an <code>
 * EmptyStackException</code>.
 * <p>
 * Beneath the terms of the structure, this method expects to 
 * find a token whose value is the functor of the structure.
 *
 * @param  a  the assembly to work on
 */
public void workOn(Assembly a) {
	List termList = elementsAbove(a, new Token('('));
	Term[] termArray = ListReversedIntoTerms(termList);
	Token t = (Token) a.pop();
	a.push(new Structure(t.value(), termArray));
}
}
