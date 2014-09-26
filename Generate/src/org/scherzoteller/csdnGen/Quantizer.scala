package org.scherzoteller.csdnGen

trait Quantizer {
	def quantize(input: BigDecimal): BigDecimal;
}