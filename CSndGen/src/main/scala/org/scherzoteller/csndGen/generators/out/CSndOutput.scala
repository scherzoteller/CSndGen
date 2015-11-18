package org.scherzoteller.csndGen.generators.out

import java.io.OutputStream
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndNote
import org.scherzoteller.csndGen.musicbeans.scoretokens.CSndScoreToken
import org.omg.CORBA.portable.InputStream
import java.io.FileInputStream
import org.apache.commons.io.IOUtils
import java.io.File

class CSndOutput(output: OutputStream) {
  val END_OF_INSTRUCTION = ";\r\n";
  def write(note: CSndScoreToken) = {
    output.write(note.getValueAsString().getBytes());
  }


  def writeLn(note: CSndScoreToken) = {
    write(note);
    newLine();
  }
  
  private def newLine() = {
	  output.write(END_OF_INSTRUCTION.getBytes());
  }
  
  private def writeTag(tag: String) = {
    output.write('<');
    output.write(tag.getBytes());
    output.write('>');
  }
  
  private def writeEndTag(tag: String) = {
	  output.write('<');
	  output.write('/');
	  output.write(tag.getBytes());
	  output.write('>');
  }
  
  def encapsulate(tag: String, doInside : CSndOutput  => Unit ) = {
    writeTag(tag)
    newLine()
    doInside.apply(this)
    newLine()
    writeEndTag(tag)
    newLine()
  }
  def encapsulate(tag: String ): (CSndOutput => Unit) => Unit = {
    return (doInside : CSndOutput => Unit) => {
    	encapsulate(tag, doInside)
    }
  }
  
  def writeFile(file: File) = {
    IOUtils.copy(new FileInputStream(file), output)
  }
  
}

