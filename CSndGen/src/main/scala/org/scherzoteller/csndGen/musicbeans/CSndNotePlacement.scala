package org.scherzoteller.csndGen.musicbeans

import scala.beans.BeanProperty

/**
 * Boooouuuuhhh I'm java contaminated I cannot leave without beans...!
 * @see http://stackoverflow.com/questions/9673233/how-is-the-pojo-javabean-pattern-treated-in-scala
 * 
 * @see http://stackoverflow.com/questions/13635554/how-to-handle-null-input-parameters-in-scala: I know in Scala a method should never return null... but what's about input parameters? 
 */
class CSndNotePlacement(@BeanProperty start: String, @BeanProperty duration: BigDecimal) { 

}