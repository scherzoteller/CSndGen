package org.scherzoteller.csndgen.quantizers

import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.scherzoteller.csndGen.quantizers.ChromaticQuantizer

class ChromaticQuantizerTest extends AssertionsForJUnit {
  // http://www.scalatest.org/getting_started_with_junit_4_in_scala
  // http://www.scalatest.org/download
  @Before def initialize() {

  }
  @Test def quantizeInBounds() {
    assertEquals("Wrong quantized value", BigDecimal("46.25"), new ChromaticQuantizer().quantize(BigDecimal("45.11")))
  }
  
  @Test def randomInBounds() {
    for( a <- 1 until 1000){
      val validValue = new ChromaticQuantizer().getUnQuantizedInBoundRandowValue() 
	  assertNotNull("Null quantized value", new ChromaticQuantizer().quantize(validValue))
    }
  }
  @Test def randomQuantizedInBounds() {
	  for( a <- 1 until 1000){
		  val validValue = new ChromaticQuantizer().getRandowValue() 
		  assertEquals("Wrong quantized value", validValue, new ChromaticQuantizer().quantize(validValue+BigDecimal("0.01")))
	  }
  }
  @Test def quantizeOutOfBounds() {
    assertNull("Wrong quantized value", new ChromaticQuantizer().quantize(BigDecimal("12345646.25")))
    assertNull("Wrong quantized value", new ChromaticQuantizer().quantize(BigDecimal("10.25")))
  }
  @Test def quantizeNull() {
	  assertNull("Wrong quantized value", new ChromaticQuantizer().quantize(null))
  }
}