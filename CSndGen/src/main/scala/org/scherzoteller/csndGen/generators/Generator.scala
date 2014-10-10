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
    while (state.continueScore) {
      {
        val note = genNote(out, state);
        // FIXME Findbugs would say that this is default encoding dependent... (I really need to encapsulate output)
        out.write(note.getValueAsString().getBytes());
        state.noteGenerated(note);
      }
    }
  }

  def generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => CSndNote, state: GenerationState) = {
    out.write("<CsoundSynthesizer>\n".getBytes());
    out.write("<CsOptions>\n".getBytes());
    out.write("</CsOptions>\n".getBytes());
    out.write("<CsInstruments>\n".getBytes());
    genOrchestra(out, state)
    out.write("</CsInstruments>\n".getBytes());
    
    
    out.write("<CsScore>\n".getBytes());
    genScore(out, genNote, state);
    out.write("</CsScore>\n".getBytes());
    out.write("</CsoundSynthesizer>\n".getBytes());
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