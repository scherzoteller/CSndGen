package org.scherzoteller.csndGen.quantizers

import scala.beans.BeanProperty
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement

class StatefulBasicDurationQuantizer(durationQuantum: BigDecimal, maxDurationInQuantum: Int, isZeroAllowed: Boolean, totalDuration: Int)
  extends BasicDurationQuantizer(durationQuantum, maxDurationInQuantum, isZeroAllowed, totalDuration) {
  @BeanProperty
  protected val quantumFill: Array[Int] = StatefulBasicDurationQuantizer.initQuantumFill(totalDuration);;
  
  override def getValidDurationTuple(start: Int): CSndNotePlacement = {
    val duration = super.getValidDurationTuple(start);
    //val startInQuantum = (start/durationQuantum).intValue;
    for(i <- start until start+duration.getStart){
      quantumFill(i) = quantumFill(i) + 1
    }
    duration;
  }
  
  
  def getValidDurationTuple(start: Int, doFill: Boolean): CSndNotePlacement = {
	  val duration = super.getValidDurationTuple(start);
	  //val startInQuantum = (start/durationQuantum).intValue;
	  if(doFill) {
		  fill(start, duration);
	  }
	  duration;
  }
  
  protected def fill(start: Int, duration: CSndNotePlacement): Unit = {
    for(i <- start until start+duration.getStart){
			  quantumFill(i) = quantumFill(i) + 1;
    }
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