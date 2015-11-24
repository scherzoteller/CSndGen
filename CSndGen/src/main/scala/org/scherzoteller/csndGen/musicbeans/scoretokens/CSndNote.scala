package org.scherzoteller.csndGen.musicbeans.scoretokens

import scala.beans.BeanProperty
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndWritable
import org.scherzoteller.csndGen.generators.out.CSndOutput

/**
 * Boooouuuuhhh I'm java contaminated I cannot leave without beans...!
 * @see http://stackoverflow.com/questions/9673233/how-is-the-pojo-javabean-pattern-treated-in-scala
 *
 * @see http://stackoverflow.com/questions/13635554/how-to-handle-null-input-parameters-in-scala: I know in Scala a method should never return null... but what's about input parameters?
 */
class CSndNote(@BeanProperty instrId: Int, @BeanProperty start: String, @BeanProperty duration: BigDecimal, @BeanProperty otherParams: Array[String]) extends CSndNotePlacement(start.toInt, duration) with CSndWritable with CSndScoreToken {
  /*
  def pitch: BigDecimal
  def pitch_=(pitch:BigDecimal)
  def duration: BigDecimal
  def duration_=(duration:BigDecimal)
  */

  def this(instrId: Int, start: String, duration: BigDecimal, pitch: BigDecimal, amplitude: BigDecimal) =
    this(instrId, start, duration, Array(CSndToken.getValueAsString(pitch), CSndToken.getValueAsString(amplitude)))

  def getValueAsString(): String = {
    // another java contamination, I was told to do mutable string buffers so often that I can't do sth else...
    val sb = new StringBuilder();
    sb.append("i ").append(instrId)
      .append(" ").append(CSndToken.getValueAsString(start))
      .append(" ").append(CSndToken.getValueAsString(duration));
    for (anExtraPara <- otherParams) {
      sb.append(" ").append(anExtraPara)
    }
    sb.toString
  }

  override def toString(): String = {
    "CSndNote[" + getValueAsString() + "]"
  }

  def writeTo(out: CSndOutput): Unit = {
    out.writeLn(this)
  }
}