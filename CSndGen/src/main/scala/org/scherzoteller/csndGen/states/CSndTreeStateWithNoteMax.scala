package org.scherzoteller.csndGen.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndScore
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndFreqTable
import scala.beans.BeanProperty

class CSndTreeStateWithNoteMax(@BeanProperty _orchestra: CSndOrchestra, @BeanProperty _freqs: CSndFreqTable, @BeanProperty _score: CSndScore, @BeanProperty _nbNotes: Int) extends CSndTreeState(_orchestra, _freqs, _score) {
  def continueScore(): Boolean = {
		  _nbNotes > 0
  }
  
  def noteGenerated(note: CSndNote): GenerationState = {
     //System.err.println("note generated state="+this )
    if(_nbNotes % 100 == 0){
      System.err.println("remaining: "+_nbNotes)
    }
		  new CSndTreeStateWithNoteMax(_orchestra, _freqs, _score.append(note), _nbNotes-1)
  }
  def tablesGenerated(pFreqs: List[CSndFreq]): GenerationState = {
		  System.err.println("table generated state="+this )
		  new CSndTreeStateWithNoteMax(_orchestra, new CSndFreqTable(pFreqs), _score, _nbNotes)
  }

  def orchestraGenerated(orchestra: CSndOrchestra): GenerationState = {
    System.err.println("orchestra generated state="+this )
    new CSndTreeStateWithNoteMax(orchestra, _freqs, _score, _nbNotes) 
    }
  
}



object CSndTreeStateWithNoteMax {
  def nbNotesState(_nbNotes: Int): CSndTreeStateWithNoteMax = new CSndTreeStateWithNoteMax(CSndOrchestra.nilObject , CSndFreqTable.nilObject, CSndScore.nilObject, _nbNotes);
  def nbNotesStateWithOrchestra(_orchestra: CSndOrchestra, _nbNotes: Int): CSndTreeStateWithNoteMax = new CSndTreeStateWithNoteMax(_orchestra , CSndFreqTable.nilObject, CSndScore.nilObject, _nbNotes);
}