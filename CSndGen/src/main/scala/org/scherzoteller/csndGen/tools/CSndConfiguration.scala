package org.scherzoteller.csndGen.tools

import org.apache.commons.io.IOUtils
import scala.io.Source
import java.io.File
import java.lang.Boolean
import org.apache.commons.io.FilenameUtils
import org.apache.commons.configuration.PropertiesConfiguration
import org.apache.commons.configuration.SystemConfiguration
import org.apache.commons.configuration.CompositeConfiguration 
import org.apache.commons.configuration.Configuration

object CSndConfiguration {
  private val PROPERTY_FILE = "/csndgen.properties";
  private val CONFIGURATION = getBundle();
  def getBundle(): Configuration = {
     try{
     val cfg = new CompositeConfiguration()
     cfg.addConfiguration(new SystemConfiguration())
     cfg.addConfiguration(new PropertiesConfiguration(getClass.getResource(PROPERTY_FILE).getFile()))
     cfg
     } catch {
     case t: Throwable => {t.printStackTrace(); null }
     }
  }
}
