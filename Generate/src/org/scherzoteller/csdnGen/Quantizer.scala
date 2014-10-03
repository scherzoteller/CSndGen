package org.scherzoteller.csdnGen

trait Quantizer {
	def quantize(input: BigDecimal): BigDecimal;
	
    def dichoSearch(toSearch: BigDecimal, array: Array[BigDecimal], binf: Int, bsup: Int): BigDecimal = {
      val closest = (toSearch: BigDecimal, x: BigDecimal, y: BigDecimal) 
    		  => if (x == null || (y != null && (x - toSearch > y - toSearch))) y else x; 
      if (toSearch < array(binf) || toSearch > array(bsup)) {
        null
      } else if (binf == bsup) {
        array(binf)
      } else if (bsup == binf + 1) {
        closest(toSearch, array(bsup), array(binf))
      } else {
        val middle = binf + ((bsup - binf) / 2);
        closest(toSearch, dichoSearch(toSearch, array, binf, middle), dichoSearch(toSearch, array, middle, binf))
      }
    }
    def dichoSearch(toSearch: BigDecimal, array: Array[BigDecimal]): BigDecimal = {
      return dichoSearch(toSearch, array, 0, array.length);
    }
}