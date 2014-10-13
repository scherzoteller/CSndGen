package org.scherzoteller.csndGen.generators

import org.scherzoteller.csndGen.musicbeans.CSndNote
import org.scherzoteller.csndGen.musicbeans.CSndFreq

trait GenerationState {
	def continueScore(): Boolean;
	
	def noteGenerated(note: CSndNote);
	
	def tablesGenerated(freqs: List[CSndFreq])
	
}