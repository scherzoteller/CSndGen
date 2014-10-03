package org.scherzoteller.csdnGen.main

import org.scherzoteller.csdnGen.tools.CSoundExec

object MyTest {
	def main(args: Array[String]){
	  println("MyTest")
	  new CSoundExec().exec("C:/Graphane/eclipseKeplerScala/workspace/Generate/csndFiles/verySimple.csd")
	}
}