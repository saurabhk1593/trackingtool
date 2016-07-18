package com.techm.trackingtool.util;

import java.util.Collection;
import java.util.Iterator;


	public class ResetableIterator implements ExtendedIterator {
	   // factory method
	   public static ExtendedIterator getIterator(Collection c) {
	      return (ExtendedIterator) new ResetableIterator(c).i;
	   }
	   private Iterator i;
	   private Collection c;
	   public ResetableIterator(Collection c) {
	      this.c= c;
	      this.i= c.iterator();
	   }
	   // Iterator implementation (delegation)
	   public boolean hasNext() { return i.hasNext(); }
	   public Object next() { return i.next(); }
	   public void remove() { 
		   
		   }
	   // extension implementation
	   public void reset() { i= c.iterator(); }
	}


