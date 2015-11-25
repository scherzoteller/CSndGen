package org.scherzoteller.csndGen.generators.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra

trait GeneratorState {
  def continueScore(): Boolean
  def noteGenerated(note: CSndNote): GeneratorState
  def tablesGenerated(freqs: List[CSndFreq]): GeneratorState
  def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState
}

private class NbNotesState(val nbNotes: Int) extends GeneratorState {
  def continueScore(): Boolean = { nbNotes > 0 }
  def noteGenerated(note: CSndNote) = { new NbNotesState(nbNotes - 1) }
  def tablesGenerated(freqs: List[CSndFreq]) = { this }
  def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState = { this }
}

object GeneratorState {
  val nullStateInst = new GeneratorState() {
    def continueScore(): Boolean = { false }
    def noteGenerated(note: CSndNote) = { this }
    def tablesGenerated(freqs: List[CSndFreq]) = { this }
    def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState = { this }
  };

  val nNotesState = new GeneratorState() {
    def continueScore(): Boolean = { false }
    def noteGenerated(note: CSndNote) = { this }
    def tablesGenerated(freqs: List[CSndFreq]) = { this }
    def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState = { this }
  };

  def nullState(): GeneratorState = { nullStateInst }

  // TODO immutable state
  //def nbNotesState(nbNotes: Int): GenerationState = new NbNotesState(nbNotes);
  @Deprecated
  def nbNotesState(_nbNotes: Int): GeneratorState = new GeneratorState() {
    var nbNotes: Int = _nbNotes;
    def continueScore(): Boolean = { nbNotes = nbNotes - 1; nbNotes > 0 }
    def noteGenerated(note: CSndNote) = { this }
    def tablesGenerated(freqs: List[CSndFreq]) = { this }
    def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState = { this }
  };

}