package org.scherzoteller.csdnGen

import java.io.OutputStream

trait Generator {
	def genOrchestra(out: OutputStream) = {
	  
	}
	def genScore(out: OutputStream) = {
	  
	}
	
	def stopGenScore(): Boolean
	
	// To be redefined in implementations
	def genNote(out: OutputStream)
}