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
 * TODO refacto: lots of functions have access to the OutputStream, see if we could abstract it.
 * TODO refacto bis: rethink in functional immutable way...
 * Build a hierarchy of CSoundTokens That will contain ne Note(rename to CSound Note, correspond to the i statement)
 * 
 * genNote should not need it anymore in the nominal case (only produced one note and nothing else) 
 */
trait Generator[T <: GeneratorState] {
  def genScore(out: CSndOutput, genNote: (CSndOutput, T) => CSndNote, state: T): T;

  def genFreqTablesSection(out: CSndOutput, genFreqs: (CSndOutput, T) => List[CSndFreq], state: T): T;
  
  def generate(out: CSndOutput,
               genOrchestra: (CSndOutput, T) => T,
               genNote: (CSndOutput, T) => CSndNote,
               genFreqs: (CSndOutput, T) => List[CSndFreq],
               state: T);
  /**
   * To be redefined usually by a call to
   * {@link Generator#generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => Note, state: GenerationState)}
   *
   */
  def generate(out: CSndOutput)
}