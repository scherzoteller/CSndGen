package org.scherzoteller.csdnGen.tools

import org.apache.commons.io.IOUtils

class CSoundExec {
	def exec(filename: String) = {
	  val PATH = "\"C:/Users/vloret.EUA/Desktop/Bordel/Softs Pluggins/snd/Csound6\"";
	  val pb = new ProcessBuilder("csound.exe", filename);
	  val env = pb.environment();
	  env.put("PATH", "%PATH%;"+PATH+"/bin");
	  println("before start");
	  val p = pb.start();
	  // pure scala stream copy defined here: https://gist.github.com/ebruchez/781458
	  // pure scala io lib available here: https://github.com/jesseeichar/scala-io
	  // http://www.scala-sbt.org/0.13.0/api/index.html#sbt.IO$
	  IOUtils.copy(p.getInputStream(), System.err);
	};
}