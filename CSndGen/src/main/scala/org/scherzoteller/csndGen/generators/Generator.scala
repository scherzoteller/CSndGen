package org.scherzoteller.csndGen.generators

import java.io.OutputStream
import java.io.File
import org.apache.commons.io.IOUtils
import java.io.FileInputStream
import org.scherzoteller.csndGen.musicbeans.CSndNote

/**
 * TODO refacto: lots of functions have access to the OutputStream, see if we could abstract it.
 * Build a hierarchy of CSoundTokens That will contain ne Note(rename to CSound Note, correspond to the i statement)
 * 
 */
trait Generator {
  def genScore(out: OutputStream, genNote: (OutputStream, GenerationState) => CSndNote, state: GenerationState) = {
    while(state.continueScore){
      state.noteGenerated(genNote(out, state));
    }
  }

  def generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => CSndNote, state: GenerationState) = {
    genOrchestra(out, state)
	genScore(out, genNote, state);
  }
  /**
   * To be redefined usually by a call to 
   * {@link Generator#generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => Note, state: GenerationState)}
   * 
   */
  def generate(out: OutputStream)

  def getFileOrchestraGenerator(scoreFile: File): ((OutputStream, GenerationState) => Unit) =
	{
	  return (out: OutputStream, state: GenerationState) => { IOUtils.copy(new FileInputStream(scoreFile), out) };
	}
}