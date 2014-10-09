package org.scherzoteller.csndGen.generators

import org.scherzoteller.csndGen.musicbeans.CSndNote

trait GenerationState {
	def continueScore(): Boolean;
	
	def noteGenerated(note: CSndNote);
	
}