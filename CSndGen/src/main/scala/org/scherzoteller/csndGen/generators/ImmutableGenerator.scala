package org.scherzoteller.csndGen.generators

import java.io.OutputStream
import java.io.File
import org.apache.commons.io.IOUtils
import java.io.FileInputStream
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.generators.out.CSndOutput
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndFileOrchestra
import org.scherzoteller.csndGen.generators.states.TreeGeneratorState

/**
 * TODO refacto: lots of functions have access to the OutputStream, see if we could abstract it.
 * TODO refacto bis: rethink in functional immutable way...
 * Build a hierarchy of CSoundTokens That will contain ne Note(rename to CSound Note, correspond to the i statement)
 *
 * genNote should not need it anymore in the nominal case (only produced one note and nothing else)
 */
trait ImmutableGenerator[T <: TreeGeneratorState] extends Generator[T] {
  override def genScore(out: CSndOutput, genNote: (CSndOutput, T) => CSndNote, state: T): T = {
    if (state.continueScore) {
      val note = genNote(out, state);
      genScore(out, genNote, state.noteGenerated(note).asInstanceOf[T])
    } else {
      state
    }
  }

  override def genFreqTablesSection(out: CSndOutput, genFreqs: (CSndOutput, T) => List[CSndFreq], state: T): T = {
    state.tablesGenerated(genFreqs(out, state)).asInstanceOf[T];
  }

  private def chain(funToChain: List[(CSndOutput, T) => T], out: CSndOutput, state: T): T = {
    if (funToChain == Nil) {
      state
    } else {
      val newState = funToChain.head(out, state)
      chain(funToChain.tail, out, newState)
    }
  }

  private def finalWrite(out: CSndOutput, state: T): T = {
    out.encapsulate("CsoundSynthesizer") {
      out.encapsulate("CsOptions") {
        //empty section
      }
      out.encapsulate("CsInstruments") {
        state.orchestra.writeTo(out)
      }
      out.encapsulate("CsScore") {
        state.freqs.writeTo(out)
        state.score.writeTo(out)
      }
    }
    state
  }

  override def generate(out: CSndOutput,
               genOrchestra: (CSndOutput, T) => T,
               genNote: (CSndOutput, T) => CSndNote,
               genFreqs: (CSndOutput, T) => List[CSndFreq],
               state: T) = {
    val stateAfterGenerate = chain(
      List((o, s) => genOrchestra(o, s),
        (o, s) => genFreqTablesSection(o, genFreqs, s),
        (o, s) => genScore(o, genNote, s),
        finalWrite),
      out, state);
  }
  /**
   * To be redefined usually by a call to
   * {@link Generator#generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => Note, state: GenerationState)}
   *
   */
  override def generate(out: CSndOutput)

  def getDeferedFileOrchestraGenerator(scoreFile: File): (CSndOutput, T) => T =
	  {
		  return (out: CSndOutput, state: T) => { state.orchestraGenerated(new CSndFileOrchestra(scoreFile)).asInstanceOf[T] };
	  }

}