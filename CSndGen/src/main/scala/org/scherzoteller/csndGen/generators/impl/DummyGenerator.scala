package org.scherzoteller.csndGen.generators.impl

import java.io.File

import scala.BigDecimal
import scala.util.Random

import org.scherzoteller.csndGen.generators.Generator
import org.scherzoteller.csndGen.generators.out.CSndOutput
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreqAdditiveGen10
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreqStraightSegmentsGen7
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndFileOrchestra
import org.scherzoteller.csndGen.quantizers.BasicDurationQuantizer
import org.scherzoteller.csndGen.quantizers.ChromaticQuantizer
import org.scherzoteller.csndGen.states.CSndTreeStateWithNoteMax
import org.scherzoteller.csndGen.states.GenerationState

class DummyGenerator(val nbNotes: Int) extends Generator[CSndTreeStateWithNoteMax] {
  def generate(out: CSndOutput) = {
    val quantizer = new ChromaticQuantizer();
    val quantum = BigDecimal("0.5");
    val totalDuration = 70;
    val durationQuantizer = new BasicDurationQuantizer(quantum, 8, true, totalDuration);

    val genNote = (out: CSndOutput, state: GenerationState) => {
      val instrument = Random.nextInt(4) + 1;
      val start = durationQuantizer.getQuantizedStart();
      val startStr = String.valueOf(start);
      val duration = durationQuantizer.getValidDuration(start);
      val pitch = quantizer.getUnQuantizedInBoundRandowValue();
      val amplitude = quantizer.getUnQuantizedInBoundRandowValue(); // create seeders for amplitude
      new CSndNote(instrument, startStr, duration, pitch, amplitude)
    }

    val genFreqs = (out: CSndOutput, state: GenerationState) => {
      val sine = new CSndFreqAdditiveGen10(1, 0, 4096, Array("1"))
      val triangle = new CSndFreqStraightSegmentsGen7(2, 0, 16384, Array("0", "4096", "1", "8192", "-1", "4097", "0"))
      val sawtooth = new CSndFreqStraightSegmentsGen7(3, 0, 16384, Array("-1", "16385", "1"))
      val square = new CSndFreqStraightSegmentsGen7(4, 0, 16384, Array("1", "8192", "1", "0", "-1", "8192", "-1"))
      sine :: (triangle :: (sawtooth :: (square :: Nil)))
    }

    val state = CSndTreeStateWithNoteMax.nbNotesState(nbNotes)
    // FIXME add orchestra generator in state impl... 
    
    
    
		val orchestraFile = new File(DummyGenerator.this.getClass().getResource("/fourAnalogWaves.orc").getFile());
    generate(out, getDeferedFileOrchestraGenerator(orchestraFile), genNote, genFreqs, state);
  }

}