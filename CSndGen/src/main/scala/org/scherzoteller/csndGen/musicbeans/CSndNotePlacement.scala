package org.scherzoteller.csndGen.musicbeans

import scala.beans.BeanProperty

/**
 * Boooouuuuhhh I'm java contaminated I cannot leave without beans...!
 * @see http://stackoverflow.com/questions/9673233/how-is-the-pojo-javabean-pattern-treated-in-scala
 * 
 * @see http://stackoverflow.com/questions/13635554/how-to-handle-null-input-parameters-in-scala: I know in Scala a method should never return null... but what's about input parameters?
 * 
 * FIXME those @BeanProperty annotations cause warning and eclipse doesn't see them !!!!
 *  
 */
class CSndNotePlacement(@BeanProperty start: Int, @BeanProperty duration: BigDecimal) { 
  def getDuration: BigDecimal = this.duration // FIXME to remove when found a workaround for eclipse
  def getStart: Int = this.start // FIXME to remove when found a workaround for eclipse
}

object CSndNotePlacement{
  val nilObjectInst = new CSndNotePlacement(0, BigDecimal(0))
  def nilObject(): CSndNotePlacement = { nilObjectInst };
}