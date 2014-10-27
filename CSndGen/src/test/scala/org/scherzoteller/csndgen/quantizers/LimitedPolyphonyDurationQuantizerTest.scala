package org.scherzoteller.csndgen.quantizers

import org.scherzoteller.csndGen.quantizers.StatefulBasicDurationQuantizer
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.scherzoteller.csndGen.quantizers.LimitedPolyphonyDurationQuantizer

class LimitedPolyphonyDurationQuantizerTest {
  // http://www.scalatest.org/getting_started_with_junit_4_in_scala
  // http://www.scalatest.org/download
  @Before def initialize() {

  }
  @Test def getValidDurationTuple_getQuantizedStart() {
    val quantum = BigDecimal("0.5");
    val totalDuration = 70;
    val maxPolyphony = 4;
    val maxDuration = 8;
    val durationQuantizer = new LimitedPolyphonyDurationQuantizer(quantum, maxDuration, true, totalDuration, maxPolyphony);
    
    val nbNotesToGen = 1000;
    val myTuples = (1 until nbNotesToGen toList).map(_ => durationQuantizer.getValidDurationTuple(durationQuantizer.getQuantizedStart()) );
    myTuples.foreach((tuple: (Int, BigDecimal)) => {
      assertTrue(tuple._1 >= 0)
      assertTrue(tuple._1 <= totalDuration)
      assertTrue(tuple._2 >= 0)
      assertTrue(tuple._2 <= BigDecimal(70)*quantum)
    } );
    
    // stop it guy Scala one liners are cool but this one is a bit to much: OK for TU part ;-) => calculate the sum of the durations (_2 of the tuple) 
    val nbQuantumGenExpected = myTuples.reduceLeft((t1: (Int, BigDecimal), t2: (Int, BigDecimal)) =>  (t1._1 + t2._1, t1._2 + t2._2))._1; // 280
    val nbQuantumGen = durationQuantizer.getQuantumFill().reduceLeft(_+_) // 1000 => KO
    assertEquals("too many quantum gen", nbQuantumGenExpected,  nbQuantumGen);
    
    assertTrue("too many quantum created according to nb notes and max duration: expected nbQuantumGen("+nbQuantumGen+") < nbNotesToGen*maxDuration("+nbNotesToGen*maxDuration+")", nbQuantumGen <= nbNotesToGen*maxDuration)
    assertTrue("too many according to table size and max polyphony: expected nbQuantumGen("+nbQuantumGen+") <= totalDuration*maxPolyphony("+totalDuration*maxPolyphony+")", nbQuantumGen <= totalDuration*maxPolyphony)
    
  }

}