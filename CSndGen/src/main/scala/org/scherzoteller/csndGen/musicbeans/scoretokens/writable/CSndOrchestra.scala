package org.scherzoteller.csndGen.musicbeans.scoretokens.writable

import org.scherzoteller.csndGen.generators.out.CSndOutput

trait CSndOrchestra extends CSndWritable {

}

object CSndOrchestra {
  val _nilObject = new CSndOrchestra(){
    def writeTo(out: CSndOutput): Unit = {}
  }
  def nilObject = _nilObject
}