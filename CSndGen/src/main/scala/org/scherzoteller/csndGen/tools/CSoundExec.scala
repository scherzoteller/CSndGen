package org.scherzoteller.csndGen.tools

import org.apache.commons.io.IOUtils
import scala.io.Source
import java.io.File
import java.lang.Boolean
import org.apache.commons.io.FilenameUtils

object CSoundExec {
  // To manage properties:
  // - http://scuff.googlecode.com/svn/trunk/scuff/docs/api/scuff/PropertiesFormatter.html (very basic but quite clever)
  // - http://commons.apache.org/proper/commons-configuration/
  // - spring
  private val PATH = "\"C:/Users/vloret.EUA/Desktop/Bordel/Softs Pluggins/snd/Csound6\""; // TODO properties
  def execResource(res: String): Int = {
    return exec(new File(classOf[CSoundExec].getResource(res).getFile()), false)
  }
  def exec(file: File, internalParams: Boolean): Int = {
    if (internalParams)
      exec(new ProcessBuilder("csound.exe", file.getCanonicalPath()))
    else {
      val wavFile = FilenameUtils.removeExtension(file.getCanonicalPath()) + ".wav"
      exec(new ProcessBuilder("csound.exe", file.getCanonicalPath(), "-Wo", wavFile))
    }

  };
  private def exec(processBuilder: ProcessBuilder): Int = {
    val env = processBuilder.environment();
    env.put("PATH", "%PATH%;" + PATH + "/bin");
    val p = processBuilder.start();
    // pure scala stream copy defined here: https://gist.github.com/ebruchez/781458
    // pure scala io lib available here: https://github.com/jesseeichar/scala-io
    // http://www.scala-sbt.org/0.13.0/api/index.html#sbt.IO$
    IOUtils.copy(p.getInputStream(), System.err);
    return p.waitFor();
  }
}

class CSoundExec {

}