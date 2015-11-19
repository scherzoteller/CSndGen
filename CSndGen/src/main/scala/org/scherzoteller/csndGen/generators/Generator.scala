package org.scherzoteller.csndGen.generators

import java.io.OutputStream
import java.io.File
import org.apache.commons.io.IOUtils
import java.io.FileInputStream
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.states.GenerationState
import org.scherzoteller.csndGen.generators.out.CSndOutput

/**
 * TODO refacto: lots of functions have access to the OutputStream, see if we could abstract it.
 * TODO refacto bis: rethink in functional immutable way...
 * Build a hierarchy of CSoundTokens That will contain ne Note(rename to CSound Note, correspond to the i statement)
 *
 * genNote should not need it anymore in the nominal case (only produced one note and nothing else)
 */
trait Generator {
  def genScore(out: CSndOutput, genNote: (CSndOutput, GenerationState) => CSndNote, state: GenerationState) = {
    while (state.continueScore) {
      {
        val note = genNote(out, state);
        out.writeLn(note);
        state.noteGenerated(note);
      }
    }
  }

  def genTablesSection(out: CSndOutput, genTables: (CSndOutput, GenerationState) => List[CSndFreq], state: GenerationState): GenerationState = {
    val tables = genTables(out, state);
    tables.foreach { freq => out.writeLn(freq) }
    state.tablesGenerated(tables);
  }

  def generate(out: CSndOutput,
               genOrchestra: (CSndOutput, GenerationState) => Unit,
               genNote: (CSndOutput, GenerationState) => CSndNote,
               genTables: (CSndOutput, GenerationState) => List[CSndFreq],
               state: GenerationState) = {
    // TODO propagate state to pass immutable
    // Immutable doesn't seems so adapted to xml structure... this will force us to store string values... annoying
    // Let's introduce the concept of future generators? gas storage plant / kludge? 
    // The concept of parallelism might be interesting however:
    // - basic generators will simply bufferize their output and won't be optimized/reactive (long 1st step: "state evolution+bufferizing" -  short 2nd step: "writing buffer")
    // - reactive generators will have some speed state evolution steps and longer but differed writing (NB: not very adapted to limited polyphony since for that state contains the )
    
    
    out.encapsulate("CsoundSynthesizer"){
      out.encapsulate("CsOptions"){
        //empty section
      }
      out.encapsulate("CsInstruments"){
        genOrchestra(out, state)
      }
      out.encapsulate("CsScore"){
        genTablesSection(out, genTables, state);
        genScore(out, genNote, state);
      }
    }
  }
  /**
   * To be redefined usually by a call to
   * {@link Generator#generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => Note, state: GenerationState)}
   *
   */
  def generate(out: CSndOutput)

  def getFileOrchestraGenerator(scoreFile: File): ((CSndOutput, GenerationState) => Unit) =
    {
      return (out: CSndOutput, state: GenerationState) => { out.writeFile(scoreFile) };
    }
}