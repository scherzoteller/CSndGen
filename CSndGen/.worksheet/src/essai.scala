
import scala.util.Random
import org.apache.commons.io.FileUtils

class Toto{
	def this(content: String) = {
	this()
		System.err.println(content)
	}
	
}

object essai {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(217); 
  println("Welcome to the Scala worksheet");$skip(97); 

  val genRandomIntInBound = (start: Int, end: Int) => start + (Random.nextInt * (end - start));System.out.println("""genRandomIntInBound  : (Int, Int) => Int = """ + $show(genRandomIntInBound ));$skip(30); val res$0 = 
  genRandomIntInBound(0, 100);System.out.println("""res0: Int = """ + $show(res$0));$skip(93); 

  val genRandomIntInBound2 = (start: Int, end: Int) => start + Random.nextInt(end - start);System.out.println("""genRandomIntInBound2  : (Int, Int) => Int = """ + $show(genRandomIntInBound2 ));$skip(31); val res$1 = 
  genRandomIntInBound2(0, 100);System.out.println("""res1: Int = """ + $show(res$1));$skip(26); val res$2 = 

  Random.nextInt(4) + 1;System.out.println("""res2: Int = """ + $show(res$2));$skip(22); val res$3 = 

  Random.nextInt(4);System.out.println("""res3: Int = """ + $show(res$3))}
}
