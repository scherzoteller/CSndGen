package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty

class StatefulBasicDurationQuantizer(@BeanProperty durationQuantum: BigDecimal, @BeanProperty maxDurationInQuantum: Int, @BeanProperty isZeroAllowed: Boolean, @BeanProperty totalDuration: Int)
  extends BasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {
  /*
   * TODO move totalDuration as a main field in BasicDurationQuantizer
   */
  @BeanProperty
  val quantumFill: Array[Int] = StatefulBasicDurationQuantizer.initQuantumFill(totalDuration);;
  
  override def getValidDurationTuple(start: BigDecimal): (Int, BigDecimal) = {
    val duration = super.getValidDurationTuple(start);
    val startInQuantum = (start/durationQuantum).intValue;
    System.err.println("Add note at "+start+" duration "+duration._1+" quanta="+duration._2);
    for(i <- startInQuantum until startInQuantum+duration._1){
      quantumFill(i) = quantumFill(i) + 1
	  System.err.println("	++ at"+i);
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