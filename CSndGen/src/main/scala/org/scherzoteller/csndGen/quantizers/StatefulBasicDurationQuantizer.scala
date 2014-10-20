package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty

class StatefulBasicDurationQuantizer(@BeanProperty durationQuantum: BigDecimal, @BeanProperty maxDurationInQuantum: Int, @BeanProperty isZeroAllowed: Boolean, @BeanProperty totalDuration: Int)
  extends BasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {
  @BeanProperty
  protected val quantumFill: Array[Int] = StatefulBasicDurationQuantizer.initQuantumFill(totalDuration);;
  
  override def getValidDurationTuple(start: Int): (Int, BigDecimal) = {
    val duration = super.getValidDurationTuple(start);
    //val startInQuantum = (start/durationQuantum).intValue;
    for(i <- start until start+duration._1){
      quantumFill(i) = quantumFill(i) + 1
    }
    duration;
  }
  
}

object StatefulBasicDurationQuantizer{
  def initQuantumFill(totalDuration: Int): Array[Int] = {
    val quantumFill: Array[Int] = new Array(totalDuration);
    for(i <- 0 until totalDuration){
      quantumFill(i) = 0
    }
    quantumFill
  }
}