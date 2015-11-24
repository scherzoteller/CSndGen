package org.scherzoteller.csndGen.musicbeans.scoretokens.writable

import org.scherzoteller.csndGen.generators.out.CSndOutput
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndPolyphony

class CSndPolyphonyTable(val polyphony: List[CSndPolyphony]) extends CSndWritable {
  def writeTo(out: CSndOutput) = {
    
  }
  
  def append(note: CSndNote): CSndPolyphonyTable = {
    // TODO !!!
    this
  }
}