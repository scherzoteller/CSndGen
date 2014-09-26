package org.scherzoteller.csdnGen.tools

class CSoundExec {
  
	def exec(filename: String) = {
	  val PATH = "\"C:/Users/vloret.EUA/Desktop/Bordel/Softs Pluggins/snd/Csound6\"";
	  val pb = new ProcessBuilder("csound.exe", filename);
	  val env = pb.environment();
	  env.put("PATH", "%PATH%;"+PATH+"/bin");
	  val p = pb.start();
	};
}