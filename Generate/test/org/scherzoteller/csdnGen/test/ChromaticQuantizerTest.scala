package org.scherzoteller.csdnGen.test

import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.scherzoteller.csdnGen.ChromaticQuantizer;
import org.junit.Assert
import org.junit.Assert.assertEquals

class ChromaticQuantizerTest extends AssertionsForJUnit {
  // http://www.scalatest.org/getting_started_with_junit_4_in_scala
  // http://www.scalatest.org/download
  @Before def initialize() {

  }
  @Test def verifyEasy() {
    assertEquals("Wrong quantized value", BigDecimal("45"), new ChromaticQuantizer().quantize(BigDecimal("45.26")))
  }
}