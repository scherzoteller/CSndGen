package org.scherzoteller.csndGen.musicbeans.scoretokens

import scala.beans.BeanProperty

import org.scherzoteller.csndGen.generators.out.CSndOutput
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndWritable

/**
 * Boooouuuuhhh I'm java contaminated I cannot leave without beans...!
 * @see http://stackoverflow.com/questions/9673233/how-is-the-pojo-javabean-pattern-treated-in-scala
 * 
 * @see http://stackoverflow.com/questions/13635554/how-to-handle-null-input-parameters-in-scala: I know in Scala a method should never return null... but what's about input parameters? 
 */
class CSndPolyphony(@BeanProperty start: String, @BeanProperty duration: BigDecimal, @BeanProperty nbNotes: Int) extends CSndNotePlacement(start.toInt, duration) {
  
}