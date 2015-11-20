package org.scherzoteller.csndGen.musicbeans.scoretokens.writable

import org.scherzoteller.csndGen.generators.out.CSndOutput
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote

class CSndScore(val notes: List[CSndNote]) extends CSndWritable {
  def writeTo(out: CSndOutput) = {
    if(notes != Nil){
    	notes.head.writeTo(out)
      new CSndScore(notes.tail).writeTo(out)
    }
  }
  
  def append(note: CSndNote): CSndScore = {
    new CSndScore(notes :+ note)
  }
}