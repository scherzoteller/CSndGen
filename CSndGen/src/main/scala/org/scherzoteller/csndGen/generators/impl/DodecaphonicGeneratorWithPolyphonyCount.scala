package org.scherzoteller.csndGen.generators.impl

import java.io.OutputStream
import java.io.File
import org.scherzoteller.csndGen.generators.MutableGenerator
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.quantizers.ChromaticQuantizer
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import scala.util.Random
import org.scherzoteller.csndGen.quantizers.BasicDurationQuantizer
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.quantizers.StatefulBasicDurationQuantizer
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreqAdditiveGen10
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreqStraightSegmentsGen7
import org.scherzoteller.csndGen.generators.states.DodecaphonicGeneratorState
import org.scherzoteller.csndGen.generators.states.GeneratorState

/**
 * this is actually a simple copy of DummyGenerator with quantized values (will match tempered notes)
 * TODO polyphony limitation: fill algorythm will be quite touchy...
 * 
 * With the quantum based quantizer, we will be able to manage it with an array/list of remaining 
 * 
 */
class DodecaphonicGeneratorWithPolyphonyCount extends MutableGenerator[DodecaphonicGeneratorState] {
  def generate(out: OutputStream) = {
    val quantizer = new ChromaticQuantizer();
    val quantum = BigDecimal("0.5");
    val totalDuration = 140;
    val durationQuantizer = new StatefulBasicDurationQuantizer(quantum, 8, true, totalDuration);

    val genNote = (out: OutputStream, state: DodecaphonicGeneratorState) => {
      val instrument = Random.nextInt(4) + 1;
      val start = durationQuantizer.getQuantizedStart();
      val startStr = String.valueOf(start);
      val duration = durationQuantizer.getValidDuration(start);
      val pitch = quantizer.getRandowValue();
      val amplitude = quantizer.getUnQuantizedInBoundRandowValue(); // create seeders for amplitude
      new CSndNote(instrument, startStr, duration, pitch, amplitude)
    }

    val genFreqs = (out: OutputStream, state: DodecaphonicGeneratorState) => {
      val sine = new CSndFreqAdditiveGen10(1, 0, 4096, Array("1"))
      val triangle = new CSndFreqStraightSegmentsGen7(2, 0, 16384, Array("0", "4096", "1", "8192", "-1", "4097", "0"))
      val sawtooth = new CSndFreqStraightSegmentsGen7(3, 0, 16384, Array("-1", "16385", "1"))
      val square = new CSndFreqStraightSegmentsGen7(4, 0, 16384, Array("1", "8192", "1", "0", "-1", "8192", "-1"))
      sine :: triangle :: sawtooth :: square :: Nil
    }

    val orchestraFile = new File(this.getClass().getResource("/fourAnalogWaves.orc").getFile());
    val state = new DodecaphonicGeneratorState();
    generate(out, getFileOrchestraGenerator(orchestraFile), genNote, genFreqs, state);
    
    val qFill = durationQuantizer.getQuantumFill()
    val sb = new StringBuilder("[");
    for(i<- 0 until qFill.length){
      sb.append(qFill(i))
      if(i != qFill.length-1) sb.append(", ") 
    }
    sb.append("]")
    System.err.println(sb);
  }

}