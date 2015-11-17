package org.scherzoteller.csndGen.generators

import java.io.OutputStream
import java.io.File
import org.apache.commons.io.IOUtils
import java.io.FileInputStream
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.states.GenerationState

/**
 * TODO refacto: lots of functions have access to the OutputStream, see if we could abstract it.
 * TODO refacto bis: rethink in functional immutable way...
 * Build a hierarchy of CSoundTokens That will contain ne Note(rename to CSound Note, correspond to the i statement)
 * 
 * genNote should not need it anymore in the nominal case (only produced one note and nothing else) 
 */
@Deprecated
trait MutableGenerator {
  def genScore(out: OutputStream, genNote: (OutputStream, GenerationState) => CSndNote, state: GenerationState) = {
    while (state.continueScore) {
      {
        val note = genNote(out, state);
        // FIXME Findbugs would say that this is default encoding dependent... (I really need to encapsulate output)
        out.write(note.getValueAsString().getBytes());
        out.write(';');
        out.write('\r');
        out.write('\n');
        state.noteGenerated(note);
      }
    }
  }

  def genTablesSection(out: OutputStream, genTables: (OutputStream, GenerationState) => List[CSndFreq], state: GenerationState): GenerationState = {
    val tables = genTables(out, state);
    out.write(tables.map((freq) => {freq.getValueAsString()}).mkString(";\r\n").getBytes());
    out.write(';');
    out.write('\r');
    out.write('\n')
    state.tablesGenerated(tables);
  }
  
  def generate(out: OutputStream, genOrchestra: (OutputStream, GenerationState) => Unit, genNote: (OutputStream, GenerationState) => CSndNote, genTables: (OutputStream, GenerationState) => List[CSndFreq], state: GenerationState) = {
    out.write("<CsoundSynthesizer>\r\n".getBytes());
    out.write("<CsOptions>\r\n".getBytes());
    out.write("</CsOptions>\r\n".getBytes());
    out.write("<CsInstruments>\r\n".getBytes());
    genOrchestra(out, state)
    out.write("</CsInstruments>\r\n".getBytes());
    
    
    out.write("<CsScore>\r\n".getBytes());
    genTablesSection(out, genTables, state);
    genScore(out, genNote, state);
    out.write("</CsScore>\r\n".getBytes());
    out.write("</CsoundSynthesizer>\r\n".getBytes());
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