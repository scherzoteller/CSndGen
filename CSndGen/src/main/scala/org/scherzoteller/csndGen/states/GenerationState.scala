package org.scherzoteller.csndGen.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq

trait GenerationState {
  def continueScore(): Boolean
  def noteGenerated(note: CSndNote): GenerationState
  def tablesGenerated(freqs: List[CSndFreq]): GenerationState
}

private class NbNotesState(val nbNotes: Int) extends GenerationState {
  def continueScore(): Boolean = { nbNotes > 0 }
  def noteGenerated(note: CSndNote) = { new NbNotesState(nbNotes - 1) }
  def tablesGenerated(freqs: List[CSndFreq]) = { this }
}

object GenerationState {
  val nullStateInst = new GenerationState() {
    def continueScore(): Boolean = { false }
    def noteGenerated(note: CSndNote) = { this }
    def tablesGenerated(freqs: List[CSndFreq]) = { this }
  };

  val nNotesState = new GenerationState() {
    def continueScore(): Boolean = { false }
    def noteGenerated(note: CSndNote) = { this }
    def tablesGenerated(freqs: List[CSndFreq]) = { this }
  };

  def nullState(): GenerationState = { nullStateInst }

  // TODO immutable state
  //def nbNotesState(nbNotes: Int): GenerationState = new NbNotesState(nbNotes);
  def nbNotesState(_nbNotes: Int): GenerationState = new GenerationState() {
    var nbNotes: Int = _nbNotes;
    def continueScore(): Boolean = { nbNotes = nbNotes - 1; nbNotes > 0 }
    def noteGenerated(note: CSndNote) = { this }
    def tablesGenerated(freqs: List[CSndFreq]) = { this }
  };

}