package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty

class LimitedPolyphonyDurationQuantizer(@BeanProperty durationQuantum: BigDecimal, @BeanProperty maxDurationInQuantum: Int, 
    @BeanProperty isZeroAllowed: Boolean, @BeanProperty totalDuration: Int, @BeanProperty maxPolyphony: Int)
  extends StatefulBasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {
  
  // TODO (nothing to do with this class ;-)) http://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/
  
  override def getValidDurationTuple(start: Int): (Int, BigDecimal) = {
	// TODO return the max possible duration at this start... not really useful but who knows
    super.getValidDurationTuple(start)
  }
  
  
  // TODO def getValidNotePlacement(totalNbNotes: Int, nbRemainingNotes: Int) => introduce an object CSndNotePlacement with just start and duration
  // possible strategies
  // - generate random duration do a 1st search of free space for this duration, and fallback to the biggest free space if not found (might be too greedy, maybe durations should be chosen to maintain an average around nbQuantum/nbNotes)
  
  
  // => search of free space should be circular starting from a random position 
  
  
  
  
  // Type of sound to generate (should be studied in parallel to CSndGen... and listen. ******** RANDOM RESULTS SHOULD NOT BE LISTENED, FORBIDDEN **********
  // - melodic phrases with living articulations (how to manage glides and legatos in CSound?)
  // - random wavtables (segment based, harmonic based, ... who knows)
  // - pads/soundscapes
  // - evolving sounds
  // - percussive rythmic sounds

  
  // 
  
}

