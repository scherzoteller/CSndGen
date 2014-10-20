package org.scherzoteller.csndGen.quantizers

import scala.util.Random
import scala.beans.BeanProperty
import scala.math.BigDecimal

/**
 * This quantizer is a basic one based on a quantum notion
 * It can generates any multiple of this quantum between 
 * a min multiple (0 or 1) and max multiple.
 * 
 * It should be quite suitable for basic rhythms like EDM but
 * TODO a more musical duration quantizer would be a good thing to allow rhythms mixing binary and ternary for example
 * Note that polyrhythms can be be achieved with either several quantizers or getUnQuantizedInBoundRandowValue()
 * Musical quantizer would only help to provide a "valid" measure count (bouhhh traditional music is obsolete..., let's do polyrhytms, dodecaphonic pieces without fixed metrics...)
 */
class BasicDurationQuantizer(@BeanProperty durationQuantum: BigDecimal, @BeanProperty maxDurationInQuantum: Int, @BeanProperty isZeroAllowed: Boolean, @BeanProperty totalDuration: Int) extends Quantizer{
//  
  
  def getAllowedVals(): Array[BigDecimal] = {
    // TODO no reason we couldn't do it but not needed for the moment: how can we do a foreach on a range resulting on a list containing all the range elements passed through a transformer...
    //    val r = (getMinDurationInQuantum() until getMaxDurationInQuantum());
    
	//    for(i <- getMinDurationInQuantum() to getMaxDurationInQuantum()){
	//      
	//    }
    
    throw new UnsupportedOperationException("getAllowedVals()");
  }
  
  final def getMinDurationInQuantum(): Int = {
    if(isZeroAllowed) 0 else 1
  }

  def quantize(input: BigDecimal): BigDecimal = {
    if(input < getMinDurationInQuantum() || input > maxDurationInQuantum){
      null
    }else{
      // FIXME always lower int... maybe closest would be better
      (input/durationQuantum).toInt * durationQuantum
    }
  }
  def getValidDurationTuple(start: Int): (Int, BigDecimal) = {
    val durationTuple = getRandowValueTuple()
    //val duration = getRandowValue()
    if(start*durationQuantum + durationTuple._2 <= (totalDuration*durationQuantum)) durationTuple else {
      val duration = (totalDuration*durationQuantum) - start*durationQuantum
      ( (duration / durationQuantum).intValue, duration)
    }
  }
  def getValidDuration(start: Int): BigDecimal = {
    getValidDurationTuple(start)._2
  }

  def getUnQuantizedInBoundRandowValue(): BigDecimal = {
    Quantizer.genRandomBigDecimalInBound(getAllowedVals()(0), getAllowedVals()(getAllowedVals().length - 1))
  }
  
  def getQuantizedStart(): Int = {
    Quantizer.genRandomIntInBound(0,totalDuration-1)
  }
  
  

  def getRandowValue(): BigDecimal = {
    getRandowValueTuple()._2;
  }

  def getRandowValueTuple(): (Int, BigDecimal) = {
    val nbQuantum = Quantizer.genRandomIntInBound(getMinDurationInQuantum(), maxDurationInQuantum);
    (nbQuantum, durationQuantum * nbQuantum)
  }
  
}