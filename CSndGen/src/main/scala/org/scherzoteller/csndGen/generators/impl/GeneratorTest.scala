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

class GeneratorTest extends Generator {
  /**
   * Booooohhhhh this is mutable, non functional, not pretty, caca prout...
   * How do you manage state with stateless code?? that's a philosophical question...
   */
  class MyGenerationState extends GenerationState {
    var nbNotesToGen: Int = 150;
    def continueScore(): Boolean = {
      return nbNotesToGen > 0;
    }

    def noteGenerated(note: CSndNote) = {
      nbNotesToGen = nbNotesToGen - 1;
    }
  }

  def generate(out: OutputStream) = {
    val quantizer = new ChromaticQuantizer();
    val genNote = (out: OutputStream, state: GenerationState) => {
      val instrument = Random.nextInt(4)+1;
      val start = "+";
      val duration = quantizer.getUnQuantizedInBoundRandowValue(); // create seeders for duration
      val pitch = quantizer.getUnQuantizedInBoundRandowValue();
      val amplitude = quantizer.getUnQuantizedInBoundRandowValue(); // create seeders for amplitude
      new CSndNote(instrument, start, duration, pitch, amplitude)
      
    	//new CSndNote(quantizer.getUnQuantizedInBoundRandowValue(), quantizer.getUnQuantizedInBoundRandowValue())
    }
    val orchestraFile = new File(this.getClass().getResource("/fourAnalogWaves.orc").getFile());
    val state = new MyGenerationState();
    generate(out, getFileOrchestraGenerator(orchestraFile), genNote, state);
  }

}