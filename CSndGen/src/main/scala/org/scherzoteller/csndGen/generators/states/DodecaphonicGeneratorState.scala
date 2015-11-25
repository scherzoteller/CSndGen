package org.scherzoteller.csndGen.generators.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.CSndNotePlacement
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra
import scala.collection.immutable.Nil

class DodecaphonicGeneratorState extends GeneratorState {
  var lastNote: CSndNotePlacement = CSndNotePlacement.nilObject()

  var nbNotesToGen: Int = 150
  var tables: List[CSndFreq] = Nil

  def continueScore(): Boolean = {
    return nbNotesToGen > 0;
  }

  def noteGenerated(note: CSndNote): GeneratorState = {
    nbNotesToGen=nbNotesToGen -1
    this
  }

  def tablesGenerated(tables: List[CSndFreq]) = {
    this.tables = tables
    this
  }
  def orchestraGenerated(orchestra: CSndOrchestra): GeneratorState = { 
    this 
  }
}



object DodecaphonicGeneratorState {
  
}