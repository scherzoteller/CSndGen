package org.scherzoteller.csndgen.quantizers

import org.junit.Before
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.scherzoteller.csndGen.quantizers.ChromaticQuantizer
import org.scherzoteller.csndGen.quantizers.BasicDurationQuantizer

class BasicDurationQuantizerTest extends AssertionsForJUnit {
  // http://www.scalatest.org/getting_started_with_junit_4_in_scala
  // http://www.scalatest.org/download
  @Before def initialize() {

  }
  @Test def getRandowValueTuple() {
    val quantum = BigDecimal("0.5");
    val totalDuration = 70;
    val durationQuantizer = new BasicDurationQuantizer(quantum, 8, true, totalDuration);
    
    for(a <- 1 until 1000){
      val rTuple = durationQuantizer.getRandowValueTuple();
      assertTrue(rTuple.getStart >= 0)
      assertTrue(rTuple.getStart <= 70)
      assertTrue(rTuple.getDuration >= 0)
      assertTrue(rTuple.getDuration <= BigDecimal(70)*quantum)
    }
  }
}