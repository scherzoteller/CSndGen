package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement
import org.slf4j.LoggerFactory
import org.scherzoteller.csndGen.states.DodecaphonicGeneratorState

object LimitedPolyphonyDurationQuantizer {
  val LOG = LoggerFactory.getLogger(classOf[LimitedPolyphonyDurationQuantizer])
}

class LimitedPolyphonyDurationQuantizer(@BeanProperty durationQuantum: BigDecimal, @BeanProperty maxDurationInQuantum: Int,
                                        @BeanProperty isZeroAllowed: Boolean, @BeanProperty totalDuration: Int, @BeanProperty maxPolyphony: Int)
  extends StatefulBasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {

  // TODO ((nothing to do with this class ;-)) http://bcomposes.wordpress.com/2012/05/04/basic-xml-processing-with-scala/

  private def decrementTuple(aDurationTuple: CSndNotePlacement): CSndNotePlacement = {
    new CSndNotePlacement(aDurationTuple.getStart - 1, aDurationTuple.getDuration - durationQuantum)
  }
  private def incrementeTuple(aDurationTuple: CSndNotePlacement): CSndNotePlacement = {
    new CSndNotePlacement(aDurationTuple.getStart + 1, aDurationTuple.getDuration + durationQuantum)
  }
  override def getValidDurationTuple(start: Int): CSndNotePlacement = {
    def returnValidTuple(start: Int, aDurationTuple: CSndNotePlacement): CSndNotePlacement = {
      if (start >= totalDuration) new CSndNotePlacement(0, BigDecimal(0))
      else {
        val newFill = quantumFill(start) + 1;
        if (newFill <= maxPolyphony) {
          quantumFill(start) = newFill;
          incrementeTuple(returnValidTuple(start + 1, decrementTuple(aDurationTuple)))
        } else {
          new CSndNotePlacement(0, BigDecimal(0))
        }
      }
    }
    returnValidTuple(start, super.getValidDurationTuple(start, false));
  }

  trait CircularSearchFunction[T] extends Function2[T, Int, (T, Boolean)] {
    def apply(searchObj: T, position: Int): (T, Boolean)

  }

  private def circularSearchRec[T](startIndex: Int, index: Int, nbRemainingCycles: Int, data: T, evalFunc: CircularSearchFunction[T]): Int = {
    val onStartIndex = (startIndex == index)
    val newNbRemainingCycles = if (startIndex == index) nbRemainingCycles - 1 else nbRemainingCycles
    if (!(onStartIndex && (newNbRemainingCycles == 0))) {
      val newSearchTuple = evalFunc(data, index)
      if (newSearchTuple._2) {
        index
      } else {
        circularSearch((startIndex + 1) % totalDuration, newNbRemainingCycles, newSearchTuple._1, evalFunc)
      }
    } else {
      -1
    }
  }
  private def circularSearch[T](startIndex: Int, nbCycles: Int, data: T, evalFunc: CircularSearchFunction[T]): Int = circularSearchRec(startIndex, startIndex, nbCycles + 1, data, evalFunc)

  def getValidNotePlacement(totalNbNotes: Int, nbRemainingNotes: Int): CSndNotePlacement = {
    val randomPosition = Quantizer.genRandomIntInBound(0, totalDuration);
    if (LimitedPolyphonyDurationQuantizer.LOG.isDebugEnabled() && totalNbNotes > maxPolyphony * (maxDurationInQuantum - getMinDurationInQuantum())) {
      LimitedPolyphonyDurationQuantizer.LOG.debug("Partition space is not big enough for fill with maxPolyphony = " + maxPolyphony);
    }
    // TODO
    // possible strategies
    // - generate random duration do a 1st search of free space for this duration, and fallback to the biggest free space if not found (might be too greedy, maybe durations should be chosen to maintain an average around nbQuantum/nbNotes)
    // => search of free space should be circular starting from a random position 
    val randomDurationStart = getQuantizedStart()
    val randomNbQuantum = getRandowValueTuple().getStart

    // Note this is early design of the circular search for 

    // NB passing positionWhereFound as input is not logical, we don't have it... just to allow generic signature with same input/output
    // positionWhereFound could be used to pass index but it would be quite confusing...
    val searchExact = new CircularSearchFunction[CircularSearchResult] {
      def apply(circularSearchResult: CircularSearchResult, position: Int): (CircularSearchResult, Boolean) = {
        (circularSearchResult, true)
      }
    }
    val nbCycles = 1;

    val exactResult = circularSearch(randomDurationStart, 1, new CircularSearchResult(0, randomNbQuantum, -1), searchExact)
    /* 
 * Used a workaround to use generics : 
http://stackoverflow.com/questions/2554531/how-can-i-define-an-anonymous-generic-scala-function
http://stackoverflow.com/questions/2529184/difference-between-method-and-function-in-scala/2530007#2530007
http://stackoverflow.com/questions/7399044/scala-upper-type-bounds-and-parent-classes
*/
    new CSndNotePlacement(0, BigDecimal(0))

  }

  // Type of sound to generate (should be studied in parallel to CSndGen... and listen. ******** RANDOM RESULTS SHOULD NOT BE LISTENED, FORBIDDEN **********)
  // - melodic phrases with living articulations (how to manage glides and legatos in CSound?)
  // - random wavtables (segment based, harmonic based, ... who knows)
  // - pads/soundscapes
  // - evolving sounds
  // - percussive rythmic sounds
  
  // DodecaphonicGeneratorState should be possible to rethink in immutable!!!

  def getValidNotePlacement(state: DodecaphonicGeneratorState, totalNbNotes: Int, nbRemainingNotes: Int): CSndNotePlacement = {
    new CSndNotePlacement(0, BigDecimal(0))
  }
  
}

