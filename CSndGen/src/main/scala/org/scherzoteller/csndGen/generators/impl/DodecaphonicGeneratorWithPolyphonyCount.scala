package org.scherzoteller.csndGen.generators.impl

import java.io.OutputStream
import java.io.File
import org.scherzoteller.csndGen.generators.Generator
import org.scherzoteller.csndGen.generators.GenerationState
import org.scherzoteller.csndGen.musicbeans.CSndNote
import org.scherzoteller.csndGen.generators.GenerationState
import org.scherzoteller.csndGen.quantizers.ChromaticQuantizer
import org.scherzoteller.csndGen.musicbeans.CSndNote
import org.scherzoteller.csndGen.musicbeans.CSndNote
import org.scherzoteller.csndGen.musicbeans.CSndNote
import scala.util.Random
import org.scherzoteller.csndGen.quantizers.BasicDurationQuantizer
import org.scherzoteller.csndGen.quantizers.Quantizer
import org.scherzoteller.csndGen.musicbeans.CSndFreq
import org.scherzoteller.csndGen.musicbeans.CSndFreqAdditiveGen10
import org.scherzoteller.csndGen.musicbeans.CSndFreqAdditiveGen10
import org.scherzoteller.csndGen.musicbeans.CSndFreqStraightSegmentsGen7
import org.scherzoteller.csndGen.musicbeans.CSndFreq
import org.scherzoteller.csndGen.quantizers.StatefulBasicDurationQuantizer

/**
 * this is actually a simple copy of DummyGenerator with quantized values (will match tempered notes)
 * TODO polyphony limitation: fill algorythm will be quite touchy...
 * 
 * With the quantum based quantizer, we will be able to manage it with an array/list of remaining 
 * 
 */
class DodecaphonicGeneratorWithPolyphonyCount extends Generator {
  /**
   * Booooohhhhh this is mutable, non functional, not pretty, caca prout...
   * How do you manage state with stateless code?? that's a philosophical question...
   */
  class MyGenerationState extends GenerationState {
    var nbNotesToGen: Int = 150
    var tables: List[CSndFreq] = Nil
    
    def continueScore(): Boolean = {
      return nbNotesToGen > 0;
    }

    def noteGenerated(note: CSndNote) = {
      nbNotesToGen = nbNotesToGen - 1;
    }
    
    def tablesGenerated(tables: List[CSndFreq]) = {
      this.tables = tables
    }
  }

  def generate(out: OutputStream) = {
    val quantizer = new ChromaticQuantizer();
    val quantum = BigDecimal("0.5");
    val totalDuration = 140;
    val durationQuantizer = new StatefulBasicDurationQuantizer(quantum, 8, true, totalDuration);

    val genNote = (out: OutputStream, state: GenerationState) => {
      val instrument = Random.nextInt(4) + 1;
      val start = durationQuantizer.getQuantizedStart();
      val startStr = String.valueOf(start);
      val duration = durationQuantizer.getValidDuration(start);
      val pitch = quantizer.getRandowValue();
      val amplitude = quantizer.getUnQuantizedInBoundRandowValue(); // create seeders for amplitude
      new CSndNote(instrument, startStr, duration, pitch, amplitude)
    }

    val genFreqs = (out: OutputStream, state: GenerationState) => {
      val sine = new CSndFreqAdditiveGen10(1, 0, 4096, Array("1"))
      val triangle = new CSndFreqStraightSegmentsGen7(2, 0, 16384, Array("0", "4096", "1", "8192", "-1", "4097", "0"))
      val sawtooth = new CSndFreqStraightSegmentsGen7(3, 0, 16384, Array("-1", "16385", "1"))
      val square = new CSndFreqStraightSegmentsGen7(4, 0, 16384, Array("1", "8192", "1", "0", "-1", "8192", "-1"))
      sine :: triangle :: sawtooth :: square :: Nil
    }

    val orchestraFile = new File(this.getClass().getResource("/fourAnalogWaves.orc").getFile());
    val state = new MyGenerationState();
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