package org.scherzoteller.csndGen.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndScore
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndFreqTable
import scala.beans.BeanProperty
import org.scherzoteller.csndGen.generators.out.CSndOutput

abstract class CSndTreeState(@BeanProperty _orchestra: CSndOrchestra, @BeanProperty _freqs: CSndFreqTable, @BeanProperty _score: CSndScore) extends GenerationState {
  def orchestra = _orchestra
  def score = _score
  def freqs = _freqs
 
  
  override def toString(): String = {
    _orchestra.toString() + "\n" + _score.toString() + "\n" + _freqs.toString()
  }
}
