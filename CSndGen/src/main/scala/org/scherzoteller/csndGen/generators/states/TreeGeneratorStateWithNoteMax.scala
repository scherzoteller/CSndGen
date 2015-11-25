package org.scherzoteller.csndGen.generators.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndFreqTable
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndScore
import org.scherzoteller.csndGen.spectrogram.SpectrogramUtils
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class TreeGeneratorStateWithNoteMax(_orchestra: CSndOrchestra, _freqs: CSndFreqTable, _score: CSndScore, _nbNotes: Int) extends TreeGeneratorState(_orchestra, _freqs, _score) {
  val  LOG: Logger = LoggerFactory.getLogger(classOf[SpectrogramUtils]);
  def continueScore(): Boolean = {
		  _nbNotes > 0
  }
  
  def noteGenerated(note: CSndNote): GeneratorState = {
     //System.err.println("note generated state="+this )
    if(_nbNotes % 100 == 0){
      LOG.debug("noteGenerated remaining: "+_nbNotes)
    }
		new TreeGeneratorStateWithNoteMax(_orchestra, _freqs, _score.append(note), _nbNotes-1)
  }
  def tablesGenerated(pFreqs: List[CSndFreq]): GeneratorState = {
    LOG.debug("table generated state=" + this)
    new TreeGeneratorStateWithNoteMax(_orchestra, new CSndFreqTable(pFreqs), _score, _nbNotes)
  }

  def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState = {
    LOG.debug("orchestra generated state="+this )
    new TreeGeneratorStateWithNoteMax(orchestra, _freqs, _score, _nbNotes) 
  }
}



object TreeGeneratorStateWithNoteMax {
  def nbNotesState(_nbNotes: Int): TreeGeneratorStateWithNoteMax = new TreeGeneratorStateWithNoteMax(CSndOrchestra.nilObject ,CSndFreqTable.nilObject, CSndScore.nilObject, _nbNotes);
  def nbNotesStateWithOrchestra(_orchestra: CSndOrchestra, _nbNotes: Int): TreeGeneratorStateWithNoteMax = new TreeGeneratorStateWithNoteMax(_orchestra ,CSndFreqTable.nilObject, CSndScore.nilObject, _nbNotes);
}