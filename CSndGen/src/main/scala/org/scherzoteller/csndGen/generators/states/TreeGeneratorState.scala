package org.scherzoteller.csndGen.generators.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndScore
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndFreqTable
import scala.beans.BeanProperty

abstract class TreeGeneratorState(_orchestra: CSndOrchestra, _freqs: CSndFreqTable,  _score: CSndScore) extends GeneratorState {
  def orchestra = _orchestra
  def score = _score
  def freqs = _freqs
 
  
  override def toString(): String = {
    _orchestra.toString() + "\n" + _score.toString() + "\n" + _freqs.toString()
  }
}
