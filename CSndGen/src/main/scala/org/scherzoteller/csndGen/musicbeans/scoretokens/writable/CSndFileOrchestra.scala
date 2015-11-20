package org.scherzoteller.csndGen.musicbeans.scoretokens.writable

import java.io.File
import org.scherzoteller.csndGen.generators.out.CSndOutput

class CSndFileOrchestra(val file: File) extends CSndOrchestra{
  def writeTo(out: CSndOutput) = {
    out.writeFile(file)
  }
}