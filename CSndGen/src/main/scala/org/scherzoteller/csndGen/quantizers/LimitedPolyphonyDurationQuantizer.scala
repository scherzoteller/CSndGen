package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement
import org.slf4j.LoggerFactory
import org.scherzoteller.csndGen.generators.states.DodecaphonicGeneratorState

object LimitedPolyphonyDurationQuantizer {
  val LOG = LoggerFactory.getLogger(classOf[LimitedPolyphonyDurationQuantizer])
}

class LimitedPolyphonyDurationQuantizer(durationQuantum: BigDecimal, maxDurationInQuantum: Int,
                                        isZeroAllowed: Boolean, totalDuration: Int, maxPolyphony: Int)
  extends StatefulBasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {

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
        if(aDurationTuple.getDuration == 0)
          aDurationTuple
        else if (newFill <= maxPolyphony) {
          quantumFill(start) = newFill;
          incrementeTuple(returnValidTuple(start + 1, decrementTuple(aDurationTuple)))
        } else {
          new CSndNotePlacement(0, BigDecimal(0))
        }
      }
    }
    val tuple = super.getValidDurationTuple(start, false)
    returnValidTuple(start, tuple);
  }
}

