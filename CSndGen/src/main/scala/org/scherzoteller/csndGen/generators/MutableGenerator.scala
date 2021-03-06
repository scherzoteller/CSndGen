package org.scherzoteller.csndGen.generators

import java.io.OutputStream
import java.io.File
import org.apache.commons.io.IOUtils
import java.io.FileInputStream
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.generators.states.GeneratorState
import org.scherzoteller.csndGen.generators.out.CSndOutput

/**
 * Build a hierarchy of CSoundTokens That will contain ne Note(rename to CSound Note, correspond to the i statement)
 *
 * genNote should not need it anymore in the nominal case (only produced one note and nothing else)
 * 
 * http://www.csounds.com/manual/html/ftgen.html => with that syntax, we should be able to include frequency in orchestra instead of score
 * (cf. http://iainmccurdy.org/CsoundRealtimeExamples/Cabbage/Synths/WavetableSynth.csd !!!)
 * (list of files here http://iainmccurdy.org/csound.html)
 * 
 */
trait MutableGenerator[T <: GeneratorState] extends Generator[T] {
  override def genScore(out: CSndOutput, genNote: (CSndOutput, T) => CSndNote, state: T): T = {
    while (state.continueScore) {
      {
        val note = genNote(out, state);
        out.writeLn(note)
        state.noteGenerated(note);
      }
    }
    state
  }

  override def genFreqTablesSection(out: CSndOutput, genTables: (CSndOutput, T) => List[CSndFreq], state: T): T = {
    val tables = genTables(out, state);
    tables.foreach { freq => out.writeLn(freq) }
    state.tablesGenerated(tables).asInstanceOf[T];
  }

  override def generate(out: CSndOutput,
                        genOrchestra: (CSndOutput, T) => T,
                        genNote: (CSndOutput, T) => CSndNote,
                        genTables: (CSndOutput, T) => List[CSndFreq],
                        state: T) = {
    out.encapsulate("CsoundSynthesizer") {
      out.encapsulate("CsOptions") {
        //empty section
      }
      out.encapsulate("CsInstruments") {
        genOrchestra(out, state)
      }
      out.encapsulate("CsScore") {
        genFreqTablesSection(out, genTables, state);
        genScore(out, genNote, state);
      }
    }
  }

  def getFileOrchestraGenerator(scoreFile: File): (CSndOutput, T) => T =
    {
      return (out: CSndOutput, state: T) => { out.writeFile(scoreFile); state };
    }

  def getFileOrchestraGenerator(filename: String) : (CSndOutput, T) => T =
  {
    return getFileOrchestraGenerator(new File(this.getClass().getResource(filename).getFile()))
  }
}