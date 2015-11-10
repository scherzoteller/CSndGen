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
import org.scherzoteller.csndGen.quantizers.StatefulBasicDurationQuantizer

class StatefulBasicDurationQuantizerTest extends AssertionsForJUnit {
  // http://www.scalatest.org/getting_started_with_junit_4_in_scala
  // http://www.scalatest.org/download
  @Before def initialize() {

  }
  @Test def getValidDurationTuple_getQuantizedStart() {
    val quantum = BigDecimal("0.5");
    val totalDuration = 70;
    val durationQuantizer = new StatefulBasicDurationQuantizer(quantum, 8, true, totalDuration);
    
    for(a <- 1 until 1000){
      val rTuple = durationQuantizer.getValidDurationTuple(durationQuantizer.getQuantizedStart());
      assertTrue(rTuple.getStart >= 0)
      assertTrue(rTuple.getStart <= 70)
      assertTrue(rTuple.getDuration >= 0)
      assertTrue(rTuple.getDuration <= BigDecimal(70)*quantum)
    }
    // http://www.codecommit.com/blog/scala/quick-explanation-of-scalas-syntax
    //val nbNotes = durationQuantizer.getQuantumFill().reduceLeft((a: Int, b: Int) => {a+b})
    //val nbNotes = durationQuantizer.getQuantumFill().reduceLeft[Int](_+_)
    val nbNotes = durationQuantizer.getQuantumFill().reduceLeft(_+_)
    assertTrue(nbNotes > 1000)
    assertTrue(nbNotes < 1000*8)
  }
}