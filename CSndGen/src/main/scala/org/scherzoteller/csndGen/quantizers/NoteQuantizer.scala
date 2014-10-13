package org.scherzoteller.csndGen.quantizers

import scala.util.Random

trait NoteQuantizer extends Quantizer{
  def dichoSearch(toSearch: BigDecimal, array: Array[BigDecimal]): BigDecimal = {
    val binf = 0;
    val bsup = array.length - 1;

    val closest = (toSearch: BigDecimal, x: BigDecimal, y: BigDecimal) =>
      if (x == null || (y != null && ((x - toSearch).abs > (y - toSearch).abs))) y else x;

    if (LOG.isDebugEnabled()) {
      LOG.debug("dichoSearch(" + toSearch + ", " + binf + ", " + bsup + ")")
      LOG.debug("[" + array(binf) + ", " + array(bsup) + "]")
    }

    if (array == null || toSearch == null) {
      null
    } else if (toSearch < array(binf) || toSearch > array(bsup)) {
      null
    } else if (binf == bsup) {
      array(binf)
    } else if (bsup == binf + 1) {
      closest(toSearch, array(bsup), array(binf))
    } else {
      val middle = binf + ((bsup - binf) / 2);
      val sub1 = array.slice(binf, middle + 1);
      val sub2 = array.slice(middle, bsup + 1);
      if (LOG.isDebugEnabled()) {
        LOG.debug("slice: " + "[" + array(binf) + ", " + array(bsup) + "] " + " => " + "[" + sub1(binf) + ", " + sub1(sub1.length-1) + "]" + " & " + "[" + sub2(binf) + ", " + sub2(sub2.length-1) + "]")
        LOG.debug("lengths: ("+array.length+") => ("+sub1.length+" & "+sub2.length+")");
      }
      closest(toSearch, dichoSearch(toSearch, sub1), dichoSearch(toSearch, sub2))
    }
  }
  def getAllowedVals(): Array[BigDecimal];

  def quantize(input: BigDecimal): BigDecimal = {
    return dichoSearch(input, getAllowedVals());
  }

  def getUnQuantizedInBoundRandowValue(): BigDecimal = {
    Quantizer.genRandomBigDecimalInBound(getAllowedVals()(0), getAllowedVals()(getAllowedVals().length - 1))
  }

  def getRandowValue(): BigDecimal = {
    getAllowedVals()(Quantizer.genRandomIntInBound(0, getAllowedVals().length - 1))
  }

}