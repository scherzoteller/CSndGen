
import scala.util.Random

object essai {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(86); 
  println("Welcome to the Scala worksheet");$skip(98); 
  
  val genRandomIntInBound = (start: Int, end: Int) => start + (Random.nextInt * (end - start));System.out.println("""genRandomIntInBound  : (Int, Int) => Int = """ + $show(genRandomIntInBound ));$skip(28); val res$0 = 
	genRandomIntInBound(0,100);System.out.println("""res0: Int = """ + $show(res$0));$skip(95); 
  
  val genRandomIntInBound2 = (start: Int, end: Int) => start + Random.nextInt(end - start);System.out.println("""genRandomIntInBound2  : (Int, Int) => Int = """ + $show(genRandomIntInBound2 ));$skip(29); val res$1 = 
	genRandomIntInBound2(0,100);System.out.println("""res1: Int = """ + $show(res$1))}
  
  
}
