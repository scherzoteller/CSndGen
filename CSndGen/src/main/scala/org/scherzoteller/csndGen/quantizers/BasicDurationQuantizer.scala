package org.scherzoteller.csndGen.quantizers

import scala.util.Random
import scala.beans.BeanProperty
import scala.math.BigDecimal
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement

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
class BasicDurationQuantizer(durationQuantum: BigDecimal, maxDurationInQuantum: Int, isZeroAllowed: Boolean, totalDuration: Int) extends Quantizer {
  //  

  def getAllowedVals(): Array[BigDecimal] = {
    (getMinDurationInQuantum() until maxDurationInQuantum toArray).map((nbQuantum: Int) => { nbQuantum * durationQuantum })
  }

  final def getMinDurationInQuantum(): Int = {
    if (isZeroAllowed) 0 else 1
  }

  def quantize(input: BigDecimal): BigDecimal = {
    if (input < getMinDurationInQuantum() || input > maxDurationInQuantum) {
      null
    } else {
      // FIXME always lower int... maybe closest would be better
      (input / durationQuantum).toInt * durationQuantum
    }
  }
  def getValidDurationTuple(start: Int): CSndNotePlacement = {
    val durationTuple = getRandowValueTuple()
    //val duration = getRandowValue()
    if (start * durationQuantum + durationTuple.getDuration <= (totalDuration * durationQuantum)) durationTuple else {
      val duration = (totalDuration * durationQuantum) - start * durationQuantum
      new CSndNotePlacement((duration / durationQuantum).intValue, duration)
      
    }
  }
  def getValidDuration(start: Int): BigDecimal = {
    getValidDurationTuple(start).getDuration
  }

  def getUnQuantizedInBoundRandowValue(): BigDecimal = {
    Quantizer.genRandomBigDecimalInBound(getAllowedVals()(0), getAllowedVals()(getAllowedVals().length - 1))
  }

  def getQuantizedStart(): Int = {
    getQuantizedStart(0)
  }

  def getQuantizedStart(min: Int): Int = {
    Quantizer.genRandomIntInBound(min, totalDuration - 1)
  }

  def getRandowValue(): BigDecimal = {
    getRandowValueTuple().getDuration;
  }

  def getRandowValueTuple(): CSndNotePlacement = {
    val nbQuantum = Quantizer.genRandomIntInBound(getMinDurationInQuantum(), maxDurationInQuantum);
    new CSndNotePlacement(nbQuantum, durationQuantum * nbQuantum)
  }

}