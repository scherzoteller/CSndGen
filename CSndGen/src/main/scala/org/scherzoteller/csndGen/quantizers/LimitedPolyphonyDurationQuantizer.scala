package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement
import org.slf4j.LoggerFactory

object LimitedPolyphonyDurationQuantizer{
	val LOG = LoggerFactory.getLogger(classOf[LimitedPolyphonyDurationQuantizer])
}

class LimitedPolyphonyDurationQuantizer(@BeanProperty durationQuantum: BigDecimal, @BeanProperty maxDurationInQuantum: Int,
  @BeanProperty isZeroAllowed: Boolean, @BeanProperty totalDuration: Int, @BeanProperty maxPolyphony: Int)
  extends StatefulBasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {

  // TODO (nothing to do with this class ;-)) http://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/

  private def decrementTuple(aDurationTuple: (Int, BigDecimal)): (Int, BigDecimal) = {
    (aDurationTuple._1 - 1, aDurationTuple._2 - durationQuantum)
  }
  private def incrementeTuple(aDurationTuple: (Int, BigDecimal)): (Int, BigDecimal) = {
    (aDurationTuple._1 + 1, aDurationTuple._2 + durationQuantum)
  }
  override def getValidDurationTuple(start: Int): (Int, BigDecimal) = {
	  def returnValidTuple(start: Int, aDurationTuple: (Int, BigDecimal)): (Int, BigDecimal) = {
		  val newFill = quantumFill(start) + 1;
		  if (newFill <= maxPolyphony) {
			  quantumFill(start) = newFill;
			  incrementeTuple(returnValidTuple(start + 1, decrementTuple(aDurationTuple)))
		  } else {
			  (0, BigDecimal(0))
		  }
	  }
    returnValidTuple(start, super.getValidDurationTuple(start, false));
  }

  
  
  
  def getValidNotePlacement(totalNbNotes: Int, nbRemainingNotes: Int): CSndNotePlacement = {
    val randomPosition = Quantizer.genRandomIntInBound(0, totalDuration);
    if(LimitedPolyphonyDurationQuantizer.LOG.isDebugEnabled() && totalNbNotes > maxPolyphony*(maxDurationInQuantum-getMinDurationInQuantum())) {
      LimitedPolyphonyDurationQuantizer.LOG.debug("Partition space is not big enough for fill with maxPolyphony = "+maxPolyphony);
    }
    // TODO
    // possible strategies
    // - generate random duration do a 1st search of free space for this duration, and fallback to the biggest free space if not found (might be too greedy, maybe durations should be chosen to maintain an average around nbQuantum/nbNotes)
    // => search of free space should be circular starting from a random position 
    new CSndNotePlacement("0",BigDecimal(0))
  }
  
  // Type of sound to generate (should be studied in parallel to CSndGen... and listen. ******** RANDOM RESULTS SHOULD NOT BE LISTENED, FORBIDDEN **********
  // - melodic phrases with living articulations (how to manage glides and legatos in CSound?)
  // - random wavtables (segment based, harmonic based, ... who knows)
  // - pads/soundscapes
  // - evolving sounds
  // - percussive rythmic sounds
}

