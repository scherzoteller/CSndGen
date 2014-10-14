package org.scherzoteller.csndgen.generators.impl

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.scherzoteller.csndGen.generators.impl.DummyGenerator
import java.io.File
import java.io.FileOutputStream
import org.scherzoteller.csndGen.tools.CSoundExec
import org.junit.Assert
import org.apache.commons.io.IOUtils
import java.io.FileInputStream
import org.scherzoteller.csndGen.spectrogram.SpectrogramUtils

class DummyGeneratorTest extends AssertionsForJUnit {
  @Before def initialize() {

  }
  @Test def generateAndExec() {
    val gen = new DummyGenerator()
    val parentDir = "/org/scherzoteller/csdngen/files/"
    val file = new File(new File(classOf[DummyGeneratorTest].getResource(parentDir).getFile()), "essai.csd")
    val out = new FileOutputStream(file)
    gen.generate(out)
    out.close()
    val genFileContent = IOUtils.toString(new FileInputStream(file))
    assertNotNull("File is empty", genFileContent)
    assertTrue("File is empty", genFileContent.length() > 0)
    val executor = new CSoundExec(file, false)
    assertEquals("exit code is not 0", 0, executor.exec());
    SpectrogramUtils.renderSpectrogramFile(executor.getWavFileName())
    
    
  }

}