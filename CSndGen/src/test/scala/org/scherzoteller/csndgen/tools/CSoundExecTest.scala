package org.scherzoteller.csndgen.tools

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.scherzoteller.csndGen.tools.CSoundExec

class CSoundExecTest extends AssertionsForJUnit {
  @Before def initialize() {

  }
  @Test def quantizeInBounds() {
    assertEquals("exit code is not 0", 0, CSoundExec.execResource("/org/scherzoteller/csdngen/files/verySimple.csd"))
  }

}