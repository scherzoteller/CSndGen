package org.scherzoteller.csndGen.states

import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndOrchestra
import org.scherzoteller.csndGen.musicbeans.scoretokens.writable.CSndScore

class CSndTreeState(val _orchestra : CSndOrchestra, val _score: CSndScore) {
  def orchestra = _orchestra
  def score = _score
}