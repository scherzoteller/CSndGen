package org.scherzoteller.csdnGen

import scala.math.BigDecimal

class ChromaticQuantizer extends Quantizer {

  def quantize(input: BigDecimal): BigDecimal = {
    def dichoSearch(toSearch: BigDecimal, array: Array[BigDecimal], binf: Int, bsup: Int): BigDecimal = {
      def closest(toSearch: BigDecimal, x: BigDecimal, y: BigDecimal): BigDecimal = {
        if (x == null) {
          y
        } else if (x - toSearch > y - toSearch) {
          y
        } else {
          x
        }
      }

      if (toSearch < binf || toSearch > bsup) {
        null
      } else if (binf == bsup)
        binf
      else if (bsup == binf + 1) {
        closest(toSearch, array(bsup), array(binf))
      } else {
        val middle = binf + ((bsup-binf)/2);
        closest(toSearch, dichoSearch(toSearch,array, binf, middle), dichoSearch(toSearch, array, middle, binf))
      }
    }

    return input;
  }
}