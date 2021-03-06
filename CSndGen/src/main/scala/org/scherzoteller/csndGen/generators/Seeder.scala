package org.scherzoteller.csndGen.generators

import org.scherzoteller.csndGen.quantizers.Quantizer


trait Seeder {
	def getUnquantizedValue(): BigDecimal;
	def getQuantizedValue(quantizer: Quantizer): BigDecimal = {
	  quantizer.quantize(getUnquantizedValue());
	}
}