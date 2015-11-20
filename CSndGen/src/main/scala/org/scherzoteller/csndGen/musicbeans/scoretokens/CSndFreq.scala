package org.scherzoteller.csndGen.musicbeans.scoretokens

import scala.beans.BeanProperty
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndWritable
import org.scherzoteller.csndGen.generators.out.CSndOutput


/**
 * Boooouuuuhhh I'm java contaminated I cannot leave without beans...!
 * @see http://stackoverflow.com/questions/9673233/how-is-the-pojo-javabean-pattern-treated-in-scala
 *
 * @see http://stackoverflow.com/questions/13635554/how-to-handle-null-input-parameters-in-scala: I know in Scala a method should never return null... but what's about input parameters?
 */
abstract class CSndFreq(@BeanProperty tableId: Int, @BeanProperty actionTime: Int, @BeanProperty tableSize: Int, @BeanProperty genRoutine: Int, @BeanProperty otherParams: Array[String]) extends CSndScoreToken with CSndWritable{
  /*
  def pitch: BigDecimal
  def pitch_=(pitch:BigDecimal)
  def duration: BigDecimal
  def duration_=(duration:BigDecimal)
  */

  def getValueAsString(): String = {
    // another java contamination, I was told to do mutable string buffers so often that I can't do sth else...
    val sb = new StringBuilder();
    sb.append("f ").append(tableId)
      .append(" ").append(actionTime)
      .append(" ").append(tableSize)
      .append(" ").append(genRoutine)
      ;
    for (anExtraPara <- otherParams) {
      sb.append(" ").append(anExtraPara)
    }
    sb.toString
  }
  
  def writeTo(out: CSndOutput) = {
    out.writeLn(this)
  }
  
}

/**
 * ex of gen 10:
 * f1 0 16384 10 1                                          ; Sine
 * f2 0 16384 10 1 0.5 0.3 0.25 0.2 0.167 0.14 0.125 .111   ; nearly Sawtooth
 * f3 0 16384 10 1 0   0.3 0    0.2 0     0.14 0     .111   ; nearly Square
 * f4 0 16384 10 1 1   1   1    0.7 0.5   0.3  0.1          ; nearly Pulse
 * 
 */
class CSndFreqAdditiveGen10(@BeanProperty tableId: Int, @BeanProperty actionTime: Int, @BeanProperty tableSize: Int, @BeanProperty partialStrength: Array[String]) extends CSndFreq(tableId, actionTime, tableSize, 10, partialStrength) {

}

/**
 * TODO better abstraction of the constructor params
 * segmentDef should have the format  a   n1   b   n2   c  ...
 * a, b, c, etc. -- ordinate values, in odd-numbered pfields p5, p7, p9, . . .
 * n1, n2, etc. -- length of segment (no. of storage locations), in even-numbered pfields. Cannot be negative,
 *  but a zero is meaningful for specifying discontinuous waveforms (e.g. in the example below).
 *  The sum n1 + n2 + .... will normally equal size for fully specified functions. If the sum is smaller, the
 *  function locations not included will be set to zero; if the sum is greater, only the first size locations
 *  will be stored.
 * 
 * 
 * ex: f 2 0 1024 7 0 512 1 0 -1 512 0		;sawtooth up and down
 * 
 * 1st segment goes from y=0 to y=1 and is 512 width (oblic rising segment)
 * 2nd segment goes from y=1 to y=-1 and is 0 width (vertical falling segment)
 * 3rd segment foes from y=-1 to y=0 and is 512 width (oblic rising segment with same angle as 1st)
 */
class CSndFreqStraightSegmentsGen7(@BeanProperty tableId: Int, @BeanProperty actionTime: Int, @BeanProperty tableSize: Int, @BeanProperty segmentDef: Array[String]) extends CSndFreq(tableId, actionTime, tableSize, 7, segmentDef) {

}