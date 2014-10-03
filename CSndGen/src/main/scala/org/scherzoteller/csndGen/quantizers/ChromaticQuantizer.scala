package org.scherzoteller.csndGen.quantizers

import scala.math.BigDecimal

class ChromaticQuantizer extends Quantizer {

  def getAllowedVals(): Array[BigDecimal] = CHROMATIC_NOTES;
}