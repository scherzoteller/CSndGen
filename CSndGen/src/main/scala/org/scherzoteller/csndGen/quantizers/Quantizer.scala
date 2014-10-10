package org.scherzoteller.csndGen.quantizers

import org.slf4j.LoggerFactory
import scala.util.Random

trait Quantizer {
  val LOG = LoggerFactory.getLogger(classOf[Quantizer])


  def quantize(input: BigDecimal): BigDecimal

  val genRandomBigDecimalInBound = (start: BigDecimal, end: BigDecimal) => start + (Random.nextDouble * (end - start));
  val genRandomIntInBound = (start: Int, end: Int) => start + Random.nextInt(end - start);

  def getUnQuantizedInBoundRandowValue(): BigDecimal

  def getRandowValue(): BigDecimal;

}