package org.scherzoteller.csndGen.musicbeans.scoretokens.writable

import org.scherzoteller.csndGen.generators.out.CSndOutput

trait CSndWritable {
  def writeTo(out: CSndOutput): Unit
}