package org.scherzoteller.csndGen.quantizers

import scala.util.Random

/**
 * This quantizer is a basic one based on a quantum notion
 * It can generates any multiple of this quantum between 
 * a min multiple (0 or 1) and max multiple.  
 */
trait DurationQuantizer extends Quantizer{
  
  def getDurationQuantum(): BigDecimal;
  
  def getMaxDurationInQuantum(): Int;
  def isZeroAllowed(): Boolean;
  def getAllowedVals(): Array[BigDecimal] = {
    //val r = (getMinDurationInQuantum() until getMaxDurationInQuantum());
    
//    for(i <- getMinDurationInQuantum() to getMaxDurationInQuantum()){
//      
//    }
    
    throw new UnsupportedOperationException("getAllowedVals()");
  }
  
  final def getMinDurationInQuantum(): Int = {
    if(isZeroAllowed()) 0 else 1
  }

  def quantize(input: BigDecimal): BigDecimal = {
    //getDurationQuantum() * genRandomIntInBound(getMinDurationInQuantum(), getMaxDurationInQuantum())
    if(input < getMinDurationInQuantum() || input > getMaxDurationInQuantum()){
      null
    }else{
      // FIXME always lower int... maybe closest would be better
      (input/getDurationQuantum()).toInt * getDurationQuantum()
    }
  }
  
  def getValidDuration(start: BigDecimal, totalDuration: BigDecimal){
    val duration = getRandowValue();
    if(start + duration <= totalDuration) duration else totalDuration - start
  }

  def getUnQuantizedInBoundRandowValue(): BigDecimal = {
    
    genRandomBigDecimalInBound(getAllowedVals()(0), getAllowedVals()(getAllowedVals().length - 1))
  }
  
  

  def getRandowValue(): BigDecimal = {
    getDurationQuantum() * genRandomIntInBound(getMinDurationInQuantum(), getMaxDurationInQuantum())
  }

}