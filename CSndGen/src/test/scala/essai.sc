import scala.util.Random

object essai {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val genRandomIntInBound = (start: Int, end: Int) => start + (Random.nextInt * (end - start))
                                                  //> genRandomIntInBound  : (Int, Int) => Int = <function2>
	genRandomIntInBound(0,100)                //> res0: Int = 170879384
  
  val genRandomIntInBound2 = (start: Int, end: Int) => start + Random.nextInt(end - start)
                                                  //> genRandomIntInBound2  : (Int, Int) => Int = <function2>
	genRandomIntInBound2(0,100)               //> res1: Int = 84
	
	
	Random.nextInt(4)+1                       //> res2: Int = 3
 
 Random.nextInt(4)                                //> res3: Int = 3
 }