package org.scherzoteller.csndGen.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement

/**
 * Booooohhhhh this is mutable, non functional, not pretty, caca prout...
 * How do you manage state with stateless code?? that's a philosophical question... I'll let Haskellers and other pure functional language aficionados answer
 */
class DodecaphonicGeneratorState extends GenerationState {
  var lastNote: CSndNotePlacement = CSndNotePlacement.nilObject()

  var nbNotesToGen: Int = 150
  var tables: List[CSndFreq] = Nil

  def continueScore(): Boolean = {
    return nbNotesToGen > 0;
  }

  def noteGenerated(note: CSndNote) = {
    nbNotesToGen = nbNotesToGen - 1;
    this
  }

  def tablesGenerated(tables: List[CSndFreq]) = {
    this.tables = tables
    this
  }
}



object DodecaphonicGeneratorState {
  
}