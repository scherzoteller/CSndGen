package org.scherzoteller.csndGen.musicbeans.scoretokens.writable

import org.scherzoteller.csndGen.generators.out.CSndOutput
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndFreq

class CSndFreqTable(val freqs: List[CSndFreq]) extends CSndWritable {
  def writeTo(out: CSndOutput) = {
    if(freqs != Nil){
    	freqs.head.writeTo(out)
      new CSndFreqTable(freqs.tail).writeTo(out)
    }
  }
  
  def append(freq: CSndFreq): CSndFreqTable = {
    new CSndFreqTable(freqs :+ freq)
  }
}