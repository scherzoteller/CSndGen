package org.scherzoteller.csndGen.generators

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq

trait GenerationState {
	def continueScore(): Boolean;
	
	def noteGenerated(note: CSndNote);
	
	def tablesGenerated(freqs: List[CSndFreq])
	
}