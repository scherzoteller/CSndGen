package org.scherzoteller.csndGen.quantizers

import scala.math.BigDecimal

class ChromaticQuantizer extends NoteQuantizer {

  def getAllowedVals(): Array[BigDecimal] = CHROMATIC_NOTES;
}