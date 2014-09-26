package org.scherzoteller.csdnGen

trait Seeder {
	def getUnquantizedValue(): BigDecimal;
	def getQuantizedValue(quantizer: Quantizer): BigDecimal = {
	  quantizer.quantize(getUnquantizedValue());
	}
}